package com.quowl.quowl.service.book;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.repository.books.BookRepository;
import com.quowl.quowl.service.quote.QuoteService;
import com.quowl.quowl.web.beans.book.BookBean;
import com.quowl.quowl.web.beans.book.TopBooksBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Inject
    private BookRepository bookRepository;
    @Inject
    private QuoteService quoteService;

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

    public void bookFinish(Books book, User user) {
        book.setReaded(true);
        user.setBookName(null);
        user.setAuthorName(null);
    }

    public Books findOneByBookAndAuthorAndUserId(String bookName, String authorName, Long userId) {
        return bookRepository.findOneByBookAndAuthorAndUserId(bookName, authorName, userId);
    }

    public String findAuthorByUserIdAndBook(Long userId, String book) {
        return bookRepository.findAuthorByUserIdAndBook(userId, book);
    }

    public List<Books> findAllByUser(User user) {
        return bookRepository.findAllByUser(user);
    }

    public List<BookBean> convertList(List<Books> books) {
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
    /**
     * Get top books names from DB.
     *
     * @param page the limit for select query.
     * @return top {page} books names from DB.
     */
    public List<TopBooksBean> getTopBooks() {
        List<Object[]> topObjectBooks = findTheMostReadBooks();

        return convertToTopBean(topObjectBooks);
    }

    /**
     * Get top authors names from DB.
     *
     * @param page the limit for select query.
     * @return top {page} authors names.
     */
    public List<TopBooksBean> getTopAuthors() {
        List<Object[]> topObjectBooks = findTheMostReadAuthors();

        return convertToTopBean(topObjectBooks);
    }

    /**
     * Convert from authors/books to TopBooksBean
     * @param topObjectBooks the list of authors/books
     * @return bean
     * PS. my first javadoc comment. I did try.
     */

    private List<TopBooksBean> convertToTopBean(List<Object[]> topObjectBooks) {
        List<TopBooksBean> topBooksBeanList = new ArrayList<>();
        for (Object[] objects : topObjectBooks) {
            TopBooksBean bean = new TopBooksBean();
            bean.setTitle((String) objects[0]);
            bean.setCount((BigInteger) objects[1]);
            topBooksBeanList.add(bean);
        }
        return topBooksBeanList;

    }

    // The one page limit.
    private static final Long PAGE_LIMIT = 5L;

    /**
     * Selects the most read books from books DB.
     *
     * @param page the limit for select query.
     * @return list of <code>Object[]</code> arrays.
     * Object[] contains a book and its count rows in DB.
     */
    public List<Object[]> findTheMostReadBooks() {
        return bookRepository.findTheMostReadBooks(PAGE_LIMIT);
    }

    /**
     * Selects the most read authors from books DB.
     *
     * @param page the limit for select query.
     * @return list of <code>Object[]</code> arrays.
     * Object[] contains an author and its count rows in DB.
     */
    public List<Object[]> findTheMostReadAuthors() {
        return bookRepository.findTheMostReadAuthors(PAGE_LIMIT);
    }

}
