import java.util.Scanner;

public class Fraction {

    private int numerator;
    private int denominator;

    public Fraction(int whole, int numerator, int denominator) {
        //Error checking
        if (denominator <= 0) {
            throw new IllegalArgumentException("Denominator must be positive.");
        }
        //setting
        this.denominator = denominator;
        this.numerator = whole * denominator + (whole/Math.abs(whole)) * numerator;
        simplify();
    }

    public Fraction(int numerator, int denominator) {
        //Error checking
        if (denominator <= 0) {
            throw new IllegalArgumentException("Denominator must be positive.");
        }
        //setting
        this.denominator = denominator;
        this.numerator = numerator;
        simplify();

    }

    public Fraction(int whole) {
        //I can't think of anything that would screw THIS up...
        this.numerator = whole;
        this.denominator = 1;
    }

    public Fraction(String s) {
        //reading the string
        s = s.replace("/", " ").replace("_", " ");
        Scanner sscan = new Scanner(s);
        int twhole;
        int tnum;
        int tden;
        if (wordCount(s) == 3) {
            //mixed fraction
            twhole = sscan.nextInt();
            tnum = sscan.nextInt();
            tden = sscan.nextInt();
        } else if (wordCount(s) == 2) {
            //fraction
            twhole = 0;
            tnum = sscan.nextInt();
            tden = sscan.nextInt();
        } else {
            //integer
            twhole = sscan.nextInt();
            tnum = 0;
            tden = 1;
        }
        //error checking
        if (tden <= 0) {
            throw new IllegalArgumentException("Denominator must be positive.");
        }
        //setting
        this.denominator = tden;
        this.numerator = twhole * tden + tnum;
        simplify();
    }

    public Fraction add(Fraction frac) {
        int newden = lcd(this.denominator, frac.denominator);
        int newnum = this.numerator * (this.denominator / newden)
                + frac.numerator * (frac.denominator / newden);
        //the fraction object simplifies upon init, so we don't have to do that here
        return new Fraction(newnum, newden);
    }

    public Fraction subtract(Fraction frac) {
        int newden = lcd(this.denominator, frac.denominator);
        int newnum = this.numerator * (this.denominator / newden)
                - frac.numerator * (frac.denominator / newden);
        //the fraction object simplifies upon init, so we don't have to do that here
        return new Fraction(newnum, newden);
    }

    public Fraction multiply(Fraction frac) {
        int newnum = this.numerator * frac.numerator;
        int newden = this.denominator * frac.denominator;
        //the fraction object simplifies upon init, so we don't have to do that here
        return new Fraction(newnum, newden);
    }

    public Fraction divide(Fraction frac) {
        //1/2 / 1/2 = 1/2 * 2/1
        int newnum = this.numerator * frac.denominator;
        int newden = this.denominator * frac.numerator;
        if (newden <= 0) {
            //to prevent init errors
            newden *= -1;
            newnum *= -1;
        }
        //the fraction object simplifies upon init, so we don't have to do that here
        return new Fraction(newnum, newden);
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public String toString() {
        //prints the fraction
        int whole = this.numerator / this.denominator;
        int num = Math.abs(this.numerator % this.denominator);
        int den = this.denominator;
        num /= gcf(num, den);
        den /= gcf(num, den);
        String output = "";
        if (whole != 0) {
            //if it contains an integer portion
            output += whole;
        }
        if (whole != 0 && num != 0) {
            //if it's mixed, contains BOTH
            output += "_";
        }
        if (num != 0) {
            //if it contains a fraction portion
            output += num + "/" + den;
        }
        if (whole == 0 && num == 0) {
            //if it's just 0
            return "0";
        } 
        return output;
    }

    private void simplify() {
        //simplifies object, used after init
        this.denominator /= gcf(numerator, denominator);
        this.numerator /= gcf(numerator, denominator);
    }

    private static int lcd(int a, int b) {
        //Finds the lowest common denominator between A and B
        int result = a;
        while (result % b != 0) {
            result += a;
        }
        return a;
    }

    private static int gcf(int a, int b) {
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

    private static int wordCount(String s) {
        //Counts the number of tokens
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                if (i == 0 || s.charAt(i - 1) == ' ') {
                    count++;
                }
            }
        }
        return count;
    }
}
