package com.gzy.im.repository;

import com.gzy.im.model.Account;
import com.gzy.im.model.Chat;
import com.gzy.im.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {

}
