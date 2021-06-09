import java.util.Scanner;

public class MyFA1 {


    public static boolean check(String word) {
        String state = "S0";
        if (word.length() <= 3) {
            for (int i = 0; i < word.length(); i++)
                if (state.equals("S0") && word.charAt(i) == 'a')
                    state = "S1";
                else if (state.equals("S0") && word.charAt(i) == 'b')
                    state = "S5";
                else if (state.equals("S1") && word.charAt(i) == 'a')
                    state = "S2";
                else if (state.equals("S1") && word.charAt(i) == 'b')
                    state = "S4";
                else if (state.equals("S2") && (word.charAt(i) == 'a'
                        || word.charAt(i) == 'b'))
                    state = "S3";
                else if (state.equals("S4") && word.charAt(i) == 'b')
                    state = "S3";
                else if (state.equals("S5") && word.charAt(i) == 'b')
                    state = "S4";
                else
                    return false;
            return !state.equals("S0");
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Enter the word you want to check:");
        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine();
        if (check(word))
            System.out.println("The word " + word + " is valid.");
        else
            System.out.println("The word " + word + " is NOT valid.");
    }
}
