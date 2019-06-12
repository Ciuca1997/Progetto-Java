/*
 * Author: Andrea Papiri
 * Version: 1.0
 * Update: 12/6/19
 */
package programma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class json {
	private String url_csv;//variabile che immagazzina url csv
	public json(String url_json) {
		byte[] buffer=new byte[1024];//variabile usata come buffer temporaneo
		InputStream in;//usato come stream di input generale
		OutputStream out;//output stream generale
		//verifica se il file è presente in locale
		File json_local=new File("./json");
		if(json_local.exists()==false) {
			//se non è presente lo scarica
			try {
				//apro connessione
				HttpURLConnection connection=(HttpURLConnection)(new URL(url_json)).openConnection();
				connection.setRequestMethod("GET");
				//se non si specifica l'user-agent il server risponde con forbidden
				connection.setRequestProperty("User-Agent","Godzilla/5.0");
				in=connection.getInputStream();
				//creo file in locale
				File file_out=new File("./json");
				file_out.createNewFile();
				out=new FileOutputStream(file_out);
				while(in.read(buffer)==buffer.length) {
					out.write(buffer);
				}
				out.write(buffer);
				in.close();
				out.close();
			}
			catch(Exception ex){
				System.out.println("impossibile salvare file json:");
				System.out.println(ex.getMessage());
				System.exit(-1);
			}
		}
		//parsing file locale ed inizializzazione della variabile puntatore a csv
		String json_data="";
		int url_start,url_end;
		try {
			in = new FileInputStream(new File("./json"));
			while(in.read(buffer)==buffer.length) {
				json_data+=new String(buffer);
			}
			json_data+=new String(buffer);
		url_start=json_data.indexOf("\"url\":")+7;
		url_end=url_start+json_data.substring(url_start, json_data.length()).indexOf(",")-1;
		url_csv=json_data.substring(url_start, url_end);
		} catch (Exception ex) {
			System.out.println("impossibile caricare file json locale:");
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
	}
	public String get_url_csv() {//permette di accedere alla variabile url_csv
		return url_csv;
	}
}
