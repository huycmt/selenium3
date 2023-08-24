package org.example.report;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.example.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class AllureStepListener implements StepLifecycleListener {

    @Override
    public void beforeStepStart(StepResult result) {
        log.info("[Step]: " + result.getName());
    }

    @Override
    public void beforeStepStop(StepResult result) {
        if (result.getStatus().equals(Status.FAILED)) {
            log.info("[Step] \"{}\" has been {}. Take a screenshot", result.getSteps(),
                    result.getStatus().value());
            try {
                if (Objects.nonNull(DriverManager.driver().getDriver())) {
                    ByteArrayInputStream input = new ByteArrayInputStream(DriverManager.driver().getDriver().screenshot(OutputType.BYTES));
                    Report.getInstance().attachment("ScreenShot - " + result.getSteps(), input);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    private static final Logger log = LoggerFactory.getLogger(AllureStepListener.class);
}
