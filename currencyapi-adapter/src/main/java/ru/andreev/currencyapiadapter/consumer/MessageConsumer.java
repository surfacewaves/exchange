package ru.andreev.currencyapiadapter.consumer;

import com.google.gson.Gson;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.andreev.currencyapiadapter.dto.CurrencyApiResponseDTO;
import ru.andreev.currencyapiadapter.dto.RateRequestJmsDTO;
import ru.andreev.currencyapiadapter.service.CurrencyApiService;

@Component
@EnableJms
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {
    private final Queue queue;
    private final JmsTemplate jmsTemplate;
    private final CurrencyApiService currencyApiService;
    private final Gson gson;

    @JmsListener(destination = "${queue.request-queue}")
    public void listener(Message message) {
        try {
            log.info("CORRELATION_ID: " + message.getJMSCorrelationID());
            log.info("MESSAGE FROM: " + ((TextMessage) message).getText());

            RateRequestJmsDTO rateRequestJmsDTO = gson.fromJson(((TextMessage) message).getText(),
                    RateRequestJmsDTO.class);

            CurrencyApiResponseDTO currencyApiResponseDTO = null;
            switch (rateRequestJmsDTO.getType()) {
                case "ON_DAY" -> {
                    String formattedRateDate = rateRequestJmsDTO.getRateDate().replace('-', '/');
                    currencyApiResponseDTO = currencyApiService.getRatesOnRateDate(rateRequestJmsDTO.getCurrencyCode(),
                            formattedRateDate);
                }
                case "LATEST" -> {
                    currencyApiResponseDTO = currencyApiService.getLatestRates(rateRequestJmsDTO.getCurrencyCode());
                }
            }

            TextMessage msg = new ActiveMQTextMessage();
            msg.setJMSCorrelationID(message.getJMSCorrelationID());
            msg.setText(gson.toJson(currencyApiResponseDTO));

            log.info("CORRELATION_ID: " + msg.getJMSCorrelationID());
            log.info("MESSAGE TO: " + msg.getText());

            jmsTemplate.convertAndSend(queue, msg);
        } catch (JMSException e) {
            log.error(e.getMessage());
        }
    }
}
