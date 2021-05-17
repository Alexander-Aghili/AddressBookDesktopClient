package com.addressbook.desktop;

public class GetJSONFromContact {

    protected static String getJSONFromContact(Contact contact) {
        return "{\"firstname\": \"" + contact.getFirstName() + "\", " +
                "\"lastname\": \"" + contact.getLastName() + "\", " +
                "\"phonenumber\": \"" + contact.getPhoneNumber() + "\", " +
                "\"address\": \"" + contact.getAddress() + "\", " +
                "\"code\": \"" + contact.getID() + "\"}";
    }

}

