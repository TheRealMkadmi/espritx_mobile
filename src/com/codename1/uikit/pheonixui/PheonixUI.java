/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.codename1.uikit.pheonixui;

import com.codename1.ui.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.StringUtil;
import com.espritx.client.gui.user.LoginForm;
import com.espritx.client.gui.user.ShowUsers;
import com.espritx.client.services.User.AuthenticationService;

import java.util.List;


public class PheonixUI {
    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
        UIManager.getInstance().setBundle(theme.getL10N("l1", "en"));
    }

    public void start() {
        String arg = CN.getProperty("AppArg", null);
        if(arg != null) {
            if(arg.contains("//")) {
                List<String> strs = StringUtil.tokenize(arg, "/");
                arg = strs.get(strs.size() - 1);
                while(arg.startsWith("/")) {
                    arg = arg.substring(1);
                }
            }
            if(arg.startsWith("token-")) {
                String token = arg.substring(6);
                AuthenticationService.SetAuthenticatedToken(token);
                CN.callSerially(() ->
                        (new ShowUsers()).show()
                );
            }
            return;
        }
        if (current != null) {
            current.show();
            return;
        }
        new LoginForm(theme).show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }
}
