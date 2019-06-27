/*
 * Authors: Andrea Papiri,Giacomo Ciucaloni
 * Version: 1.1
 * Update: 21/6/19
 */
package programma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class Json {
	/**
	 * manage json source and read csv url
	 */
	private String url_csv;//variabile che immagazzina url csv
	public Json(String url_json) {
		/**
		 * parsing json file and save csv url
		 */
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
				connection.setRequestProperty("User-Agent","Mozilla/5.0");
				in=connection.getInputStream();
				//creo file in locale
				File file_out=new File("./json");
				file_out.createNewFile();
				out=new FileOutputStream(file_out);
				int reads=0;
				while(reads!=-1) {
					out.write(Arrays.copyOfRange(buffer, 0, reads));
					reads=in.read(buffer);
				}
				in.close();
				out.close();
			}
			catch(Exception ex){
				System.out.println("failed to save json:");
				System.out.println(ex.getMessage());
				System.exit(-1);
			}
			System.out.println("created json local copy");
		}
		//parsing file locale ed inizializzazione della variabile puntatore a csv
		String json_data="";
		int url_start,url_end;
		try {
			in = new FileInputStream(new File("./json"));
			int reads=0;
			while(reads!=-1) {
				json_data+=new String(Arrays.copyOfRange(buffer, 0, reads));
				reads=in.read(buffer);
			}
		url_start=json_data.indexOf("\"url\":")+7;
		url_end=url_start+json_data.substring(url_start, json_data.length()).indexOf(",")-1;
		url_csv=json_data.substring(url_start, url_end);
		url_csv=url_csv.replace("\\","");
		} catch (Exception ex) {
			System.out.println("failed to load local json:");
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
	}
	public String get_url_csv() {//permette di accedere alla variabile url_csv
		/**
		 * return csv url
		 */
		return url_csv;
	}
}
