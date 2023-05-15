/* ONLY Dave WILL EDIT THIS FILE!!! */

import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Chess.Util.*;
import CustomAssets.CText;


public class GameFinish extends JFrame {
    GameFinish(PieceColor ColorOfWinner, EndStatus status) {

        /* this is a place holder, add your own implementation */

        if (status == EndStatus.CHECKMATE) {
            ImageIcon winnerIcon = new ImageIcon("Media\\WINNER.gif");
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel(winnerIcon));
            CText Announcement = new CText("Congratulations, " + ColorOfWinner + " is the winner!");
            Announcement.setForeground(Color.BLACK);
            panel.add(Announcement);
            
            int option = JOptionPane.showOptionDialog(null, panel, "Game Over", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Play Again", "Exit Game" }, "default");
            if (option == JOptionPane.YES_OPTION) {
                PlayAgain();
            } else {
                ExitGame();
            }
        } else if (status == EndStatus.DRAW) {
            ImageIcon drawIcon = new ImageIcon("Media\\DRAW.gif");
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel(drawIcon));
            CText Announcement = new CText("The game ended in a draw!");
            Announcement.setForeground(Color.BLACK);
            panel.add(Announcement);
           
            int option = JOptionPane.showOptionDialog(null, panel, "Game Over", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Play Again", "Exit Game" }, "default");
            if (option == JOptionPane.YES_OPTION) {
                PlayAgain();
            } else {
                ExitGame();
            }
        } else if (status == EndStatus.WIN_BY_TIME) {
            ImageIcon timeOutIcon = new ImageIcon("Media\\WINNER.gif");
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel(timeOutIcon));
            CText Announcement = new CText("Congratulations, " + ColorOfWinner + " won by Time Out!");
            Announcement.setForeground(Color.BLACK);
            panel.add(Announcement);
            
            int option = JOptionPane.showOptionDialog(null, panel, "Game Over", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Play Again", "Exit Game" }, "default");
            if (option == JOptionPane.YES_OPTION) {
                PlayAgain();
            } else {
                ExitGame();
            }
        }

    }

    // Call this method when the player chooses to play again
    public void PlayAgain() {
        // Create a new instance of GameStart
        GameStart newGame = new GameStart();
        // Close the current window
        this.dispose();
    }

    public void ExitGame() {
        this.dispose();
    }
}