package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frage {
    int id;
    String question;
    String varianteA;
    String varianteB;
    String varianteC;
    String bild;
    String antwort;

    public Frage(String line) {
        List<String> auxList = new ArrayList<String>(Arrays.asList(line.split("; ")));
        //System.out.println(auxList);
        id = Integer.parseInt(auxList.get(0));
        question = auxList.get(1);
        varianteA = auxList.get(2);
        varianteB = auxList.get(3);
        varianteC = auxList.get(4);
        bild = auxList.get(5);
        antwort = auxList.get(6);
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getVarianteA() {
        return varianteA;
    }

    public String getVarianteB() {
        return varianteB;
    }

    public String getVarianteC() {
        return varianteC;
    }

    public String getBild() {
        return bild;
    }

    public String getAntwort() {
        return antwort;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setVarianteA(String varianteA) {
        this.varianteA = varianteA;
    }

    public void setVarianteB(String varianteB) {
        this.varianteB = varianteB;
    }

    public void setVarianteC(String varianteC) {
        this.varianteC = varianteC;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }

    public void setAntwort(String antwort) {
        this.antwort = antwort;
    }
}
