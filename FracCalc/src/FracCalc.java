
import java.util.*;

public class FracCalc {

    public static String calc(String input) {
        input = input.toLowerCase();
        Scanner stringscan = new Scanner(input);
        String token1 = "";
        String token2 = "";
        String token3 = "";
        //Detects special commands such as "help" and "quit"
        if (stringscan.hasNext("help") && wordCount(input) == 1) {
            //Returns help
            return helpText();
        } else if (stringscan.hasNext("quit") && wordCount(input) == 1) {
            //Quits program (actual quit command detection is in the main method)
            return "Goodbye.";
        } else {
            //Tests token count, returns error if not acceptable
            //Recognizes if there's an odd word count, and multiple commands
            if (wordCount(input) < 3) {
                return "Too few tokens.";
            } else if ((wordCount(input) > 3) && (wordCount(input) % 2 == 0)) {
                return "Too many tokens.";
            }
        }
        token1 = stringscan.next();
        //after this point stringscan always has an even number of tokens going in
        while (stringscan.hasNext()) {
            //Parses string for next two tokens, then checks their validity
            token2 = stringscan.next();
            token3 = stringscan.next();
            if (!isOperator(token2)) {
                return "\"" + token2 + "\" is not an acceptable operator.";
            }
            if (!isInteger(token1) && !isFraction(token1) && !isMixed(token1)) {
                return "\"" + token1 + "\" is not an acceptable operand.";
            }
            if (!isInteger(token3) && !isFraction(token3) && !isMixed(token3)) {
                return "\"" + token3 + "\" is not an acceptable operand.";
            }
            //to improper, as well as division by 0 error checking
            token1 = toImproper(token1);
            if (token1.equals("0")) {
                return "Fractions and mixed fractions cannot have denominator 0.";
            }
            token3 = toImproper(token3);
            if (token3.equals("0")) {
                return "Fractions and mixed fractions cannot have denominator 0.";
            }
            token1 = processImproper(token1, token2, token3);
            if (token1.equals("0")) {
                return "You cannot divide by zero.";
            }
            token1 = simplify(token1);
        }
        stringscan.close();
        return token1;
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

    public static int lcm(int a, int b) {
        //Finds the lowest common multiple between A and B
        //Always returns positive value
        int result = a;
        while (result % b != 0) {
            result += a;
        }
        return result;
    }

    public static String helpText() {
        return "FracCalc takes several integers, fractions, or mixed fractions and \n"
                + "some operators, and does the appropriate operation(s) on them, then \n"
                + "returns an appropriately simplified integer, fraction, or mixed fraction. \n"
                + "Any operand may have a + or - preceding it (positive and negative signs) \n"
                + "but a sign placed anywhere else is not accepted. \n"
                + "Note that all operations are performed LEFT TO RIGHT, \n"
                + "regardless of operator precedence: \n"
                + "\"3 + 4 * 5\" would return \"35\", not \"23\". \n"
                + "The proper format for an integer is \n"
                + "<+ or - optional><positive whole number>. \n"
                + "The proper format for a fraction is \n"
                + "<+ or - optional><positive whole number>/<positive whole number> \n"
                + "The proper format for a mixed fraction is \n"
                + "<+ or - optional><positive whole number>_<positive whole number>/<positive whole number> \n"
                + "The only accepted operators are \n"
                + "+ (addition), - (subtraction), * (multiplication), and / (division). \n"
                + "There are two special commands: \n"
                + "Type \"help\" to get help (you're reading this right now!) \n"
                + "and type \"quit\" to quit.";
    }

    public static String simplify(String input) {
        //input is DEFINITELY a valid fraction
        //is called from both calc and working methods, so it has to be slash tolerant
        input = spaceString(input);
        Scanner inputscan = new Scanner(input);
        int intp = 0;
        int topp = inputscan.nextInt();
        int botp = inputscan.nextInt();
        inputscan.close();
        boolean negative = topp < 0;
        topp = Math.abs(topp);
        while (topp >= botp) {
            intp++;
            topp -= botp;
        }
        //Assesses whether or not a negative sign is necessary, then returns.
        if (topp == 0) {
            if (negative) {
                return "-" + intp;
            } else {
                return intp + "";
            }
        } else if (intp == 0) {
            if (negative) {
                return "-" + topp + "/" + botp;
            } else {
                return topp + "/" + botp;
            }
        } else {
            if (negative) {
                return "-" + intp + "_" + topp + "/" + botp;
            } else {
                return intp + "_" + topp + "/" + botp;
            }
        }
    }

    public static String processImproper(String token1, String token2, String token3) {
        //read input into nums and denoms
        Scanner op1scan = new Scanner(token1);
        int op1num = op1scan.nextInt();
        int op1den = op1scan.nextInt();
        op1scan.close();
        Scanner op2scan = new Scanner(token3);
        int op2num = op2scan.nextInt();
        int op2den = op2scan.nextInt();
        op2scan.close();
        //Performs the appropriate operation on the two fractions
        if (token2.equals("+")) {
            return add(op1num, op1den, op2num, op2den);
        } else if (token2.equals("-")) {
            return subtract(op1num, op1den, op2num, op2den);
        } else if (token2.equals("*")) {
            return multiply(op1num, op1den, op2num, op2den);
        } else { //if token2.equals("/")
            if (op2num == 0) {
                return "0";
            }
            return divide(op1num, op1den, op2num, op2den);
        }
    }

    public static String add(int op1num, int op1den, int op2num, int op2den) {
        //perform operation
        op1num *= (lcm(op1den, op2den) / op1den);
        op1den *= (lcm(op1den, op2den) / op1den);
        op2num *= (lcm(op1den, op2den) / op2den);
        op1num += op2num;
        //Simplify and return
        return op1num / gcf(op1num, op1den) + " " + op1den / gcf(op1num, op1den);
    }

    public static String subtract(int op1num, int op1den, int op2num, int op2den) {
        //perform operation
        op1num *= (lcm(op1den, op2den) / op1den);
        op1den *= (lcm(op1den, op2den) / op1den);
        op2num *= (lcm(op1den, op2den) / op2den);
        op1num -= op2num;
        //Simplify and return
        return op1num / gcf(op1num, op1den) + " " + op1den / gcf(op1num, op1den);
    }

    public static String divide(int op1num, int op1den, int op2num, int op2den) {
        if (op2num == 0) {
            return "0";
        }
        //perform operation, remember that 1/2 / 1/4 = 1/2 * 4/1
        int finnum = op1num * op2den;
        int finden = op1den * op2num;
        //Simplify and return
        return finnum / gcf(finnum, finden) + " " + finden / gcf(finnum, finden);
    }

    public static String multiply(int op1num, int op1den, int op2num, int op2den) {
        //perform operation
        int finnum = op1num * op2num;
        int finden = op1den * op2den;
        //Simplify and return
        return finnum / gcf(finnum, finden) + " " + finden / gcf(finnum, finden);
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
        if (botpart == 0) {
            return "0";
        }
        if (intpart >= 0) {
            toppart += intpart * botpart;
        } else {
            toppart += -1 * intpart * botpart;
            toppart *= -1;
        }
        output = toppart + " " + botpart;
        return output;
        //
    }

    public static String spaceString(String input) {
        //spaces out the int parts ("-3_3/4" goes to "-3 3 4")
        return input.replace('_', ' ').replace('/', ' ');
    }

    public static boolean isInteger(String input) {
        //Tests if the given string is an integer
        for (int i = 1; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        if (Character.isDigit(input.charAt(0))
                || input.charAt(0) == '-'
                || input.charAt(0) == '+') {
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
