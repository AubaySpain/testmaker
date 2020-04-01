package org.aubay.testmaker.testreports.html;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

import org.aubay.testmaker.testreports.html.GetterResourcesFromJar;
import org.aubay.testmaker.testreports.html.GetterResources.Directory;
import org.junit.Test;
import org.mockito.Mockito;

public class GetterResourcesFromJarTest {

	@Test
	public void testGetDataDirectory() {
		//Given
		List<JarEntry> jarEntrys = new ArrayList<>();
		jarEntrys.add(getMock("static/", true));
		jarEntrys.add(getMock("static/css/", true));
		jarEntrys.add(getMock("static/css/Report.css", false));
		jarEntrys.add(getMock("static/images/", true));
		jarEntrys.add(getMock("static/images/blank.gif", false));
		jarEntrys.add(getMock("static/images/compare_report.gif", false));
		jarEntrys.add(getMock("static/js/", true));
		jarEntrys.add(getMock("static/js/dojo/", true));
		jarEntrys.add(getMock("static/js/dojo/__package__.js", false));
		jarEntrys.add(getMock("static/js/dojo/browser.js", false));
		
		//When
		String dirOrigin = "static/js/";
		GetterResourcesFromJar getterResources = GetterResourcesFromJar.getNew(dirOrigin);
		Directory directory = getterResources.getDataDirectory(jarEntrys, dirOrigin);
		
		//Then
		assertEquals("js", directory.nameDirectory);
		assertEquals("static/js/", directory.pathFromResources);
		assertTrue(directory.listDirectories.size()==1);
		assertTrue(directory.listFiles.size()==0);
		Directory dirDojo = directory.listDirectories.get(0);
		assertEquals("dojo", dirDojo.nameDirectory);
		assertEquals("static/js/dojo/", dirDojo.pathFromResources);
		assertEquals("__package__.js", dirDojo.listFiles.get(0));
		assertEquals("browser.js", dirDojo.listFiles.get(1));
	}
	
	private JarEntry getMock(String path, boolean isDirectory) {
		JarEntry jarEntry = Mockito.mock(JarEntry.class);
		when(jarEntry.getName()).thenReturn(path);
		when(jarEntry.isDirectory()).thenReturn(isDirectory);
		return jarEntry;
	}
}
