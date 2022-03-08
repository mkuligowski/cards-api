package com.mkuligowski.cardsapi.cards.adapters;

import com.mkuligowski.cardsapi.cards.AddCreditCardError;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface AddCreditCardErrorMapper {
    ResponseEntity<?> mapStatus(AddCreditCardError addCreditCardError);
}
