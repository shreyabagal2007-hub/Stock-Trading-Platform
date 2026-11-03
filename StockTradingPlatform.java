import java.util.ArrayList;
import java.util.Scanner;

public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create available stocks
        ArrayList<Stock> marketStocks = new ArrayList<>();

        marketStocks.add(new Stock("TCS", "Tata Consultancy Services", 3500.00));
        marketStocks.add(new Stock("INFY", "Infosys", 1500.00));
        marketStocks.add(new Stock("RELIANCE", "Reliance Industries", 2800.00));

        // Create user portfolio with starting balance
        Portfolio portfolio = new Portfolio(100000.00);

        boolean running = true;

        System.out.println("======================================");
        System.out.println("      WELCOME TO STOCK TRADING APP");
        System.out.println("======================================");

        while (running) {

            // Display menu
            System.out.println("\n----------- MENU -----------");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Balance");
            System.out.println("6. Exit");
            System.out.println("7. View Transaction History");
            System.out.println("8. View Total Portfolio Value");
            System.out.println("9. View Profit/Loss");
            
            System.out.println("----------------------------");

            System.out.print("Enter your choice: ");
            if (!scanner.hasNextInt()) {
    System.out.println("Invalid input! Please enter a number.");
    scanner.next();
    continue;
}

int choice = scanner.nextInt();
            

            switch (choice) {

                case 1:
                    System.out.println("\n----- AVAILABLE STOCKS -----");

                    for (Stock stock : marketStocks) {
                        stock.displayStock();
                    }
                    break;

                case 2:
                    buyStock(scanner, marketStocks, portfolio);
                    break;

                case 3:
                    sellStock(scanner, marketStocks, portfolio);
                    break;

                case 4:
                    portfolio.displayPortfolio();
                    break;

                case 5:
                    portfolio.displayBalance();
                    break;
                
                case 7:
                    portfolio.displayTransactionHistory();
                    break;
                case 6:
                    running = false;
                    System.out.println("\nThank you for using Stock Trading Platform!");
                    break;
                    
                case 8:
                    portfolio.displayPortfolioValue(marketStocks);
                    break;

                case 9:
                    portfolio.displayProfitLoss();
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }

    // Method to buy stock
    public static void buyStock(Scanner scanner,
                                ArrayList<Stock> marketStocks,
                                Portfolio portfolio) {

        System.out.println("\n----- BUY STOCK -----");

        System.out.print("Enter stock symbol (TCS / INFY / RELIANCE): ");
        String symbol = scanner.next().toUpperCase();

        Stock selectedStock = findStock(symbol, marketStocks);

        if (selectedStock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        if (!scanner.hasNextInt()) {
    System.out.println("Invalid quantity!");
    scanner.next();
    return;
}

int quantity = scanner.nextInt();
    
        portfolio.buyStock(selectedStock, quantity);
    }

    // Method to sell stock
    public static void sellStock(Scanner scanner,
                                 ArrayList<Stock> marketStocks,
                                 Portfolio portfolio) {

        System.out.println("\n----- SELL STOCK -----");

        System.out.print("Enter stock symbol (TCS / INFY / RELIANCE): ");
        String symbol = scanner.next().toUpperCase();

        Stock selectedStock = findStock(symbol, marketStocks);

        if (selectedStock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        portfolio.sellStock(selectedStock, quantity);
    }

    // Find stock using symbol
    public static Stock findStock(String symbol,
                                  ArrayList<Stock> marketStocks) {

        for (Stock stock : marketStocks) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }

        return null;
    }
}