/* ONLY Junier WILL EDIT THIS FILE!!! */


import javax.swing.*;
import javax.swing.event.SwingPropertyChangeSupport;

import java.awt.*;
import java.awt.event.*;

import Chess.Util.*;
import Chess.*;
import Chess.Piece.Pawn;

public class GameStart{
    GameStart(){
        new ChessLoginGUI();
    }
}

class ChessLoginGUI extends JFrame {
        private JLabel player1Label, player2Label;
        private JTextField player1Field, player2Field;
        private JComboBox<String> player1ColorBox, player2ColorBox;
        private JRadioButton singlePlayerButton, multiplayerButton;
        private ButtonGroup gameModeGroup;
        private JButton startButton;

        public ChessLoginGUI() {

            setTitle("Chess Login");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the player 1 label and field
            player1Label = new JLabel("Player 1:");
            player1Label .setForeground(Color.WHITE);
            player1Label.setFont(new Font("STENCIL",Font.BOLD,20));
            player1Field = new JTextField(20);

            // Create the player 1 color label and combo box
            JLabel player1ColorLabel = new JLabel("Choose your color:");
            player1ColorLabel .setForeground(Color.WHITE);
            player1ColorLabel.setFont(new Font("STENCIL",Font.BOLD,20));
            String[] player1Colors = {"Black", "White"};
            player1ColorBox = new JComboBox<>(player1Colors);

            // Create the player 2 label and field
            player2Label = new JLabel("Player 2:");
            player2Label .setForeground(Color.WHITE);
            player2Label.setFont(new Font("STENCIL",Font.BOLD,20));
            player2Field = new JTextField(20);

            // Create the player 2 color label and combo box
            JLabel player2ColorLabel = new JLabel("Choose your color:");
            player2ColorLabel .setForeground(Color.WHITE);
            player2ColorLabel.setFont(new Font("STENCIL",Font.BOLD,20));
            String[] player2Colors = {"Black", "White"};
            player2ColorBox = new JComboBox<>(player2Colors);

            // Create the game mode selection buttons
            singlePlayerButton = new JRadioButton("Single Player", true);
            multiplayerButton = new JRadioButton("Multiplayer");
            gameModeGroup = new ButtonGroup();
            gameModeGroup.add(singlePlayerButton);
            gameModeGroup.add(multiplayerButton);

            // Create the start button
            startButton = new JButton("Start Game");
            startButton.addActionListener(new StartListener());
            startButton.setBackground(new Color(59, 89, 152));
            startButton.setForeground(Color.WHITE);
            startButton.setFocusPainted(false);

            // Create the login panel
            JPanel loginPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            loginPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
            loginPanel.setOpaque(false);
            loginPanel.add(player1Label);
            loginPanel.add(player1Field);
            loginPanel.add(player1ColorLabel);
            loginPanel.add(player1ColorBox);
            loginPanel.add(player2Label);
            loginPanel.add(player2Field);
            loginPanel.add(player2ColorLabel);
            loginPanel.add(player2ColorBox);
            loginPanel.add(singlePlayerButton);
            loginPanel.add(multiplayerButton);

            // Create the button panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.add(startButton);

            // Create the main panel
            ImageIcon backgroundIcon = new ImageIcon("Media\\START.png");
            JLabel backgroundLabel = new JLabel(backgroundIcon);
            JPanel mainPanel = new JPanel(new BorderLayout());

            mainPanel.setBackground(new Color(0x7EA650)); // Set background color to dark green

            mainPanel.add(backgroundLabel, BorderLayout.CENTER);
        
            mainPanel.add(backgroundLabel, BorderLayout.CENTER);
            mainPanel.add(loginPanel, BorderLayout.NORTH);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(mainPanel);

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
            this.setIconImage(new Pawn(PieceColor.WHITE).getImg().getImage());
        }

    private class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String player1 = player1Field.getText();
            String player2 = player2Field.getText();
            String player1Color = (String) player1ColorBox.getSelectedItem();
            String player2Color = (String) player2ColorBox.getSelectedItem();

            boolean isSinglePlayer = singlePlayerButton.isSelected();
            
            PieceColor player1PieceColor = getPieeColor(player1Color);
            PieceColor player2PieceColor = getPieeColor(player2Color);

            if(isSinglePlayer){
                if(player1.isBlank()){
                    JOptionPane.showMessageDialog(new JFrame(), "Player 1 Name must not be empty");
                    return;
                }

                new GameProper(player1, player1PieceColor);
                KillMe();
                return;
            }else{
                if(player1PieceColor == player2PieceColor){
                    JOptionPane.showMessageDialog(new JFrame(), "Player 1 Color must be different from Player 2 Color");
                    return;
                }

                if(player1.isBlank() || player2.isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(), "Player Names must not be empty");
                    return;
                }

                new GameProper(player1, player2, player1PieceColor);
                KillMe();
                return;
            }
            
            
        }

        private PieceColor getPieeColor(String text){
            if(text.equals("White")){
                return PieceColor.WHITE;
            }else if(text.equals("Black")){
                return PieceColor.BLACK;
            }else{
                throw new IllegalAccessError();
            }
        }
    }

    private void KillMe(){
        this.dispose();
    }
}
