/*
 * Kristen Jenkins (kij180000), Md Hassan (mfh180002), Adrien Smith (acs190011)
 * CS 4390.004
 * Note, this program was modified from Professor Solanki's TCPClient java example in the eLearning Help Code folder.
 */

//Uses TCP protocol

import java.io.*; 
import java.net.*;
import java.util.Scanner; 

public class MathClient 
{
	public static void main(String argv[]) throws Exception 
    {
		
        String sentence; 
        String calculation; 
        String clientName;
        Scanner scanner = new Scanner(System.in);
        
        //Ask the user for the client's name
        System.out.print("Please enter the name of the Client: ");
        clientName = scanner.nextLine();

        //For getting the user's input
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 

        //For connecting to the server
        Socket clientSocket = new Socket("127.0.0.1", 6789); 

        //For sending to the server
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
        
        //For reading from the server
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        
        //client gives name during initial attachment to the server
        outToServer.writeBytes(clientName + '\n'); 
        
        //waits till it gets acknowledgement from the server for a successful connection
        System.out.println("Waiting for acknowledgement from the server...");
        
        //Connected to server
        System.out.println("Acknowledgement from the server received");
        
        //Print the prompt for the math calculation from the server
        System.out.println("FROM SERVER: " + inFromServer.readLine());

        //Get the user's math calculation request
        sentence = inFromUser.readLine(); 
        
        //error handling for client; make sure math request is in correct format:
        
        //parse the sentence for the arguments
        String[] arguments = sentence.split(" ");

        //handle error of arguments not being exactly 3***
        while (arguments.length != 3) 
        {
        	System.out.println("Invalid math request! Enter it again:");
        	sentence = inFromUser.readLine();
        	arguments = sentence.split(" ");
        } 

        //Send the math calculation request to the server
        outToServer.writeBytes(sentence + '\n'); 

        //get the calculation from the server
        calculation = inFromServer.readLine(); 

        //Print the math calculation received from the server
        System.out.println("FROM SERVER: " + calculation);

        //client sends a close connection request to the server, application terminates
        clientSocket.close(); 
        scanner.close();              
    }

}