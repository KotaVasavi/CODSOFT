import java.util.Random;
import java.util.Scanner;

class NumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int score = 0;
        String playAgain;
        int round = 1;
        int baseMinRange = 0;  // Starting minimum range
        int baseMaxRange = 100; // Starting maximum range
        int rangeIncrement = 50;  // Increase both minimum and maximum range for each round

        System.out.println("Hi! Hello, Welcome to Number Game");
        System.out.println("Your task is to Guess the number,The score is based on number of attempts left. All the Best!");
        do {

            int minRange = baseMinRange + (rangeIncrement * (round - 1));  // Minimum range increases for each round
            int maxRange = baseMaxRange + (rangeIncrement * (round - 1));  // Maximum range increases for  each round
            int randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;  // Random number within minRange and maxRange
            boolean guessedCorrectly = false;  // To track if the user guessed the number

            System.out.println("Round " + round + ": Guess the number (between " + minRange + " and " + maxRange + ")! You have 4 attempts.");

            for (int attempts = 1; attempts <= 4; attempts++) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();

                 // Check if the user's guess is out of the valid range
                 if (userGuess < minRange || userGuess > maxRange) {
                    System.out.println("Your guess is out of range! The valid range is between " + minRange + " and " + maxRange + ".");
                    continue;  
                }

                // Check the user's guess and provide feedback
                if (userGuess == randomNumber) {
                    System.out.println("Congratulations! Your guess is correct.");
                    guessedCorrectly = true;
                    score += (6 - attempts);  // Score is based on attempts left
                    break;
                } else {
                    giveFeedback(userGuess, randomNumber, attempts, 4);
                }
            }

            // Only display "Sorry!" message if the player did not guess correctly
            if (!guessedCorrectly) {
                System.out.println("Sorry! You've used all attempts. The correct number was " + randomNumber);
            }

            // Display current score
            System.out.println("Your current score is: " + score);

            // Ask if the user wants to play another round
            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = scanner.next();

            if (playAgain.equalsIgnoreCase("yes")) {
                round++;  // Increase round counter for the next round
            }

        } while (playAgain.equalsIgnoreCase("yes"));

        System.out.println("Thank you for playing! Your final score is: " + score);
    }
    // Method to give feedback
    public static void giveFeedback(int userGuess, int randomNumber, int attempts, int maxAttempts) {
      
        if (Math.abs(userGuess - randomNumber) <= 5 && attempts < maxAttempts) {
            if (userGuess < randomNumber) {
                System.out.println("Almost close! The number is just a bit higher than yours.");
            } else {
                System.out.println("Almost close! The number is just a bit lower than yours.");
            }
        } else if (userGuess < randomNumber && attempts < maxAttempts) {
            System.out.println("Too low! Try to enter a bigger number.");
        } else if (userGuess > randomNumber && attempts < maxAttempts) {
            System.out.println("Too high! Try to enter a smaller number.");
        }
    }
}
