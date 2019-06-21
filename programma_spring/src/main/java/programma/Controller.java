/*
 * Authors: Andrea Papiri,Giacomo Ciucaloni
 * Version: 1.3
 * Update: 21/6/19
 */
package programma;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	Csv csv;
	//gestione ritorno metadati
	@RequestMapping(value="/metadata",produces = "application/json")
	public String metadati() {
		String result="[\r\n"
				+ "{\r\n"
				+ "\"alias\":\"CodiceRegione\",\r\n"
				+ "\"sourceField\":\"Codice della regione\",\r\n"
				+ "\"type\":\"string\"\r\n"
				+"},\r\n"
				+ "{\r\n"
				+ "\"alias\":\"CodiceASL_AO\",\r\n"
				+ "\"sourceField\":\"Codice asl regione\",\r\n"
				+ "\"type\":\"string\"\r\n"
				+"},\r\n"
				+ "{\r\n"
				+ "\"alias\":\"CognomeMedico\",\r\n"
				+ "\"sourceField\":\"Cognome del medico\",\r\n"
				+ "\"type\":\"string\"\r\n"
				+"},\r\n"
				+ "{\r\n"
				+ "\"alias\":\"NomeMEdico\",\r\n"
				+ "\"sourceField\":\"Nome del medico\",\r\n"
				+ "\"type\":\"string\"\r\n"
				+"},\r\n"
				+ "{\r\n"
				+ "\"alias\":\"IndirizzoStudioMedico_Struttura\",\r\n"
				+ "\"sourceField\":\"Indirizzo studio medico\",\r\n"
				+ "\"type\":\"string\"\r\n"
				+"},\r\n"
				+ "{\r\n"
				+ "\"alias\":\"CAP\",\r\n"
				+ "\"sourceField\":\"Codice avviamento postale\",\r\n"
				+ "\"type\":\"string\"\r\n"
				+"},\r\n"
				+ "{\r\n"
				+ "\"alias\":\"ComuneStudioMedico_Struttura\",\r\n"
				+ "\"sourceField\":\"Comune studio medico\",\r\n"
				+ "\"type\":\"string\"\r\n"
				+"},\r\n"
				+ "{\r\n"
				+ "\"alias\":\"Provincia\",\r\n"
				+ "\"sourceField\":\"Nome della provincia\",\r\n"
				+ "\"type\":\"string\"\r\n"
				+"}\r\n]";
		return result;
	}
	//gestione della richiesta dati ritorna json elaborato
	//impossibile gestire array perchè con le parentesi quadre errore di ricezione
	@RequestMapping(value="/data",produces = "application/json")
	public String data(@RequestParam Map<String,String> params)throws Exception {
		String resp="[";
		String filter=params.get("filter");
		if(filter==null) {
			for(int cur=0;cur<csv.get_doctors().size();cur++) {
				try {
					resp+=csv.get_doctors().get(cur).tojson()+",";
					}
				catch(Exception ex) {}
			}
			resp=resp.substring(0,resp.length()-1);
			resp+="]";
		}
		else {
			filter=filter.replace("\"","");
			filter=filter.replace(" ","");
			int startindex=filter.indexOf("$");
			int endindex=filter.indexOf(":",startindex+1);
			String command=filter.substring(startindex+1,endindex);
			if(command.equals("not")==true) {
				startindex=filter.indexOf("{");
				endindex=filter.indexOf(":",startindex+1);
				String field=filter.substring(startindex+1,endindex);
				startindex=filter.indexOf(":");
				startindex=filter.indexOf(":",startindex+1);
				endindex=filter.indexOf("}",startindex+1);
				String value=filter.substring(startindex+1,endindex);
				for(int cur=0;cur<csv.get_doctors().size();cur++) {
					try {
						if(csv.get_doctors().get(cur).get_attributes().get(field).equals(value)==false) {
							resp+=csv.get_doctors().get(cur).tojson()+",";
						}
					}
					catch(Exception ex) {}
				}
				resp=resp.substring(0,resp.length()-1);
				resp+="]";
			}
			if(command.equals("in")==true) {
				startindex=filter.indexOf("{");
				endindex=filter.indexOf(":",startindex+1);
				String field=filter.substring(startindex+1,endindex);
				startindex=filter.indexOf(":");
				startindex=filter.indexOf(":",startindex+1);
				endindex=filter.indexOf("}",startindex+1);
				String value=filter.substring(startindex+1,endindex);
				for(int cur=0;cur<csv.get_doctors().size();cur++) {
					try {
						if(csv.get_doctors().get(cur).get_attributes().get(field).equals(value)==true) {
							resp+=csv.get_doctors().get(cur).tojson()+",";
						}
					}
					catch(Exception ex) {}
				}
				resp=resp.substring(0,resp.length()-1);
				resp+="]";
			}
			if(command.equals("gt")==true) {
				startindex=filter.indexOf("{");
				endindex=filter.indexOf(":",startindex+1);
				String field=filter.substring(startindex+1,endindex);
				startindex=filter.indexOf(":");
				startindex=filter.indexOf(":",startindex+1);
				endindex=filter.indexOf("}",startindex+1);
				String value=filter.substring(startindex+1,endindex);
				for(int cur=0;cur<csv.get_doctors().size();cur++) {
					try {				
						if(Integer.parseInt(csv.get_doctors().get(cur).get_attributes().get(field))>Integer.parseInt(value)) {
							resp+=csv.get_doctors().get(cur).tojson()+",";
						}
					}
					catch(Exception ex) {}
				}
				resp=resp.substring(0,resp.length()-1);
				resp+="]";
			}
		}
		return resp;
	}
	//gestisce le statische(solo count visto il dataset fornito)
	//vengono eseguite su json elaborato da eventuali filtri
	@RequestMapping(value="/stats",produces = "application/json")
	public String stat(@RequestParam Map<String,String> params)throws Exception {
		String filter=params.get("filter");
		HashMap<String, String>arg=new HashMap<String, String>();
		arg.put("filter", filter);
		String result=data(arg);
		String value=params.get("field");
		value=value.replace("\"","");
		value=value.replace(" ","");
		int count=result.split("\""+value+"\"").length-1;
		String resp="[\r\n{\r\n"
			+"\"field\":\""+value+"\",\r\n"
			+"\"count\":\""+String.valueOf(count)+"\"\r\n}\r\n]";
		return resp;
	}
	public Controller() {
		//flusso inizializzazioni
		Json json=new Json("https://www.dati.gov.it/api/3/action/package_show?id=f9b81f42-9062-4caf-ba66-834123ced808");
		System.out.println("csv @ "+json.get_url_csv());
		csv=new Csv(json);
		System.out.println("data acquired "+String.valueOf(csv.get_doctors().size())+" doctors");
		System.out.println("ready to request...");
	}
}
