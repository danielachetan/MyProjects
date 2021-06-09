package lab3.repository;

import lab3.model.Teacher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Implementiert ICrudRepository fur Lehrer, deren Daten in einer Datei gespeichert werden.
 */
public class TeacherFileRepository implements ICrudRepository<Teacher> {
    List<Teacher> teacherList;

    /**
     *GETTERS
     */

    /**
     * Gibt den Wert von teacherList zuruck.
     *
     * @return der Wert von teacherList
     */
    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    /**
     * SETTERS
     */

    /**
     * Gibt teacherList einen neuen Wert.
     *
     * @param teacherList der neue Wert von teacherList
     */
    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    /**
     * Liest die Daten der Lehrer aus einer Datei und schreibt sie in teacherList.
     */
    public void readFromFile() {
        try {
            File f = new File("C:\\MAP\\lab3\\src\\lab3\\repository\\Teachers");
            Scanner sc = new Scanner(f);
            teacherList = new ArrayList<Teacher>();
            long teacherId;
            String firstName;
            String lastName;
            while (sc.hasNext()) {
                teacherId = Long.parseLong(sc.next());
                firstName = sc.next();
                lastName = sc.next();
                Teacher t = new Teacher(teacherId, firstName, lastName);
                getTeacherList().add(t);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Die angegebene Datei wurde nicht gefunden!");
        }
    }

    /**
     * Schreibt in einer Datei die Daten der Lehrer aus teacherList.
     */
    public void writeInFile() {
        try {
            Formatter f = new Formatter("C:\\MAP\\lab3\\src\\lab3\\repository\\Teachers");
            for (int i = 0; i < getTeacherList().size(); i++)
                f.format("%s %s %s %s", getTeacherList().get(i).getTeacherId(), getTeacherList().get(i).getFirstName(),
                        getTeacherList().get(i).getLastName(), "\r\n");
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("Die angegebene Datei wurde nicht gefunden!");
        }
    }

    @Override
    public Teacher findOne(Long id) {
        readFromFile();
        int i = 0;
        while (i < getTeacherList().size() && getTeacherList().get(i).getTeacherId() != id)
            i++;
        if (i == getTeacherList().size())
            return null;
        return getTeacherList().get(i);
    }

    @Override
    public Iterator<Teacher> findAll() {
        readFromFile();
        return getTeacherList().iterator();
    }

    @Override
    public Teacher save(Teacher entity) {
        readFromFile();
        if (findOne(entity.getTeacherId()) == null) {
            getTeacherList().add(entity);
            writeInFile();
            return null;
        }
        return entity;
    }

    @Override
    public Teacher delete(Long id) {
        readFromFile();
        if (findOne(id) == null)
            return null;
        int i = 0;
        while (getTeacherList().get(i).getTeacherId() != id)
            i++;
        Teacher deleted = getTeacherList().remove(i);
        writeInFile();
        return deleted;
    }

    @Override
    public Teacher update(Teacher entity) {
        readFromFile();
        if (findOne(entity.getTeacherId()) == null)
            return entity;
        int i = 0;
        while (getTeacherList().get(i).getTeacherId() != entity.getTeacherId())
            i++;
        getTeacherList().set(i, entity);
        writeInFile();
        return null;
    }
}
