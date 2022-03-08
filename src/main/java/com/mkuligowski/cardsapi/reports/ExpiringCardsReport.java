package com.mkuligowski.cardsapi.reports;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Table(name = "expiring_cards_v")
public class ExpiringCardsReport {
    @Id
    private int expirationYear;
    @Column(name = "cards_count")
    private long count;

    public int getExpirationYear() {
        return expirationYear;
    }

    public long getCount() {
        return count;
    }


}
