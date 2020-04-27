import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver" + (System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : "" ));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver();
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver.manage().deleteAllCookies();
        driver.get("https://pe.ug.edu.pl/");
    }

    @Test
    public void checkTitleTest() {
        assertEquals("Portal Edukacyjny UG", driver.getTitle());
    }

    @Test
    public void loginTest() {
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"MemberCategory\"]")));
        select.selectByVisibleText("Student UG");

        WebElement login = driver.findElement(By.xpath("//*[@id=\"login\"]"));
        login.sendKeys("I N D E K S I K");

        WebElement pw = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        pw.sendKeys("H A S E L K O");
        pw.sendKeys(Keys.ENTER);

        WebElement logout = driver.findElement(By.xpath("/html/body/nav/form/a[2]/i"));
        assertTrue(logout.isDisplayed());
    }

    @Test
    public void loginAsNotStudentShouldNotBePassed() {
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"MemberCategory\"]")));
        select.selectByVisibleText("Wymiana dwustronna");

        WebElement login = driver.findElement(By.xpath("//*[@id=\"login\"]"));
        login.sendKeys("I N D E K S I K");

        WebElement pw = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        pw.sendKeys("H A S E L K O");
        pw.sendKeys(Keys.ENTER);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[7]"));
        assertTrue(msg.isDisplayed());
    }

    @Test
    public void loginWithoutIndexTest() {
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"MemberCategory\"]")));
        select.selectByVisibleText("Wymiana dwustronna");

        WebElement pw = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        pw.sendKeys("H A S E L K O");
        pw.sendKeys(Keys.ENTER);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[4]"));
        assertTrue(msg.isDisplayed());
    }

    @Test
    public void loginWithoutPasswordTest() {
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"MemberCategory\"]")));
        select.selectByVisibleText("Wymiana dwustronna");

        WebElement login = driver.findElement(By.xpath("//*[@id=\"login\"]"));
        login.sendKeys("I N D E K S I K");
        login.sendKeys(Keys.ENTER);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[6]"));
        assertTrue(msg.isDisplayed());
    }
}
