package com.quowl.quowl.service.user;

import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.books.BooksRepository;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.repository.user.SubscribeRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.service.signinup.SecurityService;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.user.CurrentUserBean;
import com.quowl.quowl.web.beans.user.ProfileBean;
import com.quowl.quowl.web.beans.user.UserBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {
    @Inject private BooksRepository booksRepository;
    @Inject private QuoteRepository quoteRepository;
    @Inject private UserRepository userRepository;
    @Inject private SubscribeRepository subscribeRepository;

    public UserBean convertUserToUserBean(User user) {
        UserBean userBean = new UserBean();
        userBean.setNickname(user.getNickname());
        userBean.setId(user.getId());
        userBean.setAuthorName(user.getAuthorName());
        userBean.setBookName(user.getBookName());
        userBean.setCountReadBooks(booksRepository.countAllReadBooks(user.getId()));
        userBean.setCountQuotes(quoteRepository.countAllQuotes(user.getId()));
        userBean.setProfileBean(new ProfileBean(user.getProfileInfo()));

        List<Long> followers = subscribeRepository.findAllFollowersIdByFollowing(user.getId());
        List<Long> following = subscribeRepository.findAllFollowingsIdByFUser(user.getId());
        userBean.setCountFollowers((long) followers.size());
        userBean.setFollowing(following);
        userBean.setFollowers(followers);

        return userBean;
    }

    public List<UserBean> converUsersToUserBean(List<User> users) {
        List<UserBean> beans = new LinkedList<>();

        for (User user : users) {
            UserBean bean = new UserBean();
            bean.setNickname(user.getNickname());
            bean.setId(user.getId());
            bean.setAuthorName(user.getAuthorName());
            bean.setBookName(user.getBookName());
            bean.setCountReadBooks(booksRepository.countAllReadBooks(user.getId()));
            bean.setCountQuotes(quoteRepository.countAllQuotes(user.getId()));
            beans.add(bean);
        }
        return beans;
    }

    public CurrentUserBean getCurrentUser() {
        String nickname = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);

        CurrentUserBean cub = new CurrentUserBean();
        if (nickname.equals("anonymousUser")) return cub;
        cub.copyDataFromDomain(user);

        return cub;
    }

    public UserBean getUserBean() {
        String nickname = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);

        return convertUserToUserBean(user);
    }


}
