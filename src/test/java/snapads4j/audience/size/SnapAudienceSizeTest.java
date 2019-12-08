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
package snapads4j.audience.size;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.audience.size.AudienceSize;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

@RunWith(MockitoJUnitRunner.class)
public class SnapAudienceSizeTest {

    @Spy
    private SnapAudienceSize snapAudienceSize;

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

    private final String adAccountId = "8adc3db7-8148-4fbf-999c-8d2266369d74";

    private final String adSquadId = "c7b98952-4c6e-4f95-8cd1-cf0f17a77988";

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapAudienceSize.setHttpClient(httpClient);
	snapAudienceSize.setEntityUtilsWrapper(entityUtilsWrapper);
    } // setUp()

    @Test
    public void test_get_audience_size_by_squad_id_should_success() throws IOException, InterruptedException,
	    SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getSnapAudienceSizeByAdSquadID());
	Assertions.assertThatCode(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, adSquadId))
		.doesNotThrowAnyException();
	Optional<AudienceSize> optSize = snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, adSquadId);
	Assertions.assertThat(optSize.isPresent()).isTrue();
	optSize.ifPresent(s -> {
	    Assertions.assertThat(s.getAdSquadId()).isPresent();
	    Assertions.assertThat(s.getAdSquadId().get()).isEqualTo(this.adSquadId);
	    Assertions.assertThat(s.getAudienceSizeMinimum()).isEqualTo(16450000);
	    Assertions.assertThat(s.getAudienceSizeMaximum()).isEqualTo(19925000);
	});
    }// test_get_audience_size_by_squad_id_should_success()

    @Test
    public void test_get_audience_size_by_squad_id_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(null, this.adSquadId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_audience_size_by_squad_id_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_audience_size_by_squad_id_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId("", this.adSquadId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_audience_size_by_squad_id_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_audience_size_by_squad_id_should_throw_IOException() throws ClientProtocolException,
	    IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId);
    }// test_get_audience_size_by_squad_id_should_throw_IOException()

    @Test
    public void test_get_audience_size_by_squad_id_should_throw_throw_SnapArgumentException_when_id_is_null() {
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("AdSquad ID is required");
    } // test_get_audience_size_by_squad_id_should_throw_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_get_audience_size_by_squad_id_should_throw_throw_SnapArgumentException_when_id_is_empty() {
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("AdSquad ID is required");
    } // test_get_audience_size_by_squad_id_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void should_throw_exception_400_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_get_specific_audience_segment()

    @Test
    public void should_throw_exception_401_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_get_specific_audience_segment()

    @Test
    public void should_throw_exception_403_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_get_specific_audience_segment()

    @Test
    public void should_throw_exception_404_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_get_specific_audience_segment()

    @Test
    public void should_throw_exception_405_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_get_specific_audience_segment()

    @Test
    public void should_throw_exception_406_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_get_specific_audience_segment()

    @Test
    public void should_throw_exception_410_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_get_specific_audience_segment()

    @Test
    public void should_throw_exception_418_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_get_specific_audience_segment()

    @Test
    public void should_throw_exception_429_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_get_specific_audience_segment()

    @Test
    public void should_throw_exception_500_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_get_specific_audience_segment()

    @Test
    public void should_throw_exception_503_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_get_specific_audience_segment()

    @Test
    public void should_throw_exception_1337_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSize.getAudienceSizeByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_get_specific_audience_segment()

}// snapAudienceSizeTest
