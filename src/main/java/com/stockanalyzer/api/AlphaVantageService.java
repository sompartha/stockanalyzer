package com.stockanalyzer.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AlphaVantageService {

    private static final String API_KEY = "92VE4FS9NJHQ4EY5"; // Replace if needed
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    public static String getStockPrice(String symbol) {
        try {
            String urlString = BASE_URL
                    + "?function=GLOBAL_QUOTE"
                    + "&symbol=" + symbol
                    + "&apikey=" + API_KEY;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();

        } catch (Exception e) {
            return "Error fetching data: " + e.getMessage();
        }
    }

    public static String getDailyPrices(String symbol) {
        try {
            String urlString = BASE_URL
                    + "?function=TIME_SERIES_DAILY"
                    + "&symbol=" + symbol
                    + "&apikey=" + API_KEY;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();

        } catch (Exception e) {
            return "Error fetching chart data: " + e.getMessage();
        }
    }
}
