package com.gzy.im.service;

import com.gzy.im.model.FriendRelational;
import com.gzy.im.repository.FriendRelationalRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FriendService {
    @Resource
    FriendRelationalRepository friendRelationalRepository;

    /**
     * 解除双方好友关系
     * @param curId
     * @param uid
     */

    public void deleteRelational(Long curId, Long uid) {
        FriendRelational r1 = friendRelationalRepository.findFriendRelationalsByAidEqualsAndAidtoEquals(curId, uid);
        FriendRelational r2 = friendRelationalRepository.findFriendRelationalsByAidEqualsAndAidtoEquals(uid, curId);
        friendRelationalRepository.delete(r1);
        friendRelationalRepository.delete(r2);
    }
}
