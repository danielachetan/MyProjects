package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {
    Repository repo;
    public int richtig;
    public int falsch;

    public Controller() {
        this.repo = new Repository();
        richtig = 0;
        falsch = 0;
    }

    public Repository getRepo() {
        return repo;
    }

    public int getRichtig() {
        return richtig;
    }

    public int getFalsch() {
        return falsch;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    public void setRichtig(int richtig) {
        this.richtig = richtig;
    }

    public void setFalsch(int falsch) {
        this.falsch = falsch;
    }

    public String checkIfButtonsPressed(RadioButton b1, RadioButton b2, RadioButton b3) {
        String ans = "";
        if (b1.isSelected())
            ans += "a";
        if (b2.isSelected())
            ans += "b";
        if (b3.isSelected())
            ans += "c";
        return ans;
    }

    public void checkAnswer(String antwort, Frage frage) {
        if (antwort.equals(frage.getAntwort()))
            setRichtig(getRichtig() + 1);
        else
            setFalsch(getFalsch() + 1);
    }

    public List<Frage> getAllQuestions() {
        List<Frage> list = new ArrayList<Frage>();
        Iterator<Frage> it = getRepo().findAll();
        while (it.hasNext())
            list.add(it.next());
        return list;
    }
}
