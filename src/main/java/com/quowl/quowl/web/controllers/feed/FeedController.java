package com.quowl.quowl.web.controllers.feed;

import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.web.beans.user.CurrentUserBean;
import com.quowl.quowl.web.beans.user.QuoteBean;
import com.quowl.quowl.web.beans.user.UserBean;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
public class FeedController {
    @Inject private UserService userService;
    @Inject private QuoteService quoteService;
    @Inject private QuoteRepository quoteRepository;

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String openFeedPage(Model model, Device device) {
        UserBean userBean = userService.getUserBean();
        CurrentUserBean currentUser = userService.getCurrentUser();
        List<Quote> quo = quoteRepository.findAllByUserIdOrderByCreatedDateDesc(userBean.getId());
        List<QuoteBean> quotes = quoteService.convertQuotesToQuoteBean(quo);

        model.addAttribute("quotes", quotes);
        model.addAttribute("user", userBean);
        model.addAttribute("currentUser", currentUser);

        if (device.isMobile()) {
            return "mobile/feed/feed";
        }
        return "feed/feed";
    }

}
