package org.example.page.agoda;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import org.example.element.Element;

import java.util.Collections;
import java.util.List;

public class ResultPage {

    public boolean isAllDestinationHaveSearchContent(String place) {
        List<String> destinationList = destination.elements().texts();
        return destinationList.stream().allMatch(e -> e.contains(place));
    }

    Element destination = new Element("xpath=//span[@data-selenium='area-city-text']", true);

}
