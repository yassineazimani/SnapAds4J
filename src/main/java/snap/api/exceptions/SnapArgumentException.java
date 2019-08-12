package snap.api.exceptions;

/**
 * Exception thrown when an argument is missing.
 *
 * @author Yassine
 */
public class SnapArgumentException extends Exception {

  private static final long serialVersionUID = 574050857351824817L;

  /**
   * Constructor
   *
   * @param message Message exception
   */
  public SnapArgumentException(String message) {
    super(message);
  } // SnapArgumentException()
} // SnapArgumentException()
