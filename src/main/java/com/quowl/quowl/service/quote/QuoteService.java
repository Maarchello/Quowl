package com.quowl.quowl.service.quote;

import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.quote.QuoteRepository;
import com.quowl.quowl.service.comment.CommentService;
import com.quowl.quowl.service.storage.StorageService;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.web.beans.system.IService;
import com.quowl.quowl.web.beans.user.QuoteBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteService implements IService<Quote, Long> {
    @Inject private UserService userService;
    @Inject private QuoteRepository quoteRepository;
    @Inject private StorageService storageService;
    @Inject private CommentService commentService;


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
            bean.setUsers(userService.convertUsersToUserBean(quote.getLikes()));
            bean.setUserAvatar(storageService.getAvatarUrl(quote.getUser()));

            bean.setComments(commentService.getAllByQuoteId(quote.getId()));

            quoteBeen.add(bean);
        }

        return quoteBeen;
    }

    public QuoteBean convertOne(Quote quote) {
        QuoteBean quoteBean = new QuoteBean();
        quoteBean.setId(quote.getId());
        quoteBean.setAuthor(quote.getAuthor());
        quoteBean.setBook(quote.getBook());
        quoteBean.setText(quote.getText());
        quoteBean.setDate(quote.getCreatedDate());
        quoteBean.setUserId(quote.getUser().getId());
        quoteBean.setUserNickname(quote.getUser().getNickname());
        quoteBean.setUserAvatar(storageService.getAvatarUrl(quote.getUser()));

        return quoteBean;
    }

    @Override
    public void delete(Quote quote) {
        quoteRepository.delete(quote);
    }

    @Override
    public void delete(Long aLong) {
        quoteRepository.delete(aLong);
    }

    @Override
    public Quote save(Quote quote) {
        return quoteRepository.save(quote);
    }

    @Override
    public Quote findOne(Long id) {
        return quoteRepository.findOne(id);
    }

    @Override
    public boolean exists(Long aLong) {
        return quoteRepository.exists(aLong);
    }

    @Override
    public List<Quote> findAll() {
        return quoteRepository.findAll();
    }


}
