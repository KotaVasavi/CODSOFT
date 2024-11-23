import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CurrencyConverter {

    // Fetch exchange rate using HttpClient
    public static double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        String apiKey = "352efdca6719e80b5dba3bb7"; // Replace with your API key
        // Corrected URL with dynamic baseCurrency
        String urlStr = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", apiKey, baseCurrency);

        try {
            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlStr)).GET().build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Process the response to find targetCurrency
            String responseStr = response.body();
            // Example response format: {"base_code":"USD","rates":{"EUR":0.9243,"INR":82.4567,...}}

            // Search for the target currency rate
            String searchKey = "\"" + targetCurrency + "\":";
            int index = responseStr.indexOf(searchKey);
            if (index == -1) {
                throw new RuntimeException("Target currency not found in response.");
            }

            // Extract rate value
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

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input base currency
        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        // Input target currency
        System.out.print("Enter target currency (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Input amount
        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        // Fetch exchange rate
        double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
        if (exchangeRate == 0.0) {
            System.out.println("Failed to fetch exchange rate. Please try again.");
            return;
        }

        // Perform conversion
        double convertedAmount = amount * exchangeRate;

        // Display result
        System.out.printf("Converted Amount: %.2f %s%n", convertedAmount, targetCurrency);

        scanner.close();
    }
}
