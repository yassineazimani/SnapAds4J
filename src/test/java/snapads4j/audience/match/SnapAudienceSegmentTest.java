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
package snapads4j.audience.match;

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
import org.apache.http.client.methods.HttpPost;
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

import snapads4j.enums.SourceTypeEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.audience.match.AudienceSegment;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

@RunWith(MockitoJUnitRunner.class)
public class SnapAudienceSegmentTest {
    
    @Spy
    private SnapAudienceSegment snapAudienceSegment;
    
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
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    
    private AudienceSegment segment;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapAudienceSegment.setHttpClient(httpClient);
	snapAudienceSegment.setEntityUtilsWrapper(entityUtilsWrapper);
	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	this.segment = initAudienceSegment();
    } // setUp()
    
    @Test
    public void test_create_audience_segment_should_success() throws IOException, InterruptedException,
	    SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapSegmentCreated());
	Assertions.assertThatCode(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment)).doesNotThrowAnyException();
	Optional<AudienceSegment> optSegment = snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment);
	optSegment.ifPresent(s -> {
	    assertThat(s.getId()).isEqualTo("5677923948298240");
	    assertThat(s.getName()).isEqualTo(this.segment.getName());
	    assertThat(s.getStatus()).isEqualTo(StatusEnum.PENDING);
	    assertThat(s.getDescription()).isEqualTo(this.segment.getDescription());
	    assertThat(s.getSourceType()).isEqualTo(this.segment.getSourceType());
	    assertThat(s.getRetentionInDays()).isEqualTo(this.segment.getRetentionInDays());
	    assertThat(s.getAdAccountId()).isEqualTo(this.segment.getAdAccountId());
	    assertThat(s.getApproximateNumberUsers()).isEqualTo(0);
	    assertThat(s.toString()).isNotEmpty();
	    assertThat(sdf.format(s.getCreatedAt())).isEqualTo("2016-08-12T22:59:42.405Z");
	    assertThat(sdf.format(s.getUpdatedAt())).isEqualTo("2016-08-12T22:59:42.452Z");
	});
    }// test_create_audience_segment_should_success()

    @Test
    public void test_create_audience_segment_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(null, this.segment))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_create_audience_segment_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_audience_segment_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment("", this.segment)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_create_audience_segment_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_audience_segment_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment);
    }// test_create_audience_segment_should_throw_IOException()
    
    @Test
    public void test_create_audience_segment_should_throw_SnapArgumentException_when_segment_is_null() {
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Segment parameter is not given");
    } // test_create_audience_segment_should_throw_SnapArgumentException_when_segment_is_null()

    @Test
    public void test_create_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_null() {
	this.segment.setAdAccountId(null);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_create_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_create_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_empty() {
	this.segment.setAdAccountId("");
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_create_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void test_create_audience_segment_should_throw_SnapArgumentException_when_name_is_null() {
	this.segment.setName(null);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_create_audience_segment_should_throw_SnapArgumentException_when_name_is_null()
    
    @Test
    public void test_create_audience_segment_should_throw_SnapArgumentException_when_name_is_empty() {
	this.segment.setName("");
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_create_audience_segment_should_throw_SnapArgumentException_when_name_is_empty()

    @Test
    public void test_create_audience_segment_should_throw_SnapArgumentException_when_source_type_is_null() {
	this.segment.setSourceType(null);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The source type is required");
    } // test_create_audience_segment_should_throw_SnapArgumentException_when_source_type_is_null()

    @Test
    public void test_create_audience_segment_should_throw_SnapArgumentException_when_retention_days_is_lt_0() {
	this.segment.setRetentionInDays(-1);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The retention must be equal or greater than zero");
    } // test_create_audience_segment_should_throw_SnapArgumentException_when_retention_days_is_lt_0()

    @Test
    public void should_throw_exception_400_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_create_audience_segment()

    @Test
    public void should_throw_exception_401_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_audience_segment()

    @Test
    public void should_throw_exception_403_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_audience_segment()

    @Test
    public void should_throw_exception_404_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_audience_segment()

    @Test
    public void should_throw_exception_405_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_audience_segment()

    @Test
    public void should_throw_exception_406_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_audience_segment()

    @Test
    public void should_throw_exception_410_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_audience_segment()

    @Test
    public void should_throw_exception_418_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_audience_segment()

    @Test
    public void should_throw_exception_429_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_audience_segment()

    @Test
    public void should_throw_exception_500_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_audience_segment()

    @Test
    public void should_throw_exception_503_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_audience_segment()

    @Test
    public void should_throw_exception_1337_create_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_audience_segment()

    private AudienceSegment initAudienceSegment() {
	AudienceSegment segment = new AudienceSegment();
	segment.setAdAccountId(this.adAccountId);
	segment.setDescription("all the sams in the world");
	segment.setName("all the sams in the world");
	segment.setRetentionInDays(180);
	segment.setSourceType(SourceTypeEnum.FIRST_PARTY);
	return segment;
    }
}// SnapAudienceSegmentTest
