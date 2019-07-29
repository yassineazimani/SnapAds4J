package snap.api.auth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import  org.mockito.junit.MockitoJUnitRunner;

import snap.api.config.SnapConfiguration;
import snap.api.config.SnapConfigurationBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests mocked for SnapAuthorization.
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapAuthorizationTest {
    
	private SnapAuthorization auth;
	
	@Before
	public void setUp() {
		SnapConfigurationBuilder config = new SnapConfigurationBuilder();
		/*config.setClientId("fake_client_id");
		config.setRedirectUri("fake_redirect_uri");
		SnapConfiguration config = config.builder();
		this.auth.setConfiguration(config);*/
	}// setUp()
	
	@Test
	public void test_getOAuthAuthorizationURI_should_success() {
		/*Mockito.when(this.auth.getOAuthAuthorizationURI()).thenReturn("fake_redirect_uri?code=code_from_redirect_uri");
		String authorizationURI = this.auth.getOAuthAuthorizationURI();
		assertThat(authorizationURI).isNotEmpty();
		assertThat(authorizationURI).isNotBlank();
		assertThat(authorizationURI).contains("code=code_from_redirect_uri");*/
	}// test_getOAuthAuthorizationURI_should_success()
	
	public void test_getOAuthAccessToken_should_success() {
		/*Mockito.when(this.auth.getOAuthAccessToken()).thenReturn("meowmeowmeow");
		String oauthCode = "code_from_redirect_uri"; // Snap added 'code' attribute to redirect_uri
		String oAuthAccessToken = this.auth.getOAuthAccessToken(oauthCode);
		assertThat(oAuthAccessToken).isNotEmpty();
		assertThat(oAuthAccessToken).isNotBlank();
		assertThat(oAuthAccessToken).isEquals("meowmeowmeow");*/
	}// test_getOAuthAccessToken_should_success()
	
}// SnapAuthorizationTest
