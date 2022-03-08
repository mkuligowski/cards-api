package com.mkuligowski.cardsapi.cards;

import com.mkuligowski.cardsapi.cards.domainapi.MaskedCreditCard;
import com.mkuligowski.cardsapi.cards.domainapi.NewCardRequest;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardsService {

    private final CardsPublisher publisher;
    private final CVVChecker cvvChecker;
    private final Validator validator;
    private final CardRepository cardRepository;

    @Transactional
    public Either<AddCreditCardError, MaskedCreditCard> addCard(long userId, NewCardRequest newCardDto) {
        Set<ConstraintViolation<NewCardRequest>> violations = validator.validate(newCardDto);

        if (!violations.isEmpty())
            return Either.left(new AddCreditCardError(AddCreditCardErrorType.INVALID_DATA));

        if (!cvvChecker.cvvMatch(newCardDto.getCardNumber(), newCardDto.getCvv()))
            return Either.left(new AddCreditCardError(AddCreditCardErrorType.INVALID_CVV));


        return cardRepository.findByCardNumberAndAndCardOwner(newCardDto.getCardNumber(), userId)
                .<Either<AddCreditCardError, MaskedCreditCard>>map(card -> Either.left(new AddCreditCardError(AddCreditCardErrorType.CARD_ALREADY_EXISTS, card.getId())))
                .orElseGet(() -> Either.right(processCreatingCard(userId, newCardDto)));
    }

    private MaskedCreditCard processCreatingCard(long userId, NewCardRequest newCardDto) {
        Card card = Card.create(newCardDto, userId);
        Card savedCard = cardRepository.save(card);
        publisher.publish(savedCard.toMessage());

        return savedCard.toMaskedCreditCard();
    }

    @Transactional(readOnly = true)
    public Optional<MaskedCreditCard> findUserCard(long id, long authenticatedUser) {
        return cardRepository.findByIdAndCardOwner(id, authenticatedUser)
                .map(Card::toMaskedCreditCard);
    }
}
