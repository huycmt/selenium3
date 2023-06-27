package org.example.page.agoda;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import org.example.element.Element;

import java.util.Collections;
import java.util.List;

public class ResultPage {

    public void isAllDestinationHaveSearchContent(String place) {
        ElementsCollection destinationList = destination.elements();
        List<String> expectedText = Collections.nCopies(destinationList.size(), place);
        destinationList.shouldHave(CollectionCondition.texts(expectedText));
    }

    Element destination = new Element("xpath=//span[@data-selenium='area-city-text']", true);

}
