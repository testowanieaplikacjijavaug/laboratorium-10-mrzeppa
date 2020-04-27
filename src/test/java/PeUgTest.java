import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PeUgTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver.get("https://pe.ug.edu.pl");
    }

    @Test
    public void countLinks() {
        List<WebElement> list = driver.findElements(By.xpath(".//a"));
        int size = list.size();
        assertEquals(16, size);
    }

    @Test
    public void countImages() {
        List<WebElement> list = driver.findElements(By.xpath(".//img"));
        int size = list.size();
        assertEquals(2, size);
    }

    @Test
    public void testLinks() throws InterruptedException {
        List<String> urls = driver.findElements(By.xpath("//a"))
                .stream()
                .map(x -> x.getAttribute("href"))
                .collect(Collectors.toList());
        String startTitle = driver.getTitle();
        for (String url : urls) {
            driver.get(url);
            driver.navigate().back();
        }

        assertEquals("Portal Edukacyjny UG", startTitle);
    }

    @Test
    public void countInputs() {
        List<WebElement> formElements = driver.findElement(By.xpath("//form")).findElements(By.xpath("./*"));

        assertFalse(formElements.isEmpty());
    }

    @AfterAll
    public static void tearDown() {
        driver.close();
    }

}