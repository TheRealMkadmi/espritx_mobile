package com.espritx.client.services.Service;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.PropertyBusinessObject;
import com.espritx.client.entities.Request;
import com.espritx.client.entities.User;
import com.espritx.client.utils.Statics;

import java.util.ArrayList;
import java.util.List;

public class RequestService {
    public static List<Request> GetAll() {
        Response<List<PropertyBusinessObject>> k = Rest.get(Statics.BASE_URL + "/request/showAll").acceptJson().getAsPropertyList(Request.class);
        List<PropertyBusinessObject> res = k.getResponseData();
        List<Request> RequestList = new ArrayList<>();
        for (PropertyBusinessObject r : res) {
            RequestList.add((Request) r);
        }
        return RequestList;
    }

    public static List<Request> GetUserRequests() {
        Response<List<PropertyBusinessObject>> k = Rest.get(Statics.BASE_URL + "/request/showUser").acceptJson().getAsPropertyList(Request.class);
        List<PropertyBusinessObject> res = k.getResponseData();
        List<Request> RequestList = new ArrayList<>();
        for (PropertyBusinessObject r : res) {
            RequestList.add((Request) r);
        }
        return RequestList;
    }

    public static List<Request> GetServiceRequests() {
        Response<List<PropertyBusinessObject>> k = Rest.get(Statics.BASE_URL + "/request/showService").acceptJson().getAsPropertyList(Request.class);
        List<PropertyBusinessObject> res = k.getResponseData();
        List<Request> RequestList = new ArrayList<>();
        for (PropertyBusinessObject r : res) {
            RequestList.add((Request) r);
        }
        return RequestList;
    }

    public static List<Request> GetGroupRequests() {
        Response<List<PropertyBusinessObject>> k = Rest.get(Statics.BASE_URL + "/request/showGroup").acceptJson().getAsPropertyList(Request.class);
        List<PropertyBusinessObject> res = k.getResponseData();
        List<Request> RequestList = new ArrayList<>();
        for (PropertyBusinessObject r : res) {
            RequestList.add((Request) r);
        }
        return RequestList;
    }
}
