/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.user;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.properties.InstantUI;
import com.codename1.properties.PropertyIndex;
import com.codename1.properties.UiBinding;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Group;
import com.espritx.client.entities.User;
import com.espritx.client.services.User.GroupService;
import com.espritx.client.services.User.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wahib
 */
public class GroupForm extends BaseForm {
    private Group group;

    public GroupForm() {
        this(Resources.getGlobalResources());
    }

    public GroupForm(Resources resourceObjectInstance, Group instance) {
        this.group = instance;
        instance.getPropertyIndex().registerExternalizable();
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        setName("AddUserForm");
        setInlineStylesTheme(resourceObjectInstance);
        initUserControls(resourceObjectInstance);
        installSidemenu(resourceObjectInstance);
        getToolbar().setTitleComponent(new Label("User Details", "Title"));
    }

    public GroupForm(Resources resourceObjectInstance) {
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
        //// Basic information
        InstantUI iui = new InstantUI();
        iui.excludeProperty(this.group.id);
        iui.setMultiChoiceLabels(this.group.group_type, "Super Admin", "Student", "Site Staff", "Faculty Staff", "Teachers");
        iui.setMultiChoiceValues(this.group.group_type, "Super admin", "Student", "Site staff", "Faculty staff", "Teachers");
        Container cnt = iui.createEditUI(group, true);
        cnt.setScrollableY(true);
        cnt.setInlineStylesTheme(resourceObjectInstance);
        addComponent(BorderLayout.NORTH, cnt);

        //////// Group members.
        if (group.id.get() != null) {
            UiBinding ui = new UiBinding();
            List<User> listOfGroupUsers = GroupService.GetMembers(group);
            User prot = new User();
            UiBinding.BoundTableModel tb = ui.createTableModel(listOfGroupUsers, prot);
            tb.excludeProperty(prot.groups);
            tb.excludeProperty(prot.avatarFile);
            tb.excludeProperty(prot.classe);
            tb.excludeProperty(prot.identityType);
            tb.excludeProperty(prot.identityDocumentNumber);
            tb.excludeProperty(prot.phoneNumber);
            tb.excludeProperty(prot.plainPassword);
            tb.excludeProperty(prot.userStatus);

            Table t = new Table(tb);
            t.setSortSupported(true);
            t.setScrollVisible(true);
            t.setScrollableY(true);
            t.setScrollableX(true);
            t.setInlineStylesTheme(resourceObjectInstance);
            addComponent(BorderLayout.CENTER, t);
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
                Dialog d = new Dialog("Add a user");
                d.setLayout(new BorderLayout());
                final DefaultListModel<String> options = new DefaultListModel<>();
                AutoCompleteTextField ac = new AutoCompleteTextField(options) {
                    @Override
                    protected boolean filter(String text) {
                        if (text.length() < 3) {
                            return false;
                        }
                        ArrayList<String> l = UserService.AutoComplete(text);
                        if (l == null || l.size() == 0) {
                            return false;
                        }
                        options.removeAll();
                        for (String s : l) {
                            options.addItem(s);
                        }
                        return true;
                    }
                };
                ac.setMinimumElementsShownInPopup(2);
                ac.setPopupPosition(AutoCompleteTextField.POPUP_POSITION_UNDER);

                Button button = this.makeButton("Add", "Add");
                button.addActionListener(evt -> {
                    Map result = GroupService.AddUserToGroup(ac.getText(), group);
                    String message = (String) result.get("result");
                    if (message.equals("User added successfully")) { // probably should have been a separate property. But heh.
                        User u = new User();
                        u.getPropertyIndex().populateFromMap((Map) result.get("user"));
                        tb.addRow(tb.getRowCount(), u);
                    }
                    ToastBar.setDefaultMessageTimeout(5000);
                    ToastBar.showInfoMessage(message);
                    d.dispose();
                });
                d.addComponent(BorderLayout.SOUTH, button);
                d.add(BorderLayout.CENTER, ac);
                d.show();
            });

            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_REMOVE, e -> {
                if (t.getSelectedRow() > -1) {
                    tb.removeRow(t.getSelectedRow());
                    Integer id = (Integer) t.getModel().getValueAt(t.getSelectedRow(), 0);
                    Map result = GroupService.RemoveUserFromGroup(id, group);
                    String message = (String) result.get("result");
                    ToastBar.setDefaultMessageTimeout(5000);
                    ToastBar.showInfoMessage(message);
                }
            });
        }
        /// Actions
        ArrayList<Button> actions = new ArrayList<Button>();
        Button saveButton = makeButton("SaveButton", "Save");
        saveButton.addActionListener(evt -> {
            Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
            try {
                if (group.id.get() != null) {
                    GroupService.Update(group);
                } else {
                    GroupService.Create(group);
                }
            } catch (Exception e) {
                Log.p(e.getMessage(), Log.ERROR);
                dlg.dispose();
                Dialog.show("error", e.getMessage(), "ok", null);
            }
            dlg.dispose();
            (new ShowUsers()).show();
        });
        actions.add(saveButton);

        if (group.id.get() != null) {
            Button deleteButton = makeButton("DeleteButton", "Delete");
            deleteButton.addActionListener(evt -> {
                Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
                try {
                    GroupService.Delete(group);
                } catch (Exception e) {
                    Log.p(e.getMessage(), Log.ERROR);
                    dlg.dispose();
                    Dialog.show("error", e.getMessage(), "ok", null);
                }
                dlg.dispose();
                (new ShowUsers()).show();
            });
            actions.add(deleteButton);
        }
        Container actionsContainer = BoxLayout.encloseXCenter(actions.toArray(new Button[0]));
        addComponent(BorderLayout.SOUTH, actionsContainer);
    }
}
