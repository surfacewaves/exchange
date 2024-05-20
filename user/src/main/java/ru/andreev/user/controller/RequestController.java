package ru.andreev.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreev.user.dto.CreateRequestDTO;
import ru.andreev.user.dto.DoubleDTO;
import ru.andreev.user.dto.RequestDTO;
import ru.andreev.user.dto.SuccessBoughtRequestDTO;
import ru.andreev.user.service.RequestService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/requests")
@RequiredArgsConstructor
@CrossOrigin
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/{requestId}")
    public ResponseEntity<RequestDTO> getRequestById(@PathVariable("requestId") UUID requestId) {
        return new ResponseEntity<>(requestService.getRequestDtoById(requestId), OK);
    }

    @PostMapping
    public ResponseEntity<RequestDTO> createRequest(@RequestBody CreateRequestDTO request,
                                                    @RequestHeader("sub") UUID userId) {
        return new ResponseEntity<>(requestService.createRequest(request, userId), OK);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<SuccessBoughtRequestDTO> checkout(@PathVariable("requestId") UUID requestId,
                                                            @RequestBody DoubleDTO amount,
                                                            @RequestHeader("sub") UUID userId) {
        return new ResponseEntity<>(requestService.checkout(requestId, amount.getValue(), userId), OK);
    }

    @GetMapping
    public ResponseEntity<List<RequestDTO>> getRequests() {
        return new ResponseEntity<>(requestService.getActiveRequests(), OK);
    }
}
