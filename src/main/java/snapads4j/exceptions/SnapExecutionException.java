package snapads4j.exceptions;

/**
 * This exception is thrown when a function doesn't well work... (I/O issues,
 * bugs...)
 * 
 * @author yassine
 *
 */
public class SnapExecutionException extends Exception {

    private static final long serialVersionUID = 7104116778061387287L;

    public SnapExecutionException(String message, Throwable cause) {
	super(message, cause);
    }// SnapExecutionException()

}// SnapExecutionException
