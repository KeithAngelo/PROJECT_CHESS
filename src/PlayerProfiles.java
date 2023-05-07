/* ONLY Rolando WILL EDIT THIS FILE!!! */


import Chess.Util.*;
import Chess.*;

import CustomAssets.*;

import javax.swing.JPanel;

public class PlayerProfiles extends JPanel{

    final String PlayerName;
    final PieceColor colorOfPlayer;
    public int Score = 0;

    PlayerProfiles(String PlayerName, PieceColor colorOfPlayer){
        this.PlayerName = PlayerName;
        this.colorOfPlayer = colorOfPlayer;

        this.add(new CText(PlayerName)); // this is a Placeholder, add your own implementation
    }

    public void LoadElements(){
        //Write all of the instantiation of objects here, so the contents of the panel can be updated

    }


    public void updateScore(int newScore){
        Score = newScore;
        LoadElements();
    }
    

}
