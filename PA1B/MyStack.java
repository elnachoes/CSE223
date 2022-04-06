import java.lang.System;
import java.util.LinkedList;
import java.util.Scanner;

public class MyStack
{
    private LinkedList<Integer> stack = null;

    public MyStack()
    {
        stack = new LinkedList<Integer>();
    }
    
    // ---------- stack functions ----------
    //this function prints the stack
    public String toString() { return "TOS ->" + this.stack; }
    
    //this function pushes a new element to the top of the stack
    public void push(int num) { this.stack.push(num); }
    
    //this function checks if the stack is empty or not
    public boolean isEmpty() { return (this.stack.size() == 0) ? (true) : (false); }
    
    //this function pops the top element of the stack
    public int pop() { return this.stack.pop(); }
}