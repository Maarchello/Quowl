package com.quowl.quowl.web.controllers.quote;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.books.BookRepository;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.repository.user.UserRepository;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.utils.ExecutionStatus;
import com.quowl.quowl.utils.SecurityUtils;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import com.quowl.quowl.web.beans.user.QuoteBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class QuoteController {
    @Inject private UserRepository userRepository;
    @Inject private BookRepository booksRepository;
    @Inject private QuoteService quoteService;

    @RequestMapping(value = "/quote/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResultBean addNewQuote(@RequestParam(value = "quote", required = true) String quote) {
        String nickname = SecurityUtils.getCurrentLogin();
        User user = userRepository.findOneByNickname(nickname);
        if (user.getBookName() == null || user.getAuthorName() == null) {
            return JsonResultBean.failure("Автор или название книги не установленны");
        }
        Books book = booksRepository.findOneByBookAndAuthorAndUserId(user.getBookName(), user.getAuthorName(), user.getId());

        Quote quo = new Quote();
        quo.setBookId(book.getId());
        quo.setUser(user);
        quo.setBook(user.getBookName());
        quo.setAuthor(user.getAuthorName());
        quo.setText(quote);
        try {
            quo = quoteService.save(quo);
        } catch (DataIntegrityViolationException e) {
            return JsonResultBean.failure(ExecutionStatus.S210.toString());
        }
        QuoteBean quoteBean = quoteService.convertOne(quo);

        return JsonResultBean.success(quoteBean);
    }

    @RequestMapping(value = "/quote/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResultBean editQuote(@PathVariable(value = "id") Long id, @RequestParam(value = "text") String text) {
        Quote quote = quoteService.findOne(id);
        quote.setText(text);
        quoteService.save(quote);

        return JsonResultBean.success();
    }

    @RequestMapping(value = "/quote/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResultBean deleteQuote(@PathVariable(value = "id") Long id) {
        quoteService.delete(id);
        return JsonResultBean.success();
    }

}
