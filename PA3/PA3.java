import java.util.Scanner;

//Author : Corbin Martin
//Date : 5/1/2022
//      This is the main program for the 20 questions game and it uses a DecisionTree object to navigate the questions
//      It will load a database file into the DecisionTree object from CMD arguments or a default one called "20Q.txt"
//      It will traverse the DecisionTree asking questions until it finds an answer and it will ask the user if it is the correct answer or not
//      If the answer is not what the user is thinking it will ask the user what were you thinking of and what is a good yes/no question for next time to get their answer
//      After that the program will write the updated tree to the input file
//      It will also ask the user if they want to play again
public class PA3 
{
    //this is the main game function 
    public static void main(String[] args) 
    {
        //overarching program loop
        while (true)
        {
            //setup the decision tree from the cmd args or by a default 20Q.txt file
            DecisionTree decisionTree = new DecisionTree();
            if (args.length >= 1) if (!decisionTree.BuildTreeFromFile(args[0])) decisionTree.BuildTreeFromFile("20Q.txt");
            else if (args.length == 0)
            {
                //if it cannot find the default file return 20q and exit
                if (!decisionTree.BuildTreeFromFile("20Q.txt"))
                {
                    System.out.println("error : cant load the 20Q.txt file");
                    return;
                }
            }
            
            //setup the scanner to get user input
            Scanner scanner = new Scanner(System.in);
    
            //welcome message
            System.out.println("hello! lets play a game of 20 questions!");
            System.out.println("think of something and I will try to guess what it is!");
    
            //main game loop 
            while (true) 
            {
                //if the current node in the decision tree is the answer ask the user if the guess was correct
                //if the guess was correct then say yay and end the game
                //if the guess was NOT correct then ask for what the player was thinking of and ask for a new question to use next game
                if (decisionTree.IsCurrentDecisionAnswer()) 
                {
                    System.out.println("is it a " + decisionTree.GetCurrentDecision() + "?");
                    String input = scanner.nextLine();

                    if (input.compareTo("yes".toLowerCase()) == 0 || input.compareTo("y".toLowerCase()) == 0)
                    {
                        //if the computer got it right end the game with a game over message 
                        System.out.println("cool thanks for playing!");
                        break;
                    }
                    else if (input.compareTo("no".toLowerCase()) == 0 || input.compareTo("n".toLowerCase()) == 0)
                    {
                        //get what the user was thinking of and a good replacement question
                        System.out.println("darn! what where you thinking of? : ");
                        String answer = scanner.nextLine();
                        System.out.println("can you give me a good yes/no question, yes if " + answer + ", no if " + decisionTree.GetCurrentDecision() + " : ");
                        String newQuestion = scanner.nextLine();
                        //add the new question to the tree
                        decisionTree.AddQuestion(newQuestion, answer);
                        System.out.println("cool I will remember that next time!");
                        break;
                    }
                    else System.out.println("please answer with yes or no.");
                }
                else
                {
                    //ask the user the current question and get their input
                    System.out.println(decisionTree.GetCurrentDecision());
                    String input = scanner.nextLine();
                    if (input.compareTo("yes".toLowerCase()) == 0 || input.compareTo("y".toLowerCase()) == 0) decisionTree.AdvanceToNextDecision(true);
                    else if (input.compareTo("no".toLowerCase()) == 0 || input.compareTo("n".toLowerCase()) == 0) decisionTree.AdvanceToNextDecision(false);
                    else System.out.println("please answer with yes or no.");
                }
            }

            //write the updated tree to the file so the game remembers to ask the new questions
            if (args.length >= 1) decisionTree.WriteTreeToFile(args[0]);
            else decisionTree.WriteTreeToFile("20Q.txt");

            //this is the play again loop 
            //if the user puts in broken input ask them again
            while (true) 
            {
                System.out.println("play again yes/no?");
                String input = scanner.nextLine();
                if (input.compareTo("yes".toLowerCase()) == 0 || input.compareTo("y".toLowerCase()) == 0)
                {
                    System.out.println("ok let's play again!");
                    break;
                }
                else if (input.compareTo("no".toLowerCase()) == 0 || input.compareTo("n".toLowerCase()) == 0)
                {
                    //close the scanner before returning and exiting the program
                    System.out.println("thanks for playing!");
                    scanner.close();
                    return;
                }
                else System.out.println("please answer with yes or no.");
            }
        }
    }
}
