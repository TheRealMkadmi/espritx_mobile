package com.espritx.client.gui.ForumPost;

import com.codename1.ui.Button;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;

public class HomeForum extends BaseForm {
    public HomeForum() {
        setTitle("Forum EspritX");
        setLayout(BoxLayout.y());
        Button btnAdd = new Button("Add Blog");
        Button btnShow = new Button("Show Forum");
        btnAdd.addActionListener((evt -> new AddForumPost(this).show()));
        btnShow.addActionListener((evt -> new ShowForum(this).show()));


        installSidemenu(Resources.getGlobalResources());
        addAll(btnAdd,btnShow);


    }
}