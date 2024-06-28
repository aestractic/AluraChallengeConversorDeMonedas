package Main.models;

import Main.entity.Exchange;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteExchange {

    private static final String API_KEY = "5e69e8b55dadfa9bb8c69ffa";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public double convert(String fromCurrency, String toCurrency, double amount) {
        JsonObject rates = getExchangeRates(fromCurrency);
        double rate = getRateForCurrency(rates, toCurrency);
        return amount * rate;
    }

    private JsonObject getExchangeRates(String baseCurrency) {
        try {
            URL url = new URL(BASE_URL + baseCurrency);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonObject json = JsonParser.parseReader(new InputStreamReader((request.getInputStream()))).getAsJsonObject();
            return json.getAsJsonObject("conversion_rates");
        } catch (IOException e) {
            throw new RuntimeException("Error al obtener tasas de cambio", e);
        }
    }

    private double getRateForCurrency(JsonObject rates, String currency) {
        if (!rates.has(currency)) {
            throw new IllegalArgumentException("Moneda no soportada: " + currency);
        }
        return rates.get(currency).getAsDouble();
    }
}