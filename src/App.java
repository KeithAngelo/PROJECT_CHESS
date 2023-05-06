import java.awt.BorderLayout;
import java.awt.*;

import javax.swing.*;

import Chess.JChessUI;
import Chess.Util.PieceColor;
public class App {
    public static void main(String[] args) throws Exception {
        new myFrame();
    }
}

class myFrame extends JFrame{
    myFrame(){
        // Frame Settings
        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 580;

        final int WIDTH = FRAME_WIDTH / 40;
        final int HEIGHT = FRAME_HEIGHT / 40;
        
        this.setResizable(false); 
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT); 
        this.setTitle("Game");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Create Chess Board
        JChessUI MyChessGame = new JChessUI(500,Chess.Util.PieceColor.WHITE);
        MyChessGame.playAgainstBot(true);
        MyChessGame.addCheckEvent(e -> {

            JOptionPane.showMessageDialog(new JFrame(), "Check : " + e);
        });
        this.add(MyChessGame,BorderLayout.CENTER);

        MyChessGame.addWinEvent(e -> {
            
 
            JOptionPane.showMessageDialog(new JFrame(), "CheckMate : " + e);
        
        });

        MyChessGame.addCaptureEvent(e -> System.out.println("Captured piece is "+e));

        MyChessGame.addMoveEvent(e -> System.out.println(e + " has moved"));

        MyChessGame.addDrawEvent(() -> JOptionPane.showMessageDialog(new JFrame(), "Draw" ));

        // Create Undo Button at the bottom
        JButton GoBack = new JButton("Undo");
        GoBack.addActionListener(e -> MyChessGame.Revert());
        GoBack.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        // Create Reset Button at the top
        JButton Reset = new JButton("RESET");
        Reset.addActionListener(e -> MyChessGame.ResetGame());
        Reset.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        
        this.add(Reset,BorderLayout.NORTH);
        this.add(GoBack, BorderLayout.SOUTH);
        this.setVisible(true); //This Change
    }
}
