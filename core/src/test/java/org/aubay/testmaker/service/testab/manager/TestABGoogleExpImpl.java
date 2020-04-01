package org.aubay.testmaker.service.testab.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aubay.testmaker.conf.Channel;
import org.aubay.testmaker.service.testab.TestABGoogleExp;
import org.aubay.testmaker.unittestdata.AppEcom;

public enum TestABGoogleExpImpl implements TestABGoogleExp {

	GaleriaDesktopReact(
		"KgtNo3S3SWCTsPzuWFIT-Q",
		"sub_8rhk5o_wc6i3wg92ap",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Galería sin React", //Este es el único caso posible en Outlet
			"V1-Galería con React"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop, AppEcom.votf)
	),
	
	//TODO es posible eliminarlo en cuanto se ejecute el planchado
	GaleriaDesktopReactPRESemanal(
		"LgtNo3S3SWCTsPzuWFIT-Q",
		"sub_8rhk5o_wc6i3wg92ap",
		Arrays.asList(0,1),
		Arrays.asList(
			"V0-Galería sin React", //Este es el único caso posible en Outlet
			"V1-Galería con React"),
		Arrays.asList(Channel.desktop),
		Arrays.asList(AppEcom.shop, AppEcom.votf)
	);
		
	private String valueCookieShop;
	private String valueCookieOutlet;
	private List<Integer> variantesInt;
	private List<Channel> channels;
	private List<AppEcom> apps;
	private TestABGoogleExpImpl(
			String valueCookieShop, String valueCookieOutlet, List<Integer> variantesInt, List<String> variantes, 
			List<Channel> channels, List<AppEcom> apps) {
		this.valueCookieShop = valueCookieShop;
		this.valueCookieOutlet = valueCookieOutlet;
		this.variantesInt = variantesInt;
		this.channels = channels;
		this.apps = apps;
	}
	
	@Override
	public List<Integer> getVariantes() {
		return this.variantesInt;
	}
	@Override
	public List<Channel> getChannels() {
		return this.channels;
	}
	@Override
	public List<Enum<?>> getApps() {
		List<Enum<?>> listReturn = new ArrayList<>();
		for (Enum<?> app : this.apps) {
			listReturn.add(app);
		}
		return listReturn;
	}
	@Override
	public String getValueCookie(Enum<?> app) {
		AppEcom appE = (AppEcom)app;
		switch (appE) {
		case outlet:
			return valueCookieOutlet;
		default:
		case shop:
			return valueCookieShop;
		}
	}
}
