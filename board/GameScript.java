package board;

public class GameScript {

	public static void main(String[] args) {
		//This is the script which will call objects and produce the text based representations of the current board
	
		GameTextDisplay gameinterface = new GameTextDisplay();
		int size = Integer.parseInt(gameinterface.prompt("Enter a number from 3 to 10 as the size of one dimension of our game board:"));
		gameinterface.setGame(new Game(size));
		
		
		//continuously reprompts the user until the board is full then prints the score
		gameinterface.tick();
		
	}

}
