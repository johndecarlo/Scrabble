/**
 * John DeCarlo
 * ScrabbleBoard.java
 * Contains methods for our
 * ScrabbleBoard and ScrabbleDriver
 */
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ScrabbleBoard extends JPanel implements MouseListener, MouseMotionListener {
   //Letter Tile Images
   private ImageIcon letter_A = new ImageIcon("letters/letter_A.png");  //Tile A
   private ImageIcon letter_B = new ImageIcon("letters/letter_B.png");  //Tile B
   private ImageIcon letter_C = new ImageIcon("letters/letter_C.png");  //Tile C
   private ImageIcon letter_D = new ImageIcon("letters/letter_D.png");  //Tile D
   private ImageIcon letter_E = new ImageIcon("letters/letter_E.png");  //Tile E
   private ImageIcon letter_F = new ImageIcon("letters/letter_F.png");  //Tile F
   private ImageIcon letter_G = new ImageIcon("letters/letter_G.png");  //Tile G
   private ImageIcon letter_H = new ImageIcon("letters/letter_H.png");  //Tile H
   private ImageIcon letter_I = new ImageIcon("letters/letter_I.png");  //Tile I
   private ImageIcon letter_J = new ImageIcon("letters/letter_J.png");  //Tile J
   private ImageIcon letter_K = new ImageIcon("letters/letter_K.png");  //Tile K
   private ImageIcon letter_L = new ImageIcon("letters/letter_L.png");  //Tile L
   private ImageIcon letter_M = new ImageIcon("letters/letter_M.png");  //Tile M
   private ImageIcon letter_N = new ImageIcon("letters/letter_N.png");  //Tile N
   private ImageIcon letter_O = new ImageIcon("letters/letter_O.png");  //Tile O
   private ImageIcon letter_P = new ImageIcon("letters/letter_P.png");  //Tile P
   private ImageIcon letter_Q = new ImageIcon("letters/letter_Q.png");  //Tile Q
   private ImageIcon letter_R = new ImageIcon("letters/letter_R.png");  //Tile R
   private ImageIcon letter_S = new ImageIcon("letters/letter_S.png");  //Tile S
   private ImageIcon letter_T = new ImageIcon("letters/letter_T.png");  //Tile T
   private ImageIcon letter_U = new ImageIcon("letters/letter_U.png");  //Tile U
   private ImageIcon letter_V = new ImageIcon("letters/letter_V.png");  //Tile V
   private ImageIcon letter_W = new ImageIcon("letters/letter_W.png");  //Tile W
   private ImageIcon letter_X = new ImageIcon("letters/letter_X.png");  //Tile X
   private ImageIcon letter_Y = new ImageIcon("letters/letter_Y.png");  //Tile Y
   private ImageIcon letter_Z = new ImageIcon("letters/letter_Z.png");  //Tile Z
   private ImageIcon blank = new ImageIcon("letters/blankTile.png");  //Blank Tile
   
   //Board Space Images
   private ImageIcon blankSpace = new ImageIcon("board_spaces/blank.png"); //Blank board space
   private ImageIcon doubleLetter = new ImageIcon("board_spaces/doubleLetter.png"); //Double letter space
   private ImageIcon doubleWord = new ImageIcon("board_spaces/doubleWord.png");     //Double word space
   private ImageIcon tripleLetter = new ImageIcon("board_spaces/tripleLetter.png"); //Triple letter space
   private ImageIcon tripleWord = new ImageIcon("board_spaces/tripleWord.png");     //Triple word space
   private ImageIcon star = new ImageIcon("board_spaces/star.png");     //Star space
   private ImageIcon crossHair = new ImageIcon("crossHair.GIF");	//GIF immages can have transparency
   
   public static final int SIZE = 45;     //Board print out size
   
   public static Tile[][] board = new Tile[15][15];        //Create our scrabble board we will be using
   public static Player player1 = new Player();            //Create player one of our game
   public static Player player2 = new Player();            //Create player two of our game
   public static ArrayList<Tile> bag;                 //Create ArrayList of bag holding unused tiles
   public static ArrayList<String> dictionary;        //Create a dictionary that we can use to get words
   public static ArrayList<Integer> exchange_tiles;   //Creates a list to hold the tiles we wish to exchange
   public static Stack<Tile> tiles_used;  //Holds the tiles we place on the board in case theres an error
   public static int turn_count;          //Counter whether it's player one or two's turn
   public static boolean gameIsOver;      //There are no more moves and the game is over
   public static boolean selected;        //Whether we have a piece selected or not
   public static boolean exchange;        //Wehether we want to excahnge pieces or not
   public static int playerR;
   public static int playerC;
   public static int index, r2, c2;  	//Index to move pieces
         
   protected static int mouseX;			//location for the mouse pointer X
   protected static int mouseY;        	//location for the mouse pointer Y
      
   public ScrabbleBoard() throws Exception {
      bag = fillBag();                    //Fill our bag with tiles
      dictionary = createDictionary();    //Initialize our dictionary of words
      exchange_tiles = new ArrayList();   //Initalize our exchanged tiles array
      tiles_used = new Stack();           //Initalize our tiles_used stack
      player1.generateRack(bag);          //Generate player1 rack from the bag  
      player2.generateRack(bag);          //Generate player2 rack from the bag
      turn_count = 0;                     //Initalize the turn_count
      gameIsOver = false;                 //Initalize gameIsOver
      addMouseListener( this );           //Initalize mouseListener
      addMouseMotionListener( this );     //Initalize mouseMotionListener
      mouseX = 0;    //Initalize mouseX
      mouseY = 0;    //Initalize mouseY
   }

   public void paintComponent(Graphics g) {
      g.setColor(Color.red);     //Set the background color to no pieces as red
      super.paintComponent(g); 	//Call super method
      showBoard(g);					//draw the contents of the board on the screen
      showRack(g);               //draw the contents of the rack on the screen
   }
   
   //Display the board and its contents
   public void showBoard(Graphics g) {
      int x=0, y=0;
      //Print out the Scrabble Board
      for(int r = 0; r < board.length; r++) {   //# of rows
         x = 0;            //Reset x to zero
         for(int c = 0; c < board[0].length; c++) {   //# of columns
            if(board[r][c] != null) {  //If tiles on board is not null
               g.drawImage(getLetter(board[r][c]).getImage(), x, y, SIZE, SIZE, null); //Print out whatever letter is on the board
            } else {
               if(r == 7 && c == 7) {   
                  g.drawImage(star.getImage(), x, y, SIZE, SIZE, null); //Middle star of the board
               } else if(isDoubleLetter(r,c)) { 
                  g.drawImage(doubleLetter.getImage(), x, y, SIZE, SIZE, null);//Double letter score space
               } else if(isDoubleWord(r,c)) { 
                  g.drawImage(doubleWord.getImage(), x, y, SIZE, SIZE, null); //Double word score space
               } else if(isTripleLetter(r,c)) { 
                  g.drawImage(tripleLetter.getImage(), x, y, SIZE, SIZE, null); //Triple letter score space
               } else if(isTripleWord(r,c)) { 
                  g.drawImage(tripleWord.getImage(), x, y, SIZE, SIZE, null); //Triple word score space
               } else {
                  g.drawImage(blankSpace.getImage(), x, y, SIZE, SIZE, null); //Blank space
               }
            }
            x+=SIZE;	//Increment X by size
         }
         y+=SIZE;	//Increment Y by size
      } 
   }

   //Get the value that exchange currently is
   public boolean getExchange() {
      return exchange; //Return exchange
   }

   //Change if we want to exchange tiles or not
   public void setExchange() {
      if(this.exchange == true)   //If exchange is true, change to false
         this.exchange = false;		
      else
         this.exchange = true;   //If exchange is false, change to true
   }
   
   public ArrayList<Integer> getExchangeTiles() {
      return exchange_tiles;	//Return our exchange files array
   }

   //Return our bag of tiles
   public ArrayList<Tile> getBag() {
      return bag; //Return bag
   }
   
   //Return the tile that is at board location row, col
   public Tile get(int row, int col) {
      return board[row][col];	//Return tile at location board(row, col)
   }
   
   //Add tile to board
   public void addBoard(int row, int col, Tile temp) {
      board[row][col] = temp; //Set board[r][c] to our tile
   }
   
   //Remove tile from board
   public void removeBoard(int row, int col) {
      board[row][col] = null; //Set board[r][c] to null
   }
   
   //Add tile to the player's rack
   public void addRack(Player p, int index, Tile tile) {
      p.setTile(index, tile); //Set the tile at index to the rack
   }
   
   //Remove tile from the player's rack
   public static void removeRack(Player p, int index) {
      p.setTile(index, null); //Set the tile at index to null
   }
   
   //Get the dictionary holding all our words
   public ArrayList<String> getDictionary() {
      return dictionary;   //Return dictionary
   }
   
   //Print out the rack of the player who's turn it is
   public void showRack(Graphics g) {
      int x = 45, y = 700;    //Set starting point for x and y
      if(turn_count % 2 == 0) {  //If turn_count is even, its player #2's turn
         for(int p1 = 0; p1 < player1.getRack().length; p1++) {   //For each tile in the rack length
            g.drawImage(getLetter(player1.getTileIndex(p1)).getImage(), x, y, SIZE, SIZE, null); //Print out whatever letter is on the board 
            x+=(SIZE*2);	//Increment X by size * 2
         }
      }
      else {
         for(int p2 = 0; p2 < player2.getRack().length; p2++) {   //For each tile in the rack legnth
            g.drawImage(getLetter(player2.getTileIndex(p2)).getImage(), x, y, SIZE, SIZE, null); //Print out whatever letter is on the board 
            x+=(SIZE*2);	//Increment X by size * 2
         }
      }
   }
   
    //***BEGIN MOUSE STUFF***
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)	
      {
         repaint();
      }
   }

   public void mousePressed( MouseEvent e )
   {}

   public void mouseReleased( MouseEvent e )
   {}

   public void mouseEntered( MouseEvent e )
   {}

   //Picks up if we have a tile in the rack selected
   public static int rackLocation( MouseEvent e) {
      int button = e.getButton();
      if(button == MouseEvent.BUTTON1){
         int mouseR = mouseY; //(mouseY/SIZE)
         int mouseC = mouseX; //(mouseX/SIZE);
         if((mouseR >= 700 && mouseC >= 45 && mouseR < 745 && mouseC < 90)) { //Tile #1 selected
            return 0;
         } else if((mouseR >= 700 && mouseC >= 135 && mouseR < 745 && mouseC < 180)) { //Tile #2 selected
            return 1;
         } else if((mouseR >= 700 && mouseC >= 225 && mouseR < 745 && mouseC < 270)) { //Tile #3 selected
            return 2;
         } else if((mouseR >= 700 && mouseC >= 315 && mouseR < 745 && mouseC < 360)) { //Tile #4 selected
            return 3;
         } else if((mouseR >= 700 && mouseC >= 405 && mouseR < 745 && mouseC < 450)) { //Tile #5 selected
            return 4;
         } else if((mouseR >= 700 && mouseC >= 495 && mouseR < 745 && mouseC < 540)) { //Tile #6 selected
            return 5;
         } else if((mouseR >= 700 && mouseC >= 585 && mouseR < 745 && mouseC < 630)) { //Tile #7 selected
            return 6;
         } else 
            return -1;  //No tile is selected
      }
      return -1;  //No tile is selected
   }
   
   //See if our mouse clicked on a board location
   public static boolean boardLocation( MouseEvent e) {
      int button = e.getButton();   
      if(button == MouseEvent.BUTTON1) {	//If this is a mouse click
         int mouseR = (mouseY/SIZE);   //Get the row index for our mouse click
         int mouseC = (mouseX/SIZE);   //Get the col index for our mouse click
         if(mouseR >=0 && mouseC >= 0 && mouseR < board.length && mouseC < board[0].length && board[mouseR][mouseC] == null) {
            return true;   //Return true if the mouse click selects a location on the board
         }
         else if(mouseR >=0 && mouseC >= 0 && mouseR < board.length && mouseC < board[0].length && board[mouseR][mouseC] != null && board[mouseR][mouseC].getLetter().contains("#")) {
            return true;   //Return true if the mouse click selects a location on the board that contains a blank tile
         }
         else
            return false;  //Return false if otherwise
      }
      return false;  //Return false if press another button
   }

   //Series of events to happen when we click our mouse
   public void mouseClicked( MouseEvent e )
   {
      int button = e.getButton();  
      if(button == MouseEvent.BUTTON1) {     //int x = 45, y = 700;   
         r2 = (mouseY/SIZE);  //Row equals the selected row
         c2 = (mouseX/SIZE);  //Col equals the selected col
         if (turn_count % 2 == 0) { //If turn count is even, its player #1's turn
            //We want to exchange tiles in our rack
            if(exchange == true) {
               if(rackLocation(e) != -1) {	//If we have a tile from the rack selected
                  if(exchange_tiles.contains(rackLocation(e)))	//If exchange_tiles contains our exchange_tiles array
                     exchange_tiles.remove(rackLocation(e));	//Remove this from our exchange_tiles array
                  else
                     exchange_tiles.add(rackLocation(e));	//If we dont have it, add it to our exchange_tiles array
               }
            }
            //We have a blank tile that we want to chagne to a word
            else if(selected == false && boardLocation(e) && isBlankTile(r2, c2)) {   	//If is a location on the board and is a blank tile
               board[r2][c2].setLetter(changeBlankTile(board[r2][c2].getLetter()));		//Change the blank tile on our board
            }
            //Select a tile from the rack
            else if(selected == false) { //If selected is false, we need to select a piece
               if(rackLocation(e) != -1) {   //If we have a rack location selected
                  Tile temp = player1.getTileIndex(rackLocation(e)); //Get the tile of the index we selected 
                  if(temp != null) {   //If the tile is not equal to null
                     index = rackLocation(e);   //Get the index value
                     selected = true;  //We have a tile selected
                  }
               }
            }
            //Place a piece on the board
            else if(selected == true) { //Aleady selected piece
               if(boardLocation(e)) {  //If this is a board location
                  Tile temp = player1.getTileIndex(index);  //Temp is equal to the selected index
                  removeRack(player1, index);   //Remove that tile from the index
                  tiles_used.push(temp);     //Add tile to our temp stack temp stack
                  addBoard(r2, c2, temp);    //Add the piece to the board
                  selected = false;       //Selected is equal to false
               }  //Switch two tiles in the rack
               else if(rackLocation(e) != -1) {    //If this if a location on our rack
                  int temp_index = rackLocation(e);   //Get the index of the rack location
                  Tile temp1 = player1.getTileIndex(temp_index);  //temp1 equals our temp index
                  Tile temp2 = player1.getTileIndex(index); //temp2 equals our previously selected index
                  removeRack(player1, temp_index); //Remove temp_index from the rack
                  removeRack(player1, index);   //Remove index from the rack
                  addRack(player1, index, temp1);  //Add temp1 to the rack in the swapped spot
                  addRack(player1, temp_index, temp2);   //Add temp2 to the rack in the swapped spot
                  selected = false; //Selected is equal to false
               }
            }
            repaint();			//refresh the screen
         }
         else {   //If turn_count is odd, its player #2's turn  
          //We want to exchange tiles in our rack
            if(exchange == true) {
               if(rackLocation(e) != -1) {	//If we have a tile from the rack selected
                  if(exchange_tiles.contains(rackLocation(e)))	//If exchange_tiles contains our exchange_tiles array
                     exchange_tiles.remove(rackLocation(e));	//Remove this from our exchange_tiles array
                  else
                     exchange_tiles.add(rackLocation(e));	//If we dont have it, add it to our exchange_tiles array
               }
            }
            //We have a blank tile that we want to chagne to a word
            else if(selected == false && boardLocation(e) && isBlankTile(r2, c2)) {   	//If is a location on the board and is a blank tile
               board[r2][c2].setLetter(changeBlankTile(board[r2][c2].getLetter()));		//Change the blank tile on our board
            }
            //Select a tile from the rack
            else if(selected == false) { //We have not selected a tile from the rack
               if(rackLocation(e) != -1) {   //If we have selected a valid rack location
                  Tile temp = player2.getTileIndex(rackLocation(e)); 
                  if(temp != null) {	//If the selected location is not null
                     index = rackLocation(e);	//Set index to the tile at whatever index it is in the array
                     selected = true;	//Selected is now true
                  }
               }
            }
            //Place a piece on the board
            else if(selected == true) { //Aleady selected piece
               if(boardLocation(e)) {	//If the selected area is a location on the board
                  Tile temp = player2.getTileIndex(index);	//Temp is equal to the tile at the index
                  removeRack(player2, index);	//Remove the tile from the rack
                  tiles_used.push(temp);     //Add tile to our temp stack temp stack
                  addBoard(r2, c2, temp);    //Add the piece to the board
                  selected = false;		//Selected is now false
               }
               else if(rackLocation(e) != -1) {    //If this if a location on our rack
                  int temp_index = rackLocation(e);   //Get the index of the rack location
                  Tile temp1 = player2.getTileIndex(temp_index);  //temp1 equals our temp index
                  Tile temp2 = player2.getTileIndex(index); //temp2 equals our previously selected index
                  removeRack(player2, temp_index); //Remove temp_index from the rack
                  removeRack(player2, index);   //Remove index from the rack
                  addRack(player2, index, temp1);  //Add temp1 to the rack in the swapped spot
                  addRack(player2, temp_index, temp2);   //Add temp2 to the rack in the swapped spot
                  selected = false; //Selected is equal to false
               }
            }
            repaint();			//refresh the screen
         }
      }
   }
   
   public void mouseMoved( MouseEvent e)
   {
      mouseX = e.getX();	//Get mouseX value
      mouseY = e.getY();	//Get mouseY value
      	
      int mouseR = (mouseY/SIZE);	//mouseRow is equal to the row
      int mouseC = (mouseX/SIZE);	//mouseCol is equal to the col
     // System.out.println(mouseR+":"+mouseC);
      if(mouseR >=0 && mouseC >= 0 && mouseR < board.length && mouseC < board[0].length)
      {
         playerR = mouseR;	//Player R
         playerC = mouseC;	//Player C
      }
      else
      {
         playerR = board.length/2;
         playerC = board[0].length/2;
      
      }
      repaint();			//refresh the screen
   }

   public void mouseDragged( MouseEvent e)
   {}

   public void mouseExited( MouseEvent e )
   {}
   
   //Access Player 1
   public Player getPlayer1() {
      return player1;	//Return player 1
   }
   
   //Access Player 2
   public Player getPlayer2() {
      return player2;	//Return player 2
   }
   
   //Take the tile and get the image of the letter in that tile
   public ImageIcon getLetter(Tile tile) {
      if(tile != null) {
         if(tile.getLetter().equals("A") || tile.getLetter().equals("#A"))
            return letter_A;  //Letter A
         else if(tile.getLetter().equals("B") || tile.getLetter().equals("#B"))
            return letter_B;  //Letter B
         else if(tile.getLetter().equals("C") || tile.getLetter().equals("#C"))
            return letter_C;  //Letter C
         else if(tile.getLetter().equals("D") || tile.getLetter().equals("#D"))
            return letter_D;  //Letter D
         else if(tile.getLetter().equals("E") || tile.getLetter().equals("#E"))
            return letter_E;  //Letter E
         else if(tile.getLetter().equals("F") || tile.getLetter().equals("#F"))
            return letter_F;  //Letter F
         else if(tile.getLetter().equals("G") || tile.getLetter().equals("#G"))
            return letter_G;  //Letter G
         else if(tile.getLetter().equals("H") || tile.getLetter().equals("#H"))
            return letter_H;  //Letter H
         else if(tile.getLetter().equals("I") || tile.getLetter().equals("#I"))
            return letter_I;  //Letter I
         else if(tile.getLetter().equals("J") || tile.getLetter().equals("#J"))
            return letter_J;  //Letter J
         else if(tile.getLetter().equals("K") || tile.getLetter().equals("#K"))
            return letter_K;  //Letter K
         else if(tile.getLetter().equals("L") || tile.getLetter().equals("#L"))
            return letter_L;  //Letter L
         else if(tile.getLetter().equals("M") || tile.getLetter().equals("#M"))
            return letter_M;  //Letter M
         else if(tile.getLetter().equals("N") || tile.getLetter().equals("#N"))
            return letter_N;  //Letter N
         else if(tile.getLetter().equals("O") || tile.getLetter().equals("#O"))
            return letter_O;  //Letter O
         else if(tile.getLetter().equals("P") || tile.getLetter().equals("#P"))
            return letter_P;  //Letter P
         else if(tile.getLetter().equals("Q") || tile.getLetter().equals("#Q"))
            return letter_Q;  //Letter Q
         else if(tile.getLetter().equals("R") || tile.getLetter().equals("#R"))
            return letter_R;  //Letter R
         else if(tile.getLetter().equals("S") || tile.getLetter().equals("#S"))
            return letter_S;  //Letter S
         else if(tile.getLetter().equals("T") || tile.getLetter().equals("#T"))
            return letter_T;  //Letter T
         else if(tile.getLetter().equals("U") || tile.getLetter().equals("#U"))
            return letter_U;  //Letter U
         else if(tile.getLetter().equals("V") || tile.getLetter().equals("#V"))
            return letter_V;  //Letter V
         else if(tile.getLetter().equals("W") || tile.getLetter().equals("#W"))
            return letter_W;  //Letter W
         else if(tile.getLetter().equals("X") || tile.getLetter().equals("#X"))
            return letter_X;  //Letter X
         else if(tile.getLetter().equals("Y") || tile.getLetter().equals("#Y"))
            return letter_Y;  //Letter Y
         else if(tile.getLetter().equals("Z") || tile.getLetter().equals("#Z"))
            return letter_Z;  //Letter Z
         else if(tile.getLetter().equals("#@"))
            return blank;  //Letter Z
      }
      return blankSpace; //Return blank tile space if therre is no tile found
   }
   
   //Generate the bag will all the tiles that can be used
   public static ArrayList<Tile> fillBag() throws IOException {     
      ArrayList<Tile> bag = new ArrayList();               		//Create a bag that holds the tiles
      File file = new File("tile_bag.txt");                     //Tile bag file holds data for tiles in bag
      Scanner reader = new Scanner(new FileReader(file));		//Scanner is set to our file input
      while(reader.hasNext()) {									//While reader has next
         String[] a = reader.next().split(",");                 //Array holding letters and points
         bag.add(new Tile(a[0], Integer.parseInt(a[1])));       //Add the new tile to the bag
      } 
      reader.close();      //Close the file reader
      return bag;          //Return the filled bag
   }
   
   //Create our dictionary that contains all possible words
   public static ArrayList<String> createDictionary() throws IOException {
      ArrayList<String> dictionary = new ArrayList();	//Initalize the dictionary array list
      File file = new File("dictionary.txt");				//Set file equal to our "dictionary.txt" text file
      Scanner reader = new Scanner(new FileReader(file));	//Scanner is set to our file impit
      while(reader.hasNext())								//While reader has next
         dictionary.add(reader.next());			//Add words to dictionary
      reader.close();							//Close the file reader
      return dictionary;						//Return the completed dictionary
   }
   
   //Get the turn count to tell us who's turn it is
   public int getTurnCount() {
      return turn_count;	//Return turn count
   }
   
   //Increase the turn count
   public void incTurnCount() {
      turn_count++;	//Increase turn_count
   }
   
   //Compute the score after the player's turn
   public static int computeScore() {
      boolean double_word = false;	//If we have a double word
      boolean triple_word = false;	//If we have a triple word
      int total = 0;	//Total points found this turn
      int word_total = 0;	//Total points found from word
      int count = 0;	//Count to track location
      int length = 0; //Count to track the length of the word
      
      //Traverse through the board horizontally
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[0].length; c++) {
            if(board[r][c] != null) {	//If this location in the board is not null
               if(board[r][c].getRow() == -1 || board[r][c].getCol() == -1) {	//If the tile at this location is not set
                  while(c+count > 0 && board[r][c+count-1] != null) {	//While we look for the first tile in the line of them
                     count--;	//Deincrement count
                  }
                  while(c+count < 15 && board[r][c+count] != null) {	//While we have not reached the end of the board
                     if(isDoubleLetter(r,c+count) && board[r][c+count].getRow() == -1 &&  board[r][c+count].getCol() == -1)	{//If the location we are on is a double letter
                        word_total = word_total + (board[r][c+count].getPoints() * 2);	//Double the points and add to word_total
                     }else if(isTripleLetter(r,c+count) && board[r][c+count].getRow() == -1 &&  board[r][c+count].getCol() == -1)	{//If the location is a triple letter
                        word_total = word_total + (board[r][c+count].getPoints() * 3);	//Triple the points and add to word_total
                     }else if(isDoubleWord(r,c+count) && board[r+count][c].getRow() == -1 &&  board[r][c+count].getCol() == -1) {	//Else if we found a double word location
                        double_word = true;		//Set double word equal to true
                        word_total = word_total + board[r][c+count].getPoints(); 	//Add the letter to word_total
                     }else if(isTripleWord(r,c+count) && board[r][c+count].getRow() == -1 &&  board[r][c+count].getCol() == -1) {	//Else if we found a triple word location
                        triple_word = true;		//Set triple word equal to true
                        word_total = word_total + board[r][c+count].getPoints(); 	//Add the letter to word_total
                     }else {				
                        word_total = word_total + board[r][c+count].getPoints(); 	//Add the letter to word_total
                     }
                     count++;	//Increase our count value
                     length++; //Increase length value
                  }
                  if(double_word)	//If we found it to be on a double word spot
                     word_total = word_total * 2;	//Multiply the word score by 2
                  if(triple_word)	//If we found it to be on a triple word spot
                     word_total = word_total * 3;	//Multiply the word score by 3
                  c = c + count;	//Set the column equal to count
                  if(length != 1)	//If the word is not a single tile
                     total += word_total;	//Add the word to word_total
                  if((turn_count % 2 == 0 && player1.rackIsEmpty()) || (turn_count % 2 == 1 && player2.rackIsEmpty()))	//If the player used all their tiles
                     total += 50;	//They get 50 points added automatically
                  word_total = 0;	//Reset the word total
                  count = 0;		//Reset word count
                  length = 0;    //Reset word length
                  double_word = false;	//Double word is now false
                  triple_word = false;	//Triple word is now false
               }
            }
         }
      }
      //Traverse through the board vertically
      for(int c = 0; c < board.length; c++) {
         for(int r = 0; r < board[0].length; r++) {
            if(board[r][c] != null) {	//If this location in the board is not null
               if(board[r][c].getRow() == -1 || board[r][c].getCol() == -1) {	//If the tile at this location is not set
                  while(r+count > 0 && board[r+count-1][c] != null) {	//While we look for the first tile in the line of them
                     count--;	//De-increment count
                  }
                  while(r+count < 15 && board[r+count][c] != null) {	//While we have not reached the end of the board
                     if(isDoubleLetter(r+count,c) && board[r+count][c].getRow() == -1 &&  board[r+count][c].getCol() == -1)	{//If the location we are on is a double letter
                        word_total = word_total + (board[r+count][c].getPoints() * 2);	//Double the points and add to word_total
                     }else if(isTripleLetter(r+count,c) && board[r+count][c].getRow() == -1 &&  board[r+count][c].getCol() == -1)	{//If the location is a triple letter
                        word_total = word_total + (board[r+count][c].getPoints() * 3);	//Triple the points and add to word_total
                     }else if(isDoubleWord(r+count, c) && board[r+count][c].getRow() == -1 &&  board[r+count][c].getCol() == -1) {	//Else if we found a double word location
                        double_word = true;		//Set double word equal to true
                        word_total = word_total + board[r+count][c].getPoints(); 	//Add the letter to word_total
                     }else if(isTripleWord(r+count,c) && board[r+count][c].getRow() == -1 &&  board[r+count][c].getCol() == -1) {	//Else if we found a triple word location
                        triple_word = true;		//Set triple word equal to true
                        word_total = word_total + board[r+count][c].getPoints(); 	//Add the letter to word_total
                     }else {				
                        word_total = word_total + board[r+count][c].getPoints(); 	//Add the letter to word_total
                     }
                     count++;	//Increase our count value
                     length++; //Increase our length value
                  }
                  if(double_word)	//If we found it to be on a double word spot
                     word_total = word_total * 2;	//Multiply the word score by 2
                  if(triple_word)	//If we found it to be on a triple word spot
                     word_total = word_total * 3;	//Multiply the word score by 3
                  r = r + count;	//Set the row equal to count
                  if(length != 1)	//If the word is not a single tile
                     total += word_total;	//Add the word to word_total
                  if((turn_count % 2 == 0 && player1.rackIsEmpty()) || (turn_count % 2 == 1 && player2.rackIsEmpty()))	//If the player used all their tiles
                     total += 50;	//They get 50 points added automatically
                  word_total = 0;	//Reset the word total
                  count = 0;		//Reset word count
                  length = 0;    //Reset word length
                  double_word = false;	//Double word is now false
                  triple_word = false;	//Triple word is now false
               }
            }
         }
      }
      return total;	//Return the total points scored from that turn
   }
   /**
    * Check to see if it is a double letter spot
    */
   public static boolean isDoubleLetter(int row, int col) {
      if(row == 0 || row == 14) {	//Locations (0, 3) (0, 11) (14, 3) and (14, 11)
         if(col == 3 || col == 11)
            return true;
      } else if (row == 2 || row == 12) {	//Locations (2, 6) (2, 8) (12, 6) and (12, 8)
         if(col == 6 || col == 8)
            return true;
      } else if (row == 3 || row == 11) {	//Locations (3, 0) (3, 7) (3, 14) (11, 0) (11, 7) and (11, 14)
         if(col == 0 || col == 7 || col == 14)
            return true;
      } else if (row == 6 || row == 8) {	//Locations (6, 2) (6, 6) (6, 8) (6, 12) (8, 2) (8, 6) (8, 8) and (8, 12)
         if(col == 2 || col == 6 || col == 8 || col == 12)
            return true;
      } else if (row == 7) {	//Locations (7, 3) and (7, 11)
         if(col == 3 || col == 11)
            return true;
      }
      return false;	//Not a triple word spot
   }
   
   //Check to see if it is a double word spot
   public static boolean isDoubleWord(int row, int col) {
      if(row == 1 || row == 2 || row == 3 || row == 4 || row == 10 || row == 11 || row == 12 || row == 13) {
         if(col == board.length - 1 - row || col == row) //Diagonal location on the board
            return true;
      }
      return false;	//Not a double word spot
   }
   
   //Check to see if it is a triple letter spot
   public static boolean isTripleLetter(int row, int col) {
      if(row == 1 || row == 13) { //Locations (1, 5) (1, 9) (13, 5) and (13, 9)
         if(col == 5 || col == 9) 
            return true;
      } else if(row == 5 || row == 9) {	 //Locations (5, 1) (5, 5) (5, 9) (5, 13) (9, 1) (9, 5) (9, 9) and (9, 13)
         if(col == 1 || col == 5 || col == 9 || col == 13)
            return true;
      }
      return false;	//Not a triple letter spot
   }
   
   //Check to see if it is a triple word spot
   public static boolean isTripleWord(int row, int col) {
      if(row == 0 || row == 14) {  //Locations (0, 0) (0, 7) (0, 14) (14, 0) (14, 7) and (14, 14)
         if(col == 0 || col == 7 || col == 14)
            return true;
      } else if (row == 7) { //Locations (7, 0) and (7, 14)
         if(col == 0 || col == 14) 
            return true;
      }
      return false;	//Not a triple word spot
   }

   //Check to see if the word we submit is in our dictionary
   public String checkSubmitWord(ArrayList<String> dict) {
      //Traverse through the board horizontally
      boolean tile_found = false;	//We found a new tile
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[0].length; c++) {
            if(board[r][c] != null) {	//If tile is not null
               if(board[r][c].getRow() == -1 || board[r][c].getCol() == -1) //If row and col are -1
                  tile_found = true;	//We found a new tile
               if(singleTile(r, c)) {	//If it's a single tile
                  return "single";	//Return single error
               }
               else {
                  int count = 0;	//Create a count variable for moving across board
                  String result = "";	//Our resulting word
                  while(c+count < 15 && board[r][c+count] != null) {	//While we are not at the end of the board
                     result += board[r][c+count].toString().replace("#","");	//Add letter to word, remove any #
                     count++;	//Increase count
                     if(c+count == 14) {	//If count is 14, we have reached the end of the board
                        break;	//Break
                     }
                  }
                  if(count != 1 && !checkDictionary(dict, result))
                     return "dictionary";	//If result is not in dictionary, return dictionary error
                  c = c + count;	//Set col equal to count
               }
            }
         }
      } 
      //Traverse through the board vertically
      for(int c = 0; c < board[0].length; c++) {
         for(int r = 0; r < board.length; r++) {
            if(board[r][c] != null) {	//If tile is not null
               if(board[r][c].getRow() == -1 || board[r][c].getCol() == -1) //If row and col are -1
                  tile_found = true;	//We found a new tile
               if(singleTile(r, c)) {	//If it's a single tile
                  return "single";	//Return single error
               }
               else {
                  int count = 0;	//Create a count variable for moving across board
                  String result = "";	//Our resulting word
                  while(r+count < 15 && board[r+count][c] != null) {	//While we are not at the end of the board
                     result += board[r+count][c].toString().replace("#","");	//Add letter to word, remove any #
                     count++;	//Increase count
                     if(r+count == 14) {	//If count is 14, we have reached the end of the board 
                        break;	//Break
                     }
                  }    
                  if(count != 1 && !checkDictionary(dict, result)) 
                     return "dictionary";	//If result is not in dictionary, return dictionary error
                  r = r + count;	//Set row equal to count
               }
            }
         }
      } 
      if(tile_found == false) 
         return "none";	//We have not found a new tile, return none error
      if(board[7][7] == null) {
         return "star";	//There is no tile on the star, return a star error
      }
      return "legal";	//The move is legal, return legal
   }
   
   //Check to see if the tile has no surrounding tiles
   public static boolean singleTile(int row, int col) {
      if(row > 0) {  //Check above
         if(board[row-1][col] != null) {
            return false;	//There is a tile above it
         }
      }
      if(row < board.length - 1) {  //Check below
         if(board[row+1][col] != null) {
            return false;	//There is a tile below it
         }}
      if(col > 0) {  //Check to the left
         if(board[row][col-1] != null) {
            return false;	//There is a tile to the left of it
         }
      }
      if(col < board[0].length - 1) {  //Check to the right
         if(board[row][col+1] != null) {
            return false;	//There is a tile to the right of it
         }
      }
      return true;	//There is no tile around this tile
   }
   
   //Check to see if the dictionary contains the word found
   public static boolean checkDictionary(ArrayList<String> dictionary, String word) {
      for(int i = 0; i < dictionary.size(); i++) {
         if(word.equals(dictionary.get(i))) {
            return true;	//The dictionary contains the word
         }
      }
      return false;	//The dictionary does not contain the word
   }
   
   //Set the tiles that we just placed on the board
   public static void setBoardWords() {
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[0].length; c++) {
            Tile temp = board[r][c];
            if(temp != null) {	//If the location found is not null
               if(temp.getRow() == -1 || temp.getCol() == -1)	//If the row or col of the tile is -1, it is a new tile
                  board[r][c].setLocation(r, c);	//Set the location to the row and col
            }
         }
      }
   }
   
   //Reset the board and rack of the player
   public void reset(Player p) {
      resetRack(p);	//Reset the player's rack
      resetBoard();	//Reset the board
   }
   
   //Reset the rack of the player
   public static void resetRack(Player p) {
      Tile[] temp_rack = p.getRack();	//Create a temp_rack value
      for(int i = 0; i < temp_rack.length; i++) {
         if(temp_rack[i] == null) {	//If the value at this location is null
            temp_rack[i] = tiles_used.pop();	//Pop from the stack and put in the rack
            if(temp_rack[i].getLetter().contains("#")) {
               temp_rack[i] = new Tile("#@", 0);
            }
         }
      }
   }
   
   //Reset the board
   public static void resetBoard() {
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[0].length; c++) {
            if(board[r][c] != null) {	//If the tiel is not null
               if(board[r][c].getRow() == -1 && board[r][c].getCol() == -1) {	//If the row or col of the tile is -1, it is a new tile
                  board[r][c] = null;	//Set the location to null
               }
            }
         }
      }
   }
   
   //Clear the tiles used
   public void clearTilesUsed() {
      tiles_used.clear();	//Clear our stack of tiles used
   }
   
   //Check to see if the tile found is a blank tile
   public static boolean isBlankTile(int row, int col) {
      if(board[row][col] != null) {	//If the tile at this location is not null
         if(board[row][col].getLetter().contains("#")) {	//If it contains a # that means it is a blank tile
            if(board[row][col].getRow() == -1 && board[row][col].getCol() == -1) {	//If row or col of the tile is -1, the tile is not set
               return true;	//Return true so we can change the tile
            }
         }
      }
      return false;	//Return false
   }
   
   //Increment throught the alphabet allowing us to change a blank tile/
   public static String changeBlankTile(String name) {
      if(name.equals("#@"))
         return "#A";  //Letter A
      else if(name.equals("#A"))
         return "#B";  //Letter B
      else if(name.equals("#B"))
         return "#C";  //Letter C
      else if(name.equals("#C"))
         return "#D";  //Letter D
      else if(name.equals("#D"))
         return "#E";  //Letter E
      else if(name.equals("#E"))
         return "#F";  //Letter FB
      else if(name.equals("#F"))
         return "#G";  //Letter G
      else if(name.equals("#G"))
         return "#H";  //Letter H
      else if(name.equals("#H"))
         return "#I";  //Letter I
      else if(name.equals("#I"))
         return "#J";  //Letter J
      else if(name.equals("#J"))
         return "#K";  //Letter K
      else if(name.equals("#K"))
         return "#L";  //Letter L
      else if(name.equals("#L"))
         return "#M";  //Letter M
      else if(name.equals("#M"))
         return "#N";  //Letter N
      else if(name.equals("#N"))
         return "#O";  //Letter O
      else if(name.equals("#O"))
         return "#P";  //Letter P
      else if(name.equals("#P"))
         return "#Q";  //Letter Q
      else if(name.equals("#Q"))
         return "#R";  //Letter R
      else if(name.equals("#R"))
         return "#S";  //Letter S
      else if(name.equals("#S"))
         return "#T";  //Letter T
      else if(name.equals("#T"))
         return "#U";  //Letter U
      else if(name.equals("#U"))
         return "#V";  //Letter V
      else if(name.equals("#V"))
         return "#W";  //Letter W
      else if(name.equals("#W"))
         return "#X";  //Letter X
      else if(name.equals("#X"))
         return "#Y";  //Letter Y
      else if(name.equals("#Y"))
         return "#Z";  //Letter Z
      else if(name.equals("#Z"))
         return "#A";  //Letter Z
      else
         return "#@";  //Blank Tile    
   }
   
   //Exchange our tiles with new ones
   public static boolean exchangeTiles() {
      if(exchange_tiles.size() == 0) //If the # of tiles selected is zero
         return false;	//Return false
      else {	//Exchange tiles selected
         int size = exchange_tiles.size();	//Create a size value to hold the list size
         if(turn_count % 2 == 0) {	//If it is player 1's turn
            for(int i = 0; i < size; i++) {
               bag.add(player1.getTileIndex(exchange_tiles.get(i)));	//Add the tile back to the bag
               removeRack(player1, exchange_tiles.get(i));	//Remove the tile from the rack
               player1.setTile(exchange_tiles.get(i), bag.get((int)(Math.random() * bag.size())));   //Set rack value to piece we are removing
            }	
         } else {
            for(int i = 0; i < size; i++) {
               bag.add(player2.getTileIndex(exchange_tiles.get(i)));	//Add the tile back to the bag
               removeRack(player2, exchange_tiles.get(i));	//Remove the tile from the rack
               player2.setTile(exchange_tiles.get(i), bag.get((int)(Math.random() * bag.size())));   //Set rack value to piece we are removing
            }
         }
         exchange_tiles.clear();	//Clear the tiles in exchange_tiles
         return true;	//Return true
      }
   }
}