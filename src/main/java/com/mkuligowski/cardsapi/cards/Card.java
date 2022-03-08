package com.mkuligowski.cardsapi.cards;

import com.mkuligowski.cardsapi.cards.domainapi.*;
import com.mkuligowski.cardsapi.common.AbstractBaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Card extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Embedded
    private CardNumber cardNumber;

    @Embedded
    private CvvNumber cvvNumber;

    @Embedded
    private CardExpirationDate expirationDate;

    @Enumerated(value = EnumType.STRING)
    private CardState state;

    @Embedded
    private CardOwner cardOwner;

    static Card create(NewCardRequest newCardDto, long cardOwnerId) {
        return Card.builder()
                .state(CardState.VALID)
                .cardNumber(CardNumber.of(newCardDto.getCardNumber()))
                .cvvNumber(CvvNumber.of(newCardDto.getCvv()))
                .expirationDate(CardExpirationDate.of(newCardDto.getExpirationDate()))
                .cardOwner(CardOwner.of(cardOwnerId))
                .build();
    }

    Card block() {
        if (state != CardState.VALID) {
            throw new InvalidCardStateException("Only VALID card can be blocked");
        }
        state = CardState.BLOCKED;
        return this;
    }

    MaskedCreditCard toMaskedCreditCard() {
        return MaskedCreditCard.builder()
                .id(id)
                .cardNumber(cardNumber.maskedNumber())
                .expirationDate(expirationDate.getValue())
                .build();
    }

    public CardAddedMessage toMessage() {
        return new CardAddedMessage(id, cardOwner.getId());
    }
}
