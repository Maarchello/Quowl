package com.quowl.quowl.service.book;


import com.quowl.quowl.domain.logic.books.BookPlan;
import com.quowl.quowl.repository.books.BookPlanRepository;
import com.quowl.quowl.web.beans.system.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BookPlanService implements IService<BookPlan, Long> {
    private final Logger log = LoggerFactory.getLogger(BookPlanService.class);
    @Inject private BookPlanRepository bookPlanRepository;

    public List<BookPlan> getByUserId(Long userId) {
        return bookPlanRepository.findAllByUserId(userId);
    }

    public boolean tryAdd(BookPlan bookPlan) {
        try {
            save(bookPlan);
        } catch (DataIntegrityViolationException e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public BookPlan save(BookPlan bookPlan) {
        return bookPlanRepository.save(bookPlan);
    }

    @Override
    public void delete(BookPlan object) {
        bookPlanRepository.delete(object);
    }

    @Override
    public void delete(Long aLong) {
        bookPlanRepository.delete(aLong);
    }

    @Override
    public List<BookPlan> findAll() {
        return bookPlanRepository.findAll();
    }

    @Override
    public BookPlan findOne(Long aLong) {
        return bookPlanRepository.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return bookPlanRepository.exists(aLong);
    }
}
