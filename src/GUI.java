import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.math.BigDecimal;


public class GUI  {
	
	private final Dimension DEFAULT_DIM;
	private final JFrame frame;
	private JPanel pane;
	private JPanel matrixEnterPane;
	private JPanel optionsDisplayPane;
	private JPanel scalarEROPane;
	private JPanel swapEROPane;
	private JPanel addEROPane;
	private GridBagConstraints c;
	private int rowsM;
	private int colsN;
	private JFormattedTextField sizeInputBoxRows;
	private JFormattedTextField sizeInputBoxCols;
	private JFormattedTextField[][] initMatrix;
	private Matrix matrix01;
	private DecimalFormat decForm;
	private JFormattedTextField rowInputBox;
	private JFormattedTextField scalarInputBox;
	private JFormattedTextField row2InputBox;
	private JFormattedTextField row1InputBox;
	private JFormattedTextField scalarADDOPInputBox;
	private JFormattedTextField row2ADDOPInputBox;
	private JFormattedTextField row1ADDOPInputBox;
	private boolean okToContinue;
	private String ERROR;

	

	public GUI()///////////////
	{
		DEFAULT_DIM = new Dimension(400,300);
		frame = new JFrame();
		pane = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		rowsM = 0;
		colsN = 0;
		ERROR = "";
		
		start();
	}
	
	public void start() ////////////////////////////////////////////////////////
	{

		frame.setTitle("Matrix Calculator");
		frame.setSize(DEFAULT_DIM);
		//frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(pane);
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		
		JLabel label = new JLabel("Super Simple Matrix Calculator!");
		c.gridx = 1;
		c.gridy = 0;
		pane.add(label, c);
		
		JLabel matrixLabel = new JLabel("Matrix:");
		c.gridx = 0;
		c.gridy = 2;
		pane.add(matrixLabel, c);
		
		JLabel rowsLabel = new JLabel("Rows");
		c.gridx = 1;
		c.gridy = 1;
		pane.add(rowsLabel, c);
		
		JLabel colsLabel = new JLabel("Columns");
		c.gridx = 2;
		c.gridy = 1;
		pane.add(colsLabel, c);
		
		
		
		
		
		decForm = new DecimalFormat();
		sizeInputBoxRows = new JFormattedTextField(decForm);
		sizeInputBoxRows.setPreferredSize(new Dimension(50,20));
		sizeInputBoxRows.setVisible(true);
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(sizeInputBoxRows, c);
		
		sizeInputBoxCols = new JFormattedTextField(decForm);
		sizeInputBoxCols.setPreferredSize(new Dimension(50,20));
		sizeInputBoxCols.setVisible(true);
		c.gridx = 2;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(sizeInputBoxCols, c);
		
		
		
		
		
		
		JButton button1 = new JButton("ENGAGE");
		button1.setVisible(true);
		
		button1.addActionListener(new ActionListener() { //***************************
			 
	            public void actionPerformed(ActionEvent e)
	            {
	            	//pane.setVisible(false);
	            	matrixEnter();
	            }
	        }); 
		
		c.gridx = 1;
		c.gridy = 3;
		pane.add(button1, c);
		
		pane.setVisible(true);
		frame.setVisible(true);
	}
	
	public void matrixEnter()//////////////
	{
		pane.setVisible(false);
		frame.remove(pane);
		matrixEnterPane = new JPanel(new GridBagLayout());
		
		try {
			sizeInputBoxRows.commitEdit();
		} catch (ParseException e) {
			sizeInputBoxRows.setValue(1);
		}
		rowsM = Integer.parseInt(sizeInputBoxRows.getValue().toString());
		
		
		try {
			sizeInputBoxCols.commitEdit();
		} catch (ParseException e) {
			sizeInputBoxCols.setValue(1);
		}
		colsN = Integer.parseInt(sizeInputBoxCols.getValue().toString());
		
		DecimalFormat decForm1 = new DecimalFormat();
		
		
		initMatrix = new JFormattedTextField[rowsM][colsN];
		
		for(int m = 0; m < rowsM; m++)
		{
			for(int n = 0; n < colsN; n++)
			{
				JFormattedTextField matrixEnterBox = new JFormattedTextField(decForm1);
				matrixEnterBox.setPreferredSize(new Dimension(50,20));
				matrixEnterBox.setVisible(true);
				c.gridx = n;
				c.gridy = m + 1;
				c.weightx = 1;
				c.weighty = 1;
				c.gridheight = 1;
				c.gridwidth = 1;
				
				initMatrix[m][n] = matrixEnterBox;
				matrixEnterPane.add(initMatrix[m][n], c);
			}
		}
		
		
		JLabel label = new JLabel("Enter Your Matrix. If a box is left empty it will be set to ZERO");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = colsN;
		matrixEnterPane.add(label, c);
		
		
		
		JButton enterMatrixButton = new JButton("Enter Matrix");
		c.gridx = 0;
		c.gridy = rowsM + 1;
		enterMatrixButton.setVisible(true);
		matrixEnterPane.add(enterMatrixButton,c);
		
		enterMatrixButton.addActionListener(new ActionListener() { //****************
			 
            public void actionPerformed(ActionEvent e)
            {
            	
            	matrixInit();
            	matrixOptions();
            }
        }); 
		
		
		matrixEnterPane.setVisible(true);
		frame.add(matrixEnterPane);
	}
	
	public void matrixInit()
	{
		matrix01 = new Matrix(rowsM, colsN);
		
		for(int m = 0; m < rowsM; m++)
		{
			for(int n = 0; n < colsN; n++)
			{
				try 
				{
					initMatrix[m][n].commitEdit();
				} 
				catch (ParseException e)
				{
					initMatrix[m][n].setValue(0);
				}
				matrix01.setEntry(m,n,new BigDecimal(Double.parseDouble(initMatrix[m][n].getValue().toString())));
			}
		}
	}
	
	public void matrixOptions() // Displays matrix and asks for options, 
												  //parameter is for an error message to display at bottom of matrix
	{
		matrixEnterPane.setVisible(false);
		frame.remove(matrixEnterPane);
		optionsDisplayPane = new JPanel(new GridBagLayout());
		GridBagConstraints d = new GridBagConstraints();
		
		d.gridx = 0;
		d.gridy = 0;
		d.weightx = 1;
		d.weighty = 1;
		d.gridheight = 1;
		d.gridwidth = 1;
		
		for(int m = 0; m < rowsM; m++)
		{
			for(int n = 0; n < colsN; n++)
			{
				d.gridx = n;
				d.gridy = m + 1;
				JLabel displayedMatrix = new JLabel(matrix01.getEntry(m, n).toString());
				optionsDisplayPane.add(displayedMatrix, d);
			}
		}
		
		optionsDisplayPane.setVisible(true);
		
		
		
		
		
		
		JButton ERO1Button = new JButton("Scalar ERO");
		d.gridx = colsN + 1;
		d.gridy = 1;
		optionsDisplayPane.add(ERO1Button, d);
		
		
		ERO1Button.addActionListener(new ActionListener() { //****************
			 
            public void actionPerformed(ActionEvent e)
            {
            	ERO1ButtonMethod();
            }
        }); 
		
		
		JButton ERO2Button = new JButton("Row Swap ERO");
		d.gridx = colsN + 1;
		d.gridy = 2;
		optionsDisplayPane.add(ERO2Button, d);
		
		
		ERO2Button.addActionListener(new ActionListener() { //****************
			 
            public void actionPerformed(ActionEvent e)
            {
            	okToContinue = true;
            	ERO2ButtonMethod();	
            }
        }); 
		
		JButton ERO3Button = new JButton("Scalar Add ERO");
		d.gridx = colsN + 1;
		d.gridy = 3;
		optionsDisplayPane.add(ERO3Button, d);
		
		ERO3Button.addActionListener(new ActionListener() { //****************
			 
            public void actionPerformed(ActionEvent e)
            {
            	okToContinue = true;
            	ERO3ButtonMethod();	
            }
        }); 
		
		
		JButton RREFButton = new JButton("RREF");
		d.gridx = colsN + 1;
		d.gridy = 4;
		optionsDisplayPane.add(RREFButton, d);
		
		RREFButton.addActionListener(new ActionListener() { //****************
			 
            public void actionPerformed(ActionEvent e)
            {
            	optionsDisplayPane.setVisible(false);
            	RREFButtonPressed();	
            }
        }); 
		
		
		
		JLabel errorOption = new JLabel(ERROR);
		errorOption.setForeground(Color.red);
		d.gridwidth = colsN;
		d.gridx = 0;
		d.gridy = rowsM + 1;
		optionsDisplayPane.add(errorOption,d);
		ERROR = "";
		
		frame.add(optionsDisplayPane);
	}
	
	
	public void ERO1ButtonMethod()
	{
		optionsDisplayPane.setVisible(false);
		frame.remove(optionsDisplayPane);
		
		scalarEROPane = new JPanel(new GridBagLayout());
		scalarEROPane.setVisible(true);
		
		GridBagConstraints g = new GridBagConstraints();
		g.weightx = 1;
		g.weighty = 1;
		
		
		g.gridwidth = 2;
		g.gridx = 0;
		g.gridy = 0;
		JLabel scalarEROTitle = new JLabel("Operation will not occur if invalid row #");
		scalarEROPane.add(scalarEROTitle,g);
		g.gridwidth = 1;
		
		
		g.gridx = 1;
		g.gridy = 1;
		rowInputBox = new JFormattedTextField(decForm);
		rowInputBox.setPreferredSize(new Dimension(50,20));
		rowInputBox.setVisible(true);
		scalarEROPane.add(rowInputBox, g);
		
		
		
		JLabel rowInputBoxLabel = new JLabel("Enter Row");
		g.gridx = 0;
		g.gridy = 1;
		scalarEROPane.add(rowInputBoxLabel, g);
		
		////////////////////////////////////////
		scalarInputBox = new JFormattedTextField(decForm);
		scalarInputBox.setPreferredSize(new Dimension(50,20));
		scalarInputBox.setVisible(true);
		g.gridx = 1;
		g.gridy = 2;
		scalarEROPane.add(scalarInputBox, g);
		
		
		
		JLabel scalarInputBoxLabel = new JLabel("Enter Scalar");
		g.gridx = 0;
		g.gridy = 2;
		scalarEROPane.add(scalarInputBoxLabel, g);
		
		
		JButton scalarEROEnterButton = new JButton("Enter");
		g.gridwidth = 2;
		g.gridy = 3;
		g.gridx = 0;
		scalarEROPane.add(scalarEROEnterButton,g);
		
		scalarEROEnterButton.addActionListener(new ActionListener() { //****************
			 
            public void actionPerformed(ActionEvent e)
            {
            	scalarEROEnterButtonPressed();
            	scalarEROPane.setVisible(false);
            	matrixOptions();
            }
        }); 
		
		
		
		frame.add(scalarEROPane);
	}
	
	
	public void scalarEROEnterButtonPressed()
	{
		
		
		try {
			scalarInputBox.commitEdit();
		} catch (ParseException e) {
			scalarInputBox.setValue(1);
		}
		try {
			rowInputBox.commitEdit();
		} catch (ParseException e) {
			rowInputBox.setValue(1);
		}
		
		
		int rowCommitted =Integer.parseInt(rowInputBox.getValue().toString());
		double scalarCommitted = Double.parseDouble(scalarInputBox.getValue().toString());
		
		
		if(rowCommitted > rowsM)
		{
			ERROR = "Invalid Row";
		}
		else
		{
			matrix01.rowOPScalar(rowCommitted - 1 ,new BigDecimal(scalarCommitted));
		}
		
		
		
		
	}
	
	
	public void ERO2ButtonMethod()
	{
		optionsDisplayPane.setVisible(false);
		frame.remove(optionsDisplayPane);
		
		swapEROPane = new JPanel(new GridBagLayout());
		swapEROPane.setVisible(true);
		
		GridBagConstraints g = new GridBagConstraints();
		g.weightx = 1;
		g.weighty = 1;
		
		
		g.gridwidth = 2;
		g.gridx = 0;
		g.gridy = 0;
		JLabel swapEROTitle = new JLabel("Operation will not occur if invalid row #");
		swapEROPane.add(swapEROTitle,g);
		g.gridwidth = 1;
		
		
		g.gridx = 1;
		g.gridy = 1;
		row1InputBox = new JFormattedTextField(decForm);
		row1InputBox.setPreferredSize(new Dimension(50,20));
		row1InputBox.setVisible(true);
		swapEROPane.add(row1InputBox, g);
		
		
		
		JLabel row1InputBoxLabel = new JLabel("Enter Row 1");
		g.gridx = 0;
		g.gridy = 1;
		swapEROPane.add(row1InputBoxLabel, g);
		
		////////////////////////////////////////
		row2InputBox = new JFormattedTextField(decForm);
		row2InputBox.setPreferredSize(new Dimension(50,20));
		row2InputBox.setVisible(true);
		g.gridx = 1;
		g.gridy = 2;
		swapEROPane.add(row2InputBox, g);
		
		
		
		JLabel row2InputBoxLabel = new JLabel("Enter Row 2");
		g.gridx = 0;
		g.gridy = 2;
		swapEROPane.add(row2InputBoxLabel, g);
		
		
		JButton swapEROEnterButton = new JButton("Enter");
		g.gridwidth = 2;
		g.gridy = 3;
		g.gridx = 0;
		swapEROPane.add(swapEROEnterButton,g);
		
		
		
		swapEROEnterButton.addActionListener(new ActionListener() { //****************
			 
            public void actionPerformed(ActionEvent e)
            {
            	swapEROEnterButtonPressed();
            	swapEROPane.setVisible(false);
            	matrixOptions();
            }
        }); 
		
		
		
		frame.add(swapEROPane);
	}
	
	private void swapEROEnterButtonPressed()
	{
		
		try {
			row1InputBox.commitEdit();
		} catch (ParseException e) {
			row1InputBox.setValue(1);
		}
		
		try {
			row2InputBox.commitEdit();
		} catch (ParseException e) {
			row2InputBox.setValue(1);
		}
		
		int row1Committed = 1;
		int row2Committed = 1;
		try {
			row1Committed = Integer.parseInt(row1InputBox.getValue().toString());
			row2Committed = Integer.parseInt(row2InputBox.getValue().toString());
		} catch (Exception e) {
			okToContinue = false;
		}
		
		
		if(row1Committed > rowsM || row2Committed > rowsM || !okToContinue)
		{
			ERROR ="INVALID ROW OR COLUMN";
		}
		else
		{
			matrix01.rowOPSwitch(row1Committed - 1 , row2Committed - 1);
			row1Committed = 1;
			row2Committed = 1; 
		}
		
	}
	
	public void ERO3ButtonMethod()
	{
		optionsDisplayPane.setVisible(false);
		frame.remove(optionsDisplayPane);
		
		addEROPane = new JPanel(new GridBagLayout());
		addEROPane.setVisible(true);
		
		GridBagConstraints g = new GridBagConstraints();
		g.weightx = 1;
		g.weighty = 1;
		
		//////////////////////////////////////////////////////////
		g.gridwidth = 2;
		g.gridx = 0;
		g.gridy = 0;
		JLabel addEROTitle = new JLabel("Operation will not occur if invalid row #'s");
		addEROPane.add(addEROTitle,g);
		g.gridwidth = 1;
		
		////////////////////////////////////////////////////////
		g.gridx = 1;
		g.gridy = 1;
		row1ADDOPInputBox = new JFormattedTextField(decForm);
		row1ADDOPInputBox.setPreferredSize(new Dimension(50,20));
		row1ADDOPInputBox.setVisible(true);
		addEROPane.add(row1ADDOPInputBox, g);
		
		
		
		JLabel row1InputBoxLabel = new JLabel("Enter Row 1 (Destination)");
		g.gridx = 0;
		g.gridy = 1;
		addEROPane.add(row1InputBoxLabel, g);
		
		
		/////////////////////////////////////////////////////////
		g.gridx = 1;
		g.gridy = 2;
		row2ADDOPInputBox = new JFormattedTextField(decForm);
		row2ADDOPInputBox.setPreferredSize(new Dimension(50,20));
		row2ADDOPInputBox.setVisible(true);
		addEROPane.add(row2ADDOPInputBox, g);
		
		
		JLabel row2InputBoxLabel = new JLabel("Enter Row 2 (Source)");
		g.gridx = 0;
		g.gridy = 2;
		addEROPane.add(row2InputBoxLabel, g);
		
		////////////////////////////////////////
		scalarADDOPInputBox = new JFormattedTextField(decForm);
		scalarADDOPInputBox.setPreferredSize(new Dimension(50,20));
		scalarADDOPInputBox.setVisible(true);
		g.gridx = 1;
		g.gridy = 3;
		addEROPane.add(scalarADDOPInputBox, g);
		
		
		
		JLabel scalarInputBoxLabel = new JLabel("Enter Scalar (Applied to Source and added to Destination)");
		g.gridx = 0;
		g.gridy = 3;
		addEROPane.add(scalarInputBoxLabel, g);
		
		////////////////////////////////////////////////
		JButton addEROEnterButton = new JButton("Enter");
		g.gridwidth = 2;
		g.gridy = 4;
		g.gridx = 0;
		addEROPane.add(addEROEnterButton,g);
		
		addEROEnterButton.addActionListener(new ActionListener() { //****************
			 
            public void actionPerformed(ActionEvent e)
            {
            	addEROEnterButtonPressed();
            	addEROPane.setVisible(false);
            	matrixOptions();
            }
        }); 
		
		
		
		frame.add(addEROPane);
	}

	public void addEROEnterButtonPressed()
	{
		try {
			scalarADDOPInputBox.commitEdit();
		} catch (ParseException e) {
			scalarADDOPInputBox.setValue(1);
		}
		try {
			row1ADDOPInputBox.commitEdit();
		} catch (ParseException e) {
			row1ADDOPInputBox.setValue(-1);
		}
		try {
			row2ADDOPInputBox.commitEdit();
		} catch (ParseException e) {
			row2ADDOPInputBox.setValue(-1);
		}
		
		int row1Committed =Integer.parseInt(row1ADDOPInputBox.getValue().toString());
		int row2Committed =Integer.parseInt(row2ADDOPInputBox.getValue().toString());
		double scalarCommitted = Double.parseDouble(scalarADDOPInputBox.getValue().toString());
		
		
		if(row1Committed > rowsM  || row2Committed > colsN)
		{
			ERROR ="INVALID ROW OR COLUMN";
		}
		else
		{
			matrix01.rowOPAdd(row1Committed - 1 , new BigDecimal(scalarCommitted), row2Committed -1);
		}
		
		
	}
	
	public void RREFButtonPressed()
	{
		matrix01.RREF();
		matrixOptions();
	}
	
}
