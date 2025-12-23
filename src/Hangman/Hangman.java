package Hangman;

import java.util.*;

public class Hangman {

    private static final String[] WORDS = {"python", "java", "javascript", "kotlin"};
    private static final int MAX_MISTAKES = 8;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("HANGMAN");

        while (true) {
            System.out.print("Type \"play\" to play the game, \"exit\" to quit: ");
            String command = scanner.nextLine();

            if ("exit".equals(command)) {
                break;
            } else if ("play".equals(command)) {
                play(scanner, random);
            }
            // якщо ввели щось інше — просто знову показуємо меню
        }
    }

    private static void play(Scanner scanner, Random random) {
        String word = WORDS[random.nextInt(WORDS.length)];

        char[] masked = new char[word.length()];
        Arrays.fill(masked, '-');

        Set<Character> guessed = new HashSet<>();
        int mistakes = 0;

        while (mistakes < MAX_MISTAKES && hasHidden(masked)) {
            System.out.print(new String(masked) + " Input a letter: ");
            String input = scanner.nextLine();

            if (input.length() != 1) {
                System.out.println("You should input a single letter");
                continue;
            }

            char ch = input.charAt(0);

            if (ch < 'a' || ch > 'z') {
                System.out.println("Please enter a lowercase English letter");
                continue;
            }

            if (guessed.contains(ch)) {
                System.out.println("You've already guessed this letter");
                continue;
            }

            guessed.add(ch);

            boolean found = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == ch) {
                    masked[i] = ch;
                    found = true;
                }
            }

            if (!found) {
                System.out.println("That letter doesn't appear in the word");
                mistakes++;
            }
        }

        if (!hasHidden(masked)) {
            System.out.println("You guessed the word " + word + "!");
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }
    }

    private static boolean hasHidden(char[] masked) {
        for (char c : masked) {
            if (c == '-') return true;
        }
        return false;
    }
}
