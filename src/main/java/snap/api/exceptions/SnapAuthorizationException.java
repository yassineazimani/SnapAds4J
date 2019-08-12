package snap.api.exceptions;

/**
 * Exception thrown when an argument bound to the authorization is missing.
 *
 * @author Yassine
 */
public class SnapAuthorizationException extends Exception {

  private static final long serialVersionUID = 1266340422251663658L;

  /**
   * Constructor
   *
   * @param message Message exception
   */
  public SnapAuthorizationException(String msg) {
    super(msg);
  } // SnapAuthorizationException()
} // SnapAuthorizationException
