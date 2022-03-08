package com.mkuligowski.cardsapi.cards;

import com.mkuligowski.cardsapi.cards.config.FakeCVVChecker;
import com.mkuligowski.cardsapi.cards.config.FakeCardsPublisher;
import com.mkuligowski.cardsapi.cards.domainapi.MaskedCreditCard;
import com.mkuligowski.cardsapi.cards.domainapi.NewCardRequest;
import io.vavr.control.Either;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class CardsServiceTest {

    private CardsService cardsService;
    private FakeCardsPublisher fakeCardsPublisher;
    private FakeCVVChecker cvvChecker;
    private FakeValidator fakeValidator;
    private InMemoryCardRepo inMemoryCardRepo;

    @Before
    public void setup() {
        this.fakeCardsPublisher = new FakeCardsPublisher();
        this.cvvChecker = new FakeCVVChecker();
        this.fakeValidator = new FakeValidator();
        this.inMemoryCardRepo = new InMemoryCardRepo();
        this.cardsService = new CardsService(fakeCardsPublisher, cvvChecker, fakeValidator, inMemoryCardRepo);
    }

    @Test
    public void addCard_allChecksSuccessful_cardAdded() {
        final long userId = 1L;
        NewCardRequest dto = new NewCardRequest("1234123412341234", FakeCVVChecker.DEFAULT_MATCHING_CVV,"2021-13");
        Either<AddCreditCardError, MaskedCreditCard> result = cardsService.addCard(userId, dto);
        assertThat(result.isRight());
        assertThat(fakeCardsPublisher.getCardAddedMessageList()).hasSize(1);
        assertThat(fakeCardsPublisher.getCardAddedMessageList().get(0).getCardId()).isEqualTo(result.get().getId());
    }

    @Test
    public void addCard_cvvCheckFailed_invalidCvvErrorTypeReturned() {
        final long userId = 1L;
        NewCardRequest dto = new NewCardRequest("1234123412341234", "999","2021-13");
        Either<AddCreditCardError, MaskedCreditCard> result = cardsService.addCard(userId, dto);
        assertThat(result.isLeft());
        assertThat(result.getLeft().getErrorType()).isEqualTo(AddCreditCardErrorType.INVALID_CVV);
    }

    @Test
    public void addCard_cardAlreadyExists_invalidDataErrorTypeReturned() {
        final long userId = 1L;
        NewCardRequest dto = new NewCardRequest(FakeValidator.FAILING_CARD_NUMBER, FakeCVVChecker.DEFAULT_MATCHING_CVV,"2021-13");
        Either<AddCreditCardError, MaskedCreditCard> result = cardsService.addCard(userId, dto);
        assertThat(result.isLeft());
        assertThat(result.getLeft().getErrorType()).isEqualTo(AddCreditCardErrorType.INVALID_DATA);
    }



    @Test
    public void addCard_cardAlreadyExists_invalidDataErrorTypeReturnedxxxxxxxxx() {
        final long userId = 1L;
        NewCardRequest dto = new NewCardRequest("1234123412341234", FakeCVVChecker.DEFAULT_MATCHING_CVV,"2021-13");
        Either<AddCreditCardError, MaskedCreditCard> result = cardsService.addCard(userId, dto);
        assertThat(result.isRight());
        Either<AddCreditCardError, MaskedCreditCard> result2 = cardsService.addCard(userId, dto);
        assertThat(result2.isLeft());
        assertThat(result2.getLeft().getErrorType()).isEqualTo(AddCreditCardErrorType.CARD_ALREADY_EXISTS);
    }

    @Test
    public void getCardById_cardAlreadyExists_cardReturned() {
        final long userId = 1L;
        NewCardRequest dto = new NewCardRequest("1234123412341234", FakeCVVChecker.DEFAULT_MATCHING_CVV,"2021-13");
        Either<AddCreditCardError, MaskedCreditCard> result = cardsService.addCard(userId, dto);
        MaskedCreditCard maskedCreditCard = result.getOrElseThrow((Supplier<RuntimeException>) RuntimeException::new);
        Optional<MaskedCreditCard> optionalMaskedCreditCard = cardsService.findUserCard(maskedCreditCard.getId(), userId);
        assertThat(optionalMaskedCreditCard.isPresent());
    }

    @Test
    public void getCardById_cardNotExists_emptyOptionalReturned() {
        final long userId = 1L;
        final long notExistingCardId = 10L;
        Optional<MaskedCreditCard> optionalMaskedCreditCard = cardsService.findUserCard(notExistingCardId, userId);
        assertThat(optionalMaskedCreditCard.isEmpty());
    }




}
