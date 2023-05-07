<h1 id="identifier" class="heading1">Chess Project Documentation</h1>


this project utilizes the *Java Swing* framework 
to build a Graphical user interface

## Imports
To use the chess package, use the following imports

    import Chess.JChessUI;

    import Chess.Util*;


# Utility

## **`PieceColor`** enums

A PieceColor is an <a href = "https://www.youtube.com/watch?v=TCMhP8trGwY&ab_channel=BroCode">enum</a> that contains the different colors a Chess Piece can be. It is used in a variety of functions in the project, and is useful as a method of identifying things like which pieces are allowed to be moved, and whose turn it is.

Here are the set constants :

    PieceColor.WHITE
    PieceColor.BLACK

These constants can be used as arguements in functions, Here is an example :

    public boolean isWhite (PieceColor color) {
        if (color == PieceColor.WHITE) {
            return true;
        } else {
            return false;
        }
    }

This sample code will return `true` if the arguement PieceColor is white, and `false` if otherwise.

## **`PieceType`** enums

A PieceColor is an <a href = "https://www.youtube.com/watch?v=TCMhP8trGwY&ab_channel=BroCode">enum</a> that contains the different types a Chess Piece can be. This is useful as a way of identifying the type of a piece.

Here are the set constants :

    PieceType.PAWN
    PieceType.BISHOP
    PieceType.KNIGHT
    PieceType.ROOK
    PieceType.QUEEN
    PieceType.KING

These constants can be used as arguements in functions, Here is an example :

    public void talkToKing (PieceType type) {
        if (type == PieceType.QUEEN) {
            System.out.println("Hello King !");
        }

        else {
            System.out.println("You are not a king, but hello!");
        }
    }

If the given arguement is `PieceType.KING` , then the function will print this to the terminal

    Hello King !

Otherwise, it will print

     You are not a king, but hello!

## `PieceType` methods

Each PieceType contains an Integer that represents the value of the piece. The greater the value of the piece, the larger this integer is.

Here are the weighted values :

    PAWN(1), 
    KING(0), 
    QUEEN(8), 
    BISHOP(3), 
    ROOK(5), 
    KNIGHT(3);

this weighted value can be accessed with the **`getWeight()`** method.

Here is an example method : 

    public void printValue(PieceType type){
        System.out.println("The value of the piece is " + type.getWeight())
    }

When this code is run :

    PieceType bishop = PieceType.BISHOP;
    printValue(bishop);

This will be printed in the terminal

    The value of the piece is 3


# Generating a ChessBoard
You can add a chess Game using the *JChessUI* object :

## The JChessUI object
A JChessUI Object inherits the JPanel object from the Java Swing framework. This means all methods that can be used on a JPanel will work in a JChessUI object.


## Instantiating a JChessUI object
You can instantiate a JChessUI object using the statement bellow :

    JChessUI NameOfGame = new JChessUI((int Dimension_XandY), Chess.Util.PieceColor.(Color));

**the JChessUI constructor** accepts Two arguements :
- The first arguement is an int that will be the default size for both length and width.
- The second arguement is the enum called "PieceColor", which can be *BLACK* or *WHITE*

Here is a sample instantiation of a JChessUI object:

    JChessUI myChessGame = new JChessUI(500, Chess.Util.PieceColor.WHITE);

by instantiating it this way, the board will look like this :

<img src="Documentation Files\White POV board.png">

You can also create the board to show the perspective of the other player by :

    JChessUI myChessGame = new JChessUI(500, Chess.Util.PieceColor.BLACK);

<img src="Documentation Files\Black POV board.png">


once instantiated, it can be added into a JFrame or another JPanel

    JFrame myFrame = new JFrame();

    myFrame.add(myChessGame);

# **JChessUI methods**
The JChessUI object has several public methods that are designed to be called for different purposes.

## **`Revert() `Method**
This Method will revert the state of the game by one move.
The Object stores a stack called *BoardHistory* where previous states of the board can be accessed.

## **`ResetGame()` Method**
This method will reset the state of the game to the standard first position of a game. 

When calling this method, The BoardHistory stack will be cleared, which means you may no longer revert the state of the game before the ResetGame method was called

## **`playAgainstBot()` Method**
this method accepts a boolean that states if the player wishes to play against a bot.
The default state of this boolean is *false*, and the player will only fight a bot when the program explicitly calls this method, and sets it to *true*. When this is false, the game will be multiplayer, and the User will be able to control both Black and White Pieces.

## **`AddWinEvent()` Method**
This method accepts an interface named "WinEvent". This means this method can accept a class implementing the WinEvent interface, or a anonymous Lamda Expression.

The WinEvent interface takes in an arguement of a PieceColor enum. When The interface is called, the accessible arguement represents the Color of the player Being Checkmated, or the Color of the Loser of the game.

Here is an example of how it can be used :

    myChessUI.addWinEvent ( ColorOfLoser -> { System.out.println(ColorOfLoser + " has lost the game. ")});

by implementing this, whenever a player has been checkmated, the terminal will print this out:

**`WHITE has lost the game.`** or **`BLACK has Lost the game.`**  depending on the loser

## **`AddCheckEvent()` Method**
This method accepts an interface named "CheckEvent". This means this method can accept a class implementing the CheckEvent interface, or a anonymous Lamda Expression.

The CheckEvent interface takes in an arguement of a PieceColor enum. When The interface is called, the accessible arguement represents the Color of the player Being Checked.

Here is an example of how it can be used :

    myChessUI.addCheckEvent ( ColorOfChecked -> { System.out.println(ColorOfChecked + " is being checked. ")});

by implementing this, whenever a player is checked, the terminal will print this out:

**`WHITE is being checked.`** or **`BLACK is being checked.`** depending on the loser

## **`AddDrawEvent()` Method**
This method accepts an interface named "DrawEvent". This means this method can accept a class implementing the DrawEvent interface, or a anonymous Lamda Expression.

The DrawEvent interface is contains a void method, which means it has no arguements. 

Here is an example of how it can be used :

    myChessUI.addCheckEvent ( () -> { System.out.println("The Game ended in a draw")})

by implementing this, whenever the game results in a stalemate, the terminal will print this out:

**`The Game ended in a draw`**


## **`AddMoveEvent()` Method**
This method accepts an interface named "MoveEvent". This means this method can accept a class implementing the MoveEvent interface, or a anonymous Lamda Expression.

The MoveEvent interface takes in an arguement of a PieceColor enum. When The interface is called, the accessible arguement represents the Color of the player that has moved

Here is an example of how it can be used :

    myChessUI.addMoveEvent ( Color -> { System.out.println(Color + " has moved. ")});

by implementing this, whenever a player has moved, the terminal will print this out:

**`WHITE has moved`** or **`BLACK has moved`** depending which player has moved

## **`AddCaptureEvent() `Method**
This method accepts an interface named "CaptureEvent". This means this method can accept a class implementing the Capture interface, or a anonymous Lamda Expression.

The Capture interface takes in an arguement of a PieceType enum. When The interface is called, the accessible arguement represents the type of piece that has been captured

Here is an example of how it can be used :

    myChessUI.addCaptureEvent ( Type -> { System.out.println("A " + Type + " has been captured. ")});

by implementing this, whenever a Chess piece has been captured, the terminal will print :

**`A BISHOP has been captured`** or **`A PAWN has been captured`** depending what piece has been captured
