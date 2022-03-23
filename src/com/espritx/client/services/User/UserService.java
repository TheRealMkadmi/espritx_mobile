package com.espritx.client.services.User;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.PropertyBusinessObject;
import com.espritx.client.entities.User;
import com.espritx.client.utils.Statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserService {
    public static List<User> GetAllUsers() {
        Response<List<PropertyBusinessObject>> k = Rest.get(Statics.BASE_URL + "/user").acceptJson().getAsPropertyList(User.class);
        List<PropertyBusinessObject> res = k.getResponseData();
        List<User> u = new ArrayList<>(); // why can't streams be used in android build?!
        for (PropertyBusinessObject r : res) {
            u.add((User) r);
        }
        return u;
    }

    public static Map CreateUser(User u) {
        return Rest.post(Statics.BASE_URL + "/user/").body(u).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }

    public static Map UpdateUser(User u) {
        return Rest.patch(Statics.BASE_URL + "/user/" + u.id).body(u).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }

    public static Map DeleteUser(User u) {
        return Rest.delete(Statics.BASE_URL + "/user/" + u.id).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }
}
