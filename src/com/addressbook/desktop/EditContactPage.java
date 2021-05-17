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

public class EditContactPage implements EventHandler<ActionEvent> {
	
	Stage stage;
	Button backButton, editContactButton;
	Contact contact;
	TextField firstNameField, lastNameField, phoneNumberField, addressField;
	EditContactService editService;
	Thread editContactThread;
	
	private ArrayList<Contact> contacts;
	
	public EditContactPage(Stage stage, Contact contact) {
		GetListService contactService = new GetListService();
		contacts = contactService.getContactsFromDatabase();
		
		this.stage = stage;
		this.contact = contact;
		
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(50));
		vbox.setSpacing(8);
		vbox.setAlignment(Pos.TOP_LEFT);
		
		firstNameField = createField(contact.getFirstName());
		lastNameField = createField(contact.getLastName());
		phoneNumberField = createField(contact.getPhoneNumber());
		addressField = createField(contact.getAddress());
		
		Text firstNameLabel = createLabel("First Name: ");
		Text lastNameLabel = createLabel("Last Name: ");
		Text phoneNumberLabel = createLabel("Phone Number: ");
		Text addressLabel = createLabel("Address: ");
		
		Text[] labels = {firstNameLabel, lastNameLabel, phoneNumberLabel, addressLabel};
		TextField[] textFields = {firstNameField, lastNameField, phoneNumberField, addressField};
		  
		backButton = createButton("Back");
		editContactButton = createButton("Edit Contact");
		
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
		miniBox.getChildren().add(field);
		
		return miniBox;
	}
	
	private HBox createBottomButton() {
		HBox box = new HBox();
		box.autosize();
		box.setSpacing(100);
		box.setAlignment(Pos.CENTER);
		box.getChildren().add(backButton);
		box.getChildren().add(editContactButton);
		return box;
	}
	
	private TextField createField(String fill) {
		TextField field = new TextField();
		field.setPrefSize(100, 40);
		field.setFont(new Font(15));
		field.setAlignment(Pos.CENTER);
		field.setText(fill);
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
		if (e.getSource().equals(editContactButton)) {
			
			InputValidation validator = new InputValidation(firstNameField, lastNameField, phoneNumberField, addressField);
			if (!validator.checkValidInputs()) return;
			
			String firstName = firstNameField.getText().toString();
            String lastName = lastNameField.getText().toString();
            String phoneNumber = phoneNumberField.getText().toString();
            String address = addressField.getText().toString();
            Contact newContact = new Contact(firstName, lastName, phoneNumber, address, contact.getID());
            editService = new EditContactService(newContact);

            String contactValidation = validator.isUniqueInfo(contacts, newContact);
            
            if(contact.equals(newContact) || contactValidation.equals("same info")) {
            	new MainPage(stage);
                return;
            }

            if (!firstName.equals(contact.getFirstName())) {
                editContactThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        editService.editFirstName();
                    }
                });
                startThread();
            }
            if (!lastName.equals(contact.getLastName())) {
                editContactThread = new Thread(new Runnable() {
                     @Override
                     public void run() {
                         editService.editLastName();
                     }
                });
                startThread();
            }
            if (!phoneNumber.equals(contact.getPhoneNumber())) {
                editContactThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        editService.editPhoneNumber();
                    }
                });
                startThread();
            }
            if (!address.equals(contact.getAddress())) {
                editContactThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        editService.editAddress();
                    }
                });
                startThread();
            }
            
            new MainPage(stage);
		}
		
	}
	
    private void startThread() {
        try {
            editContactThread.start();
            editContactThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
