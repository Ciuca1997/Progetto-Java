/*
 * Author: Andrea Papiri
 * Version: 1.0
 * Update: 18/6/19
 */
package programma;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Csv {
	private HashMap<String, ArrayList<String>>csv_parsed_data=new HashMap<>();//mappa dati csv
	public Csv(Json init_json) {
		String url=init_json.get_url_csv();//prendo l'url di riferimento
		InputStream in;//usato come stream di input generale
		byte[] buffer=new byte[1024];//variabile usata come buffer temporaneo
		String csv_data="";
		try {
			//apro connessione
			HttpURLConnection connection=(HttpURLConnection)(new URL(url)).openConnection();
			connection.setRequestMethod("GET");
			//se non si specifica l'user-agent il server risponde con forbidden
			connection.setRequestProperty("User-Agent","Godzilla/5.0");
			in=connection.getInputStream();
			while(in.read(buffer)==buffer.length) {
				csv_data+=new String(buffer);
			}
			in.close();		
		}
		catch(Exception ex){
			System.out.println("impossibile trovare file csv:");
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
		//creo un buffer temporaneo di chiavi e per ogni riga un buffer temporaneo di valori
		String[] keys;
		String[] value;
		keys=(csv_data.split("\n")[0]).split(";");
		keys=Arrays.copyOfRange(keys,0,keys.length-2);
		//inizializzo campi vuoti
		for(int cur_key=0;cur_key<keys.length;cur_key+=1) {
			csv_parsed_data.put(keys[cur_key],new ArrayList<String>());
		}
		for(int cur_row=1;cur_row<csv_data.split("\n").length;cur_row+=1) {
			//creo array valori e li carico oStringgnuno nella apposita chiave
			value=(csv_data.split("\n")[cur_row]).split(";");
			value=Arrays.copyOfRange(value,0,value.length-2);
			for(int cur_val=0;cur_val<value.length;cur_val+=1) {
				csv_parsed_data.get(keys[cur_val]).add(String.valueOf(value[cur_val]));
			}
		}
	}
	public HashMap<String, ArrayList<String>> get_csv_parsed_data() {
		return csv_parsed_data;
	}

}
