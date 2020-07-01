import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;



public class UI 
{
    JFrame mainFrame = new JFrame("Pathfinding Visualization");;

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

    JSlider speedSlider = new JSlider(1, 100);

    JLabel speedLabel = new JLabel("Speed:");
    JLabel rowsLabel = new JLabel("Rows:");
    JLabel colsLabel = new JLabel("Columns:");

    static JCheckBox declareStartBOX = new JCheckBox("Declare Start");
    static JCheckBox declareEndBOX = new JCheckBox("Declare End");
    static JCheckBox declareWallsBOX = new JCheckBox("Declare Walls");
    static JCheckBox deselectBOX = new JCheckBox("Deselect");

    int rows = 10, cols = 10;

    GroupLayout optionsLayout = new GroupLayout(settingsPanel);

    LinkedList<PathRectangle>[] connections;

    


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
        
        //mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.add(settingsPanel, BorderLayout.NORTH);
        }
        
        
        
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
                    SwingUtilities.invokeLater(runRedraw);
            
                }
    
                Runnable runRedraw = () -> redrawArea(Integer.valueOf((Integer) colSpinner.getValue()), (Integer) rowSpinner.getValue());
    
            });

        declareWallsBOX.setSelected(true);;
        mainFrame.pack();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 1000);
        mainFrame.setVisible(true);

    }


    
    private void redrawArea(int rows, int cols)
    {
        //mainPanel.removeAll();
        //mainPanel.revalidate();
        //mainPanel.repaint();

        int calculatedSize = (int) (-13.2306 * (Math.log(0.0023*( (rows + cols) / 2) )));
        int mainFrameSize  = ((rows * cols) * calculatedSize) + 4500;

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
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setSize(mainFrameSize, mainFrameSize);
    }

}