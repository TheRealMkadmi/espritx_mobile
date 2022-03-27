/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.posts;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ShareButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.codename1.util.StringUtil;
import com.espritx.client.entities.Commentaire;
import com.espritx.client.entities.Post;


import com.espritx.client.services.ServiceCommentaire.ServiceCommentaire;
import com.espritx.client.services.ServicePost.ServicePost;


import com.espritx.client.utils.StringUtils;

import java.util.List;

/**
 * @author mouna
 */
public class Admin extends BaseForm {

    public Admin() {
        this(Resources.getGlobalResources());
    }

    public Admin(Resources resourceObjectInstance) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setInlineStylesTheme(resourceObjectInstance);
      initPostControls(resourceObjectInstance);
        setTitle("Gerer les posts ");
        setName("Gerer les posts");
        installSidemenu(resourceObjectInstance);
    }

    private void initPostControls(Resources resourceObjectInstance) {
        Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
        List<Post> posts = ServicePost.getInstance().afficherAllPosts() ;

        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        list.setInlineStylesTheme(resourceObjectInstance);
        int size = Display.getInstance().convertToPixels(8, true);
        EncodedImage placeholder = EncodedImage.createFromImage(FontImage.createFixed("" + FontImage.MATERIAL_POST_ADD, FontImage.getMaterialDesignFont(), 0xff, size, size), true);



        for (Post p: posts) {

            MultiButton mb = new MultiButton(p.getTitle()+ " creé le " +p.getCreated_at() );




            mb.setInlineStylesTheme(resourceObjectInstance);
            mb.setIcon(placeholder);
            mb.setUIIDLine1("SlightlySmallerFontLabelLeft");

            mb.setUIIDLine2("RedLabel");



            Label lSupprimer=new Label(" ");
            lSupprimer.setUIID("NewsTopLine2");
            Style supprimerStyle=new Style(lSupprimer.getUnselectedStyle());
            FontImage supprimerImage= FontImage.createMaterial(FontImage.MATERIAL_DELETE,supprimerStyle);


            lSupprimer.setIcon(supprimerImage);
            lSupprimer.setTextPosition(RIGHT);
            supprimerStyle.setFgColor(0xf21f1f);
















            lSupprimer.addPointerPressedListener(l->{
                Dialog dig = new Dialog("Suppression Post");
                if(dig.show("Suppression","Cher utilisateur voulez vous vraiment supprimer ce post ?","Annuler","Oui")){
                    dig.dispose();

                }
                else

                    dig.dispose();
                if(ServicePost.getInstance().deletePost(p.getId())){

                    InfiniteProgress ip = new InfiniteProgress();;// loading after insert data
                    final Dialog idialog = ip.showInfiniteBlocking();
                    idialog.dispose();
                    ToastBar.showMessage(" cher user votre le post a eté supprimé", FontImage.MATERIAL_DELETE);
                    // this.current.refreshTheme();
                    this.refreshTheme();

                }




            });



            Label lModifier=new Label(" ");

                lModifier.setUIID("NewsTopLine2");
                Style modifierStyle = new Style(lModifier.getUnselectedStyle());
                FontImage modifierImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
                lModifier.setIcon(modifierImage);
                lModifier.setTextPosition(LEFT);
                modifierStyle.setFgColor(0xf7ad02);


                lModifier.addPointerPressedListener(l -> {
                    new UpdatePost(resourceObjectInstance, p).show();

                });






          //  Label validtxt = new Label("isValid"+p.getIsValid().toString(),"NewsTopLine2");
            //   if(isValid)

        /*    if( p.getIsValid() == true){
                validtxt.setText("approuvée") ;}
            else{

                validtxt.setText("non approuvée");

            }
*/


            ShareButton sb = new ShareButton();
            sb.setText("Share");


          //  list.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(mb),BoxLayout.encloseX(lSupprimer)));

            list.addComponent(mb);
            list.add(BoxLayout.encloseX(lSupprimer,lModifier,sb));

      //      mb.putClientProperty("id", p.getId());

        }
        addComponent(list);
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                for (Component cmp : list) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
            } else {
                text = text.toLowerCase();
                for (Component cmp : list) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().contains(text) ||
                            line2 != null && line2.toLowerCase().contains(text);
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
            }
            list.animateLayout(150);
        }, 4);
        getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_POST_ADD, "FloatingActionButton", 4f), (e) -> {
            (new AjoutPost(resourceObjectInstance)).show();
        });
        dlg.dispose();
    }
}
