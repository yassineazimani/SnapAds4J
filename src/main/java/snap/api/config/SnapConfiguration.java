package snap.api.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration to use the API.
 *
 * @author Yassine
 */
@Getter
@Setter
public class SnapConfiguration {

  /** Client ID */
  private String clientId;

  /** Client Secret */
  private String clientSecret;

  /** URLEncoded Redirect URI */
  private String redirectUri;
} // SnapConfiguration
