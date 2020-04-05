package com.github.jorge2m.testmaker.testreports.html;

import java.io.File;
import java.net.URL;

class GetterResourcesFromDirectory implements GetterResources { 

	private final String dirOriginFromResources;
	private final URL urlDirToExtract;
	
	private GetterResourcesFromDirectory(String dirOriginFromResources) {
		this.dirOriginFromResources = addFileSeparatorIfNotEndsWith(dirOriginFromResources);
		this.urlDirToExtract = getClass().getResource("/" + this.dirOriginFromResources);
	}
	
	static GetterResourcesFromDirectory getNew(String dirOriginFromResources) {
		return new GetterResourcesFromDirectory(dirOriginFromResources);
	}
	
	@Override
    public Directory getDataFromPath() throws Exception {
		if (urlDirToExtract.getProtocol().equals("jar")) {
			throw new IllegalArgumentException("dirUrlDirectory must not be in a JAR File");
		}
		return getDataFromPath(dirOriginFromResources);
	}
	
    private Directory getDataFromPath(String dirOriginFromResources) throws Exception {
    	Directory directoryContent = new Directory(dirOriginFromResources);
    	URL url = getClass().getResource("/" + dirOriginFromResources);
    	if (url == null) {
    	     return null;
    	} else {
    	    File dir = new File(url.toURI());
    	    for (File nextFile : dir.listFiles()) {
    	    	if (nextFile.isDirectory()) {
    	    		String newDirectory = dirOriginFromResources + nextFile.getName() + "/";
    	    		directoryContent.addDirectory(getDataFromPath(newDirectory));
    	    	} else {
    	    		directoryContent.addFile(nextFile.getName());
    	    	}
    	    }
    	}
    	
    	return directoryContent;
    }
}
