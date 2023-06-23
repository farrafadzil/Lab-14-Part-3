package server.controller;

import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class CustomerDataManager {
    private List<Customer> customers;

    public CustomerDataManager() {
        customers = new ArrayList<>();
        initializeSampleCustomers();
        this.customers = new ArrayList<>();
        this.loadCustomers();
    }

    private void initializeSampleCustomers() {
        // Method 1: Creates a list of sample customer data
        
        // Create 10 sample customers
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer();
            customer.setCustomerId(i);
            customer.setName("Customer " + i);
            customers.add(customer);
        }
    }

    public Customer searchCustomerByName(String name) {
        // Method 2: Searches a customer based on name
        
        for (Customer customer : customers) {
            if (customer.getName().toLowerCase().contains(name.toLowerCase())) {
                return customer;
            }
        }
        
        return null; // Customer not found
    }

    public Customer searchCustomerById(int id) {
        // Method 3: Searches a customer based on ID
        
        for (Customer customer : customers) {
            if (customer.getCustomerId() == id) {
                return customer;
            }
        }
        
        return null; // Customer not found
    }

    public List<Customer> getAllCustomers() {
        // Method 4: Returns a list of customers
        return customers;
    }
    
    private void loadCustomers() {
        // Sample data
        customers.add(new Customer(1, "John Doe", "john@example.com"));
        customers.add(new Customer(2, "Jane Smith", "jane@example.com"));
        customers.add(new Customer(3, "Mike Johnson", "mike@example.com"));
    }

    // Additional methods can be added as needed for managing the customer data
}
