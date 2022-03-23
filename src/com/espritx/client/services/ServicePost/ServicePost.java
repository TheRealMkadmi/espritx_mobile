/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.services.ServicePost;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

import com.codename1.ui.events.ActionListener;
import com.espritx.client.entities.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.espritx.client.utils.Statics;

/**
 *
 * @author mouna
 */
public class ServicePost {

    // appliquer le patron de conception singleton bch menab9ach kol mara naamel f instanciation 
    public ConnectionRequest req;
    private static ServicePost instance = null;
    public boolean resultOK;
    public ArrayList<Post> posts;

    private ServicePost() {
        req = new ConnectionRequest();
    }

    public static ServicePost getInstance() {
        if (instance == null) {
            instance = new ServicePost();
        }

        return instance;
    }

    public boolean AddPost(Post post) {
// Haka
        // String url=Statics.BASE_URL+"create?title=post1&content=post1";
        // ou bien 
        String url = Statics.BASE_URL + "api/addPost";

// aasocier url a notre connection request
        req.setUrl(url);
        // par defaut post c pk il faut l'attribuer comme false ==> get
        //  req.setPost(true);
        req.addArgument("title", post.getTitle());
        req.addArgument("content", post.getContent() + "");

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseCodeListener(this);
            }
        });
        //ajouter cette req a la fil d'attente
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Post> getAllpost() {

        String url = Statics.BASE_URL + "api/post/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((evt) -> {
            try {
                posts = parsePost(new String(req.getResponseData()));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return posts;
    }

    private ArrayList<Post> parsePost(String jsonText) throws IOException {
        try {
            posts = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> postListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) postListJson.get("root");
            for (Map<String, Object> obj : list) {
                String title = "null";
                String content = null;
                if (obj.get("title") != null && obj.get("content") != null) {
                    title = obj.get("title").toString();
                }
                content = obj.get("content").toString();
                Post p = new Post(title, content);
                posts.add(p);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return posts;
    }

}
