
public class Test {

    public static void main(String[] args) {
        //test token count
        test("", "Too few tokens.");
        test("_ isAnUnderScore", "Too few tokens.");
        test("12", "Too few tokens.");
        test("12 34 56 78", "Too many tokens.");
        test("1/2 + 3/4 - 4 +", "Too many tokens.");
        //test first operand validity
        test("3.2 - 2", "\"3.2\" is not an acceptable operand.");
        test("3/-2 - 2", "\"3/-2\" is not an acceptable operand.");
        test("3_-3/2 - 2", "\"3_-3/2\" is not an acceptable operand.");
        test("3_3/-2 - 2", "\"3_3/-2\" is not an acceptable operand.");
        test("3_3\\2 - 2", "\"3_3\\-2\" is not an acceptable operand.");
        test("3/ - 2", "\"3/\" is not an acceptable operand.");
        test("_3/3 - 2", "\"_3/3\" is not an acceptable operand.");
        //test operator validity
        test("3 +- 2", "\"+-\" is not an acceptable operator.");
        //test second operand (third token) validity
        test("3 - 2.2", "\"2.2\" is not an acceptable operand.");
        test("3 - 2/-3", "\"2/-3\" is not an acceptable operand.");
        test("3 - 2_-2/3", "\"2_-2/3\" is not an acceptable operand.");
        test("3 - 2_2/-3", "\"2_2/-3\" is not an acceptable operand.");
        test("3 - 2_2\\3", "\"2_2\\3\" is not an acceptable operand.");
        test("3 - 2/", "\"2./\" is not an acceptable operand.");
        test("3 - _2/2", "\"_2/2\" is not an acceptable operand.");
        //test first operand valid cases
        //(various cases, can't possibly exhaust all)
        test("3 - 1", "2");
        test("-3 - 1", "-4");
        test("+3 - 1", "0");
        test("3/2 - 1", "1/2");
        test("-3/2 - 1", "-2_1/2");
        test("+3/2 - 1", "1/2");
        test("3_3/2 - 1", "3_1/2");
        test("-3_3/2 - 1", "-5_1/2");
        test("+3_3/2 - 1", "3_1/2");
        //test valid operator cases
        //(various cases, can't possibly exhaust all)
        test("3 - 2", "1");
        test("3 + 2", "5");
        test("3 * 2", "6");
        test("3 / 2", "1_1/2");
        //test second operand valid cases
        //(various cases, can't possibly exhaust all)
        test("2 - 1", "1");
        test("2 - -1", "3");
        test("2 - +1", "1");
        test("2 - 1/2", "1_1/2");
        test("2 - -1/2", "2_1/2");
        test("2 - +1/2", "1_1/2");
        test("2 - 1_1/2", "1/2");
        test("2 - -1_1/2", "3_1/2");
        test("2 - +1_1/2", "1/2");
        //test multiple operations cases
        //(various cases, can't possibly exhaust all)
        test("3 - 1 + 3", "5");
        test("3 - 1 * 4", "8");
        test("3/2 - 1/2 * 4", "4");
        test("5/4 - 1/2 / 3", "1/4");
        test("1/12 / 1/12 + 5/3", "2_2/3");
        //test divide by zero
        test("3/0 + 1", "Fractions and mixed fractions cannot have denominator 0.");
        test("1_3/0 + 1", "Fractions and mixed fractions cannot have denominator 0.");
        test("1 + 3/0", "Fractions and mixed fractions cannot have denominator 0.");
        test("1 + 1_3/0", "Fractions and mixed fractions cannot have denominator 0.");
        test("3/4 / 0/9","You cannot divide by zero.");
        test("32/3 / 0","You cannot divide by zero.");
        test("1_3/4 / 0/9","You cannot divide by zero.");
        //test spacing 
        //(various cases, can't possibly exhaust all but as the scanner.next() ignores spacing it should be fine)
        test("        3 - 2        ", "1");
        test("3         - 2", "1");
        test("3         - 2  +  5", "6");
        test("3- 2         ", "Too few tokens."); //Not having a space between operator and operand is not good xD
        //tests special commands (really anything involving special commands)
        test("quit", "Goodbye.");
        test("QUIT", "Goodbye.");
        test("QuIt", "Goodbye.");
        test("    quit     ", "Goodbye.");
        test("quit quit", "Too few tokens.");
        test("quit quit quit", "\"quit\" is not an acceptable operator.");
        test("quit quit quit quit", "Too many tokens.");
        test("quit quit quit quit quit", "\"quit\" is not an acceptable operator.");
        test("help", FracCalc.helpText());
        test("HELP", FracCalc.helpText());
        test("HeLp", FracCalc.helpText());
        test("    help      ", FracCalc.helpText());
        test("help help", "Too few tokens.");
        test("help help help", "\"help\" is not an acceptable operator.");
        test("help help help help", "Too many tokens.");
        test("help help help help help", "\"help\" is not an acceptable operator.");
    }

//  _____   ____    _   _  ____ _______   ______ _____ _____ _______ 
// |  __ \ / __ \  | \ | |/ __ \__   __| |  ____|  __ \_   _|__   __|
// | |  | | |  | | |  \| | |  | | | |    | |__  | |  | || |    | |   
// | |  | | |  | | | . ` | |  | | | |    |  __| | |  | || |    | |   
// | |__| | |__| | | |\  | |__| | | |    | |____| |__| || |_   | |   
// |_____/ \____/  |_| \_|\____/  |_|    |______|_____/_____|  |_|   
    private static void test(String input, String expected) {
        try {
            String actual = FracCalc.calc(input);

            if (actual.equals(expected)) {
                System.out.print("Success  ");
            } else {
                System.out.print("Fail     ");
            }

            System.out.println(" : input = \"" + input + "\", expected \"" + expected + "\", actual = \"" + actual + "\"");
        } catch (Exception e) {
            System.out.println("Exception : " + input + ", " + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}
