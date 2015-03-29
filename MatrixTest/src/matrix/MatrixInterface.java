package matrix;

import matrix.exceptions.MatrixIndexOutOfBoundsException;

public interface MatrixInterface{

	int getAmountOfRows();

	int getAmountOfCols();

	double getValue(int i, int j);

	void setValue(int i, int j, double value);
	
    void setValueSlow(int i, int j, double value) throws MatrixIndexOutOfBoundsException;

	void print();

	void initialize();

	String toStringSlow();

	@Override
	String toString();

	@Override
	boolean equals(Object obj);
	
	//@Override
	//MatrixInterface clone() throws CloneNotSupportedException;

}
