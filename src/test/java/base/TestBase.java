package base;

import org.example.config.ConfigLoader;
import org.example.driver.DriverManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import static org.example.utils.Constants.ConfigFiles;

public class TestBase {

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void beforeAll(String browser) {
        driverManager.useConfig(ConfigLoader.loadConfig(ConfigFiles.get(browser)));
        driverManager.open();
    }

    DriverManager driverManager = new DriverManager();
}
