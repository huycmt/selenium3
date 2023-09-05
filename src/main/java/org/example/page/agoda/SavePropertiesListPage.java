package org.example.page.agoda;

import com.epam.ta.reportportal.ws.annotations.In;
import org.example.data.agoda.HotelData;
import org.example.data.agoda.SearchHotelData;
import org.example.element.Element;
import org.example.utils.WebUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SavePropertiesListPage {

    /**
     * Click first result
     */
    public void clickFirstResult() {
        firstCard.click();
        WebUtils.waitForPageLoad();
    }

    /**
     * Get search data from favorite page
     * @return
     */
    public SearchHotelData getSearchHotelData() {
        return SearchHotelData.builder()
                .fromDate(LocalDate.parse(checkInDate.getAttribute("data-date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .toDate(LocalDate.parse(checkOutDate.getAttribute("data-date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .occupancy(SearchHotelData.Occupancy.builder()
                        .rooms(Integer.parseInt(roomValue.getText().split(" ")[0]))
                        .adult(Integer.parseInt(adultValue.getText().split(" ")[0]))
                        .build())
                .build();
    }

    /**
     * Get hotel data from favorite page
     * @return
     */
    public HotelData getHotelData() {
        return HotelData.builder()
                .hotelName(hotelName.getText())
                .address(address.getText())
                .build();
    }

    /**
     * Un favorite card
     */
    public void unFavoriteCard() {
        unFavoriteIcon.click();
    }

    private Element firstCard = new Element("xpath=//div[@data-selenium='favorite-group-card']");
    private Element checkInDate = new Element("xpath=//div[@data-selenium='checkInBox']");
    private Element checkOutDate = new Element("xpath=//div[@data-selenium='checkOutBox']");
    private Element roomValue = new Element("xpath=//div[@data-selenium='roomValue']");
    private Element adultValue = new Element("xpath=//span[@data-selenium='adultValue']");
    private Element address = new Element("xpath=//div[@data-selenium='favorite-property-card']//p");
    private Element hotelName = new Element("xpath=//div[@data-selenium='favorite-property-card']//p/preceding-sibling::div");
    private Element unFavoriteIcon = new Element("xpath=//div[@data-selenium='favorite-property-card']//*[local-name()='svg']/*[local-name()='path']");
}
