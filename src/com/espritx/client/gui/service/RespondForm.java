package com.espritx.client.gui.service;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.RadioButtonList;
import com.codename1.components.ScaleImageButton;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.Log;
import com.codename1.properties.InstantUI;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Request;
import com.espritx.client.entities.Service;
import com.espritx.client.services.Service.RequestService;
import com.espritx.client.services.Service.ServiceService;

import java.util.List;

public class RespondForm extends BaseForm {
    private Request request;
    private com.espritx.client.services.Service.RequestService RequestService;

    public RespondForm() {
        this(Resources.getGlobalResources());
    }

    public RespondForm(Resources resourceObjectInstance) {
        this(Resources.getGlobalResources(), new Request());
    }

    public RespondForm(Resources resourceObjectInstance, Request instance) {
        setLayout(new BorderLayout());


        // construction
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
        iui.excludeProperty(this.request.Requester);
        iui.excludeProperty(this.request.Title);
        iui.excludeProperty(this.request.Description);
        iui.excludeProperty(this.request.Email);
        iui.excludeProperty(this.request.Type);
        iui.excludeProperty(this.request.Picture);
        iui.excludeProperty(this.request.Attachement);
        iui.setMultiChoiceLabels(this.request.Status, "Hold", "Deny", "Done");
        iui.setMultiChoiceValues(this.request.Status, "processing", "denied", "done");
        Container cnt = iui.createEditUI(request, true);
        cnt.setScrollableY(true);
        cnt.setInlineStylesTheme(resourceObjectInstance);


        Label Titlelb=new Label("Title:");
        Label Typelb=new Label("Service:");
        Label Emaillb=new Label("Email:");
        Label Deslb=new Label("Description:");
        Label Picturelb=new Label("Picture");
        Label Filelb=new Label("Files");

        Label Titletext=new Label(instance.Title.toString());
        Label Typetext=new Label(instance.Type.toString());
        Label Emailtext=new Label(instance.Email.toString());
        Label Destext=new Label(instance.Description.toString());
        ScaleImageButton PictureCnt = new ScaleImageButton(FontImage.createMaterial(FontImage.MATERIAL_DOWNLOAD, new Style()));
        ScaleImageButton FileCnt = new ScaleImageButton(FontImage.createMaterial(FontImage.MATERIAL_DOWNLOAD, new Style()));

        Container Title = new Container(BoxLayout.x());
        Title.addAll(Titlelb,Titletext);
        Container Des = new Container(BoxLayout.x());
        Des.addAll(Deslb,Destext);
        Container Email = new Container(BoxLayout.x());
        Email.addAll(Emaillb,Emailtext);
        Container Type = new Container(BoxLayout.x());
        Type.addAll(Typelb,Typetext);
        Container Pic = new Container(BoxLayout.x());
        Pic.addAll(Picturelb,PictureCnt);
        Container File = new Container(BoxLayout.x());
        File.addAll(Filelb,FileCnt);

        System.out.println(instance.Picture.get());
        cnt.addAll(Title,Des,Type);
        if(instance.Email.get() != null)
            cnt.add(Email);
        if(instance.Picture.get() != null)
            cnt.add(Pic);
        if(instance.Attachement.get() != null)
            cnt.add(File);
        addComponent(BorderLayout.CENTER, cnt);


        Button saveButton = makeButton("SaveButton", "Save");
        saveButton.addActionListener(evt -> {
            Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
            try {
                this.RequestService.RespondRequest(request);
            } catch (Exception e) {
                Log.p(e.getMessage(), Log.ERROR);
                dlg.dispose();
                Dialog.show("error", e.getMessage(), "ok", null);
            }
            dlg.dispose();
            (new ShowRequestUserForm()).show();
        });
        addComponent(BorderLayout.SOUTH, saveButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ShowRequestGroupForm().show());
    }
}
