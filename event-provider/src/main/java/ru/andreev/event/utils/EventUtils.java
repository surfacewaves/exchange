package ru.andreev.event.utils;

import lombok.experimental.UtilityClass;
import org.keycloak.events.Event;

import java.util.Map;

@UtilityClass
public class EventUtils {

    public static String toString(Event event) {
        StringBuilder sb = new StringBuilder();

        sb.append("type=").append(event.getType());
        sb.append(", realmId=").append(event.getRealmId());
        sb.append(", clientId=").append(event.getClientId());
        sb.append(", userId=").append(event.getUserId());
        sb.append(", ipAddress=").append(event.getIpAddress());

        if (event.getError() != null) {
            sb.append(", error=").append(event.getError());
        }

        if (event.getDetails() != null) {
            for (Map.Entry<String, String> e : event.getDetails().entrySet()) {
                sb.append(", ").append(e.getKey());
                if (e.getValue() == null || e.getValue().indexOf(' ') == -1) {
                    sb.append("=").append(e.getValue());
                } else {
                    sb.append("='").append(e.getValue()).append("'");
                }
            }
        }
        return sb.toString();
    }
}
