package com.quowl.quowl.service.comment;

import com.quowl.quowl.domain.system.Comment;
import com.quowl.quowl.repository.comment.CommentRepository;
import com.quowl.quowl.web.beans.system.IService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CommentService implements IService<Comment, Long> {
    @Inject private CommentRepository commentRepository;

    public List<Comment> getAllByQuoteId(Long quoteId) {
        return commentRepository.findAllByQuoteIdOrderByCreatedDateAsc(quoteId);
    }

    @Override
    public Comment save(Comment object) {
        return commentRepository.save(object);
    }

    @Override
    public void delete(Comment object) {
        commentRepository.delete(object);
    }

    @Override
    public void delete(Long aLong) {
        commentRepository.delete(aLong);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findOne(Long aLong) {
        return commentRepository.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return commentRepository.exists(aLong);
    }
}
