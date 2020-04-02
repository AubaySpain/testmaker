package org.jorge2m.testmaker.boundary.aspects;

import static org.junit.jupiter.api.Assertions.*;

import org.jorge2m.testmaker.boundary.aspects.MatcherWithMethodParams.TagData;
import org.junit.Test;

public class MatcherWithMethodParamsTest {

	@Test
	public void testOnlyParam() {
		TagData tagData = new TagData("idArticulo");
		assertTrue(tagData.nameParameter.compareTo("idArticulo")==0);
		assertTrue(tagData.methodWithoutParams1.compareTo("")==0);
		assertTrue(tagData.methodWithoutParams2.compareTo("")==0);
	}
	@Test
	public void testWith1LevelOfMethods() {
		TagData tagData = new TagData("idArticulo.getLevel1()");
		assertTrue(tagData.nameParameter.compareTo("idArticulo")==0);
		assertTrue(tagData.methodWithoutParams1.compareTo("getLevel1")==0);
		assertTrue(tagData.methodWithoutParams2.compareTo("")==0);
		
	}
	@Test
	public void testWith2LevelOfMethods() {
		TagData tagData = new TagData("idArticulo.getLevel1().getLevel2()");
		assertTrue(tagData.nameParameter.compareTo("idArticulo")==0);
		assertTrue(tagData.methodWithoutParams1.compareTo("getLevel1")==0);
		assertTrue(tagData.methodWithoutParams2.compareTo("getLevel2")==0);
	}
}
