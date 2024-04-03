package assignmentTwo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;


public class QRCodeValidator {

	public static void main(String[] args) throws MalformedURLException, IOException, NotFoundException {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://qaplayground.dev/apps/qr-code-generator/");
		
		String expText = "I am an AutomationQA";
		
		driver.findElement(By.tagName("input")).sendKeys(expText);
		
		driver.findElement(By.xpath("//button[text() = 'Generate QR Code']")).click();
		
		// Locate the QR code image element
		
		//Explicit wait
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='qr-code']/img")));
		
		WebElement qrCode =  driver.findElement(By.xpath("//div[@class='qr-code']/img"));
		
		//Get the source
		String srcImage = qrCode.getAttribute("src");
		
		// Download the image from the URL
		
		BufferedImage bufferedImage = ImageIO.read(new URL(srcImage));
		
		// Convert the image to BinaryBitmap
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        
        //decode the image content
        Result result = new MultiFormatReader().decode(binaryBitmap);

		
        SoftAssert softassert = new SoftAssert();
        softassert.assertEquals(result.getText(), expText);
        softassert.assertAll();
        
        driver.quit();
	}

}
