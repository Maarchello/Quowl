package com.quowl.quowl.web.controllers;

import com.quowl.quowl.domain.logic.books.BookPlan;
import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.service.book.BookPlanService;
import com.quowl.quowl.service.book.BookService;
import com.quowl.quowl.service.notification.NotificationService;
import com.quowl.quowl.service.user.SubscribeService;
import com.quowl.quowl.utils.ExecutionStatus;
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
public class BookController {

    @Inject
    private UserRepository userRepository;
    @Inject
    private BookService bookService;
    @Inject
    private BookPlanService planService;
    @Inject
    private NotificationService notificationService;
    @Inject
    private SubscribeService subscribeService;

    private final static String BOOK_FINISH_NOTIFICATION = "прочитал(а) книгу";


    @RequestMapping(value = "/bookComplete", method = RequestMethod.GET)
    @ResponseBody
    public JsonResultBean bookComplete() {
        String nickname = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);

        if (user.getAuthorName() == null) {
            return JsonResultBean.failure(ExecutionStatus.S130.toString());
        } else if (user.getBookName() == null) {
            return JsonResultBean.failure(ExecutionStatus.S131.toString());
        }

        Books book = bookService.findOneByBookAndAuthorAndUserId(user.getBookName(), user.getAuthorName(), user.getId());
        if (book != null) {
            if (!book.isReaded()) {
                bookService.bookFinish(book, user);
            } else {
                return JsonResultBean.failure(ExecutionStatus.S100.toString());
            }
        }

        try {
            bookService.save(book);
            user = userRepository.save(user);
        } catch (Exception e) {
            return JsonResultBean.failure(ExecutionStatus.S000.toString());
        }

        List<Long> followers = subscribeService.findAllFollowersIdByFollowing(user.getId());
        notificationService.createNotify(user.getNickname(), followers, BOOK_FINISH_NOTIFICATION);

        return JsonResultBean.success();
    }

    @RequestMapping(value = "/addBook/{book}/{userId}/{currentId}")
    @ResponseBody
    public JsonResultBean addBook(@PathVariable("book") String book, @PathVariable("userId") Long userId, @PathVariable("currentId") Long currentUserId) {
        String author = bookService.findAuthorByUserIdAndBook(userId, book);
        BookPlan bookPlan = new BookPlan();
        bookPlan.setBook(book);
        bookPlan.setAuthor(author);
        bookPlan.setUserId(currentUserId);

        boolean success = planService.tryAdd(bookPlan);

        if (!success) {
            return JsonResultBean.failure(ExecutionStatus.S110.toString());
        }

        return JsonResultBean.success(ExecutionStatus.OK.toString());
    }

    @RequestMapping(value = "/getBookPlan/{user_id}")
    @ResponseBody
    public JsonResultBean getBookPlan(@PathVariable("user_id") Long userId) {
        List<BookPlan> planList = planService.getByUserId(userId);

        return JsonResultBean.success(planList);
    }

}
