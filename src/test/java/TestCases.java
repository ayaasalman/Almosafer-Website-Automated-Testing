import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestCases {

    WebDriver driver = new ChromeDriver();
    String expectedEnLanguage = "en";
    String expectedArLanguage = "ar";
    String expectedCurrency = "SAR";
    String expectedContactNumber = "+966554400000";
    boolean expectedQitafLogo = true;
    String expectedHotelTab = "false";
    Random rand = new Random();

    @BeforeTest
    public void setup() {
        String url = "https://www.almosafer.com/en";
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // We can use the class to click this button because there's only one element
        // with this class
        driver.findElement(By.cssSelector(".sc-jTzLTM.hQpNle.cta__button.cta__saudi.btn.btn-primary")).click();
    }

    @Test(priority = 1)
    public void language() {
        WebElement htmlTag = driver.findElement(By.tagName("html"));
        String actualLanguage = htmlTag.getAttribute("lang");
        Assert.assertEquals(actualLanguage, expectedEnLanguage);
    }

    @Test(priority = 2)
    public void currency() {
        String actualCurrency = driver.findElement(By.xpath("//button[@data-testid='Header__CurrencySelector']"))
                .getText();
        Assert.assertEquals(actualCurrency, expectedCurrency);
    }

    @Test(priority = 3)
    public void contactNumber() {
        String actualContactNumber = driver.findElement(By.tagName("strong")).getText();
        Assert.assertEquals(actualContactNumber, expectedContactNumber);
    }

    @Test(priority = 4)
    public void qitafLogoPresence() {
        WebElement footerTag = driver.findElement(By.tagName("footer"));
        // We could also find the logo using the tag svg because there's only one svg in
        // this div
        boolean actualQitafLogo = footerTag.findElement(By.cssSelector(".sc-fihHvN.eYrDjb"))
                .findElement(By.cssSelector(".sc-bdVaJa.bxRSiR.sc-ekulBa.eYboXF")).isDisplayed();
        Assert.assertEquals(actualQitafLogo, expectedQitafLogo);
    }

    @Test(priority = 5)
    public void hotelSearchTab() {
        // boolean actualHotelTab =
        // driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).isSelected();
        // could work but isSelected() is for checkboxes.
        String actualHotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"))
                .getAttribute("aria-selected");
        Assert.assertEquals(actualHotelTab, expectedHotelTab);
    }

    @Test(priority = 6)
    public void flightDates() {
        LocalDate today = LocalDate.now();
        // Test departure date
        int expectedDept = today.plusDays(1).getDayOfMonth();
        // We can have spaces here in the class because we have the parent and the child
        // each with their classes. Used SelectorsHub extension in Chrome.
        int actualDept = Integer.parseInt(driver
                .findElement(By.cssSelector("div[class='sc-iHhHRJ sc-kqlzXE blwiEW'] span[class='sc-cPuPxo LiroG']"))
                .getText());
        Assert.assertEquals(actualDept, expectedDept);

        // Test return date
        int expectedReturn = today.plusDays(2).getDayOfMonth();
        int actualReturn = Integer.parseInt(driver
                .findElement(By.cssSelector("div[class='sc-iHhHRJ sc-OxbzP edzUwL'] span[class='sc-cPuPxo LiroG']"))
                .getText());
        Assert.assertEquals(actualReturn, expectedReturn);

    }

    @Test(priority = 7)
    public void changeLangRandomly() {
        String[] websites = { "https://www.almosafer.com/en",
                "https://www.almosafer.com/ar" };
        int randIndex = rand.nextInt(2);
        driver.get(websites[randIndex]);

        if (driver.getCurrentUrl().contains("en")) {
            WebElement htmlTag = driver.findElement(By.tagName("html"));
            String actualLanguage = htmlTag.getAttribute("lang");
            Assert.assertEquals(actualLanguage, expectedEnLanguage);
        } else if (driver.getCurrentUrl().contains("ar")) {
            WebElement htmlTag = driver.findElement(By.tagName("html"));
            String actualLanguage = htmlTag.getAttribute("lang");
            Assert.assertEquals(actualLanguage, expectedArLanguage);
        }
    }

    @Test(priority = 8)
    public void hotelTabSelection() {
        driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).click();
        WebElement locationField = driver
                .findElement(By.xpath("//input[@data-testid='AutoCompleteInput']"));
        String[] enLocations = { "Dubai", "Jeddah", "Riyadh" };
        String[] arLocations = { "جدة", "دبي" };
        if (driver.getCurrentUrl().contains("en")) {
            int randIndex = rand.nextInt(enLocations.length);
            locationField.sendKeys(enLocations[randIndex]);
        } else if (driver.getCurrentUrl().contains("ar")) {
            int randIndex = rand.nextInt(arLocations.length);
            locationField.sendKeys(arLocations[randIndex]);
        }
        driver.findElement(By.xpath("//li[@data-testid='AutoCompleteResultItem0']")).click();
    }

    @Test(priority = 9)
    public void noOfPeople() {
        Select selectPeople = new Select(
                driver.findElement(By.xpath("//select[@data-testid='HotelSearchBox__ReservationSelect_Select']")));
        // If we had a list with a size we would put the size instead of '2'
        int randIndex = rand.nextInt(2);
        selectPeople.selectByIndex(randIndex);
        // 'Click search button' test case
        driver.findElement(By.xpath("//button[@data-testid='HotelSearchBox__SearchButton']")).click();
    }

    @Test(priority = 10)
    public void waitForLoading() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
        WebElement resultsTab = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//span[@data-testid='HotelSearchResult__resultsFoundCount']")));
        Assert.assertEquals(resultsTab.getText().contains("found") || resultsTab.getText().contains("وجدنا"), true);
    }

    @Test(priority = 11)
    public void sort() throws InterruptedException {
        driver.findElement(By.xpath("//button[@data-testid='HotelSearchResult__sort__LOWEST_PRICE']")).click();
        Thread.sleep(3000);
        WebElement container = driver.findElement(By.cssSelector(".sc-htpNat.KtFsv.col-9"));
        List<WebElement> priceList = container.findElements(By.className("Price__Value"));
        // System.out.println(priceList.size());
        int firstPrice = Integer.parseInt(priceList.get(0).getText());
        int lastPrice = Integer.parseInt(priceList.get(priceList.size() - 1).getText());
        Assert.assertTrue(firstPrice < lastPrice);
    }

    @AfterTest(enabled = false)
    public void quitChrome() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }

}
