package com.quowl.quowl.repository.user;

import com.quowl.quowl.domain.logic.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByNickname(String nickname);

    @Query(value = "select u.* from user u right join quote q on q.user_id = u.id group by u.nickname order by count(*) desc limit ?1, ?2", nativeQuery = true)
    List<User> findRecommendedUsers(Long page, Long qty);

    @Query("select u.id from User u where u.nickname=?1")
    Long findIdByNickName(String nickName);

    @Query("select u.id from User u where u.email=?1")
    Long findIdByEmail(String email);

    @Query("select u from User u where u.id in ?1")
    List<User> findAllById(List<Long> ids);

    @Query("select u.id, u.nickname from User u where u.nickname like concat('%', ?1, '%' )")
    List<User> search(String nickname);

}
