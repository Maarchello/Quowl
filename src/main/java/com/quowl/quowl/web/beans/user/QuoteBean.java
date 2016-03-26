package com.quowl.quowl.web.beans.user;

import com.quowl.quowl.domain.logic.quote.Quote;
import com.quowl.quowl.web.beans.system.WebBean;

import java.util.Date;
import java.util.List;

public class QuoteBean implements WebBean<Quote> {

    private Long id;
    private Long userId;
    private String book;
    private String author;
    private String text;
    private String date;
    private String userAvatar;
    private String userNickname;
    private List<UserBean> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date createdDate) {
        Date current = new Date();
        Long diff = current.getTime() - createdDate.getTime();
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        StringBuilder builder = new StringBuilder();
        builder.append(diffDays).append( " д, ").append(diffHours).append(" ч.");
        this.date = builder.toString();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public List<UserBean> getUsers() {
        return users;
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }

    @Override
    public void copyDataFromDomain(Quote obj) {
        setId(obj.getId());
        setAuthor(obj.getAuthor());
        setBook(obj.getBook());
        setText(obj.getText());
        setDate(obj.getCreatedDate());
        setUserId(obj.getUser().getId());
        setUserNickname(obj.getUser().getNickname());
    }

    @Override
    public void copyDataToDomain(Quote obj) {
        obj.setId(this.getId());
        obj.setAuthor(this.getAuthor());
        obj.setBook(this.getBook());
        obj.setText(this.getText());
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
