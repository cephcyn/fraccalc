
import java.util.Scanner;

public class Fraction {

    private int numerator;
    private int denominator;

    public Fraction(int num, int den) {
        //prevent negative denom
        if (den <= 0) {
            throw new IllegalArgumentException("Denominator must be positive!");
        }
        //init
        this.denominator = den;
        this.numerator = num;
        //simplify
        simplify();
    }

    public Fraction(int whole) {
        this(whole, 1);
    }

    public Fraction(int whole, int num, int den) {
        //prevent negative denom
        if (den <= 0) {
            throw new IllegalArgumentException("Denominator must be positive!");
        }
        if (num < 0) {
            throw new IllegalArgumentException("Numerator in a mixed number cannot be negative!");
        }
        //negative?
        int neg = 1;
        if (whole < 0) {
            neg = -1;
        }
        //init
        this.denominator = den;
        this.numerator = whole * den + num * neg;
        simplify();
    }

    public Fraction(String s) {
        s = s.replace('_', ' ').replace('/', ' ');
        Scanner scan = new Scanner(s);
        if (wordCount(s) == 1) {
            //init
            this.numerator = scan.nextInt();
            this.denominator = 1;
            scan.close();
        } else if (wordCount(s) == 2) {
            //init
            this.numerator = scan.nextInt();
            this.denominator = scan.nextInt();
            scan.close();
            //error checking
            if (this.denominator <= 0) {
                throw new IllegalArgumentException("Denominator must be positive!");
            }
        } else if (wordCount(s) == 3) {
            int whole = scan.nextInt();
            int num = scan.nextInt();
            int den = scan.nextInt();
            scan.close();
            //prevent negative denom (error checking)
            if (den <= 0) {
                throw new IllegalArgumentException("Denominator must be positive!");
            }
            if (num < 0) {
                throw new IllegalArgumentException("Numerator in a mixed number cannot be negative!");
            }
            //is it negative?
            int neg = 1;
            if (whole < 0) {
                neg = -1;
            }
            this.denominator = den;
            this.numerator = whole * den + num * neg;
            simplify();
        } else {
            throw new IllegalArgumentException("Did you even give me anything resembling a fraction?");
        }
    }

    public Fraction add(Fraction frac) {
        int op1num = this.numerator;
        int op1den = this.denominator;
        int op2num = frac.numerator;
        int op2den = frac.denominator;
        //perform operation
        op1num *= (lcm(op1den, op2den) / op1den);
        op1den *= (lcm(op1den, op2den) / op1den);
        op2num *= (lcm(op1den, op2den) / op2den);
        op1num += op2num;
        //Simplify and return
        return new Fraction(op1num / gcf(op1num, op1den), op1den / gcf(op1num, op1den));
    }

    public Fraction subtract(Fraction frac) {
        int op1num = this.numerator;
        int op1den = this.denominator;
        int op2num = frac.numerator;
        int op2den = frac.denominator;
        //perform operation
        op1num *= (lcm(op1den, op2den) / op1den);
        op1den *= (lcm(op1den, op2den) / op1den);
        op2num *= (lcm(op1den, op2den) / op2den);
        op1num -= op2num;
        //Simplify and return
        return new Fraction(op1num / gcf(op1num, op1den), op1den / gcf(op1num, op1den));
    }

    public Fraction multiply(Fraction frac) {
        int op1num = this.numerator;
        int op1den = this.denominator;
        int op2num = frac.numerator;
        int op2den = frac.denominator;
        //perform operation
        int finnum = op1num * op2num;
        int finden = op1den * op2den;
        //Simplify and return
        return new Fraction(finnum / gcf(finnum, finden), finden / gcf(finnum, finden));
    }

    public Fraction divide(Fraction frac) {
        int op1num = this.numerator;
        int op1den = this.denominator;
        int op2num = frac.numerator;
        int op2den = frac.denominator;
        if (op2num == 0) {
            throw new IllegalArgumentException("Why are you trying to divide by zero?");
        }
        //prevent 1/2 / -1/2 and the like from being a disaster
        if (op2num < 0) {
            op2den *= -1;
            op2num *= -1;
        }
        //perform operation, remember that 1/2 / 1/4 = 1/2 * 4/1
        int finnum = op1num * op2den;
        int finden = op1den * op2num;
        //Simplify and return
        return new Fraction(finnum / gcf(finnum, finden), finden / gcf(finnum, finden));
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public String toString() {
        //TODO MAKE THIS EFFING WORK YOU IDIOT
        //is it negative? (For use of mixed number)
        int negval = 1;
        if (this.numerator < 0) {
            negval = -1;
        }
        if (this.numerator % this.denominator == 0) {
            //whole number case
            return "" + this.numerator / this.denominator;
        } else if (Math.abs(this.numerator) < this.denominator) {
            //fraction case
            return this.numerator + "/" + this.denominator;
        } else if (Math.abs(this.numerator) < this.denominator) {
            //mixed number case
            return (this.numerator / this.denominator) + "_" + negval * (this.numerator % this.denominator) + "/" + this.denominator;
        } else {
            //zero case
            return "0";
        }
    }

    private void simplify() {
        //simplifies object, used after init
        int temp = gcf(this.numerator, this.denominator);
        //save variables
        this.denominator /= temp;
        this.numerator /= temp;
    }

    private static int lcm(int a, int b) {
        //Finds the lowest common denominator between A and B
        //Always returns a positive value
        int result = a;
        while (result % b != 0) {
            result += a;
        }
        return Math.abs(a);
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
