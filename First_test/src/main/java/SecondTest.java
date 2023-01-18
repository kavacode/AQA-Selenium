import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class SecondTest {
    WebDriver driver = new ChromeDriver();
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


    @Test
    void checkAddToBasketPaymentSum(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://next.privat24.ua/mobile");
        driver.manage().window().fullscreen();
        driver.findElement(phoneNumber).sendKeys("985220645");
        driver.findElement(summa).sendKeys(Keys.chord(Keys.CONTROL, "a"), "55");
        driver.findElement(cardDebit).sendKeys("5309233034765085");
        driver.findElement(expireDebit).sendKeys("0124");
        driver.findElement(cvvDebit).sendKeys("891");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.findElement(firstNameDebit).sendKeys("TEST");
        driver.findElement(lastNameDebit).sendKeys("TESTOVICH");
        driver.findElement(submitBtn).submit();
    }
    @Test
    void checkTermsAndConditions() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://next.privat24.ua/mobile");

        driver.findElement(termsLink).click();
        driver.manage().window().fullscreen();
        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());


        driver.switchTo().frame("frame");

        Assertions.assertEquals("https://privatbank.ua/terms", driver.getCurrentUrl());
        Assertions.assertEquals("Умови та правила", driver.getTitle());
        Assertions.assertEquals("Умови та правила надання банківських послуг",
                driver.findElement(By.xpath("//a[@href='/main/?lang=uk']")).getText());
        driver.quit();
    }
}
