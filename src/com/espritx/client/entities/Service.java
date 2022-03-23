/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.entities;

/**
 * @author Hedi
 */
public class Service {
    private int id;
    private String Name;
    private Group Responsible;
    Group[] Recipient;
    Requests[] ServiceRequests;

    public Service() {
    }

    public Service(int id, String Name, Group Responsible, Group[] Recipient, Requests[] ServiceRequests) {
        this.id = id;
        this.Name = Name;
        this.Responsible = Responsible;
        this.Recipient = Recipient;
        this.ServiceRequests = ServiceRequests;
    }

    public Service(String Name, Group Responsible, Group[] Recipient) {
        this.Name = Name;
        this.Responsible = Responsible;
        this.Recipient = Recipient;
    }

    public Service(String Name) {
        this.Name = Name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Group getResponsible() {
        return Responsible;
    }

    public void setResponsible(Group Responsible) {
        this.Responsible = Responsible;
    }

    public Group[] getRecipient() {
        return Recipient;
    }

    public void setRecipient(Group[] Recipient) {
        this.Recipient = Recipient;
    }

    public Requests[] getServiceRequests() {
        return ServiceRequests;
    }

    public void setServiceRequests(Requests[] ServiceRequests) {
        this.ServiceRequests = ServiceRequests;
    }


}
