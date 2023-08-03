package org.example.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class Assertion {

    public static void assertTrue(boolean condition, String step) {
        try {
            softAssertion.assertTrue(condition, step);
        } catch (AssertionError ex) {
            Allure.step(step, Status.FAILED);
            throw ex;
        }
    }

    public static void assertTrue(boolean condition, String message, String... steps) {
        try {
            softAssertion.assertTrue(condition, message);
            for (String step : steps) {
                Allure.step(step);
            }
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }

    public static void assertFalse(boolean condition, String message) {
        try {
            softAssertion.assertFalse(condition, message);
        } catch (AssertionError ex) {
            Allure.step(message, Status.FAILED);
            throw ex;
        }
    }

    public static <T> void assertEquals(T actual, T expected, String step) {
        try {
            softAssertion.assertEquals(actual, expected, step);
        } catch (AssertionError ex) {
            Allure.step(step, Status.FAILED);
            throw ex;
        }
    }


    public static <T> void assertNotEquals(T actual, T expected, String step) {
        try {
            softAssertion.assertNotEquals(actual, expected, step);
        } catch (AssertionError ex) {
            Allure.step(step, Status.FAILED);
            throw ex;
        }
    }

    public static void assertAll(String message) {
        softAssertion.assertAll(message);
    }

    public static void assertAll() {
        assertAll(null);
    }

    public static SoftAssertion softAssertion = new SoftAssertion();
}
