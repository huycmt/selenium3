package org.example.page.general;


import static org.example.driver.DriverManager.driver;

public class GeneralPage {

    public void gotoURL(String url) {
        driver().open(url);
    }
}
