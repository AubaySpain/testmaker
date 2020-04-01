package org.aubay.testmaker.domain.util;

public class ParsePathClass {

    /**
     * @param pathMethod '.' separated path of a java class method
     */
    public static String getPathClass(String pathMethod) {
    	if (pathMethod.contains(".")) {
    		return (pathMethod.substring(0, pathMethod.lastIndexOf(".")));
    	}
    	return pathMethod;
    }
    
    /**
     * @param pathClass '.' separated path of a java class
     */
    public static String getNameClass(String pathClass) {
    	if (pathClass.contains(".")) {
    		return (pathClass.substring(pathClass.lastIndexOf(".") + 1));
    	}
    	return pathClass;
    }
    
    /**
     * @param pathMethod '.' separated path of a java class method
     */
    public static String getNameMethod(String pathMethod) {
    	if (pathMethod.contains(".")) {
    		return (pathMethod.substring(pathMethod.lastIndexOf(".") + 1));
    	}
    	return pathMethod;
    }
}
