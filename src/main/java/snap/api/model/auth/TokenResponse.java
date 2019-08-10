package snap.api.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"token_type"})
public class TokenResponse {

  @JsonProperty("expires_in")
  private int expiresIn;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("access_token")
  private String accessToken;
} // TokenResponse
