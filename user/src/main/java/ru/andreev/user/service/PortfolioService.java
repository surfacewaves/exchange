package ru.andreev.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.andreev.user.dto.PortfolioDTO;
import ru.andreev.user.dto.PortfolioItemRequestDTO;
import ru.andreev.user.entity.Portfolio;
import ru.andreev.user.entity.PortfolioItem;
import ru.andreev.user.entity.Request;
import ru.andreev.user.entity.User;
import ru.andreev.user.mapper.PortfolioItemMapper;
import ru.andreev.user.mapper.PortfolioMapper;
import ru.andreev.user.repository.PortfolioRepository;
import ru.andreev.user.service.rest.ExchangeRestService;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final PortfolioItemMapper portfolioItemMapper;
    private final PortfolioItemService portfolioItemService;
    private final ExchangeRestService exchangeRestService;

    private Portfolio getPortfolioById(UUID id) {
        return portfolioRepository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("PortfolioMapper with ID = %s not found", id)
                ));
    }

    // TODO: не нужен?
    public PortfolioDTO getPortfolioDtoById(UUID id) {
        return portfolioMapper.mapToPortfolioDTO(getPortfolioById(id));
    }

    public PortfolioDTO getPortfolioDtoByUserId(UUID id) {
        return portfolioMapper.mapToPortfolioDTO(getPortfolioByUserId(id));
    }

    private Portfolio getPortfolioByUserId(UUID id) {
        return portfolioRepository.getPortfolioByUserId(id);
    }

    // TODO: реализовать нормальное сохранение в родительской сущности без portfolio.getItems().add(item);
    @Transactional
    public PortfolioDTO addActiveToPortfolio(UUID portfolioId, PortfolioItemRequestDTO request) {
        Portfolio portfolio = getPortfolioById(portfolioId);

        Optional<PortfolioItem> optionalItem = portfolio.getItems().stream()
                .filter(i -> i.getActiveName().equals(request.getActiveName()))
                .findFirst();

        PortfolioItem item = null;
        if (optionalItem.isEmpty()) {
            item = portfolioItemService.save(
                    portfolioItemMapper.createPortfolioItemFromRequest(request, portfolio));
        } else {
            item = optionalItem.get();
            item.setActiveAmount(item.getActiveAmount() + request.getActiveAmount());
            item.setLastUpdatedDatetime(OffsetDateTime.now());
            portfolioItemService.save(item);
        }

        portfolio.getItems().add(item);
        updateLastUpdatedDatetime(portfolio);

        return portfolioMapper.mapToPortfolioDTO(portfolio);
    }

    public PortfolioDTO countTotal(UUID userId) {
        Portfolio portfolio = getPortfolioByUserId(userId);

        Double total = exchangeRestService.countTotal(portfolioMapper.mapToPortfolioItemsDTO(portfolio));

        portfolio.setTotal(total);
        save(portfolio);

        return portfolioMapper.mapToPortfolioDTO(portfolio);
    }

    private void save(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public void subActiveFromPortfolio(User user, Request request, Double price) {
        Optional<PortfolioItem> optionalItem = user.getPortfolio().getItems().stream()
                .filter(i -> i.getActiveName().equals(request.getTargetCurrency()))
                .findFirst();

        if (optionalItem.isEmpty() || optionalItem.get().getActiveAmount() < price) {
            throw new RuntimeException(
                    String.format("You don't have %s %s", price, request.getActiveName())
            );
        } else {
            PortfolioItem item = optionalItem.get();
            item.setActiveAmount(item.getActiveAmount() - price);
            item.setLastUpdatedDatetime(OffsetDateTime.now());

            if (item.getActiveAmount() == 0) {
                portfolioItemService.delete(item);
            }

            portfolioItemService.save(item);
        }

        updateLastUpdatedDatetime(user.getPortfolio());
    }

    private void updateLastUpdatedDatetime(Portfolio portfolio) {
        portfolio.setLastUpdatedDatetime(OffsetDateTime.now());
        save(portfolio);
    }
}