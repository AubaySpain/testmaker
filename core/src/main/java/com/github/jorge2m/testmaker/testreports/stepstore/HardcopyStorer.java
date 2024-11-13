package com.github.jorge2m.testmaker.testreports.stepstore;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class HardcopyStorer extends StepEvidenceStorer {
	
	public HardcopyStorer() {
		super(StepEvidence.IMAGEN);
	}
	
	@Override
	protected String captureContent(StepTM step) {
//		try {
//			return new FullScreenshotCapturer(getDriver(step)).captureFullScreen();
//		} catch (Exception e) {
//			step.getSuiteParent().getLogger().warn("Problem capturing Full screen", e);
			return captureVisibleScreen(step);
//		}		
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
			Log4jTM.getLogger().warn("Problem saving File {}", pathFile, e);
		}
	}
	
	private WebDriver getDriver(StepTM step) {
		return step.getTestCaseParent().getDriver();
	}
	
	private String captureVisibleScreen(StepTM step) {
		WebDriver driver = step.getTestCaseParent().getDriver();
		WebDriver newWebDriver = null;
		String screenShot = "";
		try {
			newWebDriver = (driver instanceof RemoteWebDriver) ? new Augmenter().augment(driver) : driver;
			screenShot = ((TakesScreenshot)newWebDriver).getScreenshotAs(OutputType.BASE64);
		}
		catch (Exception e) {
			step.getSuiteParent().getLogger().warn("Problem capturing page", e);
		}
		return screenShot;
	}	
	
    
    public static class FullScreenshotCapturer {
    	
    	private final WebDriver driver;
    	private final JavascriptExecutor js;
	    private final int viewportHeight;
	    private final int pageHeight;   
	    private final List<BufferedImage> screenshots = new ArrayList<>();
	    private static final int SCROLL_DELAY = 300;
    	
    	public FullScreenshotCapturer(WebDriver driver) {
    		this.driver = driver;
    		this.js = (JavascriptExecutor) driver;
    	    this.viewportHeight = getViewPortHeight();
    	    this.pageHeight = getPageHeight();    
    	}
    	
    	public String captureFullScreen() throws IOException {

 	        Long initialScrollPosition = getInitialOffsetY();
 	        screenshots.add(captureWithoutHidingFixedElements());
 	        List<?> fixedElements = hideFixedElements();
 	        captureRemainingScreenshots();
 	        restoreFixedElements(fixedElements);
 	        goToInitialPosition(initialScrollPosition);

 	        return mergeImages(screenshots);
	 	}
	
	 	private void goToInitialPosition(Long initialScrollPosition) {
	 		js.executeScript("window.scrollTo(0, arguments[0]);", initialScrollPosition);
	 		PageObjTM.waitMillis(SCROLL_DELAY);
	 	}
	 	
	 	private BufferedImage captureWithoutHidingFixedElements() throws IOException {
	 		var capture = ImageIO.read(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));
	 		js.executeScript("window.scrollTo(0, arguments[0]);", viewportHeight);
	 		PageObjTM.waitMillis(SCROLL_DELAY);
	 		return capture;
	 	}
	
	 	private void captureRemainingScreenshots() throws IOException {
	 		int scrollPosition = viewportHeight;
	 		while (scrollPosition < pageHeight) {
	 		    BufferedImage screenshot = ImageIO.read(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));
	 		    screenshots.add(screenshot);
	
	 		    scrollPosition += viewportHeight;
	 		    js.executeScript("window.scrollTo(0, arguments[0]);", scrollPosition);
		 		PageObjTM.waitMillis(SCROLL_DELAY);
	 		}
	 	}
	
	 	private Long getInitialOffsetY() {
	 		return (Long) js.executeScript("return window.pageYOffset;");		
	 	}
	 	
	 	private int getViewPortHeight() {
	 		return driver.manage().window().getSize().getHeight();
	 	}
	 	
	 	private int getPageHeight() {
	 		return ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
	 	}
	 	
	 	private void restoreFixedElements(List<?> fixedElements) {
	 		String scriptToRestoreFixedElements = "arguments[0].forEach(function(element) {" +
	 		        "  element.style.display = '';" +
	 		        "});";
	 		js.executeScript(scriptToRestoreFixedElements, fixedElements);
	 	}
	
	 	private List<?> hideFixedElements() {
	 		String scriptToHideFixedElements = "let fixedElements = [];" +
	 		        "document.querySelectorAll('*').forEach(function(element) {" +
	 		        "  const style = window.getComputedStyle(element);" +
	 		        "  if (style.position === 'fixed') {" +
	 		        "    fixedElements.push(element);" +
	 		        "    element.style.display = 'none';" +
	 		        "  }" +
	 		        "});" +
	 		        "return fixedElements;";
	 		
	 		return (List<?>) js.executeScript(scriptToHideFixedElements);
	    }
	 	
	    private static String mergeImages(List<BufferedImage> images) throws IOException {
	        int totalHeight = images.stream().mapToInt(BufferedImage::getHeight).sum();
	        int width = images.get(0).getWidth();
	
	        BufferedImage fullImage = new BufferedImage(width, totalHeight, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = fullImage.createGraphics();
	        int yPosition = 0;
	
	        for (BufferedImage image : images) {
	            g2d.drawImage(image, 0, yPosition, null);
	            yPosition += image.getHeight();
	        }
	
	        g2d.dispose();
	        
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(fullImage, "PNG", baos);
	        byte[] imageBytes = baos.toByteArray();
	
	        return Base64.getEncoder().encodeToString(imageBytes);        
	    }
    }
	
}
