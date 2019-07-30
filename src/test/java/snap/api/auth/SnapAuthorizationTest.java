package snap.api.auth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import  org.mockito.junit.MockitoJUnitRunner;

import snap.api.config.SnapConfiguration;
import snap.api.config.SnapConfigurationBuilder;
import snap.api.exceptions.SnapAuthorizationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.mock;

/**
 * Unit tests mocked for SnapAuthorization.
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapAuthorizationTest {
    
	private SnapAuthorization auth;
	
	@Before
	public void setUp() {
		auth = mock(SnapAuthorization.class);
		SnapConfigurationBuilder configBuilder = new SnapConfigurationBuilder();
		configBuilder.setClientId("fake_client_id");
		configBuilder.setRedirectUri("fake_redirect_uri");
		SnapConfiguration config = configBuilder.build();
		this.auth.setConfiguration(config);
	}// setUp()
	
	@Test
	public void test_getOAuthAuthorizationURI_should_success() throws SnapAuthorizationException {
		Mockito.when(this.auth.getOAuthAuthorizationURI()).thenReturn("fake_redirect_uri?code=code_from_redirect_uri");
		String authorizationURI = this.auth.getOAuthAuthorizationURI();
		assertThat(authorizationURI).isNotEmpty();
		assertThat(authorizationURI).isNotBlank();
		assertThat(authorizationURI).contains("code=code_from_redirect_uri");
	}// test_getOAuthAuthorizationURI_should_success()
	
	@Test
	public void test_getOAuthAuthorizationURI_should_fail_noClientID() {
		SnapConfigurationBuilder configBuilder = new SnapConfigurationBuilder();
		configBuilder.setRedirectUri("fake_redirect_uri");
		SnapAuthorization sp = new SnapAuthorization(configBuilder.build());
		assertThatThrownBy(
	            () -> {
	            	sp.getOAuthAuthorizationURI();
	            }).isInstanceOf(SnapAuthorizationException.class)
        .hasMessageContaining("Empty client ID");
	}// test_getOAuthAuthorizationURI_should_fail_noClientID()
	
	@Test
	public void test_getOAuthAuthorizationURI_should_fail_noRedirectURI() {
		SnapConfigurationBuilder configBuilder = new SnapConfigurationBuilder();
		configBuilder.setClientId("fake_client_id");
		SnapAuthorization sp = new SnapAuthorization(configBuilder.build());
		assertThatThrownBy(
	            () -> {
	            	sp.getOAuthAuthorizationURI();
	            }).isInstanceOf(SnapAuthorizationException.class)
        .hasMessageContaining("Empty Redirect URI");
	}// test_getOAuthAuthorizationURI_should_fail_noRedirectURI()
	
	@Test
	public void test_getOAuthAuthorizationURI_should_fail_noConfiguration() {
		SnapAuthorization sp = new SnapAuthorization(null);
		assertThatThrownBy(
	            () -> {
	            	sp.getOAuthAuthorizationURI();
	            }).isInstanceOf(SnapAuthorizationException.class)
        .hasMessageContaining("Configuration unfound");
	}// test_getOAuthAuthorizationURI_should_fail_noConfiguration()
	
	@Test
	public void test_getOAuthAccessToken_should_success() {
		String oauthCode = "code_from_redirect_uri"; // Snap added 'code' attribute to redirect_uri
		Mockito.when(this.auth.getOAuthAccessToken(oauthCode)).thenReturn("meowmeowmeow");
		String oAuthAccessToken = this.auth.getOAuthAccessToken(oauthCode);
		assertThat(oAuthAccessToken).isNotEmpty();
		assertThat(oAuthAccessToken).isNotBlank();
		assertThat(oAuthAccessToken).isEqualTo("meowmeowmeow");
	}// test_getOAuthAccessToken_should_success()
	
}// SnapAuthorizationTest
