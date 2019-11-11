package snap.api.adsquads;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import snap.api.enums.AdSquadTypeEnum;
import snap.api.enums.BillingEventEnum;
import snap.api.enums.OptimizationGoalEnum;
import snap.api.enums.PlacementEnum;
import snap.api.enums.StatusEnum;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.adsquads.AdSquad;
import snap.api.model.geolocation.GeoLocation;
import snap.api.model.targeting.Targeting;
import snap.api.model.targeting.TargetingBuilder;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.SnapResponseUtils;

/** Unit tests mocked for SnapAdSquads. */
@RunWith(MockitoJUnitRunner.class)
public class SnapAdSquadsTest {

    @Spy
    private SnapAdSquads sAdSquads;

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

    private final String id = "0633e159-0f41-4675-a0ba-224fbd70ac4d";

    private final String campaignId = "6cf25572-048b-4447-95d1-eb48231751be";

    private final String accountId = "16302efa-4c1f-4e36-b881-a395a36cef61";

    private AdSquad adSquad;

    private AdSquad adSquadErrosForCreation;

    private AdSquad adSquadForUpdate;

    private AdSquad adSquadErrosForUpdate;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	sAdSquads.setHttpClient(httpClient);
	sAdSquads.setEntityUtilsWrapper(entityUtilsWrapper);
	adSquad = initFunctionalAdSquad();
	adSquadErrosForCreation = initFunctionalAdSquad();
	adSquadForUpdate = initFunctionalAdSquad();
	adSquadErrosForUpdate = initFunctionalAdSquad();
	adSquadForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	adSquadForUpdate.setId("aeo300f");
	adSquadErrosForUpdate.setId(adSquadForUpdate.getId());
    } // setUp()

    @Test
    public void test_create_ad_squad_should_success() throws IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Assertions.assertThatCode(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad)).doesNotThrowAnyException();
    }// test_create_ad_squad_should_success()

    @Test
    public void test_create_ad_squad_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sAdSquads.createAdSquad(null, adSquad))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_create_ad_squad_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_ad_squad_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sAdSquads.createAdSquad("", adSquad)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_create_ad_squad_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_create_ad_squad_should_throw_IOException() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
  	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
  	sAdSquads.createAdSquad(oAuthAccessToken, adSquad);
    }// test_create_ad_squad_should_throw_IOException()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Ad squad parameter is not given");
    } // test_create_ad_squad_should_throw_SnapArgumentException_1()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_2() {
	adSquadErrosForCreation.setOptimizationGoal(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The optimization goal is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_2()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_3() {
	adSquadErrosForCreation.setPlacement(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The placement is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_3()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_4() {
	adSquadErrosForCreation.setCampaignId("");
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Campaign ID is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_4()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_5() {
	adSquadErrosForCreation.setBidMicro(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The bid micro is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_5()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_6() {
	adSquadErrosForCreation.setDailyBudgetMicro(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The daily budget micro is required");
	adSquadErrosForCreation.setDailyBudgetMicro(1.);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("The daily budget micro minimum value is 20000000");
    } // test_create_ad_squad_should_throw_SnapArgumentException_6()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_7() {
	adSquadErrosForCreation.setLifetimeBudgetMicro(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The lifetime budget micro is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_7()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_8() {
	adSquadErrosForCreation.setType(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The type is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_8()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_9() {
	adSquadErrosForCreation.setName("");
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Squad name is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_9()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_10() {
	adSquadErrosForCreation.setStatus(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The status is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_10()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_11() {
	adSquadErrosForCreation.setTargeting(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The targeting is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_11()

    @Test
    public void test_create_ad_squad_should_throw_SnapArgumentException_12() {
	adSquadErrosForCreation.setType(null);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquadErrosForCreation))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The type is required");
    } // test_create_ad_squad_should_throw_SnapArgumentException_12()

    @Test
    public void should_throw_exception_401_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_ad_squad()

    @Test
    public void should_throw_exception_403_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_ad_squad()

    @Test
    public void should_throw_exception_404_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_ad_squad()

    @Test
    public void should_throw_exception_405_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_ad_squad()

    @Test
    public void should_throw_exception_406_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_ad_squad()

    @Test
    public void should_throw_exception_410_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_ad_squad()

    @Test
    public void should_throw_exception_418_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_ad_squad()

    @Test
    public void should_throw_exception_429_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_ad_squad()

    @Test
    public void should_throw_exception_500_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_ad_squad()

    @Test
    public void should_throw_exception_503_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_ad_squad()

    @Test
    public void should_throw_exception_1337_create_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.createAdSquad(oAuthAccessToken, adSquad))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_ad_squad()

    @Test
    public void test_update_ad_squad_should_success() throws IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	Assertions.assertThatCode(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.doesNotThrowAnyException();
    }// test_update_ad_squad_should_success()

    @Test
    public void test_update_ad_squad_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(null, adSquad))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_update_ad_squad_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_update_ad_squad_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sAdSquads.updateAdSquad("", adSquad)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_update_ad_squad_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_update_ad_squad_should_throw_IOException() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
  	Mockito.when(httpClient.execute((Mockito.any(HttpPut.class)))).thenThrow(IOException.class);
  	sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate);
    }// test_update_ad_squad_should_throw_IOException()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Ad squad parameter is not given");
    } // test_update_ad_squad_should_throw_SnapArgumentException_1()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_2() {
	adSquadErrosForUpdate.setBillingEvent(null);
	adSquadErrosForUpdate.setId("toto");
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Billing event is required");
    } // test_update_ad_squad_should_throw_SnapArgumentException_2()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_3() {
	adSquadErrosForUpdate.setId(null);
	adSquadErrosForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Squad ID is required");
    } // test_update_ad_squad_should_throw_SnapArgumentException_3()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_4() {
	adSquadErrosForUpdate.setCampaignId("");
	adSquadErrosForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	adSquadErrosForUpdate.setId("aeo300");
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Campaign ID is required");
    } // test_update_ad_squad_should_throw_SnapArgumentException_4()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_5() {
	adSquadErrosForUpdate.setBidMicro(null);
	adSquadErrosForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	adSquadErrosForUpdate.setId("aeo300");
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The bid micro is required");
    } // test_update_ad_squad_should_throw_SnapArgumentException_5()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_6() {
	adSquadErrosForUpdate.setDailyBudgetMicro(null);
	adSquadErrosForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	adSquadErrosForUpdate.setId("aeo300");
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The daily budget micro is required");
	adSquadErrosForUpdate.setDailyBudgetMicro(1.);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("The daily budget micro minimum value is 20000000");
    } // test_update_ad_squad_should_throw_SnapArgumentException_6()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_7() {
	adSquadErrosForUpdate.setLifetimeBudgetMicro(null);
	adSquadErrosForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	adSquadErrosForUpdate.setId("aeo300");
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The lifetime budget micro is required");
    } // test_update_ad_squad_should_throw_SnapArgumentException_7()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_8() {
	adSquadErrosForUpdate.setName("");
	adSquadErrosForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	adSquadErrosForUpdate.setId("aeo300");
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Squad name is required");
    } // test_update_ad_squad_should_throw_SnapArgumentException_8()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_9() {
	adSquadErrosForUpdate.setStatus(null);
	adSquadErrosForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	adSquadErrosForUpdate.setId("aeo300");
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The status is required");
    } // test_update_ad_squad_should_throw_SnapArgumentException_9()

    @Test
    public void test_update_ad_squad_should_throw_SnapArgumentException_10() {
	adSquadErrosForUpdate.setTargeting(null);
	adSquadErrosForUpdate.setBillingEvent(BillingEventEnum.IMPRESSION);
	adSquadErrosForUpdate.setId("aeo300");
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadErrosForUpdate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The targeting is required");
    } // test_update_ad_squad_should_throw_SnapArgumentException_10()

    @Test
    public void should_throw_exception_401_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_update_ad_squad()

    @Test
    public void should_throw_exception_403_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_update_ad_squad()

    @Test
    public void should_throw_exception_404_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_update_ad_squad()

    @Test
    public void should_throw_exception_405_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_update_ad_squad()

    @Test
    public void should_throw_exception_406_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_update_ad_squad()

    @Test
    public void should_throw_exception_410_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_update_ad_squad()

    @Test
    public void should_throw_exception_418_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_update_ad_squad()

    @Test
    public void should_throw_exception_429_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_update_ad_squad()

    @Test
    public void should_throw_exception_500_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_update_ad_squad()

    @Test
    public void should_throw_exception_503_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_update_ad_squad()

    @Test
    public void should_throw_exception_1337_update_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.updateAdSquad(oAuthAccessToken, adSquadForUpdate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_update_ad_squad()

    @Test
    public void test_delete_ad_squad_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
	    SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	Assertions.assertThatCode(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id)).doesNotThrowAnyException();
    } // test_delete_ad_squad_should_success()

    @Test
    public void test_delete_ad_squad_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(null, id)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_delete_ad_squad_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_delete_ad_squad_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad("", id)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_delete_ad_squad_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_delete_ad_squad_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Squad ID is mandatory");
    } // test_delete_ad_squad_should_throw_SnapArgumentException_1()

    @Test
    public void test_delete_ad_squad_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Squad ID is mandatory");
    } // test_delete_ad_squad_should_throw_SnapArgumentException_2()
    
    @Test
    public void test_delete_ad_squad_should_throw_IOException() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
  	Mockito.when(httpClient.execute((Mockito.any(HttpDelete.class)))).thenThrow(IOException.class);
  	sAdSquads.deleteAdSquad(oAuthAccessToken, id);
    }// test_delete_ad_squad_should_throw_IOException()

    @Test
    public void should_throw_exception_401_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_delete_ad_squad()

    @Test
    public void should_throw_exception_403_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_delete_ad_squad()

    @Test
    public void should_throw_exception_404_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_delete_ad_squad()

    @Test
    public void should_throw_exception_405_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_delete_ad_squad()

    @Test
    public void should_throw_exception_406_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_delete_ad_squad()

    @Test
    public void should_throw_exception_410_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_delete_ad_squad()

    @Test
    public void should_throw_exception_418_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_delete_ad_squad()

    @Test
    public void should_throw_exception_429_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_delete_ad_squad()

    @Test
    public void should_throw_exception_500_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_delete_ad_squad()

    @Test
    public void should_throw_exception_503_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_delete_ad_squad()

    @Test
    public void should_throw_exception_1337_delete_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sAdSquads.deleteAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_delete_ad_squad()

    @Test
    public void test_getSpecificAdSquad_should_success() throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapSpecificAdSquad());
	final String id = "23995202-bfbc-45a0-9702-dd6841af52fe";
	Optional<AdSquad> optAdSquad = sAdSquads.getSpecificAdSquad(oAuthAccessToken, id);
	assertThat(optAdSquad.isPresent()).isTrue();
	optAdSquad.ifPresent(f -> {
	    assertThat(f.getId()).isEqualTo(id);
	    assertThat(f.getName()).isEqualTo("Ad Squad Uno");
	    assertThat(f.getCampaignId()).isEqualTo(campaignId);
	    assertThat(f.getStatus()).isEqualTo(StatusEnum.ACTIVE);
	    assertThat(f.getType()).isEqualTo(AdSquadTypeEnum.SNAP_ADS);
	    assertThat(f.getPlacement()).isEqualTo(PlacementEnum.SNAP_ADS);
	    assertThat(f.getBillingEvent()).isEqualTo(BillingEventEnum.IMPRESSION);
	    assertThat(f.getBidMicro()).isEqualTo(1000000);
	    assertThat(f.getDailyBudgetMicro()).isEqualTo(1000000000);
	    assertThat(f.getOptimizationGoal()).isEqualTo(OptimizationGoalEnum.IMPRESSIONS);
	    assertThat(f.getTargeting()).isNotNull();
	    Targeting target = f.getTargeting();
	    assertThat(target.getGeos()).isNotNull();
	    assertThat(target.getGeos()).hasSize(1);
	    List<GeoLocation> geos = target.getGeos();
	    GeoLocation geoUs = geos.get(0);
	    assertThat(geoUs.getCountryCode().equals("us"));
	});
    } // test_getSpecificAdSquad_should_success()

    @Test
    public void test_getSpecificAdSquad_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(null, id))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getSpecificAdSquad_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_getSpecificAdSquad_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad("", id)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_getSpecificAdSquad_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_getSpecificAdSquad_should_throw_IOException() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
  	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
  	sAdSquads.getSpecificAdSquad(oAuthAccessToken, id);
    }// test_getSpecificAdSquad_should_throw_IOException()

    @Test
    public void test_getSpecificAdSquad_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The AdSquad ID is mandatory");
    } // test_getSpecificAdSquad_should_throw_SnapArgumentException_1()

    @Test
    public void test_getSpecificAdAccount_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The AdSquad ID is mandatory");
    } // test_getSpecificAdAccount_should_throw_SnapArgumentException_2()

    @Test
    public void should_throw_exception_401_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getSpecificAdSquad()

    @Test
    public void should_throw_exception_403_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_getSpecificAdSquad()

    @Test
    public void should_throw_exception_404_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_getSpecificAdSquad()

    @Test
    public void should_throw_exception_405_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getSpecificAdSquad()

    @Test
    public void should_throw_exception_406_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_getSpecificAdSquad()

    @Test
    public void should_throw_exception_410_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_getSpecificAdSquad()

    @Test
    public void should_throw_exception_418_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_getSpecificAdSquad()

    @Test
    public void should_throw_exception_429_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getSpecificAdSquad()

    @Test
    public void should_throw_exception_500_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_getSpecificAdSquad()

    @Test
    public void should_throw_exception_503_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_getSpecificAdSquad()

    @Test
    public void should_throw_exception_1337_getSpecificAdSquad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getSpecificAdSquad(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_getSpecificAdSquad()

    @Test
    public void test_getAllAdSquads_Campaign_should_success() throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAllAdSquadForCampaign());

	List<AdSquad> adSquads = sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId);
	assertThat(adSquads).isNotNull();
	assertThat(adSquads).isNotEmpty();
	assertThat(adSquads).hasSize(2);

	assertThat(adSquads.get(0).getId()).isEqualTo("0633e159-0f41-4675-a0ba-224fbd70ac4d");
	assertThat(adSquads.get(0).getStatus()).isEqualTo(StatusEnum.PAUSED);
	assertThat(adSquads.get(0).getName()).isEqualTo("Ad Squad Apples");
	assertThat(adSquads.get(0).getCampaignId()).isEqualTo(campaignId);
	assertThat(adSquads.get(0).getDailyBudgetMicro()).isEqualTo(1000000000.);
	assertThat(adSquads.get(0).getType()).isEqualTo(AdSquadTypeEnum.SNAP_ADS);
	assertThat(adSquads.get(0).getPlacement()).isEqualTo(PlacementEnum.SNAP_ADS);
	assertThat(adSquads.get(0).getBillingEvent()).isEqualTo(BillingEventEnum.IMPRESSION);
	assertThat(adSquads.get(0).getOptimizationGoal()).isEqualTo(OptimizationGoalEnum.IMPRESSIONS);
	assertThat(adSquads.get(0).getBidMicro()).isEqualTo(1000000.);

	assertThat(adSquads.get(1).getId()).isEqualTo("23995202-bfbc-45a0-9702-dd6841af52fe");
	assertThat(adSquads.get(1).getStatus()).isEqualTo(StatusEnum.ACTIVE);
	assertThat(adSquads.get(1).getName()).isEqualTo("Ad Squad Uno");
	assertThat(adSquads.get(1).getCampaignId()).isEqualTo(campaignId);
	assertThat(adSquads.get(1).getDailyBudgetMicro()).isEqualTo(1000000000.);
	assertThat(adSquads.get(1).getType()).isEqualTo(AdSquadTypeEnum.SNAP_ADS);
	assertThat(adSquads.get(1).getPlacement()).isEqualTo(PlacementEnum.SNAP_ADS);
	assertThat(adSquads.get(1).getBillingEvent()).isEqualTo(BillingEventEnum.IMPRESSION);
	assertThat(adSquads.get(1).getOptimizationGoal()).isEqualTo(OptimizationGoalEnum.IMPRESSIONS);
	assertThat(adSquads.get(1).getBidMicro()).isEqualTo(1000000.);
	assertThat(adSquads.get(1).getTargeting()).isNotNull();
	assertThat(adSquads.get(1).getTargeting().getGeos()).isNotNull();
	assertThat(adSquads.get(1).getTargeting().getGeos()).hasSize(1);
	assertThat(adSquads.get(1).getTargeting().getGeos().get(0).getCountryCode()).isEqualTo("us");
    } // test_getAllAdSquads_Campaign_should_success()

    @Test
    public void test_getAllAdSquads_Campaign_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(null, campaignId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getAllAdSquads_Campaign_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_getAllAdSquads_Campaign_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign("", campaignId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getAllAdSquads_Campaign_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_getAllAdSquads_Campaign_should_throw_IOException() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
  	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
  	sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId);
    }// test_getAllAdSquads_Campaign_should_throw_IOException()
    
    @Test
    public void test_getAllAdSquads_Campaign_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Campaign ID is mandatory");
    } // test_getAllAdSquads_Campaign_should_throw_SnapArgumentException_1()

    @Test
    public void test_getAllAdSquads_Campaign_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Campaign ID is mandatory");
    } // test_getAllAdSquads_Campaign_should_throw_SnapArgumentException_2()

    @Test
    public void should_throw_exception_401_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_403_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_404_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_405_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_406_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_410_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_418_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_429_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_500_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_503_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_getAllAdSquads_Campaign()

    @Test
    public void should_throw_exception_1337_getAllAdSquads_Campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_getAllAdSquads_Campaign()

    @Test
    public void test_getAllAdSquads_AdAccount_should_success() throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAllAdSquadForAdAccount());
	List<AdSquad> adSquads = sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId);
	assertThat(adSquads).isNotNull();
	assertThat(adSquads).isNotEmpty();
	assertThat(adSquads).hasSize(2);

	assertThat(adSquads.get(0).getId()).isEqualTo("0633e159-0f41-4675-a0ba-224fbd70ac4d");
	assertThat(adSquads.get(0).getStatus()).isEqualTo(StatusEnum.PAUSED);
	assertThat(adSquads.get(0).getName()).isEqualTo("Ad Squad Apples");
	assertThat(adSquads.get(0).getCampaignId()).isEqualTo(campaignId);
	assertThat(adSquads.get(0).getDailyBudgetMicro()).isEqualTo(1000000000.);
	assertThat(adSquads.get(0).getType()).isEqualTo(AdSquadTypeEnum.SNAP_ADS);
	assertThat(adSquads.get(0).getPlacement()).isEqualTo(PlacementEnum.SNAP_ADS);
	assertThat(adSquads.get(0).getBillingEvent()).isEqualTo(BillingEventEnum.IMPRESSION);
	assertThat(adSquads.get(0).getOptimizationGoal()).isEqualTo(OptimizationGoalEnum.IMPRESSIONS);
	assertThat(adSquads.get(0).getBidMicro()).isEqualTo(1000000.);

	assertThat(adSquads.get(1).getId()).isEqualTo("23995202-bfbc-45a0-9702-dd6841af52fe");
	assertThat(adSquads.get(1).getStatus()).isEqualTo(StatusEnum.ACTIVE);
	assertThat(adSquads.get(1).getName()).isEqualTo("Ad Squad Uno");
	assertThat(adSquads.get(1).getCampaignId()).isEqualTo(campaignId);
	assertThat(adSquads.get(1).getDailyBudgetMicro()).isEqualTo(1000000000.);
	assertThat(adSquads.get(1).getType()).isEqualTo(AdSquadTypeEnum.SNAP_ADS);
	assertThat(adSquads.get(1).getPlacement()).isEqualTo(PlacementEnum.SNAP_ADS);
	assertThat(adSquads.get(1).getBillingEvent()).isEqualTo(BillingEventEnum.IMPRESSION);
	assertThat(adSquads.get(1).getOptimizationGoal()).isEqualTo(OptimizationGoalEnum.IMPRESSIONS);
	assertThat(adSquads.get(1).getBidMicro()).isEqualTo(1000000.);
	assertThat(adSquads.get(1).getTargeting()).isNotNull();
	assertThat(adSquads.get(1).getTargeting().getGeos()).isNotNull();
	assertThat(adSquads.get(1).getTargeting().getGeos()).hasSize(1);
	assertThat(adSquads.get(1).getTargeting().getGeos().get(0).getCountryCode()).isEqualTo("us");
    } // test_getAllAdSquads_AdAccount_should_success()

    @Test
    public void test_getAllAdSquads_AdAccount_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount("", accountId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getAllAdSquads_AdAccount_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_getAllAdSquads_AdAccount_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(null, accountId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getAllAdSquads_AdAccount_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_getAllAdSquads_AdAccount_should_throw_IOException() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
  	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
  	sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId);
    }// test_getAllAdSquads_AdAccount_should_throw_IOException()
    
    @Test
    public void test_getAllAdSquads_AdAccount_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The AdAccount ID is mandatory");
    } // test_getAllAdSquads_AdAccount_should_throw_SnapArgumentException_1()

    @Test
    public void test_getAllAdSquads_AdAccount_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The AdAccount ID is mandatory");
    } // test_getAllAdSquads_AdAccount_should_throw_SnapArgumentException_2()

    @Test
    public void should_throw_exception_401_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_403_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_404_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_405_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_406_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_410_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_418_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_429_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_500_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_503_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_getAllAdSquads_AdAccount()

    @Test
    public void should_throw_exception_1337_getAllAdSquads_AdAccount() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sAdSquads.getAllAdSquadsFromAdAccount(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_getAllAdSquads_AdAccount()

    private AdSquad initFunctionalAdSquad() {
	TargetingBuilder targetBuilder = new TargetingBuilder();
	targetBuilder.setRegulatedContent(true);
	AdSquad a = new AdSquad();
	a.setOptimizationGoal(OptimizationGoalEnum.APP_SIGNUP);
	a.setPlacement(PlacementEnum.CONTENT);
	a.setType(AdSquadTypeEnum.SNAP_ADS);
	a.setCampaignId("campaign2019");
	a.setBidMicro(1000000.);
	a.setDailyBudgetMicro(20000000.);
	a.setLifetimeBudgetMicro(300.);
	a.setName("AdSquad2019");
	a.setStatus(StatusEnum.ACTIVE);
	a.setTargeting(targetBuilder.build());
	return a;
    }// initFunctionalAdSquad()
} // SnapAdSquadsTest
