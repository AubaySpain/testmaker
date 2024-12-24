package com.github.jorge2m.testmaker.utils.filter.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class TestNGxmlStub extends SuiteMaker {

    public enum TypeStubTest {
    	WITHOUT_METHOS_INCLUDED_IN_CLASS,
    	ONLY_METHOD_GPO001_INCLUDED_IN_CLASS,
    	ONLY_METHOD_MIC001_INCLUDED_IN_CLASS,
    	WITH_TWO_METHODS_INCLUDED_IN_CLASS,
    	ONLY_METHOD_THAT_DOESNT_EXISTS_IN_CLASS;
    }
	
    public final static Class<?> CLASS_WITH_TEST_ANNOTATIONS = ClassWithTCasesStub.class;
    public final static String GPO001_TO_INCLUDE = "Galeria_Camisas";
    public final static String MIC001_TO_INCLUDE = "Opciones_Mi_Cuenta";
    public final static String COM001_NOT_EXISTS_IN_CLASS = "Compra_TrjSaved_Empl";
    public final static int NUMBER_TEST_CASES_DESKTOP_SHOP = 3;
    
    TypeStubTest typeTest;

    private TestNGxmlStub(TypeStubTest typeTest, InputParamsTM inputData, boolean isApiRestExecution) {
    	super(inputData, isApiRestExecution);
    	this.typeTest = typeTest;
    	TestRunMaker testRun = TestRunMaker.from("TestRun Test", CLASS_WITH_TEST_ANNOTATIONS);
    	testRun.addDependencyGroups(getDependencyGroups());
    	testRun.includeMethodsInClass(CLASS_WITH_TEST_ANNOTATIONS, getMethodsIncludedInClass());
    	addTestRun(testRun);
    }
    
    public static TestNGxmlStub getNew(TypeStubTest typeTest, InputParamsTM inputData) {
    	return getNew(typeTest, inputData, false);
    }
    
    public static TestNGxmlStub getNew(TypeStubTest typeTest, InputParamsTM inputData, boolean isApiRestExecution) {
    	TestNGxmlStub test = new TestNGxmlStub(typeTest, inputData, isApiRestExecution); 
    	return test;
    }
    
    private Map<String,String> getDependencyGroups() {
    	Map<String,String> dependencyGroups = new HashMap<>();
    	dependencyGroups.put("Buscador", "IniciarSesion");
    	dependencyGroups.put("Bolsa", "Buscador");
    	dependencyGroups.put("Compra", "Bolsa");
    	dependencyGroups.put("FichaProducto", "Compra");
    	dependencyGroups.put("GaleriaProducto", "Micuenta");
    	dependencyGroups.put("Micuenta" , "FichaProducto");
    	dependencyGroups.put("Manto", "Compra");
        return dependencyGroups;
    }
    
    public List<String> getMethodsIncludedInClass() {
    	switch (typeTest) {
    	case ONLY_METHOD_GPO001_INCLUDED_IN_CLASS:
    		return (
    			Arrays.asList(GPO001_TO_INCLUDE)
    		);
    	case ONLY_METHOD_MIC001_INCLUDED_IN_CLASS:
    		return (
    			Arrays.asList(MIC001_TO_INCLUDE)
    		);
    	case WITH_TWO_METHODS_INCLUDED_IN_CLASS:
    		return (
    			Arrays.asList(
    				GPO001_TO_INCLUDE,
    				MIC001_TO_INCLUDE)
    		);
    	case ONLY_METHOD_THAT_DOESNT_EXISTS_IN_CLASS:
    		return (
    			Arrays.asList(COM001_NOT_EXISTS_IN_CLASS)
    	    );
    	default:
    	case WITHOUT_METHOS_INCLUDED_IN_CLASS:
    		return (new ArrayList<String>());
    	}
    }
}
