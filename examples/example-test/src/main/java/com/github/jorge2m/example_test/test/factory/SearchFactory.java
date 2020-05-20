package com.github.jorge2m.example_test.test.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Factory;

import com.github.jorge2m.example_test.test.testcase.script.BuscarPatternPageObject;

public class SearchFactory {

	public static final List<String> searchValues = 
		Arrays.asList( 
			"Zelda Breath of The Wild",
			"Enter the gungeon",
			"Xenoblade Chronicles",
			"Undertale",
			"Salamander Game",
			"XCOM Enemy Unknown",
			"Super Mario 64",
			"Zelda ocarina of time",
			"Baldur's Gate 2",
			"Head over Hells Game");
	
	public SearchFactory() {}
	
	@Factory
	public Object[] FAC001_createTests() {
		List<Object> listTests = new ArrayList<>();
		for (String searchValue : searchValues) {
			listTests.add(new BuscarPatternPageObject(searchValue));
		}
		return listTests.toArray(new Object[listTests.size()]);
	}
}
