package com.quowl.quowl.web.controllers.account;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.service.account.ProfileService;
import com.quowl.quowl.service.book.BookService;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.web.beans.JsonResultBean;
import com.quowl.quowl.web.beans.book.BookBean;
import com.quowl.quowl.web.beans.user.CurrentUserBean;
import com.quowl.quowl.web.beans.user.QuoteBean;
import com.quowl.quowl.web.beans.user.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller
public class ProfileController {
    @Inject private UserRepository userRepository;
    @Inject private UserService userService;
    @Inject private QuoteRepository quoteRepository;
    @Inject private QuoteService quoteService;
    @Inject private ProfileService profileService;
    @Inject private BookService bookService;


    @RequestMapping(value = "/{nickname}", method = RequestMethod.GET)
    public String getProfile(@PathVariable("nickname") String nickname, Model model) {
        User user = userRepository.findOneByNickname(nickname);
        UserBean userBean = userService.convertUserToUserBean(user);
        CurrentUserBean currentUser = userService.getCurrentUser();
        List<Quote> quo = quoteRepository.findAllByUserIdOrderByCreatedDateDesc(userBean.getId());
        List<QuoteBean> quotes = quoteService.convertQuotesToQuoteBean(quo);

        model.addAttribute("quotes", quotes);
        model.addAttribute("user", userBean);
        model.addAttribute("currentUser", currentUser);
        return "account/profile";
    }

    @RequestMapping(value = "/followers", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean getFollowers(@RequestParam("followers") String followers) {
        List<Long> followersList = profileService.parseFromString(followers);
        List<User> follower = userRepository.findAllById(followersList);
        List<UserBean> beanFollowers = userService.converUsersToUserBean(follower);

        return JsonResultBean.success(beanFollowers);
    }

    @RequestMapping(value = "/followings", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean getFollowings(@RequestParam("following") String following) {
        List<Long> followingList = profileService.parseFromString(following);
        List<User> followings = userRepository.findAllById(followingList);
        List<UserBean> beanFollowings = userService.converUsersToUserBean(followings);

        return JsonResultBean.success(beanFollowings);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean getBooks(@RequestParam("userId") Long userId) {
        User user = userRepository.findOne(userId);
        List<Books> books = bookService.findAllByUser(user);
        List<BookBean> bookBeans = bookService.converList(books);
        return JsonResultBean.success(bookBeans);
    }
}
