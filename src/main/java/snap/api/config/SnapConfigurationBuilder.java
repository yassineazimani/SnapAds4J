package snap.api.config;

/**
 * Build the configuration to use API.
 *
 * @author Yassine
 */
public class SnapConfigurationBuilder {

  /** Configuration API */
  private SnapConfiguration snapConfiguration;

  /** Constructor */
  public SnapConfigurationBuilder() {
    this.snapConfiguration = new SnapConfiguration();
  } // SnapConfigurationBuilder()

  /**
   * Save client ID in the configuration
   *
   * @param clientId client ID
   * @return SnapConfigurationBuilder
   */
  public SnapConfigurationBuilder setClientId(String clientId) {
    this.snapConfiguration.setClientId(clientId);
    return this;
  } // setClientId()

  /**
   * Save client Secret in the configuration
   *
   * @param clientSecret client secret
   * @return SnapConfigurationBuilder
   */
  public SnapConfigurationBuilder setClientSecret(String clientSecret) {
    this.snapConfiguration.setClientSecret(clientSecret);
    return this;
  } // setClientSecret()

  /**
   * Save redirect URI in the configuration
   *
   * @param redirectUri redirect URI
   * @return SnapConfigurationBuilder
   */
  public SnapConfigurationBuilder setRedirectUri(String redirectUri) {
    this.snapConfiguration.setRedirectUri(redirectUri);
    return this;
  } // setRedirectUri()

  /**
   * Build an instance of SnapConfiguration.
   *
   * @return SnapConfiguration instance
   */
  public SnapConfiguration build() {
    return this.snapConfiguration;
  } // build()
} // SnapConfigurationBuilder
