# cc3002-breakout
The objective of this project is to implement the game Breakout, both the logic of the game and the GUI.

(N.B. the dependency diagrams for the current state are in svg and png formats as
"BreakoutT2.png" and "dependencyDiagramMethods.svg")
## Game Logic
This first part of the project is about the logic behind the game and it is implemented using primarily the Façade 
Design Pattern, combined with Observer and Visitor patterns.
### The Façade
The façade is used to hide te complexity to the client, all its methods are implemented in one line as calls 
to other classes.

[Link to Façade](src/main/java/facade)
### The Controller
The class Game is the controller and its responsibility is to keep track of the current level, the amount of points 
gathered throughout the game and the amount of lives the player has left. It's also in charge of passing to the next 
level, determining if the game is over and if the player has won the game.

[Link to Controller](src/main/java/controller)
### The Levels
The levels are in charge of creating its bricks, saving its current score based on the destruction of its bricks and 
notifying the game controller when a brick is destroyed and when the level has been completed. Each playable level knows
what its next level is.

[Link to Levels](src/main/java/logic/level)
### The Bricks
The bricks are structures with a certain amount of hit points and an effect they cause when destroyed, whenever a brick
is destroyed it notifies its level.

There are 3 types of bricks in the game:
#### Glass Bricks
Glass bricks are destroyed in one hit and give 50 points.
#### Wooden Bricks
Wooden Bricks are destroyed in 3 hits and give 200 points.
#### Metal Bricks
Metal Bricks are destroyed in 10 hits and give an extra ball to the player.

[Link to Bricks](src/main/java/logic/brick)
### The Visitors
The visitors are classes with a designated action to be performed depending in the object they visit.

This implementation uses 3 visitors:
#### Gets Destroyed
This visitor is created when a brick gets destroyed and it looks up the score assigned to the type of brick just 
destroyed.
#### Is Playable
This visitor checks whether or not the current level is a playable level.
#### Is Winner
This visitor checks whether the game has been won.

[Link to Visitors](src/main/java/logic/visitor)
## How to use the Façade
In order to use this the program in its current state one must create a new instance of the 
[HomeworkTwoFacade](src/main/java/facade), this will generate a game that begins with 3 balls, and a non playable level.

To add a playable level one must create a level using the "newLevelwithBricksFull" or "newLevelWithBrickNoMetal" methods.
The first receives the name of the level, the number of non metal bricks, the probability of a non metal brick to be a 
glass brick, the probability of a metal brick to exist and the seed for the random generation of the bricks. The second 
method receives all this parameters except for the probability of metal bricks.

Once a new level is created it can be add to the queue of the game using the method "addPlayingLevel" or set it as the 
current level using the method "setCurrentLevel", both of which receive the level to be added.

Additionally there is a number of action that can be performed such as:
##### numberOfBricks()
Returns the number of bricks currently in game i.e. the ones that haven been destroyed.
##### getBricks()
Returns the list of bricks of the current level.
##### hasNextLevel()
Checks whether or not the next level in queue is playable.
##### getNextLevel()
Returns the next level in queue.
##### hasCurrentLevel()
Checks whether or the current level is playable.
##### getLevelName()
Returns the name of the current level.
##### getCurrentLevel()
Returns the current level.
##### getLevelPoints()
Returns the total amount of points possible in the current level.
##### getBallsLeft()
Returns the amount of balls left in game.
##### dropBall()
Reduces the amount of balls in 1 and returns the new amount of balls.
##### isGameOver()
Check whether or not the player has balls left.
##### winner()
Checks whether or not the player has completed the final level.