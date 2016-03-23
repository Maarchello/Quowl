package com.quowl.quowl.web.beans.book;

import com.quowl.quowl.repository.books.BookRepository;
import com.quowl.quowl.service.book.BookService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * The class is provided to get top read books
 * and authors from DB.
 *
 * @author nllsdfx
 */
public class TopBooksBean {

    @Inject
    private BookService bookService;

    private String bookName;
    private long readBookCount;


    public TopBooksBean() {
    }

    /**
     * Get top books names from DB.
     *
     * @param page the limit for select query.
     * @return top {page} books names from DB.
     */
    public List<TopBooksBean> getTopBooks(int page) {

        List<Object[]> topObjectBooks = bookService.findTheMostReadBooks(page);
        List<TopBooksBean> topBooksBeanList = new ArrayList<>();

        for (Object[] objects : topObjectBooks) {
            TopBooksBean bean = new TopBooksBean();
            bean.bookName = (String) objects[0];
            bean.readBookCount = (long) objects[1];
            topBooksBeanList.add(bean);
        }

        return topBooksBeanList;
    }

    private String authorName;
    private long authorCount;

    /**
     * Get top authors names from DB.
     *
     * @param page the limit for select query.
     * @return top {page} authors names.
     */
    public List<TopBooksBean> getTopAuthors(int page) {

        List<Object[]> topObjectBooks = bookService.findTheMostReadAuthors(page);
        List<TopBooksBean> topBooksBeanList = new ArrayList<>();

        for (Object[] objects : topObjectBooks) {
            TopBooksBean bean = new TopBooksBean();
            bean.authorName = (String) objects[0];
            bean.authorCount = (long) objects[1];
            topBooksBeanList.add(bean);
        }

        return topBooksBeanList;
    }

}
