package example;

import java.util.Scanner;

import matrix.exceptions.MatrixIndexOutOfBoundsException;

public class ExceptionTest {

 static int[] mas = { 1, 2, 3, 4 };
 private static Scanner scanner;

 public static void main(String[] args) {
  scanner = new Scanner(System.in);

  System.out.println("Введите число и получите корень!");
  System.out.println("Для выхода введите \"q\"!");
  String string;
  int number;
  boolean work = true;

  while (work) {
   string = scanner.nextLine();
   if (string.equals("q")) {
    work = false;
   }
   if (work) {
    try {
     number = Integer.parseInt(string);
     // System.out.println(mas[4]);
     printSQRT(number);
     // System.out.println("Жизнь радость!!!");
    } catch (ArrayIndexOutOfBoundsException
      | MatrixIndexOutOfBoundsException e) {
     // e.printStackTrace();
     System.out.println("Число должно быть > 0");
     /*
      * } catch (MatrixIndexOutOfBoundsException e) { // TODO
      * Auto-generated catch block e.printStackTrace();
      */
    } catch (NumberFormatException e) {
     System.out.println("Введите число а не строку");
    } catch (Exception e) {
     System.out.println("Введите число а не строку");
    } catch (Throwable e) {

    } finally {
     // выполняется всегда
     // System.out.println("Жизнь радость!!!");
    }
   }

  }

 }

 public static void printSQRT(int i) throws MatrixIndexOutOfBoundsException {
  if (i < 0) {
   // throw new ArrayIndexOutOfBoundsException();
   throw new MatrixIndexOutOfBoundsException();
  } else {
   System.out.println("Корень - " + Math.sqrt(i));
  }

 }
}