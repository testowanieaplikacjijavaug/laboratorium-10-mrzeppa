import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BingSearchTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() {
        driver.get("https://www.bing.pl");
    }

    @Test
    public void testTitlePage() {
        assertEquals("Bing", driver.getTitle());
    }

    @Test
    public void testGetLogo() {
        WebElement element = driver.findElement(By.id("b_logo"));
        assertTrue(element.isDisplayed());
    }

    @Test
    public void testSearch() {
        WebElement element = driver.findElement(By.id("sb_form_q"));
        element.sendKeys("testowanko");
        element.sendKeys(Keys.ENTER);
    }

    @Test
    public void logoAfterSearchTest() {
        WebElement element = driver.findElement(By.id("sb_form_q"));
        element.sendKeys("testowanko");
        element.sendKeys(Keys.ENTER);

        WebElement logo = driver.findElement(By.xpath("//*[@id=\"sb_form\"]/a[@class=\"b_logoArea\"]/h1[@class=\"b_logo\"]"));
        assertTrue(logo.isDisplayed());
    }

    @Test
    public void getFirst() {
        WebElement element = driver.findElement(By.id("sb_form_q"));
        element.sendKeys("testowanko");
        element.sendKeys(Keys.ENTER);

        WebElement first = driver.findElement(By.cssSelector("#b_content > main > #b_results > .b_algo > h2 > a"));

        assertNotNull(first.getText());
    }

    @Test
    public void getThird() {
        WebElement element = driver.findElement(By.id("sb_form_q"));
        element.sendKeys("testowanko");
        element.sendKeys(Keys.ENTER);

        WebElement first = driver.findElement(By.cssSelector("#b_content > main > #b_results:nth-child(2) > .b_algo > h2 > a"));

        assertNotNull(first.getText());
    }

    @Test
    public void testUnableToLocateElement() {
        try {
            driver.findElement(By.xpath("Invalid"));
            fail();
        } catch ( NoSuchElementException e ) {
            assertTrue(true);
        }

    }



}