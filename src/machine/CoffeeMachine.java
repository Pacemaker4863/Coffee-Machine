package machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static machine.CoffeeMachine.State.*;

public class CoffeeMachine {

    enum State {
        IDLE,
        BUYING,
        FILLING_WATER,
        FILLING_MILK,
        FILLING_COFFEE,
        FILLING_CUPS,
    }

    int water = 400;
    int milk = 540;
    int coffee = 120;
    int cups = 9;
    int money = 550;
    State state;

    public CoffeeMachine() {
        this.state = IDLE;
    }

    void handleInput(String action) {
        System.out.println("entering state = " + state);
        switch (state) {
            case IDLE:
                switch (action) {
                    case "buy":
                        this.state = State.BUYING;
                        break;
                    case "fill":
                        this.state = State.FILLING_WATER;
                        break;
                    case "take":
                        take();
                        this.state = IDLE;
                        break;
                    case "remaining":
                        remaining();
                        this.state = IDLE;
                        break;
                    case "exit":
                        System.exit(0);
                }
                break;
            case FILLING_WATER:
                this.fillWater(Integer.parseInt(action));
                this.state = FILLING_MILK;
                break;
            case FILLING_MILK:
                this.fillMilk(Integer.parseInt(action));
                this.state = FILLING_COFFEE;
                break;
            case FILLING_COFFEE:
                this.fillCoffee(Integer.parseInt(action));
                this.state = FILLING_CUPS;
                break;
            case FILLING_CUPS:
                this.fillCups(Integer.parseInt(action));
                this.state = IDLE;
                break;
            case BUYING:
                this.buy(action);
                this.state = IDLE;
                break;

        }
        System.out.println("exit state = " + state);
    }

    void remaining() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water%n", water);
        System.out.printf("%d ml of milk%n", milk);
        System.out.printf("%d g of coffee beans%n", coffee);
        System.out.printf("%d disposable cups%n", cups);
        System.out.printf("%d of money%n", money);
    }

    void buy(String answer) {
        if (isNumeric(answer)) {
            List<String> missingIngredient;
            if (Integer.parseInt(answer) == 1) {
                missingIngredient = checkExpresso(water, coffee, cups);
                if (missingIngredient.isEmpty()) {
                    System.out.println("I have enough resources, making you a coffee!");
                    water -= 250;
                    coffee -= 16;
                    cups -= 1;
                    money += 4;
                } else {
                    String missing = missingIngredient.stream().sorted().collect(Collectors.joining());
                    System.out.printf("Sorry, not enough %s!\n", missing);
                }
            } else if (Integer.parseInt(answer) == 2) {
                missingIngredient = checkLatte(water, milk, coffee, cups);
                if (missingIngredient.isEmpty()) {
                    System.out.println("I have enough resources, making you a coffee");
                    water -= 350;
                    milk -= 75;
                    coffee -= 20;
                    cups -= 1;
                    money += 7;
                } else {
                    String missing = missingIngredient.stream().sorted().collect(Collectors.joining());
                    System.out.printf("Sorry, not enough %s!\n", missing);
                }
            } else if (Integer.parseInt(answer) == 3) {
                missingIngredient = checkCappuccino(water, milk, coffee, cups);
                if (missingIngredient.isEmpty()) {
                    System.out.println("I have enough resources, making you a coffee!");
                    water -= 200;
                    milk -= 100;
                    coffee -= 12;
                    cups -= 1;
                    money += 6;
                } else {
                    String missing = missingIngredient.stream().sorted().collect(Collectors.joining());
                    System.out.printf("Sorry, not enough %s!\n", missing);
                }
            }
        } else {
            if (answer.equals("back")) {
                this.state = IDLE;
            }
        }
    }

    void fillWater(int water) {
        this.water += water;
    }

    void fillMilk(int milk) {
        System.out.println("Write how many ml of milk you want to add: ");
        this.milk += milk;
    }

    void fillCoffee(int coffee) {
        System.out.println("Write how many grams of coffee beans you want to add: ");
        this.coffee += coffee;
    }

    void fillCups(int cups) {
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        this.cups += cups;
    }

    void take() {
        System.out.printf("I gave you %d\n", money);
        this.money = 0;
    }

    void exit() {
        System.exit(0);
    }

    private static List<String> checkExpresso(int water, int coffee, int cups) {
        List<String> missingIngredients = new ArrayList<>();
        if (water < 250) {
            missingIngredients.add("water");
        }
        if (coffee < 16) {
            missingIngredients.add("coffee beans");
        }
        if (cups == 0) {
            missingIngredients.add("cups");
        }
        return missingIngredients;
    }

    private static List<String> checkLatte(int water, int milk, int coffee, int cups) {
        List<String> missingIngredients = new ArrayList<>();
        if (water < 350) {
            missingIngredients.add("water");
        }
        if (milk < 75) {
            missingIngredients.add("milk");
        }
        if (coffee < 20) {
            missingIngredients.add("coffee beans");
        }
        if (cups == 0) {
            missingIngredients.add("cups");
        }

        return missingIngredients;
    }

    private static List<String> checkCappuccino(int water, int milk, int coffee, int cups) {
        List<String> missingIngredients = new ArrayList<>();
        if (water < 200) {
            missingIngredients.add("water");
        }
        if (milk < 100) {
            missingIngredients.add("milk");
        }
        if (coffee < 15) {
            missingIngredients.add("coffee beans");
        }
        if (cups == 0) {
            missingIngredients.add("cups");
        }
        return missingIngredients;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static void display(CoffeeMachine coffeeMachine) {
        switch (coffeeMachine.state) {
            case IDLE:
                System.out.println("Write action (buy, fill, take, remaining, exit): ");
                break;
            case BUYING:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                break;
            case FILLING_WATER:
                System.out.println("Write how many ml of water you want to add: ");
                break;
            case FILLING_MILK:
                System.out.println("Write how many ml of milk you want to add: ");
                break;
            case FILLING_COFFEE:
                System.out.println("Write how many grams of coffee beans you want to add: ");
                break;
            case FILLING_CUPS:
                System.out.println("Write how many disposable cups of coffee you want to add: ");
                break;
        }
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        do {
            display(coffeeMachine);

            Scanner scanner = new Scanner(System.in);
            coffeeMachine.handleInput(scanner.next());
        } while (true);
    }
}

