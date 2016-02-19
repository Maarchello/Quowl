package com.quowl.quowl.repository.books;

import com.quowl.quowl.domain.logic.books.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

    @Query("SELECT count(b) FROM Books b WHERE b.user.id = ?1")
    Long countAllReadBooks(Long userId);

}
