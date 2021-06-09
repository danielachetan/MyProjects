package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

import java.util.List;

public class Ui {
    Controller ctrl;
    List<Frage> list;
    int index;

    public Ui() {
        ctrl = new Controller();
        list = ctrl.getAllQuestions();
        index = 0;
    }

    public void startScene(Stage stage) {
        stage.setTitle(String.valueOf("Examen auto"));
        FlowPane startRoot = new FlowPane();
        startRoot.setPadding(new Insets(10));
        startRoot.setHgap(10);
        startRoot.setVgap(10);
        VBox startBox = new VBox();
        Text text1 = new Text("Incepeti testul\n");
        text1.setFont(new Font(40));
        text1.setFill(Color.GREEN);
        startBox.getChildren().add(text1);
        Button startButton = new Button("START");
        startButton.setOnAction(e -> createScene(stage));
        startBox.getChildren().add(startButton);
        startRoot.getChildren().add(startBox);
        Scene startScene;
        startScene = new Scene(startRoot, 575, 500);
        stage.setScene(startScene);
        stage.show();
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

    public void createScene(Stage stage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox mainBox = new VBox();
        Text text1 = new Text("Raspunsuri corecte: " + String.valueOf(ctrl.getRichtig()));
        text1.setFill(Color.GREEN);
        text1.setFont(new Font(20));
        mainBox.getChildren().add(text1);
        Text text2 = new Text("Raspunsuri gresite: " + String.valueOf(ctrl.getFalsch()) + "\n\n");
        text2.setFill(Color.RED);
        text2.setFont(new Font(20));
        mainBox.getChildren().add(text2);
        Text text3 = new Text();
        text3.setFont(new Font(20));
        String t = list.get(index).getQuestion() + "\n\n";
        t += "a) " + list.get(index).getVarianteA() + "\n";
        t += "b) " + list.get(index).getVarianteB() + "\n";
        t += "c) " + list.get(index).getVarianteC() + "\n\n";
        text3.setText(t);
        mainBox.getChildren().add(text3);
        Image image = new Image(list.get(index).getBild(), 300, 300, true, true);
        ImageView imageView = new ImageView(image);
        mainBox.getChildren().add(imageView);
        Text text4 = new Text("\n");
        mainBox.getChildren().add(text4);
        HBox auxBox = new HBox();
        RadioButton button1 = new RadioButton("A");
        auxBox.getChildren().add(button1);
        Text text5 = new Text(" ");
        auxBox.getChildren().add(text5);
        RadioButton button2 = new RadioButton("B");
        auxBox.getChildren().add(button2);
        Text text6 = new Text(" ");
        auxBox.getChildren().add(text6);
        RadioButton button3 = new RadioButton("C");
        auxBox.getChildren().add(button3);
        mainBox.getChildren().add(auxBox);
        Text text7 = new Text("\n");
        mainBox.getChildren().add(text7);
        Button button = new Button("Verificati raspunsul");
        mainBox.getChildren().add(button);
        String[] ans = new String[3];
        button.setOnAction(e -> ctrl.checkAnswer(checkIfButtonsPressed(button1,button2,button3),list.get(index)));
        root.getChildren().add(mainBox);
        Button nextButton = new Button("Trimite raspunsul");
        nextButton.setOnAction(e -> nextScene(stage));
        root.getChildren().add(nextButton);
        Scene scene = new Scene(root,575,500);
        stage.setScene(scene);
        stage.show();
    }

    public void endScene(Stage stage) {
        FlowPane finalRoot = new FlowPane();
        finalRoot.setPadding(new Insets(10));
        finalRoot.setHgap(10);
        finalRoot.setVgap(10);
        VBox finalBox = new VBox();
        Text finalText;
        if (ctrl.getFalsch() == 5) {
            finalText = new Text("Ej varza\nDUTE ACASA");
            finalText.setFill(Color.RED);
        } else {
            finalText = new Text("Felicitari! Ati trecut testul!");
            finalText.setFill(Color.GREEN);
        }
        finalText.setFont(new Font(40));
        finalBox.getChildren().add(finalText);
        finalRoot.getChildren().add(finalBox);
        Scene finalScene = new Scene(finalRoot,575,500);
        stage.setScene(finalScene);
        stage.show();
    }

    public void nextScene(Stage stage) {
        if (index < 26)
            if (index == 25)
                endScene(stage);
            else
                if (ctrl.getFalsch() == 5)
                    endScene(stage);
                else {
                    index++;
                    createScene(stage);
                }
    }
}
