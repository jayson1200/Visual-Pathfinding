import  java.awt.*;

public class PathRectangle extends Rectangle
{

    public recType recVal = recType.UNDECLARED;

    public PathRectangle rectangleParent;

    double hCost = 0;
    double gCost = 0;

    public PathRectangle(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }



    public enum recType{WALL, STARTNODE, ENDNODE, UNDECLARED, OPEN, CLOSED, PATH}

    public recType getRecVal() {
        return this.recVal;
    }

    public void setRecVal(recType wall) {
        this.recVal = wall;
    }
    public PathRectangle getRectangleParent() {
        return this.rectangleParent;
    }

    public void setRectangleParent(PathRectangle rectangleParent) {
        this.rectangleParent = rectangleParent;
    }

    public double getHCost() {
        return this.hCost;
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
    }


    public double getGCost() {
        return this.gCost;
    }

    public void setGCost(double gCost) {
        this.gCost = gCost;
    }

    public double getFCost()
    {
        return gCost + hCost;
    }

}