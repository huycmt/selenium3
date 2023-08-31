package org.example.page.agoda;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.data.agoda.FilterResultData;
import org.example.data.agoda.HotelData;
import org.example.element.Element;
import org.example.report.Report;
import org.example.report.Status;
import org.example.utils.WebUtils;
import org.openqa.selenium.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.example.utils.WebUtils.actions;

public class ResultPage {

    /**
     * Are all the destinations have search content
     *
     * @param destinationNumber Number of destinations need to check
     * @param place             Place name
     * @return
     */
    public boolean areAllTheDestinationsHaveSearchContent(Integer destinationNumber, String place) {
        List<String> destinationList = getDestinationList(destinationNumber);
        if (Objects.isNull(destinationList)) {
            Report.getInstance().step("No result found", Status.FAILED);
            return false;
        }
        for (String str : destinationList) {
            if (!str.contains(place)) {
                Report.getInstance().step(String.format("Destination '%s' does not contain '%s'", str, place), Status.FAILED);
                return false;
            }
        }
        return true;
    }

    /**
     * Click the Lowest Price First button
     */
    public void clickLowestPriceFirstButton() {
        if (!lowestPriceButton.isDisplayed()) {
            sortByBestMatch.click();
        }
        lowestPriceButton.click();
    }

    /**
     * Get all the hotel prices
     *
     * @param hotelNumber Number of hotel need to get
     * @return List of all prices
     */
    public List<Float> getPriceList(Integer hotelNumber) {
        List<String> priceList = price.elements().texts();
        if (Objects.nonNull(hotelNumber)) {
            if (hotelNumber > priceList.size()) {
                Report.getInstance().step(String.format("Page only show %d instead of %d result(s). Scroll down for more results", priceList.size(), hotelNumber), Status.FAILED);
                return null;
            } else {
                priceList = price.elements().texts().subList(0, hotelNumber);
            }
        }
        return priceList.stream().map(e -> Float.parseFloat(e.replaceAll(",", ""))).collect(Collectors.toList());
    }

    /**
     * Get all the hotel area city
     *
     * @param hotelNumber Number of hotel need to get
     * @return List of all area city
     */
    public List<String> getDestinationList(Integer hotelNumber) {
        List<String> destinationList = destination.elements().texts();
        if (Objects.nonNull(hotelNumber)) {
            if (hotelNumber > destinationList.size()) {
                Report.getInstance().step(String.format("Page only show %d instead of %d result(s). Scroll down for more results", destinationList.size(), hotelNumber), Status.FAILED);
                return null;
            } else {
                destinationList = destination.elements().texts().subList(0, hotelNumber);
            }
        }
        return destinationList;
    }

    /**
     * Filter hotel with info
     *
     * @param filterResultData
     */
    public void filterHotel(FilterResultData filterResultData) {
        if (Objects.nonNull(filterResultData.getMinPrice())) {
            minPriceTextBox.enter(String.valueOf(filterResultData.getMinPrice()), true);
            minPriceTextBox.element().pressEnter();
            WebUtils.waitForJSandJQueryToLoad();
        }
        if (Objects.nonNull(filterResultData.getMaxPrice())) {
            maxPriceTextBox.enter(String.valueOf(filterResultData.getMaxPrice()), true);
            maxPriceTextBox.element().pressEnter();
            WebUtils.waitForJSandJQueryToLoad();
        }
        if (filterResultData.isThreeStarCheck()) {
            threeStarCheckBox.click();
            WebUtils.waitForJSandJQueryToLoad();
        }
    }

    /**
     * Get star list of hotels
     *
     * @param hotelNumber Number of hotel need to get
     * @return List star of hotels
     */
    public List<Float> getStarList(Integer hotelNumber) {
        ElementsCollection ratingList = rating.elements();
        if (Objects.nonNull(hotelNumber)) {
            if (hotelNumber > ratingList.size()) {
                Report.getInstance().step(String.format("Page only show %d instead of %d result(s). Scroll down for more results", ratingList.size(), hotelNumber), Status.FAILED);
                return null;
            } else {
                ratingList = (ElementsCollection) rating.elements().subList(0, hotelNumber - 1);
            }
        }
        List<Float> rateList = new ArrayList<>();
        float stars;
        for (SelenideElement element : ratingList) {
            stars = element.innerHtml().split("StarSymbolFillIcon").length - 1;
            stars += (element.innerHtml().split("StarHalfSymbolFillIcon").length - 1) / 2;
            rateList.add(stars);
        }
        return rateList;
    }

    /**
     * Get hotel data in result page
     */
    public List<HotelData> getHotelData(Integer hotelNumber) {
        List<HotelData> hotelData = new ArrayList<>();
        List<Float> priceList = getPriceList(hotelNumber);
        List<String> destinationList = getDestinationList(hotelNumber);
        List<Float> starList = getStarList(hotelNumber);
        for (int i = 0; i < priceList.size(); i++) {
            hotelData.add(HotelData.builder()
                    .areaCity(destinationList.get(i))
                    .price(priceList.get(i))
                    .star(starList.get(i))
                    .build());
        }
        return hotelData;
    }

    /**
     * Check if slider is highlighted after selecting price
     *
     * @return true if slider is not (0,100)
     */
    public boolean isSelectedPriceHighlight() {
        if (getMinSliderValue().equals("left: 0%") || getMaxSliderValue().equals("left: 100%")) {
            Report.getInstance().step(String.format("Slider range is (%s, %s)", getMinSliderValue(), getMaxSliderValue()), Status.FAILED);
            return false;
        }
        return true;
    }

    /**
     * Check if slider is reset or not
     *
     * @return true if slider is (0,100)
     */
    public boolean isSliderReset() {
        if (!getMinSliderValue().equals("left: 0%") || !getMaxSliderValue().equals("left: 100%")) {
            Report.getInstance().step(String.format("Slider range is (%s, %s)", getMinSliderValue(), getMaxSliderValue()), Status.FAILED);
            return false;
        }
        return true;
    }

    public String getMinSliderValue() {
        return minSliderHandle.getAttribute("style").split(";")[0];
    }

    public String getMaxSliderValue() {
        return maxSliderHandle.getAttribute("style").split(";")[0];
    }

    /**
     * Check if three-star filter is applied or not
     */
    public boolean isThreeStarFilterApplied() {
        return threeStarInRecentFilter.isDisplayed();
    }

    /**
     * Are all hotel infos match with filter
     *
     * @param hotelDataList given hotel data list
     * @param minPrice      min price when filtering
     * @param maxPrice      max price when filtering
     * @param place         searched city
     * @param star          selected star
     * @return
     */
    public boolean areAllHotelInfosMatchWithFilter(List<HotelData> hotelDataList, int minPrice, int maxPrice, String place, float star) {
        for (HotelData hotelData : hotelDataList) {
            if (hotelData.getPrice() > maxPrice || hotelData.getPrice() < minPrice || !hotelData.getAreaCity().contains(place) || (hotelData.getStar().intValue() != (int) star)) {
                Report.getInstance().step("This hotel data is not match: " + hotelData, Status.FAILED);
            }
        }
        return true;
    }

    /**
     * Remove price filter by drag and drop slider to the edge
     */
    public void removePriceFilter() {
        Rectangle rectangle = slider.element().getRect();
        actions().dragAndDropBy(minSliderHandle.element().toWebElement(), -rectangle.getX(), 0).build().perform();
        WebUtils.waitForJSandJQueryToLoad();
        actions().dragAndDropBy(maxSliderHandle.element().toWebElement(), rectangle.getX() + rectangle.getWidth(), 0).build().perform();
        WebUtils.waitForJSandJQueryToLoad();
    }

    Element destination = new Element("xpath=//span[@data-selenium='area-city-text']", true);
    Element lowestPriceButton = new Element("xpath=//*[self::a or self::li][.='Lowest price first']", true);
    Element sortByBestMatch = new Element("xpath=//button[.='Sort by: Best match']", true);
    Element price = new Element("xpath=//span[@class='PropertyCardPrice__Value']", true);
    Element bedroomOption = new Element("xpath=//span[.='1 bedroom']", true);
    Element minPriceTextBox = new Element("xpath=//input[@aria-label='Minimum price filter']", true);
    Element maxPriceTextBox = new Element("xpath=//input[@aria-label='Maximum price filter']", true);
    Element threeStarCheckBox = new Element("xpath=//span[contains(@class,'StarRating-3')]//span[@class='checkbox-icon']", true);
    Element rating = new Element("xpath=//div[@role='img']", true);
    Element minSliderHandle = new Element("xpath=(//div[@aria-label='undefined MIN'])[1]", true);
    Element maxSliderHandle = new Element("xpath=(//div[@aria-label='undefined MAX'])[1]", true);
    Element threeStarInRecentFilter = new Element("xpath=(//span[@data-component='search-filter-starrating' and contains(@class,'RecentFilters')])[1]", true);
    Element slider = new Element("xpath=(//div[@class='rc-slider rc-slider-horizontal'])[1]", true);
}
