/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.entities;

import java.util.Date;



/**
 *
 * @author mouna
 */
public class Post {
    private int id;
    private String title;
    private String content;
    private String slug;
    private Date createdAt;
    //private user
    

    // Id auto increment
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" + "title=" + title + ", content=" + content + '}';
    }

    
}
