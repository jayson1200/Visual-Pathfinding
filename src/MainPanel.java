import javax.swing.*;
import javax.swing.plaf.TreeUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {

    static ArrayList<ArrayList<PathRectangle>> paintedRectangles = new ArrayList<>();

    static int rectLenWidSize;

    static public PathRectangle startRect;
    static public PathRectangle endRect;

    Timer paintTime = new Timer(UI.speedSlider.getValue(), this);

    public MainPanel(ArrayList<ArrayList<PathRectangle>> mPathRectangles) {
        paintedRectangles = mPathRectangles;

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                for (int i = 0; i < paintedRectangles.size(); i++) {
                    for (int j = 0; j < paintedRectangles.get(0).size(); j++) {
                        if (paintedRectangles.get(i).get(j).contains(e.getPoint())) {
                            if (UI.declareWallsBOX.isSelected()) {
                                paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.WALL);
                            } else if (UI.declareStartBOX.isSelected()) {
                                checkResetStart();
                                paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.STARTNODE);
                                startRect = paintedRectangles.get(i).get(j);
                            } else if (UI.declareEndBOX.isSelected()) {
                                checkResetEnd();
                                paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.ENDNODE);
                                endRect = paintedRectangles.get(i).get(j);
                            } else if (UI.deselectBOX.isSelected()) {
                                paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.UNDECLARED);
                            }

                        }
                    }

                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {

                for (int i = 0; i < paintedRectangles.size(); i++) {
                    for (int j = 0; j < paintedRectangles.get(0).size(); j++) {
                        if (paintedRectangles.get(i).get(j).contains(e.getPoint())) {
                            if (UI.declareWallsBOX.isSelected()) {
                                paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.WALL);
                            } else if (UI.declareStartBOX.isSelected()) {
                                checkResetStart();
                                paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.STARTNODE);

                                startRect = paintedRectangles.get(i).get(j);
                            } else if (UI.declareEndBOX.isSelected()) {
                                checkResetEnd();
                                paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.ENDNODE);
                                endRect = paintedRectangles.get(i).get(j);
                            } else if (UI.deselectBOX.isSelected()) {
                                paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.UNDECLARED);
                            }
                        }
                    }

                }
                repaint();
            }
        });

        rectLenWidSize = (int) (-13.2306
                * (Math.log(0.0023 * ((paintedRectangles.size() + paintedRectangles.get(0).size()) / 2))));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        if(UI.run)
        {
            findPath();
        }

        if(shouldRunDrawPath && !UI.run)
        {
            drawPath();
        
        }

        for (int i = 0; i < paintedRectangles.size(); i++) {
            for (int j = 0; j < paintedRectangles.get(0).size(); j++) {

                switch (paintedRectangles.get(i).get(j).getRecVal()) {
                    case UNDECLARED:
                        g2D.drawRect(i * rectLenWidSize, j * rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                    case WALL:
                        g2D.setColor(Color.BLACK);
                        g2D.fillRect(i * rectLenWidSize, j * rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                    case STARTNODE:
                        g2D.setColor(Color.GREEN);
                        g2D.fillRect(i * rectLenWidSize, j * rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                    case ENDNODE:
                        g2D.setColor(Color.RED);
                        g2D.fillRect(i * rectLenWidSize, j * rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                    case OPEN:
                        g2D.setColor(Color.BLUE);
                        g2D.fillRect(i * rectLenWidSize, j * rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;

                    case CLOSED:
                        g2D.setColor(Color.GRAY);
                        g2D.fillRect(i * rectLenWidSize, j * rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;
                    case PATH:
                        g2D.setColor(Color.YELLOW);
                        g2D.fillRect(i * rectLenWidSize, j * rectLenWidSize, rectLenWidSize, rectLenWidSize);
                        break;
                }
                g2D.setColor(Color.BLACK);
            }
        }

        paintTime.setDelay(UI.speedSlider.getValue());
        paintTime.start();
    }

    public void checkResetStart() {
        startRect = null;

        for (int i = 0; i < paintedRectangles.size(); i++) {
            for (int j = 0; j < paintedRectangles.get(0).size(); j++) {
                if (paintedRectangles.get(i).get(j).getRecVal() == PathRectangle.recType.STARTNODE) {
                    paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.UNDECLARED);
                }
            }
        }

    }

    public void checkResetEnd() {
        endRect = null;

        for (int i = 0; i < paintedRectangles.size(); i++) {
            for (int j = 0; j < paintedRectangles.get(0).size(); j++) {
                if (paintedRectangles.get(i).get(j).getRecVal() == PathRectangle.recType.ENDNODE) {
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

    ArrayList<PathRectangle> openRects = new ArrayList<>();
    ArrayList<PathRectangle> closedRects = new ArrayList<>();
    ArrayList<PathRectangle> tempOpenRects = new ArrayList<>();
    PathRectangle currentNode;
    int smlFCostIndex;

    public void initFindPath() {
        PathRectangle currentNode = startRect;

        openRects = new ArrayList<>();
        closedRects = new ArrayList<>();
        tempOpenRects = new ArrayList<>();

        assignHCost();

        paintedRectangles.get(startRect.x / rectLenWidSize)
                .get(startRect.y / rectLenWidSize).recVal = PathRectangle.recType.OPEN;
        openRects.add(paintedRectangles.get(startRect.x / rectLenWidSize).get(startRect.y / rectLenWidSize));
        paintedRectangles.get(startRect.x / rectLenWidSize).get(startRect.y / rectLenWidSize).setGCost(0);
        paintedRectangles.get(startRect.x / rectLenWidSize).get(startRect.y / rectLenWidSize)
                .setRectangleParent(currentNode);
    }

    public void findSmlFCostRect() {
        smlFCostIndex = 0;
        PathRectangle smlFCostRec = new PathRectangle(0, 0, 0, 0);

        smlFCostRec.setGCost(Double.MAX_VALUE);
        smlFCostRec.setHCost(Double.MAX_VALUE);

        for (int i = 0; i < openRects.size(); i++) {

            if (openRects.get(i).getFCost() < smlFCostRec.getFCost()) {
                currentNode = openRects.get(i);
                smlFCostIndex = i;
                smlFCostRec = currentNode;
            }
        }
    }

    public void addSmlFCostRectToOpenClose() {
        openRects.get(smlFCostIndex).setRecVal(PathRectangle.recType.CLOSED);
        closedRects.add(openRects.get(smlFCostIndex));
    }

    public void findSurroundingRects() {
        if (((openRects.get(smlFCostIndex).x / rectLenWidSize) + 1 < paintedRectangles.size())
                && paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize) + 1)
                        .get(openRects.get(smlFCostIndex).y / rectLenWidSize).getRecVal() != PathRectangle.recType.WALL
                && paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize) + 1)
                        .get(openRects.get(smlFCostIndex).y / rectLenWidSize)
                        .getRecVal() != PathRectangle.recType.CLOSED) {
            tempOpenRects.add(paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize) + 1)
                    .get(openRects.get(smlFCostIndex).y / rectLenWidSize));
        }

        if (((openRects.get(smlFCostIndex).x / rectLenWidSize) - 1 >= 0
                && paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize) - 1)
                        .get(openRects.get(smlFCostIndex).y / rectLenWidSize).getRecVal() != PathRectangle.recType.WALL
                && paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize) - 1)
                        .get(openRects.get(smlFCostIndex).y / rectLenWidSize)
                        .getRecVal() != PathRectangle.recType.CLOSED)) {
            tempOpenRects.add(paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize) - 1)
                    .get(openRects.get(smlFCostIndex).y / rectLenWidSize));
        }

        if (((openRects.get(smlFCostIndex).y / rectLenWidSize) + 1 < paintedRectangles.get(0).size()
                && paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize))
                        .get((openRects.get(smlFCostIndex).y / rectLenWidSize) + 1)
                        .getRecVal() != PathRectangle.recType.WALL
                && paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize))
                        .get((openRects.get(smlFCostIndex).y / rectLenWidSize) + 1)
                        .getRecVal() != PathRectangle.recType.CLOSED)) {
            tempOpenRects.add(paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize))
                    .get((openRects.get(smlFCostIndex).y / rectLenWidSize) + 1));
        }

        if (((openRects.get(smlFCostIndex).y / rectLenWidSize) - 1 >= 0
                && paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize))
                        .get((openRects.get(smlFCostIndex).y / rectLenWidSize) - 1)
                        .getRecVal() != PathRectangle.recType.WALL
                && paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize))
                        .get((openRects.get(smlFCostIndex).y / rectLenWidSize) - 1)
                        .getRecVal() != PathRectangle.recType.CLOSED)) {
            tempOpenRects.add(paintedRectangles.get((openRects.get(smlFCostIndex).x / rectLenWidSize))
                    .get((openRects.get(smlFCostIndex).y / rectLenWidSize) - 1));
        }
    }

    public void setTempOpenRectGCost() {
        for (int i = 0; i < tempOpenRects.size(); i++) {
            tempOpenRects.get(i).setRectangleParent(currentNode);
            tempOpenRects.get(i).setGCost(Math.abs(tempOpenRects.get(i).getRectangleParent().getGCost() + Math.sqrt(Math
                    .pow((double) (currentNode.x / rectLenWidSize) - (tempOpenRects.get(i).x / rectLenWidSize), 2)
                    + Math.pow((double) (currentNode.y / rectLenWidSize) - (tempOpenRects.get(i).y / rectLenWidSize),
                            2))));
        }

        for (int i = 0; i < tempOpenRects.size(); i++) {
            for (int j = 0; j < openRects.size(); j++) {
                if ((openRects.get(j).x / rectLenWidSize) == (tempOpenRects.get(i).x / rectLenWidSize)
                        && (openRects.get(j).y / rectLenWidSize) == (tempOpenRects.get(i).y / rectLenWidSize)) {
                    double gCostComparable = Math.abs(openRects.get(smlFCostIndex).getGCost() + Math.sqrt(Math
                            .pow((double) (currentNode.x / rectLenWidSize) - (openRects.get(j).x / rectLenWidSize), 2)
                            + Math.pow(
                                    (double) (currentNode.y / rectLenWidSize) - (openRects.get(j).y / rectLenWidSize),
                                    2)));

                    if (gCostComparable < tempOpenRects.get(i).getGCost()) {
                        tempOpenRects.get(i).setRectangleParent(openRects.get(smlFCostIndex));
                        tempOpenRects.get(i).setGCost(gCostComparable);
                        openRects.remove(j);
                    } else {
                        tempOpenRects.remove(i);
                    }
                    break;
                }
            }
        }
    }

    public void removeCurrentNodeFromOpenList() {
        for (int i = 0; i < openRects.size(); i++) {
            if (openRects.get(i) == currentNode) {
                openRects.remove(i);
            }
        }
    }

    public void addTempOpenListToOpenList() {
        for (int i = 0; i < tempOpenRects.size(); i++) {
            tempOpenRects.get(i).setRecVal(PathRectangle.recType.OPEN);
            openRects.add(tempOpenRects.get(i));
        }
    }

    public boolean initReverseCurrentNode = true;
    PathRectangle reverseCurrentNode;

    public void drawPath()
    {
        if(initReverseCurrentNode)
        {
            reverseCurrentNode = endRect;

            initReverseCurrentNode = false;
        }

            paintedRectangles.get(reverseCurrentNode.x / rectLenWidSize).get(reverseCurrentNode.y / rectLenWidSize)
                    .setRecVal(PathRectangle.recType.PATH);

            reverseCurrentNode = paintedRectangles.get(reverseCurrentNode.x / rectLenWidSize)
                    .get(reverseCurrentNode.y / rectLenWidSize).getRectangleParent();

        if(reverseCurrentNode == startRect)
        {
            paintedRectangles.get(startRect.x / rectLenWidSize).get(startRect.y / rectLenWidSize).setRecVal(PathRectangle.recType.PATH);
            shouldRunDrawPath = false;
        }

    }


    public boolean shouldInit = true;
    public boolean shouldRunDrawPath = false;

    public void findPath() {

        if(shouldInit)
        {
            initFindPath();
            shouldInit = false;
        }

        if(currentNode == endRect)
        {
            UI.run = false;
        }

            if (openRects.size() == 0) {
                System.out.println("No Path");
                UI.run = false;
            }

            findSmlFCostRect();

            if (currentNode == endRect) {
                System.out.println("Found Path");
                closedRects.add(currentNode);
                shouldRunDrawPath = true;
            }

            addSmlFCostRectToOpenClose();


            findSurroundingRects();

            setTempOpenRectGCost();

            removeCurrentNodeFromOpenList();

            addTempOpenListToOpenList();

            tempOpenRects = new ArrayList<>();

    }

    public void assignHCost() {
        for (int i = 0; i < paintedRectangles.size(); i++) {
            for (int j = 0; j < paintedRectangles.get(0).size(); j++) {
                double hCost = Math.abs(Math.sqrt(Math.pow((double) i - (endRect.x / rectLenWidSize), 2)
                        + Math.pow((double) j - (endRect.y / rectLenWidSize), 2)));

                paintedRectangles.get(i).get(j).setHCost(hCost * 10);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        repaint();
    }
}