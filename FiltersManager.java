package programma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.json.*;

public class FiltersManager {
	private ArrayList<Doctor> doctors=null;
	private ArrayList<Doctor>doctorsResult=null;
	public FiltersManager(String jsonFilter,Csv csv) {
		doctors=(ArrayList<Doctor>)csv.get_doctors().clone();
		JsonParser parser = new BasicJsonParser();
		Map<String,Object>jsonmapped=null;
		jsonmapped=parser.parseMap(jsonFilter);//parso json
		String check=jsonmapped.keySet().toArray()[0].toString();
		if(!check.equals("$or")&&!check.equals("$and")) {//check struttura operatore not nin in
			Filter filter=new Filter((HashMap) jsonmapped);
			doctorsResult=execute_filter(filter.get_field(), filter.get_operator(), filter.get_values());
		}
		if(check.equals("$or")) {
			String field0=((HashMap)((ArrayList<Object>)jsonmapped.get("$or")).get(0)).keySet().toArray()[0].toString();
			String value0=(String) ((HashMap)((ArrayList<Object>)jsonmapped.get("$or")).get(0)).get(field0);
			ArrayList <String> tmp0=new ArrayList<String>();
			tmp0.add(value0);
			String field1=((HashMap)((ArrayList<Object>)jsonmapped.get("$or")).get(1)).keySet().toArray()[0].toString();
			String value1=(String) ((HashMap)((ArrayList<Object>)jsonmapped.get("$or")).get(1)).get(field1);
			ArrayList <String> tmp1=new ArrayList<String>();
			tmp1.add(value1);
			doctorsResult=orFilter(inFilter(field0,tmp0),inFilter(field1,tmp1));
		}
		if(check.equals("$and")) {
			String field0=((HashMap)((ArrayList<Object>)jsonmapped.get("$and")).get(0)).keySet().toArray()[0].toString();
			String value0=(String) ((HashMap)((ArrayList<Object>)jsonmapped.get("$and")).get(0)).get(field0);
			ArrayList <String> tmp0=new ArrayList<String>();
			tmp0.add(value0);
			String field1=((HashMap)((ArrayList<Object>)jsonmapped.get("$and")).get(1)).keySet().toArray()[0].toString();
			String value1=(String) ((HashMap)((ArrayList<Object>)jsonmapped.get("$and")).get(1)).get(field1);
			ArrayList <String> tmp1=new ArrayList<String>();
			tmp1.add(value1);
			doctorsResult=andFilter(inFilter(field0,tmp0),inFilter(field1,tmp1));
		}
	}
//////////////////////////////////////////////////metodo di parsing di un filtro
	public ArrayList<Doctor> execute_filter(String field,String operator,ArrayList<String>values) {
		//esegue il filtro ed assegna doctorsResults
		switch(operator) {
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
//////////////////////////////////////////////////filtersMethods
	
/////////////////////////////////logicalOperators

	public ArrayList<Doctor> inFilter(String field, ArrayList<String> values) { // inFilter
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		for (int i = 0; i < values.size(); i++) { // cheking all the list's value
			for (int j = 0; j < doctors.size(); j++) { // cycling inside the doctors' list
				if (doctors.get(j).get_attributes().get(field).equals(values.get(i))) { // checking the value's
																						// condiction
					doctorsTemp.add(doctors.get(j)); // add the doctor
				}
			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> ninFilter(String field, ArrayList<String> values) { // ninFilter
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		doctorsTemp = (ArrayList<Doctor>) doctors.clone(); // copy of the doctors in the doctorsTemp
		for (int i = 0; i < values.size(); i++) { // checkjing all the values of the list
			for (int j = 0; j < doctorsTemp.size(); j++) { // cycling inside the cloned list
				if (doctorsTemp.get(j).get_attributes().get(field).equals(values.get(i))) { // checking if it meet the
																							// value condition
					doctorsTemp.remove(j); // remove the doctor
				}
			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> orFilter(ArrayList<Doctor> doctors1, ArrayList<Doctor> doctors2) { // orFilter
		ArrayList<Doctor> tmpDoctors = new ArrayList<Doctor>();
		for (int i = 0; i < doctors1.size(); i++) {
			if (!tmpDoctors.contains(doctors1.get(i))) {
				tmpDoctors.add(doctors1.get(i)); // remove the elements that are not in common
			}
		}
		for (int i = 0; i < doctors2.size(); i++) {
			if (!tmpDoctors.contains(doctors2.get(i))) {
				tmpDoctors.add(doctors2.get(i)); // remove the elements that are not in common
			}
		}
		return tmpDoctors;
	}

	public ArrayList<Doctor> andFilter(ArrayList<Doctor> doctors1, ArrayList<Doctor> doctors2) { // andFilter
		ArrayList<Doctor> tmpDoctors = new ArrayList<Doctor>();
		for (int i = 0; i < doctors1.size(); i++) {
			if (doctors2.contains(doctors1.get(i))) {
				tmpDoctors.add(doctors1.get(i)); // remove the elements that are not in common
			}
		}
		return tmpDoctors;
	}

//////////////////////////////////////conditionalOperators

	public ArrayList<Doctor> gtFilter(String field, ArrayList<String> values) { // gtFilter
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		int numMin;
		try { // check if the value is a number
			numMin = Integer.parseInt(values.get(0));
		} catch (Exception ex) {
			System.out.println("the value is not a numeber");
			return null; // return null if there is an exception
		}
		for (int i = 0; i < doctors.size(); i++) {
			try { // check on the field
				if (Integer.parseInt(doctors.get(i).get_attributes().get(field)) > numMin) { // check the condiction
					doctorsTemp.add(doctors.get(i)); // add it to the list
				}
			} catch (Exception ex) { // if the field does not contains numbers return null

			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> gteFilter(String field, ArrayList<String> values) { // gteFilter
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		int numMin;
		try { // check if the value is a number
			numMin = Integer.parseInt(values.get(0));
		} catch (Exception ex) { // return null if there is an exception
			System.out.println("the value is not a numeber");
			return null;
		}
		for (int i = 0; i < doctors.size(); i++) {
			try { // check on the field
				if (Integer.parseInt(doctors.get(i).get_attributes().get(field)) >= numMin) { // check the condiction
					doctorsTemp.add(doctors.get(i)); // add it to the list
				}
			} catch (Exception ex) { // if the field does not contains numbers return null
			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> ltFilter(String field, ArrayList<String> values) { // ltFilter
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		int numMax;
		try { // check if the value is a number
			numMax = Integer.parseInt(values.get(0));
		} catch (Exception ex) { // return null if there is an exception
			System.out.println("the value is not a numeber");
			return null;
		}
		for (int i = 0; i < doctors.size(); i++) {
			try { // check on the field
				if (Integer.parseInt(doctors.get(i).get_attributes().get(field)) < numMax) { // check the condiction
					doctorsTemp.add(doctors.get(i)); // add it to the list
				}
			} catch (Exception ex) { // if the field does not contains numbers return null
			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> lteFilter(String field, ArrayList<String> values) { // lteFilter
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		int numMax;
		try { // check if the value is a number
			numMax = Integer.parseInt(values.get(0));
		} catch (Exception ex) { // return null if there is an exception
			System.out.println("the value is not a numeber");
			return null;
		}
		for (int i = 0; i < doctors.size(); i++) {
			try { // check on the field
				if (Integer.parseInt(doctors.get(i).get_attributes().get(field)) <= numMax) { // check the condiction
					doctorsTemp.add(doctors.get(i)); // add it to the list
				}
			} catch (Exception ex) { // if the field does not contains numbers return null
			}
		}
		return doctorsTemp;
	}

	public ArrayList<Doctor> btFilter(String field, ArrayList<String> values) { // btFilter
		ArrayList<Doctor> doctorsTemp = new ArrayList<Doctor>();
		ArrayList<String> value1 = new ArrayList<String>();
		ArrayList<String> value2 = new ArrayList<String>();
		value1.add(values.get(0)); // value for gt
		value2.add(values.get(1)); // value for lt
		doctorsTemp = andFilter(gtFilter(field, value1), ltFilter(field, value2)); // is used the andFilter for melt the
																					// other two filter(lt,gt)
		return doctorsTemp;
	}

///////////////////////////////////////////////////getters and setters
	public ArrayList<Doctor> getDoctorsResult() {
		return doctorsResult;
	}
}
