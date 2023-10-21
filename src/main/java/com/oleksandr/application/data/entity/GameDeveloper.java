package com.oleksandr.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**

 Represents a game developer in the system.

 The GameDeveloper class is an entity class that represents a game developer with various properties and behaviors.
 It includes properties such as ID, studio name, email, website, country of origin, and associated games.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.
 It includes relationships with the Game entity using @OneToMany and mappedBy association.
 This represents the games associated with the game developer.

 @see jakarta.persistence.Entity
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GameDeveloper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotBlank
    @Size(max = 256)
    private String studioName;

    @NotNull
    @Email
    private String email;

    @NotBlank
    private String website;

    @NotNull
    private String countryOfOrigin;

    @OneToMany(mappedBy = "developer")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Game> games = new HashSet<>();

}
