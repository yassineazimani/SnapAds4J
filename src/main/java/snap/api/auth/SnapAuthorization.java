package snap.api.auth;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.config.SnapConfiguration;
import snap.api.exceptions.SnapAuthorizationException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.auth.TokenResponse;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

@Getter
@Setter
@NoArgsConstructor
public class SnapAuthorization {

  private SnapConfiguration configuration;

  private FileProperties fp;

  private String apiUrl;

  private HttpClient httpClient;

  private static final Logger LOGGER = LogManager.getLogger(SnapAuthorization.class);

  public SnapAuthorization(SnapConfiguration configuration) {
    this.configuration = configuration;
    this.fp = new FileProperties();
    this.apiUrl = (String) fp.getProperties().get("api.url.auth");
    this.httpClient = HttpClient.newHttpClient();
  } // SnapAuthorization()

  public String getOAuthAuthorizationURI() throws SnapAuthorizationException {
    if (this.configuration == null) {
      throw new SnapAuthorizationException("Configuration unfound");
    }
    if (StringUtils.isEmpty(configuration.getClientId())) {
      throw new SnapAuthorizationException("Missing client ID");
    }
    if (StringUtils.isEmpty(configuration.getRedirectUri())) {
      throw new SnapAuthorizationException("Missing Redirect URI");
    }
    StringBuilder strBuilder =
        new StringBuilder(
            "https://accounts.snapchat.com/login/oauth2/authorize?response_type=code&scope=snapchat-marketing-api");
    strBuilder.append("&client_id=").append(configuration.getClientId());
    strBuilder.append("&redirect_uri=").append(configuration.getRedirectUri());
    return strBuilder.toString();
  } // getOAuthAuthorizationURI()

  public TokenResponse getOAuthAccessToken(String oauthCode)
      throws JsonProcessingException, SnapAuthorizationException, SnapResponseErrorException {
    TokenResponse responseFromJson = null;
    if (this.configuration == null) {
      throw new SnapAuthorizationException("Configuration unfound");
    }
    if (StringUtils.isEmpty(configuration.getRedirectUri())) {
      throw new SnapAuthorizationException("Missing Redirect URI");
    }
    if (StringUtils.isEmpty(configuration.getClientId())) {
      throw new SnapAuthorizationException("Missing client ID");
    }
    if (StringUtils.isEmpty(configuration.getClientSecret())) {
      throw new SnapAuthorizationException("Missing client Secret");
    }
    if (StringUtils.isEmpty(oauthCode)) {
      throw new SnapAuthorizationException("Missing oAuthCode");
    }
    Map<String, String> args = new HashMap<>();
    args.put("grant_type", "authorization_code");
    args.put("redirect_uri", configuration.getRedirectUri());
    args.put("code", oauthCode);
    args.put("client_id", configuration.getClientId());
    args.put("client_secret", configuration.getClientSecret());
    HttpRequest request = HttpUtils.preparePostRequest(this.apiUrl, args);
    try {
      HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      responseFromJson = mapper.readValue(response.body(), TokenResponse.class);
    } catch (IOException | InterruptedException e) {
      LOGGER.error("Impossible to get OAuthAccessToken with oauthCode {}", oauthCode, e);
    }
    return responseFromJson;
  } // getOAuthAccessToken()

  public TokenResponse refreshToken(String refreshToken)
      throws JsonProcessingException, SnapAuthorizationException, SnapResponseErrorException {
    TokenResponse responseFromJson = null;
    if (this.configuration == null) {
      throw new SnapAuthorizationException("Configuration unfound");
    }
    if (StringUtils.isEmpty(configuration.getRedirectUri())) {
      throw new SnapAuthorizationException("Missing Redirect URI");
    }
    if (StringUtils.isEmpty(configuration.getClientId())) {
      throw new SnapAuthorizationException("Missing client ID");
    }
    if (StringUtils.isEmpty(configuration.getClientSecret())) {
      throw new SnapAuthorizationException("Missing client Secret");
    }
    if (StringUtils.isEmpty(refreshToken)) {
      throw new SnapAuthorizationException("Missing refreshToken");
    }
    Map<String, String> args = new HashMap<>();
    args.put("grant_type", "refresh_token");
    args.put("redirect_uri", configuration.getRedirectUri());
    args.put("code", refreshToken);
    args.put("client_id", configuration.getClientId());
    args.put("client_secret", configuration.getClientSecret());
    HttpRequest request = HttpUtils.preparePostRequest(this.apiUrl, args);
    try {
      HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      responseFromJson = mapper.readValue(response.body(), TokenResponse.class);
    } catch (IOException | InterruptedException e) {
      LOGGER.error("Impossible to get refresh token with old refresh token {}", refreshToken, e);
    }
    return responseFromJson;
  } // refreshToken()
} // SnapAuthorization
