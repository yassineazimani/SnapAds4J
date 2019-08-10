package snap.api.exceptions;

import lombok.Getter;

@Getter
public class SnapResponseErrorException extends Exception {

  private static final long serialVersionUID = 8501189439029665947L;

  private int statusCode;

  public SnapResponseErrorException(String message, int statusCode) {
	super(message);
    this.statusCode = statusCode;
  }// SnapResponseErrorException()
  
}// SnapResponseErrorException
