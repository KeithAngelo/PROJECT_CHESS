/* ONLY Rolando WILL EDIT THIS FILE!!! */


import Chess.Util.*;
import Chess.*;

import CustomAssets.*;

import javax.swing.JPanel;

public class PlayerProfiles extends JPanel{

    final String PlayerName;
    final PieceColor colorOfPlayer;
    public int Score = 0;

    CText TextScore = new CText("0");

    PlayerProfiles(String PlayerName, PieceColor colorOfPlayer){
        this.PlayerName = PlayerName;
        this.colorOfPlayer = colorOfPlayer;

        this.add(new CText(PlayerName)); // this is a Placeholder, add your own implementation
        this.add(TextScore);
    }


    public void updateScore(int newScore){
        Score = newScore;
        TextScore.setText(String.valueOf(newScore));
    }
    

}
