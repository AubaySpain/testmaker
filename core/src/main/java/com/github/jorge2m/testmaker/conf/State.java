package com.github.jorge2m.testmaker.conf;

public enum State  {
    UNDEFINED(0, "black"),
    OK(1, "green"),
    INFO(2, "blue"),
    RETRY(3, "grey"),
    WARN(4, "#8000ff"),
    DEFECT(6, "crimson"),
    SKIP(8, "darkGrey"),
    KO(11, "red"),
    STOPPED(11, "red");
    
    private final int criticity;
    private final String colorCss;
 
    State(int criticity, String colorCss) {
        this.criticity = criticity;
        this.colorCss = colorCss;
    }
    
    public int getCriticity() {
        return this.criticity;
    }
    
    public boolean isMoreCriticThan(State state) {
    	return (criticity > state.getCriticity());
    }
    
    public static State getMoreCritic(State state1, State state2) {
    	if (state1.isMoreCriticThan(state2)) {
    		return state1;
    	}
    	
    	return state2;
    }
    
    public String getColorCss() {
        return this.colorCss;
    }
    
    public static State getState(int criticity) {
        for (State estado : State.values()) {
            if (estado.getCriticity() == criticity) {
                return estado;
            }
        }
        return null;
    }
    
    public static State from(String value) {
    	//Temporal If
    	if ("NOK".compareTo(value.toUpperCase())==0) {
    		return KO;
    	}
    	
   		return State.valueOf(value.toUpperCase());
    }
    
}
