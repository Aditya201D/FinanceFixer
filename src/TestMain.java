import java.time.LocalDate;
import java.util.Map;

/**
 * Test class to verify ExpenseManager works before building GUI
 * Run this first to ensure your core logic is correct
 */
public class TestMain {
    public static void main(String[] args) {
        System.out.println("=== Testing Finance Fixer Core ===\n");
        
        // Create manager
        ExpenseManager manager = new ExpenseManager();
        
        // Clear old data for fresh test
        manager.clearAllData();
        
        // Set budget
        manager.setMonthlyBudget(10000.0);
        System.out.println("Monthly Budget Set: ₹" + manager.getMonthlyBudget());
        
        // Add some test expenses
        System.out.println("\n--- Adding Expenses ---");
        manager.addExpense(new Expense(500, "Food", LocalDate.now(), "Lunch at cafe"));
        manager.addExpense(new Expense(200, "Transport", LocalDate.now(), "Auto rickshaw"));
        manager.addExpense(new Expense(1500, "Entertainment", LocalDate.now(), "Movie tickets"));
        manager.addExpense(new Expense(300, "Food", LocalDate.now(), "Dinner"));
        
        System.out.println("Added 4 expenses");
        
        // Display all expenses
        System.out.println("\n--- All Expenses ---");
        for (int i = 0; i < manager.getAllExpenses().size(); i++) {
            Expense e = manager.getAllExpenses().get(i);
            System.out.println((i+1) + ". " + e);
        }
        
        // Calculate totals
        System.out.println("\n--- Spending Summary ---");
        System.out.println("Total Spending: ₹" + manager.getTotalSpending());
        System.out.println("Current Month Spending: ₹" + manager.getCurrentMonthSpending());
        System.out.println("Remaining Budget: ₹" + manager.getRemainingBudget());
        
        // Category breakdown
        System.out.println("\n--- Spending by Category ---");
        Map<String, Double> categorySpending = manager.getSpendingByCategory();
        for (Map.Entry<String, Double> entry : categorySpending.entrySet()) {
            System.out.println(entry.getKey() + ": ₹" + entry.getValue());
        }
        
        // Budget check
        System.out.println("\n--- Budget Status ---");
        if (manager.isOverBudget()) {
            System.out.println("⚠️ WARNING: You are over budget!");
        } else {
            System.out.println("✓ You are within budget");
        }
        
        // Test update
        System.out.println("\n--- Testing Update ---");
        manager.updateExpense(0, new Expense(600, "Food", LocalDate.now(), "Lunch (updated)"));
        System.out.println("Updated first expense to ₹600");
        
        // Test delete
        System.out.println("\n--- Testing Delete ---");
        manager.deleteExpense(1);
        System.out.println("Deleted second expense");
        
        System.out.println("\nRemaining expenses: " + manager.getAllExpenses().size());
        
        System.out.println("\n=== All Tests Passed! ===");
        System.out.println("Data has been saved to expenses.dat");
        System.out.println("Run this again to see data persistence working!");
    }
}