package org.example.page.vietjet;

import com.codeborne.selenide.ElementsCollection;
import org.example.data.vietjet.TicketInfoData;
import org.example.element.Element;
import org.example.utils.WebUtils;

import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.example.utils.ConfigResourceBundle.CONFIG_RESOURCE;
import static org.example.utils.Constants.TIME_FORMATTER;

public class SelectFightPage {

    public String getFrom() {
        followingSpan.set(CONFIG_RESOURCE.getValue("from"));
        return followingSpan.getText().split("\\(")[0].trim();
    }

    public String getTo() {
        followingSpan.set(CONFIG_RESOURCE.getValue("to"));
        return followingSpan.getText().split("\\(")[0].trim();
    }

    public String getSelectedDayOfWeek() {
        return dayOfWeek.getText();
    }

    public String getSelectedMonthDay() {
        return monthDay.getText();
    }

    public int getTotalPassenger() {
        spanContains.set(CONFIG_RESOURCE.getValue("adult"));
        return Integer.parseInt(spanContains.getText().split(" ")[0]);
    }

    public boolean isSelectTravelOptionsPageDisplayed() {
        return businessImg.isDisplayed();
    }

    public boolean isTheTicketPriceDisplayedIn(String currency) {
        return fromPrice.getText().contains(currency);
    }

    public void selectTheFirstCheapestTicket() {
        WebUtils.scrollDownToTheEnd();
        WebUtils.scrollDownToTheEnd();
        ElementsCollection priceList = price.elements();
        minPrice = priceList.stream().mapToInt(e -> Integer.parseInt(e.scrollIntoView(true).getText().replaceAll(",", ""))).min().orElseThrow();
        firstMinPrice.set(NumberFormat.getNumberInstance(Locale.US).format(minPrice));
        firstMinPrice.scrollIntoView(true);
        firstMinPrice.click();
    }

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

    public String getTicketType() {
        ticketType.set(minPrice);
        String type = ticketType.getAttribute("src");
        if (type.contains("eco")) return "Eco";
        if (type.contains("delux")) return "Deluxe";
        if (type.contains("skyboss")) return "SkyBoss";
        return "Business";
    }

    public String getFlightNo() {
        flightNo.set(minPrice);
        return flightNo.getText();
    }

    public List<LocalTime> getFromAndToTime() {
        fromToTime.set(minPrice);
        String fromTo = fromToTime.getText();
        LocalTime from = LocalTime.parse(fromTo.split(" ")[0], TIME_FORMATTER);
        LocalTime to = LocalTime.parse(fromTo.split(" ")[2], TIME_FORMATTER);
        return List.of(from, to);
    }

    public void clickContinue() {
        continueButton.set(CONFIG_RESOURCE.getValue("continue"));
        continueButton.click();
        WebUtils.waitForJSandJQueryToLoad();
    }

    public void waitForPricesDisplay() {
        businessImg.waitForVisible();
    }

    private int minPrice;
    private Element dayOfWeek = new Element("xpath=//div[contains(@class, 'slick-current')]//p[1]");
    private Element monthDay = new Element("xpath=//div[contains(@class, 'slick-current')]//p[2]");
    private Element fromPrice = new Element("xpath=//div[contains(@class, 'slick-current')]//p[3]");
    private Element followingSpan = new Element("xpath=//span[.='%s']/following-sibling::span", true);
    private Element span = new Element("xpath=//span[.='%s']", true);
    private Element price = new Element("xpath=//p[.='000 VND']/preceding-sibling::p", true);
    private Element firstMinPrice = new Element("xpath=(//p[.='000 VND']/preceding-sibling::p[.='%s'])[1]", true);
    private Element flightNo = new Element("xpath=(//p[.='000 VND']/preceding-sibling::p[.='%d'])[1]/../../../../div[1]/div[1]", true);
    private Element fromToTime = new Element("xpath=(//p[.='000 VND']/preceding-sibling::p[.='460'])[1]/../../../../div[1]/div[2]", true);
    private Element ticketType = new Element("xpath=//div[count((//div[div[p[.='%d']]])[1]/preceding-sibling::div) +1][img[contains(@src, 'amazon')]]/img", true);
    private Element spanContains = new Element("xpath=//span[contains(.,'%s')]", true);
    private Element continueButton = new Element("xpath=(//span[.='%s'])[1]", true);
    private Element businessImg = new Element("xpath=//div/img[contains(@src,'business')]", true);
}
