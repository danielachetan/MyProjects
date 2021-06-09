import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student {
    //CONSTRUCT 1
    private String firstName;
    private String lastName;
    private int group;
    private List<Integer> grades;

    public Student() {
        firstName = "";
        lastName = "";
        group = 0;
        grades = new ArrayList<Integer>();
    }

    public Student(String firstName, String lastName, int group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        grades = new ArrayList<Integer>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGroup() {
        return group;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void generateGrades() {
        Random random = new Random();
        //CONSTRUCT 5
        for (int i = 0; i < 6; i++)
            grades.add(random.nextInt(7) + 4);
    }
}
