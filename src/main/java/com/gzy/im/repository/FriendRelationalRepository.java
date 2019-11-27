package com.gzy.im.repository;

import com.gzy.im.model.FriendRelational;
import com.gzy.im.model.Token;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRelationalRepository extends JpaRepository<FriendRelational,Long> {

    FriendRelational findFriendRelationalsByAidEqualsAndAidtoEquals(Long aid,Long toaid);


    List<FriendRelational> findFriendRelationalsByAidEquals(Pageable pageable, Long aid);
}
