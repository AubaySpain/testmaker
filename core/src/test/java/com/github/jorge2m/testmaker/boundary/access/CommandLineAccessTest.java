package com.github.jorge2m.testmaker.boundary.access;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.boundary.access.MessageError;
import com.github.jorge2m.testmaker.boundary.access.OptionTMaker;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;

import org.apache.commons.cli.ParseException;

public class CommandLineAccessTest {
	
	private enum Suites {SmokeTest, PagosPaises}
	private enum AppEcom {shop, outlet, votf}

	@Test
	public void testOptionMandatory() throws ParseException {
		//Given
    	List<OptionTMaker> options = new ArrayList<>();
    	String nameParam = "mandatoryparam";
    	OptionTMaker optionMandatory = OptionTMaker.builder(nameParam)
            .required(true)
            .hasArgs()
            .desc("Mandatory param")
            .build();
    	options.add(optionMandatory);
    	
    	List<String> listaArgs = getBaseArgs();
    	listaArgs.add("-" + nameParam); listaArgs.add("valueParam");
    	String args[] = getArray(listaArgs);

    	//When
    	InputParamsBasic inputParams = new InputParamsBasic(Suites.class, AppEcom.class);
    	CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
    	List<MessageError> storedErrors = new ArrayList<>();
    	boolean check = cmdLineAccess.checkOptionsValue(storedErrors);	
    	
    	//Then
    	assertTrue(check);
    	assertTrue(storedErrors.size()==0);
	}
	
	@Test
	public void testOptionValueInListWithCorrectPattern() throws ParseException {
		//Given
    	List<OptionTMaker> options = new ArrayList<>();
    	String paramPatternOK = "patternOK";
    	options.add(
	    	OptionTMaker.builder(paramPatternOK)
	            .required(false)
	            .hasArgs()
	            .valueSeparator(',')
	            .pattern("\\d{3}")
	            .desc("Param with list values that matches the pattern")
	            .build()
    	);

    	List<String> listaArgs = getBaseArgs();
    	listaArgs.add("-" + paramPatternOK); listaArgs.add("001,652");
    	String args[] = getArray(listaArgs);

    	//When
    	InputParamsBasic inputParams = new InputParamsBasic(options, Suites.class, AppEcom.class);
    	CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
    	List<MessageError> storedErrors = new ArrayList<>();
    	boolean check = cmdLineAccess.checkOptionsValue(storedErrors);	
    	
    	//Then
    	assertTrue(check);
    	assertTrue(storedErrors.size()==0);
	}
	
	private enum TestEnum {unitario, integrado, endtoend} 
	
	@Test
	public void testOptionValueInListInEnumValues() throws ParseException {
		//Given
    	List<OptionTMaker> options = new ArrayList<>();
    	String paramValueOK = "valueOK";
    	options.add(
	    	OptionTMaker.builder(paramValueOK)
	            .required(false)
	            .hasArgs()
	            .valueSeparator(',')
	            .possibleValues(TestEnum.class)
	            .desc("Param with list values that are in enum values")
	            .build()
    	);

    	List<String> listaArgs = getBaseArgs();
    	listaArgs.add("-" + paramValueOK); listaArgs.add(TestEnum.endtoend.toString());
    	String args[] = getArray(listaArgs);

    	//When
    	InputParamsBasic inputParams = new InputParamsBasic(options, Suites.class, AppEcom.class);
    	CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
    	List<MessageError> storedErrors = new ArrayList<>();
    	boolean check = cmdLineAccess.checkOptionsValue(storedErrors);	
    	
    	//Then
    	assertTrue(check);
    	assertTrue(storedErrors.size()==0);
	}
	
	@Test
	public void testTwoOptionWithValueKO() throws ParseException {
		//Given
    	List<OptionTMaker> options = new ArrayList<>();
    	String paramPatternKO = "patternKO";
    	options.add(
	    	OptionTMaker.builder(paramPatternKO)
	            .required(false)
	            .hasArgs()
	            .valueSeparator(',')
	            .pattern("\\d{3}")
	            .desc("Param with list values that not matches the pattern")
	            .build()
    	);
    	String paramValueKO = "valueKO";
    	options.add(
	    	OptionTMaker.builder(paramValueKO)
	            .required(false)
	            .hasArgs()
	            .valueSeparator(',')
	            .possibleValues(TestEnum.class)
	            .desc("Param with list values that not are in enum values")
	            .build()
    	);

    	List<String> listaArgs = getBaseArgs();
    	listaArgs.add("-" + paramPatternKO); listaArgs.add("001,652,45");
    	listaArgs.add("-" + paramValueKO); listaArgs.add(AppEcom.shop.toString());
    	String args[] = getArray(listaArgs);

    	//When
    	InputParamsBasic inputParams = new InputParamsBasic(options, Suites.class, AppEcom.class);
    	CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
    	List<MessageError> storedErrors = new ArrayList<>();
    	boolean check = cmdLineAccess.checkOptionsValue(storedErrors);	
    	
    	//Then
    	assertTrue(!check);
    	assertTrue(contains(storedErrors, 
    		"Param " + paramPatternKO + " with value 45 that doesn't match pattern"));
    	assertTrue(contains(storedErrors, 
    		"Param " + paramValueKO + " with value " + AppEcom.shop.toString() + " is not one of the possible values " + Arrays.asList(TestEnum.values())));
	}
	
	private boolean contains(List<MessageError> listErrors, String message) {
		for (MessageError messageError : listErrors) {
			if (messageError.getMessage().contains(message)) {
				return true;
			}
		}
		return false;
	}
	
	private List<String> getBaseArgs() {
		List<String> listArgs = new ArrayList<>();
		listArgs.add("-suite"); listArgs.add("SmokeTest");
		listArgs.add("-driver"); listArgs.add("chrome");
		listArgs.add("-channel"); listArgs.add("desktop");
		listArgs.add("-application"); listArgs.add("shop");
		listArgs.add("-version"); listArgs.add("V1");
		listArgs.add("-url"); listArgs.add("https://shop.pre.mango.com/preHome.faces");
		listArgs.add("-tcases"); listArgs.add("MIC001,FAV001");
		return listArgs;
	}
	
	private String[] getArray(List<String> list) {
		return (list.toArray(new String[list.size()]));
	}
}
