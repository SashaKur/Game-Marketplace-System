package com.oleksandr.application.data.entity.employee;

import com.oleksandr.application.data.entity.SupportTicket;

import java.util.Set;

public interface CustomerSupport {

    Set<SupportTicket> getAssignedUnansweredTickets();

    void setLanguages(Set<String> languages);
    Set<String> getLanguages();
}
