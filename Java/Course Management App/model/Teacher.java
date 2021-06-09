package lab3.model;

import javafx.beans.InvalidationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Stellt ein Lehrer dar, der keine zusatzliche Attribute zur Basisklasse 'Person' hat.
 */
public class Teacher extends Person implements Observer {
    long teacherId;
    List<Student> students;


    public Teacher(long teacherId) {
        this.teacherId = teacherId;
    }

    public Teacher(long teacherId, String firstName, String lastName) {
        this.teacherId = teacherId;
        super.firstName = firstName;
        super.lastName = lastName;
        students = new ArrayList<Student>();
    }

    /**
     * GETTERS
     */

    /**
     * Gibt den Wert von teacherId zuruck.
     *
     * @return der Wert von teacherId
     */
    public long getTeacherId() {
        return teacherId;
    }

    public List<Student> getStudents() {
        return students;
    }

    /**
     * SETTERS
     */

    /**
     * Gibt teacherId einen neuen Wert.
     *
     * @param teacherId der neue Wert von teacherId
     */
    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    /**
     * Implementiert die Methode der Klasse 'Person' fur den Fall, in dem die Person ein Lehrer ist.
     */
    public String isA() {
        return "teacher";
    }

    @Override
    public String getFirstname() {
        return super.firstName;
    }

    @Override
    public void update(Observable o, Object arg) {
        o.addObserver(this);
        o.notifyObservers();
    }
}
