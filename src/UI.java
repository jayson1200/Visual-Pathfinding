import javax.swing.*;
import java.awt.*;
import java.awt.event.*;




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


    public UI(int mRows, int mCols)
    {
        rows = mRows;
        cols = mCols;

        

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
        int largestRowColSize = 0;
        int btnLenWidSize;

        if(mRows > mCols)
        {
            largestRowColSize = mRows;
        }
        else if(mRows < mCols)
        {
            largestRowColSize = mCols;
        }
        else if(mRows == mCols)
        {
            largestRowColSize = mCols;
        }

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

                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(btnLenWidSize, btnLenWidSize));
                mainPanel.add(btn, constraints);
            }
        }


    }
    
}