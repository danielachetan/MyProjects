package lab3.repository;

import lab3.model.Teacher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementiert ICrudRepository fur Lehrer.
 */
public class TeacherRepository implements ICrudRepository<Teacher> {
    List<Teacher> teacherList;

    public TeacherRepository() {
        teacherList = new ArrayList<Teacher>();
    }

    /**
     * GETTERS
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

    @Override
    public Teacher findOne(Long id) {
        int i = 0;
        while (i < teacherList.size() && teacherList.get(i).getTeacherId() != id)
            i++;
        if (i == teacherList.size())
            return null;
        return teacherList.get(i);
    }

    @Override
    public Iterator<Teacher> findAll() {
        return teacherList.iterator();
    }

    @Override
    public Teacher save(Teacher entity) {
        if (findOne(entity.getTeacherId()) == null) {
            teacherList.add(entity);
            return null;
        }
        return entity;
    }

    @Override
    public Teacher delete(Long id) {
        if (findOne(id) == null)
            return null;
        int i = 0;
        while (teacherList.get(i).getTeacherId() != id)
            i++;
        return teacherList.remove(i);
    }

    @Override
    public Teacher update(Teacher entity) {
        if (findOne(entity.getTeacherId()) == null)
            return entity;
        int i = 0;
        while (teacherList.get(i).getTeacherId() != entity.getTeacherId())
            i++;
        teacherList.set(i, entity);
        return null;
    }
}
