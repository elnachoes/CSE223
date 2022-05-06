import java.util.LinkedList;

public class MyStack
{
    //this stack is implemented in a linked list
    private LinkedList<Integer> stack = null;

    //ctor 
    public MyStack() { stack = new LinkedList<Integer>();}
    
    //this function prints the stack
    public String toString() { return "TOS ->" + this.stack; }
    
    //this function pushes a new element to the top of the stack
    public void push(int num) { stack.push(num); }
    
    //this function checks if the stack is empty or not
    public boolean isEmpty() { return (stack.size() == 0); }
    
    //this function pops the top element of the stack
    public int pop() { return stack.pop(); }
}