package matrix.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import matrix.MatrixInterface;
import matrix.entity.MatrixDouble;
import matrix.fabric.MatrixFabric;
import matrix.fabric.MatrixType;
import matrix.operations.MatrixMultiplay;

public class MatrixTest {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int i;
        int j;
        int k;
        int l;
        System.out.println("Введите кол-во строк:");
        i = scanner.nextInt();
        System.out.println("Введите кол-во столбцов:");
        j = scanner.nextInt();

        MatrixInterface matrixA = null;
        MatrixInterface matrixB = null;

        System.out.println("Если хотите double - введите 1");
        scanner.nextLine();
        String type = scanner.nextLine();

        if (type == "1") {
            matrixA = /*new MatrixDouble2(j, i);*/MatrixFabric.getObjectOfMatrix(i, j, MatrixType.DOUBLE);
        } else if (type == "2") {
            matrixA = MatrixFabric.getObjectOfMatrix(i, j, MatrixType.DOUBLE_ARRAY);
        } else {
            matrixA = MatrixFabric.getObjectOfMatrix(i, j, MatrixType.DOUBLE_ONE);
        }
        matrixA.initialize();
       // matrixA.print();

        System.out.println("Введите кол-во строк:");
        k = scanner.nextInt();
        System.out.println("Введите кол-во столбцов:");
        l = scanner.nextInt();
        
        System.out.println("Если хотите double - введите 1");
        scanner.nextLine();
        String typi = scanner.nextLine();
        

        if (type == "1") {
            matrixB = /*new MatrixDouble2(k, l);*/MatrixFabric.getObjectOfMatrix(i, j, MatrixType.DOUBLE);
        } else if (type == "2") {
            matrixB = MatrixFabric.getObjectOfMatrix(k, l, MatrixType.DOUBLE_ARRAY);
        } else {
            matrixB = MatrixFabric.getObjectOfMatrix(k, l, MatrixType.DOUBLE_ONE);
        }
        
        matrixB.initialize();
        
        //matrixB.print();
        MatrixDouble matrixresult = (MatrixDouble) MatrixMultiplay.multiplay(matrixA, matrixB);
        //matrixresult.print();
       
        
    
        
    }
}
