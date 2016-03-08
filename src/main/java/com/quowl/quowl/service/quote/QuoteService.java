package com.quowl.quowl.service.quote;

import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.web.beans.user.QuoteBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteService {
    @Inject private UserService userService;
    @Inject private QuoteRepository quoteRepository;

    public void delete(Quote quote) {
        quoteRepository.delete(quote);
    }

    public Quote save(Quote quote) {
        return quoteRepository.save(quote);
    }

    public Quote findOne(Long id) {
        return quoteRepository.findOne(id);
    }

    public List<Quote> findAll() {
        return quoteRepository.findAll();
    }

    public Long countAllQuotesByBook(Long bookId) {
        return quoteRepository.countAllQuotesByBook(bookId);
    }

    public List<QuoteBean> convertQuotesToQuoteBean(List<Quote> quotes) {
        List<QuoteBean> quoteBeen = new ArrayList<>();

        for (Quote quote : quotes) {
            QuoteBean bean = new QuoteBean();
            bean.setId(quote.getId());
            bean.setText(quote.getText());
            bean.setDate(quote.getCreatedDate());
            bean.setAuthor(quote.getAuthor());
            bean.setBook(quote.getBook());
            bean.setUserId(quote.getUser().getId());
            bean.setUserNickname(quote.getUser().getNickname());
            bean.setUsers(userService.converUsersToUserBean(quote.getLikes()));
            quoteBeen.add(bean);
        }

        return quoteBeen;
    }

}
