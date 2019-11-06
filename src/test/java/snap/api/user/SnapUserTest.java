package snap.api.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.user.AuthenticatedUser;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.SnapResponseUtils;

/** Unit tests mocked for SnapUser. */
@RunWith(MockitoJUnitRunner.class)
public class SnapUserTest {

    @Spy
    private SnapUser snapUser;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private StatusLine statusLine;

    @Mock
    private HttpEntity httpEntity;
    
    @Mock
    private EntityUtilsWrapper entityUtilsWrapper;

    private final String oAuthAccessToken = "meowmeowmeow";

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapUser.setHttpClient(httpClient);
	snapUser.setEntityUtilsWrapper(entityUtilsWrapper);
    } // setUp()

    @Test
    public void test_aboutMe_should_success()
	    throws SnapOAuthAccessTokenException, SnapResponseErrorException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAuthenticatedUser());
	Optional<AuthenticatedUser> optUser = snapUser.aboutMe(oAuthAccessToken);
	assertThat(optUser.isPresent()).isTrue();
	optUser.ifPresent(user -> {
	    assertThat(user.getDisplayName()).isEqualTo("Honey Badger");
	    assertThat(user.getEmail()).isEqualTo("honey.badger@hooli.com");
	    assertThat(user.getId()).isEqualTo("2f5dd7e6-fcd1-4324-8455-1ea4d96caaaa");
	    assertThat(user.getOrganizationId()).isEqualTo("40d6719b-da09-410b-9185-0cc9c0dfed1d");
	});
    } // test_aboutMe_should_success()

    @Test
    public void test_aboutMe_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapUser.aboutMe(null)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_aboutMe_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_aboutMe_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapUser.aboutMe("")).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_aboutMe_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void should_throw_exception_401_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_aboutMe()

    @Test
    public void should_throw_exception_403_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Access Forbidden");
    } // should_throw_exception_403_aboutMe()

    @Test
    public void should_throw_exception_404_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Not Found");
    } // should_throw_exception_404_aboutMe()

    @Test
    public void should_throw_exception_405_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Method Not Allowed");
    } // should_throw_exception_405_aboutMe()

    @Test
    public void should_throw_exception_406_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Not Acceptable");
    } // should_throw_exception_406_aboutMe()

    @Test
    public void should_throw_exception_410_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Gone");
    } // should_throw_exception_410_aboutMe()

    @Test
    public void should_throw_exception_418_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("I'm a teapot");
    } // should_throw_exception_418_aboutMe()

    @Test
    public void should_throw_exception_429_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_aboutMe()

    @Test
    public void should_throw_exception_500_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Internal Server Error");
    } // should_throw_exception_500_aboutMe()

    @Test
    public void should_throw_exception_503_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Service Unavailable");
    } // should_throw_exception_503_aboutMe()

    @Test
    public void should_throw_exception_1337_aboutMe() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
		.hasMessage("Error 1337");
    } // should_throw_exception_1337_aboutMe()
} // SnapUserTest
