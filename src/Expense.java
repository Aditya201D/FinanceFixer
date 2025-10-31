//creating an expense class with fields amount, category, date and description
// What's happening here:

// Serializable - allows saving objects to files (we'll use this later)
// LocalDate - modern Java date class (better than old Date class)
// Private fields with getters/setters - encapsulation (OOP principle)
// toString() - for debugging and display

import java.io.Serializable;
import java.time.LocalDate;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    public Expense(double amount, String category, LocalDate date, String description){
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    //Getters
    public double getAmount(){
        return amount;
    }
    public String getCategory(){
        return category;
    }
    public LocalDate getDate(){
        return date;
    }
    public String getDescription(){
        return description;
    }

    //Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    // For displaying in table (toString method)
    //creates table view
    @Override
    public String toString() {
        return String.format("%.2f | %s | %s | %s", amount, category, date, description);
    }
}
