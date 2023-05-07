/* ONLY Junier WILL EDIT THIS FILE!!! */


import javax.swing.JFrame;

public class GameStart{
    GameStart(){
        new PickNumberOfPlayers();
    }
}

class PickNumberOfPlayers extends JFrame{
    PickNumberOfPlayers(){

    }

    // Once the Player has chosen the number of players, instantiate the "InputNames" object
}

class InputNames extends JFrame{
    InputNames(int NumberOfPlayers){
        //There can only be two players
        if(NumberOfPlayers > 2 || NumberOfPlayers < 1){
            throw new IllegalArgumentException();
        }


        // Once the Player has chosen the name(s), instantiate the "PickAColor" object

    }
}

class PickAColor extends JFrame{
    //There are two constructors (Method Overloading), one for singleplayer, one for two players
    //Do the appropriate actions for each scenario

    PickAColor(String playerName){

    }

    PickAColor(String playerName_1, String playerName_2){

    }


    /* Once all necessary parameters are chosen (Number Of players, names of colors, and chosen colors) :
     * 
     *  Instaniate a "GameProper" Object
     *  if single player, the "GameProper" object will only accept two parameters :
     * 
     *  String Player_Name, PieceColor ChosenColor
     * 
     *  if multiplayer, it will accept three parameters : 
     *
     *  String Player1_Name, String Player2_Name, PieceColor Player1_Color
     *
     * 
     * 
     *  No need to input the chosen color of player 2 since it would be the opposite color of Player 1
     * 
     */

}