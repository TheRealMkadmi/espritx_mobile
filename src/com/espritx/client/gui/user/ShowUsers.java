/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.user;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.User;
import com.espritx.client.services.User.UserService;

import java.util.List;

/**
 * @author Wahib
 */
public class ShowUsers extends BaseForm {
    private UserService userService;
    public ShowUsers() {
        this(Resources.getGlobalResources());
    }

    public ShowUsers(Resources resourceObjectInstance) {
        this.userService = new UserService();
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setInlineStylesTheme(resourceObjectInstance);
        initUserControls(resourceObjectInstance);
        setTitle("Manage Users");
        setName("ManageUsers");
        installSidemenu(resourceObjectInstance);
    }

    private void initUserControls(Resources resourceObjectInstance) {
        Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
        List<User> userList = this.userService.GetAll();
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        list.setInlineStylesTheme(resourceObjectInstance);
        int size = Display.getInstance().convertToPixels(8, true);
        EncodedImage placeholder = EncodedImage.createFromImage(FontImage.createFixed("" + FontImage.MATERIAL_PERSON, FontImage.getMaterialDesignFont(), 0xff, size, size), true);
        for (User user : userList) {
            MultiButton mb = new MultiButton(user.first_name.get() + " " + user.last_name.get());
            mb.setInlineStylesTheme(resourceObjectInstance);
            mb.setIcon(placeholder);
            mb.setUIIDLine1("SlightlySmallerFontLabelLeft");
            mb.setTextLine2(user.email.get());
            mb.setUIIDLine2("RedLabel");
            mb.addActionListener(e -> {
                new UserForm(resourceObjectInstance, user).show();
            });
            list.addComponent(mb);
            mb.putClientProperty("id", user.id.getInt());
            if (user.avatarFile.get() != null) {
                Display.getInstance().scheduleBackgroundTask(() -> {
                    Display.getInstance().callSerially(() -> {
                        String[] fragments = user.avatarFile.get().split("/");
                        String storageName = fragments[fragments.length - 1];
                        URLImage photo = URLImage.createToStorage(placeholder, storageName, user.avatarFile.get());
                        photo.fetch();
                        mb.setIcon(photo.scaled(size, size));
                        mb.revalidate();
                    });
                });
            }
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
        getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_PERSON_ADD, "FloatingActionButton",4f), (e) -> {
            (new UserForm(resourceObjectInstance, new User())).show();
        });
        dlg.dispose();
    }
}
