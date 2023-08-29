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

    private static final Logger log = LoggerFactory.getLogger(AllureStepListener.class);
}
