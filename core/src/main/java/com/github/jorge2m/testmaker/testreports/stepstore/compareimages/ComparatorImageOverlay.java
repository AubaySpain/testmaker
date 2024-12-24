package com.github.jorge2m.testmaker.testreports.stepstore.compareimages;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public class ComparatorImageOverlay extends ComparatorImages {

	public ComparatorImageOverlay(TestCaseBean testCase1, StepTM step1, TestCaseBean testCase2, StepTM step2) {
		super(testCase1, step1, testCase2, step2);
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
		
		var image1 = imagesOpt.get().getLeft();
		var image2 = imagesOpt.get().getRight();
		
        BufferedImage combinedImage = new BufferedImage(
                image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = combinedImage.createGraphics();
        g.drawImage(image1, 0, 0, null);

        float alpha = 0.65f; // Valor de 0.0f a 1.0f (0.0 completamente transparente, 1.0 opaco)
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        g.drawImage(image2, 0, 0, null);
        g.dispose();

		return saveImageCompared(combinedImage);
	}

}
