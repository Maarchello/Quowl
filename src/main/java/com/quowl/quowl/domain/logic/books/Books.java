package com.quowl.quowl.domain.logic.books;

import com.quowl.quowl.domain.base.AuditingEntity;
import com.quowl.quowl.domain.logic.user.User;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Books extends AuditingEntity {

    @Column
    private String author;

    @Column
    private String book;

    @ManyToOne(fetch = FetchType.LAZY)
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
}
