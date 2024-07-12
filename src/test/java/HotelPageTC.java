import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HotelPageTC extends TestData {
    public void hotelTest() {
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

    public void peopleTest() {
        Select selectPeople = new Select(
                driver.findElement(By.xpath("//select[@data-testid='HotelSearchBox__ReservationSelect_Select']")));
        // If we had a list with a size we would put the size instead of '2'
        int randIndex = rand.nextInt(2);
        selectPeople.selectByIndex(randIndex);
        // 'Click search button' test case
        driver.findElement(By.xpath("//button[@data-testid='HotelSearchBox__SearchButton']")).click();
    }

    public void loadingTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
        WebElement resultsTab = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//span[@data-testid='HotelSearchResult__resultsFoundCount']")));
        Assert.assertEquals(resultsTab.getText().contains("found") || resultsTab.getText().contains("وجدنا"), true);
    }

    public void hotelSortingTest() throws InterruptedException {
        driver.findElement(By.xpath("//button[@data-testid='HotelSearchResult__sort__LOWEST_PRICE']")).click();
        Thread.sleep(3000);
        WebElement container = driver.findElement(By.cssSelector(".sc-htpNat.KtFsv.col-9"));
        List<WebElement> priceList = container.findElements(By.className("Price__Value"));
        // System.out.println(priceList.size());
        int firstPrice = Integer.parseInt(priceList.get(0).getText());
        int lastPrice = Integer.parseInt(priceList.get(priceList.size() - 1).getText());
        Assert.assertTrue(firstPrice < lastPrice);
    }

}
