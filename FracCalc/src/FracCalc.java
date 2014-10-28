
import java.util.*;

public class FracCalc {

    public static String calc(String input) {
        input = input.toLowerCase();
        Scanner stringscan = new Scanner(input);
        String output = "";
        //Detects special commands such as "toggle", "help" and "quit"
        //TODO implement toggle command
        if (stringscan.hasNext("toggle") && wordCount(input) == 1) {
            return "This function isn't working currently, but it hopefully will later!";
        }
        if (stringscan.hasNext("help") && wordCount(input) == 1) {
            //TODO read special commands in any case (uppercase, lowercase, mixed)
            //Returns help
            return helpText();
        } else if (stringscan.hasNext("quit") && wordCount(input) == 1) {
            //Quits program (actual quit command detection is in the main method)
            return "Goodbye.";
        } else {
            //Tests token count, returns error if not acceptable
            if (wordCount(input) < 3) {
                return "Too few tokens.";
            } else if (wordCount(input) > 3) {
                return "Too many tokens.";
            }
        }
        //Parses string for the three tokens, then checks their validity
        String token1 = stringscan.next();
        String token2 = stringscan.next();
        String token3 = stringscan.next();
        if (!isOperator(token2)) {
            return "\"" + token2 + "\" is not an acceptable operator.";
        }
        if (!isInteger(token1) && !isFraction(token1) && !isMixed(token1)) {
            return "\"" + token1 + "\" is not an acceptable operand.";
        }
        if (!isInteger(token3) && !isFraction(token3) && !isMixed(token3)) {
            return "\"" + token3 + "\" is not an acceptable operand.";
        }
        stringscan.close();
        token1 = toImproper(token1);
        token3 = toImproper(token3);
        return token1 + " " + token2 + " " + token3;
    }

    public static int gcf(int a, int b) {
        //Finds the greatest common factor between A and B
        //Always returns a positive value
        int trans = 0;
        while (b != 0) {
            a = a % b;
            trans = a;
            a = b;
            b = trans;
        }
        return Math.abs(a);
    }

    public static String helpText() {
        //TODO update help text as needed
        return "FracCalc takes two integers, fractions, or mixed fractions and \n"
                + "an operator, and does the appropriate operation on them, then \n"
                + "returns an appropriately simplified integer, fraction, or mixed fraction. \n"
                + "Any operand may have a + or - preceding it (positive and negative signs) \n"
                + "but a sign placed anywhere else is not accepted. \n"
                + "The proper format for an integer is \n"
                + "<+ or - optional><positive whole number>. \n"
                + "The proper format for a fraction is \n"
                + "<+ or - optional><positive whole number>/<positive whole number> \n"
                + "The proper format for a mixed fraction is \n"
                + "<+ or - optional><positive whole number>_<positive whole number>/<positive whole number> \n"
                + "The only accepted operators are \n"
                + "+ (addition), - (subtraction), * (multiplication), and / (division). \n"
                + "There are three special commands: \n"
                + "Type \"toggle\" to switch between output as improper fractions or completely reduced output, \n"
                + "\"help\" to get help (you're reading this right now!) \n"
                + "or type \"quit\" to quit.";
    }

    public static String toImproper(String input) {
        //This method converts any proper operand to an Integer or Fraction/Improper Fraction, without (redundant) positive signs
        String spaced = spaceString(input);
        String output = "";
        int intpart = 0;
        int toppart = 0;
        int botpart = 1;
        //parses spaced string and turns it into a set of three ints
        if (isMixed(input)) {
            Scanner mixscan = new Scanner(spaced);
            intpart = mixscan.nextInt();
            toppart = mixscan.nextInt();
            botpart = mixscan.nextInt();
            mixscan.close();
        } else if (isFraction(input)) {
            Scanner fracscan = new Scanner(spaced);
            toppart = fracscan.nextInt();
            botpart = fracscan.nextInt();
            fracscan.close();
        } else {
            //Ints should also have a fracline... example, "7" -> "7/1"
            Scanner intscan = new Scanner(spaced);
            intpart = intscan.nextInt();
            intscan.close();
        }
        //combines int into fraction
        if (intpart >= 0) {
            toppart += intpart * botpart;
        } else {
            toppart += -1 * intpart * botpart;
            toppart *= -1;
        }
        output = toppart + "/" + botpart;
        return output;
    }

    public static String spaceString(String input) {
        //spaces out the int parts ("-3_3/4" goes to "-3 3 4")
        return input.replace('_', ' ').replace('/',' ');
    }

    public static boolean isInteger(String input) {
        //Tests if the given string is an integer
        for (int i = 1; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        if (Character.isDigit(input.charAt(0)) || input.charAt(0) == '-' || input.charAt(0) == '+') {
            return true;
        }
        return false;
    }

    public static boolean isFraction(String input) {
        //Tests if the given string is a fraction, NOT mixed
        //Fractions with signed numbers in denominators WILL NOT PASS
        int fracline = -1;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '/') {
                fracline = i;
                if (isInteger(input.substring(0, fracline))
                        && isInteger(input.substring(fracline + 1))
                        && Character.isDigit(input.charAt(fracline + 1))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isMixed(String input) {
        //Tests if the given string is a mixed fraction
        //Mixed with signed numbers in numerator or denominator WILL NOT PASS
        int underdash = -1;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '_') {
                underdash = i;
                if (isInteger(input.substring(0, underdash))
                        && isFraction(input.substring(underdash + 1))
                        && Character.isDigit(input.charAt(underdash + 1))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isOperator(String input) {
        //To test if the given string is an operator
        return (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/"));
    }

    public static int wordCount(String input) {
        //Counts the number of tokens
        //Used for too many/too few error reporting
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != ' ') {
                if (i == 0 || input.charAt(i - 1) == ' ') {
                    count++;
                }
            }
        }
        return count;
    }
}
