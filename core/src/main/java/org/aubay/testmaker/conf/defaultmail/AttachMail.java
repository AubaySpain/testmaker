package org.aubay.testmaker.conf.defaultmail;


public class AttachMail {
    String path;
    String nameInMail;
    String type;
    byte[] contenido;

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }       
    
    public String getNameInMail() {
        return this.nameInMail;
    }

    public void setNameInMail(String nameInMail) {
        this.nameInMail = nameInMail;
    }
    
    public String getType() {
    	return this.type;
    }
    
    public void setType(String type) {
    	this.type = type;
    }
    
    public byte[] getContenido() {
    	return this.contenido;
    }
    
    public void setContenido(byte[] contenido) {
    	this.contenido = contenido;
    }

    @Override
    public String toString() {
            return "attachMail [path=" + this.path + ",nameInMail=" + this.nameInMail + ", toString()=" + super.toString() + "]";
    }    
}
