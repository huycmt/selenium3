package org.example.page.agoda;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.example.element.Element;
import org.example.utils.Assertion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage {

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
        occupancyBox.click();
    }

    @Step("Click search button")
    public void clickSearch() {
        searchButton.waitForExist();
        searchButton.click();
    }

    private Element adCloseButton = new Element("xpath=//button[@aria-label='Close Message']", true);
    private Element dayUseStay = new Element("xpath=//button[.='Day Use Stays']", true);
    private Element placeTextBox = new Element("id=textInput", true);
    private Element firstPlaceResult = new Element("xpath=//div[@class='Popup__content']/ul/li[1]", true);
    private Element selectDate = new Element("xpath=//span[@data-selenium-date='%s']", true);
    private Element plusRoom = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-rooms' and @data-selenium='plus']", true);
    private Element plusAdult = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-adult' and @data-selenium='plus']", true);
    private Element occupancyBox = new Element("id=occupancy-box", true);
    private Element searchButton = new Element("xpath=//button[@data-selenium='searchButton']", true);
}
