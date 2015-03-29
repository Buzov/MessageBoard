	
package matrix.exceptions;

/**
* Signals about that the index of the matrix out of bounds.
* 
* @author Dark Lord
*/
public class MatrixIndexOutOfBoundsException extends Exception {

/**
* 
*/
private static final long serialVersionUID = 734442967201127256L;

/**
*
*/
public MatrixIndexOutOfBoundsException() {
super();
}

/**
*
* @param message the detail message.
*/
public MatrixIndexOutOfBoundsException(String message) {
super(message);
}
}