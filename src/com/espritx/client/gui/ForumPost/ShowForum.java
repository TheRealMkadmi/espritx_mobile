package com.espritx.client.gui.ForumPost;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.pheonixui.BaseForm;

public class ShowForum extends BaseForm {
    public ShowForum(Form previous) {
        setTitle("Show Forum EspritX");
        setLayout(BoxLayout.y());
        Button btnFind = new Button("find post");

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,(evt -> {
            previous.showBack();
        }));

        addAll(btnFind);
    }


}