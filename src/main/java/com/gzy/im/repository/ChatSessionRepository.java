package com.gzy.im.repository;

import com.gzy.im.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatSessionRepository extends JpaRepository<ChatSession,Long> {

    List<ChatSession> findAllByAid1EqualsOrAid2EqualsOrderByLastModifiedDateDesc(Long id1,Long id2);

    ChatSession findByAid1EqualsAndAid2Equals(Long id1,Long id2);
}
