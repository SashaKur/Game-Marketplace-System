package com.oleksandr.application.data.service;


import com.oleksandr.application.data.entity.Game;
import com.oleksandr.application.data.entity.GameDeveloper;
import com.oleksandr.application.data.entity.Payment;
import com.oleksandr.application.data.repository.GameDeveloperRepository;
import com.oleksandr.application.data.repository.GameRepository;
import com.oleksandr.application.data.repository.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class GameDeveloperService {

    private final GameDeveloperRepository gameDeveloperRepository;
    private final GameRepository gameRepository;
    private final PaymentRepository paymentRepository;

    public GameDeveloperService(GameDeveloperRepository gameDeveloperRepository, GameRepository gameRepository, PaymentRepository paymentRepository){

        this.gameDeveloperRepository = gameDeveloperRepository;
        this.gameRepository = gameRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<GameDeveloper> findAllGameDevelopers(String text){
        if(text == null || text.isBlank()){
            return gameDeveloperRepository.findAll();
        }

        return gameDeveloperRepository.searchByName(text);
    }

    public List<GameDeveloper> findAll(){
        return gameDeveloperRepository.findAll();
    }


    public Set<Game> findAllGamesByDevelopers(Set<GameDeveloper> gameDevelopers){
        if(gameDevelopers == null || gameDevelopers.isEmpty()){
            return new HashSet<>();
        }else{
            return gameRepository.findByDeveloperIn(gameDevelopers);
        }
    }

    public void savePayment(Payment payment){
        paymentRepository.save(payment);
    }
}
