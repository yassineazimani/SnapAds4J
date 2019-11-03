package snap.api.ads;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import snap.api.enums.AdTypeEnum;
import snap.api.enums.StatusEnum;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.ads.Ad;
import snap.api.utils.SnapResponseUtils;

/** Unit tests mocked for SnapAdTest. */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class SnapAdTest {

	@Spy
	private SnapAd ad;

	@Mock
	private HttpClient httpClient;

	@Mock
	private HttpResponse<String> httpResponse;

	private final String oAuthAccessToken = "meowmeowmeow";

	private final String idAdToDelete = "2ded6d53-0805-4ff8-b984-54a7eb5c8918";

	private final String id = "e8d6217f-32ab-400f-9e54-39a86a7963e4";

	private final String squadId = "23995202-bfbc-45a0-9702-dd6841af52fe";
	
	private final String accountId = "v8d6217f-32ab-400f-9e54-39a86a7963e0";
	
	private final String creativeId = "c1e6e929-acec-466f-b023-852b8cacc18f";

	private Ad adModel;
	
	private Ad adModelUpdate;
	
	private Ad adModelCreateFailure;
	
	private Ad adModelUpdateFailure;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ad.setHttpClient(httpClient);
		adModel = initFunctionalAd(null);
		adModelUpdate = initFunctionalAd("e8d6217f-32ab-400f-9e54-39a86a7963e4");
		adModelCreateFailure = initFunctionalAd(null);
		adModelUpdateFailure = initFunctionalAd("e8d6217f-32ab-400f-9e54-39a86a7963e4");
	}// setUp()

	@Test
	public void test_create_ad_should_success() throws IOException, InterruptedException {
		Mockito.when(httpResponse.statusCode()).thenReturn(200);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		Assertions.assertThatCode(() -> ad.createAd(oAuthAccessToken, adModel)).doesNotThrowAnyException();
	}// test_create_ad_should_success()

	@Test
	public void test_create_ad_should_throw_SnapOAuthAccessTokenException_1() {
		assertThatThrownBy(() -> ad.createAd(null, adModel)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	}// test_create_ad_should_throw_SnapOAuthAccessTokenException_1()

	@Test
	public void test_create_ad_should_throw_SnapOAuthAccessTokenException_2() {
		assertThatThrownBy(() -> ad.createAd("", adModel)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	}// test_create_ad_should_throw_SnapOAuthAccessTokenException_2()
	
	@Test
	public void test_create_ad_should_throw_SnapArgumentException_1() {
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, null)).isInstanceOf(SnapArgumentException.class)
			.hasMessage("Ad parameter is not given");
	}// test_create_ad_should_throw_SnapArgumentException_1()
	
	@Test
	public void test_create_ad_should_throw_SnapArgumentException_2() {
		adModelCreateFailure.setAdSquadId(null);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModelCreateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad Squad ID parameter is not given");
	}// test_create_ad_should_throw_SnapArgumentException_2()
	
	@Test
	public void test_create_ad_should_throw_SnapArgumentException_3() {
		adModelCreateFailure.setName(null);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModelCreateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad's name parameter is not given");
	}// test_create_ad_should_throw_SnapArgumentException_3()
	
	@Test
	public void test_create_ad_should_throw_SnapArgumentException_4() {
		adModelCreateFailure.setName("");
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModelCreateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad's name parameter is not given");
	}// test_create_ad_should_throw_SnapArgumentException_4()
	
	@Test
	public void test_create_ad_should_throw_SnapArgumentException_5() {
		adModelCreateFailure.setStatus(null);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModelCreateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad's status parameter is not given");
	}// test_create_ad_should_throw_SnapArgumentException_5()

	@Test
	public void should_throw_exception_401_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(401);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Unauthorized - Check your API key");
	}// should_throw_exception_401_create_ad()

	@Test
	public void should_throw_exception_403_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(403);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Access Forbidden");
	}// should_throw_exception_403_create_ad()

	@Test
	public void should_throw_exception_404_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(404);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Not Found");
	}// should_throw_exception_404_create_ad()

	@Test
	public void should_throw_exception_405_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(405);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Method Not Allowed");
	}// should_throw_exception_405_create_ad()

	@Test
	public void should_throw_exception_406_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(406);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Not Acceptable");
	}// should_throw_exception_406_create_ad()

	@Test
	public void should_throw_exception_410_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(410);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Gone");
	}// should_throw_exception_410_create_ad()

	@Test
	public void should_throw_exception_418_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(418);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("I'm a teapot");
	}// should_throw_exception_418_create_ad()

	@Test
	public void should_throw_exception_429_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(429);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Too Many Requests / Rate limit reached");
	}// should_throw_exception_429_create_ad()

	@Test
	public void should_throw_exception_500_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(500);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Internal Server Error");
	}// should_throw_exception_500_create_ad()

	@Test
	public void should_throw_exception_503_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(503);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Service Unavailable");
	}// should_throw_exception_503_create_ad()

	@Test
	public void should_throw_exception_1337_create_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(1337);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Error 1337");
	}// should_throw_exception_1337_create_ad()

	@Test
	public void test_update_ad_should_success() throws IOException, InterruptedException {
		Mockito.when(httpResponse.statusCode()).thenReturn(200);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		Assertions.assertThatCode(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).doesNotThrowAnyException();
	}// test_update_ad_should_success()

	@Test
	public void test_update_ad_should_throw_SnapOAuthAccessTokenException_1() {
		assertThatThrownBy(() -> ad.updateAd(null, adModel)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	}// test_update_ad_should_throw_SnapOAuthAccessTokenException_1()

	@Test
	public void test_update_ad_should_throw_SnapOAuthAccessTokenException_2() {
		assertThatThrownBy(() -> ad.updateAd("", adModel)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	}// test_update_ad_should_throw_SnapOAuthAccessTokenException_2()
	
	@Test
	public void test_update_ad_should_throw_SnapArgumentException_1() {
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, null)).isInstanceOf(SnapArgumentException.class)
			.hasMessage("Ad parameter is not given");
	}// test_update_ad_should_throw_SnapArgumentException_1()
	
	@Test
	public void test_update_ad_should_throw_SnapArgumentException_2() {
		adModelUpdateFailure.setAdSquadId(null);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad Squad ID parameter is not given");
	}// test_update_ad_should_throw_SnapArgumentException_2()
	
	@Test
	public void test_update_ad_should_throw_SnapArgumentException_3() {
		adModelUpdateFailure.setName(null);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad's name parameter is not given");
	}// test_update_ad_should_throw_SnapArgumentException_3()
	
	@Test
	public void test_update_ad_should_throw_SnapArgumentException_4() {
		adModelUpdateFailure.setName("");
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad's name parameter is not given");
	}// test_update_ad_should_throw_SnapArgumentException_4()
	
	@Test
	public void test_update_ad_should_throw_SnapArgumentException_5() {
		adModelUpdateFailure.setAdSquadId(squadId);
		adModelUpdateFailure.setStatus(null);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad's status parameter is not given");
	}// test_create_ad_should_throw_SnapArgumentException_5()
	
	@Test
	public void test_update_ad_should_throw_SnapArgumentException_6() {
		adModelUpdateFailure.setId(null);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure)).isInstanceOf(SnapArgumentException.class)
		.hasMessage("The Ad ID is required");
	}// test_create_ad_should_throw_SnapArgumentException_6()

	@Test
	public void should_throw_exception_401_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(401);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Unauthorized - Check your API key");
	}// should_throw_exception_401_update_ad()

	@Test
	public void should_throw_exception_403_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(403);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Access Forbidden");
	}// should_throw_exception_403_update_ad()

	@Test
	public void should_throw_exception_404_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(404);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Not Found");
	}// should_throw_exception_404_update_ad()

	@Test
	public void should_throw_exception_405_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(405);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Method Not Allowed");
	}// should_throw_exception_405_update_ad()

	@Test
	public void should_throw_exception_406_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(406);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Not Acceptable");
	}// should_throw_exception_406_update_ad()

	@Test
	public void should_throw_exception_410_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(410);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Gone");
	}// should_throw_exception_410_update_ad()

	@Test
	public void should_throw_exception_418_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(418);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("I'm a teapot");
	}// should_throw_exception_418_update_ad()

	@Test
	public void should_throw_exception_429_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(429);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Too Many Requests / Rate limit reached");
	}// should_throw_exception_429_update_ad()

	@Test
	public void should_throw_exception_500_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(500);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Internal Server Error");
	}// should_throw_exception_500_update_ad()

	@Test
	public void should_throw_exception_503_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(503);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Service Unavailable");
	}// should_throw_exception_503_update_ad()

	@Test
	public void should_throw_exception_1337_update_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(1337);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Error 1337");
	}// should_throw_exception_1337_update_ad()

	@Test
	public void test_delete_ad_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
			SnapArgumentException, IOException, InterruptedException {
		Mockito.when(httpResponse.statusCode()).thenReturn(200);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		Assertions.assertThatCode(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete)).doesNotThrowAnyException();
	} // test_delete_ad_should_success()

	@Test
	public void test_delete_ad_should_throw_SnapOAuthAccessTokenException_1() {
		assertThatThrownBy(() -> ad.deleteAd(null, idAdToDelete)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	} // test_delete_ad_should_throw_SnapOAuthAccessTokenException_1()

	@Test
	public void test_delete_ad_should_throw_SnapOAuthAccessTokenException_2() {
		assertThatThrownBy(() -> ad.deleteAd("", idAdToDelete)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	} // test_delete_ad_should_throw_SnapOAuthAccessTokenException_2()

	@Test
	public void test_delete_ad_should_throw_SnapArgumentException_1() {
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, null)).isInstanceOf(SnapArgumentException.class)
				.hasMessage("The Ad ID is mandatory");
	} // test_delete_ad_should_throw_SnapArgumentException_1()

	@Test
	public void test_delete_ad_should_throw_SnapArgumentException_2() {
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, "")).isInstanceOf(SnapArgumentException.class)
				.hasMessage("The Ad ID is mandatory");
	} // test_delete_ad_should_throw_SnapArgumentException_2()

	@Test
	public void should_throw_exception_401_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(401);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
	} // should_throw_exception_401_delete_ad()

	@Test
	public void should_throw_exception_403_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(403);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
	} // should_throw_exception_403_delete_ad()

	@Test
	public void should_throw_exception_404_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(404);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
	} // should_throw_exception_404_delete_ad()

	@Test
	public void should_throw_exception_405_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(405);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
	} // should_throw_exception_405_delete_ad()

	@Test
	public void should_throw_exception_406_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(406);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
	} // should_throw_exception_406_delete_ad()

	@Test
	public void should_throw_exception_410_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(410);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
	} // should_throw_exception_410_delete_ad()

	@Test
	public void should_throw_exception_418_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(418);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
	} // should_throw_exception_418_delete_ad()

	@Test
	public void should_throw_exception_429_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(429);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
	} // should_throw_exception_429_delete_ad()

	@Test
	public void should_throw_exception_500_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(500);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
	} // should_throw_exception_500_delete_ad()

	@Test
	public void should_throw_exception_503_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(503);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
	} // should_throw_exception_503_delete_ad()

	@Test
	public void should_throw_exception_1337_delete_ad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(1337);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
	} // should_throw_exception_1337_delete_ad()

	@Test
	public void test_getSpecificAd_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
			SnapArgumentException, IOException, InterruptedException {
		Mockito.when(httpResponse.statusCode()).thenReturn(200);
		Mockito.when(httpResponse.body()).thenReturn(SnapResponseUtils.getSnapSpecificAd());
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		final String id = "e8d6217f-32ab-400f-9e54-39a86a7963e4";
		Optional<Ad> optAd = ad.getSpecificAd(oAuthAccessToken, id);
		assertThat(optAd.isPresent()).isTrue();
		optAd.ifPresent(f -> {
			assertThat(f.getId()).isEqualTo(id);
			assertThat(f.getName()).isEqualTo("Ad One");
			assertThat(f.getType()).isEqualTo(AdTypeEnum.SNAP_AD);
			assertThat(f.getStatus()).isEqualTo(StatusEnum.ACTIVE);
			assertThat(f.getAdSquadId()).isEqualTo(squadId);
			assertThat(f.getCreativeId()).isEqualTo(creativeId);
		});
	} // test_getSpecificAd_should_success()

	@Test
	public void test_getSpecificAd_should_throw_SnapOAuthAccessTokenException_1() {
		assertThatThrownBy(() -> ad.getSpecificAd(null, id)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	} // test_getSpecificAd_should_throw_SnapOAuthAccessTokenException_1()

	@Test
	public void test_getSpecificAd_should_throw_SnapOAuthAccessTokenException_2() {
		assertThatThrownBy(() -> ad.getSpecificAd("", id)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	} // test_getSpecificAd_should_throw_SnapOAuthAccessTokenException_2()

	@Test
	public void should_throw_exception_401_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(401);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Unauthorized - Check your API key");
	} // should_throw_exception_401_getSpecificAd()

	@Test
	public void should_throw_exception_403_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(403);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Access Forbidden");
	} // should_throw_exception_403_getSpecificAd()

	@Test
	public void should_throw_exception_404_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(404);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Not Found");
	} // should_throw_exception_404_getSpecificAd()

	@Test
	public void should_throw_exception_405_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(405);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Method Not Allowed");
	} // should_throw_exception_405_getSpecificAd()

	@Test
	public void should_throw_exception_406_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(406);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Not Acceptable");
	} // should_throw_exception_406_getSpecificAd()

	@Test
	public void should_throw_exception_410_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(410);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Gone");
	} // should_throw_exception_410_getSpecificAd()

	@Test
	public void should_throw_exception_418_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(418);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("I'm a teapot");
	} // should_throw_exception_418_getSpecificAd()

	@Test
	public void should_throw_exception_429_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(429);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Too Many Requests / Rate limit reached");
	} // should_throw_exception_429_getSpecificAd()

	@Test
	public void should_throw_exception_500_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(500);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Internal Server Error");
	} // should_throw_exception_500_getSpecificAd()

	@Test
	public void should_throw_exception_503_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(503);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Service Unavailable");
	} // should_throw_exception_503_getSpecificAd()

	@Test
	public void should_throw_exception_1337_getSpecificAd() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(1337);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
				.hasMessage("Error 1337");
	} // should_throw_exception_1337_getSpecificAd()

	@Test
	public void test_getAllAds_AdSquad_should_success() throws SnapResponseErrorException,
			SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException {
		Mockito.when(httpResponse.statusCode()).thenReturn(200);
		Mockito.when(httpResponse.body()).thenReturn(SnapResponseUtils.getSnapAllAdForAdSquad());
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		List<Ad> ads = ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId);
		assertThat(ads).isNotNull();
		assertThat(ads).isNotEmpty();
		assertThat(ads).hasSize(2);

		assertThat(ads.get(0).getId()).isEqualTo("2ded6d53-0805-4ff8-b984-54a7eb5c8918");
		assertThat(ads.get(0).getStatus()).isEqualTo(StatusEnum.PAUSED);
		assertThat(ads.get(0).getName()).isEqualTo("Ad Two");
		assertThat(ads.get(0).getAdSquadId()).isEqualTo(squadId);
		assertThat(ads.get(0).getType()).isEqualTo(AdTypeEnum.SNAP_AD);
		assertThat(ads.get(0).getCreativeId()).isEqualTo(creativeId);

		assertThat(ads.get(1).getId()).isEqualTo("e8d6217f-32ab-400f-9e54-39a86a7963e4");
		assertThat(ads.get(1).getStatus()).isEqualTo(StatusEnum.ACTIVE);
		assertThat(ads.get(1).getName()).isEqualTo("Ad One");
		assertThat(ads.get(0).getAdSquadId()).isEqualTo(squadId);
		assertThat(ads.get(1).getType()).isEqualTo(AdTypeEnum.SNAP_AD);
		assertThat(ads.get(1).getCreativeId()).isEqualTo(creativeId);
	}// test_getAllAds_AdSquad_should_success()

	@Test
	public void test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_1() {
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(null, squadId))
				.isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	}// test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_1()

	@Test
	public void test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_2() {
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad("", squadId))
				.isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	}// test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_2()
	
	@Test
	public void test_getAllAds_AdSquad_should_throw_SnapArgumentException_1() {
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, ""))
				.isInstanceOf(SnapArgumentException.class)
				.hasMessage("The AdSquad ID is mandatory");
	}// test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_1()

	@Test
	public void test_getAllAds_AdSquad_should_throw_SnapArgumentException_2() {
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, null))
				.isInstanceOf(SnapArgumentException.class)
				.hasMessage("The AdSquad ID is mandatory");
	}// test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_2()
	
	@Test
	public void should_throw_exception_401_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(401);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
	} // should_throw_exception_401_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_403_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(403);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
	} // should_throw_exception_403_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_404_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(404);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
	} // should_throw_exception_404_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_405_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(405);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
	} // should_throw_exception_405_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_406_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(406);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
	} // should_throw_exception_406_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_410_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(410);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
	} // should_throw_exception_410_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_418_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(418);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
	} // should_throw_exception_418_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_429_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(429);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
	} // should_throw_exception_429_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_500_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(500);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
	} // should_throw_exception_500_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_503_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(503);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
	} // should_throw_exception_503_getAllAds_AdSquad()

	@Test
	public void should_throw_exception_1337_getAllAds_AdSquad() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(1337);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
	} // should_throw_exception_1337_getAllAds_AdSquad()
	
	@Test
	public void test_getAllAds_AdAccount_should_success() throws SnapResponseErrorException,
			SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException {
		Mockito.when(httpResponse.statusCode()).thenReturn(200);
		Mockito.when(httpResponse.body()).thenReturn(SnapResponseUtils.getSnapAllAdForAdAccount());
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		List<Ad> ads = ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId);
		assertThat(ads).isNotNull();
		assertThat(ads).isNotEmpty();
		assertThat(ads).hasSize(2);

		assertThat(ads.get(0).getId()).isEqualTo("2ded6d53-0805-4ff8-b984-54a7eb5c8918");
		assertThat(ads.get(0).getStatus()).isEqualTo(StatusEnum.PAUSED);
		assertThat(ads.get(0).getName()).isEqualTo("Ad Two");
		assertThat(ads.get(0).getAdSquadId()).isEqualTo(squadId);
		assertThat(ads.get(0).getType()).isEqualTo(AdTypeEnum.SNAP_AD);
		assertThat(ads.get(0).getCreativeId()).isEqualTo(creativeId);

		assertThat(ads.get(1).getId()).isEqualTo("e8d6217f-32ab-400f-9e54-39a86a7963e4");
		assertThat(ads.get(1).getStatus()).isEqualTo(StatusEnum.ACTIVE);
		assertThat(ads.get(1).getName()).isEqualTo("Ad One");
		assertThat(ads.get(0).getAdSquadId()).isEqualTo(squadId);
		assertThat(ads.get(1).getType()).isEqualTo(AdTypeEnum.SNAP_AD);
		assertThat(ads.get(0).getCreativeId()).isEqualTo(creativeId);
	}// test_getAllAds_AdAccount_should_success()

	@Test
	public void test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_1() {
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(null, accountId))
				.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
	}// test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_1()

	@Test
	public void test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_2() {
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount("", accountId)).isInstanceOf(SnapOAuthAccessTokenException.class)
				.hasMessage("The OAuthAccessToken must to be given");
	}// test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_2()
	
	@Test
	public void test_getAllAds_AdAccount_should_throw_SnapArgumentException_1() {
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, ""))
				.isInstanceOf(SnapArgumentException.class)
				.hasMessage("The AdAccount ID is mandatory");
	}// test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_1()

	@Test
	public void test_getAllAds_AdAccount_should_throw_SnapArgumentException_2() {
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, null))
				.isInstanceOf(SnapArgumentException.class)
				.hasMessage("The AdAccount ID is mandatory");
	}// test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_2()
	
	@Test
	public void should_throw_exception_401_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(401);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
	} // should_throw_exception_401_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_403_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(403);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
	} // should_throw_exception_403_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_404_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

		Mockito.when(httpResponse.statusCode()).thenReturn(404);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
	} // should_throw_exception_404_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_405_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(405);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
	} // should_throw_exception_405_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_406_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(406);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
	} // should_throw_exception_406_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_410_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(410);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
	} // should_throw_exception_410_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_418_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(418);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
	} // should_throw_exception_418_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_429_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(429);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
	} // should_throw_exception_429_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_500_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(500);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
	} // should_throw_exception_500_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_503_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(503);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
	} // should_throw_exception_503_getAllAds_AdAccount()

	@Test
	public void should_throw_exception_1337_getAllAds_AdAccount() throws IOException, InterruptedException,
			SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
		Mockito.when(httpResponse.statusCode()).thenReturn(1337);
		Mockito.when(httpClient.send(Mockito.isA(HttpRequest.class), Mockito.isA(BodyHandler.class)))
				.thenReturn(httpResponse);
		assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
				.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
	} // should_throw_exception_1337_getAllAds_AdAccount()
	
	private Ad initFunctionalAd(String id) {
		Ad ad = new Ad();
		if(StringUtils.isNotEmpty(id)) {
			ad.setId(id);
		}
		ad.setAdSquadId(squadId);
		ad.setCreativeId(creativeId);
		ad.setName("Ad one");
		ad.setStatus(StatusEnum.ACTIVE);
		return ad;
	}// initFunctionalAd()

}// SnapAdTest
