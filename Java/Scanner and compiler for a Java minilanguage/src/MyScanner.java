import java.io.*;
import java.util.*;

public class MyScanner {
    public static HashMap<String, Integer> operators = new HashMap<>();
    public static HashMap<String, Integer> constants = new HashMap<>();
    public static HashMap<String, Integer> keywords = new HashMap<>();
    public static HashMap<String, Integer> identifiers = new HashMap<>();
    public static HashMap<String, Integer> otherTokens = new HashMap<>();
    public static List<Integer> errors = new ArrayList<>();

    public static String findOperators(String word) {
        String[] operatorList = {"++", "+=", "-=", "*=", "/=", "==", "!=", "<=", ">=", "=",
                "+", "-", "*", "/", "<", ">", "&&", "||", "_"};
        int i = 0;
        while (!(word.equals(operatorList[i])) && !(word.contains("++")) &&
                (i < operatorList.length - 1)) i++;
        if (i < operatorList.length - 1) {
            if (operators.containsKey(operatorList[i]))
                operators.put(operatorList[i], operators.get(operatorList[i]) + 1);
            else
                operators.put(operatorList[i], 1);
            char[] chars = word.toCharArray();
            if (Character.isLetter(chars[0]))
                word = word.substring(0, word.length() - 2);
            return word;
        }
        return null;
    }

    public static String findConstants(String line) {
        if (line.contains("\"")) {
            String aux = line;
            int pos = aux.indexOf("\"");
            List<Integer> allPairs = new ArrayList<>();
            while (pos >= 0) {
                if (allPairs.isEmpty())
                    allPairs.add(pos);
                else
                    allPairs.add(pos + allPairs.get(allPairs.size() - 1) + 1);
                aux = aux.substring(pos + 1);
                pos = aux.indexOf("\"");
            }
            for (int i = 0; i < allPairs.size() - 1; i += 2) {
                String literal = line.substring(allPairs.get(i), allPairs.get(i + 1) + 1);
                String s1 = line.substring(0, allPairs.get(i));
                String s2 = line.substring(allPairs.get(i + 1) + 1);
                String rep = "";
                for (int j = allPairs.get(i); j < allPairs.get(i + 1) + 1; j++)
                    rep = rep.concat("$");
                line = s1.concat(rep).concat(s2);
                if (constants.containsKey(literal))
                    constants.put(literal, constants.get(literal) + 1);
                else
                    constants.put(literal, 1);
            }
        }
        line = line.replaceAll("\\$", "");
        return line;
    }

    public static boolean findKeywords(String word) {
        String[] allKeywords = {"import", "public", "private", "class", "static", "new",
                "List", "ArrayList", "void", "double", "int", "for", "return", "if",
                "else", "_"};
        int i = 0;
        while (!word.equals(allKeywords[i]) && i < allKeywords.length - 1) i++;
        if (i != allKeywords.length - 1) {
            if (keywords.containsKey(allKeywords[i]))
                keywords.put(allKeywords[i], keywords.get(allKeywords[i]) + 1);
            else
                keywords.put(allKeywords[i], 1);
            return true;
        }
        return false;
    }

    public static boolean findIdentifiers(String word) {
        if (word.matches("[a-zA-Z][a-zA-Z0-9]*") && !findKeywords(word)) {
            if (identifiers.containsKey(word))
                identifiers.put(word, identifiers.get(word) + 1);
            else
                identifiers.put(word, 1);
            return true;
        }
        return false;
    }

    public static String findTokens(String line) {
        char[] tokenList = {'{', '}', '[', ']', '(', ')', ',', ';', '_'};
        for (int i = 0; i < line.length(); i++) {
            int j = 0;
            while (line.charAt(i) != tokenList[j] && j < tokenList.length - 1) j++;
            if (j != tokenList.length - 1) {
                if (otherTokens.containsKey(String.valueOf(tokenList[j])))
                    otherTokens.put(String.valueOf(tokenList[j]),
                            otherTokens.get(String.valueOf(tokenList[j])) + 1);
                else
                    otherTokens.put(String.valueOf(tokenList[j]), 1);
                String s1 = line.substring(0, i);
                String s2 = line.substring(i + 1);
                line = s1.concat(" ").concat(s2);
            }
        }
        return line;
    }

    public static String clearNontokens(String line) {
        if (line.contains("//"))
            line = line.substring(0, line.indexOf("//"));
        line = line.replaceAll("\\.", " ");
        line = line.replaceAll(":", " ");
        for (int i = 0; i < line.length(); i++)
            if ((line.charAt(i) == '<' || line.charAt(i) == '>')
                    && (line.charAt(i-1) != ' ' || line.charAt(i+1) != ' ')) {
                String s1 = line.substring(0, i);
                String s2 = line.substring(i+1);
                line = s1.concat(" ").concat(s2);
            }
        return line;
    }

    public static void scan(String filename) throws IOException {
        FileReader file = new FileReader(filename);
        BufferedReader br = new BufferedReader(file);
        String data;
        int count = 0;
        while ((data = br.readLine()) != null) {
            count++;
            data = clearNontokens(data);
            data = findTokens(data);
            data = findConstants(data);
           Scanner sc = new Scanner(data);
           while (sc.hasNext()) {
               String word = sc.next();
               String op = findOperators(word);
               if (op == null) {
                   findKeywords(word);
                   findIdentifiers(word);
                   if (!findKeywords(word) && !findIdentifiers(word)
                           && !word.matches("[0-9]+"))
                       errors.add(count);

               }
           }
           sc.close();
        }
        br.close();
    }

    public static void printSorted(HashMap<String, Integer> map) {
        TreeMap<String, Integer> sortedMap = new TreeMap<>(map);
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet())
            System.out.println("\t" + entry.getKey());
    }

    public static int countTotal(HashMap<String, Integer> map) {
        int total = 0;
        for (int v : map.values())
            total += v;
        return total;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of your source file:");
        String path = scanner.nextLine();
        scan(path);
        int totalIdentifiers = countTotal(identifiers);
        int totalConstants = countTotal(constants);
        int totalKeywords = countTotal(keywords);
        int totalOperators = countTotal(operators);
        int totalTokens = totalIdentifiers + totalConstants + totalKeywords
                + totalOperators + countTotal(otherTokens);
        if (errors.isEmpty()) {
            System.out.println("Summary: ");
            System.out.println("\tTokens          " + totalTokens);
            System.out.println("\tIdentifiers     " + totalIdentifiers);
            System.out.println("\tConstants       " + totalConstants);
            System.out.println("\tKeywords        " + totalKeywords);
            System.out.println("\tOperators       " + totalOperators);
            System.out.println();
            System.out.println("Identifiers:");
            printSorted(identifiers);
            System.out.println();
            System.out.println("Constants:");
            printSorted(constants);
            System.out.println();
            System.out.println("Keywords:");
            printSorted(keywords);
        }
        else
            for (int i = 0; i < errors.size(); i++)
                System.out.println("ERROR: Lexical error on line " + errors.get(i));
    }
}
