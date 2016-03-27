package com.quowl.quowl.web.controllers.comment;

import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.domain.system.Comment;
import com.quowl.quowl.domain.system.Notification;
import com.quowl.quowl.service.comment.CommentService;
import com.quowl.quowl.service.notification.NotificationService;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;


@Controller
public class CommentController {
    @Inject private CommentService commentService;
    @Inject private NotificationService notificationService;
    @Inject private QuoteService quoteService;

    private final static String COMMENT_NOTIFICATION = "Новый комментарий от ";

    @RequestMapping(value = "comment/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean addComment(@RequestParam("comment") String message, @RequestParam("quote_id") Long quoteId, @RequestParam("user_id") Long userId, @RequestParam("user_nickname") String nickname) {
        if (message.trim().isEmpty()) {
            return JsonResultBean.failure(ExecutionStatus.S300.toString());
        }

        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setQuoteId(quoteId);
        comment.setUserId(userId);
        comment.setNickname(nickname);

        Quote quote = quoteService.findOne(quoteId);
        Notification notification = new Notification();
        notification.setTo(quote.getUser());
        notification.setFromUser(nickname);
        notification.setMessage(COMMENT_NOTIFICATION);
        notification.setSeen(false);

        notificationService.save(notification);
        comment = commentService.save(comment);

        return JsonResultBean.success(comment);
    }

    @RequestMapping(value = "comment/delete/{comment_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResultBean deleteComment(@PathVariable("comment_id") Long commentId) {
        commentService.delete(commentId);

        return JsonResultBean.success();
    }
}
