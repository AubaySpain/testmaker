package ${package}.test.testcase.tests;

import java.io.Serializable;

import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.domain.TestFromFactory;

public class Search implements TestFromFactory, Serializable {

	private static final long serialVersionUID = -3932978752450813757L;
	
	private String textToSearch = "Mario Maker 2";
	
	public Search() {}

	//From @Factory
	public Search(String textToSearch) {
		this.textToSearch = textToSearch;
	} 
	
	@Override
	public String getIdTestInFactory() {
		return textToSearch;
	}
	
	@Test (
		testName="GOO001",
		groups={"Search", "Canal:desktop,mobile_App:google"},
		description="Se comprueba que la búsqueda en Google devuelve más resultados que la búsqueda en Bing")
	public void search() throws Exception {
		new GOO001(textToSearch).execute();
	}

}
