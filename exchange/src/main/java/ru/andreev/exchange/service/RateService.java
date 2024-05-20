package ru.andreev.exchange.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andreev.exchange.dto.Portfolio.ActiveDTO;
import ru.andreev.exchange.dto.Portfolio.PortfolioItemsDTO;
import ru.andreev.exchange.dto.Rate.LatestRatesDTO;
import ru.andreev.exchange.dto.Rate.MonitoringRateDTO;
import ru.andreev.exchange.entity.Rate;
import ru.andreev.exchange.entity.RateRequest;
import ru.andreev.exchange.jms.producer.MessageProducer;
import ru.andreev.exchange.mapper.RateMapper;
import ru.andreev.exchange.repository.RateRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static ru.andreev.exchange.enums.RateRequestDictionaryCode.RATE_REQUEST_STATUS;
import static ru.andreev.exchange.enums.RateRequestDictionaryKey.FAILED;
import static ru.andreev.exchange.enums.RateRequestDictionaryKey.PROCESSED;

@Service
@RequiredArgsConstructor
public class RateService {
    private final RateRepository rateRepository;
    private final RateMapper rateMapper;
    private final RateRequestService rateRequestService;
    private final MessageProducer messageProducer;
    private final DictionaryService dictionaryService;

    public LatestRatesDTO getLatestRates(@Nullable String currencyCode) {
        List<MonitoringRateDTO> rates;

        if (currencyCode == null) {
            rates = rateMapper.mapListToPopularRateDTO(rateRepository.getLatestPopularRates());
        } else {
            List<Rate> ratesByCurrencyCode = rateRepository.getLatestRatesByCurrencyCode(currencyCode);

            if (ratesByCurrencyCode.isEmpty() || Duration.between(ratesByCurrencyCode.get(0).getDate(),
                    OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC)).toHours() >= 1) {
                createRequestAndSendToApi(currencyCode, null);
            }

            rates = rateMapper.mapListToPopularRateDTO(rateRepository.getLatestRatesByCurrencyCode(currencyCode));
        }

        return rateMapper.mapToLatestRatesDTO(rates.get(0).getDate(), rates);
    }

    public LatestRatesDTO getHistoricalRates(String currencyCode, LocalDate rateDate) throws InterruptedException {
        List<Rate> rates = rateRepository.getRatesByDateAndBaseCode(
                rateDate.atStartOfDay().atOffset(ZoneOffset.ofHours(0)), currencyCode);

        if (rates.isEmpty()) {
            createRequestAndSendToApi(currencyCode, rateDate);
        }

        return rateMapper.mapToLatestRatesDTO(rateDate.atStartOfDay().atOffset(ZoneOffset.ofHours(0)),
                rateMapper.mapListToPopularRateDTO(rateRepository.getRatesByDateAndBaseCode(
                        rateDate.atStartOfDay().atOffset(ZoneOffset.ofHours(0)), currencyCode)));
    }

    public void createRequestAndSendToApi(String currencyCode, @Nullable LocalDate rateDate) {
        RateRequest request = rateRequestService.createRequest(currencyCode, rateDate);
        sendRatesRequest(request);
    }

    private void sendRatesRequest(RateRequest request) {
        try {
            messageProducer.produce(request);
            rateRequestService.updateStatusById(request.getId(), dictionaryService.findDictionaryByCodeAndKey(
                    RATE_REQUEST_STATUS.name(), PROCESSED.name()));
        } catch (Exception e) {
            rateRequestService.updateStatusById(request.getId(), dictionaryService.findDictionaryByCodeAndKey(
                    RATE_REQUEST_STATUS.name(), FAILED.name()));
            System.out.println(e.getMessage());
        }
    }

    public Rate findLatestByBaseCodeAndConversionCode(String baseCode, String conversionCode) {
        return rateRepository.findLatestByBaseCodeAndConversionCode(baseCode, conversionCode).orElse(null);
    }

    public void saveRates(List<Rate> rates) {
        rateRepository.saveAll(rates);
    }

    public void deleteRates(List<Rate> rates) {
        rateRepository.deleteAll(rates);
    }

    public Double countTotal(PortfolioItemsDTO portfolioItemsDTO) {
        AtomicReference<Double> total = new AtomicReference<>(0.0);

        List<String> containerCodes = portfolioItemsDTO.getActives().stream()
                .map(ActiveDTO::getActiveCode)
                .toList();

        List<MonitoringRateDTO> rates = getLatestRates(portfolioItemsDTO.getBaseCode()).getRates().stream()
                .filter(r -> containerCodes.contains(r.getToCode()))
                .toList();

        rates.forEach(System.out::println);

        rates.forEach(r -> {
            portfolioItemsDTO.getActives()
                    .forEach(a -> {
                        if (r.getToCode().equals(a.getActiveCode())) {
                            total.updateAndGet(v -> v + (1 / r.getPrice()) * a.getActiveAmount());
                        }
                    });
        });

        return total.get();
    }
}
