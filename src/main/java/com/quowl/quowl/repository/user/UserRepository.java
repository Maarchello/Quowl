package com.quowl.quowl.repository.user;

import com.quowl.quowl.domain.logic.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByNickname(String nickname);

    @Query("select u from User u where u.id in ?1")
    List<User> findAllById(List<Long> ids);

    @Query("select u.id, u.nickname from User u where u.nickname like concat('%', ?1, '%' )")
    List<User> search(String nickname);

}
