package chess;

import java.util.ArrayList;
import java.awt.Point;

public class Board {

    public ArrayList<Piece> pieces;
    public String playerTurn;
    public String lastTurn;

    public Board () {
        playerTurn = "white";
        pieces = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) { // adds pawns to the board
            pieces.add(new Pawn(6, i, "white", false));
            pieces.add(new Pawn(1, i, "black", false));
        }
        //queens
        pieces.add(new Queen(7, 3, "white"));
        pieces.add(new Queen(0, 3, "black"));

        // kings
        pieces.add(new King(7, 4, "white", true));
        pieces.add(new King(0, 4, "black", true));

        // rooks
        pieces.add(new Rook(7, 0, "white", true));
        pieces.add(new Rook(7, 7, "white", true));
        pieces.add(new Rook(0, 0, "black", true));
        pieces.add(new Rook(0, 7, "black", true));

        // knights
        pieces.add(new Knight(7, 1, "white"));
        pieces.add(new Knight(7, 6, "white"));
        pieces.add(new Knight(0, 1, "black"));
        pieces.add(new Knight(0, 6, "black"));

        // bishops
        pieces.add(new Bishop(7, 2, "white"));
        pieces.add(new Bishop(7, 5, "white"));
        pieces.add(new Bishop(0, 2, "black"));
        pieces.add(new Bishop(0, 5, "black"));

    }
    
    public Board(Board b) {
        this.pieces = new ArrayList<>();
        for (Piece p : b.pieces) {
            if (p instanceof Rook) {
                this.pieces.add(new Rook(p.position.x, p.position.y, p.color, ((Rook)p).castle));
            } else if (p instanceof King) {
                this.pieces.add(new King(p.position.x, p.position.y, p.color, ((King)p).castle));
            } else if (p instanceof Pawn) {
                this.pieces.add(new Pawn(p.position.x, p.position.y, p.color, ((Pawn)p).getEnPassant()));
            } else if (p instanceof Queen) {
                this.pieces.add(new Queen(p.position.x, p.position.y, p.color));
            } else if (p instanceof Bishop) {
                this.pieces.add(new Bishop(p.position.x, p.position.y, p.color));
            } else {
                this.pieces.add(new Knight(p.position.x, p.position.y, p.color));
            }
        }
        this.playerTurn = b.playerTurn;
    }

    public boolean isCheck(String color) {
        Point kingPos = null;
        for (Piece p : pieces) { // find the posiiton of the current color's king
            if (p instanceof King && p.color.equals(color)) {
                kingPos = p.position;
                break;
            }
        }

        for (Piece p : pieces) {
            if (!p.color.equals(color)) {
                ArrayList<Point> moves = p.possibleMoves(this);
                if (moves.contains(kingPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(String color) {
        if (!isCheck(color)) {
            return false; // The king is not in check, so it's not checkmate
        }
    
        // 2. Iterate through all opponent's pieces
        for (Piece p : pieces) {
            if (p.color.equals(color)) {
                // 3. Get all possible moves for the piece
                ArrayList<Point> moves = p.possibleMoves(this);
                // 4. Check if any of these moves result in the king not being in check
                for (Point move : moves) {
                    Board copy = new Board(this); // Make a copy of the current board
                    copy.performMove(new Point[] {p.position, move}); // Make the move on the copy
                    // 5. If the move takes the king out of check, it's not checkmate
                    if (!copy.isCheck(color)) {
                        //copy.drawBoard();
                        return false;
                    }
                }
            }
        }
    
        // 6. If no legal moves take the king out of check, it's checkmate
        return true;
    }

    public Piece getPiece(Point position) {
        for (Piece p : pieces) {
            if (p.position.equals(position)) {
                return p;
            }
        }
        return null;
    }

    public boolean isPromotion(Point[] points) {
        if (this.getPiece(points[0]) instanceof Pawn && points[0].x == 1 && this.getPiece(points[0]).color.equals("white")) {
            return true;
        } else if (this.getPiece(points[0]) instanceof Pawn && points[0].x == 6 && this.getPiece(points[0]).color.equals("black")) {
            return true;
        } else {
            return false;
        }
    }

    public void promote(Piece newPiece, Point[] points) {
        if (this.getPiece(points[1]) == null) {
            pieces.remove(this.getPiece(points[0]));
            newPiece.position = points[1];
            pieces.add(newPiece);
        } else {
            pieces.remove(this.getPiece(points[1]));
            pieces.remove(this.getPiece(points[0]));
            newPiece.position = points[1];
            pieces.add(newPiece);
        }
    }

    public boolean checkValidMove(Point[] points) { // checks if the input move is valid
        Piece curr = this.getPiece(points[0]);
        if (curr == null) {
            return false;
        }
        if (points[1].x > 7 || points[1].x < 0 || points[1].y > 7 || points[1].y < 0) { // destination point is out of index range
            return false; 
        }
        if (!curr.color.equals(playerTurn)) { // first point is opposing piece
            return false; 
        }
        if (!curr.possibleMoves(this).contains(points[1])) { // checks to see if the move enterd is a possible move of the current piece
            return false; 
        } 
        Board copy = new Board(this);
        
        String color = copy.getPiece(points[0]).color;
        copy.performMove(points);
        if (copy.isCheck(color)) {
            return false;
        }
        return true;
    }

    public void removeEnPassant() { // method to reset the en passant flag after one opposing turn
        for (Piece p : this.pieces) {
            if (p instanceof Pawn) {
                ((Pawn)p).setEnPassant(false);
            }
        }
    }

    public void removeCastle(Point pos) {
        for (Piece p : this.pieces) {
            if (p instanceof Rook && p.position == pos) {
                ((Rook)p).setCastle();
            } else if (p instanceof King && p.position == pos) {
                ((King)p).setCastle();
            }
        }
    }

    public boolean isSquareAttacked(Point square, String color, Board b) { // helper function to check if a square can be attacked by opposing player
        // Iterate through all pieces on the board
        for (Piece piece : this.pieces) {
            // Check if the piece belongs to the opposing color
            if (!piece.getColor().equals(color)) {
                // Get possible moves for the piece
                ArrayList<Point> moves = piece.possibleMoves(b);
    
                // Check if the square is one of the possible moves
                if (moves.contains(square)) {
                    return true; // Square is attacked
                }
            }
        }
        return false; // Square is not attacked
    }

    public boolean isCastleAttempt(Point[] points) { // method to check if the move is a castle move
        Point[] kingPos = {new Point(0, 4), new Point(7, 4)};
        Point[] top = {new Point(0, 6), new Point(0, 2)}; // destinations for black castle
        Point[] bottom = {new Point(7, 2), new Point(7, 6)};
        if (this.getPiece(points[0]) instanceof King && points[0].equals(kingPos[0])) { // black king
            if (points[1].equals(top[0]) || points[1].equals(top[1])){
                return true;
            } else {
                return false;
            }
        } else if (this.getPiece(points[0]) instanceof King && points[0].equals(kingPos[1])) { // white king
            if (points[1].equals(bottom[0]) || points[1].equals(bottom[1])) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public int performCastle(Point[] points) {
		int selectCastle = 0;
		// System.out.println("doCastle method in board called");
		if (points[0].equals(new Point(7,4)) && points[1].equals(new Point(7,6))) {
			Piece rook = this.getPiece(new Point(7,7));
			rook.position = new Point(7,5);
			selectCastle = 1;
		} else if (points[0].equals(new Point(7,4)) && points[1].equals(new Point(7,2))){
			Piece rook = this.getPiece(new Point(7,0));
			rook.position = new Point(7,3);
			selectCastle = 2;
		} else if (points[0].equals(new Point(0,4)) && points[1].equals(new Point(0,6))) {
			Piece rook = this.getPiece(new Point(0,7));
			rook.position = new Point(0,5);
			selectCastle = 3;
		} else if (points[0].equals(new Point(0,4)) && points[1].equals(new Point(0,2))) {
			Piece rook = this.getPiece(new Point(0,0));
			rook.position = new Point(0,3);
			selectCastle = 4;
		}
		return selectCastle;
	}

    public void performMove(Point[] points) { // performs the move on the board and changes the turn flag
        Piece pieceMoving = this.getPiece(points[0]);
        if ((pieceMoving instanceof Pawn) && points[0].y != points[1].y && this.getPiece(points[1]) == null) {
            // En passant capture: remove the captured pawn
            int captureX = points[0].x; // Row of the captured pawn
            int captureY = points[1].y; // Column of the captured pawn
    
            Point capturePosition = new Point(captureX, captureY);
    
            pieces.remove(getPiece(capturePosition)); // Remove the captured pawn from the board
            Chess.removePiece(new Point[] {null, capturePosition});
        }
        if (this.getPiece(points[1]) != null) {  
            pieces.remove(this.getPiece(points[1]));
        }
        pieceMoving.position = points[1];
        if (pieceMoving instanceof Pawn && Math.abs(points[0].x - points[1].x) == 2) {
            ((Pawn)pieceMoving).setEnPassant(true);
            lastTurn = this.playerTurn;
        }
        
        playerTurn = playerTurn.equals("white") ? "black" : "white";
    }

    public void drawBoard() {
		System.out.println();
		String[][] board = new String[8][8];
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				for (Piece p: pieces) {
					if (p.position.equals(new Point(i,j))) {
						board[i][j] = p.getType() + " ";
						break;
					} 
				}
				if (board[i][j] == null) {
					if ((i+j)%2==1) 
						board[i][j] = "## ";
					else
						board[i][j] = "   ";
				}
			}
		}
		
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				System.out.print(board[i][j]);
			}
			System.out.print(7-i+1);
			
			System.out.println();
		}
		
		System.out.println(" a  b  c  d  e  f  g  h ");
		System.out.println();
		
	}
}
