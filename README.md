Il programma esegue analisi dati rispetto ad un riferimento json fornito.<br>
Il sorgente del programma è stato caricato in:[sorgente](https://github.com/Ciuca1997/Progetto-Java/tree/master/programma_spring/src/main/java/programma/).<br>
Degli esempi di richiesta sono:<br>
http://127.0.0.1:8080/data?filter={"CAP":{"$in":"84025"}}<br>
http://127.0.0.1:8080/data?filter={"NomeMedico":{"$nin":"MARCO"}}<br>
http://127.0.0.1:8080/data?filter={"NomeMedico":{"$nin":"MARCO"}}<br>
[http://127.0.0.1:8080/data?filter={"$and":[{"NomeMedico":"MARCO"},{"CAP":"84098"}]}](http://127.0.0.1:8080/data?filter=%7B%22%24and%22%3A%5B%7B%22NomeMedico%22%3A%22MARCO%22%7D%2C%7B%22CAP%22%3A%2284098%22%7D%5D%7D)<br>
La documentazione riguardo il codice sorgente e situata in:[documentazione](https://github.com/Ciuca1997/Progetto-Java/blob/master/doc/).<br>
Esso è stato organizzato secondo il seguente diagramma:<br>
![alt text](https://github.com/Ciuca1997/Progetto-Java/blob/master/Diagrmmi%20UML%20immagini/ClassDiagram.png)
Ed il flusso delle operazioni è stato organizzato nel seguente modo:<br>
![alt text](https://github.com/Ciuca1997/Progetto-Java/blob/master/Diagrmmi%20UML%20immagini/UseCaseDiagram.png)
![alt text](https://github.com/Ciuca1997/Progetto-Java/blob/master/Diagrmmi%20UML%20immagini/SequenceDiagram.png)
