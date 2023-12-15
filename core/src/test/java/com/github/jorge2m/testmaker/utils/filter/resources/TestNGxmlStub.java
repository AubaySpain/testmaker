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
    	WithoutMethodsIncludedInClass,
    	OnlyMethodGpo001includedInClass,
    	OnlyMethodMic001includedInClass,
    	WithTwoMethodsIncludedInClass,
    	OnlyMethodThatDoesntExistInClass;
    }
	
    public final static Class<?> classWithTestAnnotations = ClassWithTCasesStub.class;
    public final static String methodGroupGaleriaProductoToInclude = "GPO001_Galeria_Camisas";
    public final static String methodGroupMiCuentaToInclude = "MIC001_Opciones_Mi_Cuenta";
    public final static String methodThatDoesNotExistsInClass = "COM001_Compra_TrjSaved_Empl";
    public final static int numberTestsCasesDesktopShop = 3;
    
    TypeStubTest typeTest;

    private TestNGxmlStub(TypeStubTest typeTest, InputParamsTM inputData) {
    	super(inputData);
    	this.typeTest = typeTest;
    	TestRunMaker testRun = TestRunMaker.from("TestRun Test", classWithTestAnnotations);
    	testRun.addDependencyGroups(getDependencyGroups());
    	testRun.includeMethodsInClass(classWithTestAnnotations, getMethodsIncludedInClass());
    	addTestRun(testRun);
    }
    
    public static TestNGxmlStub getNew(TypeStubTest typeTest, InputParamsTM inputData) {
    	TestNGxmlStub test = new TestNGxmlStub(typeTest, inputData); 
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
    	case OnlyMethodGpo001includedInClass:
    		return (
    			Arrays.asList(methodGroupGaleriaProductoToInclude)
    		);
    	case OnlyMethodMic001includedInClass:
    		return (
    			Arrays.asList(methodGroupMiCuentaToInclude)
    		);
    	case WithTwoMethodsIncludedInClass:
    		return (
    			Arrays.asList(
    				methodGroupGaleriaProductoToInclude,
    				methodGroupMiCuentaToInclude)
    		);
    	case OnlyMethodThatDoesntExistInClass:
    		return (
    			Arrays.asList(methodThatDoesNotExistsInClass)
    	    );
    	default:
    	case WithoutMethodsIncludedInClass:
    		return (new ArrayList<String>());
    	}
    }
}
