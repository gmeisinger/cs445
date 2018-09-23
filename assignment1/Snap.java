import java.util.*;
/**
 * Snap!
 * A simulation of the card game snap.
 * 
 * @author George Meisinger
 */
public class Snap
{
	/**
	 * The main method
	 * 
	 * @param args The length of the game in rounds. Required.
	 */
	public static void main(String [] args)
	{
		//initialize the decks and piles
		final int ROUNDS = Integer.parseInt(args[0]);
		MultiDS<Card> p1FaceDown = new MultiDS<Card>(52);
		MultiDS<Card> p2FaceDown = new MultiDS<Card>(52);
		MultiDS<Card> p1FaceUp = new MultiDS<Card>(52);		
		MultiDS<Card> p2FaceUp = new MultiDS<Card>(52);	
		MultiDS<Card> snapPool = new MultiDS<Card>(52);				
		MultiDS<Card> deck = new MultiDS<Card>(52);		
		//embedded loop to create a 52 card deck
		System.out.println("generating deck");
		for (Card.Suits s: Card.Suits.values())
			for (Card.Ranks r: Card.Ranks.values())
			{
				Card newCard = new Card(s,r);
				deck.addItem(newCard);
			}

		deck.shuffle();
		
		//loop to alternate dealing cards to players
		System.out.println("Dealing cards to players...");
		int modCount = 1;
		while (!(deck.empty()))
		{
			if (modCount%2 == 1)
				p1FaceDown.addItem(deck.removeItem());
			else
				p2FaceDown.addItem(deck.removeItem());
			modCount++;
		}
		System.out.println("\nPlayer 1's face-down pile: \n" + p1FaceDown.toString());
		System.out.println("\nPlayer 2's face-down pile: \n" + p2FaceDown.toString());

		//game loop
		int winner = 0;
		int currentRound = 1;		
		boolean checkMatch;
		while ((currentRound <= ROUNDS) && (winner == 0))
		{
            System.out.println("\nStarting Round " + currentRound);
            p1FaceUp.addItem(p1FaceDown.removeItem());            
            System.out.println("Player 1 plays a " + p1FaceUp.bottom().toString() + " (" + p1FaceUp.size() + ")");
            p2FaceUp.addItem(p2FaceDown.removeItem());            
			System.out.println("Player 2 plays a " + p2FaceUp.bottom().toString() + " (" + p2FaceUp.size() + ")");
			if (!(snapPool.empty()))
			{
				System.out.println("The Snap Pool shows a " + snapPool.bottom().toString() + " (" + snapPool.size() + ")");
				checkMatch = match(p1FaceUp,p2FaceUp,snapPool);
			}
			else
			{
				System.out.println("The Snap Pool is empty!");				
				checkMatch = match(true, p1FaceUp,p2FaceUp);
			}
			if (checkMatch)
			{
				int matchRoll= roll(10);
                if (matchRoll <= 1)
                {
                    System.out.println("No one called Snap!");
                }
                else if (matchRoll <= 5)
                {
                    System.out.println("Player 1 called Snap! \n");
                    
                    if (!(snapPool.empty()))
                    {
                        if (match(false, p2FaceUp, snapPool))
                        {
                            transferCards(p1FaceDown,p2FaceUp);
                            transferCards(p1FaceDown,snapPool);
                        }
        
                        else if (match(false, p1FaceUp, snapPool))
                        {
                            transferCards(p1FaceDown,snapPool);
                        }						
                    }
                    if (!(p2FaceUp.empty()) && match(false, p1FaceUp, p2FaceUp))
                    {
                        transferCards(p1FaceDown,p2FaceUp);
                    }
                    System.out.println("Shuffling Player 1's face-down pile... \n");
                    p1FaceDown.shuffle();
                }
                else if (matchRoll <=9)
                {
                    System.out.println("Player 2 called Snap! \n");
        
                    if (!(snapPool.empty()))
                    {
                        if (match(false, p1FaceUp, snapPool))
                        {
                            transferCards(p2FaceDown,p1FaceUp);					
                            transferCards(p2FaceDown,snapPool);
                        }
        
                        else if (match(false, p2FaceUp, snapPool))
                        {
                            transferCards(p2FaceDown,snapPool);					
                        }						
                    }
                    if (!(p1FaceUp.empty()) && match(false, p2FaceUp, p1FaceUp))
                    {
                        transferCards(p2FaceDown,p1FaceUp);				
                    }
        
                    System.out.println("Shuffling Player 2's face-down pile... \n");
                    p2FaceDown.shuffle();				
                }
			}
			else
			{
				System.out.println("No match");
				int p1Roll= roll(100);
				int p2Roll= roll(100);
				if (p1Roll < 1)
				{
					System.out.println("Player 1 incorrectly called Snap! \n");
					System.out.println("Shuffling Player 1's face-up pile... \n");
					p1FaceUp.shuffle();
					System.out.println("Player 1 moves " + p1FaceUp.size() + " cards to the Snap Pool");					
					while (!(p1FaceUp.empty()))
						snapPool.addItem(p1FaceUp.removeItem());
				}
				if (p2Roll < 1)
				{
					System.out.println("Player 2 incorrectly called Snap! \n");
					System.out.println("Shuffling Player 2's face-up pile... \n");
					p2FaceUp.shuffle();
					System.out.println("Player 2 moves " + p2FaceUp.size() + " cards to the Snap Pool");					
					while (!(p2FaceUp.empty()))
						snapPool.addItem(p2FaceUp.removeItem());
				}
			}

			if (p1FaceDown.empty() && p1FaceUp.empty())
			{
				winner = 2;
				System.out.println("Player 1 is out of cards!");
			}				
			else if (p1FaceDown.empty())
			{
				System.out.println("Player 1's face-down pile is empty!");
				transferCards(p1FaceDown,p1FaceUp);
				System.out.println("Shuffling Player 1's face-down pile... \n");
				p1FaceDown.shuffle();
			}

			if (p2FaceDown.empty() && p2FaceUp.empty())
			{
				winner = 1;
				System.out.println("Player 2 is out of cards!");
			}				
			else if (p2FaceDown.empty())
			{
				System.out.println("Player 2's face-down pile is empty!");			
				transferCards(p2FaceDown,p2FaceUp);
				System.out.println("Shuffling Player 2's face-down pile... \n");
				p2FaceDown.shuffle();
			}
			currentRound++;
		}

		if (currentRound > ROUNDS && winner == 0)
		{
			int p1Total = p1FaceDown.size() + p1FaceUp.size();
			int p2Total = p2FaceDown.size() + p2FaceUp.size();
			System.out.println("\nPlayer 1 has " + p1Total + " cards!");
			System.out.println("Player 2 has " + p2Total + " cards!");			
			if (p1Total > p2Total) winner = 1;
			else if (p2Total > p1Total) winner = 2;
			else if (p1Total == p2Total) winner = 3;
		}

		if (winner == 3)
			System.out.println("It's a tie! \n");
		else
		{
			System.out.println("Player " + winner + " wins!");
		}
	}

	/**
	 * Determines if there is a match showing in two
	 * face-up piles
	 * 
	 * @param msg A boolean value to determine whether out is displayed
	 * @param p1  The first player
	 * @param p2  The second player
	 * @return True for a match, false otherwise
	 */
	public static boolean match(boolean msg, MultiDS<Card> p1, MultiDS<Card> p2)
	{
		Card p1Showing = p1.bottom();
		Card p2Showing = p2.bottom();
		int result = p1Showing.compareTo(p2Showing);
		if (result == 0)
		{
            if (msg)
			    System.out.println("Match: " + p1Showing.toString() + ", " + p2Showing.toString());
			return true;
		}
		else
			return false;
	}

	/**
	 * Determines if ther is a match showing in three
	 * face-up piles
	 * 
	 * @param p1 The first player
	 * @param p2 The second player
	 * @param sp The Snap Pool
	 * @return True if match, false otherwise
	 */
	public static boolean match(MultiDS<Card> p1, MultiDS<Card> p2, MultiDS<Card> sp)
	{
		Card p1Showing = p1.bottom();
		Card p2Showing = p2.bottom();
		Card spShowing = sp.bottom();		
		int result = p1Showing.compareTo(p2Showing);
		String text;
		if (result == 0)
		{
			text = p1Showing.toString() + ", " + p2Showing.toString();
			result = p1Showing.compareTo(spShowing);
			if (result == 0)
			{
				text = text + ", " + spShowing.toString();
			}
			System.out.println("Match: " + text);
			return true;
		}
		else
		{
			result = spShowing.compareTo(p1Showing);
			if (result == 0)
			{
				System.out.println("Match: " + p1Showing.toString() + ", " + spShowing.toString());
				return true;
			}
			else if (spShowing.compareTo(p2Showing) == 0)
			{
				System.out.println("Match: " + p2Showing.toString() + ", " + spShowing.toString());
				return true;
			}
			else return false;
		}
		
	}
	
	/**
	 * Moves cards from one pile to another
	 * 
	 * @param winner the pile receiving cards
	 * @param loser the pile losing cards
	 */
	public static void transferCards(MultiDS<Card> winner, MultiDS<Card> loser)
	{
		System.out.println("Moving " + loser.size() + " cards");
		while (!(loser.empty()))
		{
			winner.addItem(loser.removeItem());
		}
	}

	/**
	 * generates a random number
	 * 
	 * @param range number will be 0 <= num < range
	 */
	public static int roll(int range)
	{
		Random generator = new Random();
		int num = generator.nextInt(range);
		return num;
	}
	
}