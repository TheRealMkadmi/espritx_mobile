package com.espritx.client.services.Service;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.espritx.client.entities.Service;
import com.espritx.client.utils.Statics;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ServiceService {

    public ArrayList<Service> services;

    public static ServiceService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceService() {
        req = new ConnectionRequest();
    }

    public static ServiceService getInstance() {
        if (instance == null) {
            instance = new ServiceService();
        }
        return instance;
    }

    public ArrayList<Service> parseServices(String jsonText){
        try {
            services=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> servicesListJson =
                    j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)servicesListJson.get("root");
            for(Map<String,Object> obj : list){
                Service S = new Service();
                float id = Float.parseFloat(obj.get("id").toString());
                S.setId((int)id);
                if (obj.get("Name")==null)
                    S.setName("null");
                else
                    S.setName(obj.get("Name").toString());
                services.add(S);
            }
        } catch (IOException ex) {

        }
        return services;
    }

    public ArrayList<Service> getAllServices(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/service/show/";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                services = parseServices(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return services;
    }

    public boolean addService(Service S) {
        String url = Statics.BASE_URL + "/service/new";
        req.setUrl(url);
        req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
