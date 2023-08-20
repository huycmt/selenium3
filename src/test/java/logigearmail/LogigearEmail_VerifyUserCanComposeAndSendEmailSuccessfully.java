package logigearmail;

import base.TestBase;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.data.logigearmail.EmailData;
import org.example.page.general.GeneralPage;
import org.example.page.logigearmail.HomePage;
import org.example.page.logigearmail.LoginPage;
import org.example.utils.Assertion;
import org.example.utils.Constants;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.example.driver.DriverManager.driver;

public class LogigearEmail_VerifyUserCanComposeAndSendEmailSuccessfully extends TestBase {
    @BeforeMethod
    public void setUp() {
        username = System.getProperty("lgguser");
        password = System.getProperty("lggpass");
        subject = "Subject " + RandomStringUtils.randomAlphabetic(6);
        emailData = EmailData.builder()
                .to(username)
                .subject(subject)
                .insertionImage(Constants.ATTACHMENT_PATH)
                .build();
    }

    @SneakyThrows
    @Test
    public void logigearEmail_VerifyUserCanComposeAndSendEmailSuccessfully() {
        driver().open("https://dnmail.logigear.com");

        Allure.step("Login to Logigear Mail site");
        loginPage.login(username, password);

        homePage.creatEmail(emailData);

        homePage.sendEmail();

        homePage.clickOnSubject(subject);

        actualEmailData = homePage.getEmailData();

        Assertion.assertEquals(emailData, actualEmailData, "VP: Verify the email is sent successfully with correct info(receiver, subject, attachment, content");

        homePage.downloadImage(Constants.PATH);

        Assertion.assertTrue(FileUtils.contentEquals(new File(Constants.PATH), new File(Constants.ATTACHMENT_PATH)), "VP: Verify download the image successfully");

        Assertion.assertAll("Complete running test case");
    }

    HomePage homePage = new HomePage();
    GeneralPage generalPage = new GeneralPage();
    LoginPage loginPage = new LoginPage();
    String username;
    String password;
    String subject;
    EmailData emailData;
    EmailData actualEmailData;

}
