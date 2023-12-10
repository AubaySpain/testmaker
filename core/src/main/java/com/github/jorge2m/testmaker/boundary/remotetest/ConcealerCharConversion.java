package com.github.jorge2m.testmaker.boundary.remotetest;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

/**
 * In ocassions the Remote Test implemented in RemoteTest.java returns a object Suite with literals incorrected 
 * transformed from UTF-8 to ISO that contains incorrect characters like "Ã¡" (from 'á'). 
 * It seems is a bug of the Embedded Jetty 9.2.14.v20151106, I tried to evolve to the latest version 9.4.27.v20200227
 * But then the server start crashes with other exception. 
 * That class is a workarround to solve the problem, detects incorrect transformed to ISO characters and then 
 * transform the whole literal to UTF-8.
 * @author jorge.muñoz
 *
 */
public class ConcealerCharConversion {

	private static final List<String> LIST_WRONG_ISO_CHARS = Arrays.asList(
		"Â", "Â¡", "Â¢", "Â£", "Â¤", "Â¥", "Â¦", "Â§", "Â¨", "Â©", "Âª", "Â«", "Â", "Â­", "Â®", "Â¯", "Â°", "Â±", "Â²", 
		"Â³", "Â´", "Âµ", "Â", "Â·", "Â¸", "Â¹", "Âº", "Â»", "Â¼", "Â½", "Â¾", "Â¿", "Ã€", "Ã",	"Ã‚", "Ãƒ", "Ã„", "Ã…", 
		"Ã†", "Ã‡", "Ãˆ", "Ã‰", "ÃŠ", "Ã‹", "ÃŒ", "ÃŽ",	"Ã‘", "Ã’", "Ã“", "Ã”", "Ã•", "Ã–", "Ã—", "Ã˜", "Ã™", "Ãš", "Ã›", 
		"Ãœ", "Ãž", "ÃŸ", "Ã", "Ã¡", "Ã¢", "Ã£", "Ã¤", "Ã¥", "Ã¦", "Ã§", "Ã¨", "Ã©", "Ãª", "Ã«", "Ã", "Ã­", "Ã®", "Ã¯", 
		"Ã°", "Ã±", "Ã²", "Ã³", "Ã´", "Ãµ", "Ã", "Ã·", "Ã¸", "Ã¹", "Ãº", "Ã»", "Ã¼", "Ã½", "Ã¾", "Ã¿");
	
	public static void conceal(TestCaseBean testCase) {
		String tcaseDescription = testCase.getDescription();
		testCase.setDescription(conceal(tcaseDescription));
		for (StepTM step : testCase.getListStep()) {
			conceal(step);
		}
	}
	
	private static void conceal(StepTM step) {
		String stepDescription = step.getDescripcion();
		step.setDescripcion(conceal(stepDescription));
		String stepResExpected = step.getResExpected();
		step.setResExpected(conceal(stepResExpected));
		for (ChecksTM checks : step.getListChecksTM()) {
			conceal(checks);
		}
	}
	
	private static void conceal(ChecksTM checks) {
		for (Check check : checks.getListChecks()) {
			check.setDescription(conceal(check.getDescription()));
		}
	}
	
	private static String conceal(String text) {
		if (!isConvertedOk(text)) {
			try {
				return new String(text.getBytes("ISO-8859-1"),"UTF-8");
			}
			catch (UnsupportedEncodingException e) {
				Log4jTM.getLogger().info("Unsupported encoding in conceal method", e);
				return text;
			}
		}
		return text;
	}
	
	private static boolean isConvertedOk(String text) {
		for (String item : LIST_WRONG_ISO_CHARS) {
			if (text.contains(item)) {
				return false;
			}
		}
		return true;
	}
}
