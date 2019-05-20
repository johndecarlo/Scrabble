/** 
 * John DeCarlo
 * Player.java
 */

import java.util.ArrayList;
import java.util.Random;

public class Player {
   
   private Tile[] rack = new Tile[7];  //Players rack that holds all the tiles
   private int totalPoints = 0;        //Total points the player has
   
   //Return the rack array and the tiles in it
   public Tile[] getRack() {
      return rack;	//Return the rack
   }
   
   //Get the tile at a specific index
   public Tile getTileIndex(int index) {
      return rack[index];	//Return tile at index
   }
   
   //Take out a tile of a certian letter
   public Tile getTile(String letter) {
      for(int i = 0; i < 7; i++) {
         if(rack[i] != null  && letter.equals(rack[i].getLetter())) {   //Tile is not null and equal to letter value
            Tile temp = rack[i]; //Set temp equal to the tile value
            rack[i] = null;   //Set the rack at index equal to null
            return temp;      //Return the temp value
         }                 
      }
      return null;      //Return null
   }
   
   //Set rack at position i to tile value
   public void setTile(int i, Tile tile) {
      this.rack[i] = tile;	//Set the tile at index to our input tile
   }
   
   //Set the players rack to the rack input
   public void setRack(Tile[] rack) {
      this.rack = rack;	//Set rack equals to rack input
   }
   
   //Return the total amount of points the player has
   public int getTotalPoints() {
      return totalPoints;	//Return the total points
   }
   
   //Set the total amount of points the player has
   public void setTotalPoints(int totalPoints) {
      this.totalPoints = totalPoints;	//Set total points
   }
   
   //Check to see if the rack contains a certain letter
   public boolean hasLetter(String letter) {
      for(int i = 0; i < 7; i++) { 
         if(rack[i] != null  && letter.equals(rack[i].getLetter())) {   //Rack value is not null and letter is equal to tile letter
            return true;   //Return true
         }
      }
      return false;  //Return false
   }
   
   //Check to see if we have no Tiles in our array
   public boolean rackIsEmpty() {
      for(int i = 0; i < 7; i++) {
         if(rack[i] != null)  //Rack at index is not equal to null
            return false;  //Return false
      }
      return true;   //Return true
   }
   
   //Generate a random rack for the player to start the game
   public Tile[] generateRack(ArrayList<Tile> bag) {
      for(int i = 0; i < 7; i++) {
         int rand = (int)(Math.random() * bag.size());   //Get a random value
         rack[i] = bag.remove(rand);   //Set rack value to piece we are removing
      }
      return rack;   //Return the newly created rack
   }
   
   //Refill the rack after we create a word or switch out pieces
   public Tile[] refillRack(ArrayList<Tile> bag) {
      for(int i = 0; i < 7; i++) {
         if(rack[i] == null) {   //If the rack value is equal to null
            int rand = (int)(Math.random() * bag.size());   //Get a random number
            rack[i] = bag.remove(rand);   //Set rack equal to the removed piece from the bag
         }
      }
      return rack;   //Return our updated rack
   }
}