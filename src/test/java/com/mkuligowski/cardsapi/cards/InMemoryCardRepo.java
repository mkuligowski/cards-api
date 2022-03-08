package com.mkuligowski.cardsapi.cards;

import com.mkuligowski.cardsapi.cards.Card;
import com.mkuligowski.cardsapi.cards.CardNumber;
import com.mkuligowski.cardsapi.cards.CardRepository;
import com.mkuligowski.cardsapi.utils.ReflectionUtilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCardRepo implements CardRepository {
    private final Map<Long, Card> repo = new HashMap<>();
    private long id = 0L;


    @Override
    public Optional<Card> findByIdAndCardOwner(long cardId, long ownerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Card> findByCardNumberAndAndCardOwner(String number, long ownerId) {
        return repo.values().stream().filter(card -> {
            CardNumber cardNumber = (CardNumber) ReflectionUtilities.getPrivateFieldValue("cardNumber", card);
            return number.equals(cardNumber.getNumber());
        }).findFirst();
    }

    @Override
    public Card save(Card card) {
        if (card.getId() == 0L) {
            ReflectionUtilities.setPrivateField("id", card, ++id);
        }

        repo.put(card.getId(), card);
        return card;
    }
}