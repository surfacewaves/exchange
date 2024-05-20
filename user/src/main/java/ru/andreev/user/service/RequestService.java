package ru.andreev.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.andreev.user.dto.CreateRequestDTO;
import ru.andreev.user.dto.PortfolioItemRequestDTO;
import ru.andreev.user.dto.RequestDTO;
import ru.andreev.user.dto.SuccessBoughtRequestDTO;
import ru.andreev.user.entity.Dictionary;
import ru.andreev.user.entity.Request;
import ru.andreev.user.entity.User;
import ru.andreev.user.mapper.RequestMapper;
import ru.andreev.user.repository.RequestRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static ru.andreev.user.enums.RequestDictionaryCode.REQUEST_STATUS;
import static ru.andreev.user.enums.RequestDictionaryKey.*;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserService userService;
    private final DictionaryService dictionaryService;
    private final PortfolioService portfolioService;

    private Request getRequestById(UUID id) {
        return requestRepository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("Request with ID = %s not found", id)
                ));
    }

    public RequestDTO getRequestDtoById(UUID id) {
        return requestMapper.mapToRequestDTO(getRequestById((id)));
    }

    @Transactional
    public RequestDTO createRequest(CreateRequestDTO newRequest, UUID userId) {
        User user = userService.getUserById(userId);
        Dictionary dictionary = dictionaryService.findDictionaryByCodeAndKey(REQUEST_STATUS.name(), CREATED.name());

        Request request = save(requestMapper.createRequest(newRequest, user, dictionary));

        return requestMapper.mapToRequestDTO(request);
    }

    private Request save(Request request) {
        return requestRepository.save(request);
    }

    @Transactional
    public SuccessBoughtRequestDTO checkout(UUID requestId, Double amount, UUID userId) {
        Request request = getRequestById(requestId);
        User user = userService.getUserById(userId);

        portfolioService.subActiveFromPortfolio(user, request, amount * request.getPrice());

        PortfolioItemRequestDTO item = new PortfolioItemRequestDTO();
        item.setActiveAmount(amount);
        item.setActiveName(request.getActiveName());
        portfolioService.addActiveToPortfolio(user.getPortfolio().getId(), item);

        PortfolioItemRequestDTO profit = new PortfolioItemRequestDTO();
        profit.setActiveAmount(amount * request.getPrice());
        profit.setActiveName(request.getTargetCurrency());
        portfolioService.addActiveToPortfolio(request.getCreatedUser().getPortfolio().getId(), profit);

        request.setCurrentAmount(request.getCurrentAmount() - amount);
        if (request.getCurrentAmount() == 0) {
            Dictionary status = dictionaryService.findDictionaryByCodeAndKey(REQUEST_STATUS.name(), COMPLETED.name());
            updateStatus(request, status);
        }
        addBoughtUsersToRequest(request, List.of(user));


        SuccessBoughtRequestDTO successBoughtRequestDTO = new SuccessBoughtRequestDTO();
        successBoughtRequestDTO.setActiveName(request.getActiveName());
        successBoughtRequestDTO.setAmount(amount * request.getPrice());
        return successBoughtRequestDTO;
    }

    private void addBoughtUsersToRequest(Request request, List<User> users) {
        for (User user : users) {
            request.getBoughtUsers().add(user);
        }
        updateLastUpdatedDatetime(request);
        save(request);
    }

    private void updateLastUpdatedDatetime(Request request) {
        request.setLastUpdatedDatetime(OffsetDateTime.now());
    }

    private void updateStatus(Request request, Dictionary dictionary) {
        request.setStatus(dictionary);
        updateLastUpdatedDatetime(request);
        save(request);
    }

    public List<RequestDTO> getActiveRequests() {
        return requestMapper.mapToListRequestDTO(requestRepository.getRequestsByStatus(
                dictionaryService.findDictionaryByCodeAndKey(REQUEST_STATUS.name(), ACTIVE.name())));
    }
}