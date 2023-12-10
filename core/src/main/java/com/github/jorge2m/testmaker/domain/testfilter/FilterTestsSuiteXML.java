package com.github.jorge2m.testmaker.domain.testfilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlTest;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.domain.util.TestNameUtils;

public class FilterTestsSuiteXML {
    
    private final DataFilterTCases dFilter;
    private final List<String> groupsToExclude;
    private final List<String> groupsToInclude;

    private FilterTestsSuiteXML(DataFilterTCases dFilter) {
    	this.dFilter = dFilter;
        GroupsChannelApps groupChannel = GroupsChannelApps.getNew(dFilter.getChannel(), dFilter.getApp());
        this.groupsToExclude = groupChannel.getGroupsExcluded();
        this.groupsToInclude = groupChannel.getGroupsIncluded();
    }
    
    public static FilterTestsSuiteXML getNew(DataFilterTCases dFilter) {
    	return (new FilterTestsSuiteXML(dFilter));
    }
    
    public DataFilterTCases dFilter() {
    	return this.dFilter;
    }
    
    public List<String> getGroupsToExclude() {
    	return this.groupsToExclude;
    }
    
    public List<String> getGroupsToInclude() {
    	return this.groupsToInclude;
    }
    
    public void filterTestCasesToExec(TestRunTM testRun) {
        try {
        	List<TestMethod> testCaseListToExec = getTestCasesToExecute(testRun);
    		includeTestCasesInTestRun(testCaseListToExec, testRun);
    		removeDependenciesWithGroupsNotExecuted(testRun);
        }
        catch (ClassNotFoundException e) {
        	testRun.getSuiteParent().getLogger().fatal("Problem filtering TestCases", e);
        }
    }
    
    public List<TestMethod> getInitialTestCaseCandidatesToExecute(XmlTest testRun) {
        List<TestMethod> listTestToReturn = new ArrayList<>();
        List<TestMethod> listTestsInXMLClasses = getTCasesAndFactorysInXMLClasses(testRun);
        for (var tmethod : listTestsInXMLClasses) {
        	if (testRun.getExcludedGroups()!=null && !testRun.getExcludedGroups().isEmpty()) {
	        	if (!groupsContainsAnyGroup(tmethod.getGroups(), testRun.getExcludedGroups())) {
	        		listTestToReturn.add(tmethod);
	        	}
        	} else {
        		if (testRun.getIncludedGroups()!=null && 
        			!testRun.getIncludedGroups().isEmpty() &&
		        	groupsContainsAnyGroup(tmethod.getGroups(), testRun.getIncludedGroups())) {
		        	listTestToReturn.add(tmethod);
        		}
        	}
        }
        
        return listTestToReturn;
    }
    
    public List<TestMethod> getTestCasesToExecute(XmlTest testRun) {
    	List<TestMethod> testsCaseCandidates = getInitialTestCaseCandidatesToExecute(testRun);
    	return getTestCasesFilteredWithParams(testsCaseCandidates);
    }
    
    private List<TestMethod> getTestCasesFilteredWithParams(List<TestMethod> listToFilter) {
    	if (!dFilter.isSomeFilterActive()) {
    		return listToFilter;
    	}
    	List<TestMethod> listTestsFilreredByGroups = getListTestsFilteredByGroups(listToFilter);
    	return (getListTestsFilteredByTestCases(listTestsFilreredByGroups));
    }
    
    private List<TestMethod> getListTestsFilteredByGroups(List<TestMethod> listToFilter) {
    	if (!dFilter.isActiveFilterByGroups()) {
    		return listToFilter;
    	}
    	
    	List<TestMethod> listTestsFiltered = new ArrayList<>();
    	for (TestMethod testMethod : listToFilter) {
    		List<String> groupsMethod = Arrays.asList(testMethod.getGroups());
    		if (someGroupInList(groupsMethod, dFilter.getGroupsFilter())) {
    			listTestsFiltered.add(testMethod);
    		}
    	}
    	
    	return listTestsFiltered;
    }
    
    private List<TestMethod> getListTestsFilteredByTestCases(List<TestMethod> listToFilter) {
    	if (!dFilter.isActiveFilterByTestCases()) {
    		return listToFilter;
    	}
    	
    	List<TestMethod> listTestsFiltered = new ArrayList<>();
    	for (TestMethod testMethod : listToFilter) {
    		String methodName = testMethod.getData().getTestCaseName();
    		if (TestNameUtils.isMethodNameInTestCaseList(methodName, dFilter.getTestCasesFilter())) {
    			listTestsFiltered.add(testMethod);
    		}
    	}
    	
    	return listTestsFiltered;
    }
     
    private boolean groupsContainsAnyGroup(String[] groupsTest, List<String> possibleGroups) {
    	if (possibleGroups==null) {
    		return true;
    	}
        for (int i=0; i<groupsTest.length; i++) {
            if (possibleGroups.contains(groupsTest[i])) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * @return obtendremos una lista de TestCases teniendo en cuenta el filtro que suponen: 
     *   - Los includes de métodos específicos existentes en la XML programática asociados a clases
     */
    protected List<TestMethod> getTCasesAndFactorysInXMLClasses(XmlTest testRun) {
        List<TestMethod> listOfAnnotationsOfTestCases = new ArrayList<>();
        try {
            for (var xmlClass : testRun.getClasses()) {
                var methodListToRun = getAllMethodsFilteredByInclude(xmlClass);
                for (var method : methodListToRun) {
                   List<Annotation> annotationsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
                   for (var annotation : annotationsList) {
                       if (annotation.annotationType()==org.testng.annotations.Test.class &&
                           ((Test)annotation).enabled()) {
                           listOfAnnotationsOfTestCases.add(new TestMethod((Test)annotation, method));
                           break;
                       }
                       if (annotation.annotationType()==org.testng.annotations.Factory.class &&
                           ((Factory)annotation).enabled()) {
                           listOfAnnotationsOfTestCases.add(new TestMethod((Factory)annotation, method));
                           break;
                       }
                    }
                }
            }
        }
        catch (ClassNotFoundException e) {
        	Log4jTM.getGlobal().fatal("Problem getting list of annotations of TestCases methods",  e);
        }
        
        return listOfAnnotationsOfTestCases;
    }    
    
    private void includeTestCasesInTestRun(List<TestMethod> testCasesToInclude, XmlTest testRun) 
    		throws ClassNotFoundException {
        for (var iterator = testRun.getClasses().iterator(); iterator.hasNext(); ) {
            var xmlClass = iterator.next();
            includeOnlyTestCasesInXmlClass(xmlClass, testCasesToInclude);
            if (xmlClass.getIncludedMethods().isEmpty()) {
                iterator.remove();
            }
        }
    }
    
    /**
     * Remove of the dependencies with source or destination group without any test for execution
     */
    private void removeDependenciesWithGroupsNotExecuted(TestRunTM testRun) {
        HashSet<String> groupsWithTestsToExec = getListOfGroupsWithTestCasesToExecute(testRun);
        XmlGroups xmlGroups = testRun.getGroups();
        if (xmlGroups!=null) {
            List<XmlDependencies> listXmlDep  = xmlGroups.getDependencies();
            if (!listXmlDep.isEmpty()) {
            	var itDependencyGroups = listXmlDep.get(0).getDependencies().entrySet().iterator();
                while (itDependencyGroups.hasNext()) {
                    Map.Entry<String, String> entryDependency = itDependencyGroups.next();
                    if (!groupsWithTestsToExec.contains(entryDependency.getKey()) ||
                        !groupsWithTestsToExec.contains(entryDependency.getValue()))
                        itDependencyGroups.remove();
                }
            }
        }
        
        testRun.setGroups(xmlGroups);
    }
    
    private void includeOnlyTestCasesInXmlClass(XmlClass xmlClass, List<TestMethod> testCasesToInclude) 
    throws ClassNotFoundException {
        xmlClass.getIncludedMethods().clear();
        List<XmlInclude> includeMethods = new ArrayList<>();
        List<Method> methodList = new ArrayList<>(Arrays.asList(Class.forName(xmlClass.getName()).getMethods()));
        for (var method : methodList) {
            List<Annotation> annotationsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
            for (var annotation : annotationsList) {
                if (((annotation.annotationType()==org.testng.annotations.Test.class && ((Test)annotation).enabled()) ||
                	 (annotation.annotationType()==org.testng.annotations.Factory.class && ((Factory)annotation).enabled())) &&
                	testMethodsContainsMethod(testCasesToInclude, method.getName())) {
                	includeMethods.add(new XmlInclude(method.getName()));
                	break;
                }
            }
        }              
        
        xmlClass.setIncludedMethods(includeMethods);
    }
    
    private HashSet<String> getListOfGroupsWithTestCasesToExecute(XmlTest testRun) {
        HashSet<String> listOfGropusWithTestCases = new HashSet<>();
        var listOfTestAnnotations = getTestCasesToExecute(testRun);
        for (var testMethod : listOfTestAnnotations) {
            listOfGropusWithTestCases.addAll(Arrays.asList(testMethod.getGroups()));
        }
        return listOfGropusWithTestCases;
    }
    
    private List<Method> getAllMethodsFilteredByInclude(XmlClass xmlClass) throws ClassNotFoundException {
        List<Method> methodListOfClass = new ArrayList<>(Arrays.asList(Class.forName(xmlClass.getName()).getMethods()));
        var includedMethods = xmlClass.getIncludedMethods();
        if (xmlClass.getIncludedMethods().isEmpty()) {
            return methodListOfClass;
        }

        //Filter by Include-XML
        List<Method> methodListFiltered = new ArrayList<>();
        for (var methodOfClass : methodListOfClass) {
            if (listOfIncludesContains(includedMethods, methodOfClass)) {
                methodListFiltered.add(methodOfClass);
            }
        }
        
        return methodListFiltered;
    }
    
    private boolean listOfIncludesContains(List<XmlInclude> listOfIncludes, Method method) {
        for (XmlInclude include : listOfIncludes) {
            if (include.getName().compareTo(method.getName())==0) {
                return true;
            }
        }
        return false;
    }
    
    private boolean someGroupInList(List<String> groupsMethod, List<String> listGroups) {
    	for (String groupMethod : groupsMethod) {
    		if (listGroups.contains(groupMethod)) {
    			return true;
    		}
    	}
    	return false;
    }

    private boolean testMethodsContainsMethod(List<TestMethod> testCasesToInclude, String methodName) {
        for (var testMethod : testCasesToInclude) {
            if (testMethod.getData().getTestCaseName().compareTo(methodName)==0 ||  
                methodName.indexOf(TestNameUtils.getCodeFromTestCase(testMethod.getData().getTestCaseName()))==0) {
                return true;
            }
        }
        return false;
    }
    
}
