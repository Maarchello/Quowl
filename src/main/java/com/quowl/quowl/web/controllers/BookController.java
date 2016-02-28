package com.quowl.quowl.web.controllers;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.books.BooksRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.JsonResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
public class BookController {

    @Inject private BooksRepository booksRepository;
    @Inject private UserRepository userRepository;

    @RequestMapping(value = "/bookComplete", method = RequestMethod.GET)
    @ResponseBody
    public JsonResultBean bookComplete() {
        String nickname = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);

        Books book = booksRepository.findOneByBookAndAuthor(user.getBookName(), user.getAuthorName());
        book.setReaded(true);
        try {
            booksRepository.save(book);
        } catch (Exception e) {
            return JsonResultBean.failure("Something gone wrong");
        }

        return JsonResultBean.success();
    }

}
