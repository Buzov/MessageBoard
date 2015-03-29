package ru.messageBoard.model.exception;

public class NullException extends RuntimeException {

	private static final long serialVersionUID = 1881154389157419478L;

	public NullException() {
    }

    public NullException(String message) {
        super(message);
    }

    public NullException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullException(Throwable cause) {
        super(cause);
    }

    public NullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
	
	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
	}
	
}
