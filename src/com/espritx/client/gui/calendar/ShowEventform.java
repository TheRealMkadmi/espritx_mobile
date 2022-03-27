/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.calendar;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.codename1.util.DateUtil;
import com.espritx.client.entities.Calendar;
import com.espritx.client.services.User.AuthenticationService;
import com.espritx.client.services.serviceCalendar.ServiceCalendar;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mohzsen
 */
public class ShowEventform extends BaseForm {


    public ShowEventform(Form prev){
        final ArrayList<Calendar>[] ev = new ArrayList[]{null};

        setTitle("Calendar");
        setLayout(BoxLayout.y());

        installSidemenu(Resources.getGlobalResources());
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_POST_ADD, (evt) -> {
            new AddEvent(prev).show();
        });
        com.codename1.ui.Calendar cal = new com.codename1.ui.Calendar();
        add(cal);
        cal.addDayActionListener((evt) -> {
            Label l = new Label("Today\ns events");
            ev[0] =ServiceCalendar.getInstance().getEventByDate(ServiceCalendar.getInstance().convertt(cal.getDate()));
            for (Calendar c : ev[0]) {
                    Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                    MultiButton mb = new MultiButton(c.getTitle());
                    mb.setTextLine2(c.getStart().toString());
                    mb.setTextLine3("and ends");
                    mb.setTextLine4(c.getStart().toString());
                    FontImage.setMaterialIcon(mb, FontImage.MATERIAL_EVENT);

                    Button manageButton = new Button("Manage");
                    Style supprimerStyle = new Style(manageButton.getUnselectedStyle());

                    manageButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_EDIT, supprimerStyle));

                    manageButton.addActionListener(evt1 -> new UpdateEvent(c, prev).show());

                    mb.addPointerPressedListener(evt12 -> Dialog.show("Event : " + c.getTitle(), "Description : " + c.getDescription() + "\nmade by\n " + c.getFirstname() + " " + c.getLastname(), "OK", null));
                    C1.add(mb);
                    if (c.getUserId() == AuthenticationService.getAuthenticatedUser().id.getInt() && DateUtil.compare(c.getStart(), new Date()) == 1)
                        C1.add(manageButton);
                    add(C1);

                showBack();
            }
        });

        reminder();

    }

}
