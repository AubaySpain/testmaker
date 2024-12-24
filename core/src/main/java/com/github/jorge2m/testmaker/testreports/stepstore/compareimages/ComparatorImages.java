package com.github.jorge2m.testmaker.testreports.stepstore.compareimages;

import static com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence.IMAGEN;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.github.jorge2m.testmaker.testreports.stepstore.compareimages.applitools.ComparatorImageApplitools;

public abstract class ComparatorImages {

	public abstract boolean compareAndSave();

	protected final TestCaseBean testCase1;
	protected final TestCaseBean testCase2;
	protected final StepTM step1;
	protected final StepTM step2;
	
	private static final String ICON_NAME_REPORT = "compare_report.gif";
	
	protected ComparatorImages(TestCaseBean testCase1, StepTM step1, TestCaseBean testCase2, StepTM step2) {
		this.testCase1 = testCase1;
		this.testCase2 = testCase2;
		this.step1 = step1;
		this.step2 = step2;
	}
	
	public static ComparatorImages make( 
			TestCaseBean testCase1, StepTM step1, TestCaseBean testCase2, StepTM step2) {
		
		return new ComparatorImageApplitools(testCase1, step1, testCase2, step2);
	}
	
	protected boolean imagesExists() {
		return 
			IMAGEN.fileExists(testCase1, step1) &&
			IMAGEN.fileExists(testCase2, step2);
	}
	
	protected File getFileImage1() {
		return Paths.get(IMAGEN.getPathFile(testCase1, step1)).toFile();
	}
	
	protected File getFileImage2() {
		return Paths.get(IMAGEN.getPathFile(testCase2, step2)).toFile();
	}

	protected Optional<Pair<BufferedImage, BufferedImage>> getImagesFromSteps() {
		BufferedImage image1 = null;
		BufferedImage image2 = null;
		try {
			image1 = ImageIO.read(getFileImage1());
			image2 = ImageIO.read(getFileImage2());
			return Optional.of(Pair.of(image1, image2));
	    } catch (IOException e) {
	    	Log4jTM.getLogger().error("Error comparing images: {}", e.getMessage());
	        return Optional.empty();
	    }
	}

	protected boolean saveImageCompared(BufferedImage bufferedImage) {
		var pathImageCompared = Paths.get(getPathFileCompared(testCase1, step1));
        try {
        	ImageIO.write(bufferedImage, "png", pathImageCompared.toFile());
        	return true;
        } catch (Exception e) {
        	Log4jTM.getLogger().error("Error saving image: {}", e.getMessage());
            return false;
        }
	}
	
	public String getPathFileCompared(TestCaseBean testCase1, StepTM step1) {
		var pathImageFileStep1 = IMAGEN.getPathFile(testCase1, step1);
		return getPathImageCompared(pathImageFileStep1);
	}
	
	public String getPathImageCompared(TestCaseBean testCase1, StepTM step1) {
		var pathImageStep1 = IMAGEN.getPathEvidencia(testCase1, step1);
		return getPathImageCompared(pathImageStep1);
	}

	private String getPathImageCompared(String pathImageStep1) {
		var imageStep1WithoutExtension = pathImageStep1.substring(0, pathImageStep1.lastIndexOf("."));
		return 
			imageStep1WithoutExtension + "-" + 
			testCase2.getIdExecSuite() + "-" + 
			IMAGEN.getNameFileEvidence(step2);
	}
	
	public static String getNameIcon() {
		return ICON_NAME_REPORT;
	}
	
}
