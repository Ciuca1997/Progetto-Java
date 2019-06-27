package programma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.json.*;

public class FiltersManager {
	
	//attributi
	private ArrayList<Doctor> doctors=null;
	private ArrayList<Doctor>doctorsResult=null;
	
	//costruttore
	public FiltersManager(String jsonFilter,Csv csv) {
		doctors=(ArrayList<Doctor>)csv.get_doctors().clone(); //copio dalla lista originale
		JsonParser parser = new BasicJsonParser(); 
		Map<String,Object>jsonmapped=null;
		jsonmapped=parser.parseMap(jsonFilter);//parso json
		String check=jsonmapped.keySet().toArray()[0].toString(); //controllo filtro
		if(!check.equals("$or")&&!check.equals("$and")) {//check struttura operatore not nin in
			Filter filter=new Filter((HashMap) jsonmapped);
			doctorsResult=execute_filter(filter.get_field(), filter.get_operator(), filter.get_values(), doctors); //richiamo il metodo di esecuzione filtri
		}
		if(check.equals("$or")) { //filtro or
			String field0=((HashMap)((ArrayList<Object>)jsonmapped.get("$or")).get(0)).keySet().toArray()[0].toString(); //ottengo primo field
			String value0=(String) ((HashMap)((ArrayList<Object>)jsonmapped.get("$or")).get(0)).get(field0); //ottengo il primo valore
			ArrayList <String> tmp0=new ArrayList<String>(); //genero lista temporanea per i valori del primo filtro
			tmp0.add(value0);
			String field1=((HashMap)((ArrayList<Object>)jsonmapped.get("$or")).get(1)).keySet().toArray()[0].toString(); //ottengo il secondo field
			String value1=(String) ((HashMap)((ArrayList<Object>)jsonmapped.get("$or")).get(1)).get(field1); //ottengo secondo valore
			ArrayList <String> tmp1=new ArrayList<String>(); //genero la lista per il secondo filtro
			tmp1.add(value1);
			doctorsResult=orFilter(inFilter(field0,tmp0),inFilter(field1,tmp1)); //richiamo il filtro or passando due filtri in
		}
		if(check.equals("$and")) { //filtro and
			String field0=((HashMap)((ArrayList<Object>)jsonmapped.get("$and")).get(0)).keySet().toArray()[0].toString(); //ottengo primo field
			String value0=(String) ((HashMap)((ArrayList<Object>)jsonmapped.get("$and")).get(0)).get(field0); //ottengo il primo valore
			ArrayList <String> tmp0=new ArrayList<String>(); //genero lista temporanea per i valori del primo filtro
			tmp0.add(value0);
			String field1=((HashMap)((ArrayList<Object>)jsonmapped.get("$and")).get(1)).keySet().toArray()[0].toString(); //ottengo secondo field
			String value1=(String) ((HashMap)((ArrayList<Object>)jsonmapped.get("$and")).get(1)).get(field1); //ottengo il secondo valore
			ArrayList <String> tmp1=new ArrayList<String>(); //genero lista temporanea per i valori del secondo filtro
			tmp1.add(value1);
			doctorsResult=andFilter(inFilter(field0,tmp0),inFilter(field1,tmp1)); //richiamo filtro and passandogli due filtri in 
		}
	}
	
	
//////////////////////////////////////////////////metodo di parsing di un filtro
	
	//metodo per l'esecuzione dei filtri
	public ArrayList<Doctor> execute_filter(String field,String operator,ArrayList<String>values,ArrayList<Doctor>doctors) { 
		//esegue il filtro ed assegna doctorsResults
		switch(operator) { //controllo tipologia filtro
		case "$not":
			return ninFilter(field, values);
		case "$nin":
			return ninFilter(field, values);
		case "$in":
			return inFilter(field, values);
		case "$gt":
			return gtFilter(field, values);
		case "$gte":
			return gteFilter(field, values);
		case "$lt":
			return ltFilter(field, values);
		case "$lte":
			return lteFilter(field, values);
		case "$bt":
			return btFilter(field, values);
		default:
			return new ArrayList<Doctor>();
		}		
	}

	
/////////////////////////////////operatori logici

	public ArrayList<Doctor> inFilter(String field, ArrayList<String> values) { // filtro in
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		for (int i = 0; i < values.size(); i++) { // controllo tutti i valori in ingresso sulla lista
			for (int j = 0; j < doctors.size(); j++) { // ciclo su tutta la lista dei dottori
				if (doctors.get(j).get_attributes().get(field).equals(values.get(i))) { // controllo la condizione
					doctorsTemp.add(doctors.get(j)); // aggiungo il valore alla lista temporanea che rispetta la condizione
				}
			}
		}
		return doctorsTemp;
	}

	
	public ArrayList<Doctor> ninFilter(String field, ArrayList<String> values) { // filtro nin
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		doctorsTemp = (ArrayList<Doctor>) doctors.clone(); // copio la lista dei dottori
		for (int i = 0; i < values.size(); i++) { // controllo tutta la lista dei valori
			for (int j = 0; j < doctorsTemp.size(); j++) { // ciclo su tutta la lista temporanea
				if (doctorsTemp.get(j).get_attributes().get(field).equals(values.get(i))) { // controllo la condizione
					doctorsTemp.remove(j); // rimuovo l'elemento che rispetta la condizione
				}
			}
		}
		return doctorsTemp;
	}

	
	public ArrayList<Doctor> orFilter(ArrayList<Doctor> doctors1, ArrayList<Doctor> doctors2) { // filtro or
		ArrayList<Doctor> tmpDoctors = new ArrayList<Doctor>();
		for (int i = 0; i < doctors1.size(); i++) {  //copio la prima lista sulla lista temporanea
			tmpDoctors.add(doctors1.get(i)); 
		}
		for (int i = 0; i < doctors2.size(); i++) { //aggiungo alla lista temporanea gli elementi della seconda lista
			if (!tmpDoctors.contains(doctors2.get(i))) { //evitando di inserire cloni
				tmpDoctors.add(doctors2.get(i)); 
			}
		}
		return tmpDoctors;
	}

	public ArrayList<Doctor> andFilter(ArrayList<Doctor> doctors1, ArrayList<Doctor> doctors2) { // filtro and
		ArrayList<Doctor> tmpDoctors = new ArrayList<Doctor>();
		for (int i = 0; i < doctors1.size(); i++) { //ciclo la prima lista
			if (doctors2.contains(doctors1.get(i))) { //controllo gli elementi in comune
				tmpDoctors.add(doctors1.get(i)); //aggiungo gli elementi in comune alle due liste su quella temporanea
			}
		}
		return tmpDoctors;
	}

//////////////////////////////////////conditionalOperators

	public ArrayList<Doctor> gtFilter(String field, ArrayList<String> values) { // filtro gt
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		int numMin;
		try { // controllo se il valore in ingresso è numerico
			numMin = Integer.parseInt(values.get(0)); //conversione del valore da string a int
		} catch (Exception ex) {
			System.out.println("the value is not a numeber");
			return null; // ritorna null se ci sono problemi
		}
		for (int i = 0; i < doctors.size(); i++) {
			try { // controllo valori del field
				if (Integer.parseInt(doctors.get(i).get_attributes().get(field)) > numMin) { // controlla la condizione
					doctorsTemp.add(doctors.get(i)); // aggiunge l'elemento alla lista
				}
			} 
			catch (Exception ex) { // se non contiene numeri salta

			}
		}
		return doctorsTemp;
	}

	
	public ArrayList<Doctor> gteFilter(String field, ArrayList<String> values) { // filtro gte
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		int numMin;
		try { //controlla se il valore è un numero
			numMin = Integer.parseInt(values.get(0)); //conversione del valore da string a int
		} catch (Exception ex) { // ritorna null altrimenti
			System.out.println("the value is not a numeber");
			return null;
		}
		for (int i = 0; i < doctors.size(); i++) {
			try { // controllo sul field
				if (Integer.parseInt(doctors.get(i).get_attributes().get(field)) >= numMin) { // controllo la condizione
					doctorsTemp.add(doctors.get(i)); // agginge alla lista temporanea il valore che rispetta la condizione
				}
			} catch (Exception ex) { // se il field è non numerico salta
			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> ltFilter(String field, ArrayList<String> values) { // filtro lt
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		int numMax;
		try { // controllo sul valore 
			numMax = Integer.parseInt(values.get(0)); //conversione valore da string a int
		} catch (Exception ex) { // ritorna null se valore non è numerico
			System.out.println("the value is not a numeber");
			return null;
		}
		for (int i = 0; i < doctors.size(); i++) {
			try { // controllo del field
				if (Integer.parseInt(doctors.get(i).get_attributes().get(field)) < numMax) { //controllo la condizione
					doctorsTemp.add(doctors.get(i)); // aggiungo l'elemento che rispetta la condizione alla lista temporanea
				}
			} catch (Exception ex) { //se il field non contiene numeri salto
			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> lteFilter(String field, ArrayList<String> values) { // filtro lte
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		int numMax;
		try { //controllo il valore
			numMax = Integer.parseInt(values.get(0));  //converto il valore da string a int
		} catch (Exception ex) { // ritorno null se il valore nonè numerico
			System.out.println("the value is not a numeber");
			return null;
		}
		for (int i = 0; i < doctors.size(); i++) {
			try { //controllo field
				if (Integer.parseInt(doctors.get(i).get_attributes().get(field)) <= numMax) { // controllo la condizione
					doctorsTemp.add(doctors.get(i)); //aggiungo alla lista temporanea l'elemento che soddisfa la condizione
				}
			} catch (Exception ex) { //se il field non contiene numeri salta
			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> btFilter(String field, ArrayList<String> values) { // filtro bt
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		ArrayList<String> value1 = new ArrayList<String>();
		ArrayList<String> value2 = new ArrayList<String>();
		value1.add(values.get(0)); // valore per il filtro gt
		value2.add(values.get(1)); // valore per il filtro lt
		doctorsTemp = andFilter(gtFilter(field, value1), ltFilter(field, value2)); // questo filtro utilizza un filtro and con in ingresso un filtro lt ed uno gt 
		return doctorsTemp;
	}

///////////////////////////////////////////////////metodo get del risultato ottenuto
	public ArrayList<Doctor> getDoctorsResult() {
		return doctorsResult;
	}
}
