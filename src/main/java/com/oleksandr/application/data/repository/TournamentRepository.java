package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.Tournament;
import org.springframework.data.repository.CrudRepository;

public interface TournamentRepository  extends CrudRepository<Tournament, Long> {
}
