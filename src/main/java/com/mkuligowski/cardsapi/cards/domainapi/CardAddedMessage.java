package com.mkuligowski.cardsapi.cards.domainapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CardAddedMessage {
    private final long cardId;
    private final long userId;
}
