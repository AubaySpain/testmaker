package org.jorge2m.testmaker.testreports.stepstore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

import org.jorge2m.testmaker.conf.Log4jConfig;
import org.jorge2m.testmaker.domain.suitetree.StepTM;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HardcopyStorer extends EvidenceStorer {
	
	public HardcopyStorer() {
		super(StepEvidence.imagen);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		WebDriver driver = step.getDriver();
		WebDriver newWebDriver = null;
		if (driver.getClass() == RemoteWebDriver.class) {
			newWebDriver = new Augmenter().augment(driver);
		} else {
			newWebDriver = driver;
		}
		
		String screenShot = "";
		try {
			screenShot = ((TakesScreenshot)newWebDriver).getScreenshotAs(OutputType.BASE64);
		}
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Problem capturing page", e);
		}
		return screenShot;
	}
	
	@Override
	public void saveContentEvidenceInFile(String content, String pathFile) {
		byte[] bytesPng = Base64.getMimeDecoder().decode(content);
		OutputStream stream = null;
		try {
			File file = new File(pathFile);
			stream = new FileOutputStream(file);
			stream.write(bytesPng);
		} 
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Problem saving File " + pathFile, e);
		}
	}
	
}
