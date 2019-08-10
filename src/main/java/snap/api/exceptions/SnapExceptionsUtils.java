package snap.api.exceptions;

public class SnapExceptionsUtils {

  public static SnapResponseErrorException getResponseExceptionByStatusCode(int statusCode) {
    SnapResponseErrorException ex;
    switch (statusCode) {
      case 400:
        ex = new SnapResponseErrorException("Bad Request", statusCode);
        break;
      case 401:
        ex = new SnapResponseErrorException("Unauthorized - Check your API key", statusCode);
        break;
      case 403:
        ex = new SnapResponseErrorException("Access Forbidden", statusCode);
        break;
      case 404:
        ex = new SnapResponseErrorException("Not Found", statusCode);
        break;
      case 405:
        ex = new SnapResponseErrorException("Method Not Allowed", statusCode);
        break;
      case 406:
        ex = new SnapResponseErrorException("Not Acceptable", statusCode);
        break;
      case 410:
        ex = new SnapResponseErrorException("Gone", statusCode);
        break;
      case 418:
        ex = new SnapResponseErrorException("I'm a teapot", statusCode);
        break;
      case 429:
        ex = new SnapResponseErrorException("Too Many Requests / Rate limit reached", statusCode);
        break;
      case 500:
        ex = new SnapResponseErrorException("Internal Server Error", statusCode);
        break;
      case 503:
        ex = new SnapResponseErrorException("Service Unavailable", statusCode);
        break;
      default:
        ex = new SnapResponseErrorException("Error " + statusCode, statusCode);
        break;
    }
    return ex;
  } // SnapResponseErrorException()
} // SnapExceptionsUtils
