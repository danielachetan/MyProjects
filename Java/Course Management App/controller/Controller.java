package lab3.controller;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.ICrudRepository;

import java.sql.SQLException;
import java.util.*;
import java.lang.Integer;
import java.util.stream.Collectors;

/**
 * Verwendet die Methoden aus ICrudRepository, um neue Methoden zu implementieren.
 */
public class Controller {
    ICrudRepository<Student> studentRepo;
    ICrudRepository<Teacher> teacherRepo;
    ICrudRepository<Course> courseRepo;

    public Controller() {
    }

    ;

    public Controller(ICrudRepository<Student> studentRepo, ICrudRepository<Teacher> teacherRepo,
                      ICrudRepository<Course> courseRepo) {
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.courseRepo = courseRepo;
    }

    /**
     *GETTERS
     */

    /**
     * Gibt den Wert von studentRepo zuruck.
     *
     * @return der Wert von studentRepo
     */
    public ICrudRepository<Student> getStudentRepo() {
        return studentRepo;
    }

    /**
     * Gibt den Wert von teacherRepo zuruck.
     *
     * @return der Wert von teacherRepo
     */
    public ICrudRepository<Teacher> getTeacherRepo() {
        return teacherRepo;
    }

    /**
     * Gibt den Wert von courseRepo zuruck.
     *
     * @return der Wert von courseRepo
     */
    public ICrudRepository<Course> getCourseRepo() {
        return courseRepo;
    }

    /**
     *SETTERS
     */

    /**
     * Gibt studentRepo einen neuen Wert.
     *
     * @param studentRepo der neue Wert von studentRepo
     */
    public void setStudentRepo(ICrudRepository<Student> studentRepo) {
        this.studentRepo = studentRepo;
    }

    /**
     * Gibt teacherRepo einen neuen Wert.
     *
     * @param teacherRepo der neue Wert von teacherRepo
     */
    public void setTeacherRepo(ICrudRepository<Teacher> teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    /**
     * Gibt courseRepo einen neuen Wert.
     *
     * @param courseRepo der neue Wert von courseRepo
     */
    public void setCourseRepo(ICrudRepository<Course> courseRepo) {
        this.courseRepo = courseRepo;
    }

    /**
     * Uberpruft, ob ein Student in einem Kurs eingeschrieben ist.
     *
     * @param course  der Kurs, der uberpruft wird
     * @param student der Student, der uberpruft wird
     * @return true, wenn der Student eingeschrieben ist, false sonst
     */
    public boolean registered(Course course, Student student) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (getCourseRepo().findOne(course.getCourseId()) != null &&
                getStudentRepo().findOne(student.getStudentId()) != null &&
                course.getStudentsEnrolled().contains(student.getStudentId()) &&
                student.getEnrolledCourses().contains(course.getCourseId()))
            return true;
        return false;
    }

    /**
     * Gibt alle Kurse, die noch freie Platze haben, zuruck.
     *
     * @return eine Liste mit Kurse
     */
    /*
    public List<Course> retrieveCoursesWithFreePlaces() {
        Course course;
        List<Course> list = new ArrayList<Course>();
        Iterator<Course> it = getCourseRepo().findAll();
        while (it.hasNext()) {
            course = it.next();
            if (course.getMaxEnrollment() > course.getStudentsEnrolled().size())
                list.add(course);
        }
        return list;
    }
    */

    public List<Course> retrieveCoursesWithFreePlaces() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Course> list = new ArrayList<Course>();
        Iterator<Course> it = getCourseRepo().findAll();
        it.forEachRemaining(c -> list.add(c));
        List<Course> finalList = list.stream()
                .filter(c -> c.getMaxEnrollment() > c.getStudentsEnrolled().size())
                .collect(Collectors.toList());
        return finalList;
    }

    /**
     * Gibt alle Studenten, die in einem Kurs eingeschrieben sind, zuruck.
     *
     * @param course der Kurs
     * @return eine Liste von Studenten
     */
    /*
    public List<Student> retrieveStudentsEnrolledForACourse(Course course) {
        Student student;
        List<Student> list = new ArrayList<Student>();
        Iterator<Student> it = getStudentRepo().findAll();
        while (it.hasNext()) {
            student = it.next();
            if (registered(course, student))
                list.add(student);
        }
        return list;
    }
     */

    public List<Student> retrieveStudentsEnrolledForACourse(Course course) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Student> list = new ArrayList<Student>();
        Iterator<Student> it = getStudentRepo().findAll();
        it.forEachRemaining(st -> list.add(st));
        List<Student> finalList = list.stream()
                .filter(st -> {
                    try {
                        return registered(course,st);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toList());
        return finalList;
    }

    /**
     * Gibt alle Kurse zuruck.
     *
     * @return eine Liste mit allen Kursen
     */
    /*
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<Course>();
        Iterator<Course> it = getCourseRepo().findAll();
        while (it.hasNext())
            list.add(it.next());
        return list;
    }
     */

    public List<Course> getAllCourses() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Course> list = new ArrayList<Course>();
        Iterator<Course> it = getCourseRepo().findAll();
        it.forEachRemaining(c -> list.add(c));
        return list;
    }

    /**
     * Schreibt ein Student in einem Kurs ein.
     *
     * @param course  der Kurs, in dem der Student eingeschrieben wird
     * @param student der Student, der im Kurs eingeschrieben wird
     * @return true, wenn der Student eingeschrieben wurde oder false, wenn dieser schon eingeschrieben ist
     * @throws CannotRegisterException wenn der Student schon in zu vielen Kurse eingeschrieben ist oder wenn der
     *                                 Kurs keine freien Platze mehr hat
     */
    public boolean register(Course course, Student student) throws CannotRegisterException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (registered(course, student))
            return false;
        if (course.getStudentsEnrolled().size() == course.getMaxEnrollment())
            throw new CannotRegisterException();
        if (student.getTotalCredits() + course.getCredits() > 30)
            throw new CannotRegisterException();
        Course c = course;
        Student s = student;
        c.getStudentsEnrolled().add(student.getStudentId());
        getCourseRepo().update(c);
        s.getEnrolledCourses().add(course.getCourseId());
        s.setTotalCredits(s.getTotalCredits() + c.getCredits());
        getStudentRepo().update(s);
        return true;
    }

    /**
     * Sortiert absteigend die Liste von Studenten nach der Gesamtsumme der Kredite.
     *
     * @return eine Liste von Studenten
     */
    /*
    public List<Student> sortStudentByNumberOfCourses() {
        List<Student> list = new ArrayList<Student>();
        Iterator<Student> it = getStudentRepo().findAll();
        while (it.hasNext())
            list.add(it.next());
        Student aux;
        for (int i = 0; i < list.size() - 1; i++)
            for (int j = i + 1; j < list.size(); j++)
                if (list.get(i).getTotalCredits() < list.get(j).getTotalCredits()) {
                    aux = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, aux);
                }
        return list;
    }
     */

    public List<Student> sortStudentByNumberOfCourses() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Student> list = new ArrayList<Student>();
        Iterator<Student> it = getStudentRepo().findAll();
        it.forEachRemaining(st -> list.add(st));
        Comparator<Student> compareByCredits = (Student s1,Student s2) -> s1.compareTo(s2);
        Collections.sort(list,compareByCredits);
        return list;
    }

    /**
     * Gibt die Kurse, die von einem Lehrer unterrichtet werden, zuruck.
     *
     * @param teacherId der Lehrer, der die bestimmten Kurse unterrichtet
     * @return eine Liste von Kurse
     * @throws CannotFilterException wenn der Lehrer nicht existiert
     */
    /*
    public List<Course> filterCoursesByTeacher(long teacherId) throws CannotFilterException {
        if (getTeacherRepo().findOne(teacherId) == null)
            throw new CannotFilterException();
        else {
            Course course;
            List<Course> list = new ArrayList<Course>();
            Iterator<Course> it = getCourseRepo().findAll();
            while (it.hasNext()) {
                course = it.next();
                if (course.getTeacher() == teacherId)
                    list.add(course);
            }
            return list;
        }
    }
     */

    public List<Course> filterCoursesByTeacher(long teacherId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (getTeacherRepo().findOne(teacherId) == null)
            throw new CannotFilterException();
        else {
            List<Course> list = new ArrayList<Course>();
            Iterator<Course> it = getCourseRepo().findAll();
            it.forEachRemaining(c -> list.add(c));
            List<Course> finalList = list.stream()
                    .filter(c -> teacherId == c.getTeacher())
                    .collect(Collectors.toList());
            return finalList;
        }
    }

    public Student addStudent(Student student) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        return studentRepo.save(student);
    }

    public Student deleteStudent(long studentId) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        return studentRepo.delete(studentId);
    }

    public Teacher addTeacher(Teacher teacher) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        return teacherRepo.save(teacher);
    }

    public Teacher deleteTeacher(long teacherId) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        return teacherRepo.delete(teacherId);
    }

    public Course addCourse(Course course) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        return courseRepo.save(course);
    }

    public Course deleteCourse(long courseId) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        return courseRepo.delete(courseId);
    }

    public Course findOneCourse(long courseId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        return courseRepo.findOne(courseId);
    }

    public long checkPerson(String firstName,String lastName) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Student> studentList = new ArrayList<Student>();
        Iterator<Student> itS = getStudentRepo().findAll();
        itS.forEachRemaining(s -> studentList.add(s));
        Student student = studentList.stream()
                .filter(s -> s.getFirstName() == firstName && s.getLastName() == lastName)
                .findFirst()
                .get();
        long studentId = student.getStudentId();
        if (studentId != 0)
            return studentId;
        List<Teacher> teacherList = new ArrayList<Teacher>();
        Iterator<Teacher> itT = getTeacherRepo().findAll();
        itT.forEachRemaining(t -> teacherList.add(t));
        Teacher teacher = teacherList.stream()
                .filter(t -> t.getFirstName() == firstName && t.getLastName() == lastName)
                .findFirst()
                .get();
        long teacherId = teacher.getTeacherId();
        return teacherId;
    }

    public long findStudentId(String firstName,String lastName) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Student> list = new ArrayList<Student>();
        Iterator<Student> it = getStudentRepo().findAll();
        it.forEachRemaining(st -> list.add(st));
        boolean valid = list.stream()
                .anyMatch(s -> s.getFirstName().equals(firstName) && s.getLastName().equals(lastName));
        if (!valid)
            return 0;
        Student student = list.stream()
                .filter(s -> s.getFirstName().equals(firstName) && s.getLastName().equals(lastName))
                .findAny()
                .get();
        long studentId = student.getStudentId();
        return studentId;
    }

    public long findTeacherId(String firstName,String lastName) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Teacher> list = new ArrayList<Teacher>();
        Iterator<Teacher> it = getTeacherRepo().findAll();
        it.forEachRemaining(t -> list.add(t));
        boolean valid = list.stream()
                .anyMatch(t -> t.getFirstName().equals(firstName) && t.getLastName().equals(lastName));
        if (!valid)
            return 0;
        Teacher teacher = list.stream()
                .filter(t -> t.getFirstName().equals(firstName) && t.getLastName().equals(lastName))
                .findAny()
                .get();
        return teacher.getTeacherId();
    }

    public Student findOneStudent(long id) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        return studentRepo.findOne(id);
    }

    public Teacher findOneTeacher(long id) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        return teacherRepo.findOne(id);
    }

}
