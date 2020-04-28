package com.github.jorge2m.testmaker.testreports.html;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.testreports.html.GetterResources.Directory;

public class ResourcesExtractor {
	
	private ResourcesExtractor() {}
	
	public static ResourcesExtractor getNew() {
		return new ResourcesExtractor();
	}
	
	public void copyDirectoryResources(String dirOriginFromResources, String dirDestination) {
		GetterResources getterResources = GetterResources.makeGetter(dirOriginFromResources);
		try {
			Directory directoryData = getterResources.getDataFromPath();
			if (directoryData!=null) {
				copyDirectoryResources(directoryData, dirDestination);
			} else {
				Log4jTM.getLogger().warn("Not found data from path in resources " + dirOriginFromResources);
			}
		} 
		catch (Exception e) {
			Log4jTM.getLogger().error("Problem in copy of static directory for HTML Report", e);
		}
	}

	private void copyDirectoryResources(Directory dirOriginFromResources, String dirDestination) 
	throws Exception {
		createDirectoryIfNotExists(dirDestination);
		for (String fileName : dirOriginFromResources.listFiles) {
			String pathFileNameOrigin = dirOriginFromResources.pathFromResources + fileName;
			String pathFileNameDestination = dirDestination + "/" + fileName;
			copyFileFromResources(pathFileNameOrigin, pathFileNameDestination);
		}
		
		for (Directory directory : dirOriginFromResources.listDirectories) {
			copyDirectoryResources(directory, dirDestination + "/" + directory.nameDirectory);
		}
	}
	
    private void createDirectoryIfNotExists(String directoryFromClasspathNormalized) {
    	File newDirectory = new File(directoryFromClasspathNormalized);
    	if (!newDirectory.exists()) {
    		newDirectory.mkdirs();
    	}
    }
    
    private void copyFileFromResources(
    		String pathFileNameOriginFromResources, String pathFileNameDestination) throws IOException {
    	InputStream inputStreamResources = getClass().getResourceAsStream("/" + pathFileNameOriginFromResources);
    	File fileDestination = new File(pathFileNameDestination);
    	FileUtils.copyInputStreamToFile(inputStreamResources, fileDestination);
    }
}
