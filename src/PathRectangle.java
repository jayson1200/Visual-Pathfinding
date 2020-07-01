import  java.awt.*;

public class PathRectangle extends Rectangle
{

    private recType recVal = recType.UNDECLARED;

    //double pythHeurVal, manhattanHeurVal;

    public PathRectangle(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    public enum recType{WALL, STARTNODE, ENDNODE, UNDECLARED}

    public recType getRecVal() {
        return this.recVal;
    }

    public void setRecVal(recType wall) {
        this.recVal = wall;
    }


}