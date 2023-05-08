# Development Guide

## Gameplay loop

<img src="Documentation Files\gameplay loop.png" alt="">

# `GameProper` first look
The gameplay proper will look something like this

<img src="Documentation Files\plan.png" alt="">


# Stylings

rather than using components such as JLabel, JButton, or JTextField, use our custom components that extend java swing components

## `CustomAssets` package
the CustomAssets package contain everything we need to build the UI.

### **DO NOT USE ANY OTHER COMPONENTS OTHER THAN THE PRESET COMPONENTS IN THIS PACKAGE**

this is to ensure that the whole UI will have consistent styling and color themes

## `CText`

instead of using the `JLabel` object, use `CText` instead. this extends the JLabel class but has preset fonts and colors

    CText customtext = new CText("this is a custom label);

## `CButton`

instead of using the `JButton` object, use `CButton` instead. this extends the JLabel class but has preset fonts and colors

    CButton customButton = new CButton("this is a custom button);


## `CTextField`

instead of using the `JTextFields` object, use `CTextField` instead. this extends the JLabel class but has preset fonts and colors

    CTextField customTextField = new CTextField();


## Custom colors

the `Styling` class contains all of the custom colors you might need. use these instead of picking your own colors

     Color PrimaryColor = new Color(0xf3c311);
     Color SecondaryColor = new Color(0xFFFFFF);
     Color NeutralColor = new Color(0x222222);
     Color DarkColors = new Color(0x323437);
     Color CheckIndicator = new Color(0xAA2222);

for example, when setting the color of a new JPanel, you can do this

    JPanel myPanel = new JPanel();
    myPanel.setBackground(Styling.DarkColors);