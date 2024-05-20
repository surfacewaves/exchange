package ru.andreev.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.andreev.exchange.entity.Dictionary;
import ru.andreev.exchange.entity.RateRequest;

import java.util.List;
import java.util.UUID;

@Repository
public interface RateRequestRepository extends JpaRepository<RateRequest, UUID>, JpaSpecificationExecutor<RateRequest> {

    List<RateRequest> getRateRequestByType(Dictionary type);

    List<RateRequest> getRateRequestByStatus(Dictionary status);
}
