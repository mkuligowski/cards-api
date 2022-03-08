package com.mkuligowski.cardsapi.cards.domainapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NewCardRequest {
    @Pattern(regexp = "[0-9]{16}")
    private String cardNumber;
    @Pattern(regexp = "[0-9]{3}")
    private String cvv;
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}")
    private String expirationDate;
}
