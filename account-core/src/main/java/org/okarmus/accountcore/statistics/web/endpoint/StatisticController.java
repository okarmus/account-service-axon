package org.okarmus.accountcore.statistics.web.endpoint;

import org.okarmus.accountcore.statistics.Statistic;
import org.okarmus.accountcore.statistics.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/statistic")
class StatisticController {

    private final StatisticsRepository statisticsRepository;

    @Autowired
    StatisticController(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @RequestMapping("/{date}")
    ResponseEntity<Statistic> findStatisticsForDate(@PathVariable String date) {
        return statisticsRepository.findByDate(date)
                .map(statistic -> {
                    System.out.println(statistic);
                    return new ResponseEntity<>(statistic, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

}
