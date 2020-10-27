package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {

    public static void main(String[] args) {
        Integer[] a = {4, 7, 2, 9, 4, 1, 2};
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);


    }

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
        Objects.requireNonNull(verdi, "Ikke lov med nullverdier");

        Node<T> p = rot, q = null;  // p starter i roten
        int sammenligning = 0; // hjelpevariabel

        while (p != null) {
            q = p;                                           // q er forelder til p
            sammenligning = comp.compare(verdi, p.verdi); // bruker komparatoren
            p = sammenligning < 0 ? p.venstre : p.høyre; // flytter p
        }

        // p er nå null, dvs ute av treet, q er den siste vi passerte

        p = new Node<T>(verdi, q); // oppretter ny node    Oppdaterer foreldrenode

        if (q == null) {
            rot = p; // p blir rotnode
        } else if (sammenligning < 0) {
            q.venstre = p; // venstre barn til q
        } else {
            q.høyre = p;
        }
        antall++;
        return true;
    }


    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        Node<T> denne = rot;
        int antallet = 0;


        while (denne != null) {
            int sammenligner = comp.compare(verdi, denne.verdi);
            if (sammenligner < 0) denne = denne.venstre;

            else {
                if (sammenligner == 0) antallet++;

                denne = denne.høyre;
            }

        }

        return antallet;
    }


    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Node<T> q = p.forelder;

        if (q != null) { // sjekker om p er null, hvis ikke så er p forelder.
            p = q;
        }

        while (true) {  // så lenge p ikke er null går man gjennom treet
            if (p.venstre != null) // når p.venstre ikke er null
                p = p.venstre;          // så settes p til p.venstre
            else if (p.høyre != null) p = p.høyre; // når p.høyre ikek er null settes p til p.høyre
            else return p;         // til slutt returneres p som første Post orden
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
                    while (p.venstre != null || p.høyre != null) {
                        if (p.venstre != null) {
                            p = p.venstre;
                        } else {
                            p = p.høyre;
                        }
                    }
                }
            }

        }

        return p;
    }


    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot; // starter i roten slik at man får med hele "treet"
        p = førstePostorden(p);
        while(true){
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {

    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
