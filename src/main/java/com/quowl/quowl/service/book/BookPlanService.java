package com.quowl.quowl.service.book;


import com.quowl.quowl.domain.logic.books.BookPlan;
import com.quowl.quowl.repository.books.BookPlanRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookPlanService {
    @Inject private BookPlanRepository bookPlanRepository;

    public BookPlan save(BookPlan bookPlan) {
        return bookPlanRepository.save(bookPlan);
    }
}
