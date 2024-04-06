package chess;

import java.util.ArrayList;
import java.awt.Point;

public  class Rook extends Piece {

    public boolean castle;
    public Rook(int x, int y, String color, boolean castle) {
        super(x, y, color);
        if (color.equals("white")) {
            type = "wr";
        } else {
            type = "br";
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

    public ArrayList<Point> possibleMoves(Board b) {
        ArrayList<Point> movesList = new ArrayList<>();
        generalMoves(b, movesList);

        return movesList;
    }
}
