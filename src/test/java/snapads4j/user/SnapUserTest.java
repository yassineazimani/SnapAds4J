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
package snapads4j.user;

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
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.user.AuthenticatedUser;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests mocked for SnapUser.
 */
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

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        snapUser.setHttpClient(httpClient);
        snapUser.setEntityUtilsWrapper(entityUtilsWrapper);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    } // setUp()

    @Test
    public void test_aboutMe_should_success()
            throws SnapOAuthAccessTokenException, SnapResponseErrorException, IOException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAuthenticatedUser());
        Optional<AuthenticatedUser> optUser = snapUser.aboutMe(oAuthAccessToken);
        assertThat(optUser.isPresent()).isTrue();
        optUser.ifPresent(user -> {
            assertThat(user.toString()).isNotEmpty();
            assertThat(user.getDisplayName()).isEqualTo("Honey Badger");
            assertThat(user.getEmail()).isEqualTo("honey.badger@hooli.com");
            assertThat(user.getId()).isEqualTo("2f5dd7e6-fcd1-4324-8455-1ea4d96caaaa");
            assertThat(user.getOrganizationId()).isEqualTo("40d6719b-da09-410b-9185-0cc9c0dfed1d");
            assertThat(sdf.format(user.getCreatedAt())).isEqualTo("2016-08-12T01:56:39.842Z");
            assertThat(sdf.format(user.getUpdatedAt())).isEqualTo("2016-08-12T01:56:39.841Z");
        });
    } // test_aboutMe_should_success()

    @Test
    public void test_aboutMe_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> snapUser.aboutMe(null)).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_aboutMe_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_aboutMe_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> snapUser.aboutMe("")).isInstanceOf(SnapOAuthAccessTokenException.class)
                .hasMessage("The OAuthAccessToken is required");
    } // test_aboutMe_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void should_throw_exception_401_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_aboutMe()

    @Test
    public void should_throw_exception_403_aboutMe() throws IOException {

        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Access Forbidden");
    } // should_throw_exception_403_aboutMe()

    @Test
    public void should_throw_exception_404_aboutMe() throws IOException {

        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Found");
    } // should_throw_exception_404_aboutMe()

    @Test
    public void should_throw_exception_405_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Method Not Allowed");
    } // should_throw_exception_405_aboutMe()

    @Test
    public void should_throw_exception_406_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Not Acceptable");
    } // should_throw_exception_406_aboutMe()

    @Test
    public void should_throw_exception_410_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Gone");
    } // should_throw_exception_410_aboutMe()

    @Test
    public void should_throw_exception_418_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("I'm a teapot");
    } // should_throw_exception_418_aboutMe()

    @Test
    public void should_throw_exception_429_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_aboutMe()

    @Test
    public void should_throw_exception_500_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Internal Server Error");
    } // should_throw_exception_500_aboutMe()

    @Test
    public void should_throw_exception_503_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Service Unavailable");
    } // should_throw_exception_503_aboutMe()

    @Test
    public void should_throw_exception_1337_aboutMe() throws IOException {
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> snapUser.aboutMe(oAuthAccessToken)).isInstanceOf(SnapResponseErrorException.class)
                .hasMessage("Error 1337");
    } // should_throw_exception_1337_aboutMe()
} // SnapUserTest
