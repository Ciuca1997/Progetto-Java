package programma;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	//@RequestMapping("/data") mappo get e parametri in RequestPAram
	//mappo il posto e i dati stanno in RequestBody
	/*@RequestMapping(value="/data",consumes ="application/json")
	public void data() {
		System.out.println();
	}*/
	public ArrayList<Doctor> filter(ArrayList<Doctor>doctors,String string_filter){
		
		return doctors;
	}
	public Controller() {
		//Json json=new Json("https://www.dati.gov.it/api/3/action/package_show?id=f9b81f42-9062-4caf-ba66-834123ced808");
		//System.out.println(json.get_url_csv());
		//Csv csv=new Csv(json);
	}
}
