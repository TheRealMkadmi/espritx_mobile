/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.user;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.codename1.uikit.pheonixui.InboxForm;
import com.codename1.uikit.pheonixui.SplashForm;
import com.espritx.client.services.User.AuthenticationService;

/**
 * @author Wahib
 */
public class LoginForm extends BaseForm {
    public LoginForm() {
        this(Resources.getGlobalResources());
    }

    public LoginForm(Resources resourceObjectInstance) {
        setInlineStylesTheme(resourceObjectInstance);
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        setTitle("Sign in");
        setName("SignInForm");
        initUserControls(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        getToolbar().setUIID("Container");
        getToolbar().getTitleComponent().setUIID("SigninTitle");
        FontImage mat = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, "SigninTitle", 3.5f);
        getToolbar().addCommandToLeftBar("", mat, e -> new SplashForm().show());
        getContentPane().setUIID("SignInForm");
    }

    private void initUserControls(Resources resourceObjectInstance) {
        Container formContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        formContainer.setScrollableY(true);
        formContainer.setInlineStylesTheme(resourceObjectInstance);
        addComponent(BorderLayout.CENTER, formContainer);

        Label profilePicture = new Label();
        profilePicture.setUIID("CenterLabel");
        profilePicture.setInlineStylesTheme(resourceObjectInstance);
        profilePicture.setIcon(resourceObjectInstance.getImage("profile_image.png"));
        formContainer.addComponent(profilePicture);

        ComponentGroup credentialsContainer = makeComponentGroup("CredentialsContainer");
        TextField emailTextField = makeTextField("EmailTextField", "@esprit.tn e-mail", "test_super_admin_134@esprit.tn");
        emailTextField.setConstraint(TextField.EMAILADDR);
        credentialsContainer.addComponent(emailTextField);
        TextField passwordTextField = makeTextField("PasswordTextField", "Password", "12345");
        credentialsContainer.addComponent(passwordTextField);
        formContainer.addComponent(credentialsContainer);
        Button loginButton = makeButton("SignInButton", "Sign In");

        loginButton.addActionListener(evt -> {
            Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
            try {
                AuthenticationService.Authenticate(emailTextField.getText(), passwordTextField.getText());
                dlg.dispose();
                new InboxForm().show();
            } catch (Exception e) {
                Log.p(e.getMessage(), Log.ERROR);
                dlg.dispose();
                Dialog.show("error", e.getMessage(), "ok", null);
            }
        });

        formContainer.addComponent(loginButton);
        formContainer.addComponent(makeButton("ForgotPasswordContainer", "Forgot your Password?", "CenterLabelSmall"));
        addComponent(BorderLayout.SOUTH, makeButton("RegisterButton", "Create New Account", "CenterLabel"));
    }
}
