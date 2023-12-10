package com.github.jorge2m.testmaker.service.genericchecks;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.utils.WebUtils;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;

public class CheckerJSerrors implements Checker {

	private final State level;
	
	public CheckerJSerrors(State level) {
		this.level = level;
	}
	
	public ChecksTM check(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		
		//Nota: No funciona con GeckoDriver porque no están implementados los servicios al no formar parte del protocolo W3C https://github.com/w3c/webdriver/issues/406
		if (PageObjTM.getTypeDriver(driver)!=EmbeddedDriver.firefox &&
			PageObjTM.getTypeDriver(driver)!=EmbeddedDriver.firefoxhless) {
			int maxErrors = 1;
			ResultadoErrores resultadoLogs = WebUtils.getLogErrors(Level.WARNING, driver, maxErrors);
			String descripValidac = "No hay errores JavaScript";
			boolean resultadoOK = resultadoLogs.getResultado() == ResultadoErrores.Resultado.OK;
			
			//Sólo mostraremos warning en caso que alguno no se haya mostrado ya un máximo de veces durante el test
			checks.add(
				Check.make(
				    descripValidac,
				    resultadoOK || (resultadoLogs.getResultado()==ResultadoErrores.Resultado.MAX_ERRORES), level)
				.info(getInfoError(resultadoLogs))
				.store(StoreType.NONE)
				.build());
		}
		
		return checks;
	}
	
	private String getInfoError(ResultadoErrores resultadoLogs) {
		boolean resultadoOK = resultadoLogs.getResultado() == ResultadoErrores.Resultado.OK;
		if (!resultadoOK && resultadoLogs.getResultado()!=ResultadoErrores.Resultado.MAX_ERRORES) {
			String info = "<ul>";
			for (String error : resultadoLogs.getlistaLogError()) {
				info+="<li>" +  error + "</li>";
			}
			info+="</ul>";
			return info.replace("<br>", "");
		}
		return "";
	}
	
}
