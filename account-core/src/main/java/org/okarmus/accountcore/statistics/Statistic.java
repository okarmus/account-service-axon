package org.okarmus.accountcore.statistics;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Builder
@Accessors(fluent = true)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Setter
@Entity
class Statistic {

    @Id @Column
    String date;
    long moneyAdded;
    long moneyRemoved;
    long balance;
    long accountsCreated;
    long accountsDeactivated;

    void accountCreated() {
        this.accountsCreated++;
    }

    void accountDeactivated() {
        this.accountsDeactivated++;
    }

    void addMoney(long money) {      //TODO question is how it will work in multi threaded environment
        this.moneyAdded += money;
        this.balance += money;
    }

    void substractMoney(long money) {
        this.moneyRemoved += money;
        this.balance -= money;
    }
}
