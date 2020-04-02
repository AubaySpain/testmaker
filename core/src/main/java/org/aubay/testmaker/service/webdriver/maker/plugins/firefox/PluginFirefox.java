package org.jorge2m.testmaker.service.webdriver.maker.plugins.firefox;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxProfile;


public abstract class PluginFirefox {
	//Example for add a plugin to Firefox
    //ArrayList<PluginFirefox.typePluginFirefox> listPlugins = new ArrayList<>();
    //listPlugins.add(PluginFirefox.typePluginFirefox.HARExportTrigger);
    //addPluginsFirefox(fp, listPlugins);
	
    public String folderInResources = "pluginsBrowser";
    public static enum typePluginFirefox {  }
    
    public void addPluginToFirefox(FirefoxProfile firefoxProfile, String fileNamePlugin) throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + this.folderInResources + "/" + fileNamePlugin)) {
            File tmpFile = File.createTempFile("pluginFirefox", "temp.xpi");
            tmpFile.deleteOnExit();
            FileUtils.copyInputStreamToFile(inputStream, tmpFile);
            firefoxProfile.addExtension(tmpFile);
        }
    }
    
    public abstract void addPluginToFirefox(FirefoxProfile firefoxProfile) throws Exception;
}
