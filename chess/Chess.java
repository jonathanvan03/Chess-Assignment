/*
 Jonathan Van jv654 
 Christine Yue cy344
 */
package chess;

import java.util.ArrayList;
import java.awt.Point;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece othepiece = (ReturnPiece)other;
		return pieceType == othepiece.pieceType &&
				pieceFile == othepiece.pieceFile &&
				pieceRank == othepiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	
	enum Player { white, black }

    public static ArrayList<ReturnPiece> returnPieces; // array of ReturnPiece type
	public static Board board; // board update 
	public static Player turn; // flag for player turn 
	public static boolean resign; // flag for board resignation 
	public static boolean draw; // flad for board draw
	
	public  static Point stringToPoint(String in) { // helper method to convert FileRank to a point
		int x = 8 - (in.charAt(1) - '0');
		int y = in.charAt(0) - 'a';
		return new Point(x, y);
	}

	public static void removePiece(Point[] points) {
		Point point = points[1];
		String file = yToFile(point.y);
		int rank = 8 - point.x;
		for (ReturnPiece p : returnPieces) {
			if (p.pieceRank == rank && (""+p.pieceFile+"").equals(file)){
                returnPieces.remove(p);
				break;
            }
		}
	}

	public static void updatePosition(Point[] points) {
		Point start = points[0];
		Point end = points[1];

		String startFile = yToFile(start.y);
		int startRank = 8 - start.x;

		String endFile = yToFile(end.y);
		int endRank = 8 - end.x;

		for (ReturnPiece p : returnPieces) {
			if (p.pieceRank == startRank && (""+p.pieceFile+"").equals(startFile)){
                p.pieceRank = endRank;
                changeFile(endFile, p);
            }
		}
		

		
	}

	public static void updateCastle(int s) {
		
        Point location = new Point();
        Point start = new Point();
    
        if (s == 1) {
            location = new Point(7, 5);
            start = new Point(7, 7);
        } else if (s == 2) {
            location = new Point(7, 3);
            start = new Point(7, 0);
        } else if (s == 3) {
            location = new Point(0, 5);
            start = new Point(0, 7);
        } else if (s == 4) {
            location = new Point(0, 3);
            start = new Point(0, 0);
        } else {
            // Handle invalid value of s, or provide an appropriate action
            System.out.println("Invalid value of s: " + s);
            return;
        }
    
        Point[] rookCastlePoints = {start, location};
    
        // Call the updateLocation method with rookCastlePoints if it exists
        updatePosition(rookCastlePoints);
	}

	public static void changeType(String s, ReturnPiece p) {
		switch (s){
			case "WQ":
				p.pieceType = ReturnPiece.PieceType.WQ;
                break;
			case "BQ":
				p.pieceType = ReturnPiece.PieceType.BQ;
                break;
			case "WR":
				p.pieceType = ReturnPiece.PieceType.WR;
                break;
			case "BR":
				p.pieceType = ReturnPiece.PieceType.BR;
                break;
			case "WB":
				p.pieceType = ReturnPiece.PieceType.WB;
                break;
			case "BB":
				p.pieceType = ReturnPiece.PieceType.BB;
                break;
			case "WN":
				p.pieceType = ReturnPiece.PieceType.WN;
                break;
			case "BN":
				p.pieceType = ReturnPiece.PieceType.BN;
                break;	
		}
	}

	public static void changeFile (String file, ReturnPiece p){
        switch (file) {
		case "a":
			p.pieceFile = ReturnPiece.PieceFile.a;
            break; 
		case "b":
			p.pieceFile= ReturnPiece.PieceFile.b;
            break;
		case "c":
			p.pieceFile= ReturnPiece.PieceFile.c;
            break;
		case "d":
			p.pieceFile= ReturnPiece.PieceFile.d;
            break;
		case "e":
			p.pieceFile= ReturnPiece.PieceFile.e;
            break;
		case "f":
			p.pieceFile= ReturnPiece.PieceFile.f;
            break;
		case "g":
			p.pieceFile= ReturnPiece.PieceFile.g;
            break;
		case "h":
			p.pieceFile= ReturnPiece.PieceFile.h;
            break;
		}
	}

	public static void promote(Point[] move, String type) {
		Point start = move[0];
		//Point end = move[1];

		String startFile = yToFile(start.y);
		int startRank = 8 - start.x;

		// String endFile = yToFile(end.y);
		// int endRank = 8 - end.x;

		for (ReturnPiece p : returnPieces) {
			if (p.pieceRank == startRank && (""+p.pieceFile+"").equals(startFile)) {
				changeType(type, p);
			}
		}

		updatePosition(move);
	}

	public static String yToFile (int y){
		char res = (char)(y + 'a');
        return Character.toString(res);
    }
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
		ReturnPlay res = new ReturnPlay();


		
			if (move.toLowerCase().equals("resign")) {  // checks if a player has chosen to resign
				resign = true;
				res.piecesOnBoard = returnPieces;
				if (board.playerTurn.equals("white")) { // checks the current players turn to determine who resigns
					res.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
				} else {
					res.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
				}
				return res;
			}

			if (board.playerTurn.equals(board.lastTurn)) {
				board.removeEnPassant();
			}

			String[] input = move.split(" "); // splits the move into spearate parts
			if (input.length >= 2) {
				Point[] moves = {stringToPoint(input[0]), stringToPoint(input[1])};
				if (board.checkValidMove(moves)) { // validates the move entered
					if (board.isPromotion(moves)) {
						if (input.length == 2) { // automatically promote to queen
							board.promote(new Queen(0, 0, board.playerTurn), moves);
                                if (board.playerTurn.equals("white")){
                                    promote(moves, "WQ");
                                }
                                else{
                                    promote(moves, "BQ");
                                }
                                res.piecesOnBoard = returnPieces;
                                res.message = null;
						} else { // promote to piece requested
							switch (input[2]) {
								case "Q":
                                board.promote(new Queen(0, 0, board.playerTurn), moves);
                                if (board.playerTurn.equals("white")){
                                    promote(moves, "WQ");
                                }
                                else{
                                    promote(moves, "BQ");
                                }
                                res.piecesOnBoard = returnPieces;
                                res.message = null;
                                //board.drawBoard();
                                break;
                            case "N":
                                board.promote(new Knight(0, 0, board.playerTurn), moves);
                                if (board.playerTurn.equals("white")){
                                    promote(moves, "WN");
                                }
                                else{
                                    promote(moves, "BN");
                                }
                                res.piecesOnBoard = returnPieces;
                                res.message = null;
                                //board.drawBoard();
                                break;
                            case "R":
                                board.promote(new Rook(0, 0, board.playerTurn, false), moves);
                                if (board.playerTurn.equals("white")){
                                    promote(moves, "WR");
                                }
                                else{
                                    promote(moves, "BR");
                                }
                                res.piecesOnBoard = returnPieces;
                                res.message = null;
                                //board.drawBoard();
                                break;
                            case "B":
                                board.promote(new Bishop(0, 0, board.playerTurn), moves);
                                if (board.playerTurn.equals("white")){
                                    promote(moves, "WB");
                                }
                                else{
                                    promote(moves, "BB");
                                }
                                res.piecesOnBoard = returnPieces;
                                res.message = null;
                                //board.drawBoard();
								break;
                                
                            default:
                                res.piecesOnBoard = returnPieces;
                                res.message = ReturnPlay.Message.ILLEGAL_MOVE;
                                return res;
							}
						}
					} else {
						if (board.isCastleAttempt(moves)) {
							board.performMove(moves);
							updatePosition(moves);
							int whichCastle = board.performCastle(moves);
							if (whichCastle != 0) {
								updateCastle(whichCastle);
							}
						} else {						
							// update rook/king castling if it was a rook/king that moved
							board.removeCastle(moves[0]);
							board.performMove(moves);
							removePiece(moves);
							updatePosition(moves);
						}						
					}

					if (input.length == 3) { // if a third argument is passed in
						if (input[2].equals("draw?")) {
							draw = true;
						}
					}
				} else {
					res.message = ReturnPlay.Message.ILLEGAL_MOVE;
				}
				
			} else {
				res.message = ReturnPlay.Message.ILLEGAL_MOVE;
			}
			
			if (draw) {
				res.message = ReturnPlay.Message.DRAW;
			}

			//is checkmate 

			String oppositeColor = (board.playerTurn.equals("white")) ? "white" : "black"; // since value is changed after move, this essentiall finds the previoius turn color
   			boolean opponentCheck = board.isCheck(oppositeColor);
			if (opponentCheck && res.message != ReturnPlay.Message.ILLEGAL_MOVE) {
				if (board.isCheckmate(board.playerTurn)) {
					if (board.playerTurn.equals("white")) {
						res.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
					} else {
						res.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
					}
				} else {
					res.message = ReturnPlay.Message.CHECK;
				}
			}


		res.piecesOnBoard = returnPieces;
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return res;
	}
	
	public static void addPiece(ArrayList<ReturnPiece> list, ReturnPiece.PieceType type, ReturnPiece.PieceFile file, int rank) {
		ReturnPiece piece = new ReturnPiece();
		piece.pieceType = type;
		piece.pieceFile = file;
		piece.pieceRank = rank;
		list.add(piece);
	}

	/**
	 * This method should reset the board, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		returnPieces = new ArrayList<ReturnPiece>();
		board = new Board();
		turn = Player.white;
		resign = false;
		draw = false;

		// add white pieces to returnPieces
		addPiece(returnPieces, ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.a, 1);
		addPiece(returnPieces, ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.b, 1);
		addPiece(returnPieces, ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.c, 1);
		addPiece(returnPieces, ReturnPiece.PieceType.WQ, ReturnPiece.PieceFile.d, 1);
		addPiece(returnPieces, ReturnPiece.PieceType.WK, ReturnPiece.PieceFile.e, 1);
		addPiece(returnPieces, ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.f, 1);
		addPiece(returnPieces, ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.g, 1);
		addPiece(returnPieces, ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.h, 1);

		// add black pieces to returnPieces
		addPiece(returnPieces, ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.a, 8);
		addPiece(returnPieces, ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.b, 8);
		addPiece(returnPieces, ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.c, 8);
		addPiece(returnPieces, ReturnPiece.PieceType.BQ, ReturnPiece.PieceFile.d, 8);
		addPiece(returnPieces, ReturnPiece.PieceType.BK, ReturnPiece.PieceFile.e, 8);
		addPiece(returnPieces, ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.f, 8);
		addPiece(returnPieces, ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.g, 8);
		addPiece(returnPieces, ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.h, 8);

		// add all pawns
		char[] files = {'a','b','c','d','e','f','g','h'};
		for (int i = 0; i < 8; i++){
			addPiece(returnPieces, ReturnPiece.PieceType.WP, ReturnPiece.PieceFile.valueOf(String.valueOf(files[i])), 2);
			addPiece(returnPieces, ReturnPiece.PieceType.BP, ReturnPiece.PieceFile.valueOf(String.valueOf(files[i])), 7);
		}

		if (board.playerTurn.equals("white")) {
			System.out.println("White player's Turn");
		} else {
			System.out.println("white players Turn");
		}
	}	
}
