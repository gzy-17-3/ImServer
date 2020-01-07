package com.gzy.im.repository;

import com.gzy.im.model.Account;
import com.gzy.im.model.Chat;
import com.gzy.im.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
//    List<Chat> findAllBySessionidEqualsOrderByIdDesc(Long sessionid);

    /**
     * 获取聊天数据
     * @param sessionid 那个会话的聊天数据
     * @param lastId  从那个开始获取 不包含本条数据
     * @return
     */
    List<Chat> findAllBySessionidEqualsAndIdGreaterThanOrderByIdAsc(Long sessionid,Long lastId);
}
