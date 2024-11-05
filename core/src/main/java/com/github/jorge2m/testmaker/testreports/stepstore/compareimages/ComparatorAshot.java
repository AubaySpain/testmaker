package com.github.jorge2m.testmaker.testreports.stepstore.compareimages;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class ComparatorAshot extends ComparatorImages {

	public ComparatorAshot(TestCaseBean testCase1, StepTM step1, TestCaseBean testCase2, StepTM step2) {
		super(testCase1, step1, testCase2, step2);
	}
	
	@Override
	public boolean compareAndSave() {
		if (!imagesExists()) {
			return false;
		}
		
		ImageDiff diff = null;
		try {
			diff = new ImageDiffer().makeDiff(
				ImageIO.read(getFileImage1()), 
				ImageIO.read(getFileImage2()));
        } catch (IOException e) {
        	Log4jTM.getLogger().error("Error comparing images: " + e.getMessage());
            return false;
        }
		
		if (!diff.hasDiff()) {
			return false;
		}
		
		return saveImageCompared(diff.getMarkedImage());
	}

}
