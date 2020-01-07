package com.gzy.im.service;

import com.gzy.im.core.math.Logic;
import com.gzy.im.dto.ChatDTO;
import com.gzy.im.dto.ChatSessionDTO;
import com.gzy.im.model.Account;
import com.gzy.im.model.Chat;
import com.gzy.im.model.ChatSession;
import com.gzy.im.repository.AccountRepository;
import com.gzy.im.repository.ChatRepository;
import com.gzy.im.repository.ChatSessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChatService {
    @Resource
    ChatRepository chatRepository;
    @Resource
    ChatSessionRepository chatSessionRepository;
    @Resource
    AccountService accountService;
    @Resource
    AccountRepository accountRepository;


    /**
     * 创建 会话
     *
     * @param fromId 我的id
     * @param toId   对方的id
     */
    public ChatSessionDTO createSession(Long fromId, Long toId) {

        if (!accountService.existsById(fromId)) {
            throw new RuntimeException("不存在此用户：" + fromId);
        }
        if (!accountService.existsById(toId)) {
            throw new RuntimeException("不存在此用户：" + toId);
        }


        ChatSession chatSession;
        // 防止重复创建
        ChatSession a1 = chatSessionRepository.findByAid1EqualsAndAid2Equals(fromId, toId);
        if (a1 != null) {
            chatSession = a1;
        } else {

            ChatSession a2 = chatSessionRepository.findByAid1EqualsAndAid2Equals(fromId, toId);
            if (a2 != null) {
                chatSession = a2;
            } else {
                chatSession = new ChatSession();
                chatSession.setAid1(fromId);
                chatSession.setAid2(toId);
                chatSessionRepository.save(chatSession);
            }
        }

        ChatSessionDTO chatSessionDTO = new ChatSessionDTO();

        BeanUtils.copyProperties(chatSession,chatSessionDTO);


        chatSessionDTO.setAccount(accountService.getAccount(toId));

        if (chatSession.getLastChatId() != null) {
            var chat = chatRepository.findById(chatSession.getLastChatId());
            chatSessionDTO.setLastChat(chat.get());
        }

        return chatSessionDTO;
    }

    /**
     * 获取 消息（会话）列表
     * @param meId 当前用户 id
     * @return
     */
    public List<ChatSessionDTO> showAllSession(Long meId) {

        // 查询聊天的 连接
        List<ChatSession> all = chatSessionRepository.findAllByAid1EqualsOrAid2EqualsOrderByLastModifiedDateDesc(meId, meId);
        // 定义返回的数据
        List<ChatSessionDTO> resList = new ArrayList<>(all.size());

        // 存储出现的所有用户ids 使用 set 排重
        Set<Long> aidSet = new HashSet<>();
        // chat 的 ids
        Set<Long> chatIdSet = new HashSet<>();

        for (ChatSession session : all) {
            Long aid1 = session.getAid1();
            Long aid2 = session.getAid2();

            aidSet.add(aid1);
            aidSet.add(aid2);

            if (session.getLastChatId() != null) {
                chatIdSet.add(session.getLastChatId());
            }
        }

        // 查 用户 数据
        List<Account> accountAllById = accountRepository.findAllById(aidSet);
        // 查 聊天数据
        List<Chat> chatAllById1 = chatRepository.findAllById(chatIdSet);

        for (ChatSession session : all) {
            ChatSessionDTO dto = new ChatSessionDTO();
            BeanUtils.copyProperties(session,dto);

            Long aid = Logic.removeDuplicate(meId, session.getAid1(), session.getAid2());

            for (Account account : accountAllById) {
                if (account.getId().equals(aid)) {
                    dto.setAccount(account);
                    break;
                }
            }

            if (session.getLastChatId() != null) {
                for (Chat chat : chatAllById1) {
                    if (chat.getId().equals(session.getLastChatId())) {
                        dto.setLastChat(chat);
                    }
                }
            }

            resList.add(dto);
        }

        return resList;
    }

    /**
     * 发送信息（伪）
     * @param session_id 会话id
     * @param fromaid 发送人
     * @param toaid 接收人
     * @param content 发送内容
     * @return 返回创建的对象
     */
    public Chat sendChat(Long session_id, Long fromaid, Long toaid, String content) {

        // 1. 校验是否存在 会话
        Optional<ChatSession> byId = chatSessionRepository.findById(session_id);
        if (byId.isEmpty()){
            throw new RuntimeException("找不到会话");
        }

        Long aid1 = byId.get().getAid1();
        Long aid2 = byId.get().getAid2();

        // 2. 判断  我 id  和 对方id 是否匹配
        if (!Logic.isEquality(aid1,aid2,fromaid,toaid)) {
            throw new RuntimeException("会话和 用户不匹配");
        }

        // 3. 创建会话对象
        Chat chat = new Chat();
        chat.setSessionid(session_id);
        chat.setFromaid(fromaid);
        chat.setToaid(toaid);
        chat.setContent(content);

        Chat save = chatRepository.save(chat);

        // 4. 更新会话状态 设置次 chat 为 session 的最后一个
        byId.get().setLastChatId(save.getId());
        chatSessionRepository.save(byId.get());

        return save;
    }

    /**
     * 获取 会话的聊天数据
     * @param meid       当前用户id
     * @param sessionid     会话 id
     * @param lastChatId   最后的 chat id （如果为0则加载所有数据）
     * @return
     */
    public List<ChatDTO> loadChatList(Long meid, Long sessionid, Long lastChatId) {
        // 1. 校验 session
        Optional<ChatSession> session = chatSessionRepository.findById(sessionid);
        if (session.isEmpty()){
            throw new RuntimeException("找不到会话");
        }

        if (!Logic.contains(meid,session.get().getAid1(),session.get().getAid2())) {
            throw new RuntimeException("不是当前用户的会话");
        }

        // 2. 查询 聊天数据
        // SessionidEquals   And   IdGreaterThan    OrderByIdAsc
        List<Chat> all = chatRepository.findAllBySessionidEqualsAndIdGreaterThanOrderByIdAsc(sessionid,lastChatId);

        List<ChatDTO> result = new ArrayList<>(all.size());


        // 3. 查询 用户数据 数据
        Stream<Long> fromIds = all.stream().map(v -> v.getFromaid());
        Stream<Long> toIds = all.stream().map(v -> v.getToaid());

        List<Long> fromIdsCollect = fromIds.collect(Collectors.toList());
        List<Long> toIdsCollect = toIds.collect(Collectors.toList());

        fromIdsCollect.addAll(toIdsCollect);
        // 3.1 此处查询
        List<Account> accountRepositoryAllById = accountRepository.findAllById(fromIdsCollect);

        // 4. 构建需要返回的数据集
        for (Chat m : all) {
            ChatDTO chatDTO = new ChatDTO();
            BeanUtils.copyProperties(m,chatDTO);

            chatDTO.setFrom(accountRepositoryAllById.stream().filter(v -> v.getId().equals(m.getFromaid())).findFirst().get());
            chatDTO.setTo(accountRepositoryAllById.stream().filter(v -> v.getId().equals(m.getToaid())).findFirst().get());

            result.add(chatDTO);
        }

        return result;
    }
}
