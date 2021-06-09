package lab3.model;

/**
 * Stellt eine generische Person mit Vorname und Nachname dar.
 */
public abstract class Person {
    String firstName;
    String lastName;

    /**
     * GETTERS
     */

    /**
     * Gibt den Wert von firstName zuruck.
     *
     * @return der Wert von firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gibt den Wert von lastName zuruck.
     *
     * @return der Wert von lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * SETTERS
     */

    /**
     * Gibt firstName einen neuen Wert.
     *
     * @param firstName der neue Wert von firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gibt lastName einen neuen Wert.
     *
     * @param lastName der neue Wert von lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Stellt fest, ob die Person ein Lehrer oder ein Student ist.
     *
     * @return 'teacher' oder 'student', abhangig vom Typ der Person
     */
    public abstract String isA();

    public abstract String getFirstname();

    //public abstract String getFirstname();

}
