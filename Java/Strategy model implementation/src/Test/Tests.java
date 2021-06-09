package Test;

import interfaces.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;


class Tests {

    public List<Sport> testList1() {
        List<Sport> liste = new ArrayList();
        Basketball b1 = new Basketball();
        liste.add(b1);
        Fussball f1 = new Fussball();
        liste.add(f1);
        Hindernislauf hi1 = new Hindernislauf();
        liste.add(hi1);
        Hindernislauf hi2 = new Hindernislauf();
        liste.add(hi2);
        Basketball b2 = new Basketball();
        liste.add(b2);
        Hochsprung ho1 = new Hochsprung();
        liste.add(ho1);
        Basketball b3 = new Basketball();
        liste.add(b3);
        return liste;
    }

    @Test
    public void testvalidData() {
        Benutzer benutzer = new Benutzer();
        benutzer.setVorName("A");
        benutzer.setNachName("B");
        List<Sport> liste = testList1();
        benutzer.setSport(liste);
        Assert.assertEquals(benutzer.getVorName(), "A");
        Assert.assertEquals(benutzer.getNachName(), "B");
    }

    @Test
    public void testKalkuliereZeit() {
        Benutzer benutzer = new Benutzer();
        benutzer.setVorName("A");
        benutzer.setNachName("B");
        List<Sport> liste = testList1();
        benutzer.setSport(liste);
        Assert.assertEquals(benutzer.kalkuliereZeit(), 310);
        Basketball b = new Basketball();
        Assert.assertEquals(benutzer.kalkuliereZeit(b), 165);
        Fussball f = new Fussball();
        Assert.assertEquals(benutzer.kalkuliereZeit(f), 65);
        Hindernislauf hi = new Hindernislauf();
        Assert.assertEquals(benutzer.kalkuliereZeit(hi), 60);
        Hochsprung ho = new Hochsprung();
        Assert.assertEquals(benutzer.kalkuliereZeit(ho), 20);
    }

    @Test
    public void testKalkuliereDurchschnittszeit() {
        Benutzer benutzer = new Benutzer();
        benutzer.setVorName("A");
        benutzer.setNachName("B");
        List<Sport> liste = testList1();
        benutzer.setSport(liste);
        Assert.assertEquals(benutzer.kalkuliereDurchschnittszeit(), 44.2);
    }
}