package lab3;

import javafx.application.Application;
import javafx.stage.Stage;
import lab3.controller.Controller;
import lab3.repository.CourseJdbcRepository;
import lab3.repository.ICrudRepository;
import lab3.repository.StudentJdbcRepository;
import lab3.repository.TeacherJdbcRepository;
import lab3.view.Lab7_View;

public class Lab7 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ICrudRepository studentRepo = new StudentJdbcRepository();
        ICrudRepository teacherRepo = new TeacherJdbcRepository();
        ICrudRepository courseRepo = new CourseJdbcRepository();
        Controller ctrl = new Controller(studentRepo,teacherRepo,courseRepo);
        Lab7_View view = new Lab7_View(ctrl);
        view.startScene(primaryStage);
    }
}
