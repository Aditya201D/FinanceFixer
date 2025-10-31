// What's happening here:

// ArrayList<Expense> - stores all expenses
// CRUD methods - add, get, update, delete
// saveExpenses() & loadExpenses() - file I/O for persistence
// getSpendingByCategory() - uses HashMap to group expenses
// Budget checking methods
// Stream API for calculations (modern Java feature)

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Manages CRUD operations

public class ExpenseManager {
    private List<Expense> expenses;
    private double monthlyBudget;
    private static final String DATA_FILE = "expenses.dat";

    public ExpenseManager(){
        this.expenses = new ArrayList<>();
        this.monthlyBudget = 0.0;
        loadExpenses();     //this function will load the saved data from previous session when app starts
    }

    //CREATE operation / Add expense
    public void addExpense(Expense expense){
        expenses.add(expense);
        saveExpenses();     //this function will save the total expenses automatically after every create operation
    }

    //READ operation / Get all expenses
    public List<Expense> getAllExpenses(){
        return new ArrayList<>(expenses);
    }

    //UPDATE operation / update an expense
    public void updateExpense(int index, Expense newExpense){
        if (index>=0 && index < expenses.size()){
            expenses.set(index, newExpense);
            saveExpenses();
        }
    }

    //DELETE operation / delete an expense
    public void deleteExpense(int index){
        if (index>=0 && index < expenses.size()){
            expenses.remove(index);
            saveExpenses();
        }
    }

    //to calculate total expenses
    public double getTotalSpending() {
        return expenses.stream()
                      .mapToDouble(Expense::getAmount)
                      .sum();
    }

    //to get total expenses by each expense category
    public Map<String, Double> getSpendingByCategory() {
        Map<String, Double> categoryTotals = new HashMap<>();
        
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            categoryTotals.put(category, 
                categoryTotals.getOrDefault(category, 0.0) + expense.getAmount());
        }
        
        return categoryTotals;
    }

    //to calculate the total expenses by each month
    public double getCurrentMonthSpending() {
        LocalDate now = LocalDate.now();
        return expenses.stream()
                      .filter(e -> e.getDate().getMonth() == now.getMonth() 
                                && e.getDate().getYear() == now.getYear())
                      .mapToDouble(Expense::getAmount)
                      .sum();
    }

    // Budget management
    public void setMonthlyBudget(double budget) {
        this.monthlyBudget = budget;
        saveExpenses();
    }
    
    public double getMonthlyBudget() {
        return monthlyBudget;
    }
    
    public double getRemainingBudget() {
        return monthlyBudget - getCurrentMonthSpending();
    }
    
    // Check if over budget
    public boolean isOverBudget() {
        return getCurrentMonthSpending() > monthlyBudget;
    }
    
    // Save expenses to file
    private void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(expenses);
            oos.writeDouble(monthlyBudget);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    
    // Load expenses from file
    @SuppressWarnings("unchecked")
    private void loadExpenses() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No saved data found. Starting fresh.");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            expenses = (List<Expense>) ois.readObject();
            monthlyBudget = ois.readDouble();
            System.out.println("Data loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            expenses = new ArrayList<>();
        }
    }
    
    // Clear all data (useful for testing)
    public void clearAllData() {
        expenses.clear();
        monthlyBudget = 0.0;
        saveExpenses();
    }
}
