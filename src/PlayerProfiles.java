/* ONLY Rolando WILL EDIT THIS FILE!!! */


import Chess.Util.*;
import Chess.*;

import javax.swing.JPanel;

public class PlayerProfiles extends JPanel{

    final String PlayerName;
    final PieceColor colorOfPlayer;
    int Score = 0;

    PlayerProfiles(String PlayerName, PieceColor colorOfPlayer){
        this.PlayerName = PlayerName;
        this.colorOfPlayer = colorOfPlayer;


    }

    public void LoadElements(){
        //Write all of the instantiation of objects here, so the contents of the panel can be updated

    }


    public void updateScore(int newScore){
        Score = newScore;
        LoadElements();
    }
    

}
