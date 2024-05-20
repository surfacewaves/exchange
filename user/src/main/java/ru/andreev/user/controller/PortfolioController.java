package ru.andreev.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreev.user.dto.PortfolioDTO;
import ru.andreev.user.dto.PortfolioItemRequestDTO;
import ru.andreev.user.service.PortfolioService;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/portfolios")
@RequiredArgsConstructor
@CrossOrigin
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping("/{userId}")
    public ResponseEntity<PortfolioDTO> getPortfolioByUserId(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<>(portfolioService.getPortfolioDtoByUserId(userId), OK);
    }

    @PutMapping("/{portfolioId}")
    public ResponseEntity<PortfolioDTO> addActiveToPortfolio(@PathVariable("portfolioId") UUID portfolioId,
                                                             @RequestBody PortfolioItemRequestDTO request) {
        return new ResponseEntity<>(portfolioService.addActiveToPortfolio(portfolioId, request), OK);
    }

    @GetMapping("/{userId}/count-total")
    public ResponseEntity<PortfolioDTO> countTotal(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<>(portfolioService.countTotal(userId), OK);
    }
}
