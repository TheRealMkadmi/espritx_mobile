package com.espritx.client.services.Conversation;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.PropertyBusinessObject;
import com.espritx.client.entities.Conversation;
import com.espritx.client.services.User.AuthenticationService;

import java.util.*;

public class ServiceConversation {
    public List<Conversation> afficher_conver() {
        Response<List<PropertyBusinessObject>> k = Rest.get("http://127.0.0.1:8000/api/showChannel").acceptJson().getAsPropertyList(Conversation.class);
        List<PropertyBusinessObject> res = k.getResponseData();

        List<Conversation> LoadedConversations = new ArrayList<>();
        for (PropertyBusinessObject r : res) {
            LoadedConversations.add((Conversation) r);
        }
        return LoadedConversations;
    }
    public List<Conversation>Filter_conver(List<Conversation> l1){
        List<Conversation> l2=new ArrayList<>();
        for (Conversation conversation : l1) {
            if (conversation.getParticipants().get(0).getPropertyIndex().get("id").equals(AuthenticationService.getAuthenticatedUser().getPropertyIndex().get("id"))) {
                l2.add(conversation);
            }
            if (conversation.getParticipants().get(1).getPropertyIndex().get("id").equals(AuthenticationService.getAuthenticatedUser().getPropertyIndex().get("id"))) {
                l2.add(conversation);

            }

        }
        return l2;
    }

    public void add_Conversation(Conversation c1) {
        System.out.println(Rest.post("http://127.0.0.1:8000/api/addChannelapi").body(c1).jsonContent().acceptJson().getAsJsonMap().getResponseData());
    }

    public void Delete_conversation(Conversation c1) {
        System.out.println(Rest.post("http://127.0.0.1:8000/api/DeleteChannelApi").body(c1).jsonContent().acceptJson().getAsJsonMap().getResponseData());
    }

    public void Edit_conversation(Conversation c1) {
        System.out.println(Rest.post("http://127.0.0.1:8000/api/UpdateChannelApi").body(c1).jsonContent().acceptJson().getAsJsonMap().getResponseData());
    }

}
