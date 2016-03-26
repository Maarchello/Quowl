package com.quowl.quowl.domain.logic.quote;

import com.quowl.quowl.domain.base.AuditingEntity;
import com.quowl.quowl.domain.logic.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quote", indexes = {
        @Index(name = "IDX_BOOKID", columnList = "bookId"),
        @Index(name = "IDX_USER", columnList = "user_id")})
public class Quote extends AuditingEntity {

    @Column
    private Long bookId;

    @Column(name = "text")
    private String text;

    @Column
    private String author;

    @Column
    private String book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "likes")
    public List<User> likes;

    public Quote() {}


    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (id != null ? !id.equals(quote.id) : quote.id != null) return false;
        if (text != null ? !text.equals(quote.text) : quote.text != null) return false;
        if (author != null ? !author.equals(quote.author) : quote.author != null) return false;
        if (book != null ? !book.equals(quote.book) : quote.book != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
