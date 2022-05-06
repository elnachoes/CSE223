//Author : Corbin Martin
//Date : 4/23/2022
//Class : CSE223 1905
//Summary : This Class will load in a file and create a hash map of indexes. At each index on the hash map, it will have a list of locations where that word is in the file

import java.io.File;
import java.util.HashMap; 
import java.util.Scanner; 
import java.util.LinkedList;

//This Class will load in a file and create a hash map of indexes. At each index on the hash map, it will have a list of locations where that word is in the file
public class Indexer 
{
    //hash map all the words will be stored in
    private HashMap<String,LinkedList<Integer>> index = null;

    //This function will process a file you give to it 
    //loads into a hashmap which loads every word in the file into a hashmap with a corresponding linked list with the word indexes
    public boolean processFile(String filename)
    {
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            //If the file exists and loads INTO the scanner create a new hash table and fill it with the file's contents
            index = new HashMap<String,LinkedList<Integer>>();
            Integer wordIndex = 0;
            while (scanner.hasNext()) 
            {
                //clean up a word and see if it is an empty word, if so skip it otherwise add it/update it in the hash map with addReference()
                String cleanedWord = cleanupWord(scanner.next());
                if (cleanedWord.equals("")) continue;
                addReference(cleanedWord, wordIndex);
                wordIndex++;
            }
            scanner.close();
            return true;
        }
        catch (Exception e) { return false; }
    }

    //This function returns the number of instances that a word a word is in the input file
    public int numberOfInstances(String word) 
    {
        String cleanedWord = cleanupWord(word);
        //If a file hasn't bean loaded return -1
        if (index == null) return -1; 
        else if (index.get(cleanedWord) != null) return index.get(cleanedWord).size();
        else return 0;
    }

    //This function returns the number of instances that a word a word is in the input file
    public int locationOf(String word, int instanceNum)
    {
        String cleanedWord = cleanupWord(word);
        //If a file hasn't bean loaded return -1
        if (index == null) return -1;
        else if (index.get(cleanedWord) == null) return -1;
        else if (instanceNum >= 0 && instanceNum < index.get(cleanedWord).size()) return index.get(cleanedWord).get(instanceNum);
        else return -1;
    }
    
    //This function returns the number of unique words in a file 
    public int numberOfWords()
    {
        //If a file hasn't bean loaded return -1
        if (index == null) return -1;
        else if (index.size() != 0) return index.size();
        else return -1;
    }

    //This function returns the hashmap string or return null if a file hasn't been processed
    public String toString() 
    {
        if (index == null) return null;
        else return index.toString();
    }

    //This function returns a word that replaces all non letter characters with a regex and converts it to uppercase
    //I am not 100% certain but I am pretty sure this is way faster because of less allocations. It sure is a lot faster when I hit it with a 100k word file
    private String cleanupWord(String word) { return word.replaceAll("[^A-Za-z0-9]", "").toUpperCase(); }

    //This function puts a new reference in the hash table if the word isn't in the table otherwise add a new location to its linked list
    private void addReference(String word, int location)
    {
        if (index.get(word) == null)
        {
            //create a new linked list for the new item, add the word location, then put that linked list in the hash table
            LinkedList<Integer> newLinkedList = new LinkedList<Integer>();
            //add one to the location before adding it because the file is base 1 starting from the first word
            newLinkedList.add(location + 1);
            index.put(word,newLinkedList);
        }
        else index.get(word).add(location + 1);
    }
}