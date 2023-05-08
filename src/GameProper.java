/* ONLY Keith WILL EDIT THIS FILE!!! */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Chess.Util.*;
import CustomAssets.Styling;
import Chess.*;

public class GameProper extends JFrame{
    //There are two constructors (Method Overloading), one for singleplayer, one for two players
    //Do the appropriate actions for each scenario

    Color PanelColor = Styling.DarkColors;
    JChessUI ChessUI;
    final int ChessUI_Dimension = 500;

    final int FRAME_WIDTH = ChessUI_Dimension + (ChessUI_Dimension / 30);
    final int FRAME_HEIGHT = ChessUI_Dimension + (2 * ( ChessUI_Dimension / 8 ) );

   

    PieceColor CurrentTurn = PieceColor.WHITE;

    int StartTimeMilis = 10000;

    boolean isSinglePlayer;

    
    class Player{
        String Name;
        PieceColor Color;
        ChessTimer Timer;
        PlayerProfiles profiles;

        Player(String Name, PieceColor color){
            this.Name = Name;
            this.Color = color;
            Timer = new ChessTimer(StartTimeMilis, () -> new GameFinish(PieceColor.getOther(Color), EndStatus.WIN_BY_TIME));
            profiles = new PlayerProfiles(Name, Color);
        }
    }

    Player player_1;
    Player player_2;

    PlayerPanel TopPanel; 
    PlayerPanel BottomPanel;

    String defaultBotName = "ChessBot";

    boolean GameHasStarted = false;
    boolean isCheck = false;

    private Border defaultChessUIBorder = BorderFactory.createLineBorder(PanelColor,5);
    private Border checkChessUIBorder = BorderFactory.createLineBorder(Styling.CheckIndicator,5);


    private void nextTurn(){
        ChessUI.setBorder(defaultChessUIBorder);
        getPlayer(CurrentTurn).Timer.Pause();
        getPlayer(PieceColor.getOther(CurrentTurn)).Timer.Start();
        CurrentTurn = PieceColor.getOther(CurrentTurn);

        if(isCheck){
            // ChessUI.setBorder(checkChessUIBorder);
            isCheck = false;
        }


    }

    private Player getPlayer(PieceColor color){
        if(player_1.Color == color){
            return player_1;
        }else{
            return player_2;
        }
    }



    GameProper(String Player_Name, PieceColor ChosenColor){

        ChessUI = new JChessUI(ChessUI_Dimension, ChosenColor);
        ChessUI.playAgainstBot(true);
        isSinglePlayer = true;

        player_1 = new Player(Player_Name, ChosenColor);
        player_2 = new Player(defaultBotName, PieceColor.getOther(ChosenColor));

        TopPanel = new PlayerPanel(player_1);
        BottomPanel  = new PlayerPanel(player_2);

        construct();
    }

    GameProper(String Player1_Name, String Player2_Name, PieceColor Player1_Color){

        ChessUI = new JChessUI(ChessUI_Dimension, PieceColor.WHITE);
        ChessUI.playAgainstBot(false);

        player_1 = new Player(Player1_Name, Player1_Color);
        player_2 = new Player(Player2_Name, PieceColor.getOther(Player1_Color));

        TopPanel = new PlayerPanel(player_1);
        BottomPanel  = new PlayerPanel(player_2);

        construct();
    }

    

    private void construct(){
        this.setResizable(false); 
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT + (ChessUI_Dimension/10)); 
        this.setTitle("Chess");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

            this.add(ChessUI, BorderLayout.CENTER);
            this.add(TopPanel, BorderLayout.NORTH);
            this.add(BottomPanel, BorderLayout.SOUTH);
            this.add(new SidePanel(), BorderLayout.WEST);
            this.add(new SidePanel(), BorderLayout.EAST);

        ChessUI.addMoveEvent( color -> nextTurn() );
        ChessUI.addWinEvent(color -> new GameFinish(PieceColor.getOther(color), EndStatus.CHECKMATE));
        ChessUI.addDrawEvent( ()-> new GameFinish(PieceColor.getOther(CurrentTurn), EndStatus.DRAW));
        ChessUI.addCheckEvent(color -> isCheck = true);
        ChessUI.setBorder(defaultChessUIBorder);


        this.setVisible(true);
    }


    class PlayerPanel extends JPanel{
        PlayerPanel(Player player){
            this.setBackground(PanelColor);
            this.setPreferredSize(new Dimension(FRAME_WIDTH, (FRAME_HEIGHT - ChessUI_Dimension) / 2 ));

            this.setLayout(new BorderLayout());
            player.Timer.setPreferredSize(new Dimension(FRAME_WIDTH/3, (FRAME_HEIGHT - ChessUI_Dimension) / 2 ));
            player.Timer.setBackground(PanelColor);
            player.profiles.setPreferredSize(new Dimension(FRAME_WIDTH/3, (FRAME_HEIGHT - ChessUI_Dimension) / 2 ));
            player.profiles.setBackground(PanelColor);
            this.add(player.Timer, BorderLayout.EAST);
            this.add(player.profiles, BorderLayout.WEST);
        }
    }

    class SidePanel extends JPanel{
        SidePanel(){
            this.setBackground(PanelColor);
            this.setPreferredSize(new Dimension((FRAME_WIDTH - ChessUI_Dimension) / 2, FRAME_HEIGHT));
        }
    }


}

