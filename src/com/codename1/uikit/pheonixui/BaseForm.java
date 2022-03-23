/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.codename1.uikit.pheonixui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.espritx.client.gui.posts.HomeForm;
import com.espritx.client.gui.service.ShowForm;
import com.espritx.client.gui.user.LoginForm;
import com.espritx.client.gui.user.ShowUsers;
import com.espritx.client.services.User.AuthenticationService;

/**
 * Utility methods common to forms e.g. for binding the side menu
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {
    private Resources resources;

    public void installSidemenu(Resources res) {
        this.resources = res;
        Image selection = res.getImage("selection-in-sidemenu.png");

        Image inboxImage = null;
        if (isCurrentInbox()) inboxImage = selection;

        Image trendingImage = null;
        if (isCurrentTrending()) trendingImage = selection;

        Image calendarImage = null;
        if (isCurrentCalendar()) calendarImage = selection;

        Image statsImage = null;
        if (isCurrentStats()) statsImage = selection;

        Button inboxButton = new Button("Inbox", inboxImage);
        inboxButton.setUIID("SideCommand");
        inboxButton.getAllStyles().setPaddingBottom(0);
        Container inbox = FlowLayout.encloseMiddle(inboxButton, new Label("18", "SideCommandNumber"));
        inbox.setLeadComponent(inboxButton);
        inbox.setUIID("SideCommand");
        inboxButton.addActionListener(e -> new InboxForm().show());
        getToolbar().addComponentToSideMenu(inbox);
        getToolbar().addCommandToSideMenu("Calendar", calendarImage, e -> new CalendarForm(res).show());
        getToolbar().addCommandToSideMenu("Service", null, e -> new ShowForm(res).show());
        getToolbar().addCommandToSideMenu("Trending", trendingImage, e -> new TrendingForm(res).show());
        getToolbar().addCommandToSideMenu("Settings", null, e -> {
        });
        getToolbar().addCommandToSideMenu("Posts", null, e -> new HomeForm().show());
        getToolbar().addCommandToSideMenu("Manage Users", null, e -> new ShowUsers(res).show());
        getToolbar().addCommandToSideMenu("Logout", null, e -> {
            AuthenticationService.Deauthenticate();
            new LoginForm(resources).show();
        });
        // getToolbar().addMaterialCommandToLeftBar("Posts", FontImage.MATERIAL_POST_ADD, (evt)->{});

        // spacer
        getToolbar().addComponentToSideMenu(new Label(" ", "SideCommand"));
        getToolbar().addComponentToSideMenu(new Label(res.getImage("profile_image.png"), "Container"));
        getToolbar().addComponentToSideMenu(new Label("Detra Mcmunn", "SideCommandNoPad"));
        getToolbar().addComponentToSideMenu(new Label("Long Beach, CA", "SideCommandSmall"));
    }

    protected Button makeButton(String name, String text, String uiid) {
        Button button = this.makeButton(name, text);
        button.setUIID(uiid);
        return button;
    }

    protected Button makeButton(String name, String text) {
        Button button = new Button();
        button.setText(text);
        if (name != null)
            button.setName(name);
        button.setInlineStylesTheme(this.resources);
        return button;
    }

    protected TextField makeTextField(String name, String hint, String text) {
        TextField textField = this.makeTextField(name, hint);
        textField.setText(text);
        return textField;
    }

    protected TextField makeTextField(String name, String hint) {
        TextField textField = new TextField();
        textField.setHint(hint);
        if (name != null)
            textField.setName(name);
        textField.setInlineStylesTheme(this.resources);
        return textField;
    }

    protected ComponentGroup makeComponentGroup(String name) {
        ComponentGroup credentialsContainer = new ComponentGroup();
        credentialsContainer.setInlineStylesTheme(this.resources);
        if (name != null)
            credentialsContainer.setName(name);
        return credentialsContainer;
    }

    protected boolean isCurrentInbox() {
        return false;
    }

    protected boolean isCurrentTrending() {
        return false;
    }

    protected boolean isCurrentCalendar() {
        return false;
    }

    protected boolean isCurrentStats() {
        return false;
    }
}
