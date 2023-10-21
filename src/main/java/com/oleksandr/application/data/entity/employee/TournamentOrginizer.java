package com.oleksandr.application.data.entity.employee;

import com.oleksandr.application.data.entity.enums.Genre;
import com.oleksandr.application.data.entity.enums.TournamentStatus;

import java.util.Set;

public interface TournamentOrginizer {
    void changeTournamentStatus(Long id, TournamentStatus tournamentStatus);
    Set<Genre> getGenresExpertise();
    void setGenresExpertise(Set<Genre> genresExpertise);

}
