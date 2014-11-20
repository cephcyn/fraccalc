
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
        this.numerator = whole * denominator + numerator;
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
        int twhole = sscan.nextInt();
        int tnum = sscan.nextInt();
        int tden = sscan.nextInt();
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
    }

    public Fraction subtract(Fraction frac) {
    }

    public Fraction multiply(Fraction frac) {
    }

    public Fraction divide(Fraction frac) {
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public String toString() {
    }

    private void simplify() {
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
}
