/**
 * John DeCarlo
 * Tile.Java
 */

public class Tile {

   private String letter = "";   //String letter
   private int points = 0;    //Number of points for the letter
   private int row = -1;      //Row the tile is on the board
   private int col = -1;      //Col the tile is on the board
   
   //Tile constructor
   public Tile(String letter, int points) {
      this.letter = letter;   //Initalize the letter
      this.points = points;   //Initalize the points
   }
   
   //Return the letter of the tile
   public String getLetter() {
      return letter;	//Return the letter value
   }
   
   //Set the letter value of the tile
   public void setLetter(String letter) {
      this.letter = letter;	//The letter value
   }
   
   //Get the number of points that the tile is worth
   public int getPoints() {
      return points;	//Return the total points
   }
   
   //Set the points value of the tile
   public void setPoints(int points) {
      this.points = points;	//Set the total points
   }
   
   //Get the row that the tile is located
   public int getRow() {
      return row;	//Return the row
   } 
   
   //Get the col that the tile is located
   public int getCol() {
      return col;	//Return the col
   }	
   
   //Set the location that the tile is located
   public void setLocation(int row, int col) {
      this.row = row;	//Set the row
      this.col = col;	//Set the col
   }
   
   //Print out the letter value
   public String toString() {
      return letter;	//Return letter
   }
}