package com.quowl.quowl.repository.books;

import com.quowl.quowl.domain.logic.books.Books;
import com.quowl.quowl.domain.logic.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link Books} entities. It
 * provides all necessary CRUD method.
 *
 * @author Marchello
 * @author nllsdfx
 */

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {

    @Query("SELECT count(b) FROM Books b WHERE b.user.id = ?1 and b.readed = true")
    Long countAllReadBooks(Long userId);

    List<Books> findAllByUser(User user);

    @Query("select b from Books b where b.book = ?1 and b.author = ?2 and b.user.id = ?3")
    Books findOneByBookAndAuthorAndUserId(String bookName, String authorName, Long userId);

    @Query("select b.author from Books b where b.user.id = ?1 and b.book = ?2 ")
    String findAuthorByUserIdAndBook(Long userId, String book);

    /**
     * Selects the most read books from books DB.
     *
     * @param page the limit for select query.
     * @return list of <code>Object[]</code> arrays.
     * Object[] contains a book and its count rows in DB.
     */
    @Query(value = "select b.book, count(b.book) as qty from books b where b.readed = 1 group by b.book order by qty desc limit ?1", nativeQuery = true)
    List<Object[]> findTheMostReadBooks(Long limit);

    /**
     * Selects the most read authors from books DB.
     *
     * @param page the limit for select query.
     * @return list of <code>Object[]</code> arrays.
     * Object[] contains an author and its count rows in DB.
     */
    @Query(value = "select b.author, count(b.author) as qty from books b where b.readed = 1 group by b.author order by qty desc limit ?1", nativeQuery = true)
    List<Object[]> findTheMostReadAuthors(Long limit);
}
