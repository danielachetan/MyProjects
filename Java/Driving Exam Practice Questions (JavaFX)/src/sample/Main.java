package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class Main extends Application {
    public Controller ctrl = new Controller();
    public List<Frage> list = ctrl.getAllQuestions();

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

    public FlowPane createRoot(Frage frage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox mainBox = new VBox();
        int[] richtig = {0};
        int[] falsch = {0};
        Text text1 = new Text("Raspunsuri corecte: " + String.valueOf(richtig[0]));
        text1.setFill(Color.GREEN);
        text1.setFont(new Font(20));
        mainBox.getChildren().add(text1);
        Text text2 = new Text("Raspunsuri gresite: " + String.valueOf(falsch[0]) + "\n\n");
        text2.setFill(Color.RED);
        text2.setFont(new Font(20));
        mainBox.getChildren().add(text2);
        Text text3 = new Text();
        text3.setFont(new Font(20));
        String t = frage.getQuestion() + "\n\n";
        t += "a) " + frage.getVarianteA() + "\n";
        t += "b) " + frage.getVarianteB() + "\n";
        t += "c) " + frage.getVarianteC() + "\n\n";
        text3.setText(t);
        mainBox.getChildren().add(text3);
        Image image = new Image(frage.getBild(), 300, 300, true, true);
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
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (button1.isSelected())
                    ans[0] = "a";
                else
                    ans[0] = "";
                if (button2.isSelected())
                    ans[1] = "b";
                else
                    ans[1] = "";
                if (button3.isSelected())
                    ans[2] = "c";
                else
                    ans[2] = "";
                String answer = ans[0] + ans[1] + ans[2];
                ctrl.checkAnswer(answer,frage);
                richtig[0] = ctrl.getRichtig();
                falsch[0] = ctrl.getFalsch();
            }
        });
        root.getChildren().add(mainBox);
        return root;
    }


    public void changeScene(Stage stage, Button button, FlowPane root) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(new Scene(root, 575, 500));
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(String.valueOf("Examen auto"));
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
        startBox.getChildren().add(startButton);
        startRoot.getChildren().add(startBox);
        Scene startScene;
        startScene = new Scene(startRoot, 575, 500);
        FlowPane firstRoot = createRoot(list.get(0));
        Button button = new Button("Trimite raspunsul");
        firstRoot.getChildren().add(button);
        Scene firstScene = new Scene(firstRoot, 575, 500);
        startButton.setOnAction(e -> primaryStage.setScene(firstScene));
        primaryStage.setScene(startScene);
        Button prevButton;
        int i = 0;
        while (i < 24 && ctrl.getFalsch() != 5) {
            prevButton = button;
            FlowPane root = createRoot(list.get(i + 1));
            button = new Button("Trimite raspunsul");
            root.getChildren().add(button);
            changeScene(primaryStage, prevButton, root);
            i++;
        }
        FlowPane finalRoot = new FlowPane();
        finalRoot.setPadding(new Insets(10));
        finalRoot.setHgap(10);
        finalRoot.setVgap(10);
        VBox finalBox = new VBox();
        Text finalText;
        if (ctrl.getFalsch() == 5) {
            finalText = new Text("Ati picat testul :(");
            finalText.setFill(Color.RED);
        } else {
            finalText = new Text("Felicitari! Ati trecut testul!");
            finalText.setFill(Color.GREEN);
        }
        finalText.setFont(new Font(40));
        finalBox.getChildren().add(finalText);
        finalRoot.getChildren().add(finalBox);
        changeScene(primaryStage, button, finalRoot);
        //primaryStage.setScene(new Scene(finalRoot,575,500));
        primaryStage.show();
         */
        Ui ui = new Ui();
        ui.startScene(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
