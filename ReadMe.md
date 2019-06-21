Progetto Programmazione ad oggetti

1)Descrizione: il progetto assegnatoci consiste di un programma capace di fare parsing ed elaborazione dati per poi restituirli. 
I passi essenziali dell'elaborazione sono:il creare un file locale json che punta alle risorse in formato csv,elaborare il csv incapsulando i dati in opportune strutture e restituirli quando richiesti dopo l'applicazione di opportune statistiche e filtri.

2)Struttura progetto:
Il progetto consiste in cinque classi di cui quattro operative ed una di test:
a)json (classe che gestisce il json)
b)csv (classe che gestisce il csv)
c)controller (classe che gestisce richieste ed esegue filtraggio e statistica)
d)doctor (classe utile all'incapsulamento dei dati inerenti ai dottori)
e)test_csv (classe non impiegata nel programma finale usata per testare le funzioni di lettura)

a)Richiede in ingresso l'url del file json e provvede se non presente alla creazione di una copia locale. Fatto ciò si occupa di estrarne l'url del csv che potrà essere estratto tramite opportuno metodo
b)Data in ingresso la classe json provvede ad estrarne l'url di interesse mediante opportuno metodo. Elabora la risorsa remota incapsulando i dati nelle classi dottore e cui si potrà accedere tramite metodo get.
c)Il controller istanzia le classi json e csv per poi definire tutte le callback che gestiscono le richieste post e get
d)doctor classe che provvede ad un unico metodo per la restituizione della hasmap al fine di caricarci i valori desiderati
e)classe non usata nel progetto finale ed utile solo in caso di test

-filtri implementati:
  in,not,gt,lt
-statistiche:
  count(unica applicabile per questo tipo di dataset)
  
-bug:
  impossibile gestire array di parametri causa errore non gestibile del rest controller in presenza di parentesi quadre
  impossibile gestire riavvio del server in caso di errore perchè non inseribile all'interno di un try,catch

