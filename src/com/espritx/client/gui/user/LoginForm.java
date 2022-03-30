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
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.codename1.uikit.pheonixui.InboxForm;
import com.codename1.uikit.pheonixui.SplashForm;
import com.espritx.client.services.User.AuthenticationService;
import com.espritx.client.utils.StringUtils;

/**
 * @author Wahib
 */
public class LoginForm extends BaseForm {

    public static final String CREDENTIAL_SEPERATOR = ";/////;";
    private static final String authenticationBucket = "credentials";


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
        profilePicture.setIcon(resourceObjectInstance.getImage("logo.png"));
        formContainer.addComponent(profilePicture);

        ComponentGroup credentialsContainer = makeComponentGroup("CredentialsContainer");
        TextField emailTextField = makeTextField("EmailTextField", "@esprit.tn e-mail", "test_super_admin_618@esprit.tn");
        emailTextField.setConstraint(TextField.EMAILADDR);
        credentialsContainer.addComponent(emailTextField);
        TextField passwordTextField = makeTextField("PasswordTextField", "Password", "12345");
        passwordTextField.setConstraint(TextField.PASSWORD);
        credentialsContainer.addComponent(passwordTextField);
        formContainer.addComponent(credentialsContainer);
        Button loginButton = makeButton("SignInButton", "Sign In");
        CheckBox remember_biometrically = new CheckBox("Remember biometrically");
        remember_biometrically.setSelected(false);
        if (Fingerprint.isAvailable()) {
            formContainer.add(remember_biometrically);
            FloatingActionButton fab = FloatingActionButton.createBadge("Biometric Login");
            fab.addActionListener(evt -> {
                Fingerprint.getPassword(
                        "Get selected account from device",  // Message to display in auth dialog
                        authenticationBucket
                ).onResult((password, err) -> {
                    if (err != null) {
                        Log.e(err);
                        Log.p("Failed to get password: " + err.getMessage());
                    } else {
                        Log.p("The password was " + password);
                        String[] fragments = StringUtils.split(password, CREDENTIAL_SEPERATOR);
                        Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
                        try {
                            AuthenticationService.Authenticate(fragments[0], fragments[1]);
                            dlg.dispose();
                            new InboxForm(resourceObjectInstance).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            dlg.dispose();
                        }
                    }
                });
            });
        }

        loginButton.addActionListener(evt -> {
            Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
            try {
                String email = emailTextField.getText();
                String password = passwordTextField.getText();
                String token = email + CREDENTIAL_SEPERATOR + password;
                AuthenticationService.Authenticate(email, password);
                dlg.dispose();
                if (remember_biometrically.isSelected()) {
                    Fingerprint.addPassword(
                            "Save account to device", // Message to display in authentication dialog
                            authenticationBucket,
                            token
                    ).onResult((success, err) -> {
                        if (err != null) {
                            Log.e(err);
                            ToastBar.showErrorMessage("Failed to save password: " + err.getMessage());
                        } else {
                            ToastBar.showInfoMessage("Successfully saved password.");
                        }
                    });
                }

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
