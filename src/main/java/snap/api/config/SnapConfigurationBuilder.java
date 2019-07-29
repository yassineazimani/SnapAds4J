package snap.api.config;

public class SnapConfigurationBuilder {
	
	private SnapConfiguration snapConfiguration;
	
	public SnapConfigurationBuilder() {
		this.snapConfiguration = new SnapConfiguration();
	}// SnapConfigurationBuilder()
	
	public SnapConfigurationBuilder setClientId(String clientId) {
		return this;
	}// setClientId()
	
	public SnapConfigurationBuilder setRedirectUri(String redirectUri) {
		return this;
	}// setRedirectUri()
	
	public SnapConfiguration build() {
		return this.snapConfiguration;
	}// build()
	
}// SnapConfigurationBuilder
