/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.pixel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

import snapads4j.enums.StatusEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.pixel.Pixel;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

@RunWith(MockitoJUnitRunner.class)
public class SnapPixelTest {

    @Spy
    private SnapPixel snapPixel;

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

    private Pixel pixel;

    private final String oAuthAccessToken = "meowmeowmeow";

    private final String adAccountId = "3cb7c65d-a943-448b-90aa-bd6bac71dabc";

    private final String specificId = "sf6f3815-3527-49e3-a5a7-b9681b31daf4";

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	pixel = initPixel();
	snapPixel.setHttpClient(httpClient);
	snapPixel.setEntityUtilsWrapper(entityUtilsWrapper);
	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    } // setUp()

    @Test
    public void get_pixel_associated_by_ad_account_should_success()
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    SnapExecutionException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getPixelAssociatedWithAdAccount());
	Assertions.assertThatCode(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.doesNotThrowAnyException();
	Optional<Pixel> optPixel = snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId);
	Assertions.assertThat(optPixel).isPresent();
	optPixel.ifPresent(pixel -> {
	    Assertions.assertThat(pixel.getId()).isEqualTo("6abc82ca-4a3a-4391-98ba-0317a8471234");
	    Assertions.assertThat(pixel.getEffectiveStatus()).isEqualTo("ACTIVE");
	    Assertions.assertThat(pixel.getName()).isEqualTo("Test pixel");
	    Assertions.assertThat(pixel.getAdAccountId()).isEqualTo(adAccountId);
	    Assertions.assertThat(pixel.getStatus()).isEqualTo(StatusEnum.ACTIVE);
	    Assertions.assertThat(pixel.getPixelJavascript()).isNotEmpty();
	    assertThat(sdf.format(pixel.getCreatedAt())).isEqualTo("2017-03-15T18:19:08.576Z");
	    assertThat(sdf.format(pixel.getUpdatedAt())).isEqualTo("2017-03-15T18:19:08.576Z");
	});
    }// get_pixel_associated_by_ad_account_should_success()

    @Test
    public void get_pixel_associated_by_ad_account_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount("", adAccountId))
		.hasMessage("The OAuthAccessToken must to be given").isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_pixel_associated_by_ad_account_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void get_pixel_associated_by_ad_account_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(null, adAccountId))
		.hasMessage("The OAuthAccessToken must to be given").isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_pixel_associated_by_ad_account_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void get_pixel_associated_by_ad_account_should_throw_SnapArgumentException_when_id_is_empty() {
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, ""))
		.hasMessage("The Ad Account ID is required").isInstanceOf(SnapArgumentException.class);
    }// get_pixel_associated_by_ad_account_should_throw_SnapArgumentException_when_id_is_empty()

    @Test
    public void get_pixel_associated_by_ad_account_should_throw_SnapArgumentException_when_id_is_null() {
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, null))
		.hasMessage("The Ad Account ID is required").isInstanceOf(SnapArgumentException.class);
    }// get_pixel_associated_by_ad_account_should_throw_SnapArgumentException_when_id_is_null()

    @Test
    public void test_get_pixel_associated_should_throw_SnapExecutionException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	Assertions
		.assertThatThrownBy(
			() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapExecutionException.class);
    }// test_get_pixel_associated_should_throw_SnapExecutionException()

    @Test
    public void should_throw_exception_400_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_pixel_associated()

    @Test
    public void should_throw_exception_401_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_pixel_associated()

    @Test
    public void should_throw_exception_403_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_pixel_associated()

    @Test
    public void should_throw_exception_404_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_pixel_associated()

    @Test
    public void should_throw_exception_405_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_pixel_associated()

    @Test
    public void should_throw_exception_406_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_pixel_associated()

    @Test
    public void should_throw_exception_410_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_pixel_associated()

    @Test
    public void should_throw_exception_418_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_pixel_associated()

    @Test
    public void should_throw_exception_429_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_pixel_associated()

    @Test
    public void should_throw_exception_500_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_pixel_associated()

    @Test
    public void should_throw_exception_503_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_pixel_associated()

    @Test
    public void should_throw_exception_1337_get_pixel_associated() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixelAssociatedByAdAccount(oAuthAccessToken, adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_pixel_associated()

    @Test
    public void get_specific_pixel_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
	    SnapArgumentException, SnapExecutionException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSpecificPixel());
	Assertions.assertThatCode(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.doesNotThrowAnyException();
	Optional<Pixel> optPixel = snapPixel.getSpecificPixel(oAuthAccessToken, specificId);
	Assertions.assertThat(optPixel).isPresent();
	optPixel.ifPresent(pixel -> {
	    Assertions.assertThat(pixel.getId()).isEqualTo("sf6f3815-3527-49e3-a5a7-b9681b31daf4");
	    Assertions.assertThat(pixel.getEffectiveStatus()).isEqualTo("ACTIVE");
	    Assertions.assertThat(pixel.getName()).isEqualTo("Test pixel");
	    Assertions.assertThat(pixel.getAdAccountId()).isEqualTo(adAccountId);
	    Assertions.assertThat(pixel.getStatus()).isEqualTo(StatusEnum.ACTIVE);
	    Assertions.assertThat(pixel.getPixelJavascript()).isNotEmpty();
	    assertThat(sdf.format(pixel.getCreatedAt())).isEqualTo("2017-03-15T18:19:08.576Z");
	    assertThat(sdf.format(pixel.getUpdatedAt())).isEqualTo("2017-03-15T18:19:08.576Z");
	});
    }// get_specific_pixel_should_success()

    @Test
    public void get_specific_pixel_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixel("", specificId))
		.hasMessage("The OAuthAccessToken must to be given").isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_specific_pixel_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void get_specific_pixel_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixel(null, specificId))
		.hasMessage("The OAuthAccessToken must to be given").isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_specific_pixel_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void get_specific_pixel_should_throw_SnapArgumentException_when_id_is_empty() {
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, ""))
		.hasMessage("The Pixel ID is required").isInstanceOf(SnapArgumentException.class);
    }// get_specific_pixel_should_throw_SnapArgumentException_when_id_is_empty()

    @Test
    public void get_specific_pixel_should_throw_SnapArgumentException_when_id_is_null() {
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, null))
		.hasMessage("The Pixel ID is required").isInstanceOf(SnapArgumentException.class);
    }// get_specific_pixel_should_throw_SnapArgumentException_when_id_is_null()

    @Test
    public void test_get_specific_pixel_should_throw_SnapExecutionException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	Assertions.assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapExecutionException.class);
    }// test_get_specific_pixel_should_throw_SnapExecutionException()

    @Test
    public void should_throw_exception_400_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_specific_pixel()

    @Test
    public void should_throw_exception_401_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_specific_pixel()

    @Test
    public void should_throw_exception_403_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_specific_pixel()

    @Test
    public void should_throw_exception_404_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_specific_pixel()

    @Test
    public void should_throw_exception_405_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_specific_pixel()

    @Test
    public void should_throw_exception_406_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_specific_pixel()

    @Test
    public void should_throw_exception_410_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_specific_pixel()

    @Test
    public void should_throw_exception_418_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_specific_pixel()

    @Test
    public void should_throw_exception_429_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_specific_pixel()

    @Test
    public void should_throw_exception_500_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_specific_pixel()

    @Test
    public void should_throw_exception_503_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_specific_pixel()

    @Test
    public void should_throw_exception_1337_get_specific_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapPixel.getSpecificPixel(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_specific_pixel()

    @Test
    public void test_update_ad_should_success() throws IOException, InterruptedException, SnapOAuthAccessTokenException,
	    SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getPixelUpdated());
	Assertions.assertThatCode(() -> snapPixel.updatePixel(oAuthAccessToken, pixel)).doesNotThrowAnyException();
	Optional<Pixel> optPixel = snapPixel.updatePixel(oAuthAccessToken, pixel);
	assertThat(optPixel.isPresent()).isTrue();
	optPixel.ifPresent(f -> {
	    assertThat(f.toString()).isNotEmpty();
	    assertThat(f.getId()).isEqualTo(pixel.getId());
	    assertThat(f.getName()).isEqualTo(pixel.getName());
	    assertThat(f.getEffectiveStatus()).isEqualTo("ACTIVE");
	    assertThat(f.getStatus()).isEqualTo(StatusEnum.ACTIVE);
	    assertThat(f.getAdAccountId()).isEqualTo(adAccountId);
	    assertThat(sdf.format(f.getCreatedAt())).isEqualTo("2017-02-07T19:14:05.852Z");
	    assertThat(sdf.format(f.getUpdatedAt())).isEqualTo("2017-02-07T21:36:53.324Z");
	});
    }// test_update_ad_should_success()

    @Test
    public void test_update_ad_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapPixel.updatePixel(null, pixel)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    }// test_update_ad_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_update_ad_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapPixel.updatePixel("", pixel)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    }// test_update_ad_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_update_ad_should_throw_SnapExecutionException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPut.class)))).thenThrow(IOException.class);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapExecutionException.class);
    }// test_update_ad_should_throw_SnapExecutionException()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("Pixel parameter is not given");
    }// test_update_ad_should_throw_SnapArgumentException_1()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_2() {
	pixel.setAdAccountId(null);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("Ad Account ID parameter is required");
    }// test_update_ad_should_throw_SnapArgumentException_2()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_3() {
	pixel.setName(null);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("Pixel name parameter is required");
    }// test_update_ad_should_throw_SnapArgumentException_3()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_4() {
	pixel.setName("");
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("Pixel name parameter is required");
    }// test_update_ad_should_throw_SnapArgumentException_4()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_5() {
	pixel.setId("");
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("Pixel ID parameter is required");
    }// test_create_ad_should_throw_SnapArgumentException_5()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_6() {
	pixel.setId(null);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("Pixel ID parameter is required");
    }// test_create_ad_should_throw_SnapArgumentException_6()

    @Test
    public void should_throw_exception_400_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_update_pixel()

    @Test
    public void should_throw_exception_401_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_update_pixel()

    @Test
    public void should_throw_exception_403_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_update_pixel()

    @Test
    public void should_throw_exception_404_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_update_pixel()

    @Test
    public void should_throw_exception_405_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_update_pixel()

    @Test
    public void should_throw_exception_406_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_update_pixel()

    @Test
    public void should_throw_exception_410_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_update_pixel()

    @Test
    public void should_throw_exception_418_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_update_pixel()

    @Test
    public void should_throw_exception_429_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_update_pixel()

    @Test
    public void should_throw_exception_500_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_update_pixel()

    @Test
    public void should_throw_exception_503_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_update_pixel()

    @Test
    public void should_throw_exception_1337_update_pixel() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapPixel.updatePixel(oAuthAccessToken, pixel))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_update_pixel()

    private Pixel initPixel() {
	Pixel pixel = new Pixel();
	pixel.setId("ef6f3815-3527-49e3-a5a7-b9681b31daf4");
	pixel.setName("New pixel name");
	pixel.setAdAccountId(adAccountId);
	return pixel;
    }// initPixel()
}// SnapPixelTest
