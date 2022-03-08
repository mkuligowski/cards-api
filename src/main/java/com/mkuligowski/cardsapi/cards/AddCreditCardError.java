package com.mkuligowski.cardsapi.cards;

import java.util.Optional;

public class AddCreditCardError {
    private AddCreditCardErrorType errorType;
    private Long creditCardId;

    public AddCreditCardError(AddCreditCardErrorType errorType, Long creditCardId) {
        this.errorType = errorType;
        this.creditCardId = creditCardId;
    }

    public AddCreditCardError(AddCreditCardErrorType errorType) {
        this.errorType = errorType;
    }

    public AddCreditCardErrorType getErrorType() {
        return errorType;
    }

    public Optional<Long> getCreditCardId() {
        return Optional.ofNullable(creditCardId);
    }

}
