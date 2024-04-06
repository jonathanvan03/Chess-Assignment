package chess;

import java.util.ArrayList;
import java.awt.Point;

public class Knight extends Piece {
    public Knight(int x, int y, String color) {
        super(x, y, color);
        if (color == "white") {
            type = "wn";
        } else {
            type = "bn";
        }
    }

    public String getType() {
        return type;
    }

    public void generalMoves(Board b, ArrayList<Point> list) {
        int x = position.x;
        int y = position.y; 

        Point[] moveSet = {
            new Point(x-2, y-1), new Point(x-2, y+1), new Point(x-1, y-2), new Point(x-1, y+2), // up moves
            new Point(x+2, y-1), new Point(x+2, y+1), new Point(x+1, y-2), new Point(x+1, y+2) //down moves
        };
        for (Point p : moveSet) {
            if (p.x >= 0 && p.x <= 7 && p.y <= 7 && p.y >= 0) { // within bounds
                if (b.getPiece(p) == null) {
                 list.add(p);
                } else if (!b.getPiece(p).color.equals(this.color)) {
                    list.add(p);
                }
            }
        }
    }

    public ArrayList<Point> possibleMoves(Board b) {
        ArrayList<Point> movesList = new ArrayList<>();
        generalMoves(b, movesList);
        return movesList;
    }



}
