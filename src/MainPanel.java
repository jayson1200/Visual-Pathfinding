import javax.swing.*;



import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class MainPanel extends JPanel {

    static ArrayList<ArrayList<PathRectangle>> paintedRectangles = new ArrayList<>();

    static int rectLenWidSize;

    static public PathRectangle startRect;
    static public PathRectangle endRect;

    ArrayList<PathRectangle> openRects = new ArrayList<>();
    ArrayList<PathRectangle> closedRects = new ArrayList<>();


    public MainPanel(ArrayList<ArrayList<PathRectangle>> mPathRectangles) 
    {
        paintedRectangles = mPathRectangles;

        addMouseListener(new MouseAdapter()
        {
           
            @Override
            public void mousePressed(MouseEvent e) {
    
                for(int i = 0; i < paintedRectangles.size(); i++)
                {
                        for(int j = 0; j < paintedRectangles.get(0).size(); j++)
                        {
                            if(paintedRectangles.get(i).get(j).contains(e.getPoint()))
                            {
                                if(UI.declareWallsBOX.isSelected())
                                {
                                    paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.WALL);
                                }
                                else if(UI.declareStartBOX.isSelected())
                                {
                                    checkResetStart();
                                    paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.STARTNODE);
                                    startRect = paintedRectangles.get(i).get(j);
                                }
                                else if(UI.declareEndBOX.isSelected())
                                {
                                    checkResetEnd();
                                    paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.ENDNODE);
                                    endRect = paintedRectangles.get(i).get(j);
                                }
                                else if(UI.deselectBOX.isSelected())
                                {
                                    paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.UNDECLARED);
                                }

                            }
                    }
            
                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter()
        {

            @Override
            public void mouseDragged(MouseEvent e) 
            {
                
                    for(int i = 0; i < paintedRectangles.size(); i++)
                    {
                            for(int j = 0; j < paintedRectangles.get(0).size(); j++)
                            {
                                if(paintedRectangles.get(i).get(j).contains(e.getPoint()))
                                {
                                    if(UI.declareWallsBOX.isSelected())
                                    {
                                        paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.WALL);
                                    }
                                    else if(UI.declareStartBOX.isSelected())
                                    {
                                        checkResetStart();
                                        paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.STARTNODE);

                                        startRect = paintedRectangles.get(i).get(j);
                                    }
                                    else if(UI.declareEndBOX.isSelected())
                                    {
                                        checkResetEnd();
                                        paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.ENDNODE);
                                        endRect = paintedRectangles.get(i).get(j);
                                    }
                                    else if(UI.deselectBOX.isSelected())
                                    {
                                        paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.UNDECLARED);
                                    }
                                }
                        }
                
                    }
                    repaint();
            }
        });

        rectLenWidSize = (int) (-13.2306 * (Math.log(0.0023*((paintedRectangles.size() + paintedRectangles.get(0).size()) / 2))));

    }

    public void paintComponent(Graphics g)
    {
            super.paintComponent(g);
            
            Graphics2D g2D = (Graphics2D) g;

            for(int i = 0; i < paintedRectangles.size(); i++)
            {
                for(int j = 0; j < paintedRectangles.get(0).size(); j++)
                {
                
                    switch(paintedRectangles.get(i).get(j).getRecVal())
                    {
                        case UNDECLARED:
                        g2D.drawRect(i*rectLenWidSize, j*rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                        case WALL:
                        g2D.setColor(Color.BLACK);
                        g2D.fillRect(i*rectLenWidSize, j * rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                        case STARTNODE:
                        g2D.setColor(Color.GREEN);
                        g2D.fillRect(i*rectLenWidSize, j*rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                        case ENDNODE:
                        g2D.setColor(Color.RED);
                        g2D.fillRect(i*rectLenWidSize, j*rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                        case OPEN:
                        g2D.setColor(Color.GRAY);
                        g2D.fillRect(i*rectLenWidSize, j*rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                        case CLOSED:
                        g2D.setColor(Color.BLUE);
                        g2D.fillRect(i*rectLenWidSize, j*rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;
                    }
                    g2D.setColor(Color.BLACK);
                }
            }
        }
            
        
    

    public void checkResetStart()
    {
        startRect = null;

        for(int i = 0; i < paintedRectangles.size(); i++)
        {
                for(int j = 0; j < paintedRectangles.get(0).size(); j++)
                {
                    if(paintedRectangles.get(i).get(j).getRecVal() == PathRectangle.recType.STARTNODE)
                    {
                        paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.UNDECLARED);
                    }
                }
        }
    
    }

    public void checkResetEnd()
    {
        endRect = null;
         
        for(int i = 0; i < paintedRectangles.size(); i++)
        {
                for(int j = 0; j < paintedRectangles.get(0).size(); j++)
                {
                    if(paintedRectangles.get(i).get(j).getRecVal() == PathRectangle.recType.ENDNODE)
                    {
                        paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.UNDECLARED);
                    }
                }
        }
    
    }

    public int getRectLenWidSize() {
        return this.rectLenWidSize;
    }

    public void setRectLenWidSize(int rectLenWidSize) {
        this.rectLenWidSize = rectLenWidSize;
    }

    public void findPath()
    {
        ArrayList<PathRectangle> openRects = new ArrayList<>();
        ArrayList<PathRectangle> closedRects = new ArrayList<>();

        PathRectangle currentNode = startRect;

        assignHCost();

        paintedRectangles.get(startRect.x/rectLenWidSize).get(startRect.y/rectLenWidSize).recVal = PathRectangle.recType.CLOSED;
        closedRects.add(paintedRectangles.get(startRect.y/rectLenWidSize).get(startRect.x/rectLenWidSize));
        repaint();

        while(currentNode != endRect && UI.run)
        {
            UI.run = false;
            //Find efficient way to find new open nodes
        }
    }

    public void assignHCost()
    {
        for(int i = 0; i < paintedRectangles.size(); i++)
        {
            for(int j = 0; j < paintedRectangles.get(0).size(); j++)
            {
                double hCost = Math.sqrt(Math.pow((double) i-(endRect.y/rectLenWidSize), 2) + Math.pow((double) j-(endRect.x/rectLenWidSize), 2));

                paintedRectangles.get(i).get(j).setHCost(10*hCost);

                //System.out.println(hCost);
            }
        }
    }
}