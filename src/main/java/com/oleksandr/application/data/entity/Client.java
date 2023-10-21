package com.oleksandr.application.data.entity;

import com.oleksandr.application.data.entity.enums.Genre;
import com.oleksandr.application.data.service.PaymentService;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**

 Represents a client in the system.

 The Client class is an entity class that represents a client with various properties and behaviors.
 It includes properties such as ID, account name, email, profile picture URL, preferred genres, purchased games,
 payments, posts, comments, references, messages, support tickets, and tournament participation.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.
 It includes relationships with other entities using various annotations such as @ElementCollection, @CollectionTable,
 @OneToMany, @ManyToMany, @ManyToOne, and @JoinColumn.

 @see jakarta.persistence.Entity
 */


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Account name is mandatory")
    @Size(max = 255)
    private String accountName;

    @Email
    @NotNull
    private String email;

    @URL
    @Nullable
    private String profilePictureURL;

    @ElementCollection
    @CollectionTable(name = "client_genres", joinColumns = @JoinColumn(name="client_id"))
    @Builder.Default
    private Set<Genre> preferredGenres = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "purchased_games", joinColumns = @JoinColumn(name="client_id"))
    @Builder.Default
    private Set<String> games = new HashSet<>();

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Payment> payments = new HashSet<>();


    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "commentator", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Client> referencesTo = new HashSet<>();

    @ManyToMany(mappedBy="referencesTo", fetch = FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Client> referencesFrom = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Message> receivedMessages = new ArrayList<>();

    @OneToMany(mappedBy = "issuer", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SupportTicket> tickets = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tournament> tournaments = new HashSet<>();

    public void addPayment(Payment payment){
        this.payments.add(payment);
    }

}
