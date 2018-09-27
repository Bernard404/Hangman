import java.util.Scanner;
import java.util.Arrays;
public class Hangman {
	
	static int gamesPlayed=0;
	static int gamesWon=0;
	
	public static void main(String[] args)
	{	
		int endGame = 0;
		String pattern1 = "y|Y|n|N", pattern2 = "n|N";
		String selection = "", playAgain = "";
		String[] wordArray = {"Animals", "Tonedeaf", "Adrenaline", "Survive", "Tesseract", "Gorilla", "Peripheral", "Juggernaut", "Matriarch"};
		int[] usedWords = new int[wordArray.length];
		Scanner keyboard = new Scanner(System.in);
		do
		{
			int indexOfWord = wordToGuess(wordArray, usedWords);
			String wordToGuess = wordArray[indexOfWord];
			String[] letterArray = letterArray(wordToGuess);
			String[] wordInDashes = dashesArray(wordToGuess);
			boolean notGuessed = takeGuess(letterArray, wordInDashes, wordToGuess);
			
			if(!notGuessed)	
			{
				System.out.println("\nCongratulations, you guessed the hidden word: " + wordToGuess + "\n");
				gamesWon++;
			}
			else
			{
				System.out.println("\n*************\n*************\n\n  --\n o |\n/|\\|\n/ \\|\n_____");
				System.out.println("You lose! Better luck next time!\n");
			}
			gamesPlayed++;
			
			do
			{	
				System.out.println("Would you like to play again? (y/Y/n/N)");
				playAgain = keyboard.nextLine();
				if(playAgain == null) endGame = 1;
				else if(!(playAgain.matches(pattern1)))
					System.out.println("Invalid option, please try again");
				else if(playAgain.matches(pattern2))
					endGame = 1;
				else 
					endGame = 2;
			} while(endGame != 1 && endGame != 2);
		} while(endGame == 2);
		System.out.print("\n********\nThe total number of games played:" + gamesPlayed + "\n********\nTotal number of games won is: "+ gamesWon + "\n");
	}
	
	/* 	wordToGuess() takes String array called wordArray and empty int array called usedWords and returns an int named wordIndex. The int returned
		is an int containing the index for the random word to be guessed.
		
		The method first declares an int named wordIndex and defines it as 0.
		Next, a random number is generated to select a word from the array.
		An if statement with the condition that the address of the random number in usedWords is not 0 is used
		to repeat the method if that word index has been used so far. 
		Else if is has not been used, the address at randNum will be set to 1 and wordIndex will be assigned the randNum value.
		The int value 'wordIndex' is then returned.
	*/
	public static int wordToGuess(String[] wordArray, int[] usedWords)
	{
		int wordIndex = 0;
		int randNum = (int) (Math.random() * wordArray.length);
		if(usedWords[randNum] != 0)
			wordToGuess(wordArray, usedWords);
		else
		{
			usedWords[randNum] = 1;
			wordIndex = randNum;
		}
		return wordIndex;
	}
	
	/*	letterArray() takes in a String object, wordToGuess, and returns a String array. The String array		returned is an array containing a letter from the String wordToGuess at each address.
		
		First, the method declares a new String array called letterArray that will be the length
		of the String wordToGuess.
		Next, using a for loop and the substring method, the method takes each letter of wordToGuess
		and put them into their own addresses in letterArray.
		The method returns letterArray to the main method.
	*/
	public static String[] letterArray(String wordToGuess)
	{
		String[] letterArray = new String[wordToGuess.length()];
		for(int i = 0; i < wordToGuess.length(); i++)
			letterArray[i] = wordToGuess.substring(i, i + 1);
		return letterArray;
	}
	
	/* 	dashesArray() takes in a String called wordToGuess and returns a String array. The String array returned 
		is an array containing a dash for each letter in wordToGuess in each address.
		
		First, the method declares a String array named dashesArray that will be the length of
		the String wordToGuess.
		Next, using a for loop, the method puts a "_" into it's own address in dashesArray.
		The method returns dashesArray to the main method.
	*/
	public static String[] dashesArray(String wordToGuess)
	{
		String [] dashesArray = new String [wordToGuess.length()];
		for(int i = 0; i < wordToGuess.length(); i++)
			dashesArray[i] = "_";
		return dashesArray;
	}
	
	/*	takeGuess() takes in two String arrays, letterArray and dashesArray, and a String called wordToGuess
		and returns nothing.
		
		First, the method declares two empty Strings called 'guess' and 'guessedAlready',
		a counter set to 0, booleans 'notGuessed' set to true, 'invalid' to false, 'repeat'
		to false and a Scanner object called 'input'. Next a it starts a while loop 
		with the conditions counter is less than 6 and notGuessed is true. The while
		loop calls a method called printGallows and passed counter as an argument.
		The if else if part will print a message to the user if they have repeated a guess
		or entered invalid input. The boolean values notGuessed, invalid and repeat are
		reset to false. A boolean for checking if the guess is correct is declared to be
		false. 4 print statements send out messages to the user about previous guesses, 
		the word replaced with dashes (updated with correct guesses) and a prompt for input
		A string called guess is assigned the alue of whatever is typed on the keyboard next
		An if statement checks if that guess has already been made, if it has, the boolean 'repeat'
		is set to true. The string 'guess' is added to 'guessedAlready' with a space. 
		There is an if else if else sequence to seperate how the input is checked based on if
		its a single letter or a guess at the full word or invalid. If it is just a letter
		th eletter will be checked against each address of the letterArray. If the letter is 
		found, the dash in dashesArray at that address will be replaced with the letter and correctGuess
		be set to true. Another for loop will check if there any dashes left in dashesArray
		and if there are, notGuessed is set to true.
		If the guess is a full word guess, the word is checked against 'wordToGuess' and if they 
		are equal notGuessed is set to false, else it is set to true. If the input is invalid,
		the invalid boolean is set to true and so is notGuessed. Finally, if correctGuess is
		false, the counter is incremented and notGuessed is returned.
	*/
	public static boolean takeGuess(String[] letterArray, String[] dashesArray, String wordToGuess)
	{
		String guess, guessedAlready = "";
		int counter = 0;
		boolean notGuessed = true, invalid = false, repeat = false;
		Scanner input = new Scanner(System.in);
		
		while(counter < 6 && notGuessed) 
		{
			printGallows(counter);
			if(repeat) System.out.println("Repeated guesses are penalised.\n");
			else if(invalid) System.out.println("Only enter single letters or a guess for the full word.\n");
			notGuessed = false; invalid = false; repeat = false;
			boolean correctGuess = false;
			System.out.println("Previous guesses: " + guessedAlready);
			System.out.print("Word is: ");
			System.out.println(Arrays.toString(dashesArray));
			System.out.print("\nEnter guess to check if it is present: ");
			guess = input.nextLine();
			if(guessedAlready.contains(guess))
				repeat = true;
			guessedAlready += guess + " ";
		
			if(guess.length() == 1 && !repeat)
			{	
				for (int i = 0; i < letterArray.length; i++)
				{
					if(letterArray[i].equalsIgnoreCase(guess))
					{
						dashesArray[i] = guess;
						correctGuess = true;
					}
				}
			
				for(int i = 0; i < letterArray.length; i++)
				{
					if(dashesArray[i].equals("_"))
						notGuessed = true;
				}
			}
			
			else if(guess.length() == letterArray.length && !repeat)
			{
				if (wordToGuess.equalsIgnoreCase(guess)) 	notGuessed = false;
				else 						   	correctGuess = false;
			}		
			
			else
			{
				notGuessed = true; invalid = true;
			}
			if(!correctGuess) counter++;
		}
		return notGuessed;
	}
	
	/*	printGallows() takes in an int called guessed and returns nothing. 
		
		printGallows contains a switch statement with a case for each value of 
		guesses between 0 and 5. For guesses is 0, it will print an empty gallows
		and state that you have 6 chances left. As guesses goes up in value, a 
		body part will be added to the gallows and the amount of guesses will
		be displayed accordingly. 
	*/
	public static void printGallows(int guesses) 
    {
		switch(guesses)
		{
		  case 0:
			System.out.println("\n*************\n*************\n\n  --\n   |\n   |\n   |\n_____");	 //empty gallows
			System.out.println("You have 6 chances to guess the hidden word\n");
			break;
		  case 1:
            System.out.println("\n*************\n*************\n\n  --\n o |\n   |\n   |\n_____");   //+head
            System.out.println("You only have 5 chances left!\n");
            break;
          case 2: 
            System.out.println("\n*************\n*************\n\n  --\n o |\n/  |\n   |\n_____");   //+leftarm
            System.out.println("You only have 4 chances left!\n");
            break;
          case 3: 
            System.out.println("\n*************\n*************\n\n  --\n o |\n/| |\n   |\n_____");   //+body
            System.out.println("You only have 3 chances left!\n");
            break;
          case 4: 
            System.out.println("\\n*************\n*************\n\n  --\n o |\n/|\\|\n   |\n_____"); //+rightarm
            System.out.println("You only have 2 chances left!\n");
            break;
          case 5: 
            System.out.println("\n**************\n*************\n\n --\n o |\n/|\\|\n/  |\n_____");  //+leftleg
            System.out.println("You only have 1 chances left!\n");
            break;
		}
    }
}