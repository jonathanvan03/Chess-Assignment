# Chess Assignment
### Chess Assignment for Software Methodology

 ##### Provided Files: 
 - PlayChess.java: 
 application which runs chess game in terminal

---
##### Implemented Files:
- Chess.java: 
Chess Package 
- Board.java:
Board Class handling game logic including, check, checkmate, illegal moves, and more.
- Pieces:
Individual classes for each piece type handling movesets. Includes special cases for pieces such as En Passant for Pawns and castling for Kings and Rooks. 

---
##### Instructions to run:
- Compile chess package to create java .class files: in Chess-Assignment directory run 'javac chess/*.java'
- To run the program: 'java chess.PlayChess' 
- White always moves first
- move examples: 
    - e2 e4 (move piece from fileRank to fileRank)
    - g1 f3 draw? (assumes both players agree to draw)
    - resign (current player's turn resigns)
    - reset (resets the board and starts a new game)
    - quit (closes program)


