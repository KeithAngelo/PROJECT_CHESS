/* ONLY Keith WILL EDIT THIS FILE!!! */


import javax.swing.JFrame;

import Chess.Util.*;
import Chess.*;

public class GameProper extends JFrame{
    //There are two constructors (Method Overloading), one for singleplayer, one for two players
    //Do the appropriate actions for each scenario

    JChessUI ChessUI;
    int ChessUI_Dimension = 500;

    GameProper(String Player_Name, PieceColor ChosenColor){
        ChessUI = new JChessUI(ChessUI_Dimension, ChosenColor);
        ChessUI.playAgainstBot(true);


    }

    GameProper(String Player1_Name, String Player2_Name, PieceColor Player1_Color){

        ChessUI = new JChessUI(ChessUI_Dimension, PieceColor.WHITE);
        ChessUI.playAgainstBot(false);

        
    }




}
