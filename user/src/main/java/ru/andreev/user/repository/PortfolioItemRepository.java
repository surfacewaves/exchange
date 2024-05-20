package ru.andreev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.andreev.user.entity.PortfolioItem;

import java.util.UUID;

@Repository
public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, UUID>, JpaSpecificationExecutor<PortfolioItem> {

}
