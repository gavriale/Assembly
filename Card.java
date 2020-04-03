
public class Card {

	private String face;
	private final String suit;
		
	public Card(String cardFace,String cardSuit) {
			
		this.face = cardFace;
		this.suit = cardSuit;
	}
	
	//
	public Card(Card copy) {
		
		this.face = copy.face;
		this.suit = copy.suit;
	}
	
	public String getFace() {
		
		return this.face;
	}
	
	public String toString() {
		
		return face + " of " + suit;
	}
	
	public int cardValue(){
		
		if(face == "Ten" ||face=="Jack"||face=="Queen"||face=="King")
			return 10;
		if(face == "Deuce")
			return 2;
		if(face == "Three")
			return 3;
		if(face == "Four")
			return 4;
		if(face == "Five")
			return 5;
		if(face == "Six")
			return 6;
		if(face == "Seven")
			return 7;
		if(face == "Eight")
			return 8;
		if(face == "Nine")
			return 9;
		if(face == "Ace")
			return 11;
		
		

	return 0;	 		
   }		
}