import javax.swing.*;
import java.awt.*;



public class PathJButton extends JButton 
{
    BTNCoordinates coordinates;
    //double pythHeurVal, manhattanHeurVal;


    public PathJButton(BTNCoordinates mCoordinates)
    {
        coordinates = mCoordinates;
    }

    //Will do operations with Linkedlists in the future
    public void makeWall()
    {
        this.setBackground(Color.BLACK);
    }

    public void makeStart()
    {
        this.setBackground(Color.GREEN);
    }

    public void makeEnd()
    {
        this.setBackground(Color.RED);
    }
}



class BTNCoordinates
{
    private int x;
    private int y;

    public BTNCoordinates(int mX, int mY)
    {
        x = mX;
        y = mY;
    }    

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

}