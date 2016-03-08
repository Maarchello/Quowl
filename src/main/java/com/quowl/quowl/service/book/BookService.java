package com.quowl.quowl.service.book;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.books.BookRepository;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.web.beans.book.BookBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Inject private BookRepository bookRepository;
    @Inject private QuoteService quoteService;

    public void delete(Books book) {
        bookRepository.delete(book);
    }

    public Books save(Books book) {
        return bookRepository.save(book);
    }

    public Books findOne(Long id) {
        return bookRepository.findOne(id);
    }

    public List<Books> findAll() {
        return bookRepository.findAll();
    }

    public Books findOneByBookAndAuthor(String bookName, String authorName) {
        return bookRepository.findOneByBookAndAuthor(bookName, authorName);
    }

    public List<Books> findAllByUser(User user) {
        return bookRepository.findAllByUser(user);
    }

    public List<BookBean> converList(List<Books> books) {
        List<BookBean> beans = new ArrayList<>();
        for (Books book : books) {
            BookBean bookBean = new BookBean();
            bookBean.setId(book.getId());
            bookBean.setAuthor(book.getAuthor());
            bookBean.setName(book.getBook());
            bookBean.setCountQuotes(quoteService.countAllQuotesByBook(book.getId()));
            beans.add(bookBean);
        }

        return beans;
    }

}
