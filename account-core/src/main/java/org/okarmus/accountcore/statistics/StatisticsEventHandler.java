package org.okarmus.accountcore.statistics;

import org.axonframework.eventhandling.EventHandler;
import org.okarmus.accountcore.core.event.AccountCreatedEvent;
import org.okarmus.accountcore.core.event.MoneyStoredEvent;
import org.okarmus.accountcore.core.event.MoneySubstractedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class StatisticsEventHandler {

    private final StatisticsRepository repository;

    @Autowired
    public StatisticsEventHandler(StatisticsRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    void on (AccountCreatedEvent event) {
        final Statistic statistic = findStatistic();
        statistic.accountCreated();
        repository.save(statistic);
    }

    @EventHandler
    void on(MoneyStoredEvent event) {
        final Statistic statistic = findStatistic();
        statistic.addMoney(event.value());
        repository.save(statistic);
    }

    @EventHandler
    void on(MoneySubstractedEvent event) {
        final Statistic statistic = findStatistic();
        statistic.substractMoney(event.value());
        repository.save(statistic);
    }

    private Statistic findStatistic() {
        final String today = LocalDate.now().toString();

        return repository.findByDate(today)
                .orElse(Statistic.builder().date(today).build());
    }

}
