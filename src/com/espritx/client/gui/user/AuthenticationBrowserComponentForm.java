/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.user;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.fingerprint.Fingerprint;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.codename1.uikit.pheonixui.InboxForm;
import com.codename1.uikit.pheonixui.SplashForm;
import com.espritx.client.services.User.AuthenticationService;
import com.espritx.client.utils.Statics;
import com.espritx.client.utils.StringUtils;

/**
 * @author Wahib
 */
public class AuthenticationBrowserComponentForm extends BaseForm {


    public static final String VIEWPORT_SCRIPT = "viewport = document.querySelector(\"meta[name=viewport]\");\r\n" +
            "viewport.setAttribute('content', 'width=1920px, height=1920px, initial-scale=0.25, maximum-scale=4.0, user-scalable=1');";

    public AuthenticationBrowserComponentForm() {
        this(Resources.getGlobalResources());
    }

    public AuthenticationBrowserComponentForm(Resources resourceObjectInstance) {
        setInlineStylesTheme(resourceObjectInstance);
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        setTitle("Sign in with Google");
        setName("SignInForm");
        initUserControls(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        getToolbar().setUIID("Container");
        getToolbar().getTitleComponent().setUIID("SigninTitle");
        getContentPane().setUIID("SignInForm");
    }

    private void initUserControls(Resources resourceObjectInstance) {
        setLayout(BorderLayout.center());
        BrowserComponent browser = new BrowserComponent();
        add(BorderLayout.CENTER, browser);
        browser.setHeight(2000);
        browser.setWidth(1000);
        browser.setPreferredH(2000);
        browser.setPreferredW(1000);
        browser.setInlineStylesTheme(resourceObjectInstance);
        browser.addWebEventListener(BrowserComponent.onLoad, (evt1 -> {
            if(browser.getURL().contains("connect") && browser.getURL().contains("state") && !browser.getURL().contains("auth/identifier") && !browser.getURL().contains("challenge")){
                NetworkManager.getInstance().addDefaultHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDgwNDc4NTEsImV4cCI6MTY1MDYzOTg1MSwicm9sZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJ1c2VybmFtZSI6InRlc3Rfc3VwZXJfYWRtaW5fMTM0QGVzcHJpdC50biJ9.Me3z8lPMu6qrPrcpA-K5bTdHWkUQRAAGMYLjlHzWomaBjlPYqmzsvJmcHlkUvcJ2LYNSMxXQeBoAhp6uhUjFN2RAfb-6LxEgEWOTgSjWFxrIXwASUlrd3qNfI6HEH_1CmCkD46S6c4KZ7xbkx9D7-rn5Y919kMgMIXg9Xco-GT3S9LJeAjdIbH9LMi0WIX65zSZ5OGFvWBKbiYvWaeieAf6fwgv7DQltqTHgnUDxvPpjDYek_AXa-dPMl3wquLlroDk4h47VO-aabQ5JIcVBEuZ0JEIo5uxUX5nNzL1pdDh4IQqDmomya2jzGE41oInCVv72HABwtqp4HJvawTLrWQ");
                new ShowGroups().show();
            }
            Log.p(evt1.toString());
        }));
        browser.setURL(Statics.DOMAIN + "/connect/google");
    }
}
