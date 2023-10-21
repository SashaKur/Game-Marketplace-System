package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.GameDeveloper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameDeveloperRepository  extends CrudRepository<GameDeveloper, Long> {

    @Query("select dev from GameDeveloper dev where lower(dev.studioName) like lower(concat('%', :text, '%')) ")
    List<GameDeveloper> searchByName(@Param("text") String text);


    List<GameDeveloper> findAll();
}
