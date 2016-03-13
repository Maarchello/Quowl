package com.quowl.quowl.repository.books;


import com.quowl.quowl.domain.logic.books.BookPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPlanRepository extends JpaRepository<BookPlan, Long> {
}
