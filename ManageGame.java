/**
 * @author Alexander Gavrilov
 * 
 * Class ManageGame implements BlackJack between two players : dealer and player.
 * the class generates a shuffled stack of cards for the game from the DeckOfCards and Card classes, and give two cards for each player.
 * 
 * the player sees the cards in his hand and the number of points he has,and the player sees only one card of the dealer,after the
 * player choose not to take more cards the dealer take cards, and after every player took his cards they reveal the cards,the player that 
 * closer to 21 points win the round. if a player points greater the 21 he automatically lose the round. 
 * 
 */




import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Iterator;

public class ManageGame {
  
	private int currentPlayer;//0 is the player and 1 is the dealer
	private DeckOfCards stack;
	private ArrayList<Card> playerCards, dealerCards;
	private int playerScore, dealerScore;
	private boolean playerEndGame, dealerEndGame;
	private int playerAces, dealerAces;
	private int playerAcesChangedToOne, dealerAcesChangedToOne;
	
	
	public ManageGame() {
		
		this.currentPlayer = 0;//player = 0,dealer = 1
		this.stack = new DeckOfCards();//stack of cards
	    this.stack.shuffle();//shuffle cards
	    this.playerCards = new ArrayList<Card>();	
		this.dealerCards = new ArrayList<Card>();
		this.playerAces = 0;
		this.dealerAces = 0;
		this.playerAcesChangedToOne = 0;
		this.dealerAcesChangedToOne = 0;
		
		Card player1 = stack.dealCard();
		Card player2 = stack.dealCard();
		Card dealer1 = stack.dealCard();
		Card dealer2 = stack.dealCard();
		
		if(player1.getFace() == "Ace")
			playerAces++;
		
		if(player2.getFace() == "Ace")
			playerAces++;
		
		if(dealer1.getFace() == "Ace")
			dealerAces++;
		
		if(dealer2.getFace() == "Ace")
			dealerAces++;
		
		
		this.playerCards.add(player1);
		this.playerCards.add(player2);
		this.dealerCards.add(dealer1);
		this.dealerCards.add(dealer2);
		
		this.calcScore(playerCards, 0);
		this.calcScore(dealerCards, 1);
		
		if(dealerAces > 1 || playerAces > 1) {
		   this.AceToOne(0);
		   this.AceToOne(1);
		}
		this.playerEndGame = false;
		this.dealerEndGame = false;			 
	}
	
	//calculate the current score of the player/dealer
	public void calcScore(ArrayList cards,int player) {
		  	
	   Iterator iter = cards.iterator();
	   int score = 0;
	   int playerChangedAces  = this.playerAcesChangedToOne;
	   int dealerChangedAces = this.dealerAcesChangedToOne;
			   
	   while(iter.hasNext()) {
		      
		   Card c = (Card) iter.next();
		   if(c!=null)
			   score = score + c.cardValue();   
	   }
	   
	   if(player == 0) {
		   this.playerScore = score - (10*playerChangedAces);
	   }else{
		   this.dealerScore = score - (10*dealerChangedAces);
	   }
	   
	}
	
	//after every card the player or the dealer take update all the data related to each one of them and display it
	public void updateGameStatus(int player,ManageGame game) {
	    
		if(player == 0) {
			
			switch(JOptionPane.showConfirmDialog(null,
					 "this is Players hand:\n" + game.playerCards + "\n" + "Player score:" + game.playerScore + "\n"
				    +"this is Dealers visible Card:\n" + game.dealerCards.get(0).toString() + "\n" 
					+ "\nPull more Cards ?")) {
			
			    //pull more cards
				case 0:
					Card pulled = game.stack.dealCard();
					if(pulled!=null && pulled.getFace() == "Ace")
						game.playerAces++;
					game.playerCards.add(pulled);
					game.calcScore(game.playerCards, player);
					
					//if score is over 21 check if the player as aces to save him from losing the game init/main
					if(game.playerScore > 21) {
						game.AceToOne(0);
					}
			
			 	    break;	
			
				case 1:
					game.playerEndGame = true;
			        game.switchPlayers();
			        break;
			
			   
				case 2:
			        game.playerEndGame = true;
			        game.switchPlayers();
			        break;	
			}	
		}
		
		else if(player == 1) {
			
			    if(game.dealerScore > game.playerScore) {
			    	
					game.dealerEndGame = true;
					return;

			    }
			  	
				if(game.dealerScore>=17 && game.dealerScore > game.playerScore || game.playerScore > 21 ) {
					game.dealerEndGame = true;
					game.switchPlayers();
					return;	
				}
					
				  while(game.dealerEndGame == false && game.dealerScore < game.playerScore){
					
					Card pulled = game.stack.dealCard();
					    if(pulled!=null && pulled.getFace() == "Ace")
						    game.dealerAces++;
					        game.dealerCards.add(pulled);
					        game.calcScore(game.dealerCards, player);
					
					    if(game.dealerScore > 21) {
						    game.AceToOne(1);
						    if(game.dealerScore > 21) {
						    	game.dealerEndGame = true;
						    	return;
						    }
					    }
					    if(game.dealerScore > game.playerScore){
						    game.dealerEndGame = true;
						    return;
					    }
					    
					    if(game.dealerScore > 21) {
					    	game.dealerEndGame = true;
					    	return;
					    }
					    if(game.dealerScore < 22 && game.dealerScore > game.playerScore) {
					    	game.dealerEndGame = true;
					    	return;
					    }	
				  }
				  game.dealerEndGame = true;
				  
				  
		     }
	}
		
	//Method that change the value of the ace from 11 to 1 if needed
	public void AceToOne(int player) {
		
		
		if(player == 0 && this.playerScore > 21 && this.playerAces > 0 
				&& this.playerAces != this.playerAcesChangedToOne) {
			
			while(this.playerScore > 21 && this.playerAces != this.playerAcesChangedToOne) {
			   this.playerAcesChangedToOne++;
			   this.calcScore(this.playerCards, 0);
			}
		}
		
		if(player == 1 && this.dealerScore > 21 && this.dealerAces > 0 
				&& this.dealerAces != this.dealerAcesChangedToOne) {
			
			while(this.dealerScore > 21 && this.dealerAces != this.dealerAcesChangedToOne) {
				   this.dealerAcesChangedToOne++;
				   this.calcScore(this.dealerCards, 1);
			}	
		}
	}
		
	
	public void switchPlayers() {
		
		if(this.currentPlayer == 0)
			this.currentPlayer = 1;
		else {
			this.currentPlayer = 0;
		}	
	}	
	
	//display the outcome of the round
	public void endGame() {
		
		if(this.dealerScore < 22 && this.playerScore > 21)
			  JOptionPane.showMessageDialog(null,this.resultToString() + "\n" + "Casino wins!");
		                   
		if(this.playerScore < this.dealerScore && this.playerScore<22 && this.dealerScore<22)  
	          JOptionPane.showMessageDialog(null,this.resultToString() + "\n" + "Casino wins!");
		     
		if(this.playerScore < 22 && this.dealerScore > 21)
		      JOptionPane.showMessageDialog(null,this.resultToString() + "\n" + "Player wins!");
		          
		if((this.playerScore > this.dealerScore) && this.playerScore<22 && this.dealerScore<22 ) 
	          JOptionPane.showMessageDialog(null,this.resultToString() + "\n" + "Player wins!");
		      
		if(this.playerScore == this.dealerScore)
		      JOptionPane.showMessageDialog(null,this.resultToString() + "\n" + "Its a Draw!");     
		
		return;
	}
		
	       
	private String resultToString() {
		
		return "Player score :" + this.playerScore + "\n" + this.playerCards + "\n" +
			   "Dealer Score :" + this.dealerScore + "\n" + this.dealerCards + "\n";			    
	} 
	
   
	//initialize the game
    public static void init() {
		
        ManageGame game = new ManageGame();
        
		
		while( game.playerScore < 22 && game.dealerScore < 22 
				&& (game.playerEndGame == false || game.dealerEndGame == false)) {
			
				game.updateGameStatus(game.currentPlayer,game);	
		}
		game.endGame();
	}
	
    //if the player wants to play another game return true else false
	public static boolean newGame() {
		
		  int startNewGame = JOptionPane.showConfirmDialog(null, "New Game?"); 
          
			  if(startNewGame == 0)
				  return true;
			  return false;
	}
	
	
	public static void main(String[] args) {
			
	    while(newGame()) {
	    	init();
	    }	    
	}		
}					
		