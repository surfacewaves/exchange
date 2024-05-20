package ru.andreev.exchange.shedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.exchange.entity.RateRequest;
import ru.andreev.exchange.service.RateRequestService;
import ru.andreev.exchange.service.RateService;

import java.util.List;

import static ru.andreev.exchange.enums.RateRequestDictionaryKey.FAILED;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final RateService rateService;
    private final RateRequestService rateRequestService;

    @Scheduled(cron = "${schedule.task.get-latest-rub-rates}")
    @SchedulerLock(name = "expireGetLatestRubRates")
    public void expireGetLatestRubRates() throws InterruptedException {
        Thread.sleep(5000);
        log.debug("Started task: expireGetLatestRubRates.");
        rateService.createRequestAndSendToApi("RUB", null);
    }

    @Scheduled(cron = "${schedule.task.delete-failed-requests}")
    @SchedulerLock(name = "expireFailRequests")
    public void expireFailedRequests() {
        log.debug("Started task: expireFailRequests.");
        rateRequestService.deleteFailedRequests();
    }

    @Scheduled(cron = "${schedule.task.delete-on-date-rates}")
    @SchedulerLock(name = "expiredDeleteOnDateRates")
    @Transactional
    public void expiredDeleteOnDateRates() {
        log.debug("Started task: expiredDeleteOnDateRates.");

        List<RateRequest> requests = rateRequestService.getOnDateRequests();
        if (!requests.isEmpty()) {
            requests.forEach(r -> {
                if (r.getStatus().getKey().equals(FAILED.name())) {
                    rateService.deleteRates(r.getRates());
                }

                rateRequestService.deleteRequest(r);
            });
        }
    }
}
