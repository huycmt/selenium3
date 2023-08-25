package org.example.report;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import org.example.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class AllureTestListener implements TestLifecycleListener {

    @Override
    public void beforeTestStop(TestResult result) {
        if (result.getStatus().equals(Status.BROKEN)) {
            log.info("Test case \"{}\" has been {}. Take a screenshot", result.getFullName(),
                    result.getStatus().value());
            try {
                if (Objects.nonNull(DriverManager.driver().getDriver())) {
                    ByteArrayInputStream input = new ByteArrayInputStream(DriverManager.driver().getDriver().screenshot(OutputType.BYTES));
                    Report.getInstance().attachment("ScreenShot - " + result.getFullName(), input);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    private static final Logger log = LoggerFactory.getLogger(AllureTestListener.class);
}
