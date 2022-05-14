package com.espritx.client.gui.service;

import com.codename1.components.*;
import com.codename1.ext.filechooser.FileChooser;
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
        this(Resources.getGlobalResources(), new Request());
    }

    public AddRequestForm(Resources resourceObjectInstance, Request instance) {
        setLayout(new BorderLayout());

        final String[] attachmentsArray = new String[2];
        Container upload=new Container(BoxLayout.y());
        Container pictureContainer = new Container(BoxLayout.x());
        pictureContainer.setName("Picture Container");
        Label upti=new Label("Picture");
        pictureContainer.add(upti);
        Label filePickerStatus = new Label("No file chosen.");
        pictureContainer.add(filePickerStatus);
        Image icon = FontImage.createMaterial(FontImage.MATERIAL_ADD, new Style());
        ScaleImageButton addImageButton = new ScaleImageButton(icon);
        addImageButton.addActionListener((evt) -> {
            ActionListener callback = e -> {
                if (e != null && e.getSource() != null) {
                    attachmentsArray[0] = (String) e.getSource();
                    filePickerStatus.setText("Selected Picture");
                }
            };

            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".png,image/png,.jpg,image/jpg,.jpeg", callback);
            } else {
                Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
            }
        });
        pictureContainer.add(addImageButton);
        upload.add(pictureContainer);
        Container FileContainer = new Container(BoxLayout.x());
        pictureContainer.setName("File Container");
        Label upti1=new Label("Attachements");
        FileContainer.add(upti1);
        Label filePickerStatus1 = new Label("No file chosen.");
        FileContainer.add(filePickerStatus1);
        ScaleImageButton addFileButton = new ScaleImageButton(icon);
        addFileButton.addActionListener((evt) -> {
            ActionListener callback = e -> {
                if (e != null && e.getSource() != null) {
                    attachmentsArray[1] = (String) e.getSource();
                    filePickerStatus1.setText("Selected Picture");
                }
            };

            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf", callback);
            } else {
                Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
            }
        });
        FileContainer.add(addFileButton);
        upload.add(FileContainer);

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

        Label Type = new Label("Type");

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

        cnt.addAll(Type, b, upload);

        addComponent(BorderLayout.NORTH, cnt);

        Button saveButton = makeButton("SaveButton", "Save");
        saveButton.addActionListener(evt -> {
            Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
            try {
                Service TypeSer = (Service) radioButtonList.getModel().getItemAt(radioButtonList.getModel().getSelectedIndex());
                request.Type.set(TypeSer);
                if (instance.id.get()==null)
                    com.espritx.client.services.Service.RequestService.CreateRequest(request,attachmentsArray[0], attachmentsArray[1]);
                else com.espritx.client.services.Service.RequestService.UpdateRequest(request, attachmentsArray[0], attachmentsArray[1]);
            } catch (Exception e) {
            }
            dlg.dispose();
            (new ShowRequestUserForm()).show();
        });
        addComponent(BorderLayout.SOUTH, saveButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ShowRequestUserForm().show());
    }
}
