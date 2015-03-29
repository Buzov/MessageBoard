package matrix;

import java.math.BigDecimal;

public abstract class MatrixAbstract implements MatrixInterface, Cloneable {

    protected int rows = 0;
    protected int cols = 0;
    protected boolean vigilanceMax = false;
    protected boolean vigilanceMin = false;
    protected double max;
    protected double min;
    protected int rowsMin;
    protected int rowsMax;
    protected int colsMin;
    protected int colsMax;

    @Override
    public int getAmountOfRows() {
        return rows;
    }

    @Override
    public int getAmountOfCols() {
        return cols;
    }

    @Override
    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.format("%.3f", getValue(i, j));
                System.out.print("    ");
            }
            System.out.println();
        }
    }

    @Override
    public void initialize() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                setValue(i, j, Math.random() * 20 - 10);
                vigilanceMax = true;
                vigilanceMin = true;
            }
        }
    }

    /*@Override
     public abstract MatrixInterface clone() throws CloneNotSupportedException;*/
    public static BigDecimal roundNumber(double value, int digits) {
        // we approximate the transferred number "value" with accuracy "digits"
        BigDecimal num = new BigDecimal("" + value).setScale(digits,
                BigDecimal.ROUND_HALF_UP);
        return num;
    }

    @Override
    public String toString() {
        String s = "";
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                s += getValue(i, j);
            }
            s += "\r\n";
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out
                .println("Multiplication of matrixes lasted " + time + " ms.");
        return s;
    }

    @Override
    public String toStringSlow() {
        long startTime = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stringBuilder.append(getValue(i, j));
                stringBuilder.append(" ");
            }
            stringBuilder.append("\r\n");
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out
                .println("Multiplication of matrixes lasted " + time + " ms.");
        return stringBuilder.toString();

    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        /* obj ссылается на null */
        if (obj == null) {
            return false;
        }

        /* Удостоверимся, что ссылки имеют тот же самый тип */

        /*
         * if (!(getClass() == obj.getClass())) { return false; }
         */
        MatrixInterface matrixB;

        if (!(obj instanceof MatrixInterface)) {
            return false;
        } else {
            matrixB = (MatrixInterface) obj;
        }
        int rowsB = matrixB.getAmountOfRows();
        int colsB = matrixB.getAmountOfCols();
        boolean c = true;

        if (colsB != cols || rows != rowsB) {
            c = false;
            System.out.println("Матрицы не одинакового размера");
        } else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colsB; j++) {
                    if (getValue(i, j) != matrixB.getValue(i, j)) {
                        return false;
                    }
                }
            }
        }
        return c;
    }

    public double max() {

        if (vigilanceMax == true) {

            this.max = getValue(0, 0);
            for (int i = 0; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    if (getValue(i, j) > this.max) {
                        this.max = getValue(i, j);
                        rowsMax = i;
                        colsMax = j;
                    }

                }
            }
            vigilanceMax = false;
            return max;
        }
        return max;

    }

    public double min() {
        if (vigilanceMin == true) {

            this.max = getValue(0, 0);
            for (int i = 0; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    if (getValue(i, j) < min) {
                        min = getValue(i, j);
                        rowsMin = i;
                        colsMin = j;
                    }

                }
            }
            vigilanceMin = false;
            return min;
        }
        return min;

    }
    protected boolean checkIndices(int rows, int cols) {
  if ((rows < 0) || (cols < 0) || (rows >= this.rows) || (cols >= this.cols)) {
   return false;
  } else {
   return true;
  }
 }
}
