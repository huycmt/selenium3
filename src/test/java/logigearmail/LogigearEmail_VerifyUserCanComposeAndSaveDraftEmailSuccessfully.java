package logigearmail;

import base.TestBase;
import io.qameta.allure.Allure;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.data.agoda.FilterResultData;
import org.example.data.logigearmail.EmailData;
import org.example.page.agoda.ResultPage;
import org.example.page.general.GeneralPage;
import org.example.page.logigearmail.HomePage;
import org.example.page.logigearmail.LoginPage;
import org.example.utils.Assertion;
import org.example.utils.Constants;
import org.example.utils.WebUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.example.driver.DriverManager.driver;

public class LogigearEmail_VerifyUserCanComposeAndSaveDraftEmailSuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        username = System.getProperty("lgguser");
        password = System.getProperty("lggpass");
        content = "Content " + RandomStringUtils.randomAlphabetic(6);
        subject = "Subject " + RandomStringUtils.randomAlphabetic(6);
        emailData = EmailData.builder()
                .to(username)
                .subject(subject)
                .content(content)
                .attachment(Constants.ATTACHMENT_PATH)
                .build();
    }

    @Test
    public void logigearEmail_VerifyUserCanComposeAndSaveDraftEmailSuccessfully() {
        generalPage.open("https://dnmail.logigear.com//");

        Allure.step("Login to Logigear Mail site");
        loginPage.login(username, password);

        homePage.creatEmailToDraft(emailData);

        homePage.clickDraft();

        homePage.clickOnSubject(subject);

        actualEmailData = homePage.getEmailData();

        Assertion.assertEquals(emailData, actualEmailData, "VP: Verify The email is save to Draft folder successfully with correct info(receiver, subject, attachment, content)");

        Assertion.assertAll("Complete running test case");

    }

    HomePage homePage = new HomePage();
    GeneralPage generalPage = new GeneralPage();
    LoginPage loginPage = new LoginPage();
    String username;
    String password;
    String content;
    String subject;
    EmailData emailData;
    EmailData actualEmailData;
}
