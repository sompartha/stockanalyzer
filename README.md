# 📈 Stock Analyzer (JavaFX)

A simple and elegant JavaFX desktop application to fetch and visualize real-time stock prices using the Alpha Vantage API.

---

## 🔧 Features

- 🔍 **Live Stock Price Fetching** – Enter a stock symbol (e.g., `TCS.NS`, `INFY.NS`) to get its current market price.
- 📊 **7-Day Line Chart** – Visualizes the stock’s closing price over the last 7 trading days.
- 💬 **User-Friendly Interface** – Built with JavaFX for a modern, responsive UI.

---

## 🛠️ Tech Stack

- **Java 17**
- **JavaFX 17.0.9**
- **Maven**
- **Gson** for JSON parsing
- **Alpha Vantage API** for financial data

---

## 🚀 How to Run Locally

> Make sure you have Java 17 and Maven installed.

```bash
# Clone the repository
git clone https://github.com/sompartha/stockanalyzer.git
cd stockanalyzer

# Run the app
mvn clean javafx:run
