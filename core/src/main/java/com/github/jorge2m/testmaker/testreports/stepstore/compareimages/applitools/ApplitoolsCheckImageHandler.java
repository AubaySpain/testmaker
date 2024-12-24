package com.github.jorge2m.testmaker.testreports.stepstore.compareimages.applitools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.StepInfo;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsStatus;
import com.applitools.eyes.images.Eyes;
import com.github.jorge2m.testmaker.conf.Log4jTM;

public class ApplitoolsCheckImageHandler {

    private final String appliToolsKey;
    private TestResults testResults;
    private StepInfo stepInfo;
    
    public enum TypeImage { BASELINE, CURRENT, DIFF }
	
	public ApplitoolsCheckImageHandler(String appliToolsKey) {
		this.appliToolsKey = appliToolsKey;
	}

	public TestResultsStatus checkImage(BufferedImage newImage, String keyStep) {
        Eyes eyes = new Eyes();
        eyes.setApiKey(appliToolsKey); 		
		
        RectangleSize viewportSize = new RectangleSize(800, 600);
        eyes.open("TestMaker", "Image check", viewportSize);
        eyes.checkImage(newImage, keyStep);
        
        this.testResults = eyes.close(false); 
		this.stepInfo = testResults.getStepsInfo()[0];
		
	    if (Boolean.TRUE.equals(testResults.isNew())) {
	    	Log4jTM.getLogger().info("Baseline creada para el test/step: {}", keyStep);
        }  

	    eyes.abortIfNotClosed();
		return testResults.getStatus(); 
	}
	
	public String getUrlReportTest() {
		return testResults.getUrl();
	}
	
	public Optional<BufferedImage> getImage(TypeImage typeImage) {
		var imageUrlOpt = getUrlImage(typeImage);
		if (imageUrlOpt.isEmpty()) {
			return Optional.empty();
		}
		
		var imageUrl = imageUrlOpt.get();
	    try {
	    	URL url = new URL(imageUrl + "?apiKey=" + appliToolsKey);
	    	try (InputStream in = url.openStream()) {
	    		return Optional.of(ImageIO.read(in));
	    	}
	    } catch (MalformedURLException e) {
	    	Log4jTM.getLogger().error("Malformed URL for the diff image: {}", e.getMessage());
	    } catch (IOException e) {
	    	Log4jTM.getLogger().error("Error opening the URL data stream: {}", e.getMessage());
	    }
	    
    	return Optional.empty();
	}
	
	private Optional<String> getUrlImage(TypeImage typeImage) {
		if (stepInfo==null) {
			return Optional.empty();
		}
		
		switch (typeImage) {
		case BASELINE:
			return Optional.of(stepInfo.getApiUrls().getBaselineImage() + "?apiKey=" + appliToolsKey);
		case CURRENT:
			return Optional.of(stepInfo.getApiUrls().getCurrentImage() + "?apiKey=" + appliToolsKey);
		case DIFF:
			return Optional.of(stepInfo.getApiUrls().getDiffImage() + "?apiKey=" + appliToolsKey);
		default:
			return Optional.empty();
		}
	}

}
