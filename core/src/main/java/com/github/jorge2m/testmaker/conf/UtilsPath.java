package com.github.jorge2m.testmaker.conf;

import java.util.Optional;

public class UtilsPath {

	private UtilsPath() {}
	
	public static void setUserDir() {
		var dataPathPropertyOpt = getDataPathProperty();
		if (dataPathPropertyOpt.isPresent()) {
			System.setProperty("user.dir", dataPathPropertyOpt.get());
		}
	}
	
	private static Optional<String> getDataPathProperty() {
		var configLoader = new ConfigLoader();
		var property = configLoader.getProperty("testmaker.datapath"); 
		if (property==null || "".compareTo(property)==0) {
			return Optional.empty();
		}
		return Optional.of(property);
	}
	
}
