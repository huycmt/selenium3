package logigearmail;

import base.TestBase;
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

    @Test(description = "Verify user can compose and save draft email successfully")
    public void logigearEmail_VerifyUserCanComposeAndSaveDraftEmailSuccessfully() {
        Report.getInstance().step("1. Go to Logigear Mail site");
        generalPage.gotoURL("https://dnmail.logigear.com//");

        Report.getInstance().step("2. Login with user: " + username);
        loginPage.login(username, password);

        Report.getInstance().step("3. Create new emai: " + emailData.toString());
        homePage.creatEmail(emailData);

        Report.getInstance().step("4. Save the email and close the composing email pop up");
        homePage.saveAndCloseEmailPopup(emailData);

        Report.getInstance().step("5. Click Draft folder in left menu");
        homePage.clickDraft();

        Report.getInstance().step("6. Click on Subject: " + subject);
        homePage.clickOnSubject(subject);

        Report.getInstance().step("7. Get email data");
        actualEmailData = homePage.getEmailData();

        Assertion.assertEquals(emailData, actualEmailData, "VP: Verify The email is saved to Draft folder successfully with correct info(receiver, subject, attachment, content)");

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
