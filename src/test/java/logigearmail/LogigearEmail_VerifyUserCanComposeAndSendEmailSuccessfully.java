package logigearmail;

import base.TestBase;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.data.logigearmail.EmailData;
import org.example.page.general.GeneralPage;
import org.example.page.logigearmail.HomePage;
import org.example.page.logigearmail.LoginPage;
import org.example.report.Report;
import org.example.utils.Assertion;
import org.example.utils.Constants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

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
    @Test(description = "Verify user can compose and send email successfully")
    public void logigearEmail_VerifyUserCanComposeAndSendEmailSuccessfully() {
        Report.getInstance().step("1. Go to Logigear Mail site");
        generalPage.gotoURL("https://dnmail.logigear.com");

        Report.getInstance().step("2. Login with user: " + username);
        loginPage.login(username, password);

        Report.getInstance().step("3. Create new emai: " + emailData.toString());
        homePage.creatEmail(emailData);

        Report.getInstance().step("4. Send email");
        homePage.sendEmail();

        Report.getInstance().step("5. Click on Subject: " + subject);
        homePage.clickOnSubject(subject);

        Report.getInstance().step("6. Get email data");
        actualEmailData = homePage.getEmailData();

        Assertion.assertEquals(emailData, actualEmailData, "VP: Verify the email is sent successfully with correct info(receiver, subject, attachment, content");

        Report.getInstance().step("7. Download image in content to: " + Constants.PATH);
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
