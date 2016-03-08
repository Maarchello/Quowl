package com.quowl.quowl.web.controllers.quote;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.books.BookRepository;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.JsonResultBean;
import com.quowl.quowl.web.beans.user.QuoteBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
public class QuoteController {
    @Inject private QuoteRepository quoteRepository;
    @Inject private UserRepository userRepository;
    @Inject private BookRepository booksRepository;

    @RequestMapping(value = "/addQuote", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean addNewQuote(@RequestParam(value = "quote", required = true) String quote) {
        String nickname = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);
        if (user.getBookName() == null || user.getAuthorName() == null) {
            return JsonResultBean.failure("Автор или название книги не установленны");
        }
        Books book = booksRepository.findOneByBookAndAuthor(user.getBookName(), user.getAuthorName());

        if (book == null) {
            book = new Books();
            book.setAuthor(user.getAuthorName());
            book.setBook(user.getBookName());
            book.setUser(user);
            book = booksRepository.save(book);
        }

        Quote quo = new Quote();
        quo.setBookId(book.getId());
        quo.setUser(user);
        quo.setBook(user.getBookName());
        quo.setAuthor(user.getAuthorName());
        quo.setText(quote);
        quo = quoteRepository.save(quo);
        QuoteBean quoteBean = new QuoteBean();
        quoteBean.copyDataFromDomain(quo);

        return JsonResultBean.success(quoteBean);
    }

    @RequestMapping(value = "/editQuote/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResultBean editQuote(@PathVariable(value = "id") Long id, @RequestParam(value = "text") String text) {
        Quote quote = quoteRepository.findOne(id);
        quote.setText(text);
        quoteRepository.save(quote);

        return JsonResultBean.success();
    }

    @RequestMapping(value = "/deleteQuote/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResultBean deleteQuote(@PathVariable(value = "id") Long id) {
        quoteRepository.delete(id);
        return JsonResultBean.success();
    }

}
