package com.espritx.client.entities;

import com.codename1.properties.*;

import java.util.Date;

public class Request implements PropertyBusinessObject {
    public final IntProperty<Request> id = new IntProperty<>("id");
    public final Property<Date, Request> RespondedAt = new Property<>("RespondedAt");
    public final Property<Date, Request> CreatedAt = new Property<>("CreatedAt");
    public final Property<Date, Request> UpdatedAt = new Property<>("UpdatedAt");
    public final Property<String, Request> Title = new Property<>("Title");
    public final Property<String, Request> Description = new Property<>("Description");
    public final Property<Service, Request> Type = new Property<>("Type");
    public final Property<String, Request> Email = new Property<>("Email");
    public final Property<String, Request> Picture = new Property<>("Picture");
    public final Property<String, Request> Attachement = new Property<>("Attachement");
    public final Property<String, Request> Status = new Property<>("Status");
    public final Property<String, Request> Response = new Property<>("Response");
    public final Property<User, Request> Requester = new Property<>("Requester");

    public final PropertyIndex index = new PropertyIndex(this, "Request",
            id, RespondedAt,CreatedAt,UpdatedAt,Title,Description,Type,Email,Picture,Attachement,Status,Response,Requester
    );

    @Override
    public PropertyIndex getPropertyIndex() {
        return index;
    }

    public Request(){
        Title.setLabel("Title");
        Description.setLabel("Description");
        Email.setLabel("Email");
        Picture.setLabel("Picture");
        Attachement.setLabel("Attachement");
    }


}
