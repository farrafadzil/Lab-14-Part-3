package server.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Customer;
import server.controller.CustomerDataManager;

public class TCPCustomerServerApp {
    private static final int SERVER_PORT = 8088;
    private static final Logger LOGGER = Logger.getLogger(TCPCustomerServerApp.class.getName());

    public static void main(String[] args) {
        LOGGER.setLevel(Level.ALL);

        try {
            LOGGER.info("Executing TCPCustomerServerApp");
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            LOGGER.info("Server started and waiting for client requests...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("Client connected: " + clientSocket.getInetAddress());

                // Read client request
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                String customerName = (String) ois.readObject();
                LOGGER.info("Received customer name from client: " + customerName);

                // Process customer search
                CustomerDataManager customerDataManager = new CustomerDataManager();
                Customer customer = customerDataManager.searchCustomerByName(customerName);

                // Send customer object back to the client
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                oos.writeObject(customer);
                LOGGER.info("Sending customer object to client");

                // Close client socket
                clientSocket.close();
                LOGGER.info("Client disconnected");
            }
        } catch (IOException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Exception occurred", ex);
        }
    }
}

