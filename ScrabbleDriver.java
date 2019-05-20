/**
   John DeCarlo
   ScrabbleDriver.java
   Main program that runs application
**/

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ScrabbleDriver {

   public static ScrabbleBoard screen;          //ScrabbleBoard we will print out
   public static JLabel player1_score;          //Print out player_1's score
   public static JLabel player1_last;           //Print out player_1's last word
   public static JLabel player2_score;          //Print out player_2's score
   public static JLabel player2_last;           //Print out player_2's score
   public static JLabel tiles_left;             //Print out # of tiles we have left
   public static JLabel message;                //Print out main message
   
   public static void main(String[]args) throws Exception {
      JFrame board = new JFrame("Scrabble");    //Create our JFrame
      screen = new ScrabbleBoard();             //Create a scrabbleBoard
      createSideBar();                    //Side bar with all our options and messages
               
      board.setSize(900, 800);			   //Size of game window
      board.setLocation(450, 0);				//location of game window on the screen
      board.setResizable(false);          //Cannot change size of the screen
      board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit when close out 
      board.setContentPane(screen);       //Set contentPanel to our board
      board.setVisible(true);             //Make the screen visible
   }
   
   public static void updateScore() {
      player1_score.setText("Player 1: " + screen.getPlayer1().getTotalPoints() + " points");   //Update the score of player_1
      player2_score.setText("Player 2: " + screen.getPlayer2().getTotalPoints() + " points");   //Update the score of player_2
   } 
   
   public static void createSideBar() throws Exception {
      screen.setLayout(null);                            //Side bar is set to null
      JButton exchange = new JButton("Exchange Tiles");  //Exchange tiles for better ones your turn
      exchange.addActionListener(                        //Create the action listener for exchange button
         new ActionListener() { 
            public void actionPerformed(ActionEvent e) {    //Action performed
               if(screen.getTurnCount() % 2 == 0)  //If it's player #1's turn
                  screen.reset(screen.getPlayer1());  //call reset(player 1)
               else                              //If it's player #2's turn
                  screen.reset(screen.getPlayer2());  //call reset(player 2)
               if(screen.getExchange() == false) {	//If exchange is false
                  message.setText("<html>Select the tiles you would like to exchange</html>"); 
                  screen.setExchange();	//Flip exchange value
               } else if(screen.getExchangeTiles().size() == 0) {	//If size is zero, we have no selected tiles
                  screen.setExchange();		//Flip exchange value
                  message.setText("<html>Make a word or exchange tile(s)</html>");  //Print out error message
               }
               else {
                  screen.exchangeTiles();				//Call exchange tiles method
                  screen.incTurnCount();                //Increase the turn count
                  if(screen.getTurnCount() % 2 == 0)	//If it's player 1's turn now
                     message.setText("<html>Player 1's turn</html>");   //Update the message text
                  else									//If it's player 2's turn npw
                     message.setText("<html>Player 2's turn</html>");   //Update the message text
                  screen.setExchange();					//Flip exchange value
               }
               
            } 
         } );
      exchange.setBounds(725, 275, 125, 125);         //Set the boundaries for the exchange buttons
      JButton submit = new JButton("Submit Word");    //See if you can make the word you placed down
      submit.addActionListener(                       //Creat the action listener for the submit button
         new ActionListener() {                 
            public void actionPerformed(ActionEvent e) {   //Action performed
               if(screen.getTurnCount() % 2 == 0) {         //Player #1's turn      
                  if(screen.checkSubmitWord(screen.getDictionary()).equals("legal")) { //All the words we find are legal
                     screen.getPlayer1().setTotalPoints(screen.getPlayer1().getTotalPoints() + screen.computeScore());  //Update the total points for player1
                     screen.getPlayer1().refillRack(screen.getBag());   //Refill player #1's rack after we trade tiles
                     screen.setBoardWords();                //Set all the words on the board so they won't be cleared
                     player1_last.setText("<html>Last Word Played: </html>");  //Update the last word played text
                     screen.incTurnCount();                 //Increase the turn count
                     tiles_left.setText("<html>Tiles left in bag: " + screen.getBag().size() + " tile(s)</html>");   //Update # of tiles left in bag
                     message.setText("<html>Player 2's turn</html>");   //Update the message text
                  } 
                  else if(screen.checkSubmitWord(screen.getDictionary()).equals("dictionary")) {   //If we found a dictionary error
                     message.setText("<html>That is an invalid word</html>"); ///Print out error message
                     screen.reset(screen.getPlayer1());     //Repeat Player #1's turn
                  } 
                  else if(screen.checkSubmitWord(screen.getDictionary()).equals("single")) { //If we found a single tile error
                     message.setText("<html>You must make the tiles connect horizontally or vertically</html>"); //Print out error message 
                     screen.reset(screen.getPlayer1());    //Repeat Player #1's turn
                  } 
                  else if(screen.checkSubmitWord(screen.getDictionary()).equals("none")) {   //If the player never placed any tiles on the board
                     message.setText("<html>Make a word or exchange tile(s)</html>");   //Print out error message
                     screen.reset(screen.getPlayer1());    //Repeat Player #1's turn
                  } 
                  else {   //If the player never placed their tiles on the star to start the game
                     message.setText("<html>Your word must be placed on the star to start the game</html>");   //Print out error message
                     screen.reset(screen.getPlayer1());  //Repeat Player #2's turn
                  }
               } else { //The turn_count is odd, so it is player #2's turn
                  if(screen.checkSubmitWord(screen.getDictionary()).equals("legal")) {    //All the words we find are legal 
                     screen.getPlayer2().setTotalPoints(screen.getPlayer2().getTotalPoints() + screen.computeScore());  //Update the total points for player2
                     screen.getPlayer2().refillRack(screen.getBag());   //Refill player #2's rack after we trade tiles
                     screen.setBoardWords();                //Set all the words on the board so they wont be cleared
                     player2_last.setText("<html>Last Word Played: </html>"); //Update the last word played text
                     screen.incTurnCount();                 //Increase the turn count
                     tiles_left.setText("<html>Tiles left in bag: " + screen.getBag().size() + " tile(s)</html>");   //Update # of tiles left in bag
                     message.setText("<html>Player 1's turn</html>");   //Update the message text
                  } 
                  else if(screen.checkSubmitWord(screen.getDictionary()).equals("dictionary")) { //If we found a dictionary error
                     message.setText("<html>That is an invalid word</html>"); //Print out error message
                     screen.reset(screen.getPlayer2());     //Repeat Player #2's turn
                  } 
                  else if(screen.checkSubmitWord(screen.getDictionary()).equals("single")) {  //If we found a single tile error
                     message.setText("<html>You must make the tiles connect horizontally or vertically</html>");  //Print out error message
                     screen.reset(screen.getPlayer2());     //Repeat Player #2's turn
                  } 
                  else if(screen.checkSubmitWord(screen.getDictionary()).equals("none")) { //If the player never placed any tiles on the board
                     message.setText("<html>Make a word or exchange tile(s)</html>");  //Print out error message
                     screen.reset(screen.getPlayer2());     //Repeat Player #2's turn
                  } 
                  else { //If the player never placed their tiles on the star to start the game
                     message.setText("<html>Your word must be placed on the star to start the game</html>");   //Print out error message
                     screen.reset(screen.getPlayer2());  //Repeat Player #2's turn
                  }
               }
               updateScore(); //Update the score messages
            } 
         } );
      submit.setBounds(725, 425, 125, 125);          //Set boundaries for the submit button
      JButton clear = new JButton("Clear");          //Clear the board after turn
      clear.addActionListener(
         new ActionListener() { 
            public void actionPerformed(ActionEvent e) { //Action performed
               if(screen.getTurnCount() % 2 == 0) {   //If it's player #1's turn
                  screen.reset(screen.getPlayer1());  //call reset(player 1)
               } else {                               //If it's player #2's turn
                  screen.reset(screen.getPlayer2());  //call reset(player 2)
               }
            } 
         } );
      clear.setBounds(725, 575, 125, 125);            //Set boundaries for the clear button
      screen.add(exchange);                  //Add exchange button to screen
      screen.add(submit);                    //Add submit button to screen
      screen.add(clear);                     //Add clear button to screen
      player1_score = new JLabel("<html>Player 1: " + screen.getPlayer1().getTotalPoints() + " points</html>");   //Initialize player1_score
      player1_last = new JLabel("<html>Last Word Played: </html>");  //Initialize player1_last
      player2_score = new JLabel("<html>Player 2: " + screen.getPlayer1().getTotalPoints() + " points</html>");   //Initialize player2_score
      player2_last = new JLabel("<html>Last Word Played: </html>");  //Initialize player2_last
      tiles_left = new JLabel("<html>Tiles left in bag: " + screen.getBag().size() + " tile(s)</html>"); //Initialized tiles_left
      message = new JLabel("<html>Player 1's turn</html>");    //Initalize message
      player1_score.setBounds(700, 25, 150, 25);   //Set bounds for player1_score
      player1_last.setBounds(700, 50, 150, 25);    //Set bounds for player1_last
      player2_score.setBounds(700, 100, 150, 25);  //Set bounds for player2_score
      player2_last.setBounds(700, 125, 150, 25);   //Set bounds for player2_last
      tiles_left.setBounds(700, 165, 150, 25);     //Set bounds for tiles_left
      message.setBounds(700, 175, 150, 100);       //Set bounds for message
      screen.add(player1_score);       //Add player1_score to screen
      screen.add(player1_last);        //Add player1_last to screen
      screen.add(player2_score);       //Add player2_score to screen
      screen.add(player2_last);        //Add player2_last to screen
      screen.add(tiles_left);          //Add tiles_left to screen
      screen.add(message);             //Add message to screen
   }
   
   public static class listen implements KeyListener 
   { 
      public void keyTyped(KeyEvent e)
      {
         
      }
      
      public void keyPressed(KeyEvent e)
      {
         
      }
      
      public void keyReleased(KeyEvent e)
      {
      
      }
   }
}
