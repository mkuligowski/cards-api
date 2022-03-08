package com.mkuligowski.cardsapi.cards.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MaskingUtilsTest {

    @Test
    void maskNumber_maskWithLastFourNumberProvided_cardMasked() {
        String maskedNumber = MaskingUtils.maskNumber("1111222233334444","xxxx-xxxx-xxxx-####");
        assertThat(maskedNumber).isEqualTo("xxxx-xxxx-xxxx-4444");
    }

    @Test
    void maskNumber_maskWithFirstNumberAndLastFourNumberProvided_cardMasked() {
        String maskedNumber = MaskingUtils.maskNumber("1111222233334444","#xxx-xxxx-xxxx-####");
        assertThat(maskedNumber).isEqualTo("1xxx-xxxx-xxxx-4444");
    }
}