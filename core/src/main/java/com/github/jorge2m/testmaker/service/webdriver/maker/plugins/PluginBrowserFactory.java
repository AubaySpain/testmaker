package com.github.jorge2m.testmaker.service.webdriver.maker.plugins; 

import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginHTML5AutoplayBlocker;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginSgreenRecorder;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome.TypePluginChrome;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.firefox.PluginFirefox;
import com.github.jorge2m.testmaker.service.webdriver.maker.plugins.firefox.PluginFirefox.typePluginFirefox;


public class PluginBrowserFactory {
    
    public static PluginChrome makePluginChrome(TypePluginChrome typePlugin) {
        switch (typePlugin) {
        case HTML5_AUTOPLAY_BLOCKER:
            return new PluginHTML5AutoplayBlocker();
        case MOVAVI_SCREEN_RECORDER:
        	return PluginSgreenRecorder.makePlugin();
        default:
        	return null;
        }
    }
    
    @SuppressWarnings("unused")
	public static PluginFirefox makePluginFirefox(typePluginFirefox typePlugin) {
        PluginFirefox extensionMaked = null; 
        //switch (typePlugin) {
        //case HARExportTrigger:
            //extensionMaked = new PluginHARExportTrigger();
        //    break;
        //default:
        //    break;
        //}
        
        return extensionMaked;
    }    
}
