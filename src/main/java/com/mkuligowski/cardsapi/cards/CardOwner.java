package com.mkuligowski.cardsapi.cards;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
class CardOwner {

    @Column(name = "user_id")
    private Long id;

    static CardOwner of(long userId) {
        return new CardOwner(userId);
    }
}
