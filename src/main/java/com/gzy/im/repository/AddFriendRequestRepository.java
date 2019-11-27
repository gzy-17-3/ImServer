package com.gzy.im.repository;

import com.gzy.im.model.AddFriendRequest;
import com.gzy.im.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddFriendRequestRepository extends JpaRepository<AddFriendRequest,Long> {
    List<AddFriendRequest> findAddFriendRequestsByToaidEquals(Long toaid);
}
