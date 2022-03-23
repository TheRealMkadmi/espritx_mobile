/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.entities;

import java.util.Date;

/**
 *
 * @author Hedi
 */
public class Requests {
    private int id;
    private Date RespondedAt;
    private Date CreatedAt;
    private Date UpdatedAt;
    private String Title;
    private String Description;
    private Service Type;
    private String Email;
    private String Picture;
    private String Attachement;
    private String Status;
    private String Response;
    private User Requester;

    public Requests(int id, Date RespondedAt, String Title, String Description, Service Type, String Email, String Picture, String Attachement, String Status, String Response, User Requester) {
        this.id = id;
        this.RespondedAt = RespondedAt;
        this.Title = Title;
        this.Description = Description;
        this.Type = Type;
        this.Email = Email;
        this.Picture = Picture;
        this.Attachement = Attachement;
        this.Status = Status;
        this.Response = Response;
        this.Requester = Requester;
    }

    public int getId() {
        return id;
    }

    public Date getRespondedAt() {
        return RespondedAt;
    }

    public void setRespondedAt(Date RespondedAt) {
        this.RespondedAt = RespondedAt;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Service getType() {
        return Type;
    }

    public void setType(Service Type) {
        this.Type = Type;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }

    public String getAttachement() {
        return Attachement;
    }

    public void setAttachement(String Attachement) {
        this.Attachement = Attachement;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String Response) {
        this.Response = Response;
    }

    public User getRequester() {
        return Requester;
    }

    public void setRequester(User Requester) {
        this.Requester = Requester;
    }
    
    
}
