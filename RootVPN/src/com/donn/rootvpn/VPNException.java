package com.donn.rootvpn;

public class VPNException extends Exception {
	
	private static final long serialVersionUID = 1234432; 
	private Object sourceClass;
	private String message;
	
	public VPNException (Object sourceClass, String message) {
		this.sourceClass = sourceClass;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Object getSource() {
		return sourceClass;
	}

}
