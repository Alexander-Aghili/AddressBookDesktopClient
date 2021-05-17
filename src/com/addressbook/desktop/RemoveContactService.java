package com.addressbook.desktop;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RemoveContactService {
	
	Contact contact;
	
	public RemoveContactService(Contact contact) {
		this.contact = contact;
		
		Thread removeThread = new Thread(new Runnable() {

			@Override
			public void run() {
				removeContact();
			}
			
		});
		try {
			removeThread.start();
			removeThread.join();
		} catch (InterruptedException e) {}
		
	}
	
	private void removeContact() {
		Client client = Client.create();

        WebResource webResource = client.resource(Domain.getDomain() + "/rest/removeContact");

        String input = GetJSONFromContact.getJSONFromContact(contact);

        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, input);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
	}

}
