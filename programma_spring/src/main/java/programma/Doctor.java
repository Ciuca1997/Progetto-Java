/*
 * Authors: Andrea Papiri,Giacomo Ciucaloni
 * Version: 1.0
 * Update: 21/6/19
 */
package programma;

import java.util.HashMap;

public class Doctor {
	private HashMap<String,String>attributes=new HashMap<String, String>();
	HashMap<String,String> get_attributes(){
		return attributes;
	}
	public String tojson() {
		//formatta i campi in json
		String result="{";
		for(int cur=0;cur<attributes.keySet().size();cur+=1) {
			String key=(String) attributes.keySet().toArray()[cur];
			String value=attributes.get(key);
			result+="\""+key+"\""+":"+"\""+value+"\",";
		}
		result=result.substring(0,result.length()-1);
		result+="}";
		return result;
	}
}
