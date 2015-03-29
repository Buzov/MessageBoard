package matrix.entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import matrix.MatrixAbstract;
import matrix.MatrixInterface;
import matrix.exceptions.MatrixIndexOutOfBoundsException;

public class MatrixDouble extends MatrixAbstract {

	protected double[][] array;// = new double[10][10];

	public MatrixDouble(int i, int j) {
		super.rows = i;
		super.cols = j;
		array = new double[i][j];
		System.out.println("Создан обьект матрицы с Double");
	}

	@Override
	public double getValue(int i, int j) {
		return array[i][j];
	}

	@Override
	public void setValue(int i, int j, double value) {
		array[i][j] = value;
		vigilanceMax = true;
		vigilanceMin = true;
	}

	@Override
	public MatrixInterface clone() throws CloneNotSupportedException {
		MatrixDouble newClone = (MatrixDouble) super.clone();
		newClone.array = array.clone();
		for (int j = 0; j < getAmountOfRows(); j++) {
			newClone.array[j] = array[j].clone();
		}
		return newClone;
	}

	@Override
	public void setValueSlow(int i, int j, double value) throws MatrixIndexOutOfBoundsException {
		if (!checkIndices(i, j)) {
			throw new MatrixIndexOutOfBoundsException("Inadmissible value of an index.");
		}
		array[i][j] = value;
		if (getValue(i, j) > max) {
			max = getValue(i, j);
		}
		if (getValue(i, j) < min) {
			min = getValue(i, j);
		}
	}
}
