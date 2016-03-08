package com.quowl.quowl.web.beans.book;


public class BookBean {

    private Long id;
    private String author;
    private String name;
    private Long countQuotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountQuotes() {
        return countQuotes;
    }

    public void setCountQuotes(Long countQuotes) {
        this.countQuotes = countQuotes;
    }
}
