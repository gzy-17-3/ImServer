package com.gzy.im.repository;

import com.gzy.im.model.Account;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findAccountByPhone(String phone);

    List<Account> findAccountsByNameLike(String name);
}
