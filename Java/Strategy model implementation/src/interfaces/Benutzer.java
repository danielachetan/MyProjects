package interfaces;

import java.util.List;

public class Benutzer {
    String vorName;
    String nachName;
    List<Sport> sport;

    public String getVorName() {
        return vorName;
    }

    public String getNachName() {
        return nachName;
    }

    public List<Sport> getSport() {
        return sport;
    }

    public void setVorName(String vorName) {
        this.vorName = vorName;
    }

    public void setNachName(String nachName) {
        this.nachName = nachName;
    }

    public void setSport(List<Sport> sport) {
        this.sport = sport;
    }

    /**
     * Berechnet die Gesamtzeit, in der ein Benutzer alle seine Lieblingssportarten getrieben hat.
     *
     * @return die Gesamtzeit, in der ein Benutzer alle seine Lieblingssportarten getrieben hat
     */
    public double kalkuliereZeit() {
        double sum = 0;
        for (int i = 0; i < getSport().size(); i++)
            sum += getSport().get(i).kalkuliereZeit();
        return sum;
    }

    /**
     * Berechnet die Gesamtzeit, in der ein Benutzer eine bestimmte Sportart getrieben hat.
     *
     * @param sp ist die gewahlte Sportart
     * @return die Gesamtzeit, in der ein Benutzer eine bestimmte Sportart getrieben hat
     */
    public double kalkuliereZeit(Sport sp) {
        double anz = 0;
        for (int i = 0; i < getSport().size(); i++)
            if (getSport().get(i).kalkuliereZeit() == sp.kalkuliereZeit())
                anz++;
        return anz * sp.kalkuliereZeit();
    }

    /**
     * Berechnet die Durchschnittszeit, in der ein Benutzer Sport getrieben hat.
     *
     * @return die Durchschnittszeit, in der ein Benutzer Sport getrieben hat
     */
    public double kalkuliereDurchschnittszeit() {
        return kalkuliereZeit() / getSport().size();
    }
}