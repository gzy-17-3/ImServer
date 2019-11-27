package com.gzy.im.service;

import com.gzy.im.model.Account;
import com.gzy.im.model.FriendRelational;
import com.gzy.im.repository.AccountRepository;
import com.gzy.im.repository.FriendRelationalRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    @Resource
    FriendRelationalRepository friendRelationalRepository;
    @Resource
    AccountService accountService;
    @Resource
    AccountRepository accountRepository;

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

    public Account findFriend(Long uid) {
        Account account = accountService.getAccount(uid);
        return account;
    }

    public List<Account> findAll(Pageable pageable, Long aid) {
        List<FriendRelational> relationals = friendRelationalRepository.findFriendRelationalsByAidEquals(pageable,aid);

        List<Long> toaidList = new ArrayList<>();

        for (FriendRelational relational : relationals) {
            Long aidto = relational.getAidto();
            toaidList.add(aidto);
        }

        List<Account> res = accountRepository.findAllById(toaidList);

        return res;
    }
}
