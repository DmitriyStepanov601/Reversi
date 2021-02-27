# Reversi

This application is an interpretation of the famous game Reversi (Othello), the essence of which is to capture as many cells of the field as possible, thereby ensuring victory.
The game uses a square board with a size of 8 by 8 cells (all cells can be of the same color) and 64 special chips, painted from different sides in contrasting colors, for example, white and black. The cells of the board are numbered from the upper-left corner: vertically-in Latin letters, horizontally-in numbers.
In this game, the AI plays for white, and the user plays for black. When making a move, the player puts a chip on the square of the board with "his" color up.

At the beginning of the game, 4 chips are placed in the center of the board: black on d5 and e4, white on d4 and e5.
1) Black makes the first move. Next, the players take turns.
2) Making a move, the player must place your chip on one of the squares so that between that delivered by chip and one already on the Board chip of his color was a continuous row of opponent's pieces, horizontal, vertical, or diagonal (in other words, to a continuous row of opponent's pieces have been closed by the player's pieces on both sides). All the opponent's chips that are included in the" closed " row on this turn are turned over to the other side (change color) and go to the player who walked.
3) If, as a result of one move, more than one row of the opponent's chips is "closed" at the same time, then all the chips that are on all the "closed" rows are turned over.
4) The player has the right to choose any of the possible moves for him. If a player has possible moves, he cannot refuse a move. If the player has no valid moves, the move is passed to the opponent.
5) The game stops when all the chips are placed on the board or when none of the players can make a move. At the end of the game, the chips of each color are counted, and the player with more chips on the board is declared the winner. If the number of chips is equal, a draw is counted.

After the game has been finished, the main score is displayed, based on which a message is displayed about who won the game: the user or the AI.

![reversi](https://user-images.githubusercontent.com/61186198/109392719-c2e24f80-792e-11eb-98b9-ad16dc02478d.gif)
