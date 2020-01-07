package com.gzy.im.repository;

import com.gzy.im.model.Account;
import com.gzy.im.model.Chat;
import com.gzy.im.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
//    List<Chat> findAllBySessionidEqualsOrderByIdDesc(Long sessionid);

    List<Chat> findAllBySessionidEqualsAndIdGreaterThanOrderByIdAsc(Long sessionid,Long lastId);
}
