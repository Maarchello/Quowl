package com.quowl.quowl.repository.comment;

import com.quowl.quowl.domain.system.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByQuoteIdOrderByCreatedDateAsc(Long quoteId);

}
