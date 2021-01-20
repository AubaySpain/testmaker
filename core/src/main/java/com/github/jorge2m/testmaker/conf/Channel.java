package com.github.jorge2m.testmaker.conf;

public enum Channel { 
	desktop(true, false), 
	mobile(false, true), 
	tablet(false, true); 
	
	private boolean isPC;
	private boolean isDevice;
	private Channel(boolean isPC, boolean isDevice) {
		this.isPC = isPC;
		this.isDevice = isDevice;
	}
	
	public boolean isPC() {
		return isPC;
	}
	public boolean isDevice() {
		return isDevice;
	}
}
