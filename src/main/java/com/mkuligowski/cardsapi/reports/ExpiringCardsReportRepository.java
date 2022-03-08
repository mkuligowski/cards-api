package com.mkuligowski.cardsapi.reports;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface ExpiringCardsReportRepository extends Repository<ExpiringCardsReport, Integer> {
    List<ExpiringCardsReport> findAll();
}
