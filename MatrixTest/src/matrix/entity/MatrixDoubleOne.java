package matrix.entity;

import matrix.MatrixAbstract;
import matrix.MatrixInterface;

public class MatrixDoubleOne extends MatrixAbstract {

    protected double[] array;// = new double[10][10];

    public MatrixDoubleOne(int i, int j) {
        super.rows = i;
        super.cols = j;
        array = new double[i * j];
        System.out.println("Создан обьект матрицы с DoubleOne");
    }

    @Override
    public double getValue(int i, int j) {
        int temp = cols * i + j;

        return array[temp];
    }

    @Override
    public void setValue(int i, int j, double value) {
        int temp = cols * i + j;
        array[temp] = value;
        vigilanceMax = true;
        vigilanceMin = true;
    }

    @Override
    public MatrixInterface clone() throws CloneNotSupportedException {
        MatrixDoubleOne newClone = (MatrixDoubleOne) super.clone();
        newClone.array = array.clone();
        return newClone;
    }

    @Override
    public void setValueSlow(int i, int j, double value) {
        int temp = cols * i + j;
        array[temp] = value;
        if (getValue(i, j) > max) {
            max = getValue(i, j);
        }
        if (getValue(i, j) < min) {
            min = getValue(i, j);
        }
    }

}
