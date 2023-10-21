package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.Game;
import com.oleksandr.application.data.entity.GameDeveloper;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface GameRepository  extends CrudRepository<Game, Long> {

    Set<Game> findByDeveloperIn(Set<GameDeveloper> ids);

    Optional<Game> findGameById(Long id);

}
