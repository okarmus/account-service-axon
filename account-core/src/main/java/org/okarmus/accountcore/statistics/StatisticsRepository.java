package org.okarmus.accountcore.statistics;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StatisticsRepository extends MongoRepository<Statistic, String> {

    Optional<Statistic> findByDate(String date);

}
