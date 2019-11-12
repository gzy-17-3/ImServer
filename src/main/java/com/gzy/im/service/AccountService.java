package com.gzy.im.service;

import com.gzy.im.model.Account;
import com.gzy.im.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AccountService {

    @Resource
    AccountRepository accountRepository;

    public Account getAccount(Long uid) {
        Optional<Account> byId = accountRepository.findById(uid);
        // 如果为空  应该直接返回 404

        return byId.get();
    }
}
