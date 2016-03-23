package com.quowl.quowl.web.beans.book;


/**
 * The class is provided to get top read books
 * and authors from DB.
 *
 * @author nllsdfx
 */
public class TopBooksBean {

    private String title;
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
