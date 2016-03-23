package com.quowl.quowl.service.user;


import com.quowl.quowl.domain.logic.user.Subscribe;
import com.quowl.quowl.repository.user.SubscribeRepository;
import com.quowl.quowl.web.beans.IService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SubscribeService implements IService<Subscribe, Long> {
    @Inject private SubscribeRepository subscribeRepository;

    public Subscribe findByFollowerAndFollowing(Long follower, Long following) {
        return subscribeRepository.findByFollowerAndFollowing(follower, following);
    }

    public List<Long> findAllFollowersIdByFollowing(Long following) {
        return subscribeRepository.findAllFollowersIdByFollowing(following);
    }

    public List<Long> findAllFollowingsIdByFUser(Long userId) {
        return subscribeRepository.findAllFollowingsIdByFUser(userId);
    }


    @Override
    public Subscribe save(Subscribe object) {
        return subscribeRepository.save(object);
    }

    @Override
    public void delete(Subscribe object) {
        subscribeRepository.delete(object);
    }

    @Override
    public void delete(Long aLong) {
        subscribeRepository.delete(aLong);
    }

    @Override
    public List<Subscribe> findAll() {
        return subscribeRepository.findAll();
    }

    @Override
    public Subscribe findOne(Long aLong) {
        return subscribeRepository.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return subscribeRepository.exists(aLong);
    }
}
