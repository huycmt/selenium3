package org.example.report;

import io.qameta.allure.Allure;

import java.io.InputStream;
import java.util.Objects;

public class Report {

    private static volatile Report instance;
    private static Object mutex = new Object();

    private Report() {
    }

    public static Report getInstance() {
        Report result = instance;
        if (Objects.isNull(result)) {
            synchronized (mutex) {
                result = instance;
                if (Objects.isNull(result)) instance = result = new Report();
            }
        }
        return result;
    }

    public void step(String name) {
        Allure.step(name);
    }

    public void step(String name, Status status) {
        Allure.step(name, getStatus(status));
    }

    public void attachment(String name, InputStream input) {
        Allure.attachment(name, input);
    }

    private io.qameta.allure.model.Status getStatus(Status status) {
        switch (status) {
            case PASSED:
                return io.qameta.allure.model.Status.PASSED;
            case FAILED:
                return io.qameta.allure.model.Status.FAILED;
            case SKIPPED:
                return io.qameta.allure.model.Status.SKIPPED;
        }
        return io.qameta.allure.model.Status.BROKEN;
    }
}
