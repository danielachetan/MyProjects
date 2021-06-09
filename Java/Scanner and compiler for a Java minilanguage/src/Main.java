import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Student> students;

    public static void init() {
        students = new ArrayList<>();
        Student s1 = new Student("Paula", "Carter", 731);   //CONSTRUCT 2
        s1.generateGrades();
        students.add(s1);
        Student s2 = new Student("Douglas", "Anderson", 731);   //CONSTRUCT 2
        s2.generateGrades();
        students.add(s2);
        Student s3 = new Student("Ryan", "Brooks", 731);   //CONSTRUCT 2
        s3.generateGrades();
        students.add(s3);
        Student s4 = new Student("Kathy", "Johnson", 732);   //CONSTRUCT 2
        s4.generateGrades();
        students.add(s4);
        Student s5 = new Student("Rebecca", "Reed", 732);   //CONSTRUCT 2
        s5.generateGrades();
        students.add(s5);
        Student s6 = new Student("Arthur", "Martin", 732);   //CONSTRUCT 2
        s6.generateGrades();
        students.add(s6);
    }

    public static double getFinalGrade(Student student) {
        int sum = 0;   //CONSTRUCT 1
        //CONSTRUCT 5
        for (int i = 0; i < 6; i++)
            sum += student.getGrades().get(i);   //CONSTRUCT 3
        double avg = (double) sum / 6;   //CONSTRUCT 3
        return avg;
    }

    public static List<Student> getStudents(int group, double grade) {
        List<Student> result = new ArrayList<>();
        //CONSTRUCT 5
        for (int i = 0; i < students.size(); i++)
            //CONSTRUCT 4
            if (students.get(i).getGroup() == group && getFinalGrade(students.get(i)) >= grade)
                result.add(students.get(i));
        return result;
    }

    public static void printStudents(List<Student> studentList) {
        //CONSTRUCT 5
        for (int i = 0; i < studentList.size(); i++) {
            //CONSTRUCT 7
            System.out.println();
            System.out.println(i + 1);
            System.out.println("First name: " + studentList.get(i).getFirstName());
            System.out.println("Last name: " + studentList.get(i).getLastName());
            System.out.println("Group: " + studentList.get(i).getGroup());
            double finalGrade = getFinalGrade(studentList.get(i));
            System.out.println("Final grade: " + finalGrade);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        init();
        Scanner scanner = new Scanner(System.in);
        int group;   //CONSTRUCT 1
        System.out.println("Enter a group number: ");   //CONSTRUCT 7
        group = scanner.nextInt();   //CONSTRUCT 6
        double grade;   //CONSTRUCT 1
        System.out.println("Enter a grade: ");   //CONSTRUCT 7
        grade = scanner.nextDouble();   //CONSTRUCT 6
        List<Student> passed;
        passed = getStudents(group, grade);
        printStudents(passed);
    }
}
