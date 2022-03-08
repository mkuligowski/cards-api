package com.mkuligowski.cardsapi.cards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CardRepository extends Repository<Card, Long> {

    @Query("SELECT c from Card c where c.id = ?1 and c.cardOwner.id = ?2")
    Optional<Card> findByIdAndCardOwner(long cardId, long ownerId);

    @Query("SELECT c from Card c where c.cardNumber.number = ?1 and c.cardOwner.id = ?2")
    Optional<Card> findByCardNumberAndAndCardOwner(String number, long ownerId);

    Card save(Card card);
}
