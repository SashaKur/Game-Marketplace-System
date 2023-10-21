package com.oleksandr.application.data.entity;

import com.oleksandr.application.data.entity.employee.Employee;
import com.oleksandr.application.data.entity.enums.ESRB;
import com.oleksandr.application.data.entity.enums.Genre;
import com.oleksandr.application.data.entity.enums.Platform;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**

 Represents a game in the system.

 The Game class is an entity class that represents a game with various properties and behaviors.
 It includes properties such as ID, title, description, release date, genre, platforms, ESRB rating,
 VR requirement, price, associated payments, and game developer.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.
 It includes relationships with the GameDeveloper entity using @ManyToOne and @JoinColumn association.
 This represents the game developer associated with the game.
 It includes relationships with the Payment entity using @OneToMany and mappedBy association.
 This represents the payments associated with the game.

 @see jakarta.persistence.Entity
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 256)
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private Genre genre;

    @NotNull
    @ElementCollection
    @CollectionTable(name = "game_platforms", joinColumns = @JoinColumn(name="game_id"))
    @Builder.Default
    @Size(min = 1, message = "Platforms list cannot be smaller than 1")
    private Set<String> platforms = new HashSet<>();

    @NotNull
    private ESRB esrb;

    @Builder.Default
    private boolean requiresVR = false;

    @NotNull
    @Min(value = 0, message = "Price cannot be negative")
    @Max(value = maxPrice)
    private double price;

    @Min(0)
    @Builder.Default
    private final static int maxPrice = 500;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Payment> payments = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "gamedeveloper_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GameDeveloper developer;

    public void addPayment(Payment payment){
        this.payments.add(payment);
    }

}

