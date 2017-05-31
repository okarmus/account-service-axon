package org.okarmus.accountcore.statistics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistic, String> {

    Optional<Statistic> findByDate(String date);

}
