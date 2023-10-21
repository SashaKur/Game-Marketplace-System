package com.oleksandr.application.data.entity.employee;


import com.oleksandr.application.data.entity.SupportTicket;
import com.oleksandr.application.data.entity.Tournament;
import com.oleksandr.application.data.entity.enums.Genre;
import com.oleksandr.application.data.entity.enums.TournamentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;


/**

 Represents an employee in the system.

 The Employee class is an entity class that represents an employee with various properties and behaviors.
 It implements the CustomerSupport, PlatformModerator, and TournamentOrganizer interfaces.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes other annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, and @ToString for generating
 boilerplate code for getters, setters, constructors, and toString method respectively.

 @see CustomerSupport

 @see PlatformModerator

 @see TournamentOrginizer
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee implements CustomerSupport, PlatformModerator, TournamentOrginizer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = EmployeeType.class)
    @Column(nullable = false)
    private Set<EmployeeType> types;

    @NotBlank
    @Size(max=255)
    private String name;

    @NotBlank
    @Size(max=255)
    private String surname;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    private Integer salary;

    @NotNull
    @Min(value = 0)
    private final static Integer minSalary = 4000;

    public void setSalary(Integer salary) {
        if(salary<minSalary)
            throw new IllegalArgumentException("Salary must be >= minSalary");

        this.salary = salary;
    }

    @ElementCollection
    @CollectionTable(name = "employee_languages", joinColumns = @JoinColumn(name="customersupport_id"))
    @Size(min = 1, message = "Languages list cannot be smaller than 1")
    private Set<String> languages;

    @Min(value = 0)
    private Double averageResponseTime;

    @ElementCollection
    @CollectionTable(name = "orginizer_experience", joinColumns = @JoinColumn(name="organizer_id"))
    @Size(min = 1, message = "Genres list cannot be smaller than 1")
    private Set<Genre> genresExpertise;


    @OneToMany(mappedBy = "customerSupport", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SupportTicket> tickets = new HashSet<>();


    @ManyToMany(mappedBy = "organizers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tournament> tournaments = new HashSet<>();


    public Employee(EnumSet<EmployeeType> types, Long id, String name, String surname, LocalDate birthDate, Integer salary, Set<String> languages, Set<Genre> genresExpertise, Double averageResponseTime) {
        setId(id);
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setSalary(salary);

        changeRole(types, languages, genresExpertise, averageResponseTime);
    }


    public boolean isCustomerSupport(){
        return this.types.contains(EmployeeType.Support);
    }

    public boolean isTournamentOrginizer(){
        return this.types.contains(EmployeeType.Orginizer);
    }

    public boolean isPlatformModerator(){
        return this.types.contains(EmployeeType.Moderator);
    }

    public void changeRole(EnumSet<EmployeeType> types, Set<String> languages, Set<Genre> genresExpertise, Double averageResponseTime) {
        if (types.size() > 2)
            throw new IllegalArgumentException("Too much roles");

        if (types.isEmpty())
            throw new IllegalArgumentException("Cannot create abstract Employee");

        if (types.contains(EmployeeType.Orginizer) && (types.contains(EmployeeType.Support) || types.contains(EmployeeType.Moderator)))
            throw new IllegalArgumentException("Wrong employee occupation");

        this.types = types;

        if (types.contains(EmployeeType.Moderator) && averageResponseTime != null)
            setAverageResponseTime(averageResponseTime);

        if (types.contains(EmployeeType.Orginizer) && genresExpertise != null)
            setGenresExpertise(genresExpertise);

        if (types.contains(EmployeeType.Support) && languages != null)
            setLanguages(languages);

    }

    public Set<String> getLanguages() {
        return Collections.unmodifiableSet(languages);
    }

    public void setLanguages(Set<String> languages) {
        if(!types.contains(EmployeeType.Support))
            throw new IllegalArgumentException("Employee is not customer support");

        this.languages = languages;
    }

    public double getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(Double averageResponseTime) {
        if(!types.contains(EmployeeType.Moderator))
            throw new IllegalArgumentException("Employee is not platform moderator");

        this.averageResponseTime = averageResponseTime;
    }

    public Set<Genre> getGenresExpertise() {
        return Collections.unmodifiableSet(genresExpertise);
    }

    public void setGenresExpertise(Set<Genre> genresExpertise) {
        if(!types.contains(EmployeeType.Orginizer))
            throw new IllegalArgumentException("Employee is not tournament organizer");

        this.genresExpertise = genresExpertise;
    }


    public Set<Tournament> getTournaments() {
        if(!this.types.contains(EmployeeType.Orginizer))
            throw new IllegalArgumentException("Can't access this method");

        return Collections.unmodifiableSet(tournaments);
    }


    public Set<SupportTicket> getTickets() {
        if(!this.types.contains(EmployeeType.Support))
            throw new IllegalArgumentException("Can't access this method");

        return Collections.unmodifiableSet(tickets);
    }

    public void setTickets(Set<SupportTicket> tickets) {
        if(!this.types.contains(EmployeeType.Support))
            throw new IllegalArgumentException("Can't access this list");

        this.tickets = tickets;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        if(!this.types.contains(EmployeeType.Orginizer))
            throw new IllegalArgumentException("Can't access this list");

        this.tournaments = tournaments;
    }

    public void addTicket(SupportTicket ticket) {
        if(!this.types.contains(EmployeeType.Support))
            throw new IllegalArgumentException("Can't access this list");

        this.tickets.add(ticket);
    }


    public void addTournament(Tournament tournament) {
        if(!this.types.contains(EmployeeType.Orginizer))
            throw new IllegalArgumentException("Can't access this list");

        this.tournaments.add(tournament);
    }

    /**

     Retrieves the set of assigned unanswered support tickets.

     @throws IllegalArgumentException if the current employee type does not have access to this method

     @return the set of assigned unanswered support tickets
     */

    @Override
    public Set<SupportTicket> getAssignedUnansweredTickets() {
        if(!this.types.contains(EmployeeType.Support))
            throw new IllegalArgumentException("Can't access this method");

        Set<SupportTicket> supportTickets = new HashSet<>();

        for(SupportTicket supportTicket : this.tickets){
            if(supportTicket.getAnswer() == null || supportTicket.getAnswer().isBlank())
                supportTickets.add(supportTicket);
        }

        return Collections.unmodifiableSet(supportTickets);
    }

    /**

     Changes the status of a tournament with the given ID.

     @param id the ID of the tournament

     @param tournamentStatus the new status to be set for the tournament

     @throws IllegalArgumentException if the current employee type does not have access to this method
     */

    @Override
    public void changeTournamentStatus(Long id, TournamentStatus tournamentStatus) {
        if(!this.types.contains(EmployeeType.Orginizer))
            throw new IllegalArgumentException("Can't access this method");

        for(Tournament tournament : tournaments)
            if(tournament.getId()==id) {
                tournament.setTournamentStatus(tournamentStatus);
                break;
            }

    }
}
