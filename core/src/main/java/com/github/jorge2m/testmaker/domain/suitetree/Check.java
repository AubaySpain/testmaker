package com.github.jorge2m.testmaker.domain.suitetree;

import com.github.jorge2m.testmaker.conf.State;



public class Check {
	//TODO este campo es prescindible cuando esté completada la migración a Aspectos
    private int id;
    
	private String description = "";
    private State levelResult = State.Undefined;
    private boolean avoidEvidences = false;
	boolean overcomed = false;

    public Check() {}
	
    public Check(int id) {
    	this.id = id;
    }
    
    public static Check of(int id, String description, boolean overcomed, State levelResult, boolean avoidEvidences) {
    	Check resultValidation = of(id, levelResult);
    	resultValidation.setDescription(description);
    	resultValidation.setOvercomed(overcomed);
    	resultValidation.setAvoidEvidences(avoidEvidences);
    	return resultValidation;
    }
    
    public static Check of(int id, State levelResult) {
    	Check resultValidation = new Check(id);
    	resultValidation.setLevelResult(levelResult);
    	return resultValidation;
    }
    
    public int getId() {
		return id;
	}
    public void setId(int id) {
    	this.id = id;
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
    public boolean isAvoidEvidences() {
		return avoidEvidences;
	}
	public void setAvoidEvidences(boolean avoidEvidences) {
		this.avoidEvidences = avoidEvidences;
	}
    public boolean isOvercomed() {
		return overcomed;
	}
	public void setOvercomed(boolean overcomed) {
		this.overcomed = overcomed;
	}
}
