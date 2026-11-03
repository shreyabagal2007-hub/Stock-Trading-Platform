
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    private double balance;
    private HashMap<String, Integer> ownedStocks;
    private ArrayList<String> transactionHistory;
    private HashMap<String, Double> investedAmounts;
    private double totalProfitLoss;

    // Constructor
    public Portfolio(double initialBalance) {
        balance = initialBalance;
        ownedStocks = new HashMap<>();
        transactionHistory = new ArrayList<>();
        investedAmounts = new HashMap<>();
        totalProfitLoss = 0.0;
    }

    // Buy Stock
    public void buyStock(Stock stock, int quantity) {

        double totalCost = stock.getPrice() * quantity;

        if (quantity <= 0) {
            System.out.println("Please enter a valid quantity.");
        } else if (totalCost > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= totalCost;

            ownedStocks.put(
                stock.getSymbol(),
                ownedStocks.getOrDefault(stock.getSymbol(), 0) + quantity
            );

            investedAmounts.put(
                stock.getSymbol(),
                investedAmounts.getOrDefault(stock.getSymbol(), 0.0) + totalCost
            );

            transactionHistory.add(
                "BOUGHT " + quantity + " shares of " +
                stock.getSymbol() + " at Rs. " + stock.getPrice()
            );

            System.out.println("Successfully bought " + quantity +
                    " shares of " + stock.getSymbol());
        }
    }

    // Sell Stock
    public void sellStock(Stock stock, int quantity) {

        int currentQuantity =
            ownedStocks.getOrDefault(stock.getSymbol(), 0);

        if (quantity <= 0) {
            System.out.println("Please enter a valid quantity.");
        } else if (quantity > currentQuantity) {
            System.out.println("You do not own enough shares to sell.");
        } else {

            int remainingQuantity = currentQuantity - quantity;

            double averageBuyPrice =
                investedAmounts.get(stock.getSymbol()) / currentQuantity;

            double profitLoss =
                (stock.getPrice() - averageBuyPrice) * quantity;

            totalProfitLoss += profitLoss;

            double remainingInvestment =
                investedAmounts.get(stock.getSymbol()) -
                (averageBuyPrice * quantity);

            if (remainingQuantity > 0) {
                investedAmounts.put(stock.getSymbol(), remainingInvestment);
            } else {
                investedAmounts.remove(stock.getSymbol());
            }

            balance += stock.getPrice() * quantity;

            if (remainingQuantity == 0) {
                ownedStocks.remove(stock.getSymbol());
            } else {
                ownedStocks.put(stock.getSymbol(), remainingQuantity);
            }

            transactionHistory.add(
                "SOLD " + quantity + " shares of " +
                stock.getSymbol() + " at Rs. " + stock.getPrice()
            );

            System.out.println("Successfully sold " + quantity +
                    " shares of " + stock.getSymbol());
        }
    }

    // Display Portfolio
    public void displayPortfolio() {

        System.out.println("\n----- YOUR PORTFOLIO -----");

        if (ownedStocks.isEmpty()) {
            System.out.println("You do not own any stocks yet.");
        } else {
            for (Map.Entry<String, Integer> entry : ownedStocks.entrySet()) {
                System.out.println(
                    "Stock: " + entry.getKey() +
                    " | Quantity: " + entry.getValue()
                );
            }
        }
    }

    // Display Balance
    public void displayBalance() {
        System.out.printf("Available Balance: Rs. %.2f%n", balance);
    }

    // Display Transaction History
    public void displayTransactionHistory() {

        System.out.println("\n----- TRANSACTION HISTORY -----");

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (int i = 0; i < transactionHistory.size(); i++) {
                System.out.println((i + 1) + ". " +
                        transactionHistory.get(i));
            }
        }
    }

    // Display Total Portfolio Value
    public void displayPortfolioValue(ArrayList<Stock> marketStocks) {

        double totalValue = 0;

        for (Map.Entry<String, Integer> entry : ownedStocks.entrySet()) {
            for (Stock stock : marketStocks) {

                if (stock.getSymbol().equals(entry.getKey())) {
                    totalValue += stock.getPrice() * entry.getValue();
                }
            }
        }

        System.out.printf("Total Portfolio Value: Rs. %.2f%n", totalValue);
    }

    // Display Profit and Loss
    public void displayProfitLoss() {

        System.out.printf(
            "Realized Profit/Loss: Rs. %.2f%n",
            totalProfitLoss
        );
    }
}
