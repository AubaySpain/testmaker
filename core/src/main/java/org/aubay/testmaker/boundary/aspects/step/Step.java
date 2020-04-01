package org.aubay.testmaker.boundary.aspects.step;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Step {
	String description() default "";
	String expected() default "";
	SaveWhen saveImagePage() default SaveWhen.IfProblem;
	SaveWhen saveErrorData() default SaveWhen.IfProblem;
	SaveWhen saveHtmlPage() default SaveWhen.Never;
	SaveWhen saveNettraffic() default SaveWhen.Never;
}
