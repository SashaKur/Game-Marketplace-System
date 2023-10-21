package com.oleksandr.application.data.service;

import com.oleksandr.application.data.entity.Game;
import com.oleksandr.application.data.repository.GameRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<Game> findGameById(long id){
        return gameRepository.findById(id);
    }
}
