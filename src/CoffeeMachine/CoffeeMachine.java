package CoffeeMachine;

import java.util.Scanner;

public class CoffeeMachine {

    private enum State {
        ACTION, BUY, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS, EXIT
    }

    // Default resources (as in the task examples)
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    private State state = State.ACTION;

    public void printPrompt() {
        switch (state) {
            case ACTION:
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                break;
            case BUY:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                break;
            case FILL_WATER:
                System.out.println("Write how many ml of water do you want to add:");
                break;
            case FILL_MILK:
                System.out.println("Write how many ml of milk do you want to add:");
                break;
            case FILL_BEANS:
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;
            case FILL_CUPS:
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
            default:
                break;
        }
    }

    public void processInput(String input) {
        input = input.trim();

        switch (state) {
            case ACTION:
                handleAction(input);
                break;

            case BUY:
                handleBuy(input);
                break;

            case FILL_WATER:
                Integer addWater = parseIntSafe(input);
                if (addWater == null) {
                    System.out.println("Please, enter a number.");
                    printPrompt();
                    return;
                }
                water += addWater;
                state = State.FILL_MILK;
                printPrompt();
                break;

            case FILL_MILK:
                Integer addMilk = parseIntSafe(input);
                if (addMilk == null) {
                    System.out.println("Please, enter a number.");
                    printPrompt();
                    return;
                }
                milk += addMilk;
                state = State.FILL_BEANS;
                printPrompt();
                break;

            case FILL_BEANS:
                Integer addBeans = parseIntSafe(input);
                if (addBeans == null) {
                    System.out.println("Please, enter a number.");
                    printPrompt();
                    return;
                }
                beans += addBeans;
                state = State.FILL_CUPS;
                printPrompt();
                break;

            case FILL_CUPS:
                Integer addCups = parseIntSafe(input);
                if (addCups == null) {
                    System.out.println("Please, enter a number.");
                    printPrompt();
                    return;
                }
                cups += addCups;
                state = State.ACTION;
                printPrompt();
                break;

            case EXIT:
                // do nothing
                break;
        }
    }

    private void handleAction(String input) {
        switch (input) {
            case "buy":
                state = State.BUY;
                printPrompt();
                break;

            case "fill":
                state = State.FILL_WATER;
                printPrompt();
                break;

            case "take":
                takeMoney();
                printPrompt();
                break;

            case "remaining":
                printRemaining();
                printPrompt();
                break;

            case "exit":
                state = State.EXIT;
                break;

            default:
                // If user typed something else, just ask again
                printPrompt();
                break;
        }
    }

    private void handleBuy(String input) {
        if ("back".equals(input)) {
            state = State.ACTION;
            printPrompt();
            return;
        }

        switch (input) {
            case "1": // espresso
                makeCoffee(250, 0, 16, 4);
                break;
            case "2": // latte
                makeCoffee(350, 75, 20, 7);
                break;
            case "3": // cappuccino
                makeCoffee(200, 100, 12, 6);
                break;
            default:
                // invalid -> stay in BUY
                printPrompt();
                return;
        }

        state = State.ACTION;
        printPrompt();
    }

    private void makeCoffee(int needWater, int needMilk, int needBeans, int price) {
        if (water < needWater) {
            System.out.println("Sorry, not enough water!");
            return;
        }
        if (milk < needMilk) {
            System.out.println("Sorry, not enough milk!");
            return;
        }
        if (beans < needBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            return;
        }
        if (cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");
        water -= needWater;
        milk -= needMilk;
        beans -= needBeans;
        cups -= 1;
        money += price;
    }

    private void takeMoney() {
        System.out.println("I gave you " + money);
        money = 0;
    }

    private void printRemaining() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
    }

    private Integer parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isExit() {
        return state == State.EXIT;
    }

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);

        machine.printPrompt();
        while (!machine.isExit() && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            machine.processInput(line);
        }

        scanner.close();
    }
}
