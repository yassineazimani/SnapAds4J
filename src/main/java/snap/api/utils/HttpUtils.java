package snap.api.utils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtils {

  public static HttpRequest prepareGetRequest(String url, String oAuthAccessToken) {
    return HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .header("Authorization", "Bearer " + oAuthAccessToken)
        .build();
  } // prepareGetRequest()

  public static HttpRequest preparePostRequest(String url, Map<String, String> args)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String requestBody = mapper.writeValueAsString(args);
    return HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(BodyPublishers.ofString(requestBody))
        .header("Content-Type", "application/json")
        .build();
  } // preparePostRequest()

  public static HttpRequest preparePostRequest(
      String url, String oAuthAccessToken, Map<String, String> args)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String requestBody = mapper.writeValueAsString(args);
    return HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(BodyPublishers.ofString(requestBody))
        .header("Authorization", "Bearer " + oAuthAccessToken)
        .header("Content-Type", "application/json")
        .build();
  } // preparePostRequest()
} // HttpUtils
