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
class CardExpirationDate {

    @Column(name = "expiration_year")
    private int year;

    @Column(name = "expiration_month")
    private int month;


    static CardExpirationDate of(String yearAndMonth) {
        if (yearAndMonth == null) {
            throw new InvalidValueException("Card expiration date cannot be null");
        }

        String[] values = yearAndMonth.split("-");
        CardExpirationDate cardExpirationDate = new CardExpirationDate();
        cardExpirationDate.year = Integer.parseInt(values[0]);
        cardExpirationDate.month = Integer.parseInt(values[1]);
        return cardExpirationDate;
    }

    public String getValue() {
        return String.format("%d-%02d", year, month);
    }
}
