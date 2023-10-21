package com.oleksandr.application.data.entity;

import com.oleksandr.application.data.entity.enums.PostReaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.annotation.Nullable;

/**

 Represents a comment on a post.

 The Comment class is an entity class that represents a comment on a post with various properties.
 It includes properties such as ID, text, post reaction, associated post, and commentator.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.
 It includes relationships with the Post entity using @ManyToOne and @JoinColumn association.
 This represents the post associated with the comment.
 It includes relationships with the Client entity using @ManyToOne and @JoinColumn association.
 This represents the commentator who posted the comment.

 @see jakarta.persistence.Entity
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull
    @Size(max = 400)
    private String text;

    @Nullable
    private PostReaction postReaction;


    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false, updatable = false)
    private Post post;


    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false, updatable = false)
    private Client commentator;
}
