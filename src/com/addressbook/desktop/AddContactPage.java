package com.addressbook.desktop;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddContactPage implements EventHandler<ActionEvent> {
	
	Stage stage;
	Button backButton, addContactButton;
	TextField firstNameField, lastNameField, phoneNumberField, addressField;
	
	private ArrayList<Contact> contacts;
	
	public AddContactPage(Stage stage) {
		
		//Update contact list here because info from main page not guaranteed to be updated.
		GetListService contactService = new GetListService();
		contacts = contactService.getContactsFromDatabase();
		
		this.stage = stage;
		
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(50));
		vbox.setSpacing(8);
		vbox.setAlignment(Pos.TOP_LEFT);
		
		firstNameField = createField();
		lastNameField = createField();
		phoneNumberField = createField();
		addressField = createField();
		
		Text firstNameLabel = createLabel("First Name: ");
		Text lastNameLabel = createLabel("Last Name: ");
		Text phoneNumberLabel = createLabel("Phone Number: ");
		Text addressLabel = createLabel("Address: ");
		
		Text[] labels = {firstNameLabel, lastNameLabel, phoneNumberLabel, addressLabel};
		TextField[] textFields = {firstNameField, lastNameField, phoneNumberField, addressField};
		  
		backButton = createButton("Back");
		addContactButton = createButton("Add Contact");
		
		vbox.getChildren().add(listEntries(labels, textFields));
		vbox.getChildren().add(createBottomButton());
		
		Scene addContactPage = new Scene(vbox);
		  
		 
		stage.setScene(addContactPage);
		stage.show(); 
	}
	
	private VBox listEntries(Text[] labels, TextField[] textFields) {
		VBox box = new VBox();
		box.autosize();
		box.setSpacing(50);
		box.setPadding(new Insets(60));
		box.setAlignment(Pos.TOP_CENTER);
		
		for(int i = 0; i < labels.length; i++) {
			Node node = createMiniBox(labels[i], textFields[i]);
			box.getChildren().add(node);
		}
		
		return box;
		
	}
	
	private VBox createMiniBox(Text label, TextField field) {
		VBox miniBox = new VBox();
		miniBox.autosize();
		miniBox.setSpacing(5);
		miniBox.setAlignment(Pos.CENTER);
		miniBox.getChildren().add(label);
		
		if (field.equals(phoneNumberField)) {
			field.setText("+x (xxx) xxx-xxxx");
		}
		
		miniBox.getChildren().add(field);
		
		return miniBox;
	}
	
	private HBox createBottomButton() {
		HBox box = new HBox();
		box.autosize();
		box.setSpacing(100);
		box.setAlignment(Pos.CENTER);
		box.getChildren().add(backButton);
		box.getChildren().add(addContactButton);
		return box;
	}
	
	private TextField createField() {
		TextField field = new TextField();
		field.setPrefSize(100, 40);
		field.setFont(new Font(15));
		field.setAlignment(Pos.CENTER);
		return field;
	}
	
	private Text createLabel(String string) {
		Text text = new Text(string);
		
		String cssStyle = " -fx-font-size: 20px;";
		
		text.setStyle(cssStyle);
		text.autosize();
		return text;
	}
	
	private Button createButton(String text) {
		Button button = new Button(text);
		button.setPrefSize(100, 35);
		button.setOnAction(this);
		return button;
	}

	@Override
	public void handle(ActionEvent e) {
		if (e.getSource().equals(backButton)) {
			new MainPage(stage);
		}
		//Add contact button clicked
		if (e.getSource().equals(addContactButton)) {
			//Validates all input, it is unique info, and no id is the same accidentally.
			InputValidation validator = new InputValidation(firstNameField, lastNameField, phoneNumberField, addressField);
			if (!validator.checkValidInputs()) {
				return;
			}
			
			Contact contact = createContact();
			
			String contactValidation = validator.isUniqueInfo(contacts, contact);
//            
            if (contactValidation.equals("dupID")) return;
           
			if (contactValidation.equals("same info")) {
				new MainPage(stage);
				return;
			}
			
			//Once all validation is complete and correct, it will add contact to the database
			AddContactService addContact = new AddContactService(contact);
			try {
				addContact.start();
				addContact.join();
			} catch (InterruptedException e1) {
			}
			new MainPage(stage);
			
		}
		
	}
	
	private Contact createContact() {
		return new Contact(firstNameField.getText(), lastNameField.getText(), phoneNumberField.getText(), addressField.getText());
	}
}
