Progetto Programmazione ad oggetti

1)Descrizione: il progetto consiste in un programma in grado di scaricare un file CSV tramite link ottenuto da un file Json scaricato anch'esso da internet. Inoltre il programma consentirà all'utente di richiedere statistiche campi ed altre informazioni dal file CSV tramite richieste di POST e GET.

2)Costruzione progetto:
Il progetto consiste in quattro componenti:
a)json (classe che gestisce il json)
b)csv (classe che gestisce il csv)
c)controller(classe che gestisce richieste )
d)flow_control(metodo che gestisce il flusso del programma)

a)La classe json ha il compito di scaricare e di ricercare il link al file CSV che dovrà poi essere analizzato. Essa è composta da:
il costruttore che si occupa del caching e della ricerca dell'url e del metodo di get per ritornare l'url del CSV.
b)La classe csv si occupa del download del file CSV , del suo parsing e del  mapping ,senza salvare il file in memoria.
c)La classe controller è la classe che si occupa della gestione delle richieste dell'utente.
d)Il flow_controller si occupa dell'inizializzazione delle classi.



