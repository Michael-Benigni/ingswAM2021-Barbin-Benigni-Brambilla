# ing-sw-2021-Barbin-Benigni-Brambilla
Progetto del corso Ingegneria del Software 2021.

Progetto di ingegneria del software

Funzionalità implementate:
- CLI
  -GUI
- match in single player (regole complete)
- partite multiple
- resilienza alle disconnessioni

Avvio:
C’è un unico jar che può essere lanciato con dei parametri da riga di comando per lanciare il client:
-s seguito dall’indirizzo ip del server a cui connettersi
-p seguito dal numero della porta a cui connettersi al server
- - cli o - - gui per far scegliere la tipologia di client

Compatibilità:
-il client funziona correttamente come cli su shell in wsl, o MacOS, non su terminali Windows che non supportano caratteri UNICODE
