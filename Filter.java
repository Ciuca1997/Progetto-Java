/*
 * authors: Andrea Papiri, Giacomo Ciucaloni
 * version:1.0.0
 * update: 26/06/2019
 * description: class that is used for filter the list of doctors. every method implements a different type of filter
*/
package programma;

import java.util.ArrayList;

public class Filter {  //filters: in,not,nin,or,and,gt,lt
	///////////////////////////////////////////////////attributes
	
	//private ArrayList<String> values;       //list of filter's values
	private ArrayList<Doctor> doctorsResult;  //list of doctors that is used for the filter's operations
	//private ArrayList<String> fields;       //field(s) of the values 
	private final ArrayList<Doctor> doctors;  //list of ll doctors
	
	
	///////////////////////////////////////////////////constructor
	
	public Filter(String jsonFilter,Csv csv) {
		doctorsResult.clear();
		doctors=csv.get_doctors();
		//doctorsTemp=(ArrayList<Doctor>) doctors.clone();
	}

	//////////////////////////////////////////////////filtersMethods
	
	public ArrayList<Doctor> inFilter(String field,ArrayList<String> values) { //inFilter
		ArrayList<Doctor> doctorsTemp =new ArrayList<Doctor>();
		for(int i=0;i<values.size();i++ ) {   //cheking all the list's value
			for(int j=0;j<doctors.size();j++) {  //cycling inside the doctors' list
				if(doctors.get(j).get_attributes().get(field).equals(values.get(i))) {  //checking the value's condiction 
					doctorsTemp.add(doctors.get(j));  //add the doctor
				}
			}
		}
		return doctorsTemp;
	}

	
	public ArrayList<Doctor> ninFilter(String field,ArrayList<String> values) { //ninFilter
		ArrayList<Doctor> doctorsTemp =new ArrayList<Doctor>();     
		doctorsTemp=(ArrayList<Doctor>) doctors.clone();   //copy of the doctors in the doctorsTemp
		for(int i=0;i<values.size();i++ ) {        //checkjing all the values of the list
			for(int j=0;j<doctorsTemp.size();j++) {   //cycling inside the cloned list
				if(doctorsTemp.get(j).get_attributes().get(field).equals(values.get(i))) { //checking if it meet the value condition
					doctorsTemp.remove(j);  //remove the doctor
				}
			}
		}
		return doctorsTemp;
	}

	
	public ArrayList<Doctor> orFilter(ArrayList<Doctor> doctors1,ArrayList<Doctor> doctors2) { //orFilter
		ArrayList<Doctor> doctorsTemp=new ArrayList<Doctor>();
		doctorsTemp=(ArrayList<Doctor>) doctors1.clone();  //copy the first list 
		for(int i=0;i<doctors2.size();i++) {   
			if(!doctorsTemp.contains(doctors2.get(i))) {  
				doctorsTemp.add(doctors2.get(i));   //insert inside the copied list only the elements that are not inside the copied list
			}
		}
		return doctorsTemp;
	}
	
	
	public ArrayList<Doctor> andFilter(ArrayList<Doctor> doctors1,ArrayList<Doctor> doctors2) { //andFilter
		ArrayList<Doctor> doctorsTemp= new ArrayList<Doctor>();
		doctorsTemp=(ArrayList<Doctor>) doctors1.clone(); //copy the first list 
		for(int i=0;i<doctorsTemp.size();i++) {
			if(!doctors2.contains(doctorsTemp.get(i))) {  
				doctorsTemp.remove(i);  //remove the elements that are not in common 
			}
		}
		return doctorsTemp;
	}
	
	
	///////////////////////////////////////////////////getters and setters
	
	public ArrayList<Doctor> getDoctorsResult() {
		return doctorsResult;
	}
}
