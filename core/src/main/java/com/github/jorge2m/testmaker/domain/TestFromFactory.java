package com.github.jorge2m.testmaker.domain;

public interface TestFromFactory {

	String getIdTestInFactory();
	
	public default String getIdTest() {
		String id = getIdTestInFactory();
		if (id==null) {
			return "";
		}
		return id;
	}
	
	
}
