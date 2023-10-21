package com.oleksandr.application.data.entity;


import com.oleksandr.application.data.entity.employee.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**

 Represents a support ticket in the system.

 The SupportTicket class is an entity class that represents a support ticket with various properties and behaviors.
 It includes properties such as ID, title, description, answer, customer support employee, and issuer client.
 The class is annotated with @Entity to indicate that it is a persistent entity and can be stored in a database.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.
 It includes relationships with the Employee and Client entities using @ManyToOne associations.

 @see jakarta.persistence.Entity
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String title;


    @NotNull
    @NotBlank
    @Size(max = 1000)
    private String description;


    private String answer;

    @ManyToOne
    @JoinColumn(name = "customersupport_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee customerSupport;


    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client issuer;

}
