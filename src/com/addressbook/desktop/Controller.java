package com.addressbook.desktop;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class Controller{
	
	private ArrayList<Contact> contactList;
	
	public Controller() {
		Thread getListThread = new Thread(new Runnable() {

			@Override
			public void run() {
				GetListService service = new GetListService();
				contactList = service.getContactsFromDatabase();
			}
		});
		try {
			getListThread.start();
			getListThread.join();
		} catch (Exception e) {}
		
	}
	
	public ArrayList<Contact> getContactList() {
		Collections.sort(contactList);
		return contactList;
	}

}
