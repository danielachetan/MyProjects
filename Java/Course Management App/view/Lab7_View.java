package lab3.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lab3.controller.CannotRegisterException;
import lab3.controller.Controller;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;

import java.sql.SQLException;
import java.util.List;

import static javafx.scene.paint.Color.*;

public class Lab7_View {
    Controller ctrl;
    String person = "";
    String firstName = "";
    String lastName = "";
    long auxId = 0;

    public Lab7_View(Controller ctrl) {
        this.ctrl = ctrl;
    }

    public void startScene(Stage stage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox box = new VBox();
        Text text1 = new Text("Login\n");
        text1.setFont(new Font(60));
        text1.setFill(CADETBLUE);
        box.getChildren().add(text1);
        Button button1 = new Button();
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                person = "student";
                loginScene(stage);
            }
        });
        Label label1 = new Label(" Als Student",button1);
        label1.setFont(new Font(40));
        label1.setTextFill(DARKORCHID);
        box.getChildren().add(label1);
        Text text2 = new Text("\n");
        box.getChildren().add(text2);
        Button button2 = new Button();
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                person = "teacher";
                Stage newStage = new Stage();
                loginScene(newStage);
            }
        });
        Label label2 = new Label(" Als Lehrer",button2);
        label2.setFont(new Font(40));
        label2.setTextFill(DARKORCHID);
        box.getChildren().add(label2);
        root.getChildren().add(box);
        Scene scene;
        scene = new Scene(root,900,600);
        stage.setScene(scene);
        stage.show();
    }

    public void loginScene(Stage stage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox box = new VBox();
        HBox box1 = new HBox();
        Label label1 = new Label("Vorame: ");
        label1.setFont(new Font(40));
        box1.getChildren().add(label1);
        TextField textField1 = new TextField();
        box1.getChildren().add(textField1);
        box1.setSpacing(10);
        box.getChildren().add(box1);
        Text text1 = new Text("\n");
        box.getChildren().add(text1);
        HBox box2 = new HBox();
        Label label2 = new Label("Name: ");
        label2.setFont(new Font(40));
        box2.getChildren().add(label2);
        TextField textField2 = new TextField();
        box2.getChildren().add(textField2);
        box2.setSpacing(10);
        box.getChildren().add(box2);
        Text text2 = new Text("\n");
        box.getChildren().add(text2);
        Button button = new Button("Next");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                firstName = textField1.getText();
                lastName = textField2.getText();
                try {
                    checkPerson(stage);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        box.getChildren().add(button);
        root.getChildren().add(box);
        Scene scene = new Scene(root,900,600);
        stage.setScene(scene);
        stage.show();
    }

    public void studentMenu(Stage stage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox box = new VBox();
        Text text1 = new Text(firstName + " " + lastName + "\n");
        text1.setFont(new Font(60));
        box.getChildren().add(text1);
        Button button1 = new Button();
        button1.setOnAction(e -> {
            try {
                register(stage);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        Label label1 = new Label(" Schreiben Sie sich fur ein Kurs ein",button1);
        label1.setFont(new Font(35));
        box.getChildren().add(label1);
        Text text2 = new Text("\n");
        box.getChildren().add(text2);
        Button button2 = new Button();
        button2.setOnAction(e -> {
            try {
                showCredits(stage);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        Label label2 = new Label(" Sehen Sie sich Ihre gesamte Anzahl von Credits an",button2);
        label2.setFont(new Font(35));
        box.getChildren().add(label2);
        Text text3 = new Text("\n");
        box.getChildren().add(text3);
        Button button3 = new Button();
        button3.setOnAction(e -> startScene(stage));
        Label label3 = new Label(" Zuruck",button3);
        label3.setFont(new Font(35));
        box.getChildren().add(label3);
        root.getChildren().add(box);
        Scene scene = new Scene(root,900,600);
        stage.setScene(scene);
        stage.show();
    }

    public void teacherMenu(Stage stage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox box = new VBox();
        Text text1 = new Text(firstName + " " + lastName + "\n");
        text1.setFont(new Font(60));
        box.getChildren().add(text1);
        Button button1 = new Button();
        button1.setOnAction(e -> {
            try {
                chooseCourse(stage);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        Label label1 = new Label(" Sehen Sie die Studenten, die fur einen Kurs \n eingeschrieben sind",button1);
        label1.setFont(new Font(35));
        box.getChildren().add(label1);
        Text text2 = new Text("\n");
        box.getChildren().add(text2);
        Button button2 = new Button();
        button2.setOnAction(e -> startScene(stage));
        Label label2 = new Label(" Zuruck",button2);
        label2.setFont(new Font(35));
        box.getChildren().add(label2);
        root.getChildren().add(box);
        Scene scene = new Scene(root,900,600);
        stage.setScene(scene);
        stage.show();
    }

    public void checkPerson(Stage stage) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (person.equals("student") && ctrl.findStudentId(firstName,lastName) != 0)
            studentMenu(stage);
        else if (person.equals("teacher") && ctrl.findTeacherId(firstName,lastName) != 0)
            teacherMenu(stage);
        else
            startScene(stage);
    }

    public void showCredits(Stage stage) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox box = new VBox();
        Text text1 = new Text(firstName + " " + lastName + "\n");
        text1.setFont(new Font(60));
        box.getChildren().add(text1);
        Text text2 = new Text("Ihre gesamte Anzahl von Credits ist:\n");
        text2.setFont(new Font(35));
        box.getChildren().add(text2);
        Text text3 = new Text(String.valueOf(ctrl.findOneStudent(ctrl.findStudentId(firstName,lastName)).getTotalCredits()) + "\n");
        text3.setFont(new Font(80));
        box.getChildren().add(text3);
        Button button = new Button("Zuruck");
        button.setOnAction(e -> studentMenu(stage));
        box.getChildren().add(button);
        root.getChildren().add(box);
        Scene scene = new Scene(root,900,600);
        stage.setScene(scene);
        stage.show();
    }

    public String displayStudents(List<Student> list) {
        String rez = "";
        for (int i = 0; i < list.size(); i++) {
            String auxStudentList = "";
            for (int j = 0; j < list.get(i).getEnrolledCourses().size(); j++)
                auxStudentList += String.valueOf(list.get(i).getEnrolledCourses().get(j)) + ",";
            auxStudentList = auxStudentList.substring(0, auxStudentList.length() - 1);
            rez += String.valueOf(list.get(i).getStudentId()) + " " +
                    list.get(i).getFirstName() + " " + list.get(i).getLastName() + " " +
                    String.valueOf(list.get(i).getTotalCredits()) + " " + auxStudentList + "\n";
        }
        return rez;
    }

    public String displayTeachers(List<Teacher> list) {
        String rez = "";
        for (int i = 0; i < list.size(); i++)
            rez += String.valueOf(list.get(i).getTeacherId()) + " " + list.get(i).getFirstName() + " " +
                    list.get(i).getLastName() + "\n";
        return rez;
    }

    public String displayCourses(List<Course> list) {
        String rez = "";
        for (int i = 0; i < list.size(); i++) {
            String auxCourseList = "";
            for (int j = 0; j < list.get(i).getStudentsEnrolled().size(); j++)
                auxCourseList += String.valueOf(list.get(i).getStudentsEnrolled().get(j)) + ",";
            auxCourseList = auxCourseList.substring(0, auxCourseList.length() - 1);
            rez += String.valueOf(list.get(i).getCourseId()) + " " + list.get(i).getName() + " " +
                    String.valueOf(list.get(i).getTeacher()) + " " + String.valueOf(list.get(i).getMaxEnrollment()) + " " +
                    String.valueOf(list.get(i).getCredits()) + " " + auxCourseList + "\n";
        }
        return rez;
    }

    public void chooseCourse(Stage stage) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox box = new VBox();
        Text text1 = new Text(firstName + " " + lastName + "\n");
        text1.setFont(new Font(60));
        box.getChildren().add(text1);
        Text text2 = new Text("Die Kurse, die Sie unterrichten, sind:\n");
        text2.setFont(new Font(30));
        box.getChildren().add(text2);
        Text text3 = new Text(displayCourses(ctrl.filterCoursesByTeacher(ctrl.findTeacherId(firstName,lastName))) + "\n");
        text3.setFont(new Font(30));
        box.getChildren().add(text3);
        Label label = new Label("Geben Sie den Id eines Kurses ein:");
        box.getChildren().add(label);
        TextField textField = new TextField();
        box.getChildren().add(textField);
        Text text4 = new Text("\n");
        box.getChildren().add(text4);
        Button button = new Button("Next");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                auxId = Long.parseLong(textField.getText());
                try {
                    showStudents(stage);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        box.getChildren().add(button);
        root.getChildren().add(box);
        Scene scene = new Scene(root,900,600);
        stage.setScene(scene);
        stage.show();
    }

    public void showStudents(Stage stage) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox box = new VBox();
        Text text1 = new Text(firstName + " " + lastName + "\n");
        text1.setFont(new Font(60));
        box.getChildren().add(text1);
        Text text2 = new Text("Die Studenten, die fur diesen Kurs eingeschrieben sind, sind:\n");
        text2.setFont(new Font(30));
        box.getChildren().add(text2);
        Text text3 = new Text(displayStudents(ctrl.retrieveStudentsEnrolledForACourse(ctrl.findOneCourse(auxId))) + "\n");
        text3.setFont(new Font(30));
        box.getChildren().add(text3);
        Button button = new Button("Zuruck");
        button.setOnAction(e -> teacherMenu(stage));
        box.getChildren().add(button);
        root.getChildren().add(box);
        Scene scene = new Scene(root,900,600);
        stage.setScene(scene);
        stage.show();
    }

    public void register(Stage stage) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);
        VBox box = new VBox();
        Text text1 = new Text(firstName + " " + lastName + "\n");
        text1.setFont(new Font(60));
        box.getChildren().add(text1);
        Text text2 = new Text(displayCourses(ctrl.getAllCourses()) + "\n");
        text2.setFont(new Font(30));
        box.getChildren().add(text2);
        Label label = new Label("Geben Sie den Id des Kurses, fur den Sie sich einschreiben wollen, ein:");
        box.getChildren().add(label);
        TextField textField = new TextField();
        box.getChildren().add(textField);
        Text text3 = new Text("\n");
        box.getChildren().add(text3);
        Button button = new Button("Next");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                auxId = Long.parseLong(textField.getText());
                try {
                    Student student = ctrl.findOneStudent(ctrl.findStudentId(firstName,lastName));
                    ctrl.register(ctrl.findOneCourse(auxId),student);
                    Teacher teacher = ctrl.findOneTeacher(ctrl.findOneCourse(auxId).getTeacher());
                    teacher.update(ctrl.findOneCourse(auxId),teacher);
                    studentMenu(stage);
                } catch (CannotRegisterException e) {
                    System.out.println("Sie konnen sich fur diesen Kurs nicht einschreiben");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
        box.getChildren().add(button);
        root.getChildren().add(box);
        Scene scene = new Scene(root,900,600);
        stage.setScene(scene);
        stage.show();
    }
}
