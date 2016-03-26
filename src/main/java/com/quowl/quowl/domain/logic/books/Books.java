package com.quowl.quowl.domain.logic.books;

import com.quowl.quowl.domain.base.AuditingEntity;
import com.quowl.quowl.domain.logic.user.User;

import javax.persistence.*;

@Entity
@Table(name = "books", indexes = {
        @Index(name = "IDX_BOOK_AUTHOR_USER", columnList = "user_id,author,book"),
        @Index(name = "IDX_USER", columnList = "user_id") })
public class Books extends AuditingEntity {

    @Column
    private String author;

    @Column
    private String book;

    @Column(nullable = false)
    private boolean readed = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }
}
