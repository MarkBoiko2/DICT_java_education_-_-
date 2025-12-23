package coffeemachine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CoffeeMachine machine = new CoffeeMachine();

        while (!machine.isExit()) {
            machine.printPrompt();
            String input = sc.nextLine();
            machine.process(input);
        }
    }
}
