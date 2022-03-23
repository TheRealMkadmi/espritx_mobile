/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.user;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.Log;
import com.codename1.properties.InstantUI;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.User;
import com.espritx.client.services.User.AuthenticationService;
import com.espritx.client.services.User.UserService;

/**
 * @author Wahib
 */
public class UserForm extends BaseForm {
    private User user;

    public UserForm() {
        this(Resources.getGlobalResources());
    }

    public UserForm(Resources resourceObjectInstance, User instance) {
        this.user = instance;
        instance.getPropertyIndex().registerExternalizable();
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        setName("AddUserForm");
        setInlineStylesTheme(resourceObjectInstance);
        initUserControls(resourceObjectInstance);
        installSidemenu(resourceObjectInstance);
        getToolbar().setTitleComponent(new Label("Add a User", "Title"));
    }

    public UserForm(Resources resourceObjectInstance) {
        User user = new User();
        user.getPropertyIndex().registerExternalizable();
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        setName("AddUserForm");
        setInlineStylesTheme(resourceObjectInstance);
        initUserControls(resourceObjectInstance);
        installSidemenu(resourceObjectInstance);
        getToolbar().setTitleComponent(new Label("Add a User", "Title"));
    }

    private void initUserControls(Resources resourceObjectInstance) {
        InstantUI iui = new InstantUI();
        iui.excludeProperty(this.user.id);
        iui.excludeProperty(this.user.groups);
        iui.setMultiChoiceLabels(this.user.userStatus, "Active", "Pending", "Inactive", "Restricted");
        iui.setMultiChoiceValues(this.user.userStatus, "active", "pending", "inactive", "restricted");
        iui.setMultiChoiceLabels(this.user.identityType, "ID Card", "Passport", "N/A");
        iui.setMultiChoiceValues(this.user.identityType, "cin", "passport", "Unknown");

        Container cnt = iui.createEditUI(user, true);
        cnt.setScrollableY(true);
        cnt.setInlineStylesTheme(resourceObjectInstance);
        addComponent(BorderLayout.CENTER, cnt);

        Container actionsContainer = new Container(BoxLayout.x());
        Button saveButton = makeButton("SaveButton", "Save");
        saveButton.addActionListener(evt -> {
            Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
            try {
                UserService.UpdateUser(user);
            } catch (Exception e) {
                Log.p(e.getMessage(), Log.ERROR);
                dlg.dispose();
                Dialog.show("error", e.getMessage(), "ok", null);
            }
            dlg.dispose();
            (new ShowUsers()).show();
        });
        actionsContainer.addComponent(saveButton);

        Button deleteButton = makeButton("DeleteButton", "Delete");
        deleteButton.addActionListener(evt -> {
            if(user == AuthenticationService.getAuthenticatedUser()){
                Dialog.show("Error", "You can't delete yourself...", "ok", null);
                return;
            }
            Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
            try {
                UserService.DeleteUser(user);
            } catch (Exception e) {
                Log.p(e.getMessage(), Log.ERROR);
                dlg.dispose();
                Dialog.show("error", e.getMessage(), "ok", null);
            }
            dlg.dispose();
            (new ShowUsers()).show();
        });
        actionsContainer.addComponent(deleteButton);
        addComponent(BorderLayout.SOUTH, actionsContainer);
    }
}
