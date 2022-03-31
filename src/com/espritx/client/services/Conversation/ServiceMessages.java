package com.espritx.client.services.Conversation;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.PropertyBusinessObject;
import com.espritx.client.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class ServiceMessages {
    public List<Message> afficher_message() {
        /*Rest.get("http://127.0.0.1:8000/api/AddMessage");*/
        Response<List<PropertyBusinessObject>> k = Rest.get("http://127.0.0.1:8000/api/showMessages").acceptJson().getAsPropertyList(Message.class);
        List<PropertyBusinessObject> res = k.getResponseData();

        List<Message> LoadedMessages = new ArrayList<>();
        for (PropertyBusinessObject r : res) {
            LoadedMessages.add((Message) r);
        }

        return LoadedMessages;
    }
    public void Add_Message(Message m) {
        System.out.println(Rest.post("http://127.0.0.1:8000/api/AddMessage").body(m).jsonContent().acceptJson().getAsJsonMap().getResponseData());
    }
    public void Delete_message(Message m) {
        System.out.println(Rest.post("http://127.0.0.1:8000/api/DeleteMessage").body(m).jsonContent().acceptJson().getAsJsonMap().getResponseData());
    }
    public void Edit_message(Message m) {
        System.out.println(Rest.post("http://127.0.0.1:8000/api/EditMessage").body(m).jsonContent().acceptJson().getAsJsonMap().getResponseData());
    }
}
