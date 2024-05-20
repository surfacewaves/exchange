package ru.andreev.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andreev.user.entity.PortfolioItem;
import ru.andreev.user.repository.PortfolioItemRepository;

@Service
@RequiredArgsConstructor
public class PortfolioItemService {
    private final PortfolioItemRepository portfolioItemRepository;

    public PortfolioItem save(PortfolioItem item) {
        return portfolioItemRepository.save(item);
    }
    public void delete(PortfolioItem item) {
        portfolioItemRepository.delete(item);
    }
}