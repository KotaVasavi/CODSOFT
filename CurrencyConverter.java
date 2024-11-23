import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class CurrencyConverter {

    // Fetch exchange rate using HttpClient
    public static double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        String apiKey = "fca_live_xhjQlrB66rFcjPYkQISulBfifMDYyugDXpFayqTo"; 
        String urlStr = String.format("https://api.freecurrencyapi.com/v1/latest?apikey=%s&base_currency=%s", apiKey, baseCurrency);

        try {
            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlStr)).GET().build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Process the response to find the target currency rate
            String responseStr = response.body();

            String searchKey = "\"" + targetCurrency + "\":";
            int index = responseStr.indexOf(searchKey);
            if (index == -1) {
                throw new RuntimeException("Target currency not found in response.");
            }

            int start = index + searchKey.length();
            int end = responseStr.indexOf(",", start);
            if (end == -1) { // Handle the last element in the JSON object
                end = responseStr.indexOf("}", start);
            }
            String rateStr = responseStr.substring(start, end).trim();
            return Double.parseDouble(rateStr);
        } catch (Exception e) {
            System.out.println("Error fetching exchange rate: " + e.getMessage());
            return 0.0;
        }
    }

    // Fetch available currencies from the API response
    public static Set<String> getAvailableCurrencies(String baseCurrency) {
        String apiKey = "fca_live_xhjQlrB66rFcjPYkQISulBfifMDYyugDXpFayqTo";
        String urlStr = String.format("https://api.freecurrencyapi.com/v1/latest?apikey=%s&base_currency=%s", apiKey, baseCurrency);

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlStr)).GET().build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseStr = response.body();

            String dataKey = "\"data\":";
            int dataIndex = responseStr.indexOf(dataKey);
            if (dataIndex == -1) {
                throw new RuntimeException("Rates not found in API response.");
            }

            String ratesSection = responseStr.substring(dataIndex + dataKey.length(), responseStr.length() - 1);

            ratesSection = ratesSection.replaceAll("[{}\"]", "");
            String[] currencyPairs = ratesSection.split(",");

            Set<String> availableCurrencies = new HashSet<>();
            for (String currencyPair : currencyPairs) {
                String currencyCode = currencyPair.split(":")[0].trim();
                availableCurrencies.add(currencyCode);
            }

            return availableCurrencies;

        } catch (Exception e) {
            System.out.println("Error fetching available currencies: " + e.getMessage());
            return new HashSet<>();
        }
    }

    // Predefined currency symbols map
    public static Map<String, String> getCurrencySymbols() {
        Map<String, String> symbols = new HashMap<>();
        symbols.put("USD", "$");
        symbols.put("EUR", "eur");
        symbols.put("INR", "rs");
        symbols.put("GBP", "£");
        symbols.put("AUD", "A$");
        symbols.put("CAD", "C$");
        symbols.put("JPY", "¥");
        symbols.put("CHF", "CHF");
        symbols.put("CNY", "¥");
        symbols.put("NZD", "NZ$");
        symbols.put("SEK", "kr");
        symbols.put("NOK", "kr");
        symbols.put("DKK", "kr");
        symbols.put("ZAR", "R");
        symbols.put("BRL", "BRL");
        symbols.put("MXN", "MXN");
        symbols.put("THB", "THB");
        symbols.put("IDR", "IDR");
        symbols.put("HUF", "HUF");
        symbols.put("PHP", "PHP");
        symbols.put("TRY", "TRY");
        symbols.put("RUB", "RUB");
        symbols.put("HKD", "HKD");
        symbols.put("ISK", "ISK");
        symbols.put("PLN", "PLN");
        symbols.put("RON", "RON");
        symbols.put("SGD", "SGD");
        symbols.put("KRW", "KRW");
        symbols.put("HRK", "HRK");
        symbols.put("BGN", "BGN");
        symbols.put("MYR", "MYR");
        // Add more symbols as necessary
        return symbols;
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Fetch available currencies (dynamically from the API)
        Set<String> currencies = getAvailableCurrencies("USD");

        // Fetch currency symbols
        Map<String, String> currencySymbols = getCurrencySymbols();

        // Display available currencies and their symbols
        System.out.println("Available Currencies and their Symbols:");
        for (String currency : currencies) {
            // For each currency, get the symbol from the map, if available
            String symbol = currencySymbols.get(currency);
            if (symbol == null || symbol.isEmpty()) {
                // If the symbol is not found, use the currency code as a fallback
                symbol = currency;
            }
            System.out.println(currency + " - " + symbol);
        }

        // Input base currency
        System.out.print("Enter base currency: ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        // Input target currency
        System.out.print("Enter target currency: ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Check if the entered currencies are valid
        if (!currencies.contains(baseCurrency) || !currencies.contains(targetCurrency)) {
            System.out.println("Invalid currency code entered.");
            return;
        }

        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
        if (exchangeRate == 0.0) {
            System.out.println("Failed to fetch exchange rate. Please try again.");
            return;
        }

        double convertedAmount = amount * exchangeRate;
String targetCurrencySymbol;
if (currencySymbols.containsKey(targetCurrency)) {
    targetCurrencySymbol = currencySymbols.get(targetCurrency);
} else {
    targetCurrencySymbol = targetCurrency; 
}

System.out.printf("Converted Amount: %.2f %s%n", convertedAmount, targetCurrencySymbol);

        scanner.close();
    }
}
