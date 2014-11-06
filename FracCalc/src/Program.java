
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        //program introduces itself
        System.out.println("FracCalc by Zhou, Joyce");
        //not-a-fencepost first part
        String input;
        String lastdisplay = "";
        //not-a-fencepost second part
        while (!lastdisplay.equals("Goodbye.")) {
            System.out.print("> ");
            input = console.nextLine();
            lastdisplay = FracCalc.calc(input);
            System.out.println(lastdisplay);
        }
        //close console. This should be the last thing to execute.
        console.close();
    }
}
