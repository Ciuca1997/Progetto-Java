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
	
	/////////////////////////////////logicalOperators
	
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
	
	
	
	//////////////////////////////////////conditionalOperators
	
	
	public ArrayList<Doctor> gtFilter(String field,ArrayList<String> values){ //gtFilter
		ArrayList<Doctor> doctorsTemp= new ArrayList<Doctor>();
		int numMin;
		try {           //check if the value is a number
			numMin=Integer.parseInt(values.get(0));
		}
		catch(Exception ex) {
			System.out.println("the value is not a numeber");
			return null;   //return null if there is an exception
		}
		for(int i=0;i<doctors.size();i++) {
			try {       //check on the field 
				if(Integer.parseInt(doctors.get(i).get_attributes().get(field))>numMin) { //check the condiction
					doctorsTemp.add(doctors.get(i));    //add it to the list 
				}
			}
			catch(Exception ex) {  //if the field does not contains numbers return null
				System.out.println("selected field does not contain numbers");
				return null;
			}
		}
		return doctorsTemp;
	}
	
	
	
	public ArrayList<Doctor> gteFilter(String field,ArrayList<String> values){ //gteFilter
		ArrayList<Doctor> doctorsTemp= new ArrayList<Doctor>();
		int numMin;
		try {   //check if the value is a number
			numMin=Integer.parseInt(values.get(0));
		}
		catch(Exception ex) {  //return null if there is an exception
			System.out.println("the value is not a numeber");
			return null;
		}
		for(int i=0;i<doctors.size();i++) {
			try {         //check on the field 
				if(Integer.parseInt(doctors.get(i).get_attributes().get(field))>=numMin) {   //check the condiction
					doctorsTemp.add(doctors.get(i));   //add it to the list
				}
			}
			catch(Exception ex) {    //if the field does not contains numbers return null
				System.out.println("selected field does not contain numbers");
				return null;
			}
		}
		return doctorsTemp;
	}
	
	
	
	public ArrayList<Doctor> ltFilter(String field,ArrayList<String> values){  //ltFilter
		ArrayList<Doctor> doctorsTemp= new ArrayList<Doctor>();
		int numMax;
		try {		//check if the value is a number
			numMax=Integer.parseInt(values.get(0));
		}
		catch(Exception ex) {    //return null if there is an exception
			System.out.println("the value is not a numeber");
			return null;
		}
		for(int i=0;i<doctors.size();i++) {  
			try {			//check on the field 
				if(Integer.parseInt(doctors.get(i).get_attributes().get(field))<numMax) {   //check the condiction
					doctorsTemp.add(doctors.get(i));   //add it to the list
				}
			}
			catch(Exception ex) {         //if the field does not contains numbers return null
				System.out.println("selected field does not contain numbers");
				return null;
			}
		}
		return doctorsTemp;
	}
	
	

	public ArrayList<Doctor> lteFilter(String field,ArrayList<String> values){   //lteFilter
		ArrayList<Doctor> doctorsTemp= new ArrayList<Doctor>();
		int numMax;
		try {		//check if the value is a number
			numMax=Integer.parseInt(values.get(0));
		}
		catch(Exception ex) {    //return null if there is an exception
			System.out.println("the value is not a numeber");
			return null;
		}
		for(int i=0;i<doctors.size();i++) {  
			try {			//check on the field 
				if(Integer.parseInt(doctors.get(i).get_attributes().get(field))<=numMax) {   //check the condiction
					doctorsTemp.add(doctors.get(i));   //add it to the list
				}
			}
			catch(Exception ex) {         //if the field does not contains numbers return null
				System.out.println("selected field does not contain numbers");
				return null;
			}
		}
		return doctorsTemp;
	}
	
	
	
	public ArrayList<Doctor> btFilter(String field,ArrayList<String> values){   //btFilter
		ArrayList<Doctor> doctorsTemp= new ArrayList<Doctor>();
		ArrayList<String> value1=new ArrayList<String>();   
		ArrayList<String> value2=new ArrayList<String>();   
		value1.add(values.get(0));		//value for gt
		value2.add(values.get(1));		//value for lt
		doctorsTemp=andFilter(gtFilter(field,value1),ltFilter(field,value2)); //is used the andFilter for melt the other two filter(lt,gt) 
		return doctorsTemp;
	}
	
	
	
	///////////////////////////////////////////////////getters and setters
	
	public ArrayList<Doctor> getDoctorsResult() {
		return doctorsResult;
	}
}
