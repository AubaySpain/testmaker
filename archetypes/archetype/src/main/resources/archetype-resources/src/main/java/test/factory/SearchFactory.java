package ${package}.test.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Factory;

import ${package}.test.testcase.tests.Search;

public class SearchFactory {

	protected static final List<String> SEARCH_VALUES = 
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
	
	@Factory
	public Object[] createTests() {
		List<Object> listTests = new ArrayList<>();
		for (String searchValue : SEARCH_VALUES) {
			listTests.add(new Search(searchValue));
		}
		return listTests.toArray(new Object[listTests.size()]);
	}
}
