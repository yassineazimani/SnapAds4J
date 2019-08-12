package snap.api.exceptions;

/**
 * Exception thrown when the OAuthAccessToken is missing when an endpoint is calling.
 *
 * @author Yassine
 */
public class SnapOAuthAccessTokenException extends Exception {

  private static final long serialVersionUID = -4988614181588855181L;

  /**
   * Constructor
   *
   * @param message Message exception
   */
  public SnapOAuthAccessTokenException(String message) {
    super(message);
  } // SnapOAuthAccessTokenException()
} // SnapOAuthAccessTokenException
