package org.example.page.agoda;

import org.example.data.agoda.SearchHotelData;
import org.example.element.Element;
import org.example.utils.WebUtils;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HomePage {

    /**
     * Wait for advertisement display and close it
     */
    public void waitForAdDisplaysAndClose() {
        adCloseButton.waitForExist();
        adCloseButton.click();
    }

    /**
     * Click Search button
     */
    public void clickSearch() {
        searchButton.waitForExist();
        searchButton.click();
        WebUtils.switchToPage(1);
        WebUtils.waitForPageLoad();
    }

    /**
     * Search hotel with info
     *
     * @param searchHotelData
     */
    public void searchHotel(SearchHotelData searchHotelData) {
        fillHotelInfo(searchHotelData);
        clickSearch();
    }

    /**
     * Filter hotel with info
     *
     * @param searchHotelData
     */
    public void fillHotelInfo(SearchHotelData searchHotelData) {
        if (searchHotelData.isDayUseStay()) {
            dayUseStay.click();
        }
        if (Objects.nonNull(searchHotelData.getPlace())) {
            placeTextBox.enter(searchHotelData.getPlace());
            firstPlaceResult.click();
        }
        if (Objects.nonNull(searchHotelData.getDate())) {
            String dateStr = searchHotelData.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            selectDate.set(dateStr);
            selectDate.click();
        }
        if (Objects.nonNull(searchHotelData.getOccupancy())) {
            SearchHotelData.Occupancy occupancy = searchHotelData.getOccupancy();
            if (Objects.nonNull(occupancy.getAdult())) {
                plusRoom.click();
                for (int i = 0; i < occupancy.getAdult(); i++) {
                    plusAdult.click();
                }
                adultValue.click();
            }
        }
    }

    Element adCloseButton = new Element("xpath=//button[@aria-label='Close Message']", true);
    Element dayUseStay = new Element("xpath=//button[.='Day Use Stays']", true);
    Element placeTextBox = new Element("id=textInput", true);
    Element firstPlaceResult = new Element("xpath=//div[@class='Popup__content']/ul/li[1]", true);
    Element selectDate = new Element("xpath=//span[@data-selenium-date='%s']", true);
    Element plusRoom = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-rooms' and @data-selenium='plus']", true);
    Element plusAdult = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-adult' and @data-selenium='plus']", true);
    Element adultValue = new Element("xpath=//span[@data-selenium='adultValue']", true);
    Element searchButton = new Element("xpath=//span[.='SEARCH']", true);
}
