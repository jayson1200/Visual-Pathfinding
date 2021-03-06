import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;



public class UI 
{
    static JFrame mainFrame = new JFrame("Pathfinding Visualization");;
    static boolean run = false;

    ArrayList<ArrayList<PathRectangle>> initList = new ArrayList<ArrayList<PathRectangle>>();
    MainPanel mainPanel;

    JPanel settingsPanel = new JPanel();

    SpinnerNumberModel colSpinnerModel = new SpinnerNumberModel(10, 5, null,1); 
    SpinnerNumberModel rowSpinnerModel = new SpinnerNumberModel(10, 5, null,1);
    JSpinner colSpinner = new JSpinner(colSpinnerModel);
    JSpinner rowSpinner = new JSpinner(rowSpinnerModel);
    JButton publishBTN = new JButton("Publish"); 

    JButton startBTN = new JButton("Start");
    JButton stopBTN = new JButton("Stop");
    JButton clearBTN = new JButton("Clear");

    static JSlider speedSlider = new JSlider(0, 1000);

    JLabel speedLabel = new JLabel("Speed:");
    JLabel rowsLabel = new JLabel("Rows:");
    JLabel colsLabel = new JLabel("Columns:");

    static JCheckBox declareStartBOX = new JCheckBox("Declare Start");
    static JCheckBox declareEndBOX = new JCheckBox("Declare End");
    static JCheckBox declareWallsBOX = new JCheckBox("Declare Walls");
    static JCheckBox deselectBOX = new JCheckBox("Deselect");

    int rows = 10, cols = 10;

    GroupLayout optionsLayout = new GroupLayout(settingsPanel);

    public UI()
    {

        for(int i = 0; i < 10; i++)
        {
            ArrayList<PathRectangle> initColList = new ArrayList<PathRectangle>();

            for(int j = 0; j < 10; j++)
            {
                initColList.add(new PathRectangle(i*49, j*49, 49, 49));
            }

            initList.add(initColList);
        }
        
        
        mainPanel = new MainPanel(initList);
        mainFrame.add(mainPanel);
        
        {
       
        settingsPanel.setLayout(optionsLayout);

        optionsLayout.setAutoCreateGaps(true);
        optionsLayout.setAutoCreateContainerGaps(true);

        
        optionsLayout.setHorizontalGroup(
            optionsLayout.createSequentialGroup()
            .addGroup(
                optionsLayout.createSequentialGroup()
                .addGroup(
                    optionsLayout.createParallelGroup()
                    .addComponent(speedLabel)
                    .addComponent(speedSlider)
                    .addGroup(
                        optionsLayout.createSequentialGroup()
                        .addComponent(rowsLabel)
                        .addComponent(rowSpinner)
                        .addComponent(colsLabel)
                        .addComponent(colSpinner)
                        .addComponent(publishBTN)
                        .addComponent(declareStartBOX)
                        .addComponent(declareEndBOX)
                        .addComponent(declareWallsBOX)
                        .addComponent(deselectBOX)))

                       
                        .addComponent(startBTN)
                        .addComponent(stopBTN)
                        .addComponent(clearBTN))
                    
        );

        optionsLayout.setVerticalGroup(optionsLayout.createSequentialGroup()
            .addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(speedLabel)
            .addComponent(speedSlider)
            .addComponent(startBTN)
            .addComponent(stopBTN)
            .addComponent(clearBTN))

            .addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(rowsLabel)
            .addComponent(colSpinner)
            .addComponent(colsLabel)
            .addComponent(rowSpinner)
            .addComponent(publishBTN)
            .addComponent(declareStartBOX)
            .addComponent(declareEndBOX)
            .addComponent(declareWallsBOX)
            .addComponent(deselectBOX))
        );
        
        mainFrame.add(settingsPanel, BorderLayout.NORTH);
        }
        
    
        mainPanel.addMouseWheelListener(new MouseAdapter()
        {
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                mainPanel.rectLenWidSize -= e.getPreciseWheelRotation() * 1;
                resizeRectangles(mainPanel.rectLenWidSize);


                mainPanel.repaint();
                mainFrame.revalidate();
            }

        }
        );
        
        declareWallsBOX.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 declareStartBOX.setSelected(false);
                 declareEndBOX.setSelected(false); 
                 deselectBOX.setSelected(false);        
            }       
        });

        declareStartBOX.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 declareWallsBOX.setSelected(false);
                 declareEndBOX.setSelected(false);   
                 deselectBOX.setSelected(false);          
            }       
        });

        declareEndBOX.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 declareStartBOX.setSelected(false);
                 declareWallsBOX.setSelected(false);   
                 deselectBOX.setSelected(false);       
            }       
        });

        deselectBOX.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 declareStartBOX.setSelected(false);
                 declareEndBOX.setSelected(false); 
                 declareWallsBOX.setSelected(false);        
            }       
        });

        publishBTN.addActionListener(
        new ActionListener()
        {       
        
            public void actionPerformed(ActionEvent e)
            {
                mainPanel.endRect = null;
                mainPanel.startRect = null;
                mainPanel.initReverseCurrentNode = true;
                mainPanel.shouldInit = true;
                mainPanel.shouldRunDrawPath = false;

                SwingUtilities.invokeLater(runRedraw);
            }
    
                Runnable runRedraw = () -> redrawArea(Integer.valueOf((Integer) colSpinner.getValue()), (Integer) rowSpinner.getValue());
    
            });

        startBTN.addActionListener(
            new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                run = true;
                mainPanel.findPath();
            }
        });

        stopBTN.addActionListener(
            new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                run = false;
            }
        });

        clearBTN.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.initReverseCurrentNode = true;
                    mainPanel.shouldInit = true;
                    mainPanel.shouldRunDrawPath = false;

                    MainPanel.paintedRectangles.get(mainPanel.startRect.x / mainPanel.rectLenWidSize).get(mainPanel.startRect.y / mainPanel.rectLenWidSize)
                    .setRecVal(PathRectangle.recType.STARTNODE);

                    MainPanel.paintedRectangles.get(mainPanel.endRect.x / mainPanel.rectLenWidSize).get(mainPanel.endRect.y / mainPanel.rectLenWidSize)
                    .setRecVal(PathRectangle.recType.ENDNODE);
                    
                    for (int i = 0; i < MainPanel.paintedRectangles.size(); i++) 
                    {
                        for (int j = 0; j < MainPanel.paintedRectangles.get(0).size(); j++) 
                        {
                            if(MainPanel.paintedRectangles.get(i).get(j).getRecVal() == PathRectangle.recType.OPEN ||
                            MainPanel.paintedRectangles.get(i).get(j).getRecVal() == PathRectangle.recType.CLOSED ||
                            MainPanel.paintedRectangles.get(i).get(j).getRecVal() == PathRectangle.recType.PATH && 
                            MainPanel.paintedRectangles.get(i).get(j) != mainPanel.startRect &&
                            MainPanel.paintedRectangles.get(i).get(j) != mainPanel.endRect)
                            {
                                MainPanel.paintedRectangles.get(i).get(j).setRecVal(PathRectangle.recType.UNDECLARED);
                            }
                            
                        }
    
                    }
                }

            }
        );

        declareWallsBOX.setSelected(true);;
        mainFrame.pack();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 1000);
        mainFrame.setVisible(true);

    }

    
    
    private void redrawArea(int rows, int cols)
    {
        int calculatedSize = (int) (-13.2306 * (Math.log(0.0023*( (rows + cols) / 2) )));
      

        ArrayList<ArrayList<PathRectangle>> gridList = new ArrayList<ArrayList<PathRectangle>>();

        for(int i = 0; i < rows; i++)
        {
            ArrayList<PathRectangle> gridColList = new ArrayList<PathRectangle>();

            for(int j = 0; j < cols; j++)
            {
                gridColList.add(new PathRectangle(i*calculatedSize, j*calculatedSize, calculatedSize, calculatedSize));
            }

            gridList.add(gridColList);
        }

        mainFrame.remove(mainPanel);
        mainPanel = new MainPanel(gridList);

        mainPanel.addMouseWheelListener(new MouseAdapter()
        {
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                mainPanel.rectLenWidSize -= e.getPreciseWheelRotation() * 1;
                resizeRectangles(mainPanel.rectLenWidSize);

                mainPanel.repaint();
                mainFrame.revalidate();
            }

        }
        );

        mainFrame.add(mainPanel);
        mainFrame.revalidate();
    }

    public void resizeRectangles(int size)
    {
        for(int i = 0; i < mainPanel.paintedRectangles.size(); i++)
        {
                for(int j = 0; j < mainPanel.paintedRectangles.get(0).size(); j++)
                {
                        mainPanel.paintedRectangles.get(i).get(j).setBounds(i* size, j * size, size, size);;
                }
            }
    
        }
    }

