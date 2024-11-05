package com.github.jorge2m.testmaker.testreports.stepstore;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class HardcopyStorer extends StepEvidenceStorer {
	
	public HardcopyStorer() {
		super(StepEvidence.IMAGEN);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		return captureContentAsShot(step);
	}
	
	protected String captureContentAsShot(StepTM step) {
		var screenShot = new AShot()
		  .shootingStrategy(ShootingStrategies.viewportPasting(100))
		  .takeScreenshot(getDriver(step));
		
		return convertImageToString(screenShot.getImage(), step);
	}
	
	protected String captureContenSelenium(StepTM step) {
		WebDriver driver = step.getTestCaseParent().getDriver();
		WebDriver newWebDriver = null;
		String screenShot = "";
		try {
			if (driver.getClass() == RemoteWebDriver.class) {
				newWebDriver = new Augmenter().augment(driver);
			} else {
				newWebDriver = driver;
			}
			screenShot = ((TakesScreenshot)newWebDriver).getScreenshotAs(OutputType.BASE64);
		}
		catch (Exception e) {
			step.getSuiteParent().getLogger().warn("Problem capturing page", e);
		}
		return screenShot;
	}
	
	@Override
	public void saveContentEvidenceInFile(String content, String pathFile) {
		byte[] bytesPng = Base64.getMimeDecoder().decode(content);
		File file = new File(pathFile);
		try (
			OutputStream stream = new FileOutputStream(file)) {
			stream.write(bytesPng);
		} 
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem saving File " + pathFile, e);
		}
	}
	
	private WebDriver getDriver(StepTM step) {
		return step.getTestCaseParent().getDriver();
	}
	
    private String convertImageToString(BufferedImage image, StepTM step) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            outputStream.close();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
        	step.getSuiteParent().getLogger().warn("Problem capturing page", e);
            return "";
        }
    }
	
}
