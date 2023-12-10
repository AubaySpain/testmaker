package com.github.jorge2m.testmaker.service.genericchecks;

import java.io.FileNotFoundException;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.testreports.stepstore.GestorDatosHarJSON;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence;

public class UtilsChecker {

	private UtilsChecker() {}
	
	public static GestorDatosHarJSON getGestorHar(WebDriver driver) {
		GestorDatosHarJSON gestorHAR = null;
		
		StepTM step = TestMaker.getLastStep();
		SaveWhen whenSaveNettraffic = step.getWhenSave(StepEvidence.HAR);
		if (whenSaveNettraffic == SaveWhen.ALWAYS &&
			driver.toString().toLowerCase().contains("firefox")) {
			try {
				gestorHAR = new GestorDatosHarJSON(step);
			}
			catch (FileNotFoundException e) {
				//Capturamos la excepción para que no se produzca error (las posteriores validaciones generarán un warning para este caso)
				Log4jTM.getLogger().warn(
						". Not located file HAR associated to method " + step.getTestCaseParent() + 
						", step " + Integer.valueOf(step.getNumber()), e);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn(
						". Error creating Gestor for file HAR " + step.getTestCaseParent() + 
						", step " + Integer.valueOf(step.getNumber()), e);
			}
		}
		
		return gestorHAR;
	}
	
}
