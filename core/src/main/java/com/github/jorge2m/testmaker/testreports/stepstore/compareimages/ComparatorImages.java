package com.github.jorge2m.testmaker.testreports.stepstore.compareimages;

import static com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence.IMAGEN;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public abstract class ComparatorImages {

	public abstract boolean compareAndSave();

	public enum Comparator_Images { ASHOT, /*IMAGE_COMPARISON,*/ OVERLAY }
	
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
			Comparator_Images comparator, 
			TestCaseBean testCase1, StepTM step1, TestCaseBean testCase2, StepTM step2) {
		
		switch (comparator) {
		case ASHOT:
			return new ComparatorAshot(testCase1, step1, testCase2, step2);
//		case IMAGE_COMPARISON:
//			return new ComparatorImageComparison(testCase1, step1, testCase2, step2);
		case OVERLAY:
		default:
			return new ComparatorImageOverlay(testCase1, step1, testCase2, step2);
		}
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
	
	protected boolean saveImageCompared(BufferedImage bufferedImage) {
		var pathImageCompared = Paths.get(getPathFileCompared(testCase1, step1));
        try {
        	ImageIO.write(bufferedImage, "png", pathImageCompared.toFile());
        	return true;
        } catch (Exception e) {
        	Log4jTM.getLogger().error("Error saving compared image: " + e.getMessage());
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
