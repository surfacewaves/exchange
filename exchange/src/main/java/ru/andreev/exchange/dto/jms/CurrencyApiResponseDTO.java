package ru.andreev.exchange.dto.jms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import ru.andreev.exchange.deserializer.IntegerDeserializer;
import ru.andreev.exchange.deserializer.StringDeserializer;

import java.util.Map;

@Data
public class CurrencyApiResponseDTO {

    @SerializedName("result")
    String result;

    @SerializedName("time_last_update_utc")
    String timeLastUpdateUtc;

    @SerializedName("base_code")
    String baseCode;

    @SerializedName("conversion_rates")
    Map<String, Double> conversionRates;

    @SerializedName("year")
    Integer year;

    @SerializedName("month")
    Integer month;

    @SerializedName("day")
    Integer day;

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDeserializer())
                .registerTypeAdapter(String.class, new StringDeserializer())
                .create();

        CurrencyApiResponseDTO obj = new CurrencyApiResponseDTO();
        System.out.println(gson.toJson(obj));
    }
}
