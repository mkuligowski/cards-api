package com.mkuligowski.cardsapi.cards.adapters;

import com.mkuligowski.cardsapi.cards.AddCreditCardError;
import com.mkuligowski.cardsapi.cards.AddCreditCardErrorType;
import com.mkuligowski.cardsapi.cards.CardsService;
import com.mkuligowski.cardsapi.cards.domainapi.NewCardRequest;
import com.mkuligowski.cardsapi.security.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
class CardsController {

    private static final Map<AddCreditCardErrorType, AddCreditCardErrorMapper> ADD_CREDIT_CARD_ERROR_MAPPING = Map.of(
            AddCreditCardErrorType.CARD_ALREADY_EXISTS, cardAlreadyExistsMapper(),
            AddCreditCardErrorType.INVALID_DATA, addCreditCardError -> ResponseEntity.badRequest().build(),
            AddCreditCardErrorType.INVALID_CVV, addCreditCardError -> ResponseEntity.unprocessableEntity().build()
    );

    private final CardsService cardsService;
    private final SecurityContext securityContext;

    @GetMapping("/api/cards/{id}")
    ResponseEntity<?> findById(@PathVariable long id) {
        long authenticatedUser = securityContext.getAuthenticatedUserId();

        return cardsService.findUserCard(id, authenticatedUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/cards")
    ResponseEntity<?> addCard(@RequestBody NewCardRequest newCard) {
        long authenticatedUser = securityContext.getAuthenticatedUserId();

        return cardsService.addCard(authenticatedUser, newCard)
                .fold(this::mapError, ResponseEntity::ok);
    }

    private ResponseEntity<?> mapError(AddCreditCardError addCreditCardError) {
        return ADD_CREDIT_CARD_ERROR_MAPPING.get(addCreditCardError.getErrorType()).mapStatus(addCreditCardError);
    }

    private static AddCreditCardErrorMapper cardAlreadyExistsMapper() {
        return addCreditCardError -> addCreditCardError.getCreditCardId().map(creditCardId -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, String.format("/api/cards/%d",creditCardId));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
