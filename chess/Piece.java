package chess;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Piece {
    public Point position;
    public String color;
    public String type;

    public Piece(int x, int y, String color) {
        this.position = new Point(x, y);
        this.color = color;
        type = "";
    }

    public String getColor() {
        return this.color;
    }

    public Point getPosition() {
        return position;
    }

    public abstract String getType();

    public abstract ArrayList<Point> possibleMoves(Board b);
}
