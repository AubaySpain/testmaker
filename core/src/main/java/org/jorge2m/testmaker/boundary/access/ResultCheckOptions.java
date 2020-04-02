package org.jorge2m.testmaker.boundary.access;

import java.util.List;

public class ResultCheckOptions {

	private boolean ok;
	private List<MessageError> listMessagesError;
	
	public ResultCheckOptions() {}
	
	private ResultCheckOptions(boolean ok, List<MessageError> listMessagesError) {
		this.ok = ok;
		this.listMessagesError = listMessagesError;
	}
	
	public static ResultCheckOptions from(boolean ok, List<MessageError> listMessagesError) {
		return new ResultCheckOptions(ok, listMessagesError);
	}
	
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public List<MessageError> getListMessagesError() {
		return listMessagesError;
	}
	public void setListMessagesError(List<MessageError> listMessagesError) {
		this.listMessagesError = listMessagesError;
	}
}
