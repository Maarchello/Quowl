package com.quowl.quowl.web.beans.user;


import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.web.beans.system.ReadOnlyWebBean;

public class RecommendUserBean implements ReadOnlyWebBean<User> {

    private Long id;
    private String nickname;
    private String avaPath;
    private String bookName;
    private String authorName;
    private Long countQuotes;
    private Long countBooks;
    private Long countFollowers;
    private Long countFollowings;

    public RecommendUserBean() {}

    public RecommendUserBean(User user) {
        copyDataFromDomain(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvaPath() {
        return avaPath;
    }

    public void setAvaPath(String avaPath) {
        this.avaPath = avaPath;
    }

    public Long getCountQuotes() {
        return countQuotes;
    }

    public void setCountQuotes(Long countQuotes) {
        this.countQuotes = countQuotes;
    }

    public Long getCountBooks() {
        return countBooks;
    }

    public void setCountBooks(Long countBooks) {
        this.countBooks = countBooks;
    }

    public Long getCountFollowers() {
        return countFollowers;
    }

    public void setCountFollowers(Long countFollowers) {
        this.countFollowers = countFollowers;
    }

    public Long getCountFollowings() {
        return countFollowings;
    }

    public void setCountFollowings(Long countFollowings) {
        this.countFollowings = countFollowings;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public void copyDataFromDomain(User obj) {
        id = obj.getId();
        nickname = obj.getNickname();
        bookName = obj.getBookName();
        authorName = obj.getAuthorName();
    }
}
