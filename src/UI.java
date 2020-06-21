import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;



public class UI 
{
    JFrame mainFrame = new JFrame("Pathfinding Visualization");;
    JPanel mainPanel = new JPanel();
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

    GridBagConstraints constraints = new GridBagConstraints();

    LinkedList<PathJButton>[] connections;

    /*volatile*/ Boolean isMousePressed = false;


    public UI(int mRows, int mCols)
    {
        rows = mRows;
        cols = mCols;

        
        {
        mainPanel.setLayout(new GridBagLayout());
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
        
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.add(settingsPanel, BorderLayout.NORTH);

       
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        mainPanel.addMouseListener(new MouseListener()
        {
        public void mousePressed(MouseEvent e)
        {
            System.out.println("I am pressed");
            isMousePressed = true;
        }

        public void mouseReleased(MouseEvent e)
        {
            isMousePressed =false;    
        }

        public void mouseEntered(MouseEvent e){}           
        public void mouseExited(MouseEvent e){}
        public void mouseClicked(MouseEvent e){}
        });
        

        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {
                constraints.gridx = row;
                constraints.gridy = col;

                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(28,28));
                mainPanel.add(btn, constraints);
            }
        }

        mainFrame.pack();

        publishBTN.addActionListener(
        new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                SwingUtilities.invokeLater(runRedraw);
        
            }

            Runnable runRedraw = () -> redrawArea(Integer.valueOf((Integer) colSpinner.getValue()), (Integer) rowSpinner.getValue());

        });
    
        mainFrame.setVisible(true);

    }

    private void redrawArea(int mRows, int mCols)
    {
        int rowColAvg = (mRows + mCols) / 2;
        int btnLenWidSize;

        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();

        btnLenWidSize = (int) (-13.2306 * (Math.log(0.0023*rowColAvg)));

        for(int row = 0; row < mRows; row++)
        {
            for(int col = 0; col < mCols; col++)
            {
                constraints.gridx = row;
                constraints.gridy = col;

                PathJButton btn = new PathJButton(new BTNCoordinates(row, col));
                btn.setPreferredSize(new Dimension(btnLenWidSize, btnLenWidSize));
                mainPanel.add(btn, constraints);
                

                btn.addMouseListener(new MouseListener()
                {
                    public void mousePressed(MouseEvent e){}
                    public void mouseReleased(MouseEvent e){}

                    public void mouseEntered(MouseEvent e)
                    {
                        System.out.println("Mouse Entered");
                        if(isMousePressed && declareWallsBOX.isSelected())
                        {
                            btn.makeWall();
                            System.out.println("Should Make wall");     
                        }
                        else if(isMousePressed && declareStartBOX.isSelected())
                        {
                            btn.makeStart();
                        }
                        else if(isMousePressed && declareEndBOX.isSelected())
                        {
                            btn.makeEnd();
                        }
                    }       

                    public void mouseExited(MouseEvent e){}
                    public void mouseClicked(MouseEvent e){}
                });

            
            }
        }


    }

}