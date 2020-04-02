package org.jorge2m.testmaker.testreports.html;

import java.util.ArrayList;


public class ResultadoErrores {

    public enum Resultado {OK, ERRORES, MAX_ERRORES}
	
    private Resultado resultado = Resultado.OK;
    private ArrayList<String> listaLogError;
	
    public Resultado getResultado() {
        return this.resultado;
    }
	
    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }
	
    public ArrayList<String> getlistaLogError() {
        return this.listaLogError;
    }
	
    public void setListaLogError(ArrayList<String> listaLogError) {
        this.listaLogError = listaLogError;
    }
	
    public boolean isOK() {
        return (getResultado() == Resultado.OK);
    }
}
