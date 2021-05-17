# Address Book Desktop Client

Guide to using:
The JAR uses no module dependencies. Therefore, depending on how you have your java installed, it might not work.
If the JavaFX dependencies, specifically the .dll files, are not part of the JRE, then it likely will not work.
I used JDK and JRE 1.8 to compile and run, though it should work on JRE 8+.

To get it working on Eclipse, I did the following:
- Add JavaFX dependencies, as well as jersey dependencies, like jersey bundle, and org.json(All dependencies as well as links to get them will be at the bottom)
- Add the JavaFX path to the module VM arguments: Go to Run Configurations -> Arugments -> VM Arguments -> --module-path=${JavaFXSDK} --add-modules=javafx.controls,javafx.fxml

This must occur due to a module dependency issue I had early on. 
If wanted, it is possible to create a new JavaFX program with module dependencies and then add the proper dependecies through that.
Further reading here: https://gist.github.com/stevenliebregt/bc62a382fc43064136b662ee62172ab3

If you continue to struggle, here are images on how it operates:

1. First time startup. There is an area in the middle for the contacts when you add them. The cloud icon in the bottom left is a refresh button. 
Once pressed, it will check for any edits or new contacts and display them.

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/startup.png?raw=true)

2. After clicking on Add Contact, you are presented to this page. Press back to go back without saving any changes.

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/AddContactStartup.png?raw=true)

3. If you attempt to enter an empty value, an outline of red will surround the textfield:

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/AddContactNoInfo.png?raw=true)

4. Completely fill out the fields:

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/AddContactFilled.png?raw=true)

5. Once the Add Contact button is pressed, you will be taken back to this page and displayed the new info:

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/NewContactAdded.png?raw=true)

6. Highlight the Contact and click the Edit Contact button to be presented with this page. If you attempt to enter an empty value, an outline of red will surround the textfield:

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/EditContactNoInfo.png?raw=true)

7. Completely edit the program: 

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/EditContactFilled.png?raw=true)

8. Once the Edit Contact button is pressed, the info will be altered and presented:

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/ContactEdited.png?raw=true)

9. Highlight the contact and click the Remove Contact button to get rid of the Contact:

![alt text](https://github.com/Alexander-Aghili/AddressBookDesktopClient/blob/master/Images/startup.png?raw=true)

Validation: User validation consists on ensuring that all fields have text. It also checks that the length of the phone number is resonable. 
When Adding the Contact, it checks that the ID created is unique. It checks that all the information is unique, if it is not, it just returns to the main screen.

Dependencies: https://github.com/Alexander-Aghili/AddressBookDesktopClient/tree/master/dependencies
