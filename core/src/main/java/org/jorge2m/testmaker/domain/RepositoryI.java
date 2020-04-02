package org.jorge2m.testmaker.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.jorge2m.testmaker.domain.suitetree.SuiteBean;
import org.jorge2m.testmaker.domain.suitetree.SuiteTM;
import org.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public interface RepositoryI {
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	public void storeAll(SuiteTM suite);
	public void storeSuite(SuiteTM suite);
	public SuiteBean getSuite(String idExecution) throws Exception;
	public List<SuiteBean> getListSuitesAfter(Date fechaDesde) throws Exception;
	public List<SuiteBean> getListSuites() throws Exception;
	public List<TestCaseBean> getListTestCases(String suiteExecId) throws Exception;
}
