package com.github.jorge2m.testmaker.conf.defaultmail;


public class AttachMail {
    private final String nameInMail;
    private final String type;
    private final byte[] contenido;
   
    public AttachMail(String nameInMail, String type, byte[] contenido) {
    	this.nameInMail = nameInMail;
    	this.type = type;
    	this.contenido = contenido;
    }
    
    public String getNameInMail() {
        return this.nameInMail;
    }
    public String getType() {
    	return this.type;
    }
    public byte[] getContenido() {
    	return this.contenido;
    }

    @Override
    public String toString() {
            return "attachMail [nameInMail=" + this.nameInMail + ", toString()=" + super.toString() + "]";
    }    
}
