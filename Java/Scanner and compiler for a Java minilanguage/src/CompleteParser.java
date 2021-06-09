import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompleteParser {
    public static List<TokenInformation> allTokens = new ArrayList<>();
    public static List<TableEntry> outputTable = new ArrayList<>();
    public static String scope = "global";
    public static String accessMod = "private";
    public static String identType = "--";

    // IDENT = LETTER { LETTER | DIGIT | "_" };
    public static boolean ident() {
        return allTokens.get(0).getTypeCode().equals("I");
    }

    // IMPORT = "import" { IDENT "." } IDENT ";";
    public static boolean importStatement() {
        if (allTokens.get(0).getToken().equals("import"))
            allTokens.remove(0);
        else
           return false;
        while (ident() && allTokens.get(1).getToken().equals(".")) {
            allTokens.remove(0);
            allTokens.remove(0);
        }
        if (ident())
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: importStatement()");
            return false;
        }
        if (allTokens.get(0).getToken().equals(";"))
            return true;
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: importStatement()");
            return false;
        }
    }

    // ACCESS-MODIFIER = "public" | "private";
    public static boolean accessModifier() {
        if (allTokens.get(0).getToken().equals("public") || allTokens.get(0).getToken().equals("private")) {
            accessMod = allTokens.get(0).getToken();
            allTokens.remove(0);
            return true;
        }
        else
            return false;
    }

    // TYPE = "String" [ "[" "]" ] | "int" | "double" | IDENT | "List" "<" IDENT ">";
    public static boolean hasType() {
        if (allTokens.get(0).getToken().equals("List")) {
            allTokens.remove(0);
            if (allTokens.get(0).getToken().equals("<"))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: hasType()");
                return false;
            }
            if (ident()) {
                identType = "List<" + allTokens.get(0).getToken() + ">";
                allTokens.remove(0);
            }
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: hasType()");
                return false;
            }
            if (allTokens.get(0).getToken().equals(">"))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: hasType()");
                return false;
            }
            return true;
        }
        else if (allTokens.get(0).getToken().equals("int") || allTokens.get(0).getToken().equals("double") || (ident()
                && Character.isUpperCase(allTokens.get(0).getToken().charAt(0)) &&
                !allTokens.get(0).getToken().equals("System") && !allTokens.get(0).getToken().equals("String"))) {
            identType = allTokens.get(0).getToken();
            allTokens.remove(0);
            return true;
        }
        else if (allTokens.get(0).getToken().equals("String")) {
            identType = "String";
            allTokens.remove(0);
            if (allTokens.get(0).getToken().equals("[")) {
                allTokens.remove(0);
                if (allTokens.get(0).getToken().equals("]")) {
                    identType = "String[]";
                    allTokens.remove(0);
                }
                else
                    return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    // ATTRIBUTE = [ "static" ] TYPE IDENT ";";
    public static boolean attribute() {
        if (allTokens.get(0).getToken().equals("static"))
            allTokens.remove(0);
        if (!hasType())
            return false;
        if (ident())
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: attribute()");
            return false;
        }
        if (!allTokens.get(0).getToken().equals(";")) {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: attribute()");
            return false;
        }
        return true;
    }

    //RETURN-TYPE = TYPE | "void";
    public static boolean returnType() {
        if (allTokens.get(0).getToken().equals("void")) {
            identType = "void";
            allTokens.remove(0);
            return true;
        }
        else return hasType();
    }

    // METHOD = ACCESS-MODIFIER [ "static" ] RETURN-TYPE IDENT "(" [ TYPE IDENT { "," TYPE IDENT } ] ")" "{" BLOCK "}";
    public static boolean method() {
        if (!accessModifier())
            return false;
        if (allTokens.get(0).getToken().equals("static"))
            allTokens.remove(0);
        if (!returnType()) {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
            return false;
        }
        if (ident()) {
            String name = allTokens.get(0).getToken();
            int lineOfDeclaration = allTokens.get(0).getLineNumber();
            TableEntry tableEntry = new TableEntry(name, "method", identType, lineOfDeclaration, accessMod, scope);
            outputTable.add(tableEntry);
            scope += "." + allTokens.get(0).getToken();
            allTokens.remove(0);
        }
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
            return false;
        }
        if (allTokens.get(0).getToken().equals("("))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
            return false;
        }
        if (hasType()) {
            if (ident()) {
                String name = allTokens.get(0).getToken();
                int lineOfDeclaration = allTokens.get(0).getLineNumber();
                TableEntry tableEntry = new TableEntry(name, "parameter", identType, lineOfDeclaration,
                        "--", scope);
                outputTable.add(tableEntry);
                allTokens.remove(0);
            }
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
                return false;
            }
            while (allTokens.get(0).getToken().equals(",")) {
                allTokens.remove(0);
                if (!hasType()) {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
                    return false;
                }
                if (ident()) {
                    String name = allTokens.get(0).getToken();
                    int lineOfDeclaration = allTokens.get(0).getLineNumber();
                    TableEntry tableEntry = new TableEntry(name, "parameter", identType, lineOfDeclaration,
                            "--", scope);
                    outputTable.add(tableEntry);
                    allTokens.remove(0);
                }
                else {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
                    return false;
                }
            }
        }
        if (allTokens.get(0).getToken().equals(")"))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
            return false;
        }
        if (allTokens.get(0).getToken().equals("{"))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
            return false;
        }
        if (!block())
            return false;
        if (allTokens.get(0).getToken().equals("}"))
            scope = scope.substring(0, scope.lastIndexOf("."));
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: method()");
            return false;
        }
        return true;
    }

    // STRING = """ { LETTER | DIGIT | " " | "_" | ":" } """;
    public static boolean isString() {
        if (allTokens.get(0).getTypeCode().equals("S")) {
            String name = allTokens.get(0).getToken();
            int lineOfDeclaration = allTokens.get(0).getLineNumber();
            TableEntry tableEntry = new TableEntry(name, "literal", "String", lineOfDeclaration,
                    "--", "global");
            outputTable.add(tableEntry);
            return true;
        }
        return false;
    }

    // NUMBER = [ "+" | "-" ] DIGIT { DIGIT } [ "." DIGIT { DIGIT } ];
    public static boolean isNumber() {
        if (allTokens.get(0).getTypeCode().equals("N")) {
            String name = allTokens.get(0).getToken();
            int lineOfDeclaration = allTokens.get(0).getLineNumber();
            String type;
            if (allTokens.get(0).getToken().contains("."))
               type = "double";
            else
                type = "int";
            TableEntry tableEntry = new TableEntry(name, "literal", type, lineOfDeclaration, "--",
                    "global");
            outputTable.add(tableEntry);
            return true;
        }
        return false;
    }

    // VALUE = STRING | NUMBER | IDENT;
    public static boolean value() {
        if (isString() || isNumber() || ident())
            return true;
        return false;
    }

    // FACTOR = VALUE [ METHODCALL ] { "." IDENT [ METHODCALL ] | "(" EXPRESSION ")";
    public static boolean factor() {
        if (value()) {
            allTokens.remove(0);
            methodcall();
            while (allTokens.get(0).getToken().equals(".")) {
                allTokens.remove(0);
                if (ident())
                    allTokens.remove(0);
                else {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: factor()");
                    return false;
                }
                methodcall();
            }
        }
        else if (allTokens.get(0).getToken().equals("(")) {
            allTokens.remove(0);
            if (!expression())
                return false;
            if (allTokens.get(0).getToken().equals(")"))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: factor()");
                return false;
            }
        }
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: factor()");
            return false;
        }
        return true;
    }

    // TERM = FACTOR { ( "*" | "/" ) FACTOR };
    public static boolean term() {
        if (!factor())
            return false;
        while (allTokens.get(0).getToken().equals("*") || allTokens.get(0).getToken().equals("/")) {
            allTokens.remove(0);
            if (!factor())
                return false;
        }
        return true;
    }

    // EXPRESSION = [ "(" TYPE ")" ] [ "+" | "-" ] TERM { ( "+" | "-" ) TERM };
    public static boolean expression() {
        if (allTokens.get(0).getToken().equals("(")) {
            allTokens.remove(0);
            if (!hasType()) {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: expression()");
                return false;
            }
            if (allTokens.get(0).getToken().equals(")"))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: expression()");
                return false;
            }
        }
        if (allTokens.get(0).getToken().equals("+") || allTokens.get(0).getToken().equals("-"))
            allTokens.remove(0);
        if (!term())
            return false;
        while (allTokens.get(0).getToken().equals("+") || allTokens.get(0).getToken().equals("-")) {
            allTokens.remove(0);
            if (!term())
                return false;
        }
        return true;
    }

    // METHODCALL = "(" [ VALUE { "." IDENT [ METHODCALL ] } { ( "," | "+" ) VALUE { "." IDENT [ METHODCALL ] } } ")";
    public static boolean methodcall() {
        if (allTokens.get(0).getToken().equals("("))
            allTokens.remove(0);
        else {
            return false;
        }
        if (value()) {
            allTokens.remove(0);
            while (allTokens.get(0).getToken().equals(".")) {
                allTokens.remove(0);
                if (ident())
                    allTokens.remove(0);
                else {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: methodcall()");
                    return false;
                }
                methodcall();
            }
            while (allTokens.get(0).getToken().equals(",") || allTokens.get(0).getToken().equals("+")) {
                allTokens.remove(0);
                if (value())
                    allTokens.remove(0);
                else {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: methodcall()");
                    return false;
                }
                while (allTokens.get(0).getToken().equals(".")) {
                    allTokens.remove(0);
                    if (ident())
                        allTokens.remove(0);
                    else {
                        System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                                ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: methodcall()");
                        return false;
                    }
                    methodcall();
                }
            }
        }
        if (allTokens.get(0).getToken().equals(")"))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: methodcall()");
            return false;
        }
        return true;
    }

    // CONDITION = "!" EXPRESSION | EXPRESSION ( "==" | "!=" | "<" | ">" | "<=" | ">=" ) EXPRESSION;
    public static boolean condition() {
        if (allTokens.get(0).getToken().equals("!")) {
            allTokens.remove(0);
            if (!expression())
                return false;
        }
        else if (expression()) {
            if (allTokens.get(0).getToken().equals("==") || allTokens.get(0).getToken().equals("!=") ||
                    allTokens.get(0).getToken().equals("<") || allTokens.get(0).getToken().equals(">") ||
                    allTokens.get(0).getToken().equals("<=") || allTokens.get(0).getToken().equals(">="))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: condition()");
                return false;
            }
            if (!expression())
                return false;
        }
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: condition()");
            return false;
        }
        return true;
    }

    // INITIALISATION = EXPRESSION { "." IDENT [ METHODCALL ] }| "new" IDENT [ "<" ">" ] "(" [ VALUE { "," VALUE } |
    //                  IDENT "." IDENT ] ")";
    public static boolean initialisation() {
        if (allTokens.get(0).getToken().equals("new")) {
            allTokens.remove(0);
            if (ident())
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
                return false;
            }
            if (allTokens.get(0).getToken().equals("<")) {
                allTokens.remove(0);
                if (allTokens.get(0).getToken().equals(">"))
                    allTokens.remove(0);
                else {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
                    return false;
                }
            }
            if (allTokens.get(0).getToken().equals("("))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
                return false;
            }
            if (ident()) {
                allTokens.remove(0);
                if (allTokens.get(0).getToken().equals("."))
                    allTokens.remove(0);
                else {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
                    return false;
                }
                if (ident())
                    allTokens.remove(0);
                else {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
                    return false;
                }
            }
            else if (value()) {
                allTokens.remove(0);
                while (allTokens.get(0).getToken().equals(",")) {
                    allTokens.remove(0);
                    if (value())
                        allTokens.remove(0);
                    else {
                        System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                                ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
                        return false;
                    }
                }
            }
            if (allTokens.get(0).getToken().equals(")"))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
                return false;
            }
        }
        else if (expression()) {
           while (allTokens.get(0).getToken().equals(".")) {
               allTokens.remove(0);
               if (ident())
                   allTokens.remove(0);
               else {
                   System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                           ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
               }
               methodcall();
           }
        }
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: initialisation()");
            return false;
        }
        return true;
    }

    // DECLARATION = [ ( "=" | "+=" ) INITIALISATION ];
    public static boolean declaration() {
        if (allTokens.get(0).getToken().equals("=") || allTokens.get(0).getToken().equals("+=")) {
            allTokens.remove(0);
            if (!initialisation())
                return false;
        }
        return true;
    }

    // IDENT-MODIFIER = [ TYPE ] IDENT [ METHODCALL ] { "." IDENT [ METHODCALL ] } ( DECLARATION | "++" );
    public static boolean identModifier() {
        boolean typeChanged = false;
        if (hasType())
            typeChanged = true;
        if (ident()) {
            if (typeChanged) {
                String name = allTokens.get(0).getToken();
                int lineOfDeclaration = allTokens.get(0).getLineNumber();
                TableEntry tableEntry = new TableEntry(name, "variable", identType, lineOfDeclaration,
                        "--", scope);
                outputTable.add(tableEntry);
            }
            allTokens.remove(0);
        }
        else
            return false;
        methodcall();
        while (allTokens.get(0).getToken().equals(".")) {
            allTokens.remove(0);
            if (ident())
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: identModifier()");
                return false;
            }
            methodcall();
        }
        if (allTokens.get(0).getToken().equals("++"))
            allTokens.remove(0);
        else if (!declaration())
            return false;
        return true;
    }

    // STATEMENT = [ IDENT-MODIFIER | "return" EXPRESSION | "{" STATEMENT ";" { STATEMENT ";" } "}" | CONDITIONAL |
    //              LOOP ];
    public static boolean statement() {
        if (identModifier())
            return true;
        else if (allTokens.get(0).getToken().equals("return")) {
            allTokens.remove(0);
            if (!expression()) {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: statement()");
                return false;
            }
        }
        else if (allTokens.get(0).getToken().equals("{")) {
            allTokens.remove(0);
            if (!statement()) {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: statement()");
                return false;
            }
            if (allTokens.get(0).getToken().equals(";"))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: statement()");
                return false;
            }
            while (statement())
                if (allTokens.get(0).getToken().equals(";"))
                    allTokens.remove(0);
                else {
                    System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                            ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: statement()");
                    return false;
                }

            if (allTokens.get(0).getToken().equals("}"))
                allTokens.remove(0);
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: statement()");
                return false;
            }
        }
        else if (!conditional() && !loop())
            return false;
        return true;
    }

    // BLOCK = STATEMENT ";" { BLOCK };
    public static boolean block() {
        if (!statement()) {
            return false;
        }
        if (allTokens.get(0).getToken().equals(";"))
            allTokens.remove(0);
        else if (allTokens.get(0).getToken().equals("}"))
            return true;
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: block()");
            return false;
        }
        while (statement())
            if (allTokens.get(0).getToken().equals(";"))
                allTokens.remove(0);
            else if (allTokens.get(0).getToken().equals("}"))
                return true;
            else {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: block()");
                return false;
            }
        return true;
    }

    // CONDITIONAL = "if" "(" CONDITION { ( "&&" || "||" ) CONDITION } ")" STATEMENT { "else" STATEMENT };
    public static boolean conditional() {
        if (allTokens.get(0).getToken().equals("if"))
            allTokens.remove(0);
        else
            return false;
        if (allTokens.get(0).getToken().equals("("))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: conditional()");
            return false;
        }
        if (!condition()) {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: conditional()");
            return false;
        }
        while (allTokens.get(0).getToken().equals("&&") || allTokens.get(0).getToken().equals("||")) {
            allTokens.remove(0);
            if (!condition()) {
                System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                        ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: conditional()");
                return false;
            }
        }
        if (allTokens.get(0).getToken().equals(")"))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: conditional()");
            return false;
        }
        if (!statement())
            return false;
        while (allTokens.get(0).getToken().equals("else")) {
            allTokens.remove(0);
            if (!statement())
                return false;
        }
        return true;
    }

    // LOOP = "for" "(" STATEMENT ";" CONDITION ";" STATEMENT ")" STATEMENT;
    public static boolean loop() {
        if (allTokens.get(0).getToken().equals("for"))
            allTokens.remove(0);
        else
            return false;
        if (allTokens.get(0).getToken().equals("("))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: loop()");
            return false;
        }
        if (!statement())
            return false;
        if (allTokens.get(0).getToken().equals(";"))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: loop()");
            return false;
        }
        if (!condition())
            return false;
        if (allTokens.get(0).getToken().equals(";"))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: loop()");
            return false;
        }
        if (!statement())
            return false;
        if (allTokens.get(0).getToken().equals(")"))
            allTokens.remove(0);
        else {
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: loop()");
            return false;
        }
        if (!statement())
            return false;
        return true;
    }

    // PROGRAM = { IMPORT } ACCESS-MODIFIER "class" IDENT "{" { ATTRIBUTE } { METHOD } "}";
    public static void programParser() {
        while (importStatement()) {
            allTokens.remove(0);
        }
        accessModifier();
        if (allTokens.get(0).getToken().equals("class"))
            allTokens.remove(0);
        else
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: program()");
        if (ident()) {
            String name = allTokens.get(0).getToken();
            int lineOfDeclaration = allTokens.get(0).getLineNumber();
            TableEntry tableEntry = new TableEntry(name, "class", identType, lineOfDeclaration, accessMod, scope);
            outputTable.add(tableEntry);
            allTokens.remove(0);
        }
        else
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: program()");
        if (allTokens.get(0).getToken().equals("{")) {
            scope = outputTable.get(outputTable.size() - 1).getName();
            allTokens.remove(0);
        }
        else
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: program()");
        while (attribute())
            allTokens.remove(0);
        while (method())
            allTokens.remove(0);
        if (allTokens.get(0).getToken().equals("}")) {
            scope = scope.substring(0, scope.lastIndexOf(".") + 1);
            if (allTokens.size() > 0)
                allTokens.remove(0);
        }
        else
            System.out.println("ERROR: Syntax error in line " + allTokens.get(0).getLineNumber() +
                    ". Invalid token " + allTokens.get(0).getToken() + "\n\tMethod: program()");
    }

    public static void main(String[] args) throws IOException {
        CompleteScanner.scan("Main.java");
        FileReader pif = new FileReader("PIF.csv");
        BufferedReader reader = new BufferedReader(pif);
        String line = reader.readLine();
        while (line != null) {
            String typeCode = line.substring(0, line.indexOf(", "));
            line = line.substring(line.indexOf(", ") + 2);
            int lineNumber = Integer.parseInt(line.substring(0, line.indexOf(", ")));
            line = line.substring(line.indexOf(", ") + 2);
            String tokenID = line.substring(0, line.indexOf(", "));
            String token = line.substring(line.indexOf(", ") + 2);
            TokenInformation tokenInformation = new TokenInformation(typeCode, lineNumber, tokenID, token);
            allTokens.add(tokenInformation);
            line = reader.readLine();
        }
        programParser();
        FileWriter symboltable = new FileWriter("symboltable.csv");
        for (int i = 0; i < outputTable.size(); i++)
            symboltable.write(outputTable.get(i).getName() + ", " + outputTable.get(i).getKind() + ", " +
                    outputTable.get(i).getType() + ", " + outputTable.get(i).getLineOfDeclaration() + ", " +
                    outputTable.get(i).getAccessModifier() + ", " + outputTable.get(i).getScope() + "\n");
        symboltable.close();
    }
}
