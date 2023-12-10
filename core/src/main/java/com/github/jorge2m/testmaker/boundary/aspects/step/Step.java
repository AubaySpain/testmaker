package com.github.jorge2m.testmaker.boundary.aspects.step;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Step {
	String description() default "";
	String expected() default "";
	SaveWhen saveImagePage() default SaveWhen.IF_PROBLEM;
	SaveWhen saveErrorData() default SaveWhen.IF_PROBLEM;
	SaveWhen saveHtmlPage() default SaveWhen.NEVER;
	SaveWhen saveNettraffic() default SaveWhen.NEVER;
}
