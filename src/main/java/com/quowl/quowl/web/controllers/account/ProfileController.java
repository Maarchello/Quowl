package com.quowl.quowl.web.controllers.account;

import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.web.beans.user.CurrentUserBean;
import com.quowl.quowl.web.beans.user.QuoteBean;
import com.quowl.quowl.web.beans.user.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
public class ProfileController {
    @Inject private UserRepository userRepository;
    @Inject private UserService userService;
    @Inject private QuoteRepository quoteRepository;
    @Inject private QuoteService quoteService;


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

}
