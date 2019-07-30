package snap.api.auth;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;
import lombok.Setter;
import snap.api.config.SnapConfiguration;
import snap.api.exceptions.SnapAuthorizationException;

@Setter
@NoArgsConstructor
public class SnapAuthorization {
	
	private SnapConfiguration configuration;
	
	public SnapAuthorization(SnapConfiguration configuration) {
		this.configuration = configuration;
	}// SnapAuthorization()
	
	public String getOAuthAuthorizationURI() throws SnapAuthorizationException{
		if(this.configuration == null) {
			throw new SnapAuthorizationException("Configuration unfound");
		}
		if(StringUtils.isEmpty(configuration.getClientId())) {
			throw new SnapAuthorizationException("Empty client ID");
		}
		if(StringUtils.isEmpty(configuration.getRedirectUri())) {
			throw new SnapAuthorizationException("Empty Redirect URI");
		}
		StringBuilder strBuilder = new StringBuilder("https://accounts.snapchat.com/login/oauth2/authorize?response_type=code&scope=snapchat-marketing-api");
		strBuilder.append("&client_id=").append(configuration.getClientId());
		strBuilder.append("&redirect_uri=").append(configuration.getRedirectUri());
		return strBuilder.toString();
	}// getOAuthAuthorizationURI()
	
	public String getOAuthAccessToken(String oauthCode) {
		return "";
	}// getOAuthAccessToken()
	
}// SnapAuthorization
