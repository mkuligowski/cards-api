package com.mkuligowski.cardsapi.cards;

import com.mkuligowski.cardsapi.cards.domainapi.CardAddedMessage;

public interface CardsPublisher {
    void publish(CardAddedMessage message);
}
