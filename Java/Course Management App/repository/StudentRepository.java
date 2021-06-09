package lab3.repository;

import lab3.model.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementiert ICrudRepository fur Studenten.
 */
public class StudentRepository implements ICrudRepository<Student> {
    List<Student> studentList;

    public StudentRepository() {
        studentList = new ArrayList<Student>();
    }

    /**
     * GETTERS
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
     * SETTERS
     */

    /**
     * Gibt studentList einen neuen Wert.
     *
     * @param studentList der neue Wert von studentList
     */
    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public Student findOne(Long id) {
        int i = 0;
        while (i < studentList.size() && studentList.get(i).getStudentId() != id)
            i++;
        if (i == studentList.size())
            return null;
        return studentList.get(i);
    }

    @Override
    public Iterator<Student> findAll() {
        return studentList.iterator();
    }

    @Override
    public Student save(Student entity) {
        if (findOne(entity.getStudentId()) == null) {
            studentList.add(entity);
            return null;
        }
        return entity;
    }

    @Override
    public Student delete(Long id) {
        if (findOne(id) == null)
            return null;
        int i = 0;
        while (studentList.get(i).getStudentId() != id)
            i++;
        return studentList.remove(i);
    }

    @Override
    public Student update(Student entity) {
        if (findOne(entity.getStudentId()) == null)
            return entity;
        int i = 0;
        while (studentList.get(i).getStudentId() != entity.getStudentId())
            i++;
        studentList.set(i, entity);
        return null;
    }
}
