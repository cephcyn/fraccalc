
public class Test {

    public static void main(String[] args) {
        //test token count
        test("", "Too few tokens.");
        test("_ isAnUnderScore", "Too few tokens.");
        test("12", "Too few tokens.");
        test("12 34 56 78", "Too many tokens.");
        test("1/2 + 3/4 - 4", "Too many tokens.");
        //test first operand validity
        test("3.2 - 2", "\"3.2\" is not an acceptable operand.");
        test("3/-2 - 2", "\"3/-2\" is not an acceptable operand.");
        test("3_-3/2 - 2", "\"3_-3/2\" is not an acceptable operand.");
        test("3_3/-2 - 2", "\"3_3/-2\" is not an acceptable operand.");
        //test operator validity
        test("3 +- 2", "\"+-\" is not an acceptable operator.");
        //test second operand (third token) validity
        test("3 - 2.2", "\"2.2\" is not an acceptable operand.");
        test("3 - 2/-3", "\"2/-3\" is not an acceptable operand.");
        test("3 - 2_-2/3", "\"2_-2/3\" is not an acceptable operand.");
        test("3 - 2_2/-3", "\"2_2/-3\" is not an acceptable operand.");
        //TODO replace these with actual answers as program advances
        //test first operand valid cases
        test("3 - 2", "3/1 - 2/1");
        test("-3 - 2", "-3/1 - 2/1");
        test("+3 - 2", "3/1 - 2/1");
        test("3/2 - 2", "3/2 - 2/1");
        test("-3/2 - 2", "-3/2 - 2/1");
        test("+3/2 - 2", "3/2 - 2/1");
        test("3_3/2 - 2", "9/2 - 2/1");
        test("-3_3/2 - 2", "-9/2 - 2/1");
        test("+3_3/2 - 2", "9/2 - /12");
        //test valid operator cases
        test("3 - 2", "3/1 - 2/1");
        test("3 + 2", "3/1 + 2/1");
        test("3 * 2", "3/1 * 2/1");
        test("3 / 2", "3/1 / 2/1");
        //test second operand valid cases
        test("3 - 1", "3/1 - 1/1");
        test("3 - -1", "3/1 - -1/1");
        test("3 - +1", "3/1 - 1/1");
        test("3 - 1/2", "3/1 - 1/2");
        test("3 - -1/2", "3/1 - -1/2");
        test("3 - +1/2", "3/1 - 1/2");
        test("3 - 1_1/2", "3/1 - 3/2");
        test("3 - -1_1/2", "3/1 - -3/2");
        test("3 - +1_1/2", "3/1 - 3/2");
        //test spacing 
        //(various cases, can't possibly exhaust all but as the scanner.next() ignores spacing it should be fine)
        test("        3 - 2        ", "3/1 - 2/1");
        test("3         - 2", "3/1 - 2/1");
        test("3- 2         ", "Too few tokens."); //Not having a space between operator and operand is not good xD
        //tests special commands (really anything involving special commands)
        test("quit", "Goodbye.");
        test("    quit     ", "Goodbye.");
        test("quit quit", "Too few tokens.");
        test("quit quit quit", "\"quit\" is not acceptable an operator.");
        test("quit quit quit quit", "Too many tokens.");
        test("toggle","This function isn't working currently, but it hopefully will later!");
        test("    toggle    ","This function isn't working currently, but it hopefully will later!");
        test("toggle toggle","Too few tokens.");
        test("toggle toggle toggle","\"toggle\" is not an acceptable operator.");
        test("toggle toggle toggle toggle","Too many tokens.");
        test("help", FracCalc.helpText());
        test("    help      ", FracCalc.helpText());
        test("help help", "Too few tokens.");
        test("help help help", "\"help\" is not an acceptable operator.");
        test("help help help help", "Too many tokens.");
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
