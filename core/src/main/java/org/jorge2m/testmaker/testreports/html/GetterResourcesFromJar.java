package org.jorge2m.testmaker.testreports.html;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class GetterResourcesFromJar implements GetterResources {

	private final String dirOriginFromResources;
	private final URL urlDirToExtract;
	private enum TypeSearchInDirectory {InFirstLevel, InAnyLevel};
	
	private GetterResourcesFromJar(String dirOriginFromResources) {
		this.dirOriginFromResources = addFileSeparatorIfNotEndsWith(dirOriginFromResources);
		this.urlDirToExtract = getClass().getResource("/" + this.dirOriginFromResources);
	}
	
	static GetterResourcesFromJar getNew(String dirOriginFromResources) {
		return new GetterResourcesFromJar(dirOriginFromResources);
	}
	
	@Override
    public Directory getDataFromPath() throws Exception {
		if (!urlDirToExtract.getProtocol().equals("jar")) {
			throw new IllegalArgumentException("dirUrlDirectory must not be in a JAR File");
		}
		
		List<JarEntry> listJarEntrys = getListJarEntrysInDirectory(); 
		return (getDataDirectory(listJarEntrys, dirOriginFromResources));
	}
	
	private List<JarEntry> getListJarEntrysInDirectory() throws Exception {
		List<JarEntry> listEntryReturn = new ArrayList<>();
		String jarPath = urlDirToExtract.getPath().substring(5, urlDirToExtract.getPath().indexOf("!"));
		JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
		Enumeration<JarEntry> entries = jar.entries(); 
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement(); 
			if (jarEntry.getName().indexOf(dirOriginFromResources)==0) {
				listEntryReturn.add(jarEntry);
			}
		}
		jar.close();
		
		return listEntryReturn;
	}
	
	Directory getDataDirectory(List<JarEntry> jarEntrys, String directoryPath) {
		List<JarEntry> listEntrysDirectory = getJarEntrysFromDirectory(jarEntrys, directoryPath);
		if (!listEntrysDirectory.isEmpty()) {
			Directory directoryContent = new Directory(directoryPath);
			for (JarEntry jarEntryDirectory : listEntrysDirectory) {
				if (isEntryInDirectory(jarEntryDirectory, directoryPath, TypeSearchInDirectory.InFirstLevel)) {
					if (jarEntryDirectory.isDirectory()) {
						directoryContent.addDirectory(getDataDirectory(listEntrysDirectory, jarEntryDirectory.getName()));
					} else {
						directoryContent.addFile(getNameFile(jarEntryDirectory)); 
					}
				}
			}
			return directoryContent;
		}

		return null;
	}
	
	private String getNameFile(JarEntry jarEntry) {
		String namePath = jarEntry.getName();
		int indexLastSlash = namePath.lastIndexOf("/");
		if (indexLastSlash >= 0) {
			if (jarEntry.isDirectory()) {
				return GetterResources.getNameDirectory(jarEntry.getName());
			} else {
				return (namePath.substring(indexLastSlash + 1));
			}
		} else {
			return namePath;
		}
	}
	
	private List<JarEntry> getJarEntrysFromDirectory(List<JarEntry> listEntrys, String directoryFromResources) {
		List<JarEntry> listToReturn = new ArrayList<>();
		for (JarEntry jarEntry : listEntrys) {
			if (isEntryInDirectory(jarEntry, directoryFromResources, TypeSearchInDirectory.InAnyLevel)) {
				listToReturn.add(jarEntry);
			}
		}
		
		return listToReturn;
	}
	
	private boolean isEntryInDirectory(JarEntry jarEntry, String directory, TypeSearchInDirectory typeSearch) {
		boolean isEntryInAnyLevel = (
					jarEntry.getName().indexOf(directory)==0 && 
					jarEntry.getName().compareTo(directory)!=0);
		
		switch (typeSearch) {
		case InAnyLevel:
			return isEntryInAnyLevel;
		case InFirstLevel:
			if (isEntryInAnyLevel) {
				String pathRelative = jarEntry.getName().replace(directory, "");
				if (jarEntry.isDirectory()) {
					return (pathRelative.indexOf("/")+1==pathRelative.length());
				} else {
					return (pathRelative.indexOf("/") < 0);
				}
			}
			return false;
		default:
			return false;
		}
	}
}
