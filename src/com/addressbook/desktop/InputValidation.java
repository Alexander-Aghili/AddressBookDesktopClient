package com.addressbook.desktop;

import java.util.ArrayList;

import javafx.scene.control.TextField;

public class InputValidation {
	
	TextField firstname, lastname, phonenumber, address;
	
	public InputValidation(TextField firstname, TextField lastname, TextField phonenumber, TextField address) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.phonenumber = phonenumber;
		this.address = address;
	}
	
	public boolean checkValidInputs() {
		if (checkBlankInput(firstname.getText())) {
			alterErrorCSS(firstname);
			return false;
		} else {
			alterToNormalCSS(firstname);
		}
		
		if (checkBlankInput(lastname.getText())) {
			alterErrorCSS(lastname);
			return false;
		} else {
			alterToNormalCSS(lastname);
		}
		
		if (checkNotValidPhoneNumber(phonenumber.getText())) {
			alterErrorCSS(phonenumber);
			return false;
		} else {
			alterToNormalCSS(phonenumber);
		}
		
		if (checkBlankInput(address.getText())) {
			alterErrorCSS(address);
			return false;
		} else {
			alterToNormalCSS(address);
		}
		
		return true;
	}
	
	private void alterErrorCSS(TextField field) {
		field.setStyle("-fx-text-box-border: red;"
				+ "-fx-focus-color: red ;");
	}
	
	private void alterToNormalCSS(TextField field) {
		field.setStyle("-fx-text-box-border: black;"
				+ "-fx-focus-color: black ;");
	}
	
	private boolean checkBlankInput(String input) {
		if (input.trim().equals(""))
			return true;
		return false;
		
	}
	
	private boolean checkNotValidPhoneNumber(String input) {
		int length = input.trim().length();
		if (length > 18 || length < 12)
			return true;
		return false;
	}
	
	//Reason that info is taken in during page entry is to avoid delay time when edit button pressed
	//Returns "ok" when all info is unique and no duplicate ID
	//Returns "same info" when there exists a contact with all the same information
	//Returns "dupID" when a contact with the same ID exists within the database
	public String isUniqueInfo(ArrayList<Contact> contacts, Contact contact) {

		for (Contact contactFromArray: contacts) {
			if (contactFromArray.getID().equals(contact.getID()))
				return "dupID";
			
			//If contact information is the same
			if (contactFromArray.equals(contact))
				return "same info";
		}
		return "ok";
	}
	
	

}
