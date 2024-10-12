package com.chatapp.client;

import java.io.*;

import javafx.application.Platform;
import javafx.scene.control.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private TextArea log;
    private String username;

    public ChatClient(String serverAddress, int port, TextArea log, String username) {
        try {
        	this.username = username;
        	this.log = log;
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to the chat server");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            
            sendMessage(this.username + " entered the chat!");

            new Thread(new ReadMessageTask()).start();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getUsername() {
		return username;
	}
    
	public void sendMessage (String text) {
    	try {
    		if(text != null) {
    			writer.println(text);
    			writer.flush();
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    // Task to read messages from the server
    private class ReadMessageTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    // Read message from the server
                    String message = reader.readLine();
                    
                    if (message != null) {
                        // Use Platform.runLater() to ensure thread safety when modifying UI components
                        Platform.runLater(() -> {
                            // Append the received message to the chat TextArea (or other UI component)
                            log.appendText(message + "\n");  // chatLog is the TextArea where you display the messages
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}