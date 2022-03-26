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

import java.util.List;

public class ShowRequestGroupForm extends BaseForm {
    public ShowRequestGroupForm() {
        this(Resources.getGlobalResources());
    }

    public ShowRequestGroupForm(Resources resourceObjectInstance){
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setInlineStylesTheme(resourceObjectInstance);
        Button AddRequest = new Button("Add Request");
        AddRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form addreq = new AddRequestForm();
                addreq.show();
            }
        });
        addComponent(AddRequest);
        initUserControls(resourceObjectInstance);
        setTitle("Manage Requests of your groups");
        setName("ManageRequests");
        installSidemenu(resourceObjectInstance);
    }

    private void initUserControls(Resources resourceObjectInstance) {
        //Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
        List<Request> requestList = RequestService.GetGroupRequests();

        UiBinding ui = new UiBinding();
        Request prot = new Request();
        UiBinding.BoundTableModel tb = ui.createTableModel(requestList, prot);

        for (Request r:requestList) {
            Label l = new Label(r.CreatedAt.toString());
            add(l);
        }

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
        tb.setEditable(prot.Requester.get().first_name, false);
        tb.setEditable(prot.Status, false);

        SelectableTable t = new SelectableTable(tb);
        t.setSortSupported(true);
        t.setScrollVisible(true);
        t.setScrollableY(true);
        t.setScrollableX(true);
        addComponent(t);

        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            /*if (text == null || text.length() == 0) {
                for (Component cmp : t) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
            } else {
                text = text.toLowerCase();
                for (Component cmp : t) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    boolean show = line1 != null && line1.toLowerCase().contains(text);
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
            }
            t.animateLayout(150);*/
        }, 4);
    }
}
