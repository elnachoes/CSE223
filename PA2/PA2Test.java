//
// Test code for Indexer class
// Nick Macias
//

public class PA2Test
{
    public static void main(String[] args)
    {
        Indexer ind=new Indexer();
    // make sure things don't blow up
        System.out.println("Before processing, ind="+ind);
        System.out.println("Before processing, numberOfInstances returns "+ ind.numberOfInstances("TEST"));
        System.out.println("Before processing, locationOf returns "+ ind.locationOf("TEST",1));
        System.out.println("Before processing, numberOfWords returns "+ ind.numberOfWords());

    // process a non-existent file
        boolean status=ind.processFile("badfile.bad");

        System.out.println("\nAfter processing bad file, status="+status);
        System.out.println("\nAfter processing bad file, ind="+ind);
        System.out.println("After processing bad file, numberOfInstances returns "+ ind.numberOfInstances("TEST"));
        System.out.println("After processing bad file, locationOf returns "+ ind.locationOf("TEST",1));
        System.out.println("After processing bad file, numberOfWords returns "+ ind.numberOfWords());

    // now process a good testfile
        status=ind.processFile("testfile.txt");

        System.out.println("\nAfter processing good file, status="+status);
        System.out.println("\nAfter processing good file, ind="+ind);
        System.out.println("# of instances of BADBADBAD="+ ind.numberOfInstances("BADBADBAD"));
        System.out.println("# of instance of TEST="+ ind.numberOfInstances("TEST")+"\n");
    // iterate over all instances (plus a few out of range)
        for (int i=-1;i<=ind.numberOfInstances("TEST");i++){
            System.out.println("Instance " + i + " is at location "+ ind.locationOf("TEST",i));
        }
        System.out.println("\n# of unique words="+ind.numberOfWords());
        // int x = ind.locationOf("so", 2);
        // System.out.println("x");
    }
}
