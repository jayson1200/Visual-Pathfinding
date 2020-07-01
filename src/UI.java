import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;



public class UI 
{
    JFrame mainFrame = new JFrame("Pathfinding Visualization");;

    ArrayList<PathRectangle> initList = new ArrayList<PathRectangle>();
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

    JCheckBox declareStartBOX = new JCheckBox("Declare Start");
    JCheckBox declareEndBOX = new JCheckBox("Declare End");
    JCheckBox declareWallsBOX = new JCheckBox("Declare Walls");

    int rows = 10, cols = 10;

    GroupLayout optionsLayout = new GroupLayout(settingsPanel);

    LinkedList<PathJButton>[] connections;

    


    public UI()
    {

        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; i < 10; i++)
            {
                initList.add(new PathRectangle(i*49, j*49, 49, 49));
            }
        }
        
        mainPanel = new MainPanel(10, 10, initList);
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
                        .addComponent(declareWallsBOX)))

                       
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
            .addComponent(declareWallsBOX))
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
                 System.out.println("Declare Check clicked");         
            }       
        });

        declareStartBOX.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 declareWallsBOX.setSelected(false);
                 declareEndBOX.setSelected(false);          
            }       
        });

        declareEndBOX.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 declareStartBOX.setSelected(false);
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


        mainFrame.pack();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }


    
    private void redrawArea(int mRows, int mCols)
    {
        //mainPanel.removeAll();
        //mainPanel.revalidate();
        //mainPanel.repaint();

    }

}