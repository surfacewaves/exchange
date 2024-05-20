package ru.andreev.exchange.jms.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.andreev.exchange.entity.RateRequest;
import ru.andreev.exchange.mapper.RateRequestMapper;
import ru.andreev.exchange.service.DictionaryService;
import ru.andreev.exchange.service.RateRequestService;

import static ru.andreev.exchange.enums.RateRequestDictionaryCode.RATE_REQUEST_STATUS;
import static ru.andreev.exchange.enums.RateRequestDictionaryKey.PROCESSED;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {
    private final Queue queue;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final RateRequestMapper rateRequestMapper;
    private final RateRequestService rateRequestService;
    private final DictionaryService dictionaryService;

    @Async
    public void produce(RateRequest request) throws Exception {
        try {
            TextMessage message = new ActiveMQTextMessage();
            message.setJMSCorrelationID(request.getId().toString());
            message.setText(objectMapper.writeValueAsString(rateRequestMapper.mapToRateRequestJmsDTO(request)));

            jmsTemplate.convertAndSend(queue, message);
            rateRequestService.updateStatusById(request.getId(), dictionaryService.findDictionaryByCodeAndKey(
                    RATE_REQUEST_STATUS.name(), PROCESSED.name()));

            Thread.sleep(2000);
            /*System.out.println("RATES is null: " + rateRequestService.findById(request.getId()).getRates().isEmpty());
            if (!rateRequestService.findById(request.getId()).getRates().isEmpty()) {
                rateRequestService.updateStatusById(request.getId(), dictionaryService.findDictionaryByCodeAndKey(
                        RATE_REQUEST_STATUS.name(), SUCCESSFUL.name()));
            } else {
                rateRequestService.updateStatusById(request.getId(), dictionaryService.findDictionaryByCodeAndKey(
                        RATE_REQUEST_STATUS.name(), FAILED.name()));
            }*/

        } catch (JMSException | JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }
}
