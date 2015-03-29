package matrix.test;

import matrix.entity.MatrixDouble;

public class MatrixTestClone {
	
	public static void main (String[] args) {
		MatrixDouble matrixDouble = new MatrixDouble(10, 10);
		matrixDouble.initialize();
		MatrixDouble matrixDoubleClone = null;
		try {
			matrixDoubleClone = (MatrixDouble) matrixDouble.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		System.out.println(matrixDouble.equals(matrixDoubleClone));
	}

}
