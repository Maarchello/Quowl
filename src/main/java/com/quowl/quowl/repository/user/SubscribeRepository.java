package com.quowl.quowl.repository.user;

import com.quowl.quowl.domain.logic.user.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe findByFollowerAndFollowing(Long follower, Long following);

    @Query("select count(s) from Subscribe s where s.following = ?1")
    Long countFollowers(Long following);

    @Query("select s.follower from Subscribe s where s.following = ?1")
    List<Long> findAllFollowersIdByFollowing(Long following);

}
