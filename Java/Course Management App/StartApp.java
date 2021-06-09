package lab3;

import lab3.controller.Controller;
import lab3.repository.*;
import lab3.view.View;

import java.sql.SQLException;

/**
 * Main class where program starts.
 */
public class StartApp {

    /**
     * Start point of the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Start point");
        ICrudRepository studentRepo = new StudentJdbcRepository();
        ICrudRepository teacherRepo = new TeacherJdbcRepository();
        ICrudRepository courseRepo = new CourseJdbcRepository();
        Controller controller = new Controller(studentRepo, teacherRepo, courseRepo);
        View view = new View(controller);
        view.menu();
    }
}
