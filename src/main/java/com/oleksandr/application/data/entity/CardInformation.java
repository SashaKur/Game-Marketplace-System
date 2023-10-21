package com.oleksandr.application.data.entity;


import com.oleksandr.application.data.entity.enums.CardType;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;


/**

 Represents card information used for payment processing.

 The CardInformation class is an embeddable class that represents the card information used for payment processing.
 It includes properties such as card number, CVV, expiration date, and card processing company.
 The class is annotated with @Embeddable to indicate that it is an embeddable component that can be embedded within
 other entity classes.
 It also includes annotations like @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, and @ToString for generating
 boilerplate code for getters, setters, constructors, builder, and toString method respectively.

 @see jakarta.persistence.Embeddable
 */

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CardInformation {

    @NotNull
    @NotBlank

    private String nameOnCard;

    @NotNull
    @CreditCardNumber
    private String cardNumber;

    @NotNull
    @NotBlank
    private String cvv;

    @NotNull
    @NotBlank
    @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid expiration date!")
    private String expirationDate;

    @NotNull
    @NotBlank
    private CardType cardType;
}


