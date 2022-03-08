package com.mkuligowski.cardsapi.reports.adapters;

import com.mkuligowski.cardsapi.reports.ExpiringCardsReportRepository;
import com.mkuligowski.cardsapi.reports.domainapi.ExpiringCardsReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ExpiringCardsController {

    private final ExpiringCardsReportRepository expiringCardsReportRepository;

    @GetMapping("/api/reports/expiring-cards")
    List<ExpiringCardsReportDto> fetchReport() {
        return expiringCardsReportRepository.findAll()
                .stream().map(expiringCardsReport -> new ExpiringCardsReportDto(expiringCardsReport.getExpirationYear(), expiringCardsReport.getCount()))
                .collect(Collectors.toList());
    }
}
