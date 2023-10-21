package com.oleksandr.application.data.entity;


import com.oleksandr.application.data.entity.employee.Employee;
import com.oleksandr.application.data.entity.enums.TournamentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

/**

 Represents a tournament in the system.

 The Tournament class is an entity class that represents a tournament with various properties and behaviors.
 It includes properties such as ID, title, start date, end date, prize pool, tournament status, organizers, and participants.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.
 It includes relationships with the Employee and Client entities using @ManyToMany associations.

 @see jakarta.persistence.Entity
 */


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String title;


    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Min(0)
    private int prizePool;


    @NotNull
    private TournamentStatus tournamentStatus;


    @ManyToMany
    @JoinTable(
            name = "tournament_organizer",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "organizer_id")
    )
    private Set<Employee> organizers = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "tournament_participant",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Client> participants = new HashSet<>();


    public long getDuration(){
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

}
