package com.github.jorge2m.testmaker.utils.filter;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlTest;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethod;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.github.jorge2m.testmaker.unittestdata.AppEcom;
import com.github.jorge2m.testmaker.unittestdata.Suites;
import com.github.jorge2m.testmaker.utils.filter.resources.TestNGxmlStub;
import com.github.jorge2m.testmaker.utils.filter.resources.TestNGxmlStub.TypeStubTest;

public class TestFilterTNGxmlTRun {

	List<String> groupsVoid = null;
    public enum GroupDep {from, to}
    
    @Test
    public void getListOfTestMethodsFilteredByIncludesAndDesktopShop() {
        InputParamsTM inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithoutMethodsIncludedInClass, inputData);
        
        //Code to test
        testStub.getTestRun();
        
        assertEquals("The number of tests is 3", 3, testStub.getListTests().size());
        ArrayList<String> testMethodsNames = new ArrayList<>();
        testMethodsNames.add(testStub.getListTests().get(0).getData().getTestCaseName());
        testMethodsNames.add(testStub.getListTests().get(1).getData().getTestCaseName());
        testMethodsNames.add(testStub.getListTests().get(2).getData().getTestCaseName());
        String method1 = "MIC001_Opciones_Mi_Cuenta";
        String method2 = "GPO001_Galeria_Camisas";
        String method3 = "BOR001_AddBolsaFromGaleria_NoReg";
        assertTrue("The method " + method1 + " is present", testMethodsNames.contains(method1));
        assertTrue("The method " + method2 + " is present", testMethodsNames.contains(method2));
        assertTrue("The method " + method3 + " is present", testMethodsNames.contains(method3));
    }

    @Test
    public void getListOfTestMethodsFilteredByIncludes() {
        InputParamsTM inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.OnlyMethodGpo001includedInClass, inputData);
        
        //Code to test
        testStub.getTestRun();
        
        String descriptionExpected = "[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color";
        assertEquals("The number of tests is 1", 1, testStub.getListTests().size());
        
        TestMethod methodIncluded = testStub.getListTests().get(0);
        String methodNameExpected = testStub.getMethodsIncludedInClass().get(0);
        assertEquals("The description is " + descriptionExpected, descriptionExpected, methodIncluded.getDescription());
        assertEquals("The method is " + methodNameExpected, methodNameExpected, methodIncluded.getData().getTestCaseName());
    }
    
    @Test
    public void getListOfTestMethodsNotFilteredByIncludes() {
        InputParamsTM inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithoutMethodsIncludedInClass, inputData);
        
        //Code to test
        testStub.getTestRun();
        
        assertEquals("The number of tests is 3", 3, testStub.getListTests().size());
    }
    
    @Test
    public void filterListOfTestCasesVoidWithInclude() {
        InputParamsTM inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.OnlyMethodMic001includedInClass, inputData);
        
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        //The xmlTest is not changed 
        assertEquals("The number of classes is 1", 1, testRun.getXmlClasses().size());
        assertEquals("The number of dependencies-group is 0", 0, testRun.getXmlDependencyGroups().size());
        assertEquals("The number of methods included is 1", 1, getIncludedMethodsCount(testRun.getXmlClasses()));
    }
    
    @Test
    public void filterListOfTestCasesVoidWithoutInclude() {
        InputParamsTM inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithoutMethodsIncludedInClass, inputData);
        
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        assertEquals("The number of dependencies-group is " + 1, 1, testRun.getXmlDependencyGroups().size());
        assertEquals("The number of methods is " + TestNGxmlStub.numberTestsCasesDesktopShop, TestNGxmlStub.numberTestsCasesDesktopShop, getIncludedMethodsCount(testRun.getXmlClasses()));
    }
    
    @Test
    public void filterIncludeNewTestCase() {
        InputParamsTM inputData = getInputDataBasic();
        String testExpectedToBeIncluded = TestNGxmlStub.methodGroupGaleriaProductoToInclude;
        inputData.setListTestCaseItems(Arrays.asList(testExpectedToBeIncluded));
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithoutMethodsIncludedInClass, inputData);
        
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        String textExpectedToNotBeIncluded = TestNGxmlStub.methodGroupMiCuentaToInclude;
        assertTrue("The new method " + testExpectedToBeIncluded + " is included", classIncludesMethod(testRun.getXmlClasses().get(0), testExpectedToBeIncluded));
        assertTrue("The old method " + textExpectedToNotBeIncluded + " disappear", !classIncludesMethod(testRun.getXmlClasses().get(0), textExpectedToNotBeIncluded));
        assertEquals("No remains dependencies-group", 0, testRun.getXmlDependencyGroups().size());
    }
    
    @Test
    public void filterIncludeTwoTestCaseByName_1groupRemains() {
        InputParamsTM inputData = getInputDataBasic();
        inputData.setListTestCaseItems(
        	Arrays.asList(
        		TestNGxmlStub.methodGroupGaleriaProductoToInclude, 
        		TestNGxmlStub.methodGroupMiCuentaToInclude));
        
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithTwoMethodsIncludedInClass, inputData);
        
        checkAfterIncludeTwoMethods(testStub);
    }
    
    @Test
    public void filterIncludeTwoTestCasesByCode_1groupRemains() {
        InputParamsTM inputData = getInputDataBasic();
        inputData.setListTestCaseItems(
        	Arrays.asList(
        		TestNGxmlStub.methodGroupGaleriaProductoToInclude.substring(0,6), 
        		TestNGxmlStub.methodGroupMiCuentaToInclude.substring(0,6)));
        
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithTwoMethodsIncludedInClass, inputData);
        
        checkAfterIncludeTwoMethods(testStub);
    }
    
    private void checkAfterIncludeTwoMethods(TestNGxmlStub testStub) {
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        assertEquals("Remains only 1 dependency-group ", 1, testRun.getXmlDependencyGroups().size());
        assertTrue("Is present the group \"GaleriaProducto\"", isGroupInDependencies(testRun, "GaleriaProducto", GroupDep.to));
        assertTrue("Is present the dependency \"Micuenta\"", isGroupInDependencies(testRun, "Micuenta", GroupDep.from));
        assertTrue("The new method " + TestNGxmlStub.methodGroupGaleriaProductoToInclude + " is included", classIncludesMethod(testRun.getXmlClasses().get(0), TestNGxmlStub.methodGroupGaleriaProductoToInclude));
        assertTrue("The old method " + TestNGxmlStub.methodGroupMiCuentaToInclude + " remains included", classIncludesMethod(testRun.getXmlClasses().get(0), TestNGxmlStub.methodGroupMiCuentaToInclude));        
    }
    
    @Test
    public void includeTestCaseThatDoesnotExists() {
        InputParamsTM inputData = getInputDataBasic();
        inputData.setListTestCaseItems(Arrays.asList(TestNGxmlStub.methodThatDoesNotExistsInClass));
        
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.OnlyMethodThatDoesntExistInClass, inputData);
        
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        assertEquals("No classes remains ", 0, testRun.getClasses().size());
    }
    
    private InputParamsTM getInputDataBasic() {
    	InputParamsTM inputParams = new InputParamsBasic(Suites.class, AppEcom.class);
    	inputParams.setChannel(Channel.desktop);
    	inputParams.setSuite(Suites.SuiteForUnitTest);
    	inputParams.setApp(AppEcom.shop);
    	inputParams.setUrlBase("https://shop.mango.com/preHome.faces");
    	inputParams.setWebDriverType(WebDriverType.chrome);
    	return inputParams;
    }
    
    private int getIncludedMethodsCount(List<XmlClass> listXmlClasses) {
        int count = 0;
        for (XmlClass xmlClass : listXmlClasses) {
            count+=xmlClass.getIncludedMethods().size();
        }
        return count;
    }
    
    private boolean classIncludesMethod(XmlClass xmlClass, String methodName) {
        for (XmlInclude methodIncluded : xmlClass.getIncludedMethods()) {
            if (methodIncluded.getName().compareTo(methodName)==0)
                return true;
        }
        
        return false;
    }
    
    private boolean isGroupInDependencies(XmlTest xmlTest, String group, GroupDep typeGroupDep) {
        Set<Entry<String, String>> xmlDependencyGroups = xmlTest.getXmlDependencyGroups().entrySet();
        for (Entry<String, String> dependency : xmlDependencyGroups) {
            switch (typeGroupDep) {
            case from:
                if (dependency.getValue().compareTo(group)==0)
                    return true;
                break;
            case to:
                if (dependency.getKey().compareTo(group)==0)
                    return true;
                break;
            default:
                break;
            }
        }
        
        return false;
    }
}
