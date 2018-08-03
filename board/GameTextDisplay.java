package board;
import java.util.Scanner;
import handqueue.HandQueue;

public class GameTextDisplay {
	Scanner reader = new Scanner(System.in);
	Game maingame;	
	//used for checking 3 in a rows
	boolean[][] visitedarray;
	
	public GameTextDisplay() {
			
	}
	
	public GameTextDisplay(Game centralgame) {
		maingame = centralgame;	
	}
	
	public void setGame(Game inputgame) {
		maingame = inputgame;
	}
	
	public String prompt() {
		return reader.next();
	}
	
	public String prompt(String message) {
		System.out.println(message);
		return reader.next();
	}
	
	public static void printRow(Card[] row) {
        //Prints one row in the card table
		for (Card i : row) {
        	if(i != null) {
        		System.out.print(i.getTypeString());
        	}
            System.out.print("\t");
        }
        System.out.println();
    }

    public void showTable() {
    	//shows the current table status
    	System.out.println("Current Table:");
        for(Card[] row : maingame.getTable()) {
            printRow(row);
        }
    }
	
	public void showHand() {
		//displays the card elements in the hand queue
		System.out.println("Current Hand:");
		HandQueue<Card> hand = maingame.getHand();
		Card firstCard = hand.dequeue();
		Card secondCard = hand.dequeue();
		Card thirdCard = hand.dequeue();
		System.out.println(firstCard.getTypeString() + ", " + secondCard.getTypeString() + ", " + thirdCard.getTypeString());
		hand.enqueue(firstCard);
		hand.enqueue(secondCard);
		hand.enqueue(thirdCard);
	}
	
	public void analyze(String input) {
		//analyzes a coordinate string input 
		int handposition = Character.digit(input.charAt(0), 10);
		int xdimension = Character.digit(input.charAt(2), 10);
		int ydimension = Character.digit(input.charAt(1), 10);
		if(maingame.spotisEmpty(xdimension, ydimension)) {
			maingame.place(handposition, xdimension, ydimension);
			
		}
		else {
			System.out.println("Already a Card in that spot");
		}
	}
	
	public int countthreeinarow() {
		int dimensionsize = maingame.gettablesize();
		visitedarray = new boolean[dimensionsize][dimensionsize];
		
		int startx = 0;
		int starty = 0;
		int count = 0;
		while(startx < dimensionsize) {
			while(starty < dimensionsize) {
				if(maingame.threeInARow(startx, starty)) {
					count++;
					System.out.println("3 in a row point: "+startx + " " + starty);
				}
				System.out.println(starty);
				visitedarray[startx][starty] = true;
				starty++;
			}
			starty = 0;
			startx++;
		}
		return count;
	}
	
	public boolean isFull(){
		boolean result = true;
		result = maingame.isFull();
		return result;
	}
	
	public void tick(){
		if(!maingame.isFull()) {
			//show the table and your hand
			showTable();
			showHand();
			//prompt for another input to add
			analyze(prompt("Enter 3 numbers, the index of your hand card to insert, the x coordinate, the y coordinate:"));
			//tick again
			if(!maingame.isFull())
				maingame.enemyplace(); 
			tick();
		}
		else {
			int result = countthreeinarow();
			showTable();
			System.out.println("You scored: " + result);
			System.out.println("Meaning your board resulted in " + result + " trifecta's of numbers in a row");
		}
	}
}
