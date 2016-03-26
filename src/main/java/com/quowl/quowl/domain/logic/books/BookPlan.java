package com.quowl.quowl.domain.logic.books;

import com.quowl.quowl.domain.base.AuditingEntity;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "UQ_IDX_USER_BOOK_AUTHOR", columnNames = {"userId", "book", "author"})},
        indexes = @Index(name = "IDX_USER", columnList = "userId"))
public class BookPlan extends AuditingEntity {

    @Column
    private String author;

    @Column
    private String book;

    @Column
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
