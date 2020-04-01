package org.aubay.testmaker.domain;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.aubay.testmaker.domain.suitetree.SuiteTM;

public class SuitesExecuted {

	private static final List<SuiteTM> suitesExecuted = new CopyOnWriteArrayList<>();
	
	public static void add(SuiteTM suite) {
		suitesExecuted.add(suite); 
	}
	
	public static void remove(SuiteTM suite) {
		suitesExecuted.remove(suite);
	}
	
	public static List<SuiteTM> getSuitesExecuted() {
		return suitesExecuted;
	}
	
	public static SuiteTM getSuite(String idExecution) {
		for (SuiteTM suite : suitesExecuted) {
			if (idExecution.compareTo(suite.getIdExecution())==0) {
				return suite;
			}
		}
		return null;
	}
	
}