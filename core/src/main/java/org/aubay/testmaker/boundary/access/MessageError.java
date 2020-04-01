package org.aubay.testmaker.boundary.access;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageError {

	private String message;
	
	public MessageError() {}
	
	public MessageError(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) { 
			return true; 
		} 
		if (!(o instanceof MessageError)) { 
			return false; 
		} 
		
		MessageError c = (MessageError) o; 
		return (c.getMessage().compareTo(getMessage())==0); 
	}
	
}
