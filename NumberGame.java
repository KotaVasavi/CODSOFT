import java.util.Random;
import java.util.Scanner;

class NumberGame {
    public static void main(String[] args) {
        Scanner sObj = new Scanner(System.in);
        Random random = new Random();
	int finalScore=0;
        String playAgain;
        int round = 1;
        int baseMinRange = 0;  
        int baseMaxRange = 100;
        int rangeIncrement = 50; 

        System.out.println("Hi! Hello, Welcome to Number Game");
        System.out.println("Your task is to Guess the number,The score is based on number of attempts left, if you guess the number in the last round still you get a point. All the Best!");
        do {
	    int roundScore = 0;
            int minRange = baseMinRange + (rangeIncrement * (round - 1));  
            int maxRange = baseMaxRange + (rangeIncrement * (round - 1)); 
            int randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;  
            boolean guessedCorrectly = false; 
            System.out.println("Round " + round + ": Guess the number (between " + minRange + " and " + maxRange + ")! You have 4 attempts.");

            for (int attempts = 1; attempts <= 4; attempts++) {
                System.out.print("Enter your guess: ");
                int userGuess = sObj.nextInt();
        

                 // Check if the user's guess is out of the valid range
                 if (userGuess < minRange || userGuess > maxRange) {
                    System.out.println("Your guess is out of range! The valid range is between " + minRange + " and " + maxRange + ".");
                    continue;  
                }

                if (userGuess == randomNumber) {
                    System.out.println("Congratulations! Your guess is correct.");
                    guessedCorrectly = true;
                    if(attempts==4){
                    roundScore += (5 - attempts);  // Score is based on attempts left
                    }else{
                        roundScore+=(4-attempts);
                    }
                    break;
                } else {
                    giveFeedback(userGuess, randomNumber, attempts, 4);
                }
            }

          
            if (!guessedCorrectly) {
                System.out.println("Sorry! You've used all attempts. The correct number was " + randomNumber);
            }
	    finalScore=+roundScore;
            System.out.println("Your score for this round  is: " + roundScore);

            System.out.print("Do you want to play again? (y/n): ");
            playAgain = sObj.next();

            if (playAgain.equalsIgnoreCase("y")) {
                round++;  
            }

        } while (playAgain.equalsIgnoreCase("y"));
        if(!playAgain.equals("n") && !playAgain.equals("y")){
        System.out.println("As the input is invalid exiting the game ");
        System.out.println("Thank you for playing! Your final score is: " + finalScore);
        }else{
	System.out.println("Existing the Game..!");
        System.out.println("Thank you for playing! Your final score is: " + finalScore);
        
        }
        sObj.close();
    }

    public static void giveFeedback(int userGuess, int randomNumber, int attempts, int maxAttempts) {
      
        if (Math.abs(userGuess - randomNumber) <= 5 && attempts < maxAttempts) {
            if (userGuess < randomNumber) {
                System.out.println("Almost close! The number is just few bits higher than yours.");
            } else {
                System.out.println("Almost close! The number is just few bits lower than yours.");
            }
        } else if (userGuess < randomNumber && attempts < maxAttempts) {
            System.out.println("Too low! Try to enter a bigger number.");
        } else if (userGuess > randomNumber && attempts < maxAttempts) {
            System.out.println("Too high! Try to enter a smaller number.");
        }
    }
}
