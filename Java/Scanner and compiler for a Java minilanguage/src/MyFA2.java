import java.util.Scanner;

public class MyFA2 {
    public static boolean check(String word) {
        String state = "S0";
        String letters = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < word.length(); i++)
            if (state.equals("S0") && word.charAt(i) >= 'a' && word.charAt(i) <= 'z')
                state = "S1";
            else if (state.equals("S1") && word.charAt(i) >= 'a' && word.charAt(i) <= 'z')
                state = "S2";
            else if (state.equals("S2") && word.charAt(i) == '.')
                state = "S3";
            else if (state.equals("S2") && word.charAt(i) >= 'a' && word.charAt(i) <= 'z')
                state = "S2";
            else if (state.equals("S3") && word.charAt(i) >= 'a' && word.charAt(i) <= 'z')
                state = "S1";
            else
                return false;
            return state.equals("S2");
    }

    public static void main(String[] args) {
        System.out.println("Enter the word you want to check:");
        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine();
        if (check(word))
            System.out.println(word + " is a valid hostname.");
        else
            System.out.println(word + " is NOT a valid hostname.");
    }
}
