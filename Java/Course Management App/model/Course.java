package lab3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Stellt ein Kurs dar.
 */
public class Course extends Observable {
    long courseId;
    String name;
    long teacher;
    int maxEnrollment;
    int credits;
    List<Long> studentsEnrolled;

    public Course(long courseId) {
        this.courseId = courseId;
    }

    public Course(long courseId, String name, long teacher, int maxEnrollment, int credits, List<Long> studentsEnrolled) {
        this.courseId = courseId;
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.credits = credits;
        this.studentsEnrolled = studentsEnrolled;
    }

    public Course(long courseId, String name, long teacher, int maxEnrollment, int credits) {
        this.courseId = courseId;
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.credits = credits;
        this.studentsEnrolled = new ArrayList<Long>();
    }

    /**
     * GETTERS
     */

    /**
     * Gibt den Wert von name zuruck.
     *
     * @return der Wert von name
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt den Wert von teacher zuruck.
     *
     * @return der Wert von teacher
     */
    public long getTeacher() {
        return teacher;
    }

    /**
     * Gibt den Wert von maxEnrollment zuruck.
     *
     * @return der Wert von maxEnrollment
     */
    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    /**
     * Gibt den Wert von studentsEnrolled zuruck.
     *
     * @return der Wert von studentsEnrolled
     */
    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    /**
     * Gibt den Wert von credits zuruck.
     *
     * @return der Wert von credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Gibt den Wert von courseId zuruck.
     *
     * @return der Wert von courseId
     */
    public long getCourseId() {
        return courseId;
    }


    /**
     * SETTERS
     */

    /**
     * Gibt name einen neuen Wert.
     *
     * @param name der neue Wert von name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt teacher einen neuen Wert.
     *
     * @param teacher der neue Wert von teacher
     */
    public void setTeacher(long teacher) {
        this.teacher = teacher;
    }

    /**
     * Gibt maxEnrollment einen neuen Wert.
     *
     * @param maxEnrollment der neue Wert von maxEnrollment
     */
    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    /**
     * Gibt studentsEnrolled einen neuen Wert.
     *
     * @param studentsEnrolled der neue Wert von studentsEnrolled
     */
    public void setStudentsEnrolled(List<Long> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    /**
     * Gibt credits einen neuen Wert.
     *
     * @param credits der neue Wert von credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Gibt courseId einen neuen Wert.
     *
     * @param courseId der neue Wert von courseId
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
