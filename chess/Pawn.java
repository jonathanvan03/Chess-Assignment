package chess;

import java.util.ArrayList;
import java.awt.Point;

public class Pawn extends Piece{
    private boolean enPassant; // flag for first move and en pessant

    public Pawn(int x, int y, String color, boolean enpassant) {
        super(x, y, color);
        if (color.equals("white")) {
            type = "wp";
        } else {
            type = "bp";
        }
        enPassant = false; //defaults to false only true the move two place pawn move
    }

    public void generalMoves(Board b, ArrayList<Point> list) { // if its the first move then the piece can move two places, if its not it can move one place forward if no piece is in the way
        int x = position.x;
        int y = position.y;

        if (this.color.equals("white")) { // white pawn logic 
            if (x != 0) { //  x = 0 which should never happen, because pawn will get promoted
                Point p1 = new Point(x - 1, y); // one place forward
                Point p2 = new Point(4, y); // two places forward
                if (b.getPiece(p1) == null) { // checks if the next space is free of another piece
                    list.add(p1);
                    if (x == 6 && b.getPiece(p2) == null) { // if next two places is free and its the first move 
                        list.add(p2);
                    }
                }
            } 
        } else {
            if (x != 7) {
                Point p1 = new Point(x + 1, y);
                Point p2 = new Point(3, y);
                if (b.getPiece(p1) == null) {
                    list.add(p1);
                    if (x == 1 && b.getPiece(p2) == null) {
                        list.add(p2);
                    }
                }
            }
        }
    }

    public void captureMoves(Board b, ArrayList<Point> list) { // finds moves to capture if possible 
        int x = position.x;
        int y = position.y;

        if (this.color.equals("white") && x != 0) { // checks forward diagonals for pieces of black color
            if (y != 7) {
                Point right = new Point(x - 1, y + 1); // from perspective of color 
                if (b.getPiece(right) != null && b.getPiece(right).color.equals("black")) {
                    list.add(right);
                }
            }
            if (y != 0) {
                Point left = new Point(x - 1, y - 1);
                if (b.getPiece(left) != null && b.getPiece(left).color.equals("black")) {
                    list.add(left);
                }
            }
        } else if (x != 7) { // checks forward diagonals for pieces of white color
            if (y != 0) {
                Point right = new Point(x + 1, y - 1); // from perspective of color
                if (b.getPiece(right) != null && b.getPiece(right).color.equals("white")) {
                    list.add(right);
                }
            }
            if (y != 7) {
                Point left = new Point(x + 1, y + 1);
                if (b.getPiece(left) != null && b.getPiece(left).color.equals("white")) {
                    list.add(left);
                }
            }
        }
    }

    public void enPassant(Board b, ArrayList<Point> list) {
        int x = position.x;
        int y = position.y;
        if (x == 3 && this.color.equals("white")) { 
            if (y != 0) {
                Point left = new Point(x, y - 1);
                Piece leftP = b.getPiece(left);
                if (leftP != null 
                    && leftP instanceof Pawn
                    && !leftP.color.equals(this.color)
                    && ((Pawn)leftP).getEnPassant()) {
                        list.add(new Point(x - 1, y - 1));
                }
            }
            if (y != 7) {
                Point right = new Point (x, y + 1);
                Piece rightP = b.getPiece(right);
                if (rightP != null 
                    && rightP instanceof Pawn
                    && !rightP.color.equals(this.getColor())
                    && ((Pawn) rightP).getEnPassant()) {
                        list.add(new Point(x - 1, y + 1));
                }
            }
        } else if (x == 4 && this.color.equals("black")) { // if black pawn on rank 
            if (y != 0) {
                Point right = new Point(x, y - 1);
                Piece rightP = b.getPiece(right);
                if (rightP != null
                    && rightP instanceof Pawn
                    && !rightP.color.equals(this.getColor())
                    && ((Pawn) rightP).getEnPassant()) {
                        list.add(new Point(x + 1, y - 1));
                    }
            }
            if (y != 7) {
                Point left = new Point(x, y + 1);
                Piece leftP = b.getPiece(left);
                if (leftP != null
                    && leftP instanceof Pawn
                    && !leftP.color.equals(this.color)
                    && ((Pawn)leftP).getEnPassant()) {
                        list.add(new Point(x + 1, y + 1));
                    }
            }
        }
    }

    public ArrayList<Point> possibleMoves(Board b) {
        ArrayList<Point> movesList = new ArrayList<>();
        generalMoves(b, movesList);
        captureMoves(b, movesList);
        enPassant(b, movesList);
        return movesList;
    }

    public boolean getEnPassant() {
        return this.enPassant;
    }

    public void setEnPassant(Boolean b) {
        this.enPassant = b;
    }

    public String getType() {
        return type;
    }
}
