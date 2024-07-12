import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestData {
    protected static WebDriver driver = new ChromeDriver();

    String expectedEnLanguage = "en";
    String expectedArLanguage = "ar";
    String expectedCurrency = "SAR";
    String expectedContactNumber = "+966554400000";
    boolean expectedQitafLogo = true;
    String expectedHotelTab = "false";
    Random rand = new Random();

    public void configuration() {
        String url = "https://www.almosafer.com/en";
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // We can use the class to click this button because there's only one element
        // with this class
        driver.findElement(By.cssSelector(".sc-jTzLTM.hQpNle.cta__button.cta__saudi.btn.btn-primary")).click();
    }
}
