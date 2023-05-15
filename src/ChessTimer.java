import javax.swing.JPanel;
import javax.swing.Timer;

import CustomAssets.CText;

public class ChessTimer extends JPanel {
    int startTimeMilis;
    TimeOut timeOut;
    boolean isRunning = false;
    CText text;
    Timer timer;

    ChessTimer(int startingTime, TimeOut timeOut) {
        startTimeMilis = startingTime;
        this.timeOut = timeOut;

          // Display the starting time in minutes and seconds
          int minutes = startingTime / 60000;
          int seconds = (startingTime % 60000) / 1000;
          text = new CText(String.format("%s%d:%s%d",getLeadingZero(minutes), minutes, getLeadingZero(seconds),seconds));
          this.add(text);

        // Create the timer
        isRunning = true;
        // Create the timer
        timer = new Timer(1000, e -> {
            if (isRunning) {
                startTimeMilis= startTimeMilis-1000;

                
                
                int minutes1 = startTimeMilis / 60000;
                int seconds1 = (startTimeMilis % 60000) / 1000;


                text.setText(String.format("%s%d:%s%d",getLeadingZero(minutes1), minutes1, getLeadingZero(seconds1),seconds1));
                
                if (startTimeMilis <= 0) {
                    TimeRunOut();
                    startTimeMilis = 0;
                    timer.stop();
                }

                timer.start();
            }
        });
       
    }

    private String getLeadingZero(int number){
        if(number<10){
            return "0";
        }
        else{
            return "";
        }
    }
    

    public void LoadElements() {
        // Write all of the instantiation of objects here, so the contents of the panel can be updated
    }

    public void Start() {
        isRunning = true;
        timer.start();
    }

    public void Pause() {
        isRunning = true;
        timer.stop();
        int minutes1 = startTimeMilis / 60000;
        int seconds1 = (startTimeMilis % 60000) / 1000;
        text.setText(String.format("%s%d:%s%d",getLeadingZero(minutes1), minutes1, getLeadingZero(seconds1),seconds1));
    }

    public void Reset() {
        isRunning = false;
        timer.stop();

        // Display the starting time in minutes and seconds
        int minutes1 = startTimeMilis / 60000;
        int seconds1 = (startTimeMilis % 60000) / 1000;
        text.setText(String.format("%s%d:%s%d",getLeadingZero(minutes1), minutes1, getLeadingZero(seconds1),seconds1));
    }

    // When the timer hits zero, call this method
    private void TimeRunOut() {
        isRunning = false;
        timeOut.TimeOutEvent();
    }
}

@FunctionalInterface
interface TimeOut {
    public void TimeOutEvent();
}