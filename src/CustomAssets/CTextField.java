package CustomAssets;

import javax.swing.JTextField;

public class CTextField extends JTextField{

    CTextField(){
        super();
        this.setFont(Styling.defaultFont);
        this.setForeground(Styling.SecondaryColor);
        this.setBackground(Styling.DarkColors);
    }

    CTextField(String text){
        super(text);
        this.setFont(Styling.defaultFont);
        this.setForeground(Styling.SecondaryColor);
        this.setBackground(Styling.DarkColors);
    }
    
    
}
