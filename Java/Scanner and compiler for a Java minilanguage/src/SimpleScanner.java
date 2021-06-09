import javafx.util.Pair;

import java.util.Hashtable;
import java.util.Scanner;

public class SimpleScanner {

    public static String check(String word) {
        String state = "S0";
        Hashtable<Pair<String, Character>, String> transition = new Hashtable<>();
        for (int i = 0; i < word.length(); i++) {
            Pair<String, Character> currentState = new Pair<>(state, word.charAt(i));
            if (state.equals("S0") && word.charAt(i) == 'i')
                state = "S1";
            else if (state.equals("S0") && word.charAt(i) == 'f')
                state = "S5";
            else if (state.equals("S0") && word.charAt(i) == 'r')
                state = "S10";
            else if (state.equals("S0") && word.charAt(i) == 'p')
                state = "S16";
            else if (state.equals("S0") && word.charAt(i) == 'c')
                state = "S22";
            else if (state.equals("S0") && word.charAt(i) >= '0' && word.charAt(i) <= '9')
                state = "S27";
            else if (state.equals("S0") && word.charAt(i) == '"')
                state = "S29";
            else if (state.equals("S0") && word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')
                state = "S32";
            else if (state.equals("S1") && word.charAt(i) == 'f')
                state = "S2";
            else if (state.equals("S1") && word.charAt(i) == 'n')
                state = "S3";
            else if (state.equals("S3") && word.charAt(i) == 't')
                state = "S4";
            else if (state.equals("S5") && word.charAt(i) == 'l')
                state = "S6";
            else if (state.equals("S5") && word.charAt(i) == 'o')
                state = "S8";
            else if (state.equals("S6") && word.charAt(i) == 'o')
                state = "S7";
            else if (state.equals("S7") && word.charAt(i) == 'a')
                state = "S3";
            else if (state.equals("S8") && word.charAt(i) == 'r')
                state = "S9";
            else if (state.equals("S10") && word.charAt(i) == 'e')
                state = "S11";
            else if (state.equals("S11") && word.charAt(i) == 't')
                state = "S12";
            else if (state.equals("S12") && word.charAt(i) == 'u')
                state = "S13";
            else if (state.equals("S13") && word.charAt(i) == 'r')
                state = "S14";
            else if (state.equals("S14") && word.charAt(i) == 'n')
                state = "S15";
            else if (state.equals("S16") && word.charAt(i) == 'u')
                state = "S17";
            else if (state.equals("S17") && word.charAt(i) == 'b')
                state = "S18";
            else if (state.equals("S18") && word.charAt(i) == 'l')
                state = "S19";
            else if (state.equals("S19") && word.charAt(i) == 'i')
                state = "S20";
            else if (state.equals("S20") && word.charAt(i) == 'c')
                state = "S21";
            else if (state.equals("S22") && word.charAt(i) == 'l')
                state = "S23";
            else if (state.equals("S23") && word.charAt(i) == 'a')
                state = "S24";
            else if (state.equals("S24") && word.charAt(i) == 's')
                state = "S25";
            else if (state.equals("S25") && word.charAt(i) == 's')
                state = "S26";
            else if (state.equals("S27") && word.charAt(i) >= '0' && word.charAt(i) <= '9')
                state = "S27";
            else if (state.equals("S27") && word.charAt(i) == '.')
                state = "S28";
            else if (state.equals("S28") && word.charAt(i) >= '0' && word.charAt(i) <= '9')
                state = "S27";
            else if (state.equals("S29"))
                state = "S30";
            else if (state.equals("S30"))
                if (word.charAt(i) == '"')
                    state = "S31";
                else
                    state = "S30";
            else if (state.equals("S32") && word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')
                state = "S32";
            else
                return "S0";
            transition.put(currentState, state);
        }
        return state;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word you want to check:");
        String word = scanner.nextLine();
        String checked = check(word);
        if (checked.equals("S2") || checked.equals("S4") || checked.equals("S9") || checked.equals("S15")
        || checked.equals("S21") || checked.equals("S26"))
            System.out.println("ACCEPTED Keyword");
        else if (checked.equals("S27") || checked.equals("S31"))
            System.out.println("ACCEPTED Literal");
        else if (checked.equals("S32"))
            System.out.println("ACCEPTED Identifier");
        else
            System.out.println("REJECTED");
    }
}
