package lab3.repository;

import lab3.model.Course;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementiert ICrudRepository fur Kurse.
 */
public class CourseRepository implements ICrudRepository<Course> {
    List<Course> courseList;

    public CourseRepository() {
        courseList = new ArrayList<Course>();
    }

    /**
     * GETTERS
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
     * SETTERS
     */

    /**
     * Gibt courseList einen neuen Wert.
     *
     * @param courseList der neue Wert von courseList
     */
    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public Course findOne(Long id) {
        int i = 0;
        while (i < courseList.size() && courseList.get(i).getCourseId() != id)
            i++;
        if (i == courseList.size())
            return null;
        return courseList.get(i);
    }

    @Override
    public Iterator<Course> findAll() {
        return courseList.iterator();
    }

    @Override
    public Course save(Course entity) {
        if (findOne(entity.getCourseId()) == null) {
            courseList.add(entity);
            return null;
        }
        return entity;
    }

    @Override
    public Course delete(Long id) {
        if (findOne(id) == null)
            return null;
        int i = 0;
        while (courseList.get(i).getCourseId() != id)
            i++;
        return courseList.remove(i);
    }

    @Override
    public Course update(Course entity) {
        if (findOne(entity.getCourseId()) == null)
            return entity;
        int i = 0;
        while (courseList.get(i).getCourseId() != entity.getCourseId())
            i++;
        courseList.set(i, entity);
        return null;
    }
}
