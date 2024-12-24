package com.github.jorge2m.testmaker.testreports.stepstore.compareimages.applitools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.images.Eyes;
import com.github.jorge2m.testmaker.conf.ConfigLoader;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.testreports.stepstore.compareimages.ComparatorImages;
import com.github.jorge2m.testmaker.testreports.stepstore.compareimages.applitools.ApplitoolsCheckImageHandler.TypeImage;

public class ComparatorImageApplitools extends ComparatorImages {

//	private static final String APPLITOOLS_URL = "https://eyesapi.applitools.com/api/images/baseline/";
	private final String appliToolsKey;
	
	public ComparatorImageApplitools(TestCaseBean testCase1, StepTM step1, TestCaseBean testCase2, StepTM step2) {
		super(testCase1, step1, testCase2, step2);
		var configLoader = new ConfigLoader();
		this.appliToolsKey = configLoader.getProperty("testmaker.applitools.api_key");
	}
	
	@Override
	public boolean compareAndSave() {
		if (!imagesExists()) {
			return false;
		}
		
		var imagesOpt = getImagesFromSteps();
		if (imagesOpt.isEmpty()) {
			return false;
		}

		var appliToolsCheckImageHandler = new ApplitoolsCheckImageHandler(appliToolsKey);
		var newImage = imagesOpt.get().getRight();
		
		appliToolsCheckImageHandler.checkImage(newImage, getKeyStepAppliTools(testCase1, step1));
        var imageDiffOpt = appliToolsCheckImageHandler.getImage(TypeImage.DIFF);
        
        if (imageDiffOpt.isEmpty()) {
        	return false;
        }
        
		return saveImageCompared(imageDiffOpt.get());
	}
	
	public String getKeyStepAppliTools(TestCaseBean testCase, StepTM step) {
		return "TestCase: " + testCase.getNameUnique() + " / Step: " + step.getNumber();
	}
	
//	private Optional<BufferedImage> fetchBaselineImageFromAppliTools(
//			TestCaseBean testCase, StepTM step) {
//		
//		var baselineUrl = getUrlApplitoolsForFetch(testCase, step);
//	    try (InputStream in = new URL(baselineUrl).openStream()) {
//	        return Optional.of(ImageIO.read(in));
//	    } catch (IOException e) {
//	    	Log4jTM.getLogger().error("Error opening the URL data stream: {}", e.getMessage());
//	    	return Optional.empty();
//	    }
//	}
	
//	private String getUrlApplitoolsForFetch(TestCaseBean testCase, StepTM step) {
//		var imageId = getKeyImageAppliTools(testCase, step);
//		return APPLITOOLS_URL + imageId + "?apiKey=" + appliToolsKey;		
//	}
	
//	private Optional<BufferedImage> getBufferedDiffImage(TestResults testResults) {
//	    //String diffImageUrl = testResults.getStepsInfo()[0].getApiUrls().getDiffImage();
//		try {
//			var testResultHandler= new ApplitoolsTestResultsHandlerOrig(testResults, appliToolsKey);
//			testResultHandler.downloadDiffs("c:/dades/tmp/prueba.png");
//		} catch (Exception e) {
//			Log4jTM.getLogger().error("Error opening the URL data stream: {}", e.getMessage());
//		}
//		return Optional.empty();
//        
////	    if (diffImageUrl == null) {
////	        Log4jTM.getLogger().warn("No diff image URL found for the test results.");
////	        return Optional.empty();
////	    }
////	    
////	    try {
////	        URL url = new URL(diffImageUrl + "?apiKey=" + appliToolsKey);
////	        try (InputStream in = url.openStream()) {
////	            return Optional.of(ImageIO.read(in));
////	        }
////	    } catch (MalformedURLException e) {
////	        Log4jTM.getLogger().error("Malformed URL for the diff image: {}", e.getMessage());
////	        return Optional.empty();
////	    } catch (IOException e) {
////	        Log4jTM.getLogger().error("Error opening the URL data stream: {}", e.getMessage());
////	        return Optional.empty();
////	    }
//	}	
	
}
