package com.gzy.im.service;

import com.gzy.im.core.math.Logic;
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

    // 获取 消息（会话）列表
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


    public Chat sendChat(Long session_id, Long fromaid, Long toaid, String content) {

        Optional<ChatSession> byId = chatSessionRepository.findById(session_id);
        if (byId.isEmpty()){
            throw new RuntimeException("找不到会话");
        }

        Long aid1 = byId.get().getAid1();
        Long aid2 = byId.get().getAid2();

        if (!Logic.isEquality(aid1,aid2,fromaid,toaid)) {
            throw new RuntimeException("会话和 用户不匹配");
        }

        Chat chat = new Chat();
        chat.setSessionid(session_id);
        chat.setFromaid(fromaid);
        chat.setToaid(toaid);
        chat.setContent(content);

        return chatRepository.save(chat);
    }
}
