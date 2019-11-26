package snapads4j.creatives.elements;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.creatives.elements.InteractionZone;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

public class SnapCreativeElementTest {
    
    @Spy
    private SnapCreativeElement snapCreative;
    
    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private StatusLine statusLine;
    
    @Mock
    private EntityUtilsWrapper entityUtilsWrapper;

    @Mock
    private HttpEntity httpEntity;

    private final String oAuthAccessToken = "meowmeowmeow";

    private final String adAccountID = "8adc3db7-8148-4fbf-999c-8d2266369d74";
    
    private InteractionZone interactionZone;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapCreative.setHttpClient(httpClient);
	snapCreative.setEntityUtilsWrapper(entityUtilsWrapper);
	interactionZone = initInteractionZone();
    }// setUp()
    
    @Test
    public void test_create_interaction_zone_should_success() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapCreationInteractionZone());
	Assertions.assertThatCode(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone)).doesNotThrowAnyException();
	Optional<InteractionZone> optInteraction = snapCreative.createInteractionZone(oAuthAccessToken, interactionZone);
	Assertions.assertThat(optInteraction.isPresent()).isEqualTo(true);
	optInteraction.ifPresent(zone -> {
	    Assertions.assertThat(zone.toString()).isNotEmpty();
	    Assertions.assertThat(zone.getId()).isEqualTo("a218dc8b-7a79-4da6-9a1c-e5a581c7bd46");
	    Assertions.assertThat(zone.getAdAccountId()).isEqualTo(interactionZone.getAdAccountId());
	    Assertions.assertThat(zone.getName()).isEqualTo(interactionZone.getName());
	    Assertions.assertThat(zone.getHeadline()).isEqualTo(interactionZone.getHeadline());
	    Assertions.assertThat(zone.getCreativeElements()).isNotEmpty();
	    Assertions.assertThat(zone.getCreativeElements()).isNotNull();
	    Assertions.assertThat(zone.getCreativeElements()).size().isEqualTo(interactionZone.getCreativeElements().size());
	    for(int i = 0; i < zone.getCreativeElements().size(); ++i) {
		Assertions.assertThat(zone.getCreativeElements().get(i)).isEqualTo(interactionZone.getCreativeElements().get(i));
	    }
	});
    }// test_create_interaction_zone_should_success()
    
    @Test
    public void test_create_interaction_zone_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapCreative.createInteractionZone("", interactionZone))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_create_interaction_zone_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_interaction_zone_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapCreative.createInteractionZone(null, interactionZone))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_create_interaction_zone_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_interaction_zone_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Interaction Zone parameter is not given");
    }// test_create_interaction_zone_should_throw_SnapArgumentException_1()
    
    @Test
    public void test_create_interaction_zone_should_throw_SnapArgumentException_2() {
	InteractionZone badZone = new InteractionZone();
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, badZone))
	.isInstanceOf(SnapArgumentException.class).hasMessage("The interaction zone's ad account id is required,The interaction zone's headline is required,The interaction zone's name is required,The interaction zone's creative elements is required");
    }// test_create_interaction_zone_should_throw_SnapArgumentException_2()
    
    @Test
    public void test_create_interaction_zone_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	snapCreative.createInteractionZone(oAuthAccessToken, interactionZone);
    }// test_create_interaction_zone_should_throw_IOException()
    
    @Test
    public void should_throw_exception_400_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_create_interaction_zone()

    @Test
    public void should_throw_exception_401_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_interaction_zone()

    @Test
    public void should_throw_exception_403_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_interaction_zone()

    @Test
    public void should_throw_exception_404_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_interaction_zone()

    @Test
    public void should_throw_exception_405_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_interaction_zone()

    @Test
    public void should_throw_exception_406_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_interaction_zone()

    @Test
    public void should_throw_exception_410_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_interaction_zone()

    @Test
    public void should_throw_exception_418_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_interaction_zone()

    @Test
    public void should_throw_exception_429_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_interaction_zone()

    @Test
    public void should_throw_exception_500_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_interaction_zone()

    @Test
    public void should_throw_exception_503_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_interaction_zone()

    @Test
    public void should_throw_exception_1337_create_interaction_zone() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapCreative.createInteractionZone(oAuthAccessToken, interactionZone))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_interaction_zone()
    
    private InteractionZone initInteractionZone() {
	InteractionZone zone = new InteractionZone();
	zone.setAdAccountId(adAccountID);
	zone.setHeadline("MORE");
	zone.setName("First Interaction Zone");
	List<String> ids = new ArrayList<>();
	ids.add("70debf44-cb4b-4b5f-8828-bd2b68b9f0cf");
	ids.add("a2d1c8a0-0466-4924-b769-7a7e6ed5be3b");
	ids.add("4091233e-3351-405d-8684-a97e70c3b5dc");
	ids.add("f63bb5f5-471c-404f-8f0d-e5c1a003e4d9");
	zone.setCreativeElements(ids);
	return zone;
    }// initInteractionZone()
    
}// SnapCreativeElementTest
