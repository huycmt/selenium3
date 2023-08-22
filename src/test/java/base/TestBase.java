package base;

import com.codeborne.selenide.testng.SoftAsserts;
import org.example.config.ConfigLoader;
import org.example.driver.DriverManager;
import org.example.utils.ShareParameter;
import org.testng.annotations.*;

import java.util.Objects;

import static org.example.utils.Constants.ConfigFiles;

@Listeners({SoftAsserts.class})
public class TestBase {

    DriverManager driverManager;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "language"})
    public void beforeAll(String browser, @Optional String language) {
        ShareParameter.LANGUAGE = Objects.requireNonNullElse(language, "en");

        driverManager = new DriverManager();
        driverManager.useConfig(ConfigLoader.loadConfig(ConfigFiles.get(browser)));
        driverManager.open();
    }

    @AfterClass(alwaysRun = true)
    public void closeAll() {
        driverManager.close();
    }
}
