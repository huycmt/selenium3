package org.example.page.agoda;

import io.qameta.allure.Step;
import org.example.element.Element;
import org.example.utils.WebUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage {

    private final Element adCloseButton = new Element("xpath=//button[@aria-label='Close Message']", true);
    private final Element dayUseStay = new Element("xpath=//button[.='Day Use Stays']", true);
    private final Element placeTextBox = new Element("id=textInput", true);
    private final Element firstPlaceResult = new Element("xpath=//div[@class='Popup__content']/ul/li[1]", true);
    private final Element selectDate = new Element("xpath=//span[@data-selenium-date='%s']", true);
    private final Element plusRoom = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-rooms' and @data-selenium='plus']", true);
    private final Element plusAdult = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-adult' and @data-selenium='plus']", true);
    private final Element adultValue = new Element("xpath=//span[@data-selenium='adultValue']", true);
    private final Element searchButton = new Element("xpath=//span[.='SEARCH']", true);

    @Step("Wait for ad display and close it")
    public void waitForAdDisplaysAndClose() {
        adCloseButton.waitForExist();
        adCloseButton.click();
    }

    @Step("Click Day Use Stay")
    public void clickDayUseStay() {
        dayUseStay.click();
    }

    @Step("Enter place")
    public void enterPlaceTextBox(String place) {
        placeTextBox.enter(place);
    }

    @Step("Click first result")
    public void clickFirstResult() {
        firstPlaceResult.click();
    }

    @Step("Select date")
    public void selectDate(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        selectDate.set(dateStr);
        selectDate.click();
    }

    @Step("Set occupancy")
    public void setOccupancy(Integer rooms, Integer adult, Integer children) {
        plusRoom.click();
        plusAdult.click();
        plusAdult.click();
        adultValue.click();
    }

    @Step("Click search button")
    public void clickSearch() {
        searchButton.waitForExist();
        searchButton.click();
        WebUtils.switchToPage(1);
        WebUtils.waitForPageLoad();
    }
}
