package matrix.operations;

import matrix.MatrixInterface;
import matrix.entity.MatrixDouble;

public class MatrixMultiplay {

	public static MatrixInterface multiplay(MatrixInterface matrixA, MatrixInterface matrixB) {
		int rowsA = matrixA.getAmountOfRows();
		int colsA = matrixA.getAmountOfCols();
		int rowsB = matrixB.getAmountOfRows();
		int colsB = matrixB.getAmountOfCols();
		MatrixDouble matrixC = new MatrixDouble(rowsA, colsB);
		double temp = 0;
		long startTime = System.currentTimeMillis();
		if (colsA == rowsB) {
			for (int i = 0; i < rowsA; ++i) {
				for (int j = 0; j < colsB; ++j) {
					for (int k = 0; k < colsA; ++k) {
						temp += matrixA.getValue(i, k) * matrixB.getValue(k, j);
					}
					matrixC.setValue(i, j, temp);
					temp = 0;
				}
			}

		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;

		System.out
				.println("Multiplication of matrixes lasted " + time + " ms.");
		return matrixC;
	}
}
