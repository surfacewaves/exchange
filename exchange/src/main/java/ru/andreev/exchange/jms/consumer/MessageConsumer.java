package ru.andreev.exchange.jms.consumer;

import com.google.gson.Gson;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.andreev.exchange.dto.jms.CurrencyApiResponseDTO;
import ru.andreev.exchange.entity.Rate;
import ru.andreev.exchange.entity.RateRequest;
import ru.andreev.exchange.service.DictionaryService;
import ru.andreev.exchange.service.RateRequestService;
import ru.andreev.exchange.service.RateService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ru.andreev.exchange.enums.RateRequestDictionaryCode.RATE_REQUEST_STATUS;
import static ru.andreev.exchange.enums.RateRequestDictionaryKey.LATEST;
import static ru.andreev.exchange.enums.RateRequestDictionaryKey.SUCCESSFUL;

@Component
@EnableJms
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {
    private final Gson gson;
    private final RateRequestService rateRequestService;
    private final RateService rateService;
    private final DictionaryService dictionaryService;

    @JmsListener(destination = "${queue.response-queue}")
    public void listen(Message message) {
        try {
            CurrencyApiResponseDTO currencyApiResponseDTO = gson.fromJson(((TextMessage) message).getText(),
                    CurrencyApiResponseDTO.class);

            List<Rate> rates = new ArrayList<>();
            RateRequest rateRequest = rateRequestService.findById(UUID.fromString(message.getJMSCorrelationID()));
            for (Map.Entry<String, Double> entry : currencyApiResponseDTO.getConversionRates().entrySet()) {
                Rate rate = getRate(entry, currencyApiResponseDTO, rateRequest);

                rates.add(rate);
            }

            rateService.saveRates(rates);

            rateRequestService.updateStatusById(UUID.fromString(message.getJMSCorrelationID()),
                    dictionaryService.findDictionaryByCodeAndKey(RATE_REQUEST_STATUS.name(), SUCCESSFUL.name()));
        } catch (JMSException e) {
            log.error(e.getMessage());
        }
    }

    private Rate getRate(Map.Entry<String, Double> entry, CurrencyApiResponseDTO currencyApiResponseDTO,
                         RateRequest rateRequest) {
        Rate rate = new Rate();
        if (currencyApiResponseDTO.getTimeLastUpdateUtc() != null) {
            rate.setDate(convertDate(currencyApiResponseDTO.getTimeLastUpdateUtc()));

            Rate latestRate = rateService.findLatestByBaseCodeAndConversionCode(currencyApiResponseDTO.getBaseCode(),
                    entry.getKey());

            if (latestRate != null) {
                if (latestRate.getRateRequest().getType().getKey().equals(LATEST.name())) {
                    if (entry.getValue() > latestRate.getMaxValueForDay()) {
                        rate.setMaxValueForDay(entry.getValue());
                    } else {
                        rate.setMaxValueForDay(latestRate.getMaxValueForDay());
                    }

                    if (entry.getValue() < latestRate.getMinValueForDay()) {
                        rate.setMinValueForDay(entry.getValue());
                    } else {
                        rate.setMinValueForDay(latestRate.getMinValueForDay());
                    }

                    rate.setDifferenceWithPrevious(entry.getValue() - latestRate.getConversionRate());
                }
            } else {
                rate.setMaxValueForDay(entry.getValue());
                rate.setMinValueForDay(entry.getValue());
                rate.setDifferenceWithPrevious(entry.getValue());
            }
        } else {
            rate.setDate(OffsetDateTime.of(currencyApiResponseDTO.getYear(),
                    currencyApiResponseDTO.getMonth(), currencyApiResponseDTO.getDay(),
                    0, 0, 0, 0, ZoneOffset.UTC));
        }
        rate.setBaseCode(currencyApiResponseDTO.getBaseCode());
        rate.setConversionCode(entry.getKey());
        rate.setConversionRate(entry.getValue());
        rate.setRateRequest(rateRequest);
        return rate;
    }

    private static OffsetDateTime convertDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z",
                Locale.ENGLISH);
        return OffsetDateTime.parse(dateString, formatter);
    }
}
