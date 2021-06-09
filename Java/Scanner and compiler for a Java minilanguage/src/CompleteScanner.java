import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

public class CompleteScanner {

    // false | for | true | this | class | public | static | if | int | new | else | void | boolean | return
    public static boolean checkIfKeyword(String word) {
        String state = "S0";
        for (int i = 0; i < word.length(); i++)
            if (state.equals("S0") && word.charAt(i) == 'f')
                state = "S1";
            else if (state.equals("S1") && word.charAt(i) == 'a')
                state = "S2";
            else if (state.equals("S2") && word.charAt(i) == 'l')
                state = "S3";
            else if (state.equals("S3") && word.charAt(i) == 's')
                state = "S4";
            else if (state.equals("S4") && word.charAt(i) == 'e')
                state = "S5";
            else if (state.equals("S1") && word.charAt(i) == 'o')
                state = "S6";
            else if (state.equals("S6") && word.charAt(i) == 'r')
                state = "S7";
            else if (state.equals("S0") && word.charAt(i) == 't')
                state = "S8";
            else if (state.equals("S8") && word.charAt(i) == 'r')
                state = "S9";
            else if (state.equals("S9") && word.charAt(i) == 'u')
                state = "S10";
            else if (state.equals("S10") && word.charAt(i) == 'e')
                state = "S11";
            else if (state.equals("S8") && word.charAt(i) == 'h')
                state = "S12";
            else if (state.equals("S12") && word.charAt(i) == 'i')
                state = "S13";
            else if (state.equals("S13") && word.charAt(i) == 's')
                state = "S14";
            else if (state.equals("S0") && word.charAt(i) == 'c')
                state = "S15";
            else if (state.equals("S15") && word.charAt(i) == 'l')
                state = "S16";
            else if (state.equals("S16") && word.charAt(i) == 'a')
                state = "S17";
            else if (state.equals("S17") && word.charAt(i) == 's')
                state = "S18";
            else if (state.equals("S18") && word.charAt(i) == 's')
                state = "S19";
            else if (state.equals("S0") && word.charAt(i) == 'p')
                state = "S20";
            else if (state.equals("S20") && word.charAt(i) == 'u')
                state = "S21";
            else if (state.equals("S21") && word.charAt(i) == 'b')
                state = "S22";
            else if (state.equals("S22") && word.charAt(i) == 'l')
                state = "S23";
            else if (state.equals("S23") && word.charAt(i) == 'i')
                state = "S24";
            else if (state.equals("S24") && word.charAt(i) == 'c')
                state = "S25";
            else if (state.equals("S0") && word.charAt(i) == 's')
                state = "S26";
            else if (state.equals("S26") && word.charAt(i) == 't')
                state = "S27";
            else if (state.equals("S27") && word.charAt(i) == 'a')
                state = "S28";
            else if (state.equals("S28") && word.charAt(i) == 't')
                state = "S23";
            else if (state.equals("S0") && word.charAt(i) == 'i')
                state = "S29";
            else if (state.equals("S29") && word.charAt(i) == 'f')
                state = "S30";
            else if (state.equals("S29") && word.charAt(i) == 'n')
                state = "S31";
            else if (state.equals("S31") && word.charAt(i) == 't')
                state = "S32";
            else if (state.equals("S0") && word.charAt(i) == 'n')
                state = "S33";
            else if (state.equals("S33") && word.charAt(i) == 'e')
                state = "S34";
            else if (state.equals("S34") && word.charAt(i) == 'w')
                state = "S35";
            else if (state.equals("S0") && word.charAt(i) == 'e')
                state = "S36";
            else if (state.equals("S36") && word.charAt(i) == 'l')
                state = "S37";
            else if (state.equals("S37") && word.charAt(i) == 's')
                state = "S38";
            else if (state.equals("S38") && word.charAt(i) == 'e')
                state = "S39";
            else if (state.equals("S0") && word.charAt(i) == 'v')
                state = "S40";
            else if (state.equals("S40") && word.charAt(i) == 'o')
                state = "S41";
            else if (state.equals("S41") && word.charAt(i) == 'i')
                state = "S42";
            else if (state.equals("S42") && word.charAt(i) == 'd')
                state = "S43";
            else if (state.equals("S0") && word.charAt(i) == 'b')
                state = "S44";
            else if (state.equals("S44") && word.charAt(i) == 'o')
                state = "S45";
            else if (state.equals("S45") && word.charAt(i) == 'o')
                state = "S46";
            else if (state.equals("S46") && word.charAt(i) == 'l')
                state = "S47";
            else if (state.equals("S47") && word.charAt(i) == 'e')
                state = "S48";
            else if (state.equals("S48") && word.charAt(i) == 'a')
                state = "S49";
            else if (state.equals("S49") && word.charAt(i) == 'n')
                state = "S50";
            else if (state.equals("S0") && word.charAt(i) == 'r')
                state = "S51";
            else if (state.equals("S51") && word.charAt(i) == 'e')
                state = "S52";
            else if (state.equals("S52") && word.charAt(i) == 't')
                state = "S53";
            else if (state.equals("S53") && word.charAt(i) == 'u')
                state = "S54";
            else if (state.equals("S54") && word.charAt(i) == 'r')
                state = "S55";
            else if (state.equals("S55") && word.charAt(i) == 'n')
                state = "S56";
            else
                state = "S0";
        return (state.equals("S5") || state.equals("S7") || state.equals("S11") || state.equals("S14") ||
                state.equals("S19") || state.equals("S25") || state.equals("S30") || state.equals("S32") ||
                state.equals("S35") || state.equals("S39") || state.equals("S43") || state.equals("S50") ||
                state.equals("S56"));
    }

    // ! | && | < | \+ | \- | \* | / | > | ++ | += | == | >=
    public static boolean checkIfOperator(String word) {
        String state = "S0";
        for (int i = 0; i < word.length(); i++)
            if (state.equals("S0") && word.charAt(i) == '!')
                state = "S1";
            else if (state.equals("S0") && word.charAt(i) == '&')
                state = "S2";
            else if (state.equals("S2") && word.charAt(i) == '&')
                state = "S3";
            else if (state.equals("S0") && word.charAt(i) == '<')
                state = "S4";
            else if (state.equals("S0") && word.charAt(i) == '=')
                state = "S5";
            else if (state.equals("S5") && word.charAt(i) == '=')
                state = "S11";
            else if (state.equals("S0") && word.charAt(i) == '+')
                state = "S6";
            else if (state.equals("S6") && word.charAt(i) == '=')
                state = "S11";
            else if (state.equals("S6") && word.charAt(i) == '+')
                state = "S12";
            else if (state.equals("S0") && word.charAt(i) == '-')
                state = "S7";
            else if (state.equals("S0") && word.charAt(i) == '*')
                state = "S8";
            else if (state.equals("S0") && word.charAt(i) == '/')
                state = "S9";
            else if (state.equals("S0") && word.charAt(i) == '>')
                state = "S10";
            else if (state.equals("S10") && word.charAt(i) == '=')
                state = "S11";
            else
                state = "S0";
        return (!state.equals("S0") && !state.equals("S2"));
    }

    // [\{, \}, \[, \], \(, \), \., \,, \:, ;]
    public static boolean checkIfSeparator(String word) {
        String state = "S0";
        for (int i = 0; i < word.length(); i++)
            if (state.equals("S0") && word.charAt(i) == '{')
                state = "S1";
            else if (state.equals("S0") && word.charAt(i) == '}')
                state = "S2";
            else if (state.equals("S0") && word.charAt(i) == '[')
                state = "S3";
            else if (state.equals("S0") && word.charAt(i) == ']')
                state = "S4";
            else if (state.equals("S0") && word.charAt(i) == '(')
                state = "S5";
            else if (state.equals("S0") && word.charAt(i) == ')')
                state = "S6";
            else if (state.equals("S0") && word.charAt(i) == '.')
                state = "S7";
            else if (state.equals("S0") && word.charAt(i) == ',')
                state = "S8";
            else if (state.equals("S0") && word.charAt(i) == ':')
                state = "S9";
            else if (state.equals("S0") && word.charAt(i) == ';')
                state = "S10";
            else
                state = "S0";
        return !state.equals("S0");
    }

    // [0-9]+
    public static boolean checkIfNumberLiteral(String word) {
        String state = "S0";
        for (int i = 0; i < word.length(); i++)
            if (state.equals("S0") && word.charAt(i) >= '0' && word.charAt(i) <= '9')
                state = "S1";
            else if (state.equals("S1") && word.charAt(i) >= '0' && word.charAt(i) <= '9')
                state = "S1";
            else
                state = "S0";
        return state.equals("S1");
    }

    // "[A-Za-z0-9: ]+"
    public static boolean checkIfStringLiteral(String word) {
        String state = "S0";
        for (int i = 0; i < word.length(); i++)
            if (state.equals("S0") && word.charAt(i) == '"')
                state = "S1";
            else {
                boolean b = (word.charAt(i) >= 'A' && word.charAt(i) <= 'Z') || (word.charAt(i) >= 'a'
                        && word.charAt(i) <= 'z') || (word.charAt(i) >= '0' && word.charAt(i) <= '9')
                        || word.charAt(i) == ' ' || word.charAt(i) == ':';
                if (state.equals("S1") && b)
                    state = "S2";
                else if (state.equals("S2") && b)
                    state = "S2";
                else if (state.equals("S2") && word.charAt(i) == '"')
                    state = "S3";
                else
                    state = "S0";
            }
        return state.equals("S3");
    }

    // [A-Za-z][A-Za-z0-9_]*
    public static boolean checkIfIdentifier(String word) {
        String state = "S0";
        for (int i = 0; i < word.length(); i++) {
            boolean b = word.charAt(i) >= 'a' && word.charAt(i) <= 'z';
            boolean b1 = word.charAt(i) >= 'A' && word.charAt(i) <= 'Z';
            if (state.equals("S0") && (b1 ||
                    b))
                state = "S1";
            else if (state.equals("S1") && (b1 ||
                    b || (word.charAt(i) >= '0' &&
                    word.charAt(i) <= '9') || word.charAt(i) == '_'))
                state = "S1";
            else
                state = "S0";
        }
        return state.equals("S1");
    }

    public static String formatLine(String line) {
        String newLine = "";
        for (int i = 0; i < line.length(); i++)
            if (line.charAt(i) == ';' || line.charAt(i) == ',' || line.charAt(i) == ')' || line.charAt(i) == ']'
                    || (i != 0 && line.charAt(i) == '+' && line.charAt(i - 1) != '+'))
                newLine += " " + line.charAt(i);
            else if (line.charAt(i) == '.' || line.charAt(i) == '(' || line.charAt(i) == '[' || line.charAt(i) == '<'
                    || (line.charAt(i) == '>' && line.charAt(i+1) != '='))
                newLine += " " + line.charAt(i) + " ";
            else
                newLine += line.charAt(i);
            if (newLine.contains("//"))
                newLine = newLine.substring(0, newLine.indexOf("//"));
        return newLine;
    }

    public static void scan(String filename) throws IOException {
        Map<String, String> tokenTypes = new HashMap<>();
        tokenTypes.put("Keyword", "K");
        tokenTypes.put("Operator", "O");
        tokenTypes.put("Separator", "P");
        tokenTypes.put("NumberLiteral", "N");
        tokenTypes.put("StringLiteral", "S");
        tokenTypes.put("Identifier", "I");

        Map<String, Integer> tokenIDs = new HashMap<String, Integer>();
        tokenIDs.put("false", 0);
        tokenIDs.put("true", 1);
        tokenIDs.put("class", 2);
        tokenIDs.put("public", 3);
        tokenIDs.put("static", 4);
        tokenIDs.put("this", 5);
        tokenIDs.put("if", 6);
        tokenIDs.put("for", 7);
        tokenIDs.put("new", 8);
        tokenIDs.put("else", 9);
        tokenIDs.put("{", 50);
        tokenIDs.put("}", 51);
        tokenIDs.put("[", 52);
        tokenIDs.put("]", 53);
        tokenIDs.put("(", 54);
        tokenIDs.put(")", 55);
        tokenIDs.put(".", 60);
        tokenIDs.put(",", 61);
        tokenIDs.put(":", 62);
        tokenIDs.put(";", 63);
        tokenIDs.put(">", 69);
        tokenIDs.put("!", 70);
        tokenIDs.put("&&", 71);
        tokenIDs.put("<", 72);
        tokenIDs.put("=", 73);
        tokenIDs.put("==", 74);
        tokenIDs.put("+=", 75);
        tokenIDs.put("++", 76);
        tokenIDs.put(">=", 77);
        tokenIDs.put("+", 80);
        tokenIDs.put("-", 81);
        tokenIDs.put("*", 82);
        tokenIDs.put("/", 83);
        tokenIDs.put("void", 90);
        tokenIDs.put("boolean", 91);
        tokenIDs.put("int", 92);
        tokenIDs.put("return", 93);

        FileReader file = new FileReader(filename);
        BufferedReader br = new BufferedReader(file);
        String line = br.readLine();
        HashSet<String> symbolTable = new HashSet<>();
        FileWriter pif = new FileWriter("PIF.csv");
        int lineCounter = 1;
        int countStrings = 0;
        if (line != null)
            line = formatLine(line);
        while (line != null) {
            int i = 0;
            while (i < line.length()) {
                String word = "";
                if (line.charAt(i) == '"') {
                    countStrings++;
                    word += line.charAt(i);
                    i++;
                    while (i < line.length() && countStrings % 2 == 1) {
                        if (line.charAt(i) == '"')
                            countStrings++;
                        word += line.charAt(i);
                        i++;
                    }
                } else
                    while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\n') {
                        word += line.charAt(i);
                        i++;
                    }
                if (!word.equals(""))
                    if (checkIfIdentifier(word) && !checkIfKeyword(word)) {
                        symbolTable.add(word);
                        pif.write(tokenTypes.get("Identifier") + ", " + lineCounter + ", #, " + word + "\n");
                    }
                    else if (checkIfKeyword(word))
                        pif.write(tokenTypes.get("Keyword") + ", " + lineCounter + ", " + tokenIDs.get(word) +
                                ", " + word + "\n");
                    else if (checkIfNumberLiteral(word))
                        pif.write(tokenTypes.get("NumberLiteral") + ", " + lineCounter + ", #, " + word + "\n");
                    else if (checkIfStringLiteral(word))
                        pif.write(tokenTypes.get("StringLiteral") + ", " + lineCounter + ", #, " + word + "\n");
                    else if (checkIfOperator(word))
                        pif.write(tokenTypes.get("Operator") + ", " + lineCounter + ", " + tokenIDs.get(word) +
                                ", " + word + "\n");
                    else if (checkIfSeparator(word))
                        pif.write(tokenTypes.get("Separator") + ", " + lineCounter + ", " + tokenIDs.get(word) +
                                ", " + word + "\n");
                    else if (!word.equals(" ") && !word.contains("\t") && !word.contains("\n")) {
                        System.out.println("ERROR: Lexical error in line " + lineCounter + ". Unidentified token " + word);
                    }

                i++;
            }
            line = br.readLine();
            lineCounter++;
            if (line != null)
                line = formatLine(line);
        }
        pif.close();
        FileWriter fileWriter = new FileWriter("symboltable.txt");
        TreeSet<String> sortedSymbolTable = new TreeSet<>(symbolTable);
        for (String entry : sortedSymbolTable)
            fileWriter.write(entry + "\n");
        fileWriter.close();
    }
}
