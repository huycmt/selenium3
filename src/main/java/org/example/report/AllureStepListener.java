package org.example.report;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllureStepListener implements StepLifecycleListener {

    @Override
    public void beforeStepStart(StepResult result) {
        log.info("[Step]: " + result.getName());
    }
    private static final Logger log = LoggerFactory.getLogger(AllureStepListener.class);
}
