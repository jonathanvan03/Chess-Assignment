package chess;


import java.util.ArrayList;
import java.awt.Point;
import java.util.Iterator;



public class King extends Piece{

    public boolean castle;

    public King(int x, int y, String color, boolean castle) {
        super(x, y, color);
        if (color.equals("white")) {
            type = "wk";
        } else {
            type = "bk";
        }
        this.castle = castle;
    }

    public String getType() {
        return type;
    }

    public void setCastle() {
        this.castle = false;
    }

    public void generalMoves(Board b, ArrayList<Point> list) {
        int x = position.x;
        int y = position.y;

        Point[] moveSet = {
            new Point(x-1, y-1), new Point(x-1, y), new Point(x-1, y+1), new Point(x, y+1),
            new Point(x+1, y+1), new Point(x+1, y), new Point(x+1, y-1), new Point(x, y-1)
        };

        for (Point p : moveSet) {
            if (p.x > -1 && p.x < 8 && p.y > -1 && p.x < 8) {
                if (b.getPiece(p) == null) {
                    list.add(p);
                } else if (!b.getPiece(p).color.equals(this.color)) {
                    list.add(p);
                }
            }
        }
    }

    public void castleMoves(Board b, ArrayList<Point> list) {
        if (!this.castle) return; // return if the king has already moved

        //if (b.isCheck(this.color)) return; // cannot castle if the king is under check

        ArrayList<Piece> castleRooks = new ArrayList<Piece>();
		for (Piece p:b.pieces) { // finds the rooks that can could castle with the king if there are any
			if (p instanceof Rook && p.color.equals(this.color) && ((Rook) p).castle) {
				castleRooks.add(p);
			}
		}
        /** added **/
        // Create an iterator for castleRooks list
        Iterator<Piece> iterator = castleRooks.iterator();
        while (iterator.hasNext()) {
        Piece p = iterator.next();
        // Check conditions for castling eligibility
        // If conditions are not met, remove the rook from the castleRooks list
        if (p.position.y == 0) {
            if (b.getPiece(new Point(p.position.x, 1)) != null 
                && b.getPiece(new Point(p.position.x, 2)) != null
                && b.getPiece(new Point(p.position.x, 3)) != null) {
                iterator.remove(); // Safely remove the rook from the castleRooks list
            }
        } else if (p.position.y == 7) {
            if (b.getPiece(new Point(p.position.x, 5)) != null
                && b.getPiece(new Point(p.position.x, 6)) != null) {
                iterator.remove(); // Safely remove the rook from the castleRooks list
            }
        }
    }

        // After filtering out ineligible rooks, add valid castling moves to the list
        for (Piece p : castleRooks) {
            if (p.position.y == 0) {
                list.add(new Point(this.position.x, 2));
            } else if (p.position.y == 7) {
                list.add(new Point(this.position.x, 6));
            }
        }
    }
    /** added **/

        // if (castleRooks.isEmpty()) return;

        // for (Piece p : castleRooks) {
        //     if (p.position.y == 0) {
        //         if (b.getPiece(new Point(p.position.x, 1)) != null 
        //             && b.getPiece(new Point(p.position.x, 2)) != null
        //             && b.getPiece(new Point(p.position.x, 3)) != null) {
        //                 castleRooks.remove(p);
        //         }
        //     } else if (p.position.y == 7) {
        //         if (b.getPiece(new Point(p.position.x, 5)) != null
        //             && b.getPiece(new Point(p.position.x, 6)) != null) {
        //                 castleRooks.remove(p);
        //         }
        //     }
        // }

        // if (castleRooks.isEmpty()) return;
// 
    //     for (Piece p : castleRooks) {
    //         if (p.position.y == 0) {
    //             list.add(new Point(this.position.x, 2));
    //         } else if (p.position.y == 7) {
    //             list.add(new Point(this.position.x, 6));
    //         }
    //     }

    // }

    

    public ArrayList<Point> possibleMoves(Board b) {
        ArrayList<Point> movesList = new ArrayList<>();
        generalMoves(b, movesList);
        castleMoves(b, movesList);
        return movesList;
    }
}
