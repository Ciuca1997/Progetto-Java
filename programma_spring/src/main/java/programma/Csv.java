/*
 * Authors: Andrea Papiri,Giacomo Ciucaloni
 * Version: 1.3
 * Update: 21/6/19
 */
package programma;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Csv {
	/**
	 * class that manage all information in csv file
	 */
	private ArrayList<Doctor>doctors=new ArrayList<Doctor>();//mappa dati csv
	public Csv(Json json) {
		/**
		 * inizializes array of doctor with parsed field
		 */
		String url=json.get_url_csv();//prendo l'url di riferimento
		InputStream in;//usato come stream di input generale
		byte[] buffer=new byte[1024];//variabile usata come buffer temporaneo
		String csv_data="";
		try {
			//apro connessione
			HttpURLConnection connection=(HttpURLConnection)(new URL(url)).openConnection();
			connection.setRequestMethod("GET");
			//se non si specifica l'user-agent il server risponde con forbidden
			connection.setRequestProperty("User-Agent","Mozilla/5.0");
			in=connection.getInputStream();
			int reads=0;
			while(reads!=-1) {
				csv_data+=new String(Arrays.copyOfRange(buffer, 0, reads));
				reads=in.read(buffer);
			}
			in.close();		
		}
		catch(Exception ex){
			System.out.println("failed to load csv:");
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
		//creo un buffer temporaneo di chiavi e per ogni riga un buffer temporaneo di valori
		csv_data=csv_data.replace(";;",";x;");
		String[] keys;
		String[] value;
		keys=(csv_data.split("\r\n")[0]).split(";");
		for(int cur_row=1;cur_row<csv_data.split("\r\n").length;cur_row+=1) {
			//creo array valori e li carico ognuno nella apposita chiave ed aggiungo dottore
			doctors.add(new Doctor());
			value=(csv_data.split("\r\n")[cur_row]).split(";");
			for(int cur_val=0;cur_val<keys.length;cur_val+=1) {
				try {
					doctors.get(doctors.size()-1).get_attributes().put(keys[cur_val],value[cur_val]);
				}
				catch(Exception ex) {}
			}
		}
	}
	public ArrayList<Doctor> get_doctors() {
		/**
		 * return array of doctor
		 */
		return doctors;
	}

}
