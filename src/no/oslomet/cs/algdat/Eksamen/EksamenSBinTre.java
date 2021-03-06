package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {

    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        // Her er mye inspirasjon hentet fra kompendie som beskrevet i oppgaven
        Objects.requireNonNull(verdi, "Ikke lov med nullverdier");

        Node<T> p = rot, q = null;  // p starter i roten
        int sammenligning = 0; // hjelpevariabel

        while (p != null) {
            q = p;                                           // q er forelder til p
            sammenligning = comp.compare(verdi, p.verdi); // bruker komparatoren
            p = sammenligning < 0 ? p.venstre : p.høyre; // flytter p
        }

        // p er nå null, dvs ute av treet, q er den siste vi passerte

        p = new Node<>(verdi, q); // oppretter ny node    Oppdaterer foreldrenode

        if (q == null) {
            rot = p; // p blir rotnode
        } else if (sammenligning < 0) {
            q.venstre = p; // venstre barn til q oppdateres til p hvis sammenligningen er negativ
        } else {           // Ellers oppdateres p til q.høyre
            q.høyre = p;
        }
        antall++;
        endringer++;
        return true;
    }


    public boolean fjern(T verdi) {
        // benytter kompendie 5.2.8 som beskrevet i oppgaveteksten
        // Skriver om litt, liker å bruke {} på if sjekker for eksempel.
        if (verdi == null) {
            return false;
        }

        Node<T> p = rot, parent = null; // bytter navn på enkelte varibaler for syntes det blir
        // mer oversiktlig

        while (p != null) {
            int sammenligning = comp.compare(verdi, p.verdi);
            if (sammenligning < 0) {
                parent = p;
                p = p.venstre;
            } else if (sammenligning > 0) {
                parent = p;
                p = p.høyre;
            } else break; // Fant verdien man skal slette
        }
        if (p == null) {
            return false;
        }

        // Vi skal fjerne p. Må oppdatere pekerne til barna til p med ny peker.
        if (p.venstre == null || p.høyre == null) {
            Node<T> barn = p.venstre != null ? p.venstre : p.høyre;
            if (barn != null) { // oppdaterer forelder hvis den man sletter har barn.
                barn.forelder = parent;
            }
            if (p == rot) {
                rot = barn;
            } else if (p == parent.venstre) {
                parent.venstre = barn;
            } else {
                parent.høyre = barn;
            }
        } else {
            Node<T> s = p,
                    r = p.høyre;

            while (r.venstre != null) {
                s = r;
                r = r.venstre;
            }
            p.verdi = r.verdi;

            if (s != p) {
                s.venstre = r.høyre;
            } else {
                s.høyre = r.høyre;
            }
        }
        antall--;
        endringer++;
        return true;
    }

    public int fjernAlle(T verdi) {

        int antallFjernet = 0; // Lager en "teller"
        while (true) { // så lenge den valgte verdien er finnes i treet fjernes det.
            boolean bleFjernet = fjern(verdi); // forsøker å fjerne verdien, returnerer om det gikk eller ikke. Går det ikke er det ikke flere verdier.
            if (!bleFjernet) {
                break;
            } else {
                antallFjernet++;   // øker antallet når en verdi blir fjernet
            }
        }
        return antallFjernet; // returner antall som er  fjernet

    }


    public int antall(T verdi) {
        Node<T> denne = rot; // setter starten i rotnoden
        int antallet = 0; // hjelpevariabel som teller


        while (denne != null) { // så lenge denne ikke er null
            int sammenligner = comp.compare(verdi, denne.verdi); // Bruker compare for å sammenligne
            if (sammenligner < 0) {
                denne = denne.venstre;
            } // så lenge verdi er mindre enn denne verdi

            else {
                if (sammenligner == 0) { // hvis tallet sammenlignes er like
                    antallet++;  // Øker verdien på antallet variablen.
                }

                denne = denne.høyre; // oppdaterer denne noden.
            }

        }

        return antallet;  // returnerer antallet man finner av den aktuelle noden
    }


    public void nullstill() {
        // Benytter meg av samme teknikk som i serialize oppgaven

        Queue<Node<T>> queue = new ArrayDeque<>(); // lager en kø
        if (rot == null) {
            return;
        }
        queue.add(rot); // Setter roten først i køen

        while (!queue.isEmpty()) { // så lenge nodefoerstikoen ikke er null kjører løkken.
            Node<T> nodefoerstIKoen = queue.remove();
            if (nodefoerstIKoen.venstre != null) {
                queue.add(nodefoerstIKoen.venstre); // så lenge venstrebarn ikke er null legges det i køen
            }
            if (nodefoerstIKoen.høyre != null) {
                queue.add(nodefoerstIKoen.høyre);  // så lenge høyrebarn ikke er null legges det i køen
            }
            nodefoerstIKoen.forelder = null;  // oppdaterer forelder til node til null
            nodefoerstIKoen.høyre = null; // oppdaterer høyre barn til node til null
            nodefoerstIKoen.venstre = null;  // oppdaterer venstre barn til node til null
        }
        rot = null;  // Oppdaterer rot til null
        antall = 0; // setter antall til 0
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        // hentet insoirasjon fra kompendie 5.1.7
        Node<T> q = p.forelder; // setter q til å være p.forelder

        if (q != null) { // sjekker om p er null, hvis ikke så er p forelder.
            p = q;
        }

        while (true) {  // så lenge p ikke er null går man gjennom treet
            if (p.venstre != null) // når p.venstre ikke er null
            {
                p = p.venstre;
            }         // så settes p til p.venstre
            else if (p.høyre != null) {
                p = p.høyre;
            }// når p.høyre ikek er null settes p til p.høyre
            else {
                return p;
            }        // til slutt returneres p som første Post orden
            // siden p venstre er null så må p.høyre være første post orden.

        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        if (p.forelder == null) { // sjekker at p.forelder ikke er null
            return null;
        } else {
            if (p == p.forelder.høyre) { // sjekker p er høyrebarn
                p = p.forelder; // oppdaterer p
            } else {
                if (p.forelder.høyre == null) {// sjekker om høyrebarn finnes
                    p = p.forelder;
                } else {
                    p = p.forelder.høyre;
                    while (p.venstre != null || p.høyre != null) { // sjekker om barna ikke er null,
                        // testene under sjekker hvilket av de som evnt ikke er null
                        if (p.venstre != null) {
                            p = p.venstre;
                        } else {
                            p = p.høyre;
                        }
                    }
                }
            }

        }

        return p;  // returnerer nestepostorden
    }


    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot;
        p = førstePostorden(p); // gjør så p blir første noden postorden
        while (p != null) { // så lenge ikke p er null så kjører loopen
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p); // oppdaterer p helt til den blir null og bryter ut av løkka
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        // brukt traversering uten rekursjon 5.1.10 i kompendie der det sto om preorden
        // endret rekkefølge så man går igjennom postorden, altså satt metoden utførOppgave til slutt.
        if (p.venstre != null) postordenRecursive(p.venstre, oppgave);
        if (p.høyre != null) postordenRecursive(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi); // denne må stå til slutt siden det er postorden vi skal
        // gjennomføre her.

    }

    public ArrayList<T> serialize() {
        ArrayList<T> elementer = new ArrayList<>();

        Queue<Node<T>> queue = new ArrayDeque<>(); // lager en kø
        queue.add(rot); // Setter roten først i køen


        while (!queue.isEmpty()) { // bryter ut når køen er tom
            Node<T> nodefoerstIKoen = queue.remove();
            elementer.add(nodefoerstIKoen.verdi); // Legger inn verdien til noden
            if (nodefoerstIKoen.venstre != null) {
                queue.add(nodefoerstIKoen.venstre); // så lenge venstrebarn ikke er null legges det i køen
            }
            if (nodefoerstIKoen.høyre != null) {
                queue.add(nodefoerstIKoen.høyre);  // så lenge høyrebarn ikke er null legges det i køen
            }
        }
        return elementer;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        // Benyttet kompendie programkode 5.2.3 som inspirasjon.
        EksamenSBinTre<K> tre = new EksamenSBinTre<>(c);  // Komparatoren
        data.forEach(tre::leggInn); // lager treet
        return tre;  // returnerer treet i nivåorden

    }


} // ObligSBinTre

