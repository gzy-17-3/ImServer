package com.gzy.im.service;

import com.gzy.im.core.exception.BadRequestException;
import com.gzy.im.model.AddFriendRequest;
import com.gzy.im.repository.AccountRepository;
import com.gzy.im.repository.AddFriendRequestRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class AddFriendRequestService {
    @Resource
    AccountService accountService;

    @Resource
    AccountRepository accountRepository;

    @Resource
    AddFriendRequestRepository addFriendRequestRepository;

    public List<AddFriendRequest> getUndisposedList(Long uid) {

        return null;
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

    }
}
