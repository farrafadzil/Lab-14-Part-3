package server.app;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.controller.CustomerDataManager;
import model.Customer;

public class TCPProductServerApp {

    private static final Logger LOGGER = Logger.getLogger(TCPProductServerApp.class.getName());

    public static void main(String[] args) {
        int portNo = 8088;
        CustomerDataManager manager = new CustomerDataManager();

        LOGGER.info("\n\tExecuting TCPProductServerApp");

        try {
            LOGGER.info("\tWaiting for next request");

            // Bind to a port
            ServerSocket serverSocket = new ServerSocket(portNo);

            while (true) {
                // Accept request from client
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("\tAccepted connection from client: " + clientSocket.getInetAddress());

                // Process request - read customer ID from client
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                int customerId = dis.readInt();
                LOGGER.info("\tRequest for customer ID: " + customerId);

                // Get customer by ID
                Customer customer = manager.searchCustomerById(customerId);

                // Respond to client - send customer object
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                oos.writeObject(customer);
                oos.flush();
                LOGGER.info("\tSent customer details to the client");

                // Close the client socket
                clientSocket.close();
                LOGGER.info("\tConnection with client closed");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An error occurred in the server", ex);
        }
    }
}
