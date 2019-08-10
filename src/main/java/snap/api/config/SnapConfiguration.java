package snap.api.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnapConfiguration {

  private String clientId;

  private String clientSecret;

  private String redirectUri;
} // SnapConfiguration
