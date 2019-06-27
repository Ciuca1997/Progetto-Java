/*
 * Authors: Andrea Papiri,Giacomo Ciucaloni
 * Version: 1.0
 * Update: 21/6/19
 */
package programma;

import java.util.HashMap;

public class Doctor {
	/**
	 * doctor class used to map information
	 */
	public HashMap<String,String>attributes=new HashMap<String, String>();//public perchè spring serializza solo attributi public
	HashMap<String,String> get_attributes(){
		/**
		 * return hasmap where are mapped the attributes
		 */
		return attributes;
	}
	public boolean equals(Doctor anotherDoctor) {
		return(attributes.equals(anotherDoctor.get_attributes()));
	}
}
