package com.gzy.im.service;

import com.gzy.im.core.exception.BadRequestException;
import com.gzy.im.core.exception.ForbiddenException;
import com.gzy.im.model.Account;
import com.gzy.im.model.AddFriendRequest;
import com.gzy.im.model.FriendRelational;
import com.gzy.im.repository.AccountRepository;
import com.gzy.im.repository.AddFriendRequestRepository;
import com.gzy.im.repository.FriendRelationalRepository;
import com.gzy.im.service.model.AddFriendRequestFullAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AddFriendRequestService {
    @Resource
    AccountService accountService;

    @Resource
    AccountRepository accountRepository;

    @Resource
    AddFriendRequestRepository addFriendRequestRepository;

    @Resource
    FriendRelationalRepository friendRelationalRepository;
    // 未处理的 列表
    public List<AddFriendRequestFullAccount> getUndisposedList(Long uid) {
        List<AddFriendRequest> list = addFriendRequestRepository.findAddFriendRequestsByToaidEquals(uid);
        List<AddFriendRequestFullAccount> listRes = new ArrayList<>(list.size());
        List<Long> listAid = new ArrayList<>(list.size());

        for (AddFriendRequest  request: list) {
            AddFriendRequestFullAccount fullAccount = new AddFriendRequestFullAccount();
            BeanUtils.copyProperties(request,fullAccount);
            listRes.add(fullAccount);
            listAid.add(request.getAid());
        }

        // 填充 查询  account
        List<Account> allById = accountRepository.findAllById(listAid);
        LinkedHashMap<Long,Account> linkedHashMap = new LinkedHashMap<>();
        for (Account account : allById) {
            linkedHashMap.put(account.getId(),account);
        }

        for (AddFriendRequestFullAccount re : listRes) {
            re.setAccount(linkedHashMap.get(re.getAid()));
        }

        return listRes;
    }

    /**
     * 添加为好友的申请
     * @param id
     * @param toUid
     * @param verifiInfo
     */
    public void applyAdd(Long id, Long toUid, String verifiInfo) {

        if (Objects.equals(id, toUid)){
            throw new BadRequestException();
        }

        if (!accountRepository.existsById(toUid)) {
            throw new BadRequestException();
        }

        AddFriendRequest request = new AddFriendRequest();

        request.setAid(id);
        request.setToaid(toUid);
        request.setVerifiInfo(verifiInfo);

        addFriendRequestRepository.save(request);
    }

    /**
     * 应答操作
     * @param id 当前用户 id
     * @param requestid 申请添加为好友记录 id
     * @param opt 当前用户执行的操作
     */
    public void reply(Long id, Long requestid, Long opt) {
        Optional<AddFriendRequest> addFriendRequestOptional = addFriendRequestRepository.findById(requestid);

        // 是否存在该记录
        if (addFriendRequestOptional.isEmpty()){
            throw new BadRequestException();
        }

        AddFriendRequest request = addFriendRequestOptional.get();

        // 判断是否为 被申请人
        if (!request.getToaid().equals(id)) {
            throw new ForbiddenException();
        }

        if (request.getOperation() != null && request.getOperation() != 0) {
            throw new ForbiddenException();
        }

        //    未操作
        //		0
        //    接受
        //		1
        //    忽略
        //		2
        //    拒绝
        //		3

        switch (opt.intValue()) {
            case 0:
                throw new BadRequestException();
            case 1:
                // 建立朋友关系
                makeFriendRelationship(request);
                request.setOperation(opt.intValue());
                break;
            case 2:
            case 3:
                request.setOperation(opt.intValue());
                break;
            default:
                throw new BadRequestException();
        }

        addFriendRequestRepository.save(request);
    }

    private void makeFriendRelationship(AddFriendRequest request) {
        FriendRelational fromRelational = new FriendRelational();
        FriendRelational toRelational = new FriendRelational();

        fromRelational.setAid(request.getAid());
        fromRelational.setAidto(request.getToaid());

        toRelational.setAid(request.getToaid());
        toRelational.setAidto(request.getAid());

        friendRelationalRepository.save(fromRelational);
        friendRelationalRepository.save(toRelational);
    }
}
