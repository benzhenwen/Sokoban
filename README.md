# Sokoban
it's another clone the Japanese block pusher game (:

# controls
wasd/arrows - navigating menu / movement
enter - select level
esc - exit level / exit program
r - restart when in level

# known issues
this uses .getCanonicalPath() to naviate to needed assets. You can manually overide it in Globals.java

# adding levels
you can add levels in the levels file directory. levels start at index 0 and go up. levels 0 and 1 are already included

level formatting (no spaces):
load string is formatted as follows:
 ```
there are 2 major sections divided by a "#". The first major section represents the objects on the board
e - empty space
w - wall
b - pushable box
p - the player object. there should only be one of these.

the second major section on the opther side of the "#" represents the objectives for the player to complete
e - empty
b - target for a box to cover
p - target for the player to cover

each section is formatted identically using the characters left to right as a space, and a "/" to represent a new line.
 
for example, a 5x5 area surrounded by walls, a player at (1, 1), and a player objective in the center would look like:

"wwwww/wpeew/weeew/weeew/wwwww#eeeee/eeeee/eefee/eeeee/eeeee"
|----------section 1----------|----------section 2----------|

unidentified characters will be interpreted as an "e"
```

# level formatting can also be found in GameHandler.java
