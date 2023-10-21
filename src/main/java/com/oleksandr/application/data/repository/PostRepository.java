package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository  extends CrudRepository<Post, Long> {
}
