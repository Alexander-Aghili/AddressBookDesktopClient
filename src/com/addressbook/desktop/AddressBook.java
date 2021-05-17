package com.addressbook.desktop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import static javafx.application.Application.launch;
import javafx.scene.Scene; 
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;  

/*
 *Alexander Aghili
 *Comp 232
 */

/*
 * This is an Address Book application that is written in both JavaFX and as an Android App with a REST API written in Java hosted in AWS.
 */
public class AddressBook extends Application {
	
	
	@Override 
	public void start(Stage stage) {

		stage.getIcons().add(new Image(getClass().getResourceAsStream("bookLogo.png")));
		stage.setMinHeight(700);
		stage.setMinWidth(400);
		stage.setTitle("Address Book");
	  
		new MainPage(stage);
	  
	}
 
   
   public static void main(String[] args){ 
      Application.launch(args); 
   }


}
