package com.github.jorge2m.testmaker.domain.suitetree;

import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.conf.SendType;
import com.github.jorge2m.testmaker.conf.State;


public class Check {
    
	private String description = "";
    private State levelResult = State.Undefined;
    private boolean overcomed = false;
	private StoreType store = StoreType.Evidences;
	private SendType send = SendType.None;

    public Check() {}
    
    public static Check of(
    		String description, boolean overcomed, State levelResult, StoreType storeType, SendType sendType) {
    	Check resultValidation = of(levelResult);
    	resultValidation.setDescription(description);
    	resultValidation.setOvercomed(overcomed);
    	resultValidation.setStore(storeType);
    	resultValidation.setSend(sendType);
    	return resultValidation;
    }
    
    public static Check of(State levelResult) {
    	Check resultValidation = new Check();
    	resultValidation.setLevelResult(levelResult);
    	return resultValidation;
    }

    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public State getLevelResult() {
		return levelResult;
	}
	public State getStateResult() {
		if (isOvercomed()) {
			return State.Ok;
		}
		return getLevelResult();
	}
	public void setLevelResult(State levelError) {
		this.levelResult = levelError;
	}
    public boolean isOvercomed() {
		return overcomed;
	}
	public void setOvercomed(boolean overcomed) {
		this.overcomed = overcomed;
	}
    public StoreType getStore() {
		return store;
	}
	public void setStore(StoreType store) {
		this.store = store;
	}
    public SendType getSend() {
		return send;
	}
	public void setSend(SendType send) {
		this.send = send;
	}

}
