import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

//Author : Corbin Martin
//Date : 5/1/2022
//      This is a class built to be a decision tree for a game of 20 questions
//      It has an internal private class for node which make up the parts of the tree
//      The class has an internal state of what the current node of the decision tree is
//      WARNING THIS WILL BLOW UP IF THE FILE YOU PASS IN IS CORRUPT OR NOT STRUCTURED CORRECTLY IN A NLR FASHION 
public class DecisionTree
{
    //Author : Corbin Martin
    //Date : 5/1/2022
    //      this is a node inside of the decision tree made up of a piece of text with a yes child and a no child
    //      every node will either be an answer or a preposition question 
    //      it has one method for if the node is an answer or not 
    //      this class doesn't have to be encapsulated in DecisionTree but it is so specific of an object why not?
    private class Node 
    {
        public String text = null;
        public Node yesChild = null;
        public Node noChild = null;

        public Node(String newText, Node newYesChild, Node newNoChild)
        {
            text = newText;
            yesChild = newYesChild;
            noChild = newNoChild;
        }

        //this function allows you to know if the node is an answer or not 
        //because of the recursive functions in DecisionTree, it is expedient to put this function here
        public boolean IsAnswer() 
        {
            if (this.yesChild == null && this.noChild == null) return true; 
            else return false;
        }
    }

    //these are the pointers to the root of the whole tree and where the current decision is
    private Node root = null;
    private Node currentDecision = null;

    //this function checks if the decision tree is empty
    public boolean IsTreeEmpty()
    {
        if (root == null) return true;
        else return false;
    }

    //this function returns the text of the current decision
    public String GetCurrentDecision() { return currentDecision.text; }

    //this function will tell you if the current decision is an answer or not
    public boolean IsCurrentDecisionAnswer() { return currentDecision.IsAnswer(); }

    //this function gets the next decision in the decision tree and returns the text of that decision
    public void AdvanceToNextDecision(boolean IsAnswerYes)
    {
        //if the current decision is an answer dont advance because its children ar null
        if (IsCurrentDecisionAnswer()) return;
        //if the answer is yes advance to the next node
        if (IsAnswerYes) currentDecision = currentDecision.yesChild;
        else currentDecision = currentDecision.noChild;
    }

    //this function will begin building a tree from a String file name
    //if the file does not it will return false otherwise it will invoke the recursive function CreateTree()
    //WARNING : THIS WILL BLOW UP IF YOU PASS IN A FILE THAT IS NOT STRUCTURED IN AN NLR WAY OR IF IT DOES NOT FOLLOW THIS FORMAT
    //
    //      Q: (to denote if there is a question or answer in capital letters)
    //      question text (to denote the)
    public boolean BuildTreeFromFile(String filename)
    {
        //try loading up a scanner return false if it cant
        Scanner scanner = null;
        try { scanner = new Scanner(new File(filename)); }
        catch (Exception e) { return false; }
        //create the tree from the recursive tree function and set the current decision at the root to start out
        root = CreateTree(scanner);
        currentDecision = root;
        //dont forget to close the scanner
        scanner.close();
        return true;
    }

    //this function will add a new question to the tree if the current decision is an answer
    //you have to pass in a new question and a yes answer for the new yes child
    public void AddQuestion(String newQuestion, String yesAnswer)
    {
        //if the current decision is return because you dont want to break the tree if you added in a new node where a question is
        if (!IsCurrentDecisionAnswer()) return;
        else 
        {
            //create a new decision where the current answer becomes the no child 
            currentDecision.noChild = new Node(currentDecision.text, null, null);
            currentDecision.yesChild = new Node(yesAnswer, null, null);
            currentDecision.text = newQuestion;
        }
    }

    //this function will write out the entire tree to a file that can be read in
    //returns false if it doesn't successfully load your file 
    public boolean WriteTreeToFile(String filename)
    {
        PrintWriter printWriter = null;
        try { printWriter = new PrintWriter(new File(filename));} 
        catch (Exception e) { return false; }
        SaveTree(root, printWriter);
        printWriter.close();
        return true;
    }

    //this function will recursively save the entire decision tree to a file
    //you have to pass in a child node and a printwriter to actually write to the file
    private void SaveTree(Node currentNode, PrintWriter printWriter)
    {
        //if the current node is an answer write the answer and return back
        if (currentNode.IsAnswer())
        {
            printWriter.println("A:");
            printWriter.println(currentNode.text);
            return;
        }
        //write out the question and then write its children
        printWriter.println("Q:");
        printWriter.println(currentNode.text);
        SaveTree(currentNode.yesChild, printWriter);
        SaveTree(currentNode.noChild, printWriter);
        return;
    }
    
    //this is a recursive function that will build the entire decision tree recursively
    //this will return the root of the entire tree 
    //you have to pass a scanner to this function so it will pass the scanner through the recursive
    private Node CreateTree(Scanner scanner)
    {
        //if the next item is a question then create a new node with its created children otherwise if you run into an answer create a node with null children
        if (scanner.nextLine().compareTo("Q:") == 0) return new Node(scanner.nextLine(), CreateTree(scanner), CreateTree(scanner));
        else return new Node(scanner.nextLine(), null, null);
    }
}