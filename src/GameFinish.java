/* ONLY Dave WILL EDIT THIS FILE!!! */

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Chess.Util.*;
import Chess.*;

public class GameFinish extends JFrame{
    GameFinish(PieceColor ColorOfWinner, EndStatus status){
       

         /* this is a place holder, add your own implementation  */

        if(status == EndStatus.CHECKMATE){
            JOptionPane.showMessageDialog(new JFrame(), "Congratulations, " + ColorOfWinner + " is the winner"); 
        }else if (status == EndStatus.DRAW){
            JOptionPane.showMessageDialog(new JFrame(), "The Game ended in a draw"); 
        } else if(status == EndStatus.WIN_BY_TIME){
            JOptionPane.showMessageDialog(new JFrame(), "Congratulations, " + ColorOfWinner + " won by Time Out"); 
        }

    }

    // Call this method when the player chooses to play again
    public void PlayAgain(){
        new GameStart();
        // Close the window after opening a new game window
        this.dispose();
    }

    public void ExitGame(){
        this.dispose();
    }
}
