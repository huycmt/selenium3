package org.example;

import static org.apache.commons.lang3.text.WordUtils.containsAllWords;

public class Main {

    public static void main(String[] args) {
        String str = "Tuyen Lam Lake, Dalat - 4.9 km to center".toLowerCase();
        String place = "Da Lat".toLowerCase();
        containsAllWords(str.toLowerCase(), place.toLowerCase().split(" "));
        System.out.println(!str.contains(place) || !str.contains(String.join("",place.split(" "))));
    }
}