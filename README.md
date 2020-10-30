# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering
Mappeeksamen for kandidat 283
Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Angående warnings:
Non-ASCII characters in an identifier :11 :
     Den ene noden kalles høyre fra oppgaven,som ikke er ASCII standard, så denne har j
     eg bare latt være siden de 
     som har laget eksamen har laget den.
Private field 'endringer' is assigned but never accessed : 36
     int endringer ser jeg ikke at vi skal bruke noe sted, men siden den var der fra før 
     så lar jeg den være.
Method 'inneholder(T)' is never used :47
    Det samme med inneholder metoden, som ovenfor, ikke benyttet.
Return value of the method is never used: 84
  legginn return value blir ikke brukt, siden den returnerer true, men legger inn    
  verdier i treet.
    
Non-ASCII characters in an identifier: 249
  Førstepostorden har ø i seg som ikke er ASCII standard, den er laget av de som har 
  laget eksamen så lar den ligge.
  
Det er totalt 5 warnings som jeg ikke har gjort noe med.

Jeg har brukt git til å dokumentere arbeidet mitt, jeg har 23 commits totalt, og hver logg-melding forklarer hva jeg har gjort. 

 * Oppgave 1:  Her har jeg benyttet kompendie slik som beskrevet i oppgaveteksten. 
               Sjekker om det er nullverdier som skal legges inn, dette er ikke lov,
               også benyttes while løkke så lenge p ikke er lik null så oppdateres p.
               hvis q er lik null så blir p rotnode, til slutt oppdateres antall.

 * Oppgave 2: Lager først en node kalt "denne" som settes til rot, lager også en 
              hjelpevariabel kalt antallet som skal telle hvor mange ganger tallet     
              repeteres., Bruker en while løkke for å sammenligne verdiene, bruker
              compare funksjonen for å sjekke om verdien finnes. Øker antallet om den 
              returnerer 0 (som vil si at den er lik)
             
 * Oppgave 3: Her har jeg benyttet kompendie 5.1.7 Først lagen en node som settes til 
              p.forelder og så lenge p ikke er null så sjekker man om p.venstre ikke er 
              null, så oppdateres p til p.venstre hvis høyre ikke er null oppdateres p 
              til p.høyre ellers returneres p og da er det førstePostorden som   
              returneres. nestePostorden er det brukt en rekke if tester som sjekker 
              hvilke barn p er. også sjekker en while løkke om p.venstre eller p.høyre 
              er null, der p oppdateres om p.venstre ikke er null til p.venstre, ellers 
              p til p.høyre. til slutt returneres neste postorden.
            
 
 * Oppgave 4: Lager først en node som peker til rot, gjør så p blir førstePostorden med 
              metoden beskrevet i forrige oppgave.  så lenge p ikke er null så kjører 
              oppgave.utføreOppgave(p.verdi) og gjør "noe" med hver enkelt verdi helt 
              til p blir null og bryter ut av while løkken,.
              postordenRecursive har jeg benyttet kompendie som inspirasjon 5.1.10 der 
              det sto om preorden og modifisert den slik at den blir postorden
 
 * Oppgave 5: Her lager jeg først et hjeloearray som til slutt skal returneres. Videre    
              lages en Queue og bruke ArrayDeque for å få en stack. Først legges roten i 
              køen. videre så brukes en While løkke som kjører så lenge køen ikke er 
              tom. Der legges først nodenførstikøen sin verdi, hvis node venstre ikke er 
              null legges den til så legges node høyre til om den ikke er null. dette 
              gjøres helt til køen er tom, derfor legges det også i nivåorden.
              Deserialize benytter jeg kompendie programkode 5.2.3 som inspirasjon.
              benytter komparatoren og legginn metoden med lambda uttrykk for å lage 
              treet, så returneres treet.
            
 * Oppgave 6: Her benyttes kompendie som beskrevet  for fjern metoden, har endret litt 
              på navn og litt på syntaks, lagt til slik at foreldrer noden får riktig 
              verdi i alle noder.I fjernAlle har jeg laget en teller som starter på 0. 
              Benytter en while løkke, og så lenge verdien som man skal fjerne finnes i 
              treet finnes så kjører den, der er det en booelan som sjekker om verdien 
              blir fjernet eller ikke, hvis den ikke blefjernet så går man ut av løkken, 
              blir den fjernet så øker antallfjernet telleren. Returnerer antallFjernet. 
              i nullstill har jeg benyttet meg av samme teknikk som i serialize metoden, 
              men lagt til at man oppdaterer nodeførstikøen.forelder .høyre og .venstre 
              til null helt til køen er tom. Helt til sllutt oppdateres rot til null og 
              antall til 0.
