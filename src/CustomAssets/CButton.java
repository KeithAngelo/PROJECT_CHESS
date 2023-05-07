package CustomAssets;

import javax.swing.JButton;

public class CButton extends JButton {
    public CButton(){
        super();
        this.setFont(Styling.defaultFont);
        this.setForeground(Styling.SecondaryColor);
    }

    public CButton(String text){
        super(text);
        this.setFont(Styling.defaultFont);
        this.setForeground(Styling.SecondaryColor);
    }
}
