// Author : Corbin Martin
// Date : 4/6/2022
// Summary : this is a program similar to the one I did in CSE222 with stacks and queues in linked lists but in java
// The program has two modes where you can either manipulate a stack or a queue. 

import java.lang.System;
import java.util.LinkedList;
import java.util.Scanner;

//interesting side note, java doesn't allow top level static classes for whatever reason.
public class PA1B
{
    // ---------- constants ----------
    //declaring a constant string for the help message
    private static final String HELP_MESSAGE = 
        "Welcome. This program demonstrates the use of a stack and a queue.\n" + 
        "Usage:\n" +
        "#            insert # into stack or queue\n" + 
        "s            select STACK mode and display stack\n" +
        "q            select QUEUE mode and display queue\n" +
        "<ENTER>      remove top of stack/head of queue and display\n" +
        "x            Quit\n" + 
        "?            Display help";


    // ---------- MAIN FUNCTION ----------
    public static void main(String[] args)
    {
        boolean isInStackMode = true;
        MyStack stack = new MyStack();
        MyQueue queue = new MyQueue();
        Scanner scanner = new Scanner(System.in);
        
        //printing the welcome message
        System.out.println(HELP_MESSAGE);
        System.out.print(">");

        //the main loop will keep running as long as there is input
        while (scanner.hasNextLine()) 
        {
            String input = scanner.nextLine();

            //if the user inputs s shift to stack mode and show stack
            if (input.compareTo("s") == 0) 
            {
                isInStackMode = true;
                System.out.println(stack.toString());
            }
            //if the user inputs q shift to queue mode and show queue
            else if (input.compareTo("q") == 0) 
            {
                isInStackMode = false;
                System.out.println(queue.toString());;
            }
            //if the user input enter pop from the stack or remove from the queue unless they are empty
            else if (input.compareTo("") == 0) 
            {
                if (isInStackMode && !stack.isEmpty()) System.out.println(stack.pop());
                else if (isInStackMode && stack.isEmpty()) System.out.println("Stack is empty");
                else if (!isInStackMode && !queue.isEmpty()) System.out.println(queue.remove());
                else System.out.println("Queue is empty");
            }
            //if the user input x break out of the main loop and stop the program
            else if (input.compareTo("x") == 0) 
            {
                System.out.println("Goodbye!");
                break;
            }
            //if the user put in ? print the help message
            else if (input.compareTo("?") == 0) 
            {
                System.out.println(HELP_MESSAGE);
            }
            //if the user put in anything else assume it is a number and push if in stack mode else insert into the stack
            else
            {

                try 
                {
                    int num = Integer.parseInt(input);
                    if (isInStackMode) stack.push(num);
                    else queue.insert(num);
                } 
                catch (Exception e) 
                {

                }
            }
            System.out.print(">");
        }

        //closing the scanner after we are done with it
        scanner.close();
    }
}