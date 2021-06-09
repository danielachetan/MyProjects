import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * E -> T E'
 * E' -> + E | - E | epsilon
 * T -> F T'
 * T' -> * T | / T | epsilon
 * F -> number
 */

public class ExpressionParser {

    public static int count = 0;

    // ( 0 | [1-9][0-9]* )
    public static boolean isNumber(String word) {
        String state = "S0";
        for (int i = 0; i < word.length(); i++)
            if (state.equals("S0") && word.charAt(i) == '0')
                state = "S1";
            else if (state.equals("S0") && word.charAt(i) >= '1' && word.charAt(i) <= '9')
                state = "S2";
            else if (state.equals("S2") && word.charAt(i) >= '0' && word.charAt(i) <= '9')
                state = "S2";
            else {
                state = "E";
                break;
            }
        return (state.equals("S1") || state.equals("S2"));
    }

    // ( \+ | - | \* | / )
    public static boolean isOperator(String word) {
        String state = "S0";
        for (int i = 0; i < word.length(); i++)
            if (state.equals("S0") && (word.charAt(i) == '+' || word.charAt(i) == '-' || word.charAt(i) == '*' ||
                    word.charAt(i) == '/'))
                state = "S1";
            else {
                state = "E";
                break;
            }
        return state.equals("S1");
    }

    public static List<String> scan(String expression) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        while (i < expression.length()) {
            if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                String tokenBuilder = "";
                while (i < expression.length() && expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                    tokenBuilder += expression.charAt(i);
                    i++;
                }
                if (isNumber(tokenBuilder))
                    tokens.add(tokenBuilder);
            } else {
                String op = "" + expression.charAt(i);
                if (isOperator(op))
                    tokens.add(op);
                else if (!op.equals(" ")) {
                    System.out.println("ERROR: Not allowed character at position " + i + "!");
                    return null;
                }
                i++;
            }
        }
        return tokens;
    }

    // E -> T E'
    public static int expression(List<String> tokens) throws NumberNotFoundException, OperatorNotFoundException {
        int t = term(tokens);
        tokens = tokens.subList(count + 1, tokens.size());
        count = 0;
        return auxExpression(t, tokens);
    }

    // E' -> + E | - E | epsilon
    public static int auxExpression(int t, List<String> tokens) throws NumberNotFoundException, OperatorNotFoundException {
        if (tokens.size() > 0 && tokens.get(0).equals("+")) {
            tokens = tokens.subList(1, tokens.size());
            return t + expression(tokens);
        }
        else if (tokens.size() > 0 && tokens.get(0).equals("-")) {
            tokens = tokens.subList(1, tokens.size());
            return t - expression(tokens);
        }
        else if (tokens.size() == 0 || isOperator(tokens.get(0)))
            return t;
        else
            throw new OperatorNotFoundException();
    }

    // T -> F T'
    public static int term(List<String> tokens) throws NumberNotFoundException, OperatorNotFoundException {
        int f = factor(tokens.get(0));
        tokens = tokens.subList(1, tokens.size());
        return auxTerm(f, tokens);
    }

    // T' -> * T | / T | epsilon
    public static int auxTerm(int f, List<String> tokens) throws NumberNotFoundException, OperatorNotFoundException {
        if (tokens.size() > 0 && tokens.get(0).equals("*")) {
            tokens = tokens.subList(1, tokens.size());
            count += 2;
            return f * term(tokens);
        }
        else if (tokens.size() > 0 && tokens.get(0).equals("/")) {
            tokens = tokens.subList(1, tokens.size());
            count += 2;
            return f / term(tokens);
        }
        else if (tokens.size() == 0 || isOperator(tokens.get(0)))
            return f;
        else
            throw new OperatorNotFoundException();
    }

    // F -> number
    public static int factor(String number) throws NumberNotFoundException {
        if (isNumber(number))
            return Integer.parseInt(number);
        else
            throw new NumberNotFoundException();
    }

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("Enter an arithmetic expression:");
        String expr = read.nextLine();
        List<String> tokens = scan(expr);
        if (tokens != null)
            try {
                System.out.println(expression(tokens));
            } catch (NumberNotFoundException e1) {
                System.out.println("ERROR: Number missing from expression!");
            } catch (OperatorNotFoundException e2) {
                System.out.println("ERROR: Operator missing from expression!");
            }
    }
}
