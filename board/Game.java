package board;
import handqueue.HandQueue;
import handqueue.LinkedStack;
import java.util.concurrent.ThreadLocalRandom;

public class Game{
	//The table 2D array for played Card
	Card[][] table;
	
	
	/* USER ElELMENTS */
	//The hand of potential cards the user can play - it is a Queue
	HandQueue<Card> hand = new HandQueue<Card>();
	
	//The deck of soon to be used cards
	LinkedStack<Card> deck = new LinkedStack<Card>();
	
	//In the constructor. semi random cards will be pushed to the stack in a semi random order, ( appx 1/4th O's, 1/4 X's, 1/4 $'s and 1/4 8's . Each turn the user has the option to pop a card into the hand
	
	
	//should be between 3 and 10
	//stores the size of each size of the table
	int tablesize;
	
	public Game(int size){
		//takes size as one side dimension of array board
		tablesize = size;
		//fill the array with Card Variables containing a '*' symbolizing an empty slot
		int tofill = size - 1;
		table = new Card[size][size];
		while(tofill >= 0) {
			int tofillY = size -1 ;
			while(tofillY >= 0) {
				table[tofill][tofillY] = new Card('*');
				tofillY--;
			}
			tofill--;
		}
		
		//pushed 150 cards to the draw pile
		int topushtostack = 150;
		while(topushtostack >= 0) {
			deck.push(randomCardtype());
			topushtostack--;
		}
		
		
		//draw 3 cards to the hand from the stack
		draw();
		draw();
		draw();
	}
	
	public Card[][] getTable(){
		return table;
	}
	
	public HandQueue<Card> getHand(){
		return hand;
	}
	
	public int gettablesize() {
		return tablesize;
	}
	
	//add a new card to the table
	public void add(int xposition,int yposition, Card elem) {
		table[xposition][yposition] = elem;
	}
	public void add(int xposition, int yposition, char elem) {
		table[xposition][yposition] = new Card(elem);
	}
	
	//removes a card from the deck and places it in the hand
	void draw(){
		hand.enqueue(deck.pop());
	}
	
	public void place(int handposition, int xposition, int yposition) {
		if(isFull()) {
			throw new IndexOutOfBoundsException("Cannot place in a full table");
		}
		if((handposition > 2) || (handposition < 0)) {
			throw new IndexOutOfBoundsException("Index of card in hand must be between 0 and 2");
		}
		if(handposition == 0) 
			add(xposition, yposition, hand.dequeue());
		
		if(handposition == 1) {
			Card storedCard1 = hand.dequeue();
			add(xposition, yposition, hand.dequeue());
			Card storedCard2 = hand.dequeue();
			hand.enqueue(storedCard1);
			hand.enqueue(storedCard2);
		}
		if(handposition == 2) {
			Card storedCard1 = hand.dequeue();
			Card storedCard2 = hand.dequeue();
			add(xposition, yposition, hand.dequeue());
			hand.enqueue(storedCard1);
			hand.enqueue(storedCard2);
		}
		draw();
	}
	
	//finds an empty place in our array to place a new card
	public void enemyplace() {
		if(isFull()) {
			throw new IndexOutOfBoundsException("Cannot place in a full table");
		}
		int randomNumX = ThreadLocalRandom.current().nextInt(0, tablesize);
		int randomNumY = ThreadLocalRandom.current().nextInt(0, tablesize);
		Card checkCard = table[randomNumX][randomNumY];
		if(checkCard.getTypeChar() != '*') {
			enemyplace();
		}
		else {
			Card cardtoinsert = randomCardtype();
			table[randomNumX][randomNumY] = cardtoinsert;
		}	
	}
	
	//returns a card of random type, 0 to 3 - used to enqueue random cards to the hand, and also place randomly on the board
	public Card randomCardtype(){
		int randomNumforcard = ThreadLocalRandom.current().nextInt(0, 4);
		Card cardtoreturn = new Card(Character.forDigit(randomNumforcard, 10));
		return cardtoreturn;	
	}
	
	public boolean isFull() {
		boolean full = true;
		int sizetoiterate = tablesize - 1;
		while(sizetoiterate >= 0){
			int sizetoiteratey = tablesize - 1;
			while(sizetoiteratey >= 0) {
				if(table[sizetoiterate][sizetoiteratey].getTypeChar() == '*') {
					full = false;	
				}
				sizetoiteratey--;
			}
			sizetoiterate--;
		}
		return full;
	}
	
	public boolean spotisEmpty(int xdimension, int ydimension) {
		if(table[xdimension][ydimension].getTypeChar() == '*') {
			return true;
		}
		else {
			return false;
		}
	}
	
	//determines if each direction has a card in that direction
	public boolean hasUp(int x, int y) {
		if(y>0) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean hasDown(int x, int y) {
		if(y < tablesize - 1) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean hasLeft(int x, int y) {
		if(x > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean hasRight(int x, int y) {
		if(x < tablesize - 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//determines if the card in that direction matches the coordinates of the card
	public boolean UpMatches(int x, int y) {
		if(hasUp(x, y)) {
			Card first = table[x][y];
			Card second = table[x][y-1];
			if(first.getTypeChar() == second.getTypeChar()) {
				return true;
				
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	public boolean DownMatches(int x, int y) {
		if(hasDown(x, y)) {
			Card first = table[x][y];
			Card second = table[x][y+1];
			if(first.getTypeChar() == second.getTypeChar()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	public boolean LeftMatches(int x, int y) {
		if(hasLeft(x, y)) {
			Card first = table[x][y];
			Card second = table[x-1][y];
			if(first.getTypeChar() == second.getTypeChar()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	public boolean RightMatches(int x, int y) {
		if(hasRight(x, y)) {
			Card first = table[x][y];
			Card second = table[x+1][y];
			if(first.getTypeChar() == second.getTypeChar()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public boolean threeInARow(int x, int y) {
		//check if has each direction, then if that direction matches, move in that direction, check if point visited and check in that direction again. if 3 in a row, return true
		boolean result = false;
		if(UpMatches(x,y)) {
			if(UpMatches(x,y-1)) {
				result = true;
			}
		}
		if(DownMatches(x,y)) {
			if(DownMatches(x,y+1)) {
				result = true;
			}
		}
		if(LeftMatches(x,y)) {
			if(LeftMatches(x-1,y)) {
				result = true; 
			}
		}
		if(RightMatches(x,y)) {
			if(RightMatches(x+1,y)) {
				result = true;
			}
		}
		return result;
	}
	
}
