package CustomAssets;

import java.awt.Color;
import java.awt.Font;

public class Styling {
    public static String defaultfontName = "Segoe UI";
    public static int defaultFontSize = 20;
    public static int defaultFontWeight = 1;

    public static int boldFontWeight = 3;
    public static int boldFontSize = 35;

    public static Font defaultFont = new Font(defaultfontName, Font.PLAIN, defaultFontSize);
    public static Font BoldFont = new Font(defaultfontName, Font.BOLD, boldFontSize);

    public static Color PrimaryColor = new Color(0xf3c311);
    public static Color SecondaryColor = new Color(0xFFFFFF);
    public static Color NeutralColor = new Color(0x222222);
    public static Color DarkColors = new Color(0x323437);
    public static Color CheckIndicator = new Color(0xAA2222);
}
