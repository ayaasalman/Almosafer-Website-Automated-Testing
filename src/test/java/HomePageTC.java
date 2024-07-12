import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomePageTC extends TestData {
    public void languageTest() {
        WebElement htmlTag = driver.findElement(By.tagName("html"));
        String actualLanguage = htmlTag.getAttribute("lang");
        Assert.assertEquals(actualLanguage, expectedEnLanguage);
    }

    public void currencyTest() {
        String actualCurrency = driver.findElement(By.xpath("//button[@data-testid='Header__CurrencySelector']"))
                .getText();
        Assert.assertEquals(actualCurrency, expectedCurrency);
    }

    public void numberTest() {
        String actualContactNumber = driver.findElement(By.tagName("strong")).getText();
        Assert.assertEquals(actualContactNumber, expectedContactNumber);
    }

    public void logoTest() {
        WebElement footerTag = driver.findElement(By.tagName("footer"));
        // We could also find the logo using the tag svg because there's only one svg in
        // this div
        boolean actualQitafLogo = footerTag.findElement(By.cssSelector(".sc-fihHvN.eYrDjb"))
                .findElement(By.cssSelector(".sc-bdVaJa.bxRSiR.sc-ekulBa.eYboXF")).isDisplayed();
        Assert.assertEquals(actualQitafLogo, expectedQitafLogo);
    }

    public void hotelTabTest() {
        // boolean actualHotelTab =
        // driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).isSelected();
        // could work but isSelected() is for checkboxes.
        String actualHotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"))
                .getAttribute("aria-selected");
        Assert.assertEquals(actualHotelTab, expectedHotelTab);
    }

    public void datesTest() {
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

    public void randomLangTest() {
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
}
