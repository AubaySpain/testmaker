package com.github.jorge2m.testmaker.testreports.stepstore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

public class HardcopyStorer extends EvidenceStorer {
	
	public HardcopyStorer() {
		super(StepEvidence.Imagen);
	}
	
	@Override
	protected String captureContent(StepTM step) {
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
	
}
