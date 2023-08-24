package org.example.page.logigearmail;

import org.example.element.Element;
import org.example.utils.WebUtils;

public class LoginPage {

    public void login(String username, String password) {
        usernameTextBox.enter(username, true);
        passwordTextBox.enter(password, true);
        signInButton.click();
        WebUtils.waitForPageLoad();
    }

    Element usernameTextBox = new Element("id=username", true);
    Element passwordTextBox = new Element("id=password", true);
    Element signInButton = new Element("xpath=//input[@class='btn' and @type='submit']", true);
}
