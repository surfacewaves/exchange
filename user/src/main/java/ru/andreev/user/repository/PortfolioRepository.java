package ru.andreev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.andreev.user.entity.Portfolio;

import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID>, JpaSpecificationExecutor<Portfolio> {
    Portfolio getPortfolioByUserId(UUID id);
}
