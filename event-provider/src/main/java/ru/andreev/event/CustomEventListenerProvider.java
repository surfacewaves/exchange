package ru.andreev.event;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import ru.andreev.event.utils.EventUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.util.Arrays;

import static org.keycloak.events.EventType.REGISTER;

@Slf4j
@NoArgsConstructor
public class CustomEventListenerProvider implements EventListenerProvider {

    @Override
    public void onEvent(Event event) {
        if (event.getType() == REGISTER) {

            String text = event.getUserId() + "/" + event.getDetails().get("username") + "/" +
                    event.getDetails().get("first_name") + "/" + event.getDetails().get("last_name") + "/" +
                    event.getDetails().get("email") + "/" + OffsetDateTime.now();

            log.info("information about registered user: " + text);

            HttpClient client = HttpClient.newHttpClient();
            String url = "http://localhost:8012/v1/users";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/text")
                    .header("Access-Control-Allow-Origin", "*") // Разрешаем доступ со всех источников
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT") // Разрешаем методы HTTP
                    .header("Access-Control-Allow-Headers", "Content-Type, Authorization") // Разрешаем заголовки запроса
                    .POST(HttpRequest.BodyPublishers.ofString(text))
                    .build();

            try {
                client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                log.error(e.getMessage());
                log.error(String.valueOf(e.getCause()));
                log.error(Arrays.toString(e.getStackTrace()));
            }
        }
        log.info("hihi");
        log.info("Caught event {}", EventUtils.toString(event));
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
    }

    @Override
    public void close() {
    }
}