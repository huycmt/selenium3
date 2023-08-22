package org.example.page.agoda;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import org.example.data.agoda.FilterResultData;
import org.example.element.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResultPage {

    public boolean areTheFirstDestinationsHaveSearchContent(Integer destinationNumber, String place) {
        List<String> destinationList;

        if (Objects.isNull(destinationNumber)) {
            destinationList = destination.elements().texts();
        } else {
            destinationList = destination.elements().texts().subList(0, destinationNumber);
        }
        return destinationList.stream().allMatch(e -> e.contains(place));
    }

    @Step("Click Lowest Price First button")
    public void clickLowestPriceFirstButton() {
        if (!lowestPriceButton.isDisplayed()) {
            sortByBestMatch.click();
        }
        lowestPriceButton.click();
    }

    public boolean areTheFirstHotelsSortedWithRightOrder(Integer hotelNumber) {
        List<String> priceList;
        if (Objects.isNull(hotelNumber)) {
            priceList = price.elements().texts();
        } else {
            priceList = price.elements().texts().subList(0, hotelNumber);
        }
        List<Integer> priceListInt = priceList.stream().map(e -> Integer.parseInt(e.replaceAll(",", ""))).collect(Collectors.toList());
        return Ordering.natural().isOrdered(priceListInt);
    }

    @Step("Scroll page for more results")
    public void scrollForMoreResults() {
        bedroomOption.scrollIntoView(true);
    }

    @Step("Filter hotels: {0}")
    public void filterHotel(FilterResultData filterResultData) {
        if (Objects.nonNull(filterResultData.getMinPrice())) {
            minPriceTextBox.enter(String.valueOf(filterResultData.getMinPrice()));
        }
        if (Objects.nonNull(filterResultData.getMaxPrice())) {
            maxPriceTextBox.enter(String.valueOf(filterResultData.getMaxPrice()));
        }
        if (filterResultData.isThreeStarCheck()) {
            threeStarCheckBox.click();
        }
    }

    public List<Double> getStarList(Integer hotelNumber) {
        ElementsCollection ratingList;
        if (Objects.isNull(hotelNumber)) {
            ratingList = rating.elements();
        } else {
            ratingList = (ElementsCollection) rating.elements().subList(0, hotelNumber - 1);
        }
        List<Double> rateList = new ArrayList<>();
        double stars;
        for (SelenideElement element : ratingList) {
            stars = element.innerHtml().split("StarSymbolFillIcon").length - 1;
            stars += (element.innerHtml().split("StarHalfSymbolFillIcon").length - 1) / 2;
            rateList.add(stars);
        }
        return rateList;
    }

    public boolean areTheFirstResultsDisplayedWithCorrect(Integer hotelNumber, String place, Integer minPrice, Integer maxPrice, Double star) {
        List<String> priceList, destinationList;
        if (Objects.isNull(hotelNumber)) {
            priceList = price.elements().texts();
            destinationList = destination.elements().texts();
        } else {
            priceList = price.elements().texts().subList(0, hotelNumber - 1);
            destinationList = destination.elements().texts().subList(0, hotelNumber - 1);
        }
        return destinationList.stream().allMatch(e -> e.contains(place));
    }

    Element destination = new Element("xpath=//span[@data-selenium='area-city-text']", true);
    Element lowestPriceButton = new Element("xpath=//*[self::a or self::li][.='Lowest price first']", true);
    Element sortByBestMatch = new Element("xpath=//button[.='Sort by: Best match']", true);
    Element price = new Element("xpath=//span[@class='PropertyCardPrice__Value']", true);
    Element bedroomOption = new Element("xpath=//span[.='1 bedroom']", true);
    Element minPriceTextBox = new Element("xpath=//input[@aria-label='Minimum price filter']", true);
    Element maxPriceTextBox = new Element("xpath=//input[@aria-label='Maximum price filter']", true);
    Element threeStarCheckBox = new Element("xpath=(//span[@aria-label=''])[1]//span[@role='checkbox']", true);
    Element rating = new Element("xpath=//div[@aria-label='rating']", true);

}
