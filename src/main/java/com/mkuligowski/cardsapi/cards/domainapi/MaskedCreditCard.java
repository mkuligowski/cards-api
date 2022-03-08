package com.mkuligowski.cardsapi.cards.domainapi;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MaskedCreditCard {
    private final long id;
    private final String cardNumber;
    private final String expirationDate;
}
