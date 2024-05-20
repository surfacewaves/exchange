package ru.andreev.exchange.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.andreev.exchange.entity.Dictionary;
import ru.andreev.exchange.entity.RateRequest;
import ru.andreev.exchange.mapper.RateRequestMapper;
import ru.andreev.exchange.repository.RateRequestRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static ru.andreev.exchange.enums.RateRequestDictionaryCode.RATE_REQUEST_STATUS;
import static ru.andreev.exchange.enums.RateRequestDictionaryCode.RATE_REQUEST_TYPE;
import static ru.andreev.exchange.enums.RateRequestDictionaryKey.*;

@Service
@RequiredArgsConstructor
public class RateRequestService {
    private final RateRequestRepository rateRequestRepository;
    private final DictionaryService dictionaryService;
    private final RateRequestMapper rateRequestMapper;

    public RateRequest findById(UUID id) {
        return rateRequestRepository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("RateRequest with ID = %s not found", id)
                ));
    }

    public void updateStatusById(UUID id, Dictionary dictionary) {
        RateRequest entity = findById(id);
        entity.setStatus(dictionary);
        rateRequestRepository.save(entity);
    }

    public RateRequest createRequest(String currencyCode, @Nullable LocalDate date) {
        Dictionary status = dictionaryService.findDictionaryByCodeAndKey(RATE_REQUEST_STATUS.name(),
                CREATED.name());
        Dictionary type;
        if (date == null) {
            type = dictionaryService.findDictionaryByCodeAndKey(RATE_REQUEST_TYPE.name(),
                    LATEST.name());
        } else {
            type = dictionaryService.findDictionaryByCodeAndKey(RATE_REQUEST_TYPE.name(),
                    ON_DAY.name());
        }

        return rateRequestRepository.save(rateRequestMapper.createRequest(currencyCode, date, status, type));
    }

    public List<RateRequest> getOnDateRequests() {
        return rateRequestRepository.getRateRequestByType(
                dictionaryService.findDictionaryByCodeAndKey(RATE_REQUEST_TYPE.name(), ON_DAY.name()));
    }

    public void deleteRequest(RateRequest request) {
        rateRequestRepository.delete(request);
    }

    @Transactional
    public void deleteFailedRequests() {
        List<RateRequest> requests = getFailedRateRequest();

        if (!requests.isEmpty()) {
            requests.forEach(r -> deleteRequest(r));
        }
    }

    private List<RateRequest> getFailedRateRequest() {
        return rateRequestRepository.getRateRequestByStatus(
                dictionaryService.findDictionaryByCodeAndKey(RATE_REQUEST_STATUS.name(), FAILED.name()));
    }
}
