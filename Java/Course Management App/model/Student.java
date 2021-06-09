package lab3.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Stellt ein Student dar, der eine Unterklasse der Klasse 'Person' ist.
 */
public class Student extends Person implements Comparable<Student> {
    long studentId;
    int totalCredits;
    List<Long> enrolledCourses;

    public Student(long studentId) {
        this.studentId = studentId;
        super.firstName = "";
        super.lastName = "";
        totalCredits = 0;
        enrolledCourses = null;
    }

    public Student(long studentId, String firstName, String lastName, int totalCredits, List<Long> enrolledCourses) {
        this.studentId = studentId;
        super.firstName = firstName;
        super.lastName = lastName;
        this.totalCredits = totalCredits;
        this.enrolledCourses = enrolledCourses;
    }

    public Student(long studentId, String firstName, String lastName) {
        this.studentId = studentId;
        super.firstName = firstName;
        super.lastName = lastName;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<Long>();
    }

    /**
     * GETTERS
     */

    /**
     * Gibt den Wert studentId zuruck.
     *
     * @return der Wert von studentId
     */
    public long getStudentId() {
        return studentId;
    }

    /**
     * Gibt den Wert von totalCredits zuruck.
     *
     * @return der Wert von totalCredits
     */
    public int getTotalCredits() {
        return totalCredits;
    }

    /**
     * Gibt den Wert von enrolledCourses zuruck.
     *
     * @return der Wert von enrolledCourses
     */
    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }



    /**
     * SETTERS
     */

    /**
     * Gibt studentId einen neuen Wert.
     *
     * @param studentId der neue Wert von studentId
     */
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    /**
     * Gibt totalCredits einen neuen Wert.
     *
     * @param totalCredits der neue Wert von totalCredits
     */
    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    /**
     * Gibt enrolledCourses einen neuen Wert.
     *
     * @param enrolledCourses der neue Wert von enrolledCourses
     */
    public void setEnrolledCourses(List<Long> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }



    /**
     * Implementiert die Methode der Klasse 'Person' fur den Fall, in dem die Person ein Student ist.
     */
    @Override
    public String isA() {
        return "student";
    }

    @Override
    public String getFirstname() {
        return super.firstName;
    }

    @Override
    public int compareTo(Student o) {
        return Integer.compare(this.getTotalCredits(), o.getTotalCredits());
    }

}
