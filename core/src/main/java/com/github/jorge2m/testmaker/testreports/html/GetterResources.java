package com.github.jorge2m.testmaker.testreports.html;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface GetterResources {

	public Directory getDataFromPath() throws Exception;
	
	public static GetterResources makeGetter(String dirOriginFromResources) {
    	URL directoryRelativeToResources = GetterResources.class.getResource("/" + dirOriginFromResources);
		if (directoryRelativeToResources.getProtocol().equals("jar")) {
			return GetterResourcesFromJar.getNew(dirOriginFromResources);
		}
		return GetterResourcesFromDirectory.getNew(dirOriginFromResources);
	}
	
	static String getNameDirectory(String directoryPath) {
		Pattern pattern = Pattern.compile(".*/(.*)/");
    	Matcher matcher = pattern.matcher(directoryPath);
    	if (matcher.matches()) {
    		return matcher.group(1);
    	} else {
    		pattern = Pattern.compile("(.*)/");
        	matcher = pattern.matcher(directoryPath);
        	if (matcher.matches()) {
        		return matcher.group(1);
        	}
    	}
    	return directoryPath;
	}
	
	default String addFileSeparatorIfNotEndsWith(String directoryPath) {
		if (!directoryPath.endsWith("/")) {
			return (directoryPath + "/");
		} 
		return directoryPath;
	}
	
    static class Directory {
    	public final String pathFromResources;
    	public final String nameDirectory;
    	public List<String> listFiles = new ArrayList<>();
    	public List<Directory> listDirectories = new ArrayList<>();
    	
    	public Directory(String pathFromResources) {
    		this.pathFromResources = pathFromResources;
    		this.nameDirectory = getNameDirectory(pathFromResources);
    	}
    	
    	public void addFile(String file) {
    		if (file!=null) {
    			listFiles.add(file);
    		}
    	}
    	public void addDirectory(Directory directory) {
    		if (directory!=null) {
    			listDirectories.add(directory);
    		}
    	}
    }
}
