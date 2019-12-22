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
package snapads4j.ads;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
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
import snapads4j.enums.AdTypeEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.ads.Ad;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests mocked for SnapAdTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapAdTest {

    @Spy
    private SnapAd ad;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private HttpEntity httpEntity;

    @Mock
    private EntityUtilsWrapper entityUtilsWrapper;

    @Mock
    private StatusLine statusLine;

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

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ad.setHttpClient(httpClient);
        ad.setEntityUtilsWrapper(entityUtilsWrapper);
        adModel = initFunctionalAd(null);
        adModelUpdate = initFunctionalAd("e8d6217f-32ab-400f-9e54-39a86a7963e4");
        adModelCreateFailure = initFunctionalAd(null);
        adModelUpdateFailure = initFunctionalAd("e8d6217f-32ab-400f-9e54-39a86a7963e4");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }// setUp()

    @Test
    public void test_create_ad_should_success() throws IOException, SnapOAuthAccessTokenException,
            SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAdCreated());
        Assertions.assertThatCode(() -> ad.createAd(oAuthAccessToken, adModel)).doesNotThrowAnyException();
        Optional<Ad> optAd = ad.createAd(oAuthAccessToken, adModel);
        assertThat(optAd.isPresent()).isTrue();
        optAd.ifPresent(f -> {
            assertThat(f.getId()).isEqualTo("e8d6217f-32ab-400f-9e54-39a86a7963e4");
            assertThat(f.getName()).isEqualTo("Ad One");
            assertThat(f.getType()).isEqualTo(AdTypeEnum.SNAP_AD);
            assertThat(f.getStatus()).isEqualTo(StatusEnum.ACTIVE);
            assertThat(f.getAdSquadId()).isEqualTo(squadId);
            assertThat(f.getCreativeId()).isEqualTo(creativeId);
            assertThat(sdf.format(f.getCreatedAt())).isEqualTo("2016-08-14T07:07:50.241Z");
            assertThat(sdf.format(f.getUpdatedAt())).isEqualTo("2016-08-14T07:07:50.241Z");
        });
    }// test_create_ad_should_success()

    @Test
    public void test_create_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> ad.createAd(null, adModel)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_create_ad_should_throw_SnapOAuthAccessTokenException_token_is_empty() {
        assertThatThrownBy(() -> ad.createAd("", adModel)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    }// test_create_ad_should_throw_SnapOAuthAccessTokenException_token_is_empty()

    @Test
    public void test_create_ad_should_throw_SnapArgumentException_ad_is_null() {
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, null)).isInstanceOf(SnapArgumentException.class)
                .hasMessage("Ad parameter is not given");
    }// test_create_ad_should_throw_SnapArgumentException_ad_is_null()

    @Test
    public void test_create_ad_should_throw_SnapArgumentException_ad_squad_id_is_null() {
        adModelCreateFailure.setAdSquadId(null);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModelCreateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Ad Squad ID parameter is not given");
    }// test_create_ad_should_throw_SnapArgumentException_ad_squad_id_is_null()

    @Test
    public void test_create_ad_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapExecutionException.class);
    }// test_create_ad_should_throw_SnapExecutionException()

    @Test
    public void test_create_ad_should_throw_SnapArgumentException_when_name_is_null() {
        adModelCreateFailure.setName(null);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModelCreateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Ad's name parameter is not given");
    }// test_create_ad_should_throw_SnapArgumentException_when_name_is_null()

    @Test
    public void test_create_ad_should_throw_SnapArgumentException_when_name_is_empty() {
        adModelCreateFailure.setName("");
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModelCreateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Ad's name parameter is not given");
    }// test_create_ad_should_throw_SnapArgumentException_when_name_is_empty()

    @Test
    public void test_create_ad_should_throw_SnapArgumentException_when_status_is_null() {
        adModelCreateFailure.setStatus(null);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModelCreateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Ad's status parameter is not given");
    }// test_create_ad_should_throw_SnapArgumentException_when_status_is_null()

    @Test
    public void should_throw_exception_400_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Bad Request");
    }// should_throw_exception_400_create_ad()

    @Test
    public void should_throw_exception_401_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_create_ad()

    @Test
    public void should_throw_exception_403_create_ad() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Access Forbidden");
    }// should_throw_exception_403_create_ad()

    @Test
    public void should_throw_exception_404_create_ad() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Found");
    }// should_throw_exception_404_create_ad()

    @Test
    public void should_throw_exception_405_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Method Not Allowed");
    }// should_throw_exception_405_create_ad()

    @Test
    public void should_throw_exception_406_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Acceptable");
    }// should_throw_exception_406_create_ad()

    @Test
    public void should_throw_exception_410_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Gone");
    }// should_throw_exception_410_create_ad()

    @Test
    public void should_throw_exception_418_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("I'm a teapot");
    }// should_throw_exception_418_create_ad()

    @Test
    public void should_throw_exception_429_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_create_ad()

    @Test
    public void should_throw_exception_500_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Internal Server Error");
    }// should_throw_exception_500_create_ad()

    @Test
    public void should_throw_exception_503_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Service Unavailable");
    }// should_throw_exception_503_create_ad()

    @Test
    public void should_throw_exception_1337_create_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.createAd(oAuthAccessToken, adModel)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Error 1337");
    }// should_throw_exception_1337_create_ad()

    @Test
    public void test_update_ad_should_success() throws IOException, SnapOAuthAccessTokenException,
            SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAdUpdated());
        Assertions.assertThatCode(() -> ad.updateAd(oAuthAccessToken, adModelUpdate)).doesNotThrowAnyException();
        Optional<Ad> optAd = ad.updateAd(oAuthAccessToken, adModelUpdate);
        assertThat(optAd.isPresent()).isTrue();
        optAd.ifPresent(f -> {
            assertThat(f.toString()).isNotEmpty();
            assertThat(f.getId()).isEqualTo("e8d6217f-32ab-400f-9e54-39a86a7963e4");
            assertThat(f.getName()).isEqualTo("Ad One");
            assertThat(f.getType()).isEqualTo(AdTypeEnum.SNAP_AD);
            assertThat(f.getStatus()).isEqualTo(StatusEnum.ACTIVE);
            assertThat(f.getAdSquadId()).isEqualTo(squadId);
            assertThat(f.getCreativeId()).isEqualTo(creativeId);
            assertThat(sdf.format(f.getCreatedAt())).isEqualTo("2016-08-14T07:07:50.241Z");
            assertThat(sdf.format(f.getUpdatedAt())).isEqualTo("2016-08-14T07:14:45.174Z");
        });
    }// test_update_ad_should_success()

    @Test
    public void test_update_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> ad.updateAd(null, adModel)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    }// test_update_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_update_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> ad.updateAd("", adModel)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    }// test_update_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_update_ad_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpPut.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapExecutionException.class);
    }// test_update_ad_should_throw_SnapExecutionException()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_when_ad_parameter_is_null() {
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, null)).isInstanceOf(SnapArgumentException.class)
                .hasMessage("Ad parameter is not given");
    }// test_update_ad_should_throw_SnapArgumentException_when_ad_parameter_is_null()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_when_ad_squad_id_is_null() {
        adModelUpdateFailure.setAdSquadId(null);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Ad Squad ID parameter is not given");
    }// test_update_ad_should_throw_SnapArgumentException_when_ad_squad_id_is_null()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_when_name_is_null() {
        adModelUpdateFailure.setName(null);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Ad's name parameter is not given");
    }// test_update_ad_should_throw_SnapArgumentException_when_name_is_null()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_when_name_is_empty() {
        adModelUpdateFailure.setName("");
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Ad's name parameter is not given");
    }// test_update_ad_should_throw_SnapArgumentException_when_name_is_empty()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_when_status_is_null() {
        adModelUpdateFailure.setAdSquadId(squadId);
        adModelUpdateFailure.setStatus(null);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Ad's status parameter is not given");
    }// test_update_ad_should_throw_SnapArgumentException_when_status_is_null()

    @Test
    public void test_update_ad_should_throw_SnapArgumentException_when_ad_id_is_null() {
        adModelUpdateFailure.setId(null);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdateFailure))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad ID is required");
    }// test_update_ad_should_throw_SnapArgumentException_when_ad_id_is_null()

    @Test
    public void should_throw_exception_400_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_update_ad()

    @Test
    public void should_throw_exception_401_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_update_ad()

    @Test
    public void should_throw_exception_403_update_ad() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_update_ad()

    @Test
    public void should_throw_exception_404_update_ad() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_update_ad()

    @Test
    public void should_throw_exception_405_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_update_ad()

    @Test
    public void should_throw_exception_406_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_update_ad()

    @Test
    public void should_throw_exception_410_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_update_ad()

    @Test
    public void should_throw_exception_418_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_update_ad()

    @Test
    public void should_throw_exception_429_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_update_ad()

    @Test
    public void should_throw_exception_500_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_update_ad()

    @Test
    public void should_throw_exception_503_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_update_ad()

    @Test
    public void should_throw_exception_1337_update_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.updateAd(oAuthAccessToken, adModelUpdate))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_update_ad()

    @Test
    public void test_delete_ad_should_success() throws IOException, SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapDeleteAd());
        Assertions.assertThat(ad.deleteAd(oAuthAccessToken, idAdToDelete)).isTrue();
    } // test_delete_ad_should_success()

    @Test
    public void test_delete_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> ad.deleteAd(null, idAdToDelete)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_delete_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_delete_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> ad.deleteAd("", idAdToDelete)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_delete_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_delete_ad_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpDelete.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapExecutionException.class);
    }// test_delete_ad_should_throw_SnapExecutionException()

    @Test
    public void test_delete_ad_should_throw_SnapArgumentException_when_id_is_null() {
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, null)).isInstanceOf(SnapArgumentException.class)
                .hasMessage("The Ad ID is required");
    } // test_delete_ad_should_throw_SnapArgumentException_when_id_is_null()

    @Test
    public void test_delete_ad_should_throw_SnapArgumentException_when_id_is_empty() {
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, "")).isInstanceOf(SnapArgumentException.class)
                .hasMessage("The Ad ID is required");
    } // test_delete_ad_should_throw_SnapArgumentException_when_id_is_empty()

    @Test
    public void should_throw_exception_400_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_delete_ad()

    @Test
    public void should_throw_exception_401_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_delete_ad()

    @Test
    public void should_throw_exception_403_delete_ad() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_delete_ad()

    @Test
    public void should_throw_exception_404_delete_ad() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_delete_ad()

    @Test
    public void should_throw_exception_405_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_delete_ad()

    @Test
    public void should_throw_exception_406_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_delete_ad()

    @Test
    public void should_throw_exception_410_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_delete_ad()

    @Test
    public void should_throw_exception_418_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_delete_ad()

    @Test
    public void should_throw_exception_429_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_delete_ad()

    @Test
    public void should_throw_exception_500_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_delete_ad()

    @Test
    public void should_throw_exception_503_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_delete_ad()

    @Test
    public void should_throw_exception_1337_delete_ad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> ad.deleteAd(oAuthAccessToken, idAdToDelete))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_delete_ad()

    @Test
    public void test_getSpecificAd_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
            SnapArgumentException, IOException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapSpecificAd());
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
            assertThat(sdf.format(f.getCreatedAt())).isEqualTo("2016-08-14T07:07:50.241Z");
            assertThat(sdf.format(f.getUpdatedAt())).isEqualTo("2016-08-14T07:14:45.174Z");
        });
    } // test_getSpecificAd_should_success()

    @Test
    public void test_getSpecificAd_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> ad.getSpecificAd(null, id)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_getSpecificAd_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_getSpecificAd_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> ad.getSpecificAd("", id)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_getSpecificAd_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_getSpecificAd_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapExecutionException.class);
    }// test_getSpecificAd_should_throw_SnapExecutionException()

    @Test
    public void should_throw_exception_400_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Bad Request");
    } // should_throw_exception_400_getSpecificAd()

    @Test
    public void should_throw_exception_401_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getSpecificAd()

    @Test
    public void should_throw_exception_403_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Access Forbidden");
    } // should_throw_exception_403_getSpecificAd()

    @Test
    public void should_throw_exception_404_getSpecificAd() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Found");
    } // should_throw_exception_404_getSpecificAd()

    @Test
    public void should_throw_exception_405_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getSpecificAd()

    @Test
    public void should_throw_exception_406_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Acceptable");
    } // should_throw_exception_406_getSpecificAd()

    @Test
    public void should_throw_exception_410_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Gone");
    } // should_throw_exception_410_getSpecificAd()

    @Test
    public void should_throw_exception_418_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("I'm a teapot");
    } // should_throw_exception_418_getSpecificAd()

    @Test
    public void should_throw_exception_429_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getSpecificAd()

    @Test
    public void should_throw_exception_500_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Internal Server Error");
    } // should_throw_exception_500_getSpecificAd()

    @Test
    public void should_throw_exception_503_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Service Unavailable");
    } // should_throw_exception_503_getSpecificAd()

    @Test
    public void should_throw_exception_1337_getSpecificAd() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getSpecificAd(oAuthAccessToken, id)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Error 1337");
    } // should_throw_exception_1337_getSpecificAd()

    @Test
    public void test_getAllAds_AdSquad_should_success()
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, IOException,
            SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAllAdForAdSquad());
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
        assertThat(sdf.format(ads.get(0).getCreatedAt())).isEqualTo("2016-08-14T07:18:05.699Z");
        assertThat(sdf.format(ads.get(0).getUpdatedAt())).isEqualTo("2016-08-14T07:18:05.699Z");

        assertThat(ads.get(1).getId()).isEqualTo("e8d6217f-32ab-400f-9e54-39a86a7963e4");
        assertThat(ads.get(1).getStatus()).isEqualTo(StatusEnum.ACTIVE);
        assertThat(ads.get(1).getName()).isEqualTo("Ad One");
        assertThat(ads.get(0).getAdSquadId()).isEqualTo(squadId);
        assertThat(ads.get(1).getType()).isEqualTo(AdTypeEnum.SNAP_AD);
        assertThat(ads.get(1).getCreativeId()).isEqualTo(creativeId);
        assertThat(sdf.format(ads.get(1).getCreatedAt())).isEqualTo("2016-08-14T07:07:50.241Z");
        assertThat(sdf.format(ads.get(1).getUpdatedAt())).isEqualTo("2016-08-14T07:14:45.174Z");
    }// test_getAllAds_AdSquad_should_success()

    @Test
    public void test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(null, squadId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad("", squadId)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    }// test_getAllAds_AdSquad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_getAllAds_AdSquad_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapExecutionException.class);
    }// test_getAllAds_AdSquad_should_throw_SnapExecutionException()

    @Test
    public void test_getAllAds_AdSquad_should_throw_SnapArgumentException_when_ad_squad_id_is_empty() {
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, ""))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The AdSquad ID is required");
    }// test_getAllAds_AdSquad_should_throw_SnapArgumentException_when_ad_squad_id_is_empty()

    @Test
    public void test_getAllAds_AdSquad_should_throw_SnapArgumentException_when_ad_squad_id_is_null() {
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The AdSquad ID is required");
    }// test_getAllAds_AdSquad_should_throw_SnapArgumentException_when_ad_squad_id_is_null()

    @Test
    public void should_throw_exception_400_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_401_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_403_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_404_getAllAds_AdSquad() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_405_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_406_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_410_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_418_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_429_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_500_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_503_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_getAllAds_AdSquad()

    @Test
    public void should_throw_exception_1337_getAllAds_AdSquad() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdSquad(oAuthAccessToken, squadId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_getAllAds_AdSquad()

    @Test
    public void test_getAllAds_AdAccount_should_success()
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, IOException
            , SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAllAdForAdAccount());
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
        assertThat(sdf.format(ads.get(0).getCreatedAt())).isEqualTo("2016-08-14T07:18:05.699Z");
        assertThat(sdf.format(ads.get(0).getUpdatedAt())).isEqualTo("2016-08-14T07:18:05.699Z");

        assertThat(ads.get(1).getId()).isEqualTo("e8d6217f-32ab-400f-9e54-39a86a7963e4");
        assertThat(ads.get(1).getStatus()).isEqualTo(StatusEnum.ACTIVE);
        assertThat(ads.get(1).getName()).isEqualTo("Ad One");
        assertThat(ads.get(0).getAdSquadId()).isEqualTo(squadId);
        assertThat(ads.get(1).getType()).isEqualTo(AdTypeEnum.SNAP_AD);
        assertThat(ads.get(0).getCreativeId()).isEqualTo(creativeId);
        assertThat(sdf.format(ads.get(1).getCreatedAt())).isEqualTo("2016-08-14T07:07:50.241Z");
        assertThat(sdf.format(ads.get(1).getUpdatedAt())).isEqualTo("2016-08-14T07:14:45.174Z");
    }// test_getAllAds_AdAccount_should_success()

    @Test
    public void test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(null, accountId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount("", accountId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    }// test_getAllAds_AdAccount_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_getAllAds_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapExecutionException.class);
    }// test_getAllAds_should_throw_SnapExecutionException()

    @Test
    public void test_getAllAds_AdAccount_should_throw_SnapArgumentException_when_ad_account_id_is_empty() {
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, ""))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The AdAccount ID is required");
    }// test_getAllAds_AdAccount_should_throw_SnapArgumentException_when_ad_account_id_is_empty()

    @Test
    public void test_getAllAds_AdAccount_should_throw_SnapArgumentException_when_ad_account_id_is_null() {
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The AdAccount ID is required");
    }// test_getAllAds_AdAccount_should_throw_SnapArgumentException_when_ad_account_id_is_null()

    @Test
    public void should_throw_exception_401_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_403_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_404_getAllAds_AdAccount() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_405_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_406_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_410_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_418_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_429_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_500_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_503_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_getAllAds_AdAccount()

    @Test
    public void should_throw_exception_1337_getAllAds_AdAccount() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> ad.getAllAdsFromAdAccount(oAuthAccessToken, accountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_getAllAds_AdAccount()

    private Ad initFunctionalAd(String id) {
        Ad ad = new Ad();
        if (StringUtils.isNotEmpty(id)) {
            ad.setId(id);
        }
        ad.setAdSquadId(squadId);
        ad.setCreativeId(creativeId);
        ad.setName("Ad one");
        ad.setStatus(StatusEnum.ACTIVE);
        return ad;
    }// initFunctionalAd()

}// SnapAdTest
