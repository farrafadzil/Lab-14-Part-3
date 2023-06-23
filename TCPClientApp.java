package client.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import model.Customer;

public class TCPClientApp {

    public static void main(String[] args) {
        
        try {
            // Server information
            int serverPort = 8088;
            InetAddress serverAddress = InetAddress.getLocalHost();

            // Customer IDs to search
            int[] customerIds = { 1001, 1002, 1003, 9999 };

            // Connect to the server
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server.");

            // Create object output stream to send customer IDs
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // Create object input stream to receive customer objects
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            for (int customerId : customerIds) {
                // Send customer ID to the server
                objectOutputStream.writeInt(customerId);
                objectOutputStream.flush();
                System.out.println("Sent customer ID: " + customerId);

                // Receive customer object from the server
                Customer customer = (Customer) objectInputStream.readObject();

                // Display customer details
                if (customer != null) {
                    System.out.println("Received customer:");
                    System.out.println("\tCustomer ID: " + customer.getCustomerId());
                    System.out.println("\tName: " + customer.getName());
                    System.out.println("\tEmail: " + customer.getEmail());
                } else {
                    System.out.println("Customer not found.");
                }
            }

            // Close the connections
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
            System.out.println("Connection closed.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
