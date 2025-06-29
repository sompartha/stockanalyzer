package com.stockanalyzer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stockanalyzer.api.AlphaVantageService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dashboard extends Application {

    private Label resultLabel;
    private VBox rootLayout;

    @Override
    public void start(Stage primaryStage) {
        TextField stockInput = new TextField();
        stockInput.setPromptText("Enter stock symbol (e.g. TCS.NS)");

        Button fetchButton = new Button("Get Price + Chart");
        resultLabel = new Label("Enter a stock symbol and click the button.");

        fetchButton.setOnAction(e -> {
            String symbol = stockInput.getText().trim();
            if (!symbol.isEmpty()) {
                fetchAndDisplayStockPrice(symbol);
                LineChart<String, Number> chart = createChart(symbol);
                if (rootLayout.getChildren().size() > 3) {
                    rootLayout.getChildren().remove(3);
                }
                rootLayout.getChildren().add(chart);
            } else {
                resultLabel.setText("Please enter a valid stock symbol.");
            }
        });

        rootLayout = new VBox(10, stockInput, fetchButton, resultLabel);
        rootLayout.setPadding(new Insets(20));

        Scene scene = new Scene(rootLayout, 600, 450);
        primaryStage.setTitle("Stock Analyzer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchAndDisplayStockPrice(String symbol) {
        resultLabel.setText("Fetching price for " + symbol + "...");
        String json = AlphaVantageService.getStockPrice(symbol);

        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            if (!jsonObject.has("Global Quote") ||
                jsonObject.getAsJsonObject("Global Quote").size() == 0) {
                resultLabel.setText("No data found for " + symbol + ". Try a valid symbol.");
                return;
            }

            JsonObject globalQuote = jsonObject.getAsJsonObject("Global Quote");
            String price = globalQuote.get("05. price").getAsString();
            resultLabel.setText(symbol + " Current Price: ₹" + price);
        } catch (Exception e) {
            resultLabel.setText("Error fetching or parsing price data.");
            System.err.println("Parsing error: " + e.getMessage());
        }
    }

    private LineChart<String, Number> createChart(String symbol) {
        String json = AlphaVantageService.getDailyPrices(symbol);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Close Price (₹)");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(symbol + " - Last 7 Days");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(symbol);

        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();

            if (!root.has("Time Series (Daily)")) {
                System.out.println("No chart data found for " + symbol);
                return lineChart;
            }

            JsonObject timeSeries = root.getAsJsonObject("Time Series (Daily)");

            int count = 0;
            for (String date : timeSeries.keySet()) {
                String close = timeSeries.getAsJsonObject(date).get("4. close").getAsString();
                series.getData().add(new XYChart.Data<>(date, Double.parseDouble(close)));
                count++;
                if (count == 7) break;
            }

            lineChart.getData().add(series);
        } catch (Exception e) {
            System.out.println("Error building chart: " + e.getMessage());
        }

        return lineChart;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
