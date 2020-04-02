package org.jorge2m.testmaker.boundary.access;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;

public class OptionTMaker {
	
	private String pattern = null;
	private List<String> possibleValues;
	private Option option;
	
	private OptionTMaker(Option option, Builder builder) {
		this.pattern = builder.pattern;
		this.possibleValues = builder.possibleValues;
		this.option = option;
	}
	
	public static Builder builder(final String opt) {
		return new Builder(opt);
	}

	public Option getOption() {
		return this.option;
	}

	public String getPattern() {
		return this.pattern;
	}

	public List<String> possibleValues() {
		return this.possibleValues;
	}
	
	public static final class Builder {
		private String pattern;
		private List<String> possibleValues;
		private org.apache.commons.cli.Option.Builder builderOption;

		private Builder(final String opt) {
			builderOption = Option.builder(opt);
		}
		
		public Builder pattern(String pattern) {
			this.pattern = pattern;
			return this;
		}
		
		public Builder possibleValues(List<String> possibleValues) {
			this.possibleValues = possibleValues;
			return this;
		}
		
		public Builder possibleValues(Class<? extends Enum<?>> enumVar) {
			List<String> valuesString = new ArrayList<>();
			for (Enum<?> enumValue : enumVar.getEnumConstants()) {
				valuesString.add(enumValue.toString());
			}
			return possibleValues(valuesString);
		}
		
		/**
		 * Sets the display name for the argument value.
		 *
		 * @param argName the display name for the argument value.
		 * @return this builder, to allow method chaining
		 */
		public Builder argName(final String argName) {
			builderOption.argName(argName);
			return this;
		}

		/**
		 * Sets the description for this option.
		 *
		 * @param description the description of the option.
		 * @return this builder, to allow method chaining
		 */
		public Builder desc(final String description) {
		builderOption.desc(description);
			return this;
		}

		/**
		 * Sets the long name of the Option.
		 *
		 * @param longOpt the long name of the Option
		 * @return this builder, to allow method chaining
		 */  
		public Builder longOpt(final String longOpt) {
			builderOption.longOpt(longOpt);
			return this;
		}

		/** 
		 * Sets the number of argument values the Option can take.
		 *
		 * @param numberOfArgs the number of argument values
		 * @return this builder, to allow method chaining
		 */
		public Builder numberOfArgs(final int numberOfArgs) {
			builderOption.numberOfArgs(numberOfArgs);
			return this;
		}

		/** 
		 * Sets the number of argument values the Option can take.
		 *
		 * @param numberOfArgs the number of argument values
		 * @return this builder, to allow method chaining
		 */  
		public Builder optionalArg(final boolean isOptional) {
			builderOption.optionalArg(isOptional);
			return this;
		}

		/**
		 * Marks this Option as required.
		 *
		 * @return this builder, to allow method chaining
		 */
		public Builder required() {
			return required(true);
		}

		/**
		 * Sets whether the Option is mandatory.
		 *
		 * @param required specifies whether the Option is mandatory
		 * @return this builder, to allow method chaining
		 */
		public Builder required(final boolean required) {
			builderOption.required(required);
			return this;
		}

		/**
		 * Sets the type of the Option.
		 *
		 * @param type the type of the Option
		 * @return this builder, to allow method chaining
		 */
		public Builder type(final Class<?> type) {
			builderOption.type(type);
			return this;
		}

		/**
		 * The Option will use '=' as a means to separate argument value.
		 *
		 * @return this builder, to allow method chaining
		 */
		public Builder valueSeparator() {
			return valueSeparator('=');
		}

		/**
		 * The Option will use <code>sep</code> as a means to
		 * separate argument values.
		 * <p>
		 * <b>Example:</b>
		 * <pre>
		 * Option opt = Option.builder("D").hasArgs()
		 *                                 .valueSeparator('=')
		 *                                 .build();
		 * Options options = new Options();
		 * options.addOption(opt);
		 * String[] args = {"-Dkey=value"};
		 * CommandLineParser parser = new DefaultParser();
		 * CommandLine line = parser.parse(options, args);
		 * String propertyName = line.getOptionValues("D")[0];  // will be "key"
		 * String propertyValue = line.getOptionValues("D")[1]; // will be "value"
		 * </pre>
		 *
		 * @param sep The value separator.
		 * @return this builder, to allow method chaining
		 */
		public Builder valueSeparator(final char sep) {
			builderOption.valueSeparator(sep);
			return this;
		}

		/**
		 * Indicates that the Option will require an argument.
		 * 
		 * @return this builder, to allow method chaining
		 */
		public Builder hasArg() {
			return hasArg(true);
		}

		/**
		 * Indicates if the Option has an argument or not.
		 * 
		 * @param hasArg specifies whether the Option takes an argument or not
		 * @return this builder, to allow method chaining
		 */
		public Builder hasArg(final boolean hasArg) {
			builderOption.hasArg(hasArg);
			return this;
		}

		/**
		 * Indicates that the Option can have unlimited argument values.
		 * 
		 * @return this builder, to allow method chaining
		 */
		public Builder hasArgs() {
			builderOption.hasArgs();
			return this;
		}

		/**
		 * Constructs an Option with the values declared by this {@link Builder}.
		 * 
		 * @return the new {@link Option}
		 * @throws IllegalArgumentException if neither {@code opt} or {@code longOpt} has been set
		 */
		public OptionTMaker build() {
			Option option = builderOption.build();
			return new OptionTMaker(option, this);
		}
	}
}
