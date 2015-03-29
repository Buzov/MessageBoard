package matrix.test;

import matrix.entity.MatrixDouble;
import matrix.exceptions.MatrixIndexOutOfBoundsException;

public class MatrixSetValueTest {

    public static void main(String[] args) throws MatrixIndexOutOfBoundsException {
        MatrixDouble matrixA = new MatrixDouble(2, 2);
        MatrixDouble matrixB = new MatrixDouble(2, 2);
        int [] mas = new int[5];
        mas[6] = 0;
        
			matrixB.setValueSlow(2, 2, 10);
		
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000_000_000; i++) {
            matrixA.setValue(0, 0, i);
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("Длилось " + time + " ms.");
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < 1000_000_000; i++) {
            try {
				matrixB.setValueSlow(0, 0, i);
			} catch (MatrixIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        long endTime2 = System.currentTimeMillis();
        long time2 = endTime2 - startTime2;
        System.out
                .println("Длилось " + time2 + " ms.");
    }
}
