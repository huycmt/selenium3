import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;
import utils.ConfigLoader;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.SelenideElement.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static com.codeborne.selenide.WebDriverConditions.*;
import static com.codeborne.selenide.SelenideConfig.*;
import static utils.Constants.CHROME;

public class HomeTest {

    @Test
    public void test() {
        ConfigLoader.updateConfig(CHROME);


        SelenideElement a = $("");
        open("http://google.com");


    }
}
