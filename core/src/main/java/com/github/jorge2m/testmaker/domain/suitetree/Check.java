package com.github.jorge2m.testmaker.domain.suitetree;

import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.conf.SendType;
import com.github.jorge2m.testmaker.conf.State;


public class Check {
    
	private String description = "";
    private State levelResult = State.Undefined;
    private boolean overcomed = false;
    private String code = "";
    private String info = "";
	private StoreType store = StoreType.Evidences;
	private SendType send = SendType.None;

    public Check() {}
    
    public static Check of(
    		String description, boolean overcomed, State levelResult, String code, String info, StoreType storeType, SendType sendType) {
    	Check resultValidation = of(levelResult);
    	resultValidation.setDescription(description);
    	resultValidation.setOvercomed(overcomed);
    	resultValidation.setCode(code);
    	resultValidation.setInfo(info);
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	
	public static BuilderCheck make(String description, boolean overcomed, State levelResult) {
		return new BuilderCheck(description, overcomed, levelResult);
	}
	
	public static class BuilderCheck {
		private final String description;
		private final boolean overcomed;
		private final State levelResult;
		
		private StoreType store = StoreType.Evidences; 
		private SendType send = SendType.None;
		private String code;
		private String info;
		
		public BuilderCheck(String description, boolean overcomed, State levelResult) {
			this.description = description;
			this.overcomed = overcomed;
			this.levelResult = levelResult;
		}
		
		public BuilderCheck store(StoreType store) {
			this.store = store;
			return this;
		}
		
		public BuilderCheck send(SendType send) {
			this.send = send;
			return this;
		}		
		
		public BuilderCheck code(String code) {
			this.code = code;
			return this;
		}
		
		public BuilderCheck info(String info) {
			this.info = info;
			return this;
		}

		public Check build() {
			return Check.of(description, overcomed, levelResult, code, info, store, send);
		}

	}

}
