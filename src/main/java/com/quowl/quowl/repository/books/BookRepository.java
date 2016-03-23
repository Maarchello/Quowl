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

    Books findOneByBookAndAuthor(String bookName, String authorName);

    @Query("select b.author from Books b where b.user.id = ?1 and b.book = ?2 ")
    String findAuthorByUserIdAndBook(Long userId, String book);

    /**
     * Selects the most read books from books DB.
     *
     * @param page the limit for select query.
     * @return list of <code>Object[]</code> arrays.
     * Object[] contains a book and its count rows in DB.
     */
    @Query("select b.book, count (b.book) from Books b where" +
            "b.readed = 1 group by b.book desc")
    List<Object[]> findTheMostReadBooks(Pageable page);

    /**
     * Selects the most read authors from books DB.
     *
     * @param page the limit for select query.
     * @return list of <code>Object[]</code> arrays.
     * Object[] contains an author and its count rows in DB.
     */
    @Query("select b.author, count (b.author) from Books b where" +
            "b.readed = 1 group by b.author desc")
    List<Object[]> findTheMostReadAuthors(Pageable page);
}
