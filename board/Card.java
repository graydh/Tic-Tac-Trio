package board;

class Card {
	//This interface is used for creating all cards in the game
	// Cards will have type 0, 1, 2, or 3 
	
	char type;
	 
	Card(char given){
		type = given;
	}
	Card(){
		type = ' ';
	}
	
	void setType(char given) {
		type = given;
	}
	
	//every card must implement the getType() method
	 String getTypeString() {
		return Character.toString(type);
	}
	 
	 char getTypeChar() {
		 return type; 
		 
	 }
	 
	 
}
