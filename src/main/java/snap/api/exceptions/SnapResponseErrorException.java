package snap.api.exceptions;

import lombok.Getter;

/**
 * Exception thrown when the endpoint returns a bad status code HTTP.
 *
 * @author Yassine
 */
@Getter
public class SnapResponseErrorException extends Exception {

  private static final long serialVersionUID = 8501189439029665947L;

  /** Status code HTTP */
  private int statusCode;

  /**
   * Constructor
   *
   * @param message Message exception
   * @param statusCode status code HTTP
   */
  public SnapResponseErrorException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  } // SnapResponseErrorException()
} // SnapResponseErrorException
