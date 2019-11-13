package com.gzy.im.service;

import com.gzy.im.core.AppBeanUtils;
import com.gzy.im.core.exception.NotFoundException;
import com.gzy.im.model.Account;
import com.gzy.im.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AccountService {

    @Resource
    AccountRepository accountRepository;
    public Account getAccount(Long uid) {
        Optional<Account> byId = accountRepository.findById(uid);
        // 如果为空  应该直接返回 404
        if (byId.isEmpty()){
            throw new NotFoundException();
        }

        return byId.get();
    }

    public Account updateAccount(Long uid, Account accountPara) {
        Optional<Account> byId = accountRepository.findById(uid);

        if (byId.isEmpty()){
            throw new NotFoundException();
        }
        Account account1 = byId.get();

        accountPara.setPassword(null);

        AppBeanUtils.copyNotNullProperties(accountPara,account1);

        accountRepository.save(account1);

        return account1;
    }


}
