// Author : Corbin Martin
// Date : 4/3/2022
// Summary : this is a program similar to the one I did in CSE222 with stacks and queues in linked lists but in java
// The program has two modes where you can either manipulate a stack or a queue. 

import java.lang.System;
import java.util.LinkedList;
import java.util.Scanner;

//interesting side note, java doesn't allow top level static classes for whatever reason.
public class PA1A
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

    // ---------- stack functions ----------
    //this function prints the stack
    static void showStack(LinkedList<Integer> stack) { System.out.println("TOS ->" + stack); }

    //this function pushes a new element to the top of the stack
    static void stackPush(LinkedList<Integer> stack, int num) { stack.push(num); }
    
    //this function checks if the stack is empty or not
    static boolean stackIsEmpty(LinkedList<Integer> stack) { return stack.size() == 0; }
    
    //this function pops the top element of the stack
    static int doStackPop(LinkedList<Integer> stack) { return stack.pop(); }


    // ---------- queue functions ----------
    //this function prints the queue
    static void showQueue(LinkedList<Integer> queue) { System.out.println("HEAD ->" + queue + "<-TAIL"); }

    //this function adds to the queue
    static void queueInsert(LinkedList<Integer> queue, int num) { queue.add(num); }
    
    //this function checks if the queue is empty
    static boolean queueIsEmpty(LinkedList<Integer> queue) { return queue.size() == 0; }
    
    //this function removes the first element int he queue
    static int doQueueRemove(LinkedList<Integer> queue) { return queue.removeFirst(); }


    // ---------- MAIN FUNCTION ----------
    public static void main(String[] args)
    {
        boolean isInStackMode = true;
        LinkedList<Integer> stack = new LinkedList<Integer>();
        LinkedList<Integer> queue = new LinkedList<Integer>();
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
                showStack(stack);
            }
            //if the user inputs q shift to queue mode and show queue
            else if (input.compareTo("q") == 0) 
            {
                isInStackMode = false;
                showQueue(queue);
            }
            //if the user input enter pop from the stack or remove from the queue unless they are empty
            else if (input.compareTo("") == 0) 
            {
                if (isInStackMode && !stackIsEmpty(stack)) System.out.println(doStackPop(stack));
                else if (isInStackMode && stackIsEmpty(stack)) System.out.println("Stack is empty");
                else if (!isInStackMode && !queueIsEmpty(queue)) System.out.println(doQueueRemove(queue));
                else System.out.println("Queue is empty");
            }
            //if the user input x break out of the main loop and stop the program
            else if (input.compareTo("x") == 0) 
            {
                System.out.println("Goodbye!");
                break;
            }
            //if the user put in ? print the help message
            else if (input.compareTo("?") == 0) System.out.println(HELP_MESSAGE);
            //if the user put in anything else assume it is a number and push if in stack mode else insert into the stack
            else
            {
                //WARNING WILL THROW ERROR IF YOU PUT IN ANYTHING ELSE : assignment's instruction you are allowed to assume input will be a number here
                int num = Integer.parseInt(input);
                if (isInStackMode) stackPush(stack, num);
                else queueInsert(queue, num);
            }
            System.out.print(">");
        }

        //closing the scanner after we are done with it
        scanner.close();
    }
}