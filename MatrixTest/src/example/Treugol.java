package example;

public class Treugol {

    public static void main(String[] args) {
        double[][] arrayDouble = new double[10][];

        for (int i = 1; i <= 10; ++i) {
            arrayDouble[i - 1] = new double[i];
        }

        for (int i = 0; i < arrayDouble.length; ++i) {
            for (int j = 1; j <= arrayDouble[i].length; ++j) {
                System.out.print(arrayDouble[i][j - 1] + " ");
            }
            System.out.println();
        }

        int a = 5;
        int b = 5;

        Integer aI = 5000;//-128...127
        Integer bI = 5000;
        Integer cI = bI;

        Integer с = aI + bI;

        if (a == b) {
            System.out.println("a == b");
        }

        if (aI == bI) {
            System.out.println("aI == bI");
        } else {
            System.out.println("aI != bI");
        }

        if (cI == bI) {
            System.out.println("cI == bI");
        }

        if (aI.equals(bI)) {
            System.out.println("aI.equals(bI)");
            
            String[] words = {"ваgва", "ваeва", "вавiа"};
  for(String s : words) {
   System.out.print(s);
   
   int i2 = 0;
  int k = 0;
  /*for (int i = 0, f = 5; (i2 - 567 ) <= 10; i++, f++, k=i+f) {
   
  }*/
  
  int[][] integer = {{1,4,5,5,6,4,5,6,4,56,6,4},{2,5}};
  }
        }
    }
}
