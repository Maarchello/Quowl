package com.quowl.quowl.repository.user;

import com.quowl.quowl.domain.logic.user.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe findByFollowerAndFollowing(Long follower, Long following);

    @Query(value = "select count(s.follower) from subs s where s.following = ?1", nativeQuery = true)
    Long countFollowers(Long userId);

    @Query(value = "select count(s.following) from subs s where s.follower = ?1", nativeQuery = true)
    Long countFollowings(Long userId);

    @Query("select s.follower from Subscribe s where s.following = ?1")
    List<Long> findAllFollowersIdByFollowing(Long following);

    @Query("select s.following from Subscribe s where s.follower = ?1")
    List<Long> findAllFollowingsIdByFUser(Long userId);

}
