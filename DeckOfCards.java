import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JOptionPane;

import java.util.*;

  	public class DeckOfCards {

  		private ArrayList<Card> deck;
  		private int currentCard;
  		private static final int NUMBER_OF_CARDS = 52;
	    
  		private static final SecureRandom randomNumbers = new SecureRandom();
  		
    
  		
   public DeckOfCards() {
	   
	   String [] faces = {"Ace","Deuce","Three","Four","Five","Six","Seven","Eight","Nine",
			   "Ten","Jack","Queen","King"};
	   
	   String [] suits = {"Hearts","Diamonds","Clubs","Spades"};
	   
	   
	   this.currentCard = 0;
	   
	   this.deck = new ArrayList<Card>();
	   
	   for(int count = 0; count < NUMBER_OF_CARDS; count++) {
		   
		   deck.add( new Card(faces[count % 13], suits[count / 13] ));
	   }
	 
   }		

   public void shuffle() {
	   
	   currentCard = 0;
	   
	   Card [] ArrayDeck = new Card[NUMBER_OF_CARDS];  
	   Card [] pack =  this.deck.toArray(ArrayDeck);
	   
	   ArrayList<Card> shuffled = new ArrayList<Card>();
	   
	   
	   for(int first = 0; first < NUMBER_OF_CARDS; first++) {
		      
		   //generate random number between 0-51
		   int second = randomNumbers.nextInt(NUMBER_OF_CARDS);
		      
		   Card tmp = pack[first];
		   pack[first] = pack[second];
		   pack[second] = tmp;   	   
	   }   
	  
	   Collections.addAll(shuffled, pack);
	   this.deck = shuffled;
   }
   
   public Card dealCard() {
	   
	   Card deal;
	   currentCard = 0;
	 
	   if(currentCard < 52) {
		   
		   deal = new Card(deck.get(currentCard));
		   deck.remove(0);
		   currentCard++;
	       return deal;
	   }
	   else {
		   return null;
	   }
	   
   }
   
   public ArrayList<Card> getDeck(){
	   
	   return this.deck;
   }
 
      
}