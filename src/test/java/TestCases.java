import org.testng.annotations.*;

public class TestCases extends TestData {

    HomePageTC HomeTC = new HomePageTC();
    HotelPageTC HotelTC = new HotelPageTC();

    @BeforeTest
    public void setup() {
        configuration();
    }

    @Test(priority = 1)
    public void language() {
        HomeTC.languageTest();
    }

    @Test(priority = 2)
    public void currency() {
        HomeTC.currencyTest();
    }

    @Test(priority = 3)
    public void contactNumber() {
        HomeTC.numberTest();
    }

    @Test(priority = 4)
    public void qitafLogoPresence() {
        HomeTC.logoTest();
    }

    @Test(priority = 5)
    public void hotelSearchTab() {
        HomeTC.hotelTabTest();
    }

    @Test(priority = 6)
    public void flightDates() {
        HomeTC.datesTest();
    }

    @Test(priority = 7)
    public void changeLangRandomly() {
        HomeTC.randomLangTest();
    }

    @Test(priority = 8)
    public void hotelTabSelection() {
        HotelTC.hotelTest();
    }

    @Test(priority = 9)
    public void noOfPeople() {
        HotelTC.peopleTest();
    }

    @Test(priority = 10)
    public void waitForLoading() {
        HotelTC.loadingTest();
    }

    @Test(priority = 11)
    public void sort() throws InterruptedException {
        HotelTC.hotelSortingTest();
    }

    @AfterTest
    public void quitChrome() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }

}
