package com.oleksandr.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


/**

 Represents a message in the system.

 The Message class is an entity class that represents a message with various properties and behaviors.
 It includes properties such as ID, receiver client, sender client, message content, and time sent.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.
 It includes relationships with the Client entity using @ManyToOne and @JoinColumn associations.
 The receiver and sender clients are required and have non-null constraints.

 @see jakarta.persistence.Entity
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    @NotNull
    private Client receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    @NotNull
    private Client sender;

    @NotNull
    @NotBlank
    @Size(max = 500)
    private String message;

    @NotNull
    private LocalDateTime timeSent;
}
