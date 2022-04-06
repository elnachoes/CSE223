import java.lang.System;
import java.util.LinkedList;
import java.util.Scanner;

import javax.lang.model.type.NullType;

public class MyQueue
{
    private LinkedList<Integer> queue = null;

    public MyQueue()
    {
        this.queue = new LinkedList<Integer>();
    }

    // ---------- queue functions ----------
    //this function prints the queue
    public String toString() { return "HEAD ->" + this.queue + "<-TAIL"; }

    //this function adds to the queue
    public void insert(int num) { this.queue.add(num); }
    
    //this function checks if the queue is empty
    public boolean isEmpty() { return (this.queue.size() == 0) ? (true) : (false); }
    
    //this function removes the first element int he queue
    public int remove() { return this.queue.removeFirst(); }
}