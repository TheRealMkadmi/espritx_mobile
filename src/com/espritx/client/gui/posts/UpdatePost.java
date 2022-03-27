package com.espritx.client.gui.posts;

import com.codename1.components.FloatingHint;
import com.codename1.googlemaps.MapContainer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Post;
import com.espritx.client.services.ServicePost.ServicePost;

public class UpdatePost  extends BaseForm{



        Form current;

    public UpdatePost(Resources res, Post p) {


        super("Feed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current=this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Post");


        super.addSideMenu(res);

        TextField title = new TextField(p.getTitle(), "titre" , 20 , TextField.ANY);
        TextField content = new TextField(p.getContent() , "Description" , 20 , TextField.ANY);
        TextField etat = new TextField(String.valueOf(p.getIsValid()) , "Etat" , 20 , TextField.ANY);



        ComboBox etatCombo = new ComboBox();

        etatCombo.addItem("Non approuveé");

        etatCombo.addItem("approuvée");

        if(p.getIsValid()) {
            etatCombo.setSelectedIndex(0);
        }
        else
            etatCombo.setSelectedIndex(1);





        title.setUIID("NewsTopLine");
        content.setUIID("NewsTopLine");
        etat.setUIID("NewsTopLine");

        title.setSingleLineTextArea(true);
        content.setSingleLineTextArea(true);
        etat.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick btnModifer

        btnModifier.addPointerPressedListener(l ->   {

            p.setTitle(title.getText());
            p.setContent(content.getText());

            if(etatCombo.getSelectedIndex() == 0 ) {
                p.setIsValid(false);
            }
            else
                p.setIsValid(true);


            //appel fonction modfier reclamation men service

            if(ServicePost.getInstance().modifierPost(p)) { // if true
                new ListPosts(res).show();
            }
        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListPosts(res).show();
        });


        Label l2 = new Label("");

        Label l3 = new Label("");

        Label l4 = new Label("");

        Label l5 = new Label("");

        Label l1 = new Label();

        Container contente = BoxLayout.encloseY(
                l1, l2,
                new FloatingHint(title),

                new FloatingHint(content),
                etatCombo,
                btnModifier,
                btnAnnuler


        );

        add(contente);
        show();


    }

}
