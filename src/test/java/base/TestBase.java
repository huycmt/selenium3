package base;

import com.codeborne.selenide.testng.SoftAsserts;
import org.example.config.ConfigLoader;
import org.example.driver.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import static org.example.utils.Constants.ConfigFiles;

@Listeners({SoftAsserts.class})
public class TestBase {

    DriverManager driverManager;

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void beforeAll(String browser) {
        driverManager = new DriverManager();
        driverManager.useConfig(ConfigLoader.loadConfig(ConfigFiles.get(browser)));
        driverManager.open();
    }

    @AfterClass(alwaysRun = true)
    public void closeAll() {
        driverManager.close();
    }
}
