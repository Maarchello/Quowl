package com.quowl.quowl.web.controllers.quote;

import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.service.notification.NotificationService;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
public class LikeController {
    @Inject private UserRepository userRepository;
    @Inject private QuoteRepository quoteRepository;
    @Inject private NotificationService notificationService;

    private final static String LIKE_NOTIFICATION = "Вашу запись оценил(а)";

    @RequestMapping(value = "like/{quote_id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean likeQuote(@PathVariable(value = "quote_id") Long quote_id) {
        String result;
        String nickname  = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);
        Quote quote = quoteRepository.findOne(quote_id);

        List<Quote> likes = user.getLikes();
        if (likes.contains(quote)) {
            user.getLikes().remove(quote);
            result = "decrement";
        } else {
            user.getLikes().add(quote);
            result = "increment";
            notificationService.createNotify(nickname, quote.getUser(), LIKE_NOTIFICATION);
        }
        userRepository.save(user);
        return JsonResultBean.success(result);
    }

}
