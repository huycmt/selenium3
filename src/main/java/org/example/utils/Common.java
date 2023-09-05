package org.example.utils;

import org.example.report.Report;
import org.example.report.Status;

import java.util.List;
import java.util.stream.Collectors;

public class Common {

    public static <T> List<T> sort(List<T> list) {
        return list.stream().sorted().collect(Collectors.toList());
    }

    public static boolean areAllElementsContainsOption(List<String> list, String option) {
        if (!list.stream().filter(e -> e.contains(option)).findFirst().isPresent()) {
            Report.getInstance().step(String.format("Option '%s' does not exists in element of list %s", option, list), Status.FAILED);
            return false;
        }
        return true;
    }
}
