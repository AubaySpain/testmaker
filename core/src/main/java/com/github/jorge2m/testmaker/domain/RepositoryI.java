package com.github.jorge2m.testmaker.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public interface RepositoryI {
	
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	public void store(SuiteBean suite, StoreUntil until);
	public void delete(String suiteIdExec);
	public void storeAlert(Check check, ChecksTM checksParent);
	public SuiteBean getSuite(String idExecution) throws Exception;
	public List<SuiteBean> getListSuitesBetween(Date fechaDesde, Date fechaHasta) throws Exception;
	public List<SuiteBean> getListSuites() throws Exception;
	public List<TestCaseBean> getListTestCases(String suiteExecId) throws Exception;

	public enum StoreUntil {
		nostore(false, false, false, false, false), 
		suite(true, false, false, false, false), 
		testrun(true, true, false, false, false), 
		testcase(true, true, true, false, false), 
		step(true, true, true, true, false), 
		validation(true, true, true, true, true);
		
		boolean storeSuite;
		boolean storeTestrun;
		boolean storeTestcase;
		boolean storeStep;
		boolean storeValidation;
		private StoreUntil(
				boolean storeSuite, boolean storeTestrun, boolean storeTestcase, 
				boolean storeStep, boolean storeValidation) {
			this.storeSuite = storeSuite;
			this.storeTestrun = storeTestrun;
			this.storeTestcase = storeTestcase;
			this.storeStep = storeStep;
			this.storeValidation = storeValidation;
		}
		public boolean storeSuite() {
			return storeSuite;
		}
		public boolean storeTestrun() {
			return storeTestrun;
		}
		public boolean storeTestcase() {
			return storeTestcase;
		}
		public boolean storeStep() {
			return storeStep;
		}
		public boolean storeValidation() {
			return storeValidation;
		}
	}
}
