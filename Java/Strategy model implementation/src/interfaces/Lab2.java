package interfaces;

import java.util.ArrayList;
import java.util.List;

class Lab2 {
    public static void main(String[] args) {
        Benutzer benutzer = new Benutzer();
        benutzer.setVorName("X");
        benutzer.setNachName("Y");
        List<Sport> l = new ArrayList();
        Basketball b = new Basketball();
        l.add(b);
        Fussball f = new Fussball();
        l.add(f);
        Hindernislauf hi = new Hindernislauf();
        l.add(hi);
        Hochsprung ho = new Hochsprung();
        l.add(ho);
        benutzer.setSport(l);
        double kZ1 = benutzer.kalkuliereZeit();
        System.out.println("Gesamtzeit:");
        System.out.println(kZ1);
        double kZ2 = benutzer.kalkuliereZeit(b);
        System.out.println("Basketball:");
        System.out.println(kZ2);
        double kZ3 = benutzer.kalkuliereZeit(f);
        System.out.println("Fussball:");
        System.out.println(kZ3);
        double kZ4 = benutzer.kalkuliereZeit(hi);
        System.out.println("Hindernislauf:");
        System.out.println(kZ4);
        double kZ5 = benutzer.kalkuliereZeit(ho);
        System.out.println("Hochsprung:");
        System.out.println(kZ5);
        double kZ6 = benutzer.kalkuliereDurchschnittszeit();
        System.out.println("Durchschnitt:");
        System.out.println(kZ6);
    }
}