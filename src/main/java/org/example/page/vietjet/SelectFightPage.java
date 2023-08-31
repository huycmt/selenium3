package org.example.page.vietjet;

import com.codeborne.selenide.ElementsCollection;
import org.example.data.vietjet.TicketInfoData;
import org.example.element.Element;
import org.example.utils.WebUtils;

import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

import static org.example.utils.ConfigResourceBundle.CONFIG_RESOURCE;
import static org.example.utils.Constants.TIME_FORMATTER;

public class SelectFightPage {

    /**
     * Get from place in the top of the page
     */
    public String getFrom() {
        followingSpan.set(CONFIG_RESOURCE.getValue("from"));
        return followingSpan.getText().split("\\(")[0].trim();
    }

    /**
     * Get to place in the top of the page
     */
    public String getTo() {
        followingSpan.set(CONFIG_RESOURCE.getValue("to"));
        return followingSpan.getText().split("\\(")[0].trim();
    }

    /**
     * Get selected day of week in the top of the page
     */
    public String getSelectedDayOfWeek() {
        return dayOfWeek.getText();
    }

    /**
     * Get selected month and date in the top of the page
     */
    public String getSelectedMonthDay() {
        return monthDay.getText();
    }

    /**
     * Get total passenger in the top of the page
     *
     * @return
     */
    public int getTotalPassenger() {
        spanContains.set(CONFIG_RESOURCE.getValue("adult"));
        return Integer.parseInt(spanContains.getText().split(" ")[0]);
    }

    /**
     * Is Select Travel Options page displayed
     */
    public boolean isSelectTravelOptionsPageDisplayed() {
        return ecoImg.isDisplayed();
    }

    /**
     * Is the ticket price displayed in currency
     */
    public boolean isTheTicketPriceDisplayedIn(String currency) {
        return fromPrice.getText().contains(currency);
    }

    /**
     * Select the first cheapest ticket in list option
     */
    public void selectTheFirstCheapestTicket() {
        WebUtils.scrollDownToTheEnd();
        WebUtils.scrollDownToTheEnd();
        ElementsCollection priceList = price.elements();
        minPrice = priceList.stream().mapToInt(e -> Integer.parseInt(e.scrollIntoView(true).getText().replaceAll(",", ""))).min().orElseThrow();
        minPriceStr = NumberFormat.getNumberInstance(Locale.US).format(minPrice);
        firstMinPrice.set(minPriceStr);
        firstMinPrice.scrollIntoView(true);
        firstMinPrice.click();
    }

    /**
     * Get ticket information from the ticket the user has selected
     *
     * @return ticket information
     */
    public TicketInfoData getTicketInfoData() {
        return TicketInfoData.builder()
                .from(getFrom())
                .to(getTo())
                .fromTime(getFromAndToTime().get(0))
                .toTime(getFromAndToTime().get(1))
                .flightNo(getFlightNo())
                .ticketType(getTicketType())
                .build();
    }

    /**
     * Get ticket type from the ticket the user has selected
     */
    public String getTicketType() {
        ticketType.set(minPriceStr);
        String type = ticketType.getAttribute("src");
        if (type.contains("eco")) return "Eco";
        if (type.contains("delux")) return "Deluxe";
        if (type.contains("skyboss")) return "SkyBoss";
        return "Business";
    }

    /**
     * Get flight no from the ticket the user has selected
     */
    public String getFlightNo() {
        flightNo.set(minPriceStr);
        return flightNo.getText();
    }

    /**
     * Get from and to time from the ticket the user has selected
     */
    public List<LocalTime> getFromAndToTime() {
        fromToTime.set(minPriceStr);
        String fromTo = fromToTime.getText();
        LocalTime from = LocalTime.parse(fromTo.split(" ")[0], TIME_FORMATTER);
        LocalTime to = LocalTime.parse(fromTo.split(" ")[2], TIME_FORMATTER);
        return List.of(from, to);
    }

    /**
     * Click Continue button
     */
    public void clickContinue() {
        continueButton.set(CONFIG_RESOURCE.getValue("continue"));
        continueButton.click();
        WebUtils.waitForJSandJQueryToLoad();
    }

    /**
     * Wait for price of list ticket displays
     */
    public void waitForPricesDisplay() {
        ecoImg.waitForVisible();
    }

    int minPrice;
    String minPriceStr;
    Element dayOfWeek = new Element("xpath=//div[contains(@class, 'slick-current')]//p[1]");
    Element monthDay = new Element("xpath=//div[contains(@class, 'slick-current')]//p[2]");
    Element fromPrice = new Element("xpath=//div[contains(@class, 'slick-current')]//p[3]");
    Element followingSpan = new Element("xpath=//span[.='%s']/following-sibling::span", true);
    Element span = new Element("xpath=//span[.='%s']", true);
    Element price = new Element("xpath=//p[.='000 VND']/preceding-sibling::p", true);
    Element firstMinPrice = new Element("xpath=(//p[.='000 VND']/preceding-sibling::p[.='%s'])[1]", true);
    Element flightNo = new Element("xpath=(//p[.='000 VND']/preceding-sibling::p[.='%s'])[1]/../../../../div[1]/div[1]", true);
    Element fromToTime = new Element("xpath=(//p[.='000 VND']/preceding-sibling::p[.='%s'])[1]/../../../../div[1]/div[2]", true);
    Element ticketType = new Element("xpath=//div[count((//div[div[p[.='%s']]])[1]/preceding-sibling::div) +1][img[contains(@src, 'amazon')]]/img", true);
    Element spanContains = new Element("xpath=//span[contains(.,'%s')]", true);
    Element continueButton = new Element("xpath=(//span[.='%s'])[1]", true);
    Element ecoImg = new Element("xpath=//div/img[contains(@src,'eco')]", true);
}
