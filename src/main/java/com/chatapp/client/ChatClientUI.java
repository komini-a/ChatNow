package com.chatapp.client;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;

public class ChatClientUI extends Application {
	
	private TextField inputField;
	private TextArea chatLog;
	private ChatClient user;
	private String username;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TextField nameInput = new TextField();
		nameInput.setPromptText("Enter Username: ");
		
		Label title = new Label("ChatNow v1");
		
		chatLog = new TextArea();
		chatLog.setEditable(false);
		
		inputField = new TextField();
		inputField.setVisible(false);
		
		
		Button enterChat = new Button("Join");
		Button sendMessage = new Button("Send");
		
		sendMessage.setVisible(false);
		
		
		
		enterChat.setOnAction(e ->{
			this.username = nameInput.getText();
			if(!username.isEmpty()) {
				try {
					user = new ChatClient("localhost", 8080, chatLog, username);
					nameInput.setDisable(true);
					enterChat.setDisable(true);
					enterChat.setText("Joined!");
					sendMessage.setVisible(true);
					inputField.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}	
		});
		
		nameInput.setOnAction(e ->{
			enterChat.fire();
		});
		
		this.inputField.setPromptText("Type message here");
		
		sendMessage.setOnAction (e -> {
			String message = inputField.getText();
			if(message != null) {
				try {
					
					user.sendMessage(user.getUsername() + ": " + message);
					inputField.clear();
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		inputField.setOnAction(e ->{
			sendMessage.fire();
		});
		
		HBox nameLayout = new HBox(5, enterChat, nameInput);
		HBox messageLayout = new HBox(5, sendMessage, inputField);
		
		VBox chatLayout = new VBox(10, title, nameLayout, chatLog, messageLayout);
		Scene scene = new Scene(chatLayout, 500, 500);
		scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
		
		primaryStage.setTitle("ChatNow v1");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}

	
	 public static void main (String[] args) {
	    	launch(args);
	    }

}
