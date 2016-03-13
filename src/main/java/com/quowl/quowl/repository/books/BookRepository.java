package com.quowl.quowl.repository.books;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {

    @Query("SELECT count(b) FROM Books b WHERE b.user.id = ?1 and b.readed = true")
    Long countAllReadBooks(Long userId);

    List<Books> findAllByUser(User user);

    Books findOneByBookAndAuthor(String bookName, String authorName);

    @Query("select b.author from Books b where b.user.id = ?1 and b.book = ?2 ")
    String findAuthorByUserIdAndBook(Long userId, String book);

}
