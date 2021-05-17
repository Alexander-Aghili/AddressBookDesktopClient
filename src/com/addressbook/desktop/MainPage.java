package com.addressbook.desktop;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPage implements EventHandler<ActionEvent> {
	
	Stage stage;
	
	ListView<Contact> contactListView;
	HBox buttonBox;
	Contact selectedContact = null;
	
	Button addContactButton, editContactButton, removeButton, refreshButton;
	
	public MainPage(Stage stage) {
		this.stage = stage;
		contactListView = createContactListView();
		buttonBox = createButtonRow();
		
		VBox box = new VBox();
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(contactListView);
		box.getChildren().add(buttonBox);
		box.setSpacing(60);
		
		Scene scene = new Scene(box);
		
		stage.setScene(scene);
		stage.show();

	}
	

	
	private ListView<Contact> createContactListView() {
		Controller list = new Controller();
		ObservableList<Contact> contactsObservable = FXCollections.observableArrayList(list.getContactList());
		ListView<Contact> listView = new ListView<Contact>(contactsObservable); 
		listView.setOrientation(Orientation.VERTICAL);
		listView.setPrefSize(400, 560);
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {

			@Override
			public void changed(ObservableValue<? extends Contact> arg0, Contact oldContact, Contact newContact) {
				if (oldContact == null || newContact != null) {
					editContactButton.setDisable(false);
					removeButton.setDisable(false);
				} else if (newContact == null) {
					editContactButton.setDisable(true);
					removeButton.setDisable(true);
				}
				selectedContact = newContact;
			}
		
			
		});
		return listView;
		
	}
	
	private HBox createButtonRow() {
		createButtons();
		
		HBox box = new HBox();
		box.autosize();
		box.setSpacing(30);
		box.setPadding(new Insets(15));
		box.setAlignment(Pos.CENTER);
		box.getChildren().add(refreshButton);
		box.getChildren().add(removeButton);
		box.getChildren().add(editContactButton);
		box.getChildren().add(addContactButton);
		return box;
	}
	
	private void createButtons() {
		addContactButton = createButton("Add New Contact", false);
		editContactButton = createButton("Edit Contact", true);
		removeButton = createButton("Remove Contact", true);
		
		Image img = new Image(getClass().getResourceAsStream("refresh.png"));
		ImageView view = new ImageView(img);
		view.setFitHeight(35);
		view.setPreserveRatio(true);
		
		refreshButton = new Button();
		refreshButton.setGraphic(view);
		refreshButton.setPrefSize(35, 35);
		refreshButton.setOnAction(this);
	}
	
	private Button createButton(String text, boolean disabled) {
		Button button = new Button(text);
		button.setPrefSize(130, 35);
		button.setOnAction(this);
		button.setDisable(disabled);
		return button;
	}

	
	@Override
	public void handle(ActionEvent e) {
		if (e.getTarget().equals(addContactButton)) {
			new AddContactPage(stage);
		}
		if (e.getTarget().equals(editContactButton)) {
			new EditContactPage(stage, selectedContact);
		}
		if (e.getTarget().equals(removeButton)) {
			new RemoveContactService(selectedContact);
			contactListView.getItems().remove(selectedContact);
		}
		if (e.getTarget().equals(refreshButton)) {
			new MainPage(stage);
		}
		
	}

}
