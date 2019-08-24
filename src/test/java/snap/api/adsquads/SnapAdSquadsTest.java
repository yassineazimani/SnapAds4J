package snap.api.adsquads;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import org.assertj.core.api.Assertions;
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

/** Unit tests mocked for SnapAdSquads. */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class SnapAdSquadsTest {

  @Spy private SnapAdSquads sAdSquads;

  @Mock private HttpClient httpClient;

  @Mock private HttpResponse<String> httpResponse;

  private final String oAuthAccessToken = "meowmeowmeow";

  private final String accountId = "8adc3db7-8148-4fbf-999c-8d2266369d74";

  private final String id = "0633e159-0f41-4675-a0ba-224fbd70ac4d";

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    sAdSquads.setHttpClient(httpClient);
  } // setUp()

  @Test
  public void test_delete_ad_squad_should_success()
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
          IOException, InterruptedException {
    Mockito.when(httpResponse.statusCode()).thenReturn(200);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    Assertions.assertThatCode(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .doesNotThrowAnyException();
  } // test_delete_ad_squad_should_success()

  @Test
  public void test_delete_ad_squad_should_throw_SnapOAuthAccessTokenException_1() {
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(null, id))
        .isInstanceOf(SnapOAuthAccessTokenException.class)
        .hasMessage("The OAuthAccessToken must to be given");
  } // test_delete_ad_squad_should_throw_SnapOAuthAccessTokenException_1()

  @Test
  public void test_delete_ad_squad_should_throw_SnapOAuthAccessTokenException_2() {
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad("", id))
        .isInstanceOf(SnapOAuthAccessTokenException.class)
        .hasMessage("The OAuthAccessToken must to be given");
  } // test_delete_ad_squad_should_throw_SnapOAuthAccessTokenException_2()

  @Test
  public void test_delete_ad_squad_should_throw_SnapArgumentException_1() {
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, null))
        .isInstanceOf(SnapArgumentException.class)
        .hasMessage("The Ad Squad ID is mandatory");
  } // test_delete_ad_squad_should_throw_SnapArgumentException_1()

  @Test
  public void test_delete_ad_squad_should_throw_SnapArgumentException_2() {
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, ""))
        .isInstanceOf(SnapArgumentException.class)
        .hasMessage("The Ad Squad ID is mandatory");
  } // test_delete_ad_squad_should_throw_SnapArgumentException_2()

  @Test
  public void should_throw_exception_401_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(401);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Unauthorized - Check your API key");
  } // should_throw_exception_401_delete_ad_squad()

  @Test
  public void should_throw_exception_403_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {

    Mockito.when(httpResponse.statusCode()).thenReturn(403);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Access Forbidden");
  } // should_throw_exception_403_delete_ad_squad()

  @Test
  public void should_throw_exception_404_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {

    Mockito.when(httpResponse.statusCode()).thenReturn(404);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Not Found");
  } // should_throw_exception_404_delete_ad_squad()

  @Test
  public void should_throw_exception_405_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(405);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Method Not Allowed");
  } // should_throw_exception_405_delete_ad_squad()

  @Test
  public void should_throw_exception_406_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(406);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Not Acceptable");
  } // should_throw_exception_406_delete_ad_squad()

  @Test
  public void should_throw_exception_410_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(410);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Gone");
  } // should_throw_exception_410_delete_ad_squad()

  @Test
  public void should_throw_exception_418_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(418);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("I'm a teapot");
  } // should_throw_exception_418_delete_ad_squad()

  @Test
  public void should_throw_exception_429_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(429);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Too Many Requests / Rate limit reached");
  } // should_throw_exception_429_delete_ad_squad()

  @Test
  public void should_throw_exception_500_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(500);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Internal Server Error");
  } // should_throw_exception_500_delete_ad_squad()

  @Test
  public void should_throw_exception_503_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(503);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Service Unavailable");
  } // should_throw_exception_503_delete_ad_squad()

  @Test
  public void should_throw_exception_1337_delete_ad_squad()
      throws IOException, InterruptedException, SnapResponseErrorException,
          SnapOAuthAccessTokenException, SnapArgumentException {
    Mockito.when(httpResponse.statusCode()).thenReturn(1337);
    Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
        .thenReturn(httpResponse);
    assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
        .isInstanceOf(SnapResponseErrorException.class)
        .hasMessage("Error 1337");
  } // should_throw_exception_1337_delete_ad_squad()
} // SnapAdSquadsTest
