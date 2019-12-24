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
package snapads4j.auth;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import snapads4j.config.SnapConfiguration;
import snapads4j.exceptions.SnapAuthorizationException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.auth.TokenResponse;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests mocked for SnapAuthorization.
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapAuthorizationTest {

    @Spy
    private SnapAuthorization auth;

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

    @Before
    public void setUp() {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setClientId("fake_client_id")
                .setRedirectUri("fake_redirect_uri")
                .setClientSecret("fake_client_secret").build();
        MockitoAnnotations.initMocks(this);
        this.auth.setConfiguration(config);
        this.auth.setHttpClient(httpClient);
        this.auth.setEntityUtilsWrapper(entityUtilsWrapper);
        this.auth.setApiUrl("http://www.foo.com/foo/");
    } // setUp()

    @Test
    public void test_getOAuthAuthorizationURI_should_success() throws SnapAuthorizationException {
        Mockito.when(this.auth.getOAuthAuthorizationURI())
                .thenReturn("fake_redirect_uri?code=code_from_redirect_uri");
        String authorizationURI = this.auth.getOAuthAuthorizationURI();
        assertThat(authorizationURI).isNotEmpty();
        assertThat(authorizationURI).isNotBlank();
        assertThat(authorizationURI).contains("code=code_from_redirect_uri");
    } // test_getOAuthAuthorizationURI_should_success()

    @Test
    public void test_getOAuthAuthorizationURI_should_fail_noClientID() throws IOException {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setRedirectUri("fake_redirect_uri")
                .build();
        SnapAuthorization sp = new SnapAuthorization(config);
        assertThatThrownBy(
                sp::getOAuthAuthorizationURI)
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing client ID");
    } // test_getOAuthAuthorizationURI_should_fail_noClientID()

    @Test
    public void test_getOAuthAuthorizationURI_should_fail_noRedirectURI() throws IOException {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setClientId("fake_client_id").build();
        SnapAuthorization sp = new SnapAuthorization(config);
        assertThatThrownBy(
                sp::getOAuthAuthorizationURI)
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing Redirect URI");
    } // test_getOAuthAuthorizationURI_should_fail_noRedirectURI()

    @Test
    public void test_getOAuthAuthorizationURI_should_fail_noConfiguration() throws IOException {
        SnapAuthorization sp = new SnapAuthorization(null);
        assertThatThrownBy(
                sp::getOAuthAuthorizationURI)
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Configuration unfound");
    } // test_getOAuthAuthorizationURI_should_fail_noConfiguration()

    @Test
    public void test_getOAuthAccessToken_should_success()
            throws SnapAuthorizationException, SnapResponseErrorException, IOException
            , SnapExecutionException {
        String oauthCode = "code_from_redirect_uri";
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapOAuthToken());
        TokenResponse tokenResponse = this.auth.getOAuthAccessToken(oauthCode);
        assertThat(tokenResponse).isNotNull();
        assertThat(tokenResponse.getAccessToken()).isNotNull();
        assertThat(tokenResponse.getAccessToken()).isNotEmpty();
        assertThat(tokenResponse.getAccessToken()).isEqualTo("0.MGQCxyz123");

        assertThat(tokenResponse.getExpiresIn()).isEqualTo(1800);

        assertThat(tokenResponse.getRefreshToken()).isNotNull();
        assertThat(tokenResponse.getRefreshToken()).isNotEmpty();
        assertThat(tokenResponse.getRefreshToken())
                .isEqualTo("32eb12f037712a6b60404d6d9c170ee9ae4d5b9936c73dd03c23fffff1213cb3");
    } // test_getOAuthAccessToken_should_success()

    @Test
    public void test_getOAuthAccessToken_should_fail_authCode_is_empty() {
        assertThatThrownBy(
                () -> this.auth.getOAuthAccessToken(""))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing oAuthCode");
    } // test_getOAuthAccessToken_should_fail_authCode_is_empty()

    @Test
    public void test_getOAuthAccessToken_should_fail_authCode_is_null() {
        assertThatThrownBy(
                () -> this.auth.getOAuthAccessToken(null))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing oAuthCode");
    } // test_getOAuthAccessToken_should_fail_authCode_is_null()

    @Test
    public void test_getOAuthAccessToken_should_fail_noConfiguration() throws IOException {
        SnapAuthorization sp = new SnapAuthorization(null);
        assertThatThrownBy(
                () -> sp.getOAuthAccessToken("code"))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Configuration unfound");
    } // test_getOAuthAccessToken_should_fail_noConfiguration()

    @Test
    public void test_getOAuthAccessToken_should_fail_noClientID() throws IOException {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setClientSecret("tata")
                .setRedirectUri("titi").build();
        SnapAuthorization sp = new SnapAuthorization(config);
        assertThatThrownBy(
                () -> sp.getOAuthAccessToken("code"))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing client ID");
    } // test_getOAuthAccessToken_should_fail_noClientID()

    @Test
    public void test_getOAuthAccessToken_should_fail_noClientSecret() throws IOException {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setClientId("tata")
                .setRedirectUri("titi").build();
        SnapAuthorization sp = new SnapAuthorization(config);
        assertThatThrownBy(
                () -> sp.getOAuthAccessToken("code"))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing client Secret");
    } // test_getOAuthAccessToken_should_fail_noClientSecret()

    @Test
    public void test_getOAuthAccessToken_should_fail_noRedirectURI() throws IOException {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setClientId("tata")
                .setClientSecret("tata").build();
        SnapAuthorization sp = new SnapAuthorization(config);
        assertThatThrownBy(
                () -> sp.getOAuthAccessToken("code"))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing Redirect URI");
    } // test_getOAuthAccessToken_should_fail_noRedirectURI()

    @Test
    public void should_throw_exception_401_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getOAuthAccessToken()

    @Test
    public void should_throw_exception_403_getOAuthAccessToken()
            throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Access Forbidden");
    } // should_throw_exception_403_getOAuthAccessToken()

    @Test
    public void should_throw_exception_404_getOAuthAccessToken()
            throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Found");
    } // should_throw_exception_404_getOAuthAccessToken()

    @Test
    public void should_throw_exception_405_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getOAuthAccessToken()

    @Test
    public void should_throw_exception_406_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Acceptable");
    } // should_throw_exception_406_getOAuthAccessToken()

    @Test
    public void should_throw_exception_410_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Gone");
    } // should_throw_exception_410_getOAuthAccessToken()

    @Test
    public void should_throw_exception_418_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("I'm a teapot");
    } // should_throw_exception_418_getOAuthAccessToken()

    @Test
    public void should_throw_exception_429_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getOAuthAccessToken()

    @Test
    public void should_throw_exception_500_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Internal Server Error");
    } // should_throw_exception_500_getOAuthAccessToken()

    @Test
    public void should_throw_exception_503_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Service Unavailable");
    } // should_throw_exception_503_getOAuthAccessToken()

    @Test
    public void should_throw_exception_1337_getOAuthAccessToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.getOAuthAccessToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Error 1337");
    } // should_throw_exception_1337_should_throw_exception_1337_getOAuthAccessToken()

    @Test
    public void test_refreshToken_should_success()
            throws SnapAuthorizationException, SnapResponseErrorException, IOException
            , SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapRefreshToken());
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        this.auth.setApiUrl("http://www.foo.com/foo/");
        TokenResponse tokenResponse =
                this.auth.refreshToken("32eb12f037712a6b60404d6d9c170ee9ae4d5b9936c73dd03c23fffff1213cb3");
        assertThat(tokenResponse).isNotNull();
        assertThat(tokenResponse.getAccessToken()).isNotNull();
        assertThat(tokenResponse.getAccessToken()).isNotEmpty();
        assertThat(tokenResponse.getAccessToken()).isEqualTo("0.1234567890");

        assertThat(tokenResponse.getExpiresIn()).isEqualTo(1800);

        assertThat(tokenResponse.getRefreshToken()).isNotNull();
        assertThat(tokenResponse.getRefreshToken()).isNotEmpty();
        assertThat(tokenResponse.getRefreshToken()).isEqualTo("xyz");
    } // test_refreshToken_should_success()

    @Test
    public void test_refreshToken_should_fail_refreshToken_is_empty() {
        assertThatThrownBy(
                () -> this.auth.refreshToken(""))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing refreshToken");
    } // test_refreshToken_should_fail_refreshToken_is_empty()

    @Test
    public void test_refreshToken_should_fail_refreshToken_is_null() {
        assertThatThrownBy(
                () -> this.auth.refreshToken(null))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing refreshToken");
    } // test_refreshToken_should_fail_refreshToken_is_null()

    @Test
    public void test_refreshToken_should_fail_noConfiguration() throws IOException {
        // SnapConfigurationBuilder configBuilder = new SnapConfigurationBuilder();
        SnapAuthorization sp = new SnapAuthorization(null);
        assertThatThrownBy(
                () -> sp.refreshToken("code"))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Configuration unfound");
    } // test_refreshToken_should_fail_noConfiguration()

    @Test
    public void test_refreshToken_should_fail_noClientID() throws IOException {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setClientSecret("tata")
                .setRedirectUri("titi").build();
        SnapAuthorization sp = new SnapAuthorization(config);
        assertThatThrownBy(
                () -> sp.refreshToken("code"))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing client ID");
    } // test_refreshToken_should_fail_noClientID()

    @Test
    public void test_refreshToken_should_fail_noClientSecret() throws IOException {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setClientId("tata")
                .setRedirectUri("titi").build();
        SnapAuthorization sp = new SnapAuthorization(config);
        assertThatThrownBy(
                () -> sp.refreshToken("code"))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing client Secret");
    } // test_refreshToken_should_fail_noClientSecret()

    @Test
    public void test_refreshToken_should_fail_noRedirectURI() throws IOException {
        SnapConfiguration config = new SnapConfiguration.Builder()
                .setClientId("tata")
                .setClientSecret("tata").build();
        SnapAuthorization sp = new SnapAuthorization(config);
        assertThatThrownBy(
                () -> sp.refreshToken("code"))
                .isInstanceOf(SnapAuthorizationException.class)
                .hasMessageContaining("Missing Redirect URI");
    } // test_refreshToken_should_fail_noRedirectURI()

    @Test
    public void should_throw_exception_401_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_refreshToken()

    @Test
    public void should_throw_exception_403_refreshToken()
            throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Access Forbidden");
    } // should_throw_exception_403_refreshToken()

    @Test
    public void should_throw_exception_404_refreshToken()
            throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Found");
    } // should_throw_exception_404_refreshToken()

    @Test
    public void should_throw_exception_405_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Method Not Allowed");
    } // should_throw_exception_405_refreshToken()

    @Test
    public void should_throw_exception_406_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Acceptable");
    } // should_throw_exception_406_refreshToken()

    @Test
    public void should_throw_exception_410_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Gone");
    } // should_throw_exception_410_refreshToken()

    @Test
    public void should_throw_exception_418_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("I'm a teapot");
    } // should_throw_exception_418_refreshToken()

    @Test
    public void should_throw_exception_429_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_refreshToken()

    @Test
    public void should_throw_exception_500_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Internal Server Error");
    } // should_throw_exception_500_refreshToken()

    @Test
    public void should_throw_exception_503_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Service Unavailable");
    } // should_throw_exception_503_refreshToken()

    @Test
    public void should_throw_exception_1337_refreshToken()
            throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> this.auth.refreshToken("toto"))
                .isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Error 1337");
    } // should_throw_exception_1337_refreshToken()
} // SnapAuthorizationTest
