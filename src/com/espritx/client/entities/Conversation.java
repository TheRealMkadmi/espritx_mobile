package com.espritx.client.entities;

import java.util.Objects;

public class Conversation {
    private int id;
    private String user1;
    private String user2;
    private String $Email;
    private String $Email2;
    private boolean Chateph;

    public Conversation(int id, String user1, String user2, String $Email, String $Email2, boolean chateph) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.$Email = $Email;
        this.$Email2 = $Email2;
        Chateph = chateph;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setid(int id) {
        this.id = id;
    }
    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public void set$Email(String $Email) {
        this.$Email = $Email;
    }

    public void set$Email2(String $Email2) {
        this.$Email2 = $Email2;
    }

    public void setChateph(boolean chateph) {
        Chateph = chateph;
    }

    public int getId() {
        return id;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public String get$Email() {
        return $Email;
    }

    public String get$Email2() {
        return $Email2;
    }

    public boolean isChateph() {
        return Chateph;
    }

}
