package snap.api.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Token Response
 *
 * @author Yassine
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"token_type"})
public class TokenResponse {

  /** Expiration expressed in seconds of the access token. */
  @JsonProperty("expires_in")
  private int expiresIn;

  /** Refresh token. Useful to regenerate a new Access Token. */
  @JsonProperty("refresh_token")
  private String refreshToken;

  /**
   * Access Token. If you receive a status code HTTP 401, you should use your refresh token to
   * generate a new Access Token and retry the request.
   */
  @JsonProperty("access_token")
  private String accessToken;
} // TokenResponse
