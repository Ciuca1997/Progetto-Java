package programma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Filter {
	private String field;
	private String operator;
	private ArrayList values;
	
	public Filter(HashMap mappedjson) {//si occupa di parsare il json relativo al filtro
		field=mappedjson.keySet().toArray()[0].toString();
		operator=((Map)mappedjson.get(field)).keySet().toArray()[0].toString();
		values=new ArrayList<String>();//creo array list di valori
		try {//se è un array lo casto a ArrayList
			values=(ArrayList<String>)((Map)mappedjson.get(field)).get(operator);
		}
		catch(Exception ex) {//sltrimenti append
			values.add((String)((Map)mappedjson.get(field)).get(operator));
		}
	}
	public String get_field() {
		return field;
	}
	public String get_operator() {
		return operator;
	}
	public ArrayList<String>get_values(){
		return values;
	}

}
