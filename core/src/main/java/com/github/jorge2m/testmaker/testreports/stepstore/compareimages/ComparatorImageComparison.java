//package com.github.jorge2m.testmaker.testreports.stepstore.compareimages;
//
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//import com.github.jorge2m.testmaker.conf.Log4jTM;
//import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
//import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
//import com.github.romankh3.image.comparison.ImageComparison;
//import com.github.romankh3.image.comparison.model.ImageComparisonResult;
//import com.github.romankh3.image.comparison.model.ImageComparisonState;
//
//import ru.yandex.qatools.ashot.comparison.ImageDiff;
//import ru.yandex.qatools.ashot.comparison.ImageDiffer;
//
//public class ComparatorImageComparison extends ComparatorImages {
//
//	public ComparatorImageComparison(TestCaseBean testCase1, StepTM step1, TestCaseBean testCase2, StepTM step2) {
//		super(testCase1, step1, testCase2, step2);
//	}
//	
//	@Override
//	public boolean compareAndSave() {
//		if (!imagesExists()) {
//			return false;
//		}
//
//		ImageComparisonResult diff = null;
//		try {
//			diff = new ImageComparison(
//			ImageIO.read(getFileImage2()), 
//			ImageIO.read(getFileImage1())).compareImages();
//        } catch (IOException e) {
//        	Log4jTM.getLogger().error("Error comparing images: " + e.getMessage());
//            return false;
//        }
//		
//		if (diff.getImageComparisonState().equals(ImageComparisonState.MISMATCH)) {
//			return saveImageCompared(diff.getResult());
//		}
//		
//		return false;		
//
//	}
//
//}
