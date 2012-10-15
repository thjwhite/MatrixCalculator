import java.math.*;

public class Matrix {

	private BigDecimal[][] matrix;
	private int rowsM;
	private int colsN;
	
	public Matrix(int m, int n) //m = rows   n = columns     MxN matrix
	{
		matrix = new BigDecimal[m][n];
		rowsM = m;
		colsN = n;
		
	}
	
	public void setEntry(int m, int n, BigDecimal mxn)
	{
		matrix[m][n] = mxn;
	}
	
	public BigDecimal getEntry(int m, int n)
	{
		return matrix[m][n];
	}
	
	
	public void rowOPScalar(int row, BigDecimal scalar)
	{
		for(int n = 0; n < colsN; n++)
		{
			matrix[row][n] = matrix[row][n].multiply(scalar);
		}
	}
	
	public void rowOPScalarDivide(int row, BigDecimal scalar)
	{
		for(int n = 0; n < colsN; n++)
		{
			matrix[row][n] = matrix[row][n].divide(scalar, 4,BigDecimal.ROUND_HALF_UP);
		}
	}
	
	public void rowOPSwitch(int row1, int row2)
	{
		BigDecimal[] tempRow = new BigDecimal[colsN];
		
		for(int n = 0; n < colsN; n++) //Copies the first row and puts it in a temp row
		{
			tempRow[n] = matrix[row1][n];
		}
		
		for(int n = 0; n < colsN; n++) //Copies rows2 into row1 ... now row1 = row2
		{
			matrix[row1][n] = matrix[row2][n];
		}
		
		for(int n = 0; n < colsN; n++) //copy the original row1 from the temp row into row2
		{
			matrix[row2][n] = tempRow[n];
		}
		
	}
	
	public void rowOPAdd(int destination, BigDecimal scalar, int source)
	{
		if(!(destination < 0 || source < 0))
		{
			for(int n = 0; n < colsN; n++)
			{
				matrix[destination][n] = (matrix[source][n].multiply(scalar)).add(matrix[destination][n]);
				matrix[destination][n] = matrix[destination][n].round(new MathContext(4,RoundingMode.HALF_UP));
			}
		}
		
	}
	
	public void RREF()
	{
		//double[][] matrixTemp = matrix;
		int highestRow = 0;
		int leadingOne = 0;
		int maxLeadingOnes = rowsM; //the max number of leading ones is the number of rows, is because if we have a matrix with more cols than rows,
		for(int i = 0; i< maxLeadingOnes; i++) //my algorithm will want to make an additional leading one, which will lead to an out of bounds exception
		{									//because, say there are 3 cols and 2 rows. and we have the first 2 pivot columns, it will try and get a third
			if(isColNonzero(i))				// but there is only 2 rows.
			{
				if(isFirstPossibleEntryNonZero(highestRow, i))
				{
					if(getEntry(highestRow, i).doubleValue() != 0) //if the leading one candidate is not zero
					{
						rowOPScalarDivide(highestRow, getEntry(highestRow,i)); //divide its row by the candidate, hence making it one
						leadingOne = highestRow; //it is now a leading one
						highestRow++;
						
						for(int j = leadingOne +1; j < rowsM; j++) //use rowOPAdd to make non zero entries in the pivot column ZERO
						{
							rowOPAdd(j, getEntry(j,i).negate(), leadingOne); //going down
						}
						for(int j = leadingOne - 1; j >= 0; j--)
						{
							rowOPAdd(j, getEntry(j,i).negate(), leadingOne); //going up
						}
					}
					else // the leading one candidate is already ONE, so it is now a leading one
					{
						leadingOne = highestRow;
						highestRow++;
						for(int j = leadingOne +1; j < rowsM; j++) //use rowOPAdd to make non zero entries in the pivot column ZERO
						{
							rowOPAdd(j, getEntry(j,i).negate(), leadingOne); //going down
						}
						for(int j = leadingOne - 1; j >= 0; j--)
						{
							rowOPAdd(j, getEntry(j,i).negate(), leadingOne); //going up
						}
					}
				}
				else if(isColNonzeroAfterRow(i, highestRow))
				{ 
					boolean nonZeroRowFound = false;
					int n = highestRow + 1;
					int nonZeroRow = highestRow;
					while(!nonZeroRowFound && n < rowsM)
					{
						if(getEntry(n,i).doubleValue() != 0)
						{
							nonZeroRowFound = true;
							nonZeroRow = n;
						}
						n++;
					}
					rowOPSwitch(nonZeroRow,highestRow);
					
					if(getEntry(highestRow, i).doubleValue() != 0) //if the leading one candidate is not zero
					{
						rowOPScalarDivide(highestRow, getEntry(highestRow,i)); //divide its row by the candidate, hence making it one
						leadingOne = highestRow; //it is now a leading one
						highestRow++; //because we have a leading one, no other leading row can occupy the same row
						
						for(int j = leadingOne +1; j < rowsM; j++) //use rowOPAdd to make non zero entries in the pivot column ZERO
						{
							rowOPAdd(j, getEntry(j,i).negate(), leadingOne); //going down
						}
						for(int j = leadingOne - 1; j >= 0; j--)
						{
							rowOPAdd(j, getEntry(j,i).negate(), leadingOne); //going up
						}
					}
					else // the leading one candidate is already ONE, so it is now a leading one
					{
						leadingOne = highestRow;
						highestRow++;
						for(int j = leadingOne +1; j < rowsM; j++) //use rowOPAdd to make non zero entries in the pivot column ZERO
						{
							rowOPAdd(j, getEntry(j,i).negate(), leadingOne); //going down
						}
						for(int j = leadingOne - 1; j >= 0; j--)
						{
							rowOPAdd(j, getEntry(j,i).negate(), leadingOne); //going up
						}
					}
				}
				else
				{
					
				}
			}
			else
			{
				
			}
			
		}
	}
	
	

	public boolean isColNonzero(int col1)
	{
		boolean isnonzero = false;
		int row = 0;
		while(!isnonzero && row < rowsM)
		{
			if(getEntry(row,col1).doubleValue() != 0)
			{
				isnonzero = true;
			}
			row++;
		}
		return isnonzero;
	}
	
	private boolean isFirstPossibleEntryNonZero(int highest, int col) 
	{
		return (getEntry(highest,col).doubleValue() != 0);
	}
	
	public boolean isColNonzeroAfterRow(int col1, int afterThisRow)
	{
		boolean isnonzero = false;
		int row = afterThisRow + 1;
		while(!isnonzero && row < rowsM)
		{
			if(getEntry(row,col1).doubleValue() != 0)
			{
				isnonzero = true;
			}
			row++;
		}
		return isnonzero;
	}

}
