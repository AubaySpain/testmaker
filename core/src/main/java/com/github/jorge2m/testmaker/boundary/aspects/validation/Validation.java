package com.github.jorge2m.testmaker.boundary.aspects.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.conf.SendType;
import com.github.jorge2m.testmaker.conf.State;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
	String description() default "";
	State level() default State.Defect;
	StoreType store() default StoreType.Evidences;
	SendType send() default SendType.None;
}
