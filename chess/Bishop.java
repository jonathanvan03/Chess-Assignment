package chess;


import java.util.ArrayList;
import java.awt.Point;

public class Bishop extends Piece{
    public Bishop(int x, int y, String color) {
        super(x, y, color);
        if (color.equals("white")) {
            type = "wb";
        } else {
            type = "bb";
        }
    }

    public String getType() {
        return type;
    }

    public void generalMoves(Board b, ArrayList<Point> list) {
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
        ArrayList<Point> movesList = new ArrayList<>();
        generalMoves(b, movesList);
        return movesList;
    }
}
