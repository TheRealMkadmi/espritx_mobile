package com.espritx.client.services.User;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.PropertyBusinessObject;
import com.espritx.client.entities.Group;
import com.espritx.client.entities.Group;
import com.espritx.client.utils.Statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GroupService {

    public static List<Group> GetAll() {
        Response<List<PropertyBusinessObject>> k = Rest.get(Statics.BASE_URL + "/group").acceptJson().getAsPropertyList(Group.class);
        List<PropertyBusinessObject> res = k.getResponseData();
        List<Group> u = new ArrayList<>(); // why can't streams be used in android build?!
        for (PropertyBusinessObject r : res) {
            u.add((Group) r);
        }
        return u;
    }

    public static Map Create(Group u) {
        return Rest.post(Statics.BASE_URL + "/group/").body(u).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }

    public static Map Update(Group u) {
        return Rest.patch(Statics.BASE_URL + "/group/" + u.id).body(u).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }

    public static Map Delete(Group u) {
        return Rest.delete(Statics.BASE_URL + "/group/" + u.id).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }
}

