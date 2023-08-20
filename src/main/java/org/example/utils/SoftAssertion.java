package org.example.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.example.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;
import org.testng.collections.Maps;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Objects;

public class SoftAssertion extends SoftAssert {

    @Override
    protected void doAssert(IAssert<?> a) {
        onBeforeAssert(a);
        try {
            a.doAssert();
            onAssertSuccess(a);
            Allure.step(a.getMessage(), Status.PASSED);
        } catch (AssertionError ex) {
            onAssertFailure(a, ex);
            m_errors.put(ex, a);
            Allure.step("Step failed: " + getErrorDetails(ex), Status.FAILED);
            if (Objects.nonNull(DriverManager.driver().getDriver())) {
                ByteArrayInputStream input = new ByteArrayInputStream(DriverManager.driver().getDriver().screenshot(OutputType.BYTES));
                Allure.addAttachment("ScreenShot - " + "Failed at " + a.getMessage(), input);
            }
        } finally {
            onAfterAssert(a);
        }
    }

    public void assertAll() {
        assertAll(null);
    }

    public void assertAll(String message) {
        if (!m_errors.isEmpty()) {
            Allure.step(message + " - Total check point failed: " + m_errors.size(), Status.FAILED);
            Map<AssertionError, IAssert<?>> tempErrors = m_errors;
            m_errors = Maps.newLinkedHashMap();
            throw new AssertionError(tempErrors);
        } else {
            Allure.step(message, Status.PASSED);
        }
    }

    private Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();

}