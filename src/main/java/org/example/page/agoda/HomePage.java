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
        if (searchHotelData.isDayUseStay()) {
            if (Objects.nonNull(searchHotelData.getFromDate())) {
                String dateStr = searchHotelData.getFromDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                selectDate.set(dateStr);
                selectDate.click();
            }
        } else {
            if (Objects.nonNull(searchHotelData.getFromDate())) {
                String dateStr = searchHotelData.getFromDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                selectDate.set(dateStr);
                selectDate.click();
            }
            if (Objects.nonNull(searchHotelData.getToDate())) {
                String dateStr = searchHotelData.getToDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                selectDate.set(dateStr);
                selectDate.click();
            }
        }
        if (Objects.nonNull(searchHotelData.getOccupancy())) {
            SearchHotelData.Occupancy occupancy = searchHotelData.getOccupancy();
            if (Objects.nonNull(occupancy.getRooms())) {
                for (int i = 1; i < occupancy.getRooms(); i++) {
                    plusRoom.click();
                }
            }
            if (Objects.nonNull(occupancy.getAdult())) {
                for (int i = 2; i < occupancy.getAdult(); i++) {
                    plusAdult.click();
                }
                adultValue.click();
            }
        }
    }

    /**
     * Select user menu after logging in
     *
     * @param item e.g. Bookings, Saved properties list,...
     */
    public void selectHeaderMenu(String item) {
        avatar.click();
        menuItem.set(item);
        menuItem.click();
    }

    private Element adCloseButton = new Element("xpath=//button[@aria-label='Close Message']", true);
    private Element dayUseStay = new Element("xpath=//button[.='Day Use Stays']", true);
    private Element placeTextBox = new Element("id=textInput", true);
    private Element firstPlaceResult = new Element("xpath=//div[@class='Popup__content']/ul/li[1]", true);
    private Element selectDate = new Element("xpath=//span[@data-selenium-date='%s']", true);
    private Element plusRoom = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-rooms' and @data-selenium='plus']", true);
    private Element plusAdult = new Element("xpath=//div[@data-element-name='occupancy-selector-panel-adult' and @data-selenium='plus']", true);
    private Element adultValue = new Element("xpath=//span[@data-selenium='adultValue']", true);
    private Element searchButton = new Element("xpath=//span[.='SEARCH']", true);
    private Element avatar = new Element("xpath=//div[@data-element-name='user-avatar']", true);
    private Element menuItem = new Element("xpath=//ul[@class='UserOrHostMenuContainer__UserMenu']/li[.='Saved properties list']", true);
}
