public class Test {

    public static void main(String[] args) {
        //test token count
        test("", "Too little input.");
        test("_ isAnUnderScore", "Too little input.");
        test("12", "Too little input.");
        test("12 34 56 78", "Too much input.");
        test("1/2 + 3/4 - 4 +", "Too much input.");

        //test first operand validity
        test("3.2 - 2", "\"3.2\" is not an acceptable operand.");
        test("3/-2 - 2", "\"3/-2\" is not an acceptable operand.");
        test("3_-3/2 - 2", "\"3_-3/2\" is not an acceptable operand.");
        test("3_3/-2 - 2", "\"3_3/-2\" is not an acceptable operand.");
        test("3_3\\2 - 2", "\"3_3\\2\" is not an acceptable operand.");
        test("3/ - 2", "\"3/\" is not an acceptable operand.");
        test("_3/3 - 2", "\"_3/3\" is not an acceptable operand.");
        test("1_1/ + 1_1/", "\"1_1/\" is not an acceptable operand.");
        test("1_/ + 1_/", "\"1_/\" is not an acceptable operand.");

        //test operator validity
        test("3 +- 2", "\"+-\" is not an acceptable operator.");

        //test second operand (third token) validity
        test("3 - 2.2", "\"2.2\" is not an acceptable operand.");
        test("3 - 2/-3", "\"2/-3\" is not an acceptable operand.");
        test("3 - 2_-2/3", "\"2_-2/3\" is not an acceptable operand.");
        test("3 - 2_2/-3", "\"2_2/-3\" is not an acceptable operand.");
        test("3 - 2_2\\3", "\"2_2\\3\" is not an acceptable operand.");
        test("3 - 2/", "\"2/\" is not an acceptable operand.");
        test("3 - _2/2", "\"_2/2\" is not an acceptable operand.");

        //test first operand valid cases
        //(various cases, can't possibly exhaust all)
        test("3 - 1", "2");
        test("-3 - 1", "-4");
        test("+3 - 1", "2");
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

        //test extraneous numbers cases
        //(various cases, can't possibly exhaust all)
        test("0000 + 00000000", "0");
        test("000001 + 0000002", "3");
        test("000002/3 + 2/3", "1_1/3");
        test("2/0000003 + 2/3", "1_1/3");
        test("1_0000002/000000003 + 1/3", "2");

        //test divide by zero
        test("3/0 + 1", "Fractions and mixed fractions cannot have denominator 0.");
        test("1_3/0 + 1", "Fractions and mixed fractions cannot have denominator 0.");
        test("1 + 3/0", "Fractions and mixed fractions cannot have denominator 0.");
        test("1 + 1_3/0", "Fractions and mixed fractions cannot have denominator 0.");
        test("3/4 / 0/9", "You cannot divide by zero.");
        test("32/3 / 0", "You cannot divide by zero.");
        test("1_3/4 / 0/9", "You cannot divide by zero.");

        //test spacing 
        //(various cases, can't possibly exhaust all but as the scanner.next() ignores spacing it should be fine)
        test("        3 - 2        ", "1");
        test("3         - 2", "1");
        test("3         - 2  +  5", "6");
        test("3- 2         ", "Too little input."); //Not having a space between operator and operand is not good xD

        //tests special commands (really anything involving special commands)
        //begin 'quit' tests
        test("quit", "Goodbye.");
        test("QUIT", "Goodbye.");
        test("QuIt", "Goodbye.");
        test("    quit     ", "Goodbye.");
        test("quit quit", "Too little input.");
        test("quit quit quit", "\"quit\" is not an acceptable operator.");
        test("quit quit quit quit", "Too much input.");
        test("quit quit quit quit quit", "\"quit\" is not an acceptable operator.");
        
        //begin 'help' tests
        test("help", FracCalc.helpText());
        test("HELP", FracCalc.helpText());
        test("HeLp", FracCalc.helpText());
        test("    help      ", FracCalc.helpText());
        test("help help", "Too little input.");
        test("help help help", "\"help\" is not an acceptable operator.");
        test("help help help help", "Too much input.");
        test("help help help help help", "\"help\" is not an acceptable operator.");
        
        //begin toggle tests (as the improper variable is persistent, this has to be the last category)
        test("toggle", "Return input as improper fractions is now: true");
        test("tOgGlE", "Return input as improper fractions is now: false");
        test("toggle", "Return input as improper fractions is now: true");
        test("3/5 + 3/5", "6/5");
        test("2/7 + 2/7", "4/7");
        test("3/5 + 3/5 + 3/5", "9/5");
        test("toggle", "Return input as improper fractions is now: false");
        test("3/5 + 3/5", "1_1/5");
        test("2/7 + 2/7", "4/7");
        test("3/5 + 3/5 + 3/5", "1_4/5");
        test("toggle toggle", "Too little input.");
        test("toggle toggle toggle", "\"toggle\" is not an acceptable operator.");
        test("toggle toggle toggle toggle", "Too much input.");
        test("toggle toggle toggle toggle toggle", "\"toggle\" is not an acceptable operator.");

        //Teachers' test cases follow
        test("quit", "Goodbye.");
        test("  quit", "Goodbye.");
        test("quit  ", "Goodbye.");
        test("  quit  ", "Goodbye.");
        test("QuIt", "Goodbye.");
        test("help", FracCalc.helpText());
        test("  help", FracCalc.helpText());
        test("help  ", FracCalc.helpText());
        test("  help  ", FracCalc.helpText());
        test("HelP", FracCalc.helpText());
        test("quit it", "Too little input.");
        test("quit talking to me", "Too much input.");
        test("help me", "Too little input.");
        test("help me I'm falling", "Too much input.");
        test("1 + 1 =", "Too much input.");
        // bad operands both the same to catch
        // people who don't reuse code for both
        test("1__1/1 + 1__1/1", "\"1__1/1\" is not an acceptable operand.");
        test("1_1//1 + 1_1//1", "\"1_1//1\" is not an acceptable operand.");
        test("1_-1/1 + 1_-1/1", "\"1_-1/1\" is not an acceptable operand.");
        test("1_1/-1 + 1_1/-1", "\"1_1/-1\" is not an acceptable operand.");
        test("1_+1/1 + 1_+1/1", "\"1_+1/1\" is not an acceptable operand.");
        test("1_1/+1 + 1_1/+1", "\"1_1/+1\" is not an acceptable operand.");
        test("1a1_1/1 + 1a1_1/1", "\"1a1_1/1\" is not an acceptable operand.");
        test("1_1a1/1 + 1_1a1/1", "\"1_1a1/1\" is not an acceptable operand.");
        test("1_1/1a1 + 1_1/1a1", "\"1_1/1a1\" is not an acceptable operand.");
        test("a_1/1 + a_1/1", "\"a_1/1\" is not an acceptable operand.");
        test("1_a/1 + 1_a/1", "\"1_a/1\" is not an acceptable operand.");
        test("1_1/a + 1_1/a", "\"1_1/a\" is not an acceptable operand.");
        test("1/1_1 + 1/1_1", "\"1/1_1\" is not an acceptable operand.");
        test("1_1/ + 1_1/", "\"1_1/\" is not an acceptable operand.");
        test("1_1 + 1_1", "\"1_1\" is not an acceptable operand.");
        test("1_ + 1_", "\"1_\" is not an acceptable operand.");
        test("1_/ + 1_/", "\"1_/\" is not an acceptable operand.");
        test("1/ + 1/", "\"1/\" is not an acceptable operand.");
        test("_/ + _/", "\"_/\" is not an acceptable operand.");
        test("_1/1 + _1/1", "\"_1/1\" is not an acceptable operand.");
        test("1 a 1", "\"a\" is not an acceptable operator.");
        test("1 % 1", "\"%\" is not an acceptable operator.");
        test("1a1_1/1 + 1a1_1/1", "\"1a1_1/1\" is not an acceptable operand.");
        test("1 + 1 a", "Too much input.");
        test("--1 + --1", "\"--1\" is not an acceptable operand.");
        test("+-1 + +-1", "\"+-1\" is not an acceptable operand.");
        test("++1 + ++1", "\"++1\" is not an acceptable operand.");
        // valid with extra spaces
        test("  1 + 1", "2");
        test("1   + 1", "2");
        test("1 +   1", "2");
        test("1 + 1  ", "2");
        test("  1   + 1", "2");
        test("  1 +   1", "2");
        test("  1 + 1  ", "2");
        test("1   +   1", "2");
        test("1   + 1  ", "2");
        test("1 +   1  ", "2");
        // valid with long numbers
        test("123/456 + 123/456", "41/76");
        test("-123/456 + -123/456", "-41/76");
        test("+123/456 + +123/456", "41/76");
        // valid that cannot be reduced (uses primes)
        test("1/2210 + 1/4389", "6599/9699690");
        test("1/2210 - 1/4389", "2179/9699690");
        test("1/2210 * 1/4389", "1/9699690");
        test("1/2210 / 1/4389", "1_2179/2210");
        test("23/2210 + 41/4389", "191557/9699690");
        test("29/2210 - 43/4389", "32251/9699690");
        test("31/2210 * 47/4389", "1457/9699690");
        test("37/2210 / 53/4389", "1_45263/117130");
        test("30/77 + 77/30", "2_2209/2310");
        test("30/77 - 77/30", "-2_409/2310");
        test("30/77 * 77/30", "1");
        test("30/77 / 77/30", "900/5929");
        test("30/77 + -77/30", "-2_409/2310");
        test("30/77 - -77/30", "2_2209/2310");
        test("30/77 * -77/30", "-1");
        test("30/77 / -77/30", "-900/5929");
        // whole and whole
        test("123 + 456", "579");
        test("123 - 456", "-333");
        test("123 * 456", "56088");
        test("123 / 456", "41/152");
        test("123 + +456", "579");
        test("123 - +456", "-333");
        test("123 * +456", "56088");
        test("123 / +456", "41/152");
        test("+123 + 456", "579");
        test("+123 - 456", "-333");
        test("+123 * 456", "56088");
        test("+123 / 456", "41/152");
        test("123 + -456", "-333");
        test("123 - -456", "579");
        test("123 * -456", "-56088");
        test("123 / -456", "-41/152");
        test("-123 + 456", "333");
        test("-123 - 456", "-579");
        test("-123 * 456", "-56088");
        test("-123 / 456", "-41/152");
        // whole and fraction
        test("1 + 1/2", "1_1/2");
        test("2 - 1/2", "1_1/2");
        test("3 * 1/2", "1_1/2");
        test("4 / 1/2", "8");
        test("1 + +1/2", "1_1/2");
        test("2 - +1/2", "1_1/2");
        test("3 * +1/2", "1_1/2");
        test("4 / +1/2", "8");
        test("+1 + 1/2", "1_1/2");
        test("+2 - 1/2", "1_1/2");
        test("+3 * 1/2", "1_1/2");
        test("+4 / 1/2", "8");
        test("1 + -1/2", "1/2");
        test("2 - -1/2", "2_1/2");
        test("3 * -1/2", "-1_1/2");
        test("4 / -1/2", "-8");
        test("-1 + -1/2", "-1_1/2");
        test("-2 - -1/2", "-1_1/2");
        test("-3 * -1/2", "1_1/2");
        test("-4 / -1/2", "8");
        // fraction and whole
        test("1/2 + 1", "1_1/2");
        test("1/2 - 2", "-1_1/2");
        test("1/2 * 3", "1_1/2");
        test("1/2 / 4", "1/8");
        test("+1/2 + 1", "1_1/2");
        test("+1/2 - 2", "-1_1/2");
        test("+1/2 * 3", "1_1/2");
        test("+1/2 / 4", "1/8");
        test("1/2 + +1", "1_1/2");
        test("1/2 - +2", "-1_1/2");
        test("1/2 * +3", "1_1/2");
        test("1/2 / +4", "1/8");
        test("1/2 + -1", "-1/2");
        test("1/2 - -2", "2_1/2");
        test("1/2 * -3", "-1_1/2");
        test("1/2 / -4", "-1/8");
        test("-1/2 + 1", "1/2");
        test("-1/2 - 2", "-2_1/2");
        test("-1/2 * 3", "-1_1/2");
        test("-1/2 / 4", "-1/8");
        test("-1/2 + -1", "-1_1/2");
        test("-1/2 - -2", "1_1/2");
        test("-1/2 * -3", "1_1/2");
        test("-1/2 / -4", "1/8");
        // these will fail if program does not
        // properly find duplicate factors
        test("2048/4096 + 2048/4096", "1");
        test("2048/4096 - 2048/4096", "0");
        test("2048/4096 * 2048/4096", "1/4");
        test("2048/4096 / 2048/4096", "1");
        test("2048/4096 + +2048/4096", "1");
        test("2048/4096 - +2048/4096", "0");
        test("2048/4096 * +2048/4096", "1/4");
        test("2048/4096 / +2048/4096", "1");
        test("+2048/4096 + 2048/4096", "1");
        test("+2048/4096 - 2048/4096", "0");
        test("+2048/4096 * 2048/4096", "1/4");
        test("+2048/4096 / 2048/4096", "1");
        test("2048/4096 + -2048/4096", "0");
        test("2048/4096 - -2048/4096", "1");
        test("2048/4096 * -2048/4096", "-1/4");
        test("2048/4096 / -2048/4096", "-1");
        test("-2048/4096 + 2048/4096", "0");
        test("-2048/4096 - 2048/4096", "-1");
        test("-2048/4096 * 2048/4096", "-1/4");
        test("-2048/4096 / 2048/4096", "-1");
        test("-2048/4096 + -2048/4096", "-1");
        test("-2048/4096 - -2048/4096", "0");
        test("-2048/4096 * -2048/4096", "1/4");
        test("-2048/4096 / -2048/4096", "1");
        // fractions same denominator
        test("1/2 + 1/2", "1");
        test("1/2 - 1/2", "0");
        test("1/2 * 1/2", "1/4");
        test("1/2 / 1/2", "1");
        test("1/2 + +1/2", "1");
        test("1/2 - +1/2", "0");
        test("1/2 * +1/2", "1/4");
        test("1/2 / +1/2", "1");
        test("+1/2 + 1/2", "1");
        test("+1/2 - 1/2", "0");
        test("+1/2 * 1/2", "1/4");
        test("+1/2 / 1/2", "1");
        test("1/2 + -1/2", "0");
        test("1/2 - -1/2", "1");
        test("1/2 * -1/2", "-1/4");
        test("1/2 / -1/2", "-1");
        test("-1/2 + 1/2", "0");
        test("-1/2 - 1/2", "-1");
        test("-1/2 * 1/2", "-1/4");
        test("-1/2 / 1/2", "-1");
        test("-1/2 + -1/2", "-1");
        test("-1/2 - -1/2", "0");
        test("-1/2 * -1/2", "1/4");
        test("-1/2 / -1/2", "1");
        // fractions different denominator
        test("1/2 + 1/3", "5/6");
        test("1/2 - 1/3", "1/6");
        test("1/2 * 1/3", "1/6");
        test("1/2 / 1/3", "1_1/2");
        test("1/2 + +1/3", "5/6");
        test("1/2 - +1/3", "1/6");
        test("1/2 * +1/3", "1/6");
        test("1/2 / +1/3", "1_1/2");
        test("+1/2 + 1/3", "5/6");
        test("+1/2 - 1/3", "1/6");
        test("+1/2 * 1/3", "1/6");
        test("+1/2 / 1/3", "1_1/2");
        test("1/2 + -1/3", "1/6");
        test("1/2 - -1/3", "5/6");
        test("1/2 * -1/3", "-1/6");
        test("1/2 / -1/3", "-1_1/2");
        test("-1/2 + 1/3", "-1/6");
        test("-1/2 - 1/3", "-5/6");
        test("-1/2 * 1/3", "-1/6");
        test("-1/2 / 1/3", "-1_1/2");
        test("-1/2 + -1/3", "-5/6");
        test("-1/2 - -1/3", "-1/6");
        test("-1/2 * -1/3", "1/6");
        test("-1/2 / -1/3", "1_1/2");
        // mixed & fraction same denominator
        test("1_1/2 + 1/2", "2");
        test("1_1/2 - 1/2", "1");
        test("1_1/2 * 1/2", "3/4");
        test("1_1/2 / 1/2", "3");
        test("1_1/2 + +1/2", "2");
        test("1_1/2 - +1/2", "1");
        test("1_1/2 * +1/2", "3/4");
        test("1_1/2 / +1/2", "3");
        test("+1_1/2 + 1/2", "2");
        test("+1_1/2 - 1/2", "1");
        test("+1_1/2 * 1/2", "3/4");
        test("+1_1/2 / 1/2", "3");
        test("1_1/2 + -1/2", "1");
        test("1_1/2 - -1/2", "2");
        test("1_1/2 * -1/2", "-3/4");
        test("1_1/2 / -1/2", "-3");
        test("-1_1/2 + 1/2", "-1");
        test("-1_1/2 - 1/2", "-2");
        test("-1_1/2 * 1/2", "-3/4");
        test("-1_1/2 / 1/2", "-3");
        // mixed & fraction different denominator
        test("1_1/2 + 1/4", "1_3/4");
        test("1_1/2 - 1/4", "1_1/4");
        test("1_1/2 * 1/4", "3/8");
        test("1_1/2 / 1/4", "6");
        test("1_1/2 + +1/4", "1_3/4");
        test("1_1/2 - +1/4", "1_1/4");
        test("1_1/2 * +1/4", "3/8");
        test("1_1/2 / +1/4", "6");
        test("+1_1/2 + 1/4", "1_3/4");
        test("+1_1/2 - 1/4", "1_1/4");
        test("+1_1/2 * 1/4", "3/8");
        test("+1_1/2 / 1/4", "6");
        test("1_1/2 + -1/4", "1_1/4");
        test("1_1/2 - -1/4", "1_3/4");
        test("1_1/2 * -1/4", "-3/8");
        test("1_1/2 / -1/4", "-6");
        test("-1_1/2 + 1/4", "-1_1/4");
        test("-1_1/2 - 1/4", "-1_3/4");
        test("-1_1/2 * 1/4", "-3/8");
        test("-1_1/2 / 1/4", "-6");
        test("-1_1/2 + -1/4", "-1_3/4");
        test("-1_1/2 - -1/4", "-1_1/4");
        test("-1_1/2 * -1/4", "3/8");
        test("-1_1/2 / -1/4", "6");
        // mixed & mixed same denominator
        test("1_1/2 + 3_1/2", "5");
        test("1_1/2 - 3_1/2", "-2");
        test("1_1/2 * 3_1/2", "5_1/4");
        test("1_1/2 / 3_1/2", "3/7");
        test("1_1/2 + +3_1/2", "5");
        test("1_1/2 - +3_1/2", "-2");
        test("1_1/2 * +3_1/2", "5_1/4");
        test("1_1/2 / +3_1/2", "3/7");
        test("+1_1/2 + 3_1/2", "5");
        test("+1_1/2 - 3_1/2", "-2");
        test("+1_1/2 * 3_1/2", "5_1/4");
        test("+1_1/2 / 3_1/2", "3/7");
        test("1_1/2 + -3_1/2", "-2");
        test("1_1/2 - -3_1/2", "5");
        test("1_1/2 * -3_1/2", "-5_1/4");
        test("1_1/2 / -3_1/2", "-3/7");
        test("-1_1/2 + 3_1/2", "2");
        test("-1_1/2 - 3_1/2", "-5");
        test("-1_1/2 * 3_1/2", "-5_1/4");
        test("-1_1/2 / 3_1/2", "-3/7");
        test("-1_1/2 + -3_1/2", "-5");
        test("-1_1/2 - -3_1/2", "2");
        test("-1_1/2 * -3_1/2", "5_1/4");
        test("-1_1/2 / -3_1/2", "3/7");
        // valid with leading zeroes
        test("01 + 01", "2");
        test("01/01 + 01/01", "2");
        test("01_01/01 + 01_01/01", "4");
        // more valid cases 
        test("1_1/4 + 1_1/4", "2_1/2");
        test("-1_1/4 + -1_1/4", "-2_1/2");
        test("1/2 + 1/2", "1");
        test("-1/2 + -1/2", "-1");
        test("1 + 1", "2");
        test("1_1/1 + 1_1/1", "4");
        test("-1_1/1 + -1_1/1", "-4");
        test("+1 + +1", "2");
        test("-1 + -1", "-2");
        test("1 + 1", "2");
        test("1 - 1", "0");
        test("1 * 1", "1");
        test("1 / 1", "1");
        test("1 + 0", "1");
        test("1 + -0", "1");
        test("0_1/1 + 0", "1");
        test("1_0/1 + 0", "1");
        test("0 + 1_1/0", "Fractions and mixed fractions cannot have denominator 0.");
        test("0 - 1_1/0", "Fractions and mixed fractions cannot have denominator 0.");
        test("2/3 / 0/1", "You cannot divide by zero.");
        test("2/3 / 0", "You cannot divide by zero.");
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
            System.out.println("Exception : " + input + ", " + e);
            StackTraceElement[] stackTrace = e.getStackTrace();

            for (StackTraceElement ste : stackTrace) {
                System.out.println("    " + ste);
            }
        }
    }
}
