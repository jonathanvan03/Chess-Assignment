package chess;

import java.util.ArrayList;
import java.awt.Point;

public class Queen extends Piece {
    public Queen(int x, int y, String color) {
        super(x, y, color);
        if (color.equals("white")) {
            type = "wq";
        } else {
            type = "bq";
        }
    }

    public String getType() {
        return type;
    }

    public void straightMoves (Board b, ArrayList<Point> list) {
        int x = position.x;
        int y = position.y;

        for (int i = x + 1; i < 8; i++) { // move down
            Point p = new Point(i, y);
            if (b.getPiece(p) == null) {
                list.add(p);
            } else if (!(b.getPiece(p).color.equals(this.color))) {
                list.add(p);
                break;
            } else {
                break;
            }
        }

        for (int i = x - 1; i > -1; i--) { // move up
            Point p = new Point(i, y);
            if (b.getPiece(p) == null) {
                list.add(p);
            } else if (!(b.getPiece(p).color.equals(this.color))) {
                list.add(p);
                break;
            } else {
                break;
            }
        }

        for (int i = y + 1; i < 8; i++) { // right moves
            Point p = new Point(x, i);
            if (b.getPiece(p) == null) {
                list.add(p);
            } else if (!(b.getPiece(p).color.equals(this.color))) {
                list.add(p);
                break;
            } else {
                break;
            }
        }

        for (int i = y - 1; i > -1; i--) { //left moves
            Point p = new Point(x, i);
            if (b.getPiece(p) == null) {
                list.add(p);
            } else if (!(b.getPiece(p).color.equals(this.color))) {
                list.add(p);
                break;
            } else {
                break;
            }
        }
    }


    public void diagonalMoves (Board b, ArrayList<Point> list) {
        int x = position.x;
        int y = position.y;

        int i = x - 1, j = y + 1; // up-right
        while (i > -1 && j < 8) {
            Point p = new Point (i, j);
            if (b.getPiece(p) == null) {
                list.add(p);
            } else if (!(b.getPiece(p).color.equals(this.color))) {
                list.add(p);
                break;
            } else {
                break;
            }
            i--;
            j++;
        }

        i = x - 1; j = y - 1; // up-left
        while (i > -1 && j > -1) {
            Point p = new Point (i, j);
            if (b.getPiece(p) == null) {
                list.add(p);
            } else if (!(b.getPiece(p).color.equals(this.color))) {
                list.add(p);
                break;
            } else {
                break;
            }
            i--;
            j--;
        }

        i = x + 1; j = y -1; // down-left
        while (i < 8 && j > -1) {
            Point p = new Point (i, j);
            if (b.getPiece(p) == null) {
                list.add(p);
            } else if (!(b.getPiece(p).color.equals(this.color))) {
                list.add(p);
                break;
            } else {
                break;
            }
            i++;
            j--;
        }

        i = x + 1; j = y + 1; // down-righit
        while (i < 8 && j < 8) {
            Point p = new Point (i, j);
            if (b.getPiece(p) == null) {
                list.add(p);
            } else if (!(b.getPiece(p).color.equals(this.color))) {
                list.add(p);
                break;
            } else {
                break;
            }
            i++; 
            j++;
        }
    }

    public ArrayList<Point> possibleMoves(Board b) {
        ArrayList<Point> movesList = new ArrayList<Point>();
        straightMoves(b, movesList);
        diagonalMoves(b, movesList);
        return movesList;
    }
}
