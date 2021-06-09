package lab3.repository;

import lab3.model.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Implementiert ICrudRepository fur Studenten, deren Daten in einer Datei gespeichert werden.
 */
public class StudentFileRepository implements ICrudRepository<Student> {
    List<Student> studentList;

    /**
     *GETTERS
     */

    /**
     * Gibt den Wert von studentList zuruck.
     *
     * @return der Wert von studentList
     */
    public List<Student> getStudentList() {
        return studentList;
    }

    /**
     *SETTERS
     */

    /**
     * Gibt studentList einen neuen Wert.
     *
     * @param studentList der neue Wert von studentList
     */
    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    /**
     * Liest die Daten der Studenten aus einer Datei und schreibt sie in studentList.
     */
    public void readFromFile() {
        try {
            studentList = new ArrayList<Student>();
            File file = new File("C:\\MAP\\lab3\\src\\lab3\\repository\\Students");
            Scanner sc = new Scanner(file);
            long studentId;
            String firstName;
            String lastName;
            int totalCredits;
            while (sc.hasNext()) {
                List<Long> enrolledCourses = new ArrayList<Long>();
                List<String> auxList;
                studentId = Long.parseLong(sc.next());
                firstName = sc.next();
                lastName = sc.next();
                totalCredits = Integer.parseInt(sc.next());
                auxList = new ArrayList<String>(Arrays.asList(sc.next().split(",")));
                for (int i = 0; i < auxList.size(); i++)
                    enrolledCourses.add(Long.parseLong(auxList.get(i)));
                Student s = new Student(studentId, firstName, lastName, totalCredits, enrolledCourses);
                studentList.add(s);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Die angegebene Datei wurde nicht gefunden!");
        }
    }

    /**
     * Schreibt in einer Datei die Daten der Studenten aus studentList.
     */
    public void writeInFile() {
        try {
            Formatter form = new Formatter("C:\\MAP\\lab3\\src\\lab3\\repository\\Students");
            for (int i = 0; i < getStudentList().size(); i++) {
                String auxStudentList = "";
                for (int j = 0; j < getStudentList().get(i).getEnrolledCourses().size(); j++)
                    auxStudentList += String.valueOf(getStudentList().get(i).getEnrolledCourses().get(j)) + ",";
                auxStudentList = auxStudentList.substring(0, auxStudentList.length() - 1);
                form.format("%s %s %s %s %s %s", getStudentList().get(i).getStudentId(),
                        getStudentList().get(i).getFirstName(), getStudentList().get(i).getLastName(),
                        getStudentList().get(i).getTotalCredits(), auxStudentList, "\r\n");
            }
            form.close();
        } catch (FileNotFoundException e) {
            System.out.println("Die angegebene Datei wurde nicht gefunden!");
        }
    }

    @Override
    public Student findOne(Long id) {
        readFromFile();
        int i = 0;
        while (i < getStudentList().size() && getStudentList().get(i).getStudentId() != id)
            i++;
        if (i == getStudentList().size())
            return null;
        return getStudentList().get(i);
    }

    @Override
    public Iterator<Student> findAll() {
        readFromFile();
        return getStudentList().iterator();
    }

    @Override
    public Student save(Student entity) {
        readFromFile();
        if (findOne(entity.getStudentId()) == null) {
            getStudentList().add(entity);
            writeInFile();
            return null;
        }
        return entity;
    }

    @Override
    public Student delete(Long id) {
        readFromFile();
        if (findOne(id) == null)
            return null;
        int i = 0;
        while (getStudentList().get(i).getStudentId() != id)
            i++;
        Student deleted = getStudentList().remove(i);
        writeInFile();
        return deleted;
    }

    @Override
    public Student update(Student entity) {
        readFromFile();
        if (findOne(entity.getStudentId()) == null)
            return entity;
        int i = 0;
        while (getStudentList().get(i).getStudentId() != entity.getStudentId())
            i++;
        getStudentList().set(i, entity);
        writeInFile();
        return null;
    }
}
