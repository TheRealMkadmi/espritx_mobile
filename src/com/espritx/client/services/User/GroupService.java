package com.espritx.client.services.User;

import ca.weblite.codename1.json.JSONObject;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.processing.Result;
import com.codename1.properties.PropertyBusinessObject;
import com.espritx.client.entities.Group;
import com.espritx.client.entities.Group;
import com.espritx.client.entities.User;
import com.espritx.client.services.AbstractService;
import com.espritx.client.utils.Statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupService extends AbstractService {

    public static List<Group> GetAll() {
        return fetchListFrom(Statics.BASE_URL + "/group", Group.class);

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

