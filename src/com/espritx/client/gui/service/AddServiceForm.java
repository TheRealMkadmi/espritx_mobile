package com.espritx.client.gui.service;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Service;
import com.espritx.client.services.Service.ServiceService;

import java.util.List;

public class AddServiceForm extends BaseForm {
    public AddServiceForm() {
        this(Resources.getGlobalResources());
    }

    public AddServiceForm(Resources resourceObjectInstance) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setInlineStylesTheme(resourceObjectInstance);
        setTitle("Add a new task");
        setLayout(BoxLayout.y());

        TextField tfName = new TextField("", "Service Name");


        Button btnValider = new Button("Add task");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) )
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else {
                    try {
                        Service S = new Service(tfName.getText());
                        if (ServiceService.getInstance().addService(S)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                }
            }
        });

        addAll(tfName, btnValider);
        /*getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> ShowForm(res).show());*/
        setTitle("Add Service");
        setName("AddService");
        installSidemenu(resourceObjectInstance);
    }
}
