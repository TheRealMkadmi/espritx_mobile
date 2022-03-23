/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.posts;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.espritx.client.entities.Post;
import com.espritx.client.services.ServicePost.ServicePost;

/**
 *
 * @author mouna
 */
public class AddPostForm extends Form  {
// passer en parametre la Form precedente
    public AddPostForm(Form previous) {
        setTitle("Ajouter Post");
        setLayout(BoxLayout.y());
        TextField tfTitle=new TextField("","Taper le titre de post");
        TextField tfContent=new TextField("","Taper le contenu de post");
        Button btnAdd=new Button("Publier");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                // Ajouter  un control de saisi 
                if(tfTitle.getText().equals("")){
                    Dialog.show("Error", "le titre doit avoir au moins trois caracteres", "OK", null);}
                       else
                {
                    try {
                        Post p = new Post(tfTitle.getText().toString(), tfContent.getText().toString());
                        if( ServicePost.getInstance().AddPost(p))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        
        
        
        
        getToolbar().addMaterialCommandToLeftBar("Posts", FontImage.MATERIAL_POST_ADD, (evt)->{
        previous.showBack();
        });
        addAll(tfTitle,tfContent,btnAdd);
        
        
    }
    
}
