package com.espritx.client.services.User;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.properties.PropertyBusinessObject;
import com.espritx.client.entities.User;
import com.espritx.client.utils.Statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Any sane person might ask; why in the fuck isn't this a GenericCrudService<T>. You'd be right.
// Reflection isn't supported to get parametrized types.
// Because who the fuck includes a vTable when mangling their names anymore?

public class UserService {
    public static List<User> GetAll() {
        Response<List<PropertyBusinessObject>> k = Rest.get(Statics.BASE_URL + "/user").acceptJson().getAsPropertyList(User.class);
        List<PropertyBusinessObject> res = k.getResponseData();
        List<User> u = new ArrayList<>(); // why can't streams be used in android build?!
        for (PropertyBusinessObject r : res) {
            u.add((User) r);
        }
        return u;
    }

    public static Map Create(User u) {
        return Rest.post(Statics.BASE_URL + "/user/").body(u).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }

    public static Map Update(User u) {
        return Rest.patch(Statics.BASE_URL + "/user/" + u.id).body(u).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }

    public static Map Delete(User u) {
        return Rest.delete(Statics.BASE_URL + "/user/" + u.id).jsonContent().acceptJson().getAsJsonMap().getResponseData();
    }
}
