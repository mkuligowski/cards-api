package com.mkuligowski.cardsapi.reports.domainapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpiringCardsReportDto {
    private int year;
    private long count;
}
