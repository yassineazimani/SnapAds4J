/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.exceptions;

/**
 * Utils for the exceptions.
 *
 * @author Yassine
 */
public class SnapExceptionsUtils {

  /**
   * Throw a SnapResponseErrorException according the status code HTTP given.
   *
   * @param statusCode status code HTTP
   * @return SnapResponseErrorException
   */
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
