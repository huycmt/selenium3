package org.example.page.agoda;

import org.example.data.agoda.HotelData;
import org.example.element.Element;
import org.example.report.Report;
import org.example.report.Status;

import java.util.List;

public class HotelDetailsPage {

    /**
     * Get Dining, drinking, and snacking options in Amenities and facilities part
     *
     * @return
     */
    public List<String> getDiningDrinkingSnackingOptions() {
        return diningDrinkingSnacking.elements().texts();
    }

    /**
     * Get hotel name
     */
    public String getHotelName() {
        return hotelName.getText().trim();
    }

    /**
     * Get hotel address
     */
    public String getHotelAddress() {
        return hotelAddress.getText().trim();
    }

    /**
     * Click got in popup
     */
    public void closeIfPopupExists() {
        if (gotItSpan.isDisplayed()) {
            gotItSpan.click();
        }
    }

    /**
     * Get hotel data
     */
    public HotelData getHotelData() {
        return HotelData.builder()
                .hotelName(getHotelName())
                .address(getHotelAddress())
                .diningDrinkingSnacking(getDiningDrinkingSnackingOptions())
                .build();
    }

    /**
     * Check if hotel address in details page match with address in result page
     */
    public boolean isHotelAddressMatched(String addressInDetailsPage, String addressInResultPage) {
        String addressResult = addressInResultPage.split("-")[0].trim();
        if (!addressInDetailsPage.contains(addressResult)) {
            Report.getInstance().step(String.format("Address in details page '%s' does not contain '%s'", addressInDetailsPage, addressResult), Status.FAILED);
            return false;
        }
        return true;
    }

    /**
     * Click favorite heart icon
     */
    public void clickFavoriteHeartIcon() {
        favoriteHeart.click();
    }

    /**
     * Get displayed text of rooms
     */
    public List<String> getDisplayedTextOfRooms() {
        return room.elements().texts();
    }

    private Element diningDrinkingSnacking = new Element("xpath=//h5[.='Dining, drinking, and snacking']/following-sibling::ul//li");
    private Element hotelName = new Element("xpath=//p[@data-selenium='hotel-header-name']");
    private Element hotelAddress = new Element("xpath=//span[@data-selenium='hotel-address-map']");
    private Element gotItSpan = new Element("xpath=//span[.='Got it']");
    private Element favoriteHeart = new Element("xpath=//i[@data-selenium='favorite-heart' and @data-element-value='save']");
    private Element room = new Element("xpath=//div[@id='roomGridContent']/div");
}
