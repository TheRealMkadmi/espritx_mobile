package com.espritx.client.gui.ForumPost;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Post;
import com.espritx.client.services.ServicePost.ServicePost;

import java.util.List;

public class SearchForumPost extends BaseForm {
    public SearchForumPost(){

        int fiveMM = Display.getInstance().convertToPixels(5);
        // final Image finalDuke = duke.scaledWidth(fiveMM);
        Toolbar.setGlobalToolbar(true);
        Form hi = new Form("Search", BoxLayout.y());
        hi.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(()-> {
            // this will take a while...
            List<Post> posts= ServicePost.getInstance().afficherAllPosts();
            Display.getInstance().callSerially(() -> {
                hi.removeAll();
                for(Post c : posts) {
                    MultiButton m = new MultiButton();
                    m.setTextLine1(c.getTitle());
                    m.setTextLine2(c.getContent());


                    //    m.setIcon(finalDuke);

                    hi.add(m);
                }
                hi.revalidate();
            });
        });

        hi.getToolbar().addSearchCommand(e -> {
            String text = (String)e.getSource();
            if(text == null || text.length() == 0) {
                // clear search
                for(Component cmp : hi.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                hi.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for(Component cmp : hi.getContentPane()) {
                    MultiButton mb = (MultiButton)cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                hi.getContentPane().animateLayout(150);
            }
        }, 4);
        hi.show();
    }
}
