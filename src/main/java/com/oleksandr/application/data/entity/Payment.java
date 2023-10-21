package com.oleksandr.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**

 Represents a payment in the system.

 The Payment class is an entity class that represents a payment with various properties and behaviors.
 It includes properties such as ID, card information, date, amount, product (game), and buyer client.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @Table for generating
 boilerplate code for getters, setters, constructors, builder, and defining table-level constraints respectively.
 It includes relationships with the CardInformation, Game, and Client entities using @Embedded, @ManyToOne, and @JoinColumn
 associations. The unique constraint is applied on the combination of "game_id" and "client_id" columns.
 @see jakarta.persistence.Entity
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"game_id", "client_id"})
})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Embedded
    private CardInformation cardInformation;

    @NotNull
    private LocalDate date;

    @NotNull
    @Min(0)
    private double amount;


    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull
    private Game product;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull
    private Client buyer;
}
