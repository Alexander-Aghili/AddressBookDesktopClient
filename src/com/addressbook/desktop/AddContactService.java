package com.addressbook.desktop;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AddContactService extends Thread{
    Contact contact;

    public AddContactService(Contact contact) {
        this.contact = contact;
    }
    
    @Override
    public void run() {
    	addContact();
    }

    public void addContact() {
        Client client = Client.create();

        WebResource webResource = client.resource(Domain.getDomain() + "/rest/addContact");

        String input = GetJSONFromContact.getJSONFromContact(contact);

        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, input);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
    }

}

