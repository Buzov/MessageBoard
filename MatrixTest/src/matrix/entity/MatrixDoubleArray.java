package matrix.entity;

import java.util.ArrayList;

import matrix.MatrixAbstract;
import matrix.MatrixInterface;

public class MatrixDoubleArray extends MatrixAbstract {

    private ArrayList<ArrayList<Double>> array;// = new double[10][10];

    public MatrixDoubleArray(int i, int j) {
        super.rows = i;
        super.cols = j;
        array = new ArrayList<>();
        for (int m = 0; m < rows; m++) {
            array.add(new ArrayList<Double>(cols));
            for (int n = 0; n < cols; n++) {
                array.get(m).add(Double.NaN);
            }
        }
        System.out.println("Создан обьект матрицы с DoubleArray");
    }

    @Override
    public double getValue(int i, int j) {
        return array.get(i).get(j);
    }

    @Override
    public void setValue(int i, int j, double value) {
        array.get(i).set(j, (double) value);
        vigilanceMax = true;
        vigilanceMin = true;
    }

    @Override
    public MatrixInterface clone() throws CloneNotSupportedException {
        MatrixDoubleArray newClone = (MatrixDoubleArray) super.clone();
        newClone.array = (ArrayList<ArrayList<Double>>) array.clone();
        return newClone;
    }

    @Override
    public void setValueSlow(int i, int j, double value) {
        array.get(i).set(j, (double) value);
        if (getValue(i, j) > max) {
            max = getValue(i, j);
        }
        if (getValue(i, j) < min) {
            min = getValue(i, j);
        }
    }

}
