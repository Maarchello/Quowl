package com.quowl.quowl.service.user;

import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.books.BookRepository;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.service.signinup.SecurityService;
import com.quowl.quowl.service.system.TokenProvider;
import com.quowl.quowl.utils.CookieUtils;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.system.IService;
import com.quowl.quowl.web.beans.user.CurrentUserBean;
import com.quowl.quowl.web.beans.user.ProfileBean;
import com.quowl.quowl.web.beans.user.UserBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserService implements IService<User, Long> {
    @Inject private BookRepository booksRepository;
    @Inject private QuoteRepository quoteRepository;
    @Inject private UserRepository userRepository;
    @Inject private SubscribeService subscribeService;
    @Inject private PasswordEncoder passwordEncoder;
    @Inject @Qualifier("customUserDetails") private UserDetailsService userDetailsService;
    @Inject private SecurityService securityService;
    @Inject private TokenProvider tokenProvider;
    @Inject private StorageService storageService;


    public boolean existsEmail(String email){
        Long id = userRepository.findIdByEmail(email);

        return id == null;
    }

    public boolean existsUserName(String userName){
        Long id = userRepository.findIdByNickName(userName);
        return id == null;
    }

    public User getByNickname(String nickname) {
        return userRepository.findOneByNickname(nickname);
    }

    public void changePassword(String current, HttpServletResponse response, HttpServletRequest request) {
        User user = userRepository.findOneByNickname(SecurityUtils.getCurrentLogin());
        String encryptedPassword = passwordEncoder.encode(current);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        refreshCookieForPassword(response, request);
    }

    private void refreshCookieForPassword(HttpServletResponse response, HttpServletRequest request) {
        User user = userRepository.findOneByNickname(SecurityUtils.getCurrentLogin());
        UserDetails details = userDetailsService.loadUserByUsername(user.getEmail());

        securityService.refreshAuthentication(user.getUsername(), user.getPassword());

        String token = tokenProvider.createToken(details);
        CookieUtils.setAuthCookie(response, token);

    }

    public UserBean convertUserToUserBean(User user) {
        UserBean userBean = new UserBean();
        userBean.setNickname(user.getNickname());
        userBean.setId(user.getId());
        userBean.setAuthorName(user.getAuthorName());
        userBean.setBookName(user.getBookName());
        userBean.setCountReadBooks(booksRepository.countAllReadBooks(user.getId()));
        userBean.setCountQuotes(quoteRepository.countAllQuotes(user.getId()));
        userBean.setProfileBean(new ProfileBean(user.getProfileInfo()));
        userBean.setGender(user.getProfileInfo().getGender());
        userBean.setUserAvatar(storageService.getAvatarUrl(user));

        List<Long> followers = subscribeService.findAllFollowersIdByFollowing(user.getId());
        List<Long> following = subscribeService.findAllFollowingsIdByFUser(user.getId());
        userBean.setFollowing(following);
        userBean.setFollowers(followers);

        return userBean;
    }

    public List<User> search(String nickname) {
        return userRepository.search(nickname);
    }

    public List<UserBean> convertUsersToUserBean(List<User> users) {
        List<UserBean> beans = new LinkedList<>();

        for (User user : users) {
            UserBean bean = new UserBean();
            bean.setNickname(user.getNickname());
            bean.setId(user.getId());
            bean.setAuthorName(user.getAuthorName());
            bean.setBookName(user.getBookName());
            bean.setCountReadBooks(booksRepository.countAllReadBooks(user.getId()));
            bean.setCountQuotes(quoteRepository.countAllQuotes(user.getId()));
            bean.setProfileBean(new ProfileBean(user.getProfileInfo()));
            bean.setGender(user.getProfileInfo().getGender());
            bean.setUserAvatar(storageService.getAvatarUrl(user));

            List<Long> followers = subscribeService.findAllFollowersIdByFollowing(user.getId());
            List<Long> following = subscribeService.findAllFollowingsIdByFUser(user.getId());
            bean.setFollowing(following);
            bean.setFollowers(followers);
            beans.add(bean);
        }
        return beans;
    }

    public CurrentUserBean getCurrentUser() {
        String nickname = SecurityUtils.getCurrentLogin();

        CurrentUserBean cub = new CurrentUserBean();
        if (nickname.equals("anonymousUser")) return cub;

        User user = userRepository.findOneByNickname(nickname);
        cub.copyDataFromDomain(user);

        return cub;
    }

    public UserBean getUserBean() {
        String nickname = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);

        return convertUserToUserBean(user);
    }


    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    public void delete(Long aLong) {
        userRepository.delete(aLong);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(Long aLong) {
        return userRepository.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return userRepository.exists(aLong);
    }
}
