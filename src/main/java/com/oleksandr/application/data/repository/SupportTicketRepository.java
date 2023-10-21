package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.SupportTicket;
import org.springframework.data.repository.CrudRepository;

public interface SupportTicketRepository  extends CrudRepository<SupportTicket, Long> {

    void deleteByAnswerIsNotNull();
}
