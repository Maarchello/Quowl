package com.quowl.quowl.web.controllers.account;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.books.BooksRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.JsonResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
public class StatusController {

    @Inject private UserRepository userRepository;
    @Inject private BooksRepository booksRepository;

    @RequestMapping(value = "/saveStatus", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean saveStatus(@RequestParam("bookName") String bookName, @RequestParam("authorName") String authorName) {
        if (StringUtils.isBlank(bookName) || bookName.equalsIgnoreCase("Название книги")) {
            bookName = null;
        }
        if (StringUtils.isBlank(authorName) || authorName.equalsIgnoreCase("Автор")) {
            authorName = null;
        }

        String nickname = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);
        user.setBookName(bookName);
        user.setAuthorName(authorName);
        user = userRepository.save(user);

        if (bookName != null && authorName != null) {
            Books books = booksRepository.findOneByBookAndAuthor(bookName, authorName);
            if (books == null) {
                books = new Books();
                books.setBook(bookName);
                books.setAuthor(authorName);
                books.setUser(user);
                booksRepository.save(books);
            }

        }

        return JsonResultBean.success();
    }

}
