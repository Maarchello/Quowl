package com.quowl.quowl.repository.quote;

import com.quowl.quowl.domain.logic.quote.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByUserIdOrderByCreatedDateDesc(Long userId);

    @Query("select q from Quote q where user_id in ?1 order by q.createdDate desc ")
    List<Quote> findAllByFollowing(List<Long> users);

    @Query("select q from Quote q where user.id in ?1 order by q.createdDate desc")
    List<Quote> findTenByFollowing(List<Long> users, Pageable pageable);

    @Query("SELECT count(q) FROM Quote q WHERE q.user.id = ?1")
    Long countAllQuotes(Long userId);

    @Query("select count(q) from Quote q where q.bookId = ?1")
    Long countAllQuotesByBook(Long bookId);

}
