package org.example.page.agoda;

import org.example.element.Element;
import org.example.utils.WebUtils;

public class LoginPage {

    /**
     * Login to Agoda with email and password
     *
     * @param email
     * @param password
     */
    public void login(String email, String password) {
        WebUtils.switchToFrame(iframe);
        emailTextBox.enter(email);
        passwordTextBox.enter(password);
        signInButton.click();
        WebUtils.switchToMain();
        WebUtils.waitForPageLoad();
    }

    /**
     * Check if login form displayed ot not
     *
     * @return
     */
    public boolean isLoginPageDisplayed() {
        WebUtils.switchToFrame(iframe);
        signInButton.waitForVisible();
        boolean formDisplay = signInButton.isDisplayed();
        WebUtils.switchToMain();
        return formDisplay;
    }

    private Element emailTextBox = new Element("id=email", true);
    private Element passwordTextBox = new Element("id=password", true);
    private Element signInButton = new Element("xpath=//button[@data-cy='signin-button']", true);
    private Element iframe = new Element("xpath=//iframe[@title='Universal login']", true);
}
