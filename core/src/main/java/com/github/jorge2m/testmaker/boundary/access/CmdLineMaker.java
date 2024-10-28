package com.github.jorge2m.testmaker.boundary.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine; 
import org.apache.commons.cli.CommandLineParser; 
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.InputParamsTM;

/**
 * @author jorge.muñoz
 * Implementa el acceso a través de la línea de comandos
 */
public class CmdLineMaker {

	private final String[] args;
    private static final String HELP_NAME_PARAM = "help";
    private final InputParamsTM inputParams;
	
	private final CommandLineParser parser = new DefaultParser();
	private final CommandLine cmdLine;
	
	private CmdLineMaker(String[] args, InputParamsTM inputParams) throws ParseException {
		this.args = args;
		this.inputParams = inputParams;
		this.cmdLine = getParsedOptions();
		if (cmdLine!=null) {
			inputParams.setAllDataFromCommandLine(cmdLine);
		}
	}
	
	public static CmdLineMaker from(String[] args, InputParamsTM inputParams) throws ParseException {
		return new CmdLineMaker(args, inputParams);
	}
	
	public static CmdLineMaker from(String[] args) throws ParseException {
		return new CmdLineMaker(args, new InputParamsBasic());
	}	
	public static CmdLineMaker from(InputParamsTM inputParams) throws Exception {
		String [] args = getArgs(inputParams);
		return new CmdLineMaker(args, inputParams);
	}	
	
	public Class<? extends Enum<?>> getSuiteEnum() {
		return inputParams.getSuiteEnum();
	}
	public Class<? extends Enum<?>> getAppEnum() {
		return inputParams.getAppEnum();
	}
	
	private CommandLine getParsedOptions() throws ParseException {
		CommandLine cmdLineToReturn;
		CommandLine cmdLineHelp = checkHelpParameterCase(args);
		if (cmdLineHelp==null) {
			Options options = getOptions();
			try {
				cmdLineToReturn = parser.parse(options, args);
			}
			catch (ParseException e) {
				System.out.println(e.getLocalizedMessage());
				printHelpSyntaxis(options);
				return null;
			}
		} else {
			return cmdLineHelp;
		}
		return cmdLineToReturn;
	}
	
	public ResultCheckOptions checkOptionsValue() {
		boolean check=true;
		List<MessageError> storedErrors = new ArrayList<>();
		if (!checkOptionsValue(storedErrors)) {
			check=false;
			for (MessageError error : storedErrors) {
				System.out.print(error);
			}
		}
		return (ResultCheckOptions.from(check, storedErrors));
	}
	
	public CommandLine getComandLineData() {
		return this.cmdLine;
	}
	
	public InputParamsTM getInputParams() {
		return this.inputParams;
	}
	
	private Options getOptions() {
		Options optionsReturn = new Options();
		for (OptionTMaker optionTMaker : inputParams.getListAllOptions()) {
			optionsReturn.addOption(optionTMaker.getOption());
		}
		return optionsReturn;
	}
	
	/**
	 * Parseo para contemplar el caso concreto del parámetro Help
	 */
	private CommandLine checkHelpParameterCase(String[] args) {
		Options options = new Options();
		Option helpOption = Option.builder(HELP_NAME_PARAM)
			.required(false)
			.desc("shows this message")
			.build();

		try {
			options.addOption(helpOption);
			CommandLine cmdLineHelp = parser.parse(options, args);
			if (cmdLineHelp.hasOption(HELP_NAME_PARAM)) {
				printHelpSyntaxis(options);
				return cmdLineHelp;
			}
		}
		catch (ParseException e) {
			//En caso de cualquier otro parámetro <> a Help saltará el ParseException
			//Es correcto, el parseo definitivo se realiza más adelante
		}

		return null;
	}

	private void printHelpSyntaxis(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("TestMaker", getOptions());
	}


	boolean checkOptionsValue(List<MessageError> storedErrors) {
		if (cmdLine==null ||
			HELP_NAME_PARAM.compareTo(cmdLine.getOptions()[0].getOpt())==0) {
			return false;
		}
		
		boolean check = true;
		for (var optionTMaker : inputParams.getListAllOptions()) {
			String nameParam = optionTMaker.getOption().getOpt();
			String valueOption = cmdLine.getOptionValue(nameParam);
			if (optionTMaker.getOption().isRequired() && valueOption==null) {
		    	String saltoLinea = System.getProperty("line.separator");
				storedErrors.add(new MessageError("Mandatory param " + nameParam + " doesn't exists" + saltoLinea));
				check = false;
			}
			if (valueOption!=null) {
				if (!optionTMaker.getOption().hasValueSeparator()) {
					if (!checkOptionValue(optionTMaker, valueOption, storedErrors)) {
						check = false;
					}
				} else {
					String[] valuesOption = cmdLine.getOptionValues(nameParam);
					if (!checkOptionValues(optionTMaker, valuesOption, storedErrors)) {
						check = false;
					}
				}
			}
		}
		return check;
	}

    private boolean checkOptionValues(OptionTMaker optionTMaker, String[] valuesOption, List<MessageError> storedErrors) {
		for (String valueOption : valuesOption) {
			if (!checkOptionValue(optionTMaker, valueOption, storedErrors)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkOptionValue(OptionTMaker optionTMaker, String value, List<MessageError> storedErrors) {
		String nameParam = optionTMaker.getOption().getOpt();
		String stringPattern = optionTMaker.getPattern();
		String saltoLinea = System.getProperty("line.separator");
		if (stringPattern!=null && !checkPatternValue(stringPattern, value)) {
			storedErrors.add(
				new MessageError("Param " + nameParam + " with value " + value + " that doesn't match pattern " + stringPattern + saltoLinea));
			return false;
		}
		List<String> possibleValues = optionTMaker.possibleValues();
		if (possibleValues!=null && !checkPossibleValues(possibleValues, value)) {
			storedErrors.add(
				new MessageError("Param " + nameParam + " with value " + value + " is not one of the possible values " + possibleValues + saltoLinea));
			return false;
		}
		return true;
	}

	private boolean checkPatternValue(String stringPattern, String value) {
		Pattern pattern = Pattern.compile(stringPattern);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	private boolean checkPossibleValues(List<String> possibleValues, String value) {
		for (String possibleValue : possibleValues) {
			if (possibleValue.compareTo(value)==0) {
				return true;
			}
		}
		return false;
	}

	public static String[] getArgs(InputParamsTM inputParams) {
		List<String> listArgs = new ArrayList<>();
		for (Map.Entry<String,String> entryParam : inputParams.getAllParamsValues().entrySet()) {
			String valueParam = entryParam.getValue();
			if (valueParam!=null && "".compareTo(valueParam)!=0) {
				listArgs.add("-" + entryParam.getKey());
				listArgs.add(valueParam);
			}
		}
		return listArgs.stream().toArray(String[]::new);
	}
}
