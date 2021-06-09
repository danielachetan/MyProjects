package interfaces;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BenutzerTest {
    public List<Sport> testList1() {
        //Erstellt eine Liste<Sport> Liste.
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
    public void kalkuliereZeit() {
        Benutzer benutzer = new Benutzer();
        benutzer.setVorName("A");
        benutzer.setNachName("B");
        List<Sport> liste = testList1();
        benutzer.setSport(liste);
        Assert.assertEquals(310, benutzer.kalkuliereZeit(), 0);
        Basketball b = new Basketball();
        Assert.assertEquals(165, benutzer.kalkuliereZeit(b), 0);
        Fussball f = new Fussball();
        Assert.assertEquals(65, benutzer.kalkuliereZeit(f), 0);
        Hindernislauf hi = new Hindernislauf();
        Assert.assertEquals(60, benutzer.kalkuliereZeit(hi), 0);
        Hochsprung ho = new Hochsprung();
        Assert.assertEquals(20, benutzer.kalkuliereZeit(ho), 0);
    }


    @Test
    public void kalkuliereDurchschnittszeit() {
        Benutzer benutzer = new Benutzer();
        benutzer.setVorName("A");
        benutzer.setNachName("B");
        List<Sport> liste = testList1();
        benutzer.setSport(liste);
        Assert.assertEquals(44.2, benutzer.kalkuliereDurchschnittszeit(), 0.1);
    }
}