package com.ecommerce.repositories;

import com.ecommerce.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepo extends JpaRepository<Comment,Long> {
    Collection<Comment> findByProductId(Long productId);
}
