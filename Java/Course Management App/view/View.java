package lab3.view;

import lab3.controller.CannotFilterException;
import lab3.controller.CannotRegisterException;
import lab3.controller.Controller;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class View {
    Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public List<Student> copyStudentList() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Student> list = new ArrayList<Student>();
        Iterator<Student> it = controller.getStudentRepo().findAll();
        while (it.hasNext())
            list.add(it.next());
        return list;
    }

    public List<Teacher> copyTeacherList() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Teacher> list = new ArrayList<Teacher>();
        Iterator<Teacher> it = controller.getTeacherRepo().findAll();
        while (it.hasNext())
            list.add(it.next());
        return list;
    }

    public void printStudents(List<Student> studentList) {
        for (int i = 0; i < studentList.size(); i++) {
            String student = String.valueOf(studentList.get(i).getStudentId()) + " " +
                    studentList.get(i).getFirstname() + " " + studentList.get(i).getLastName() + " " +
                    String.valueOf(studentList.get(i).getTotalCredits()) + " ";
            String auxEnrolledCourses = "";
            for (int j = 0; j < studentList.get(i).getEnrolledCourses().size(); j++)
                auxEnrolledCourses += String.valueOf(studentList.get(i).getEnrolledCourses().get(j)) + ",";
            if (auxEnrolledCourses != "")
                auxEnrolledCourses = auxEnrolledCourses.substring(0, auxEnrolledCourses.length() - 1);
            student += auxEnrolledCourses;
            System.out.println(student);
        }
    }

    public void printTeachers(List<Teacher> teacherList) {
        for (int i = 0; i < teacherList.size(); i++) {
            String teacher = String.valueOf(teacherList.get(i).getTeacherId()) + " " +
                    teacherList.get(i).getFirstName() + " " + teacherList.get(i).getLastName();
            System.out.println(teacher);
        }
    }

    public void printCourses(List<Course> courseList) {
        for (int i = 0; i < courseList.size(); i++) {
            String course = String.valueOf(courseList.get(i).getCourseId()) + " " + courseList.get(i).getName() +
                    " " + String.valueOf(courseList.get(i).getTeacher()) + " " +
                    String.valueOf(courseList.get(i).getMaxEnrollment()) + " " +
                    String.valueOf(courseList.get(i).getCredits()) + " ";
            String auxStudentsEnrolled = "";
            for (int j = 0; j < courseList.get(i).getStudentsEnrolled().size(); j++)
                auxStudentsEnrolled += String.valueOf(courseList.get(i).getStudentsEnrolled().get(j) + ",");
            if (auxStudentsEnrolled != "")
                auxStudentsEnrolled = auxStudentsEnrolled.substring(0, auxStudentsEnrolled.length() - 1);
            course += auxStudentsEnrolled;
            System.out.println(course);
        }
    }

    public void printMenu() {
        System.out.println("Wahlen Sie den Befehl aus dem Menu:");
        System.out.println("1 - Ein Student in einem Kurs einschreiben");
        System.out.println("2 - Anzeigen der Kurse, die noch freie Platze haben");
        System.out.println("3 - Anzeigen der Studenten, die in einem Kurs eingeschrieben sind");
        System.out.println("4 - Anzeigen aller Kurse");
        System.out.println("5 - Absteigendes Sortieren der Studenten nach Anzahl der Kredite");
        System.out.println("6 - Anzeigen der Kurse, die von einem Lehrer unterrichtet werden");
        System.out.println("7 - Einfugen eines Studenten");
        System.out.println("8 - Einfugen eines Lehrers");
        System.out.println("9 - Einfugen eines Kurses");
        System.out.println("10 - Loschen eines Kurses von einem Lehrer");
        System.out.println("11 - Exit");
    }

    public void menu() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Scanner s = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Wahlen Sie den Index des gewahlten Befehls: ");
            int inp = Integer.parseInt(s.next());
            if (inp == 1) {
                printStudents(copyStudentList());
                System.out.println("Geben Sie den Id des Studenten ein: ");
                long studentId = Long.parseLong(s.next());
                Student student = controller.getStudentRepo().findOne(studentId);
                printCourses(controller.getAllCourses());
                System.out.println("Geben Sie den Id des Kurses ein: ");
                long courseId = Long.parseLong(s.next());
                Course course = controller.getCourseRepo().findOne(courseId);
                try {
                    boolean b = controller.register(course, student);
                } catch (CannotRegisterException e) {
                    System.out.println("Der Student kann fur diesen Kurs nicht eingeschrieben werden");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (inp == 2) {
                System.out.println("Die Kurse, die noch freie Platze haben, sind: ");
                List<Course> liste = controller.retrieveCoursesWithFreePlaces();
                printCourses(liste);
            } else if (inp == 3) {
                printCourses(controller.getAllCourses());
                System.out.println("Geben Sie den Id des Kurses ein: ");
                long id = Long.parseLong(s.next());
                Course course = controller.getCourseRepo().findOne(id);
                List<Student> list = controller.retrieveStudentsEnrolledForACourse(course);
                System.out.println("Die Studenten, die in diesem Kurs eingeschrieben sind, sind: ");
                printStudents(list);
            } else if (inp == 4) {
                System.out.println("Alle Kurse sind: ");
                List<Course> list = controller.getAllCourses();
                printCourses(list);
            } else if (inp == 5) {
                System.out.println("Die Studenten, sortiert nach der Anzahl von Kredite, sind: ");
                List<Student> list = controller.sortStudentByNumberOfCourses();
                printStudents(list);
            } else if (inp == 6) {
                printTeachers(copyTeacherList());
                System.out.println("Geben Sie den Id eines Lehrers ein: ");
                long teacher = Long.parseLong(s.next());
                try {
                    List<Course> list = controller.filterCoursesByTeacher(teacher);
                    System.out.println("Die Kurse, die von diesem Lehrer unterrichtet sind, sind: ");
                    printCourses(list);
                } catch (CannotFilterException e) {
                    System.out.println("Der eingegebene Lehrer existiert nicht!");
                }
            } else if (inp == 7) {
                printStudents(copyStudentList());
                System.out.println("Geben Sie den Id des neuen Studenten ein:");
                long studentId = Long.parseLong(s.next());
                System.out.println("Geben Sie den Vornamen des neuen Studenten ein:");
                String firstName = s.next();
                System.out.println("Geben Sie den Nachnamen des neuen Studenten ein:");
                String lastName = s.next();
                Student student = new Student(studentId, firstName, lastName);
                controller.addStudent(student);
            } else if (inp == 8) {
                printTeachers(copyTeacherList());
                System.out.println("Geben Sie den Id des neuen Lehrers ein:");
                long teacherId = Long.parseLong(s.next());
                System.out.println("Geben Sie den Vornamen des neuen Lehrers ein:");
                String firstName = s.next();
                System.out.println("Geben Sie den Nachnamen des neuen Lehrers ein:");
                String lastName = s.next();
                Teacher teacher = new Teacher(teacherId, firstName, lastName);
                controller.addTeacher(teacher);
            } else if (inp == 9) {
                printCourses(controller.getAllCourses());
                System.out.println("Geben Sie den Id des neuen Kurses ein:");
                long courseId = Long.parseLong(s.next());
                System.out.println("Geben Sie den Namen des neuen Kurses ein:");
                String name = s.next();
                printTeachers(copyTeacherList());
                System.out.println("Geben Sie den Id des Lehrers, der diesen Kurs unterrichtet, ein:");
                long teacher = Long.parseLong(s.next());
                System.out.println("Geben Sie die maximale Anzahl von Platze fur diesen Kurs ein:");
                int maxEnrollment = Integer.parseInt(s.next());
                System.out.println("Geben Sie die Anzahl von Kredite fur diesen Kurs ein:");
                int credits = Integer.parseInt(s.next());
                Course course = new Course(courseId, name, teacher, maxEnrollment, credits);
                controller.addCourse(course);
            } else if (inp == 10) {
                printTeachers(copyTeacherList());
                System.out.println("Geben Sie Ihren Id ein:");
                long teacherId = Long.parseLong(s.next());
                try {
                    List<Course> list = controller.filterCoursesByTeacher(teacherId);
                    System.out.println("Die Kurse, die von diesem Lehrer unterrichtet sind, sind: ");
                    printCourses(list);
                } catch (CannotFilterException e) {
                    System.out.println("Der eingegebene Lehrer existiert nicht!");
                }
                System.out.println("Geben Sie den Id des Kurses, den Sie loschen wollen, ein:");
                long courseId = Long.parseLong(s.next());
                Course course = controller.findOneCourse(courseId);
                if (course.getTeacher() == teacherId)
                    controller.deleteCourse(courseId);
                else
                    System.out.println("Sie konnen diesen Kurs nicht loschen!");
            } else if (inp == 11)
                break;
            else
                System.out.println("Das gewahlte Befehl befindet sich nicht im Menu!");
        }
    }
}
