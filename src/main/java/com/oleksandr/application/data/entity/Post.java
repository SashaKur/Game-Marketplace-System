package com.oleksandr.application.data.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**

 Represents a post in the system.

 The Post class is an entity class that represents a post with various properties and behaviors.
 It includes properties such as ID, title, text, images URL, maximum number of images, and author client.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.
 It includes relationships with the Client and Comment entities using @ManyToOne and @OneToMany associations.
 The images URL are stored as a collection in a separate table using @ElementCollection and @CollectionTable annotations.

 @see jakarta.persistence.Entity
 */


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 2000)
    private String text;

    @ElementCollection
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name="post_id"))
    @Size(max = 5)
    @Builder.Default
    private List<String> imagesURL = new ArrayList<>();

    @NotNull
    @Builder.Default
    private static int maxImages = 5;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client author;


    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Comment> comments = new HashSet<>();
}
