package com.quowl.quowl.repository.quote;

import com.quowl.quowl.domain.logic.quote.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByUserIdOrderByCreatedDateDesc(Long userId);

    @Query("SELECT count(q) FROM Quote q WHERE q.user.id = ?1")
    Long countAllQuotes(Long userId);

}
