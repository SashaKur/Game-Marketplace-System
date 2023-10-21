package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository  extends CrudRepository<Message, Long> {
}
