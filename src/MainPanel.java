import javax.swing.*;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class MainPanel extends JPanel {

    private ArrayList<PathRectangle> paintedRectangles = new ArrayList<>();

    int rows = 0, cols = 0;
    int rectLenWidSize;


    public MainPanel(int mRows, int mCols, ArrayList<PathRectangle> mPathRectangles) 
    {
        rows = mRows;
        cols = mCols;
        paintedRectangles = mPathRectangles;

        addMouseListener(new MouseAdapter()
        {
           
            @Override
            public void mousePressed(MouseEvent e) {
                
                for(int i = 0; i < paintedRectangles.size(); i++)
                    {
                        if(paintedRectangles.get(i).contains(e.getPoint()))
                        {
                            //This will have to change in the future just testing it right now
                            paintedRectangles.get(i).setRecVal(PathRectangle.recType.WALL);
                            
                            
                            repaint();
                        }
                    }
            }
        });

        addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                   //System.out.println(e.getPoint());
                    for(int i = 0; i < paintedRectangles.size(); i++)
                    {
                        if(paintedRectangles.get(i).contains(e.getPoint()))
                        {
                            //This will have to change in the future just testing it right now
                            paintedRectangles.get(i).setRecVal(PathRectangle.recType.WALL);
                            
                            
                            repaint();
                        }
                    }
            }
        });

        
        
        rectLenWidSize = (int) (-13.2306 * (Math.log(0.0023*((mRows + mCols) / 2))));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2D = (Graphics2D) g;

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                
                switch(paintedRectangles.get(i).getRecVal())
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
                }
            }
        }
    }


}