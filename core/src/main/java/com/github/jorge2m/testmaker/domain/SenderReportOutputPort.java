package com.github.jorge2m.testmaker.domain;

import java.util.List;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;

public interface SenderReportOutputPort {

	public boolean send(SuiteBean suite);
	public boolean send(List<SuiteBean> suite);
	public boolean send(List<SuiteBean> suite, List<SuiteBean> suiteOld);
	
}
