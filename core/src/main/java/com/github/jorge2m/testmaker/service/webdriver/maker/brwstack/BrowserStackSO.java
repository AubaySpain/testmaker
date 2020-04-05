package com.github.jorge2m.testmaker.service.webdriver.maker.brwstack;


public class BrowserStackSO {
    
    public enum PlatformMobilBS {
        Android("android"), iOS("iPhone");
        
        String valueAPI;
        private PlatformMobilBS(String valueAPI) {
            this.valueAPI = valueAPI;
        }
        
        public String getValueaAPI() {
            return this.valueAPI;
        }
    }
    
    public enum PlatformDesktopBS {
        Windows("Windows"), OSX("OS X");
        
        String valueAPI;
        private PlatformDesktopBS(String valueAPI) {
            this.valueAPI = valueAPI;
        }
        
        public String getValueaAPI() {
            return this.valueAPI;
        }        
    }
}
