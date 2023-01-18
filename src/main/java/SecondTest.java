import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class SecondTest {
    WebDriver driver = new ChromeDriver();
    void scrollTo(int height){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(380, " + height + ")");
    }
    /** Номера карт
     * 4004 1591 1544 9003
     * 01/2021   cvv - 301
     *
     * 5309 2330 3476 5085
     * 01/2024   cvv - 891
     */
    //Описание элементов страницы
    By phoneNumber = By.xpath("//input[@data-qa-node='phone-number']");
    By summa = By.xpath("//input[@data-qa-node='amount']");
    By cardDebit = By.xpath("//input[@data-qa-node='numberdebitSource']");
    By expireDebit = By.xpath("//input[@data-qa-node='expiredebitSource']");
    By cvvDebit = By.xpath("//input[@data-qa-node='cvvdebitSource']");
    By firstNameDebit = By.xpath("//input[@data-qa-node='firstNamedebitSource']");
    By lastNameDebit = By.xpath("//input[@data-qa-node='lastNamedebitSource']");
    By submitBtn = By.xpath("//button[@type='submit']");

    By commission = By.xpath("//div[@data-qa-node='commission']");
    By totalSumma = By.xpath("//div[@data-qa-node='total-amount']");

    By termsLink = By.xpath("//a[@href='https://privatbank.ua/terms']");

    //Cтраница проверки
    By category = By.xpath("//div[@data-qa-node='category']");
    By details = By.xpath("//div[@data-qa-node='details']");
    By checkCard = By.xpath("//td[@data-qa-node='card']");
    By checkSumma = By.xpath("//div[@data-qa-node='amount']");
    By checkCommission = By.xpath("//span[@data-qa-node='commission']");

    By buttonToClick = By.xpath("//div[@class='cut_Lbdil0q5_v']");


    @Test
    void checkAddToBasketPaymentSum(){
        String numberOfCard = "5309233034765085";
        String numberOfPhone = "985220645";

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://next.privat24.ua/mobile");

        driver.findElement(phoneNumber).sendKeys(numberOfPhone);
        driver.findElement(summa).sendKeys(Keys.chord(Keys.CONTROL, "a"), "500");
        driver.findElement(cardDebit).sendKeys(numberOfCard);
        driver.findElement(expireDebit).sendKeys("0124");
        driver.findElement(cvvDebit).sendKeys("891");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(firstNameDebit).sendKeys("TEST");
        driver.findElement(lastNameDebit).sendKeys("TESTOVICH");
        String commissionSumma = driver.findElement(commission).getText().substring(0,driver.findElement(commission).getText().indexOf(" "));
        driver.findElement(submitBtn).submit();
        //Assertions
        assertEquals("Поповнення мобільного", driver.findElement(category).getText());
        assertEquals("500 UAH", driver.findElement(checkSumma).getText());
        assertEquals(numberOfCard.substring(0,4), driver.findElement(checkCard).getText().substring(0,4));
        assertEquals(numberOfCard.substring(13,16), driver.findElement(checkCard).getText().substring(16,19));
        assertEquals(numberOfPhone,driver.findElement(details).getText().substring(34,43));
        assertEquals(commissionSumma,driver.findElement(checkCommission).getText());
        driver.close();
    }
    @Test
    void checkTermsAndConditions() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://next.privat24.ua/mobile");
        driver.findElement(termsLink).click();
        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

        scrollTo(280);

        driver.switchTo().frame("frame");

        assertEquals("https://privatbank.ua/terms", driver.getCurrentUrl());
        assertEquals("Умови та правила", driver.getTitle());
        assertEquals("Умови та правила надання банківських послуг",
                driver.findElement(By.xpath("//a[@href='/main/?lang=uk']")).getText());
        driver.quit();
    }
    @Test
    void checkHideText(){
        By hideLink = By.xpath("//path[@d='M0 0h24v24H0z']");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://next.privat24.ua/mobile");
        driver.findElement(buttonToClick).click();
       //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
//        JavascriptExecutor jse = (JavascriptExecutor)driver;
//        jse.executeScript("window.scrollBy(0,250)");


        //driver.findElement(hideLink).click();
        driver.quit();


    }
}
