package coffeemachine;

public class CoffeeMachine {
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    private State state = State.MAIN;
    private boolean exit = false;

    private enum State {
        MAIN, BUY,
        FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS
    }

    private enum CoffeeRecipe {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        final int water, milk, beans, cost;

        CoffeeRecipe(int water, int milk, int beans, int cost) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cost = cost;
        }
    }

    public boolean isExit() {
        return exit;
    }

    public void printPrompt() {
        switch (state) {
            case MAIN -> System.out.println("Write action (buy, fill, take, remaining, exit):");
            case BUY -> System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
            case FILL_WATER -> System.out.println("Write how many ml of water you want to add:");
            case FILL_MILK -> System.out.println("Write how many ml of milk you want to add:");
            case FILL_BEANS -> System.out.println("Write how many grams of coffee beans you want to add:");
            case FILL_CUPS -> System.out.println("Write how many disposable cups you want to add:");
        }
    }

    public void process(String input) {
        switch (state) {
            case MAIN -> handleMain(input);
            case BUY -> handleBuy(input);

            case FILL_WATER -> {
                water += parseInt(input);
                state = State.FILL_MILK;
            }
            case FILL_MILK -> {
                milk += parseInt(input);
                state = State.FILL_BEANS;
            }
            case FILL_BEANS -> {
                beans += parseInt(input);
                state = State.FILL_CUPS;
            }
            case FILL_CUPS -> {
                cups += parseInt(input);
                state = State.MAIN;
            }
        }
    }

    private void handleMain(String input) {
        switch (input) {
            case "buy" -> state = State.BUY;
            case "fill" -> state = State.FILL_WATER;
            case "take" -> take();
            case "remaining" -> remaining();
            case "exit" -> exit = true;
            default -> System.out.println("Unknown command");
        }
    }

    private void handleBuy(String input) {
        if ("back".equals(input)) {
            state = State.MAIN;
            return;
        }

        CoffeeRecipe recipe = switch (input) {
            case "1" -> CoffeeRecipe.ESPRESSO;
            case "2" -> CoffeeRecipe.LATTE;
            case "3" -> CoffeeRecipe.CAPPUCCINO;
            default -> null;
        };

        if (recipe == null) {
            System.out.println("Unknown option");
            // остаёмся в BUY, чтобы человек ввёл снова
            return;
        }

        if (canMake(recipe)) {
            make(recipe);
        }
        state = State.MAIN;
    }

    private void take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    private void remaining() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(beans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    private boolean canMake(CoffeeRecipe r) {
        if (water < r.water) { System.out.println("Sorry, not enough water!"); return false; }
        if (milk < r.milk) { System.out.println("Sorry, not enough milk!"); return false; }
        if (beans < r.beans) { System.out.println("Sorry, not enough coffee beans!"); return false; }
        if (cups < 1) { System.out.println("Sorry, not enough disposable cups!"); return false; }

        System.out.println("I have enough resources, making you a coffee!");
        return true;
    }

    private void make(CoffeeRecipe r) {
        water -= r.water;
        milk -= r.milk;
        beans -= r.beans;
        cups -= 1;
        money += r.cost;
    }

    private int parseInt(String s) {
        return Integer.parseInt(s.trim());
    }
}
