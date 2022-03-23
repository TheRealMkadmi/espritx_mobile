package com.espritx.client.gui.service;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Service;
import com.espritx.client.services.Service.ServiceService;

import java.util.List;

public class ShowForm extends BaseForm {
    public ShowForm() {
        this(Resources.getGlobalResources());
    }

    public ShowForm(Resources resourceObjectInstance){
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setInlineStylesTheme(resourceObjectInstance);
        initUserControls(resourceObjectInstance);
        setTitle("Manage Services");
        setName("ManageServices");
        installSidemenu(resourceObjectInstance);
    }

    private void initUserControls(Resources resourceObjectInstance) {
        Dialog dlg = (new InfiniteProgress()).showInfiniteBlocking();
        List<Service> serviceList = ServiceService.getInstance().getAllServices();
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        list.setInlineStylesTheme(resourceObjectInstance);
        for (Service s : serviceList) {
            MultiButton mb = new MultiButton(s.getName());
            mb.setTextLine2(String.valueOf(s.getId()));
            list.addComponent(mb);
        }
        addComponent(list);
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                for (Component cmp : list) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
            } else {
                text = text.toLowerCase();
                for (Component cmp : list) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().contains(text) ||
                            line2 != null && line2.toLowerCase().contains(text);
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
            }
            list.animateLayout(150);
        }, 4);
    }
}