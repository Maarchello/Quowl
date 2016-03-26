package com.quowl.quowl.web.beans.book;


import java.math.BigInteger;

/**
 * The class is provided to get top read books
 * and authors from DB.
 *
 * @author nllsdfx
 */
public class TopBooksBean {

    private String title;
    private BigInteger count;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }
}
