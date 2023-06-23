package server.app;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import model.Customer;
import server.controller.CustomerDataManager;

public class TCPServerApp {

    public static void main(String[] args) {
        int portNo = 8088;
        
        CustomerDataManager manager = new CustomerDataManager();
        
        System.out.println("\n\tExecuting TCPServerApp");
        
        try {
            System.out.println("\tWaiting for next request");
            
            // 1. Bind to a port
            ServerSocket serverSocket = new ServerSocket(portNo);
            
            // 2. Server needs to continually run to listen for requests
            while (true) {
                // 3. Accept request from client
                Socket clientSocket = serverSocket.accept();
                
                // Get list of customers
                List<Customer> customers = manager.getAllCustomers();
                
                // 4. Respond to client
                OutputStream os = clientSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(customers);
                System.out.println("\tSending " + customers.size() + " customers to the client");
                
                // Log operation
                
                // Close the client socket
                clientSocket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
