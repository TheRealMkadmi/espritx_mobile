package com.espritx.client.services.Conversation;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.processing.Result;
import com.espritx.client.entities.Conversation;

import java.util.HashMap;
import java.util.Map;

public class ServiceConversation {
    public void ajouter_conver(Conversation c1){
        Map<String, Object> conver = new HashMap<>();
        conver.put("user1", c1.getUser1());
        conver.put("user2",c1.getUser2());
        conver.put("email", c1.get$Email());
        conver.put("email2",c1.get$Email2());
        conver.put("Chateph", c1.isChateph());
        final String payload = Result.fromContent(conver).toString();
        Response<Map> k = Rest.post("http://127.0.0.1:8000/api/conversation/add").jsonContent().body(payload).getAsJsonMap();
    }
    public void afficher_conver(){
        Response<Map> result = Rest.get("http://127.0.0.1:8000/api/conversation").getAsJsonMap();
        Map<String, Object> conver = new HashMap<>();
       conver=  result.getResponseData();
        for( Map.Entry<String, Object>entry : conver.entrySet() ){
            System.out.println( entry.getKey() + " => " + entry.getValue() );
        }
    }
    public void modifier(Conversation c1){
        Map<String, Object> conver = new HashMap<>();
        conver.put("user1", c1.getUser1());
        conver.put("user2",c1.getUser2());
        conver.put("email", c1.get$Email());
        conver.put("email2",c1.get$Email2());
        conver.put("Chateph", c1.isChateph());
        final String payload = Result.fromContent(conver).toString();
        String url="http://127.0.0.1:8000/api/conversation/edit/"+c1.getId();
        Response<Map> k = Rest.post(url).jsonContent().body(payload).getAsJsonMap();
    }
    public void supprimer(Conversation c1){

                String url="http://127.0.0.1:8000/api/converation/delete/"+c1.getId();
        Response<Map> result = Rest.get(url).getAsJsonMap();
    }
}
