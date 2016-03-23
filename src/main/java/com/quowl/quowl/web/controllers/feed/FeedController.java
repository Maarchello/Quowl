package com.quowl.quowl.web.controllers.feed;

import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import com.quowl.quowl.web.beans.user.CurrentUserBean;
import com.quowl.quowl.web.beans.user.QuoteBean;
import com.quowl.quowl.web.beans.user.UserBean;
import com.quowl.quowl.web.controllers.base.BaseController;
import org.springframework.data.domain.PageRequest;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController extends BaseController {
    @Inject private UserService userService;
    @Inject private QuoteService quoteService;
    @Inject private QuoteRepository quoteRepository;

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String openFeedPage(Model model, Device device) {
        long startTime = System.currentTimeMillis();
        UserBean userBean = userService.getUserBean();
        CurrentUserBean currentUser = userService.getCurrentUser();

        List<Long> following = userBean.getFollowing();
        following.add(userBean.getId());

        List<Quote> quo2 = quoteRepository.findTenByFollowing(following, new PageRequest(0, 10));

        List<QuoteBean> quotes = quoteService.convertQuotesToQuoteBean(quo2);

        model.addAttribute("quotes", quotes);
        model.addAttribute("user", userBean);
        model.addAttribute("currentUser", currentUser);
        long finish = System.currentTimeMillis();
        System.out.println("Execute time: " + (finish - startTime) + "ms");
        if (device.isMobile()) {
            return "mobile/feed/feed";
        }
        return "feed/feed";
    }

    @RequestMapping(value = "/moreQuotes/{page}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResultBean getQuotePagination(Device device, @PathVariable Integer page) {
        UserBean userBean = userService.getUserBean();
        CurrentUserBean currentUser = userService.getCurrentUser();

        List<Long> following = userBean.getFollowing();
        following.add(userBean.getId());

        List<Quote> quotes = quoteRepository.findTenByFollowing(following, new PageRequest(page, 10));
        List<QuoteBean> quotes2 = quoteService.convertQuotesToQuoteBean(quotes);

        if (quotes2.size() == 0) {
            return JsonResultBean.failure(ExecutionStatus.S200.toString());
        }

        List<Object> result = new ArrayList<>();
        result.add(quotes2);
        result.add(currentUser);

        return JsonResultBean.success(result);
    }

}
