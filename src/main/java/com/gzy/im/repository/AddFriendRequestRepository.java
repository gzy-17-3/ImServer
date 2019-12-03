package com.gzy.im.repository;

import com.gzy.im.model.AddFriendRequest;
import com.gzy.im.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
//https://www.jianshu.com/p/fd5ea29ec119
//https://docs.spring.io/spring-data/jpa/docs/1.10.2.RELEASE/reference/html/#jpa.query-methods.at-query
public interface AddFriendRequestRepository extends JpaRepository<AddFriendRequest,Long> {
    List<AddFriendRequest> findAddFriendRequestsByToaidEquals(Long toaid);

//    @Query(value ="select * from #{#entityName} as t where t.toa")
    @Query(value ="select count(*) from #{#entityName} as t where t.toaid = ?1 and (t.operation = 0 or t.operation is null)")
    Long countAddFriendRequestByToaidEqualsAndOperation(Long toaid);

}
