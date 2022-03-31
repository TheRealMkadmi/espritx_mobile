package com.espritx.client.gui.service;

import com.codename1.components.*;
//import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.Log;
import com.codename1.properties.InstantUI;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Group;
import com.espritx.client.entities.Request;
import com.espritx.client.entities.Service;
import com.espritx.client.entities.User;
import com.espritx.client.gui.user.ShowUsers;
import com.espritx.client.services.Service.RequestService;
import com.espritx.client.services.Service.ServiceService;
import com.espritx.client.services.User.GroupService;
import com.espritx.client.services.User.UserService;

import java.util.ArrayList;
import java.util.List;

public class AddRequestForm extends BaseForm {
    private Request request;
    private RequestService RequestService;

    public AddRequestForm() {
        this(Resources.getGlobalResources());
    }

    public AddRequestForm(Resources resourceObjectInstance) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setInlineStylesTheme(resourceObjectInstance);
        setTitle("Ask a request");
        setLayout(BoxLayout.y());

        TextField tfTitle = new TextField("", "Request Title");

        TextField tfDesc = new TextField("", "Request Description");

        List<Service> serviceList = ServiceService.getInstance().getAllServices();

        Label Type = new Label("Request Type");

        RadioButtonList radioButtonList = new RadioButtonList(new DefaultListModel());
        radioButtonList.setLayout(BoxLayout.y());
        for (Service S : serviceList) {
            radioButtonList.getMultiListModel().addItem(S);
        }

        Button b = new Button("Pick A Service");
        b.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            d.add(radioButtonList);
            radioButtonList.addActionListener(ee -> {
                b.setText(radioButtonList.getModel().getItemAt(radioButtonList.getModel().getSelectedIndex()).toString());
                d.dispose();
            });
            d.showPopupDialog(b);
            d.removeComponent(radioButtonList);
        });

        TextField tfEmail = new TextField("", "Email to receive answer on.");

        Label tfHint = new Label("Default value: your email");

        Button btnValider = new Button("Ask Request");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfTitle.getText().length() == 0)
                    Dialog.show("Alert", "Please add a Request Title", new Command("Sorry"));
                else if (tfDesc.getText().length() == 0)
                    Dialog.show("Alert", "Please describe your request", new Command("Right now!"));
                else if ((radioButtonList.getModel().getSelectedIndex()) == 0)
                    Dialog.show("Alert", "Please add a responsible for the service", new Command("On it"));
                else {
                    Service Type = (Service) radioButtonList.getModel().getItemAt(radioButtonList.getModel().getSelectedIndex());

                    Request req = new Request();
                    req.Title.set(tfTitle.getText());
                    req.Description.set(tfDesc.getText());
                    req.Type.set(Type);
                    if (tfEmail.getText().length() != 0)
                        req.Email.set(tfEmail.getText());
                    else req.Email.set("");
                    try {
                        RequestService.CreateRequest(req);
                        Dialog success = new Dialog("Success");
                        success.setLayout(BoxLayout.y());
                        Button btn = new Button("Great");
                        btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                Form f = new ShowRequestUserForm();
                                f.show();
                            }
                        });

                        success.add(new SpanLabel("Request sent successfully", "DialogBody"));
                        success.add(btn);
                        success.show();
                    } catch (Exception e) {
                        Log.p(e.getMessage(), Log.ERROR);
                        Dialog.show("error", e.getMessage(), "ok", null);
                    }
                }
            }
        });
        addAll(tfTitle, tfDesc, Type, b, tfEmail, tfHint, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ShowRequestUserForm().show());
        setTitle("Add Request");
        setName("AddRequest");
    }

    public AddRequestForm(Resources resourceObjectInstance, Request instance) {
        setLayout(new BorderLayout());

        final String[] newPicturePath = new String[1];
        Container pictureContainer = new Container(BoxLayout.x());
        instance.getEncodedPic(6);
        pictureContainer.add(instance.getEncodedPic(6));
        pictureContainer.setName("Picture Container");
        Label filePickerStatus = new Label("No file chosen.");
        pictureContainer.add(filePickerStatus);
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_ADD, new Style());
        ScaleImageButton scaleImageButton = new ScaleImageButton(icon);
        scaleImageButton.addActionListener((evt) -> {
            ActionListener callback = e -> {
                if (e != null && e.getSource() != null) {
                    newPicturePath[0] = (String) e.getSource();
                    filePickerStatus.setText("Selected File");
                }
            };
/*
            if (FileChooser.isAvailable()) {

                FileChooser.showOpenDialog(".png,image/png,.jpg,image/jpg,.jpeg", callback);
            } else {
                Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
            }

 */
        });
        pictureContainer.add(scaleImageButton);

        /*final String[] newFilePath = new String[1];
        Container FileContainer = new Container(BoxLayout.x());
        pictureContainer.setName("Picture Container");
        Label filePickerStatus1 = new Label("No file chosen.");
        FileContainer.add(filePickerStatus1);
        Image icon1 = FontImage.createMaterial(FontImage.MATERIAL_ADD, new Style());
        ScaleImageButton scaleImageButton1 = new ScaleImageButton(icon1);
        scaleImageButton1.addActionListener((evt) -> {
            ActionListener callback = e -> {
                if (e != null && e.getSource() != null) {
                    newFilePath[0] = (String) e.getSource();
                    filePickerStatus.setText("Selected File");
                }
            };

            if (FileChooser.isAvailable()) {

                FileChooser.showOpenDialog(".pdf", callback);
            } else {
                Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
            }
        });
        FileContainer.add(scaleImageButton1);*/

        this.RequestService = new RequestService();
        this.request = instance;
        instance.getPropertyIndex().registerExternalizable();
        setName("ChangeRequestForm");
        setInlineStylesTheme(resourceObjectInstance);
        InstantUI iui = new InstantUI();
        iui.excludeProperty(this.request.id);
        iui.excludeProperty(this.request.RespondedAt);
        iui.excludeProperty(this.request.CreatedAt);
        iui.excludeProperty(this.request.UpdatedAt);
        iui.excludeProperty(this.request.Picture);
        iui.excludeProperty(this.request.Attachement);
        iui.excludeProperty(this.request.Status);
        iui.excludeProperty(this.request.Response);
        iui.excludeProperty(this.request.Requester);
        iui.excludeProperty(this.request.Type);
        Container cnt = iui.createEditUI(request, true);
        cnt.setScrollableY(true);
        cnt.setInlineStylesTheme(resourceObjectInstance);

        List<Service> serviceList = ServiceService.getInstance().getAllServices();

        Label Type = new Label("Type (Reslect the same if there is no change)");

        RadioButtonList radioButtonList = new RadioButtonList(new DefaultListModel());
        radioButtonList.setLayout(BoxLayout.y());
        for (Service S : serviceList) {
            radioButtonList.getMultiListModel().addItem(S);
        }

        Button b = new Button(request.Type.toString());
        b.addActionListener(e -> {
            Dialog d = new Dialog();
            d.setLayout(BoxLayout.y());
            d.getContentPane().setScrollableY(true);
            d.add(radioButtonList);
            radioButtonList.addActionListener(ee -> {
                b.setText(radioButtonList.getModel().getItemAt(radioButtonList.getModel().getSelectedIndex()).toString());
                d.dispose();
            });
            d.showPopupDialog(b);
            d.removeComponent(radioButtonList);
        });

        cnt.addAll(Type,b,pictureContainer);

        addComponent(BorderLayout.CENTER, cnt);

        Button saveButton = makeButton("SaveButton", "Save");
        saveButton.addActionListener(evt -> {
            Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
            try {
                Service TypeSer = (Service) radioButtonList.getModel().getItemAt(radioButtonList.getModel().getSelectedIndex());
                request.Type.set(TypeSer);
                this.RequestService.UpdateRequest(request,newPicturePath[0]);
            } catch (Exception e) {
                Log.p(e.getMessage(), Log.ERROR);
                dlg.dispose();
                Dialog.show("error", e.getMessage(), "ok", null);
            }
            dlg.dispose();
            (new ShowRequestUserForm()).show();
        });
        addComponent(BorderLayout.SOUTH, saveButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> showBack());
    }
}
