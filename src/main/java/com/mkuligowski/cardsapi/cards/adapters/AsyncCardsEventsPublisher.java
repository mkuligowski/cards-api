package com.mkuligowski.cardsapi.cards.adapters;

import com.mkuligowski.cardsapi.cards.CardsPublisher;
import com.mkuligowski.cardsapi.cards.domainapi.CardAddedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class AsyncCardsEventsPublisher implements CardsPublisher {

    @Override
    public void publish(CardAddedMessage message) {
       log.info("Publishing message, cardId: {}", message.getCardId() );
    }
}
