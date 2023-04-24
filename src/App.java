import java.awt.BorderLayout;
import java.awt.*;

import javax.swing.*;

import Chess.JChessUI;
public class App {
    public static void main(String[] args) throws Exception {
        new myFrame();
    }
}

class myFrame extends JFrame{
    myFrame(){
        int Frame_Width = 500;
        int Frame_Height = 580;
        
        this.setResizable(false); 
        this.setSize(Frame_Width,Frame_Height); 
        this.setTitle("Game");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JChessUI MyChessGame = new JChessUI(500,Chess.Util.PieceColor.WHITE);
        MyChessGame.addCheckEvent(e -> {
            JOptionPane.showMessageDialog(new JFrame(), "Check : " + e);
        });
        this.add(MyChessGame,BorderLayout.CENTER);

        JButton GoBack = new JButton("Undo");
        GoBack.addActionListener(e -> MyChessGame.Revert());
        GoBack.setPreferredSize(new Dimension(40,40));

        JButton Reset = new JButton("RESET");
        Reset.addActionListener(e -> MyChessGame.ResetGame());
        Reset.setPreferredSize(new Dimension(40,40));

        this.add(Reset,BorderLayout.NORTH);
        this.add(GoBack, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
