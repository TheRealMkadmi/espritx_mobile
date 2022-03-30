package com.espritx.client.services.ServiceForum;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.espritx.client.entities.ForumPost;
import com.espritx.client.utils.Statics;

public class serviceForumPost {
    public ConnectionRequest req;
    public static boolean resultOK = true;
    private static serviceForumPost instance;
    private serviceForumPost() {
        req = new ConnectionRequest();
    }

    public static serviceForumPost getInstance() {
        if(instance == null)
            instance = new serviceForumPost();
        return instance;
    }

    public boolean addPost(ForumPost post) {

        String url = Statics.BASE_URL+"/forum/add_post_blog_api?title="+ForumPost.getSlug()+"&content="+ForumPost.getBody();
        req.setUrl(url);

        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode()==200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
