import java.util.LinkedList;

public class MyQueue
{
    //this queue is implemented in a linked list
    private LinkedList<Integer> queue = null;

    //ctor
    public MyQueue() { queue = new LinkedList<Integer>(); }

    //this function prints the queue
    public String toString() { return "HEAD ->" + queue + "<-TAIL"; }

    //this function adds to the queue
    public void insert(int num) { queue.add(num); }
    
    //this function checks if the queue is empty
    public boolean isEmpty() { return (queue.size() == 0); }
    
    //this function removes the first element int he queue
    public int remove() { return queue.removeFirst(); }
}