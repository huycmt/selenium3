package org.example.page.logigearmail;

import org.example.element.Element;
import org.example.utils.WebUtils;

public class LoginPage {

    /**
     * Login to Logigear Mail site
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        usernameTextBox.enter(username, true);
        passwordTextBox.enter(password, true);
        signInButton.click();
        WebUtils.waitForPageLoad();
    }

    private Element usernameTextBox = new Element("id=username", true);
    private Element passwordTextBox = new Element("id=password", true);
    private Element signInButton = new Element("xpath=//input[@class='btn' and @type='submit']", true);
}
