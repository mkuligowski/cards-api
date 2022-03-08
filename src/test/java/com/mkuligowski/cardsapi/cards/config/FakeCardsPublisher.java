package com.mkuligowski.cardsapi.cards.config;

import com.mkuligowski.cardsapi.cards.CardsPublisher;
import com.mkuligowski.cardsapi.cards.domainapi.CardAddedMessage;

import java.util.ArrayList;
import java.util.List;

public class FakeCardsPublisher implements CardsPublisher {

    private List<CardAddedMessage> cardAddedMessageList = new ArrayList<>();

    @Override
    public void publish(CardAddedMessage message) {
        this.cardAddedMessageList.add(message);
    }

    public List<CardAddedMessage> getCardAddedMessageList() {
        return cardAddedMessageList;
    }
}