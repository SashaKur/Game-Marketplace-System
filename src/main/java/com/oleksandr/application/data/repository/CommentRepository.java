package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository  extends CrudRepository<Comment, Long> {
}
