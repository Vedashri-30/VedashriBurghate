import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {

    // Method to get real-time exchange rate from an API
    private static double getExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        // API endpoint for fetching exchange rates
        String urlStr = "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        // Parse the JSON response
        JSONObject json = new JSONObject(content.toString());
        return json.getJSONObject("rates").getDouble(targetCurrency);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Currency Selection
        System.out.println("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.println("Enter target currency (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // 2. Amount Input
        System.out.println("Enter the amount to convert: ");
        double amount = scanner.nextDouble();

        try {
            // 3. Fetch real-time exchange rate
            double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

            // 4. Currency Conversion
            double convertedAmount = amount * exchangeRate;

            // 5. Display Result
            System.out.printf("%.2f %s is equal to %.2f %s (Exchange Rate: %.4f)\n", 
                               amount, baseCurrency, convertedAmount, targetCurrency, exchangeRate);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}