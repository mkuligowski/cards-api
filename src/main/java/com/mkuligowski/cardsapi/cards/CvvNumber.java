package com.mkuligowski.cardsapi.cards;

import com.mkuligowski.cardsapi.cards.domainapi.InvalidValueException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@Getter
class CvvNumber {

    private static final int CVV_VALID_LENGTH = 3;

    @Column(name = "cvv")
    private String number;

    static CvvNumber of(String number) {
        if (number == null || number.length() != CVV_VALID_LENGTH) {
            throw new InvalidValueException("CVV is not valid");
        }

        return new CvvNumber(number);
    }
}
