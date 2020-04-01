package org.aubay.testmaker.boundary.aspects;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class MatcherWithMethodParams {
	
	private final JoinPoint joinPoint;
	
	private MatcherWithMethodParams(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
	}
	
	public static MatcherWithMethodParams from(JoinPoint joinPoint) {
		return (new MatcherWithMethodParams(joinPoint));
	}
	
	public String match(String litWithTags) {
		String litToReturn = litWithTags;
		Pattern p = Pattern.compile("\\#\\{([^}]*)\\}");
		Matcher m = p.matcher(litWithTags);
		while (m.find()) {
		  String group = m.group(1);
		  String valueParameter = getStringValueParameterFromMethod(group);
		  if (valueParameter==null) {
			  valueParameter="null";
		  }
		  litToReturn = litToReturn.replace("#{" + group + "}", valueParameter);
		}
		
		return (litToReturn);
	}

	private String getStringValueParameterFromMethod(String paramNameInDescrValidation) {
		try {
			TagData tagData = new TagData(paramNameInDescrValidation);
			Object[] signatureArgs = joinPoint.getArgs();
			String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
			for (int i=0; i<parameterNames.length; i++) {
				if (parameterNames[i].compareTo(tagData.nameParameter)==0) {
					return (getStringFromParameter(tagData, signatureArgs[i]));
				}
			}
		}
		catch (Exception e) {
			return "null";
		}
		
		return paramNameInDescrValidation;
	}

	private String getStringFromParameter(TagData tagData, Object parameter) throws Exception {
		if ("".compareTo(tagData.methodWithoutParams1)!=0) {
			for (Method methodFromParam : parameter.getClass().getMethods()) {
				if (tagData.methodWithoutParams1.compareTo(methodFromParam.getName())==0) {
					Object resultInvocation1rstLevelFunction = methodFromParam.invoke(parameter);
					if ("".compareTo(tagData.methodWithoutParams2)==0) {
						return (resultInvocation1rstLevelFunction.toString());
					}
					for (Method methodFromObjectReturnLevel1 : resultInvocation1rstLevelFunction.getClass().getMethods()) {
						if (tagData.methodWithoutParams2.compareTo(methodFromObjectReturnLevel1.getName())==0) {
							Object resultInvoation2onLevelFunction = methodFromObjectReturnLevel1.invoke(resultInvocation1rstLevelFunction);
							return (resultInvoation2onLevelFunction.toString());
						}
					}
				}
			}
		}
	
		return (parameter.toString());
	}

	/**
	 * Obtiene los datos de un String del tipo "parametroX.methodY()";
	 *
	 */
	public static class TagData {
		public String nameParameter = "";
		public String methodWithoutParams1 = "";
		public String methodWithoutParams2 = "";

		public TagData(String varAndMethod) {
			Pattern p = Pattern.compile("(.*)\\.(.*)\\(\\)\\.(.*)\\(\\)");
			Matcher m = p.matcher(varAndMethod);
			if (m.find()) {
				nameParameter = m.group(1);
				methodWithoutParams1 = m.group(2).replace(")","").replace("(","");
				methodWithoutParams2 = m.group(3).replace(")","").replace("(","");
			} else {
				p = Pattern.compile("(.*)\\.(.*)\\(\\)");
				m = p.matcher(varAndMethod);
				if (m.find()) {
					nameParameter = m.group(1);
					methodWithoutParams1 = m.group(2).replace(")","").replace("(","");
				} else {
					nameParameter = varAndMethod;
				}
			}
		}
	}
}
