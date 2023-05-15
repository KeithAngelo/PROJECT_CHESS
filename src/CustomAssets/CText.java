package CustomAssets;


import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.JLabel;

public class CText extends JLabel{
    public CText(){
        super();
        this.setFont(Styling.defaultFont);
        this.setForeground(Styling.SecondaryColor);
    }

    public CText(String text){
        super(text);
        this.setFont(Styling.defaultFont);
        this.setForeground(Styling.SecondaryColor);
    }

    public CText(String text, Font font){
        super(text);
        this.setFont(font);
        this.setForeground(Styling.SecondaryColor);
    }

}
