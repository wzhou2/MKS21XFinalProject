import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Rook extends Piece{
    private boolean firstMove;
    
    public Rook(int x, int y, int c) {
        this(new Coords(x, y), c);
    }

    public Rook(Coords coor, int c) {
        super(coor, c);
        firstMove = true;
        if (c == 0) {
            this.setIcon(new ImageIcon(new ImageIcon("./Icons/blackRook.png").getImage().getScaledInstance(Board.width/8,Board.height/8, Image.SCALE_SMOOTH)));
        } else {
            this.setIcon(new ImageIcon(new ImageIcon("./Icons/whiteRook.png").getImage().getScaledInstance(Board.width/8,Board.height/8, Image.SCALE_SMOOTH)));
        }
    }
    
    public ArrayList<Coords> getValidMoves() {
        ArrayList<Coords> validSet = new ArrayList<Coords>();
        int xcor = getPosition().getX();
        int ycor = getPosition().getY();
        //Orthogonal
        validSet.addAll(getMoves(xcor,ycor, -1, 0));
        validSet.addAll(getMoves(xcor,ycor, 1, 0));
        validSet.addAll(getMoves(xcor,ycor, 0, -1));
        validSet.addAll(getMoves(xcor,ycor, 0, 1));
        return validSet;
    }
    
    public void notFirst() {
        firstMove = false;
    }
    
    public boolean getFirst() {
        return firstMove;
    }
    
    public String toString() {
        String returnString = super.toString();
        return "Rook" + returnString.substring(returnString.indexOf(";")) + ";" + getFirst();
    }
}
