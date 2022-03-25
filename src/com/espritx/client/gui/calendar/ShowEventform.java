/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espritx.client.gui.calendar;

import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.BaseForm;
import com.espritx.client.entities.Calendar;
import com.espritx.client.services.User.AuthenticationService;
import com.espritx.client.services.serviceCalendar.ServiceCalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

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
        cal.addActionListener((evt) -> {
            ev[0] =ServiceCalendar.getInstance().getEventByDate(ServiceCalendar.getInstance().convertt(cal.getDate()));
            for (Calendar c : ev[0]) {
            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Label start = new Label (c.getStart().toString());
            Label end = new Label (c.getEnd().toString());
            Label x = new Label(" ");
                Button updateButton = new Button("Manage");
                Style supprimerStyle=new Style(updateButton.getUnselectedStyle());
                updateButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_UPDATE,supprimerStyle));

                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        new UpdateEvent(c,prev).show();
                    }
                });

            start.addPointerPressedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Dialog.show("Event : "+ c.getTitle(), "Description : " + c.getDescription()+"\nmade by\n "+ c.getFirstname()+" "+c.getLastname(), "OK", null);
                }
            });
            Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                if(c.getUserId()== AuthenticationService.getAuthenticatedUser().id.getInt())
                C2.add(updateButton);

                C1.add(C2);
                C1.add(start);
                C1.add(end);
                C1.add(x);

            add(C1);
        }
        });



    }

}
