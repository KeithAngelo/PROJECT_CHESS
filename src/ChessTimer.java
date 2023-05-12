/* ONLY Raymund WILL EDIT THIS FILE!!! */


import javax.swing.JPanel;

import CustomAssets.CText;

public class ChessTimer extends JPanel{
    int startTimeMilis;

    TimeOut timeOut;

    boolean isRunning = false;

    CText text;

    ChessTimer(int startingTime, TimeOut timeOut){
        startTimeMilis = startingTime;
        this.timeOut = timeOut;

        // this is a placeholder, add your own implementation
        text  = new CText(startTimeMilis/1000 + " : 00");
        this.add(text); 
    }

    public void LoadElements(){
        //Write all of the instantiation of objects here, so the contents of the panel can be updated
    }

    public void Start(){
        isRunning = true;

        text.setText("is running"); // this is a placeholder, add your own implementation
    }
        
    public void Pause(){
        isRunning = false;

        text.setText("paused"); // this is a placeholder, add your own implementation

    }

    public void Reset(){
        isRunning = false;

        text.setText(startTimeMilis/1000 + " : 00"); // this is a placeholder, add your own implementation

    }

    

    //When the timer hits zero, call this method
    private void TimeRunOut(){
        isRunning = false;
        timeOut.TimeOutEvent();
    }

    
}

@FunctionalInterface
interface TimeOut{
    public void TimeOutEvent();
}
