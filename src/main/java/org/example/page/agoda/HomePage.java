package org.example.page.agoda;

import org.example.element.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage {

    public void waitForAdDisplaysAndClose() {
        adCloseButton.waitForExist();
        adCloseButton.click();
    }

    public void clickDayUseStay() {
        dayUseStay.click();
    }

    public void enterPlaceTextBox(String place) {
        placeTextBox.enter(place);
    }

    public void clickFirstResult() {
        firstPlaceResult.click();
    }

    public void selectDate(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        selectDate.set(dateStr);
        selectDate.click();
    }

    public void setOccupancy(Integer rooms, Integer adult, Integer children) {
        plusRoom.click();
        plusAdult.click();
        plusAdult.click();
        occupancyBox.click();
    }

    public void clickSearch() {
        searchButton.waitForExist();
        searchButton.click();
    }


    Element adCloseButton = new Element("xpath=//button[@aria-label='Close Message']", true);
    Element dayUseStay = new Element("xpath=//button[.='Day Use Stays']", true);
    Element placeTextBox = new Element("id=textInput", true);
    Element firstPlaceResult = new Element("xpath=//div[@class='Popup__content']/ul/li[1]", true);
    Element selectDate = new Element("xpath=//span[@data-selenium-date='%s']", true);
    Element plusRoom = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-rooms' and @data-selenium='plus']", true);
    Element plusAdult = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-adult' and @data-selenium='plus']", true);
    Element occupancyBox = new Element("id=occupancy-box", true);
    Element searchButton = new Element("xpath=//button[@data-selenium='searchButton']", true);

}
