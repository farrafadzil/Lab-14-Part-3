package client.app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Customer;

public class TCPCustomerClientApp {
    private static final int SERVER_PORT = 8088;
    private static final Logger LOGGER = Logger.getLogger(TCPCustomerClientApp.class.getName());

    public static void main(String[] args) {
        LOGGER.setLevel(Level.ALL);

        try {
            // Create a list of customer names to send to the server
            String[] customerNames = {"John Doe", "Jane Smith", "David", "Alex Johnson"};

            // Connect to the server
            InetAddress serverAddress = InetAddress.getLocalHost();
            Socket socket = new Socket(serverAddress, SERVER_PORT);
            LOGGER.info("Connected to server: " + serverAddress);

            // Send customer names to the server
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            for (String name : customerNames) {
                oos.writeObject(name);
                LOGGER.info("Sent customer name to server: " + name);
            }

            // Receive customer objects from the server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            for (String name : customerNames) {
                Customer customer = (Customer) ois.readObject();
                LOGGER.info("Received customer object from server: " + customer);

                // Display customer details
                displayCustomerDetails(customer);
            }

            // Close the socket
            socket.close();
            LOGGER.info("Connection closed");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception occurred", ex);
        }
    }

    private static void displayCustomerDetails(Customer customer) {
        if (customer != null) {
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            // Display other customer details as needed
            System.out.println();
        } else {
            System.out.println("Customer not found");
            System.out.println();
        }
    }
}
