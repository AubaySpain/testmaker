package com.github.jorge2m.testmaker.testreports.stepstore;

import static com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence.IMAGEN;

import java.nio.file.*;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public class ComparatorImages {

	private final TestCaseBean testCase1;
	private final TestCaseBean testCase2;
	private final StepTM step1;
	private final StepTM step2;
	
	private static final String ICON_NAME_REPORT = "compare_report.gif";
	
	public ComparatorImages(
			TestCaseBean testCase1, StepTM step1, TestCaseBean testCase2, StepTM step2) {
		this.testCase1 = testCase1;
		this.testCase2 = testCase2;
		this.step1 = step1;
		this.step2 = step2;
	}
	
	public boolean compareAndSave() {
		if (!IMAGEN.fileExists(testCase1, step1) ||
			!IMAGEN.fileExists(testCase2, step2)) {
			return false;
		}
		
		Path imageStep1path = Paths.get(IMAGEN.getPathFile(testCase1, step1));
		Path imageStep2path = Paths.get(IMAGEN.getPathFile(testCase2, step2));
		
		//TODO Aquí hemos de ejecutar la librería para la comparativa de imágenes
		
		Path getPathImageCompared = Paths.get(getPathImageCompared());
        try {
            Files.copy(imageStep1path, getPathImageCompared, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo copiado con éxito.");
        } catch (Exception e) {
            System.err.println("Error al copiar el archivo: " + e.getMessage());
        }
        
        return true;
	}
	
	public String getPathImageCompared() {
		var imageStep1 = IMAGEN.getPathFile(testCase1, step1);
		var imageStep1WithoutExtension = imageStep1.substring(0, imageStep1.lastIndexOf("."));
		
		return 
			imageStep1WithoutExtension + "-" + 
			testCase2.getIdExecSuite() + "-" + 
			IMAGEN.getNameFileEvidence(step2);
	}
	
	public static String getNameIcon() {
		return ICON_NAME_REPORT;
	}
	
//	public void saveContentEvidenceInFile(String content, String pathFile) {
//		//TODO esto quizás finalmente se pueda unificar con el saveContent d'una hardcopy
//		byte[] bytesPng = Base64.getMimeDecoder().decode(content);
//		File file = new File(pathFile);
//		try (
//			OutputStream stream = new FileOutputStream(file)) {
//			stream.write(bytesPng);
//		} 
//		catch (Exception e) {
//			Log4jTM.getLogger().warn("Problem saving File " + pathFile, e);
//		}
//	}

}
