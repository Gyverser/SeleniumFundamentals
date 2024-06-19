package selepractoce.Test;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
public class AppTest {
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
	String url = "https://the-internet.herokuapp.com/";
	
	@Test
	public void e2eTest() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get(url);
		
		//Checkboxes
		driver.findElement(By.cssSelector("a[href='/checkboxes']")).click();
		WebElement checkbox1 = driver.findElement(By.xpath("//input[@type='checkbox'][1]"));
		WebElement checkbox2 = driver.findElement(By.xpath("//input[@type='checkbox'][2]"));
		checkbox1.click();
		if(checkbox1.isSelected() && checkbox2.isSelected()) {
			System.out.println("Both checkboxes are selected!");
		}
		driver.navigate().back();
		
		//Actions class contextClick
		WebElement contextMenu = driver.findElement(By.cssSelector("a[href='/context_menu']"));
		contextMenu.click();
		WebElement contextBox = driver.findElement(By.id("hot-spot"));
		Actions a = new Actions(driver);
		a.moveToElement(contextBox).perform();
		a.contextClick().perform();
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
		driver.navigate().back();
		
		//Actions class dragAndDrop
		WebElement dragAndDrop = driver.findElement(By.cssSelector("a[href='/drag_and_drop']"));
		dragAndDrop.click();
		WebElement drag = driver.findElement(By.id("column-a"));
		WebElement drop = driver.findElement(By.id("column-b"));
		a.dragAndDrop(drag, drop).perform();
		driver.navigate().back();
		
		//Dropdown menu - Select class
		WebElement dropdownHomePage = driver.findElement(By.cssSelector("a[href='/dropdown']"));
		dropdownHomePage.click();
		WebElement dropdown = driver.findElement(By.id("dropdown"));
		Select dropdownSelect = new Select(dropdown);
		dropdownSelect.selectByVisibleText("Option 1");
		driver.navigate().back();
		
		//Basic form authentication
		WebElement login = driver.findElement(By.cssSelector("a[href='/login']"));
		login.click();
		WebElement username = driver.findElement(By.id("username"));
		String usernameValue = "tomsmith";
		username.sendKeys(usernameValue);
		WebElement password = driver.findElement(By.name("password"));
		String passwordValue = "SuperSecretPassword!";
		password.sendKeys(passwordValue);
		WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
		submitButton.submit();
		WebElement logoutButton = driver.findElement(By.className("button"));
		logoutButton.click();
		driver.navigate().to(url);
		
		//Switching between nested frames
		WebElement frames = driver.findElement(By.cssSelector("a[href='/frames']"));
		frames.click();
		WebElement nestedFrames = driver.findElement(By.cssSelector("a[href='/nested_frames']"));
		nestedFrames.click();
		WebElement topFrame = driver.findElement(By.cssSelector("frame[name='frame-top']"));
		driver.switchTo().frame(topFrame);
		WebElement middleFrame = driver.findElement(By.cssSelector("frame[name='frame-middle']"));
		driver.switchTo().frame(middleFrame);
		driver.navigate().back();
		
		//Manipulating iframes
		WebElement iframes = driver.findElement(By.cssSelector("a[href='/iframe']"));
		iframes.click();
		WebElement iframeBox = driver.findElement(By.id("mce_0_ifr"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeBox));
		WebElement paragraf = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body/p")));
		paragraf.clear();
		String paragrafText = "Iframes tend to be tricky.";
		paragraf.sendKeys(paragrafText);
		driver.switchTo().defaultContent();
		driver.navigate().to(url);
		
		//JavaScript alerts
		WebElement alerts = driver.findElement(By.cssSelector("a[href='/javascript_alerts']"));
		alerts.click();
		WebElement firstJsButton = driver.findElement(By.xpath("//button"));
		WebElement secondJsButton = driver.findElement(By.xpath("//button[1]"));
		WebElement thirdJsButton = driver.findElement(By.xpath("//button[contains(text(),'Prompt')]"));
		firstJsButton.click();
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
		secondJsButton.click();
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().dismiss();
		thirdJsButton.click();
		wait.until(ExpectedConditions.alertIsPresent());
		String alertMessage = "Hi there.";
		driver.switchTo().alert().sendKeys(alertMessage);
		driver.switchTo().alert().accept();
		driver.quit();		
	}
}