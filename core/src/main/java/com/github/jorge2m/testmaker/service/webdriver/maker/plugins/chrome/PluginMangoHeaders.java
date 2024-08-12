package com.github.jorge2m.testmaker.service.webdriver.maker.plugins.chrome;

public class PluginMangoHeaders extends PluginChrome {

    private static final String FILE_NAME = "mango_headers_1_6_0_0.crx";
    
    @Override
    protected String getCrxFileName() {
        return FILE_NAME;
    }
    
}