package com.mkuligowski.cardsapi.cards;

import com.mkuligowski.cardsapi.cards.domainapi.InvalidValueException;
import com.mkuligowski.cardsapi.cards.utils.MaskingUtils;
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
class CardNumber {

    @Column(name = "card_number")
    private String number;

    static CardNumber of(String number) {
        if (number == null) {
            throw new InvalidValueException("card number cannot be null");
        }

       return new CardNumber(number);
    }

    public String maskedNumber() {
        return MaskingUtils.maskNumber(number, "xxxx-xxxx-xxxx-x###");
    }
}