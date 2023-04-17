/*
 * Kristen Jenkins (kij180000), Md Hassan (mfh180002), Adrien Smith (acs190011)
 * CS 4390.004
 * Note, this program was modified from Professor Solanki's TCPServer java example in the eLearning Help Code folder.
 */

//Uses TCP protocol

import java.io.*;
import java.net.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MathServer {

  public static void main(String argv[]) throws Exception {
    String clientSentence;

    String[] arguments;
    String operator;
    String clientName;
    int X, Y, result = 0;

    ServerSocket welcomeSocket = new ServerSocket(6789);

    while (true) 
    {
    	//server wait's for the client's request
      System.out.println("Server is waiting for a client's request...");

      Socket connectionSocket = welcomeSocket.accept();

      //logs who the client is (name), when they connected, start a timer to keep track of how long upon attachment
      String hostName = connectionSocket.getInetAddress().getLocalHost().getHostName();
     
      //For reading from the client
      BufferedReader inFromClient = new BufferedReader(
        new InputStreamReader(connectionSocket.getInputStream())
      );

      //For sending to the client
      DataOutputStream outToClient = new DataOutputStream(
        connectionSocket.getOutputStream()
      );

      //Read in the client's math calculation
      clientName = inFromClient.readLine();
      
      //logs when the client connected
      LocalDateTime connectionTime = LocalDateTime.now();
      DateTimeFormatter formatTime = DateTimeFormatter.ofPattern(
        "dd-MM-yyyy HH:mm:ss"
      );
      String formattedTime = connectionTime.format(formatTime);
      
      //start a timer to keep track of how long
      Instant timeStart = Instant.now();

      System.out.println("Client request received");
      System.out.println("Connected to Host " + hostName + " with client name " + clientName + " at " + formattedTime);
      
      //Prompt the client to ask a simple math calculation
      outToClient.writeBytes("Enter a math calculation in the form \"X <operator> Y\" where X and Y are integers" +
        " and possible operators are +, -, *: " + "\n");

      //Read in the client's math calculation
      clientSentence = inFromClient.readLine();

      //parse the sentence for the arguments
      arguments = clientSentence.split(" ");

      //handle error of arguments not being exactly 3***
      if (arguments.length != 3) {
        outToClient.writeBytes("Invalid math request!");
      } 
      else {
        //parse the operator and operands from the client's input
        X = Integer.parseInt(arguments[0]);
        operator = arguments[1];
        Y = Integer.parseInt(arguments[2]);

        //do a switch block for each of the 3 (4?) operands, assign result
        switch (operator) {
          case "+":
            result = X + Y;
            break;
          case "-":
            result = X - Y;
            break;
          case "*":
            result = X * Y;
            break;
        }

        //gives the calculation to the client
        String answer = String.valueOf(result) + "\n";
        outToClient.writeBytes(answer);

        //log when client is done, stop timer
        Instant timeEnd = Instant.now();
        System.out.println(
          "Session with client is closed via client, Session length with Host " + hostName + " and Client " + clientName + ": " +
          Duration.between(timeStart, timeEnd).getSeconds() +
          " second(s)"
        );
      }
    }
  }
}