package lab3.repository;

import lab3.model.Course;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Implementiert ICrudRepository fur Kurse, deren Daten in einer Datei gespeichert werden.
 */
public class CourseFileRepository implements ICrudRepository<Course> {
    List<Course> courseList = new ArrayList<Course>();

    /**
     *GETTERS
     */

    /**
     * Gibt den Wert von courseList zuruck.
     *
     * @return der Wert von courseList
     */
    public List<Course> getCourseList() {
        return courseList;
    }

    /**
     *SETTERS
     */

    /**
     * Gibt courseList einen neuen Wert.
     *
     * @param courseList der neue Wert von courseList
     */
    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    /**
     * Liest die Daten von Kurse aus einer Datei und schreibt sie in courseList.
     */
    public void readFromFile() {
        try {
            File f = new File("C:\\MAP\\lab3\\src\\lab3\\repository\\Courses");
            Scanner sc = new Scanner(f);
            long courseId;
            String name;
            long teacher;
            int maxEnrollment;
            int credits;
            while (sc.hasNext()) {
                List<Long> studentsEnrolled = new ArrayList<Long>();
                List<String> auxList;
                courseId = Long.parseLong(sc.next());
                name = sc.next();
                teacher = Long.parseLong(sc.next());
                maxEnrollment = Integer.parseInt(sc.next());
                credits = Integer.parseInt(sc.next());
                auxList = new ArrayList<String>(Arrays.asList(sc.next().split(",")));
                for (int i = 0; i < auxList.size(); i++)
                    studentsEnrolled.add(Long.parseLong(auxList.get(i)));
                Course c = new Course(courseId, name, teacher, maxEnrollment, credits, studentsEnrolled);
                courseList.add(c);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Die angegebene Datei wurde nicht gefunden!");
        }
    }

    /**
     * Schreibt in einer Datei die Daten der Kurse aus courseList.
     */
    public void writeInFile() {
        try {
            Formatter f = new Formatter("C:\\MAP\\lab3\\src\\lab3\\repository\\Courses");
            for (int i = 0; i < getCourseList().size(); i++) {
                String auxCourseList = "";
                for (int j = 0; j < getCourseList().get(i).getStudentsEnrolled().size(); j++)
                    auxCourseList += String.valueOf(getCourseList().get(i).getStudentsEnrolled().get(j)) + ",";
                auxCourseList = auxCourseList.substring(0, auxCourseList.length() - 1);
                f.format("%s %s %s %s %s %s %s", getCourseList().get(i).getCourseId(), getCourseList().get(i).getName(),
                        getCourseList().get(i).getTeacher(), getCourseList().get(i).getMaxEnrollment(),
                        getCourseList().get(i).getCredits(), auxCourseList, "\r\n");
            }
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("Die angegebene Datei wurde nicht gefunden!");
        }
    }

    @Override
    public Course findOne(Long id) {
        readFromFile();
        int i = 0;
        while (i < getCourseList().size() && getCourseList().get(i).getCourseId() != id)
            i++;
        if (i == getCourseList().size())
            return null;
        return getCourseList().get(i);
    }

    @Override
    public Iterator<Course> findAll() {
        readFromFile();
        return getCourseList().iterator();
    }

    @Override
    public Course save(Course entity) {
        readFromFile();
        if (findOne(entity.getCourseId()) == null) {
            getCourseList().add(entity);
            writeInFile();
            return null;
        }
        return entity;
    }

    @Override
    public Course delete(Long id) {
        readFromFile();
        if (findOne(id) == null)
            return null;
        int i = 0;
        while (getCourseList().get(i).getCourseId() != id)
            i++;
        Course deleted = getCourseList().remove(i);
        writeInFile();
        return deleted;
    }

    @Override
    public Course update(Course entity) {
        readFromFile();
        if (findOne(entity.getCourseId()) == null)
            return entity;
        int i = 0;
        while (getCourseList().get(i).getCourseId() != entity.getCourseId())
            i++;
        getCourseList().set(i, entity);
        writeInFile();
        return null;
    }
}
