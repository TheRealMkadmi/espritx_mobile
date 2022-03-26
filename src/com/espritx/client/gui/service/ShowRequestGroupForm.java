package com.espritx.client.gui.service;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.properties.PropertyBase;
import com.codename1.properties.UiBinding;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Request;
import com.espritx.client.entities.Service;
import com.espritx.client.entities.User;
import com.espritx.client.gui.components.SelectableTable;
import com.espritx.client.services.Service.RequestService;
import com.espritx.client.services.Service.ServiceService;
import com.sun.xml.internal.ws.spi.db.PropertySetterBase;

import java.util.ArrayList;
import java.util.List;

public class ShowRequestGroupForm extends BaseForm {
    public ShowRequestGroupForm() {
        this(Resources.getGlobalResources());
    }

    public ShowRequestGroupForm(Resources resourceObjectInstance) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setInlineStylesTheme(resourceObjectInstance);
        Button AddRequest = new Button("Add Request");
        AddRequest.addActionListener(evt -> {
            (new AddRequestForm()).show();
            ;
        });
        addComponent(AddRequest);
        initUserControls(resourceObjectInstance);
        setTitle("Manage Requests of your groups");
        setName("ManageRequests");
        installSidemenu(resourceObjectInstance);
    }

    private void initUserControls(Resources resourceObjectInstance) {
        //Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
        List<Request> requestList = RequestService.GetGroupRequests(); // original array
        final List<Request> shadowCopy = new ArrayList<>(requestList); // the one we'll keep manipulating for the display

        UiBinding ui = new UiBinding();
        Request prot = new Request();
        UiBinding.BoundTableModel tb = ui.createTableModel(shadowCopy, prot);
        tb.excludeProperty(prot.id);
        tb.excludeProperty(prot.Attachement);
        tb.excludeProperty(prot.Description);
        tb.excludeProperty(prot.Email);
        tb.excludeProperty(prot.Picture);
        tb.excludeProperty(prot.RespondedAt);
        tb.excludeProperty(prot.UpdatedAt);
        tb.excludeProperty(prot.Response);
        tb.setEditable(prot.Title, false);
        tb.setEditable(prot.CreatedAt, false);
        tb.setEditable(prot.Type, false);
        tb.setEditable(prot.Requester, false);
        tb.setEditable(prot.Status, false);

        SelectableTable t = new SelectableTable(tb);

        t.setSortSupported(true);
        t.setScrollVisible(true);
        t.setScrollableY(true);
        t.setScrollableX(true);
        addComponent(t);

        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text != null) {
                shadowCopy.clear();
                for (Request req : requestList) {
                    if (req.Title.get().contains(text.trim())) {
                        shadowCopy.add(req);
                    }
                }
                t.revalidate();
                t.refresh();
            }
        }, 4);
    }
}
