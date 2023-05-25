package com.github.jorge2m.testmaker.service.genericchecks;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.conf.State.*;

public class GenericChecks extends PageObjTM {

	private final State jsErrors;
	private final State netTraffic;
	
	private GenericChecks(State jsErrors, State netTraffic) {
		this.jsErrors = jsErrors;
		this.netTraffic = netTraffic;
	}
	
	public void checks() {
		if (jsErrors!=null) {
			executeCheck(new CheckerJSerrors(jsErrors));
		}
		if (netTraffic!=null) {
			executeCheck(new CheckerNetTraffic(netTraffic));
		}
	}
	
	public void executeCheck(Checker checker) {
		ChecksTM checks = checker.check(driver);
		if (checks.size()>0) {
			createValidation(checks);
		}
	}
	
	@Validation
	private ChecksTM createValidation(ChecksTM checks) {
		return checks;
	}
	
	public static class BuilderCheck {
		
		protected State jsErrors;
		protected State netTraffic;
		
		public BuilderCheck jsErrors(State level) {
			jsErrors = level;
			return this;
		}
		public BuilderCheck jsErrors() {
			jsErrors = Info;
			return this;
		}

		public BuilderCheck netTraffic(State level) {
			netTraffic = level;
			return this;
		}
		public BuilderCheck netTraffic() {
			netTraffic = Warn;
			return this;
		}
		
		public GenericChecks build() {
			return new GenericChecks(jsErrors, netTraffic);
		}
		
	}
	
}
