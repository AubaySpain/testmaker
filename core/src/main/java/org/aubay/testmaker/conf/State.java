package org.aubay.testmaker.conf;

public enum State  {
    Undefined(0, "black"),
    Ok(1, "green"),
    Info(2, "blue"), 
    Warn(4, "#8000ff"),
    Defect(6, "crimson"),
    Skip(8, "darkGrey"),
    Nok(11, "red"),
    Stopped(11, "red");
    
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
}
