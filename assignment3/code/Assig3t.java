// Assig3.java

import java.io.*;
import java.util.*;

/**
 * Word search program. Finds phrases within a grid of letters.
 * 
 * @author George Meisinger
 */
public class Assig3
{
    // keep track of path outside of recursive calls
    private Stack<String> path = new Stack();

    /**
     * adds a point on the grid to the path as a string
     */
    public void addToPath(int r, int c)
    {
        this.path.push("(" + r + "," + c + ")");
    }

    /**
     * removes most recent entry from the path
     */
    /*public String removeFromPath()
    {
        return this.path.pop();
    }*/
    
    /**
     * Uses the full path and the phrase to identify the starting and ending coordinates for each word
     * 
     * @param path Reference to the stack where path is stored
     * @param phrase the string containing the phrase that was found
     */
    public void printPath(Stack<String> path, String phrase)
    {
        // set up variables
        String [] words = phrase.split(" ");
        String [] finalPath = new String[words.length * 2];
        String [] tempPath = new String[path.size()];

        // popping from stack gives reverse order, so I'm reversing the path and keeping it in a temp array
        for (int i=path.size()-1;i>=0;i--)
        {
            tempPath[i] = path.pop();
        }

        // filters out coordinates in middle of words
        int count = 0;
        int pathIndex = 0;
        for (String word : words)
        {
            finalPath[count++] = tempPath[pathIndex++];
            for (int j=1; j<word.length()-1; j++)
            {
                pathIndex++;
            }
            finalPath[count++] = tempPath[pathIndex++];
            System.out.println(word + ": " + finalPath[count-2] + " to " + finalPath[count-1]);            
        }
       
    }
    public static void main(String [] args)
    {
        new Assig3();
    }

    public Assig3()
    {
        Scanner inScan = new Scanner(System.in);
		Scanner fReader;
		File fName;
        String fString = "", phrase = "";

        // check filename
        while (true)
        {
           try
           {
               System.out.println("Please enter grid filename:");
               fString = inScan.nextLine();
               fName = new File(fString);
               fReader = new Scanner(fName);
              
               break;
           }
           catch (IOException e)
           {
               System.out.println("Problem " + e);
           }
        }

        // Parse input file to create 2-d grid of characters
		String [] dims = (fReader.nextLine()).split(" ");
		int rows = Integer.parseInt(dims[0]);
		int cols = Integer.parseInt(dims[1]);
		
		char [][] theBoard = new char[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			String rowString = fReader.nextLine();
			for (int j = 0; j < rowString.length(); j++)
			{
				theBoard[i][j] = Character.toLowerCase(rowString.charAt(j));
			}
        }
        
        // Show user the grid
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(theBoard[i][j] + " ");
			}
			System.out.println();
        }
        
        System.out.println("Please enter phrase (sep. by single spaces):");
        phrase = (inScan.nextLine()).toLowerCase();

        while (!(phrase.equals("")))
        {
            // gotta change this. but its the loop that iterates over grid
            // it will call a method on each tile that searches for the word until it gets a space
            boolean found = false;
			for (int r = 0; (r < rows && !found); r++)
			{
				for (int c = 0; (c < cols && !found); c++)
				{
				    // Start search for each position at index 0 of the word
                    found = findPhrase(r, c, phrase, 0, theBoard, 0, 0);
				}
            }
            
            if(found)
            {
                // the output
                System.out.println("The phrase: " + phrase);
                System.out.println("was found");
                this.printPath(this.path, phrase);
                for (int i = 0; i < rows; i++)
                {
                    for (int j = 0; j < cols; j++)
                    {
                        System.out.print(theBoard[i][j] + " ");
                        theBoard[i][j] = Character.toLowerCase(theBoard[i][j]);
                    }
                    System.out.println();
                }
            }
            else
			{
				System.out.println("The phrase: " + phrase);
                System.out.println("was not found");
                for (int i = 0; i < rows; i++)
				{
					for (int j = 0; j < cols; j++)
					{
						System.out.print(theBoard[i][j] + " ");
						theBoard[i][j] = Character.toLowerCase(theBoard[i][j]);
					}
					System.out.println();
				}
			}

            System.out.println("Please enter phrase (sep. by single spaces):");
            phrase = (inScan.nextLine()).toLowerCase();
        }

    }

    /**
     * The recursive method to find a phrase letter by letter.
     * 
     * @param r the row to begin search
     * @param c the column to begin search
     * @param phrase the phrase we are looking for
     * @param loc the position of the character in the phrase we are looking for
     * @param bo reference to the word search grid
     * @param dr delta-row, keeps track of the direction we are searching in
     * @param dc delta-column, keeps track of the direction we are searching in
     */
    public boolean findPhrase(int r, int c, String phrase, int loc, char [][] bo, int dr, int dc)
    {      
        // Check boundary conditions
		if (r >= bo.length || r < 0 || c >= bo[0].length || c < 0)
            return false;
        else if (bo[r][c] != phrase.charAt(loc)) { // char does not match       
            return false;
        }
		else  	// current character matches
		{
            bo[r][c] = Character.toUpperCase(bo[r][c]); //capitalize char to prevent double use and for display purposes
            this.addToPath(r,c);

            boolean answer;
			if (loc == phrase.length()-1)		// phrase found
                answer = true;
            else // found letter, so now look for the rest of the word
            {
                // if next char is space, skip and check all directions for the next word
                if (phrase.charAt(loc+1) == ' ')
                {
                    answer = findPhrase(r, c+1, phrase, loc+2, bo, 0, 1);  // Right
                    if (!answer)
                        answer = findPhrase(r+1, c, phrase, loc+2, bo, 1, 0);  // Down
                    if (!answer)
                        answer = findPhrase(r, c-1, phrase, loc+2, bo, 0, -1);  // Left
                    if (!answer)
                        answer = findPhrase(r-1, c, phrase, loc+2, bo, -1, 0);  // Up
                }
                else if  ((dr == 0) && (dc == 0)) // for initial case
                {
                    answer = findPhrase(r, c+1, phrase, loc+1, bo, 0, 1);  // Right
                    if (!answer)
                        answer = findPhrase(r+1, c, phrase, loc+1, bo, 1, 0);  // Down
                    if (!answer)
                        answer = findPhrase(r, c-1, phrase, loc+1, bo, 0, -1);  // Left
                    if (!answer)
                        answer = findPhrase(r-1, c, phrase, loc+1, bo, -1, 0);  // Up
                }
                else // then find the rest of the word in the same direction
                {
                    answer = findPhrase(r+dr, c+dc, phrase, loc+1, bo, dr, dc);
                }
                // if it wasnt found, backtrack, making letter lowercase and changing the path
                if (!answer) {
                    bo[r][c] = Character.toLowerCase(bo[r][c]);
                    this.path.pop();
                }
            }
            return answer;
            
        }   
    }
}