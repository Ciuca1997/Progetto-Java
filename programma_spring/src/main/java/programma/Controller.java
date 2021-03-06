/*
 * Authors: Andrea Papiri,Giacomo Ciucaloni
 * Version: 1.3
 * Update: 21/6/19
 */
package programma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	/**
	 * class that initializes json,csv and manage all request 
	 */
	private Csv csv;
	
	
	//gestione ritorno metadati
	@RequestMapping(value="/metadata",produces = "application/json")
	public ArrayList<Metadata> metadati() {
		Set<String>chiavi=csv.get_doctors().get(0).get_attributes().keySet();  //collezione delle chiavi
		ArrayList<Metadata>result=new ArrayList<Metadata>();
		//genero la lista dei field
		result.add(new Metadata("CodiceRegione", "Identificativo regione", "String"));
		result.add(new Metadata("CodiceASL_AO", "Codice ufficio ASL", "String"));
		result.add(new Metadata("CognomeMedico", "Cognome del medico", "String"));
		result.add(new Metadata("NomeMedico", "Nome del medico", "String"));
		result.add(new Metadata("IndirizzoStudioMedico_Struttura", "Ubicazione struttura", "String"));
		result.add(new Metadata("CAP", "Codice avviamento postale", "String"));
		result.add(new Metadata("ComuneStudioMedico_Struttura", "Comune dove risiede la sede operativa del medico", "String"));
		result.add(new Metadata("Provincia", "Provincia", "String"));
		return result; 
	}
	
	
	//gestione della richiesta dati ritorna json elaborato
	//impossibile gestire array perchè con le parentesi quadre errore di ricezione
	@RequestMapping(value="/data",produces = "application/json")
	public ArrayList<Doctor> data(@RequestParam Map<String,String> params)throws Exception {
		String jsonfilter=params.get("filter");
		if(jsonfilter==null) {  //controllo la presenza di filtri 
			return csv.get_doctors(); //se no ritorno tutti i dati
		}
		else {  //altrimenti controllo i filtri
			return new FiltersManager(jsonfilter, csv).getDoctorsResult();
		}
	}
	
	
	//gestisce le statische(solo count visto il dataset fornito)
	//vengono eseguite su json elaborato da eventuali filtri
	@RequestMapping(value="/stats",produces = "application/json")
	public HashMap<String, String> stat(@RequestParam Map<String,String> params)throws Exception {
		int count=data(params).size();  //richiamo il metodo data e conto il numero di elementi
		HashMap<String,String>result=new HashMap<String, String>();
		//genero la mappa con il field e il numero di ricorrenze
		result.put("field",params.get("field"));
		result.put("count",String.valueOf(count));
		return result;  
	}
	
	
	//costruttore
	public Controller() {
		/**
		 * initializes json and csv class
		 */
		//flusso inizializzazioni
		Json json=new Json("https://www.dati.gov.it/api/3/action/package_show?id=f9b81f42-9062-4caf-ba66-834123ced808"); //inizializzo il json
		System.out.println("csv @ "+json.get_url_csv()); //stampo l'url ottenuto dal json per il csv
		csv=new Csv(json);  //carico csv
		System.out.println("data acquired "+String.valueOf(csv.get_doctors().size())+" doctors"); //controllo dimensione file
		System.out.println("ready to request..."); //dichiaro che il programma è pronto per le richieste
	}
}
