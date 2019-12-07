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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
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

import snapads4j.enums.SourceTypeEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.enums.TargetableStatusEnum;
import snapads4j.enums.TypeCreationSpecDetails;
import snapads4j.enums.UploadStatusEnum;
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
    
    private final String specificId = "5701023945457664";

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private AudienceSegment segment;
    
    private AudienceSegment segmentToUpdate;

    private List<AudienceSegment> segments;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapAudienceSegment.setHttpClient(httpClient);
	snapAudienceSegment.setEntityUtilsWrapper(entityUtilsWrapper);
	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	this.segment = initAudienceSegment();
	this.segments = initAudienceSegments();
	this.segmentToUpdate = initAudienceSegmentForUpdate();
    } // setUp()

    @Test
    public void test_create_audience_segment_should_success() throws IOException, InterruptedException,
	    SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAudienceSegmentCreated());
	Assertions.assertThatCode(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
		.doesNotThrowAnyException();
	Optional<AudienceSegment> optSegment = snapAudienceSegment.createAudienceSegment(oAuthAccessToken,
		this.segment);
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
	assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment("", this.segment))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
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
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("The retention must be equal or greater than zero");
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

    @Test
    public void test_get_all_audiences_segments_should_success() throws IOException, InterruptedException,
	    SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAudienceSegmentsCreated());
	Assertions.assertThatCode(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.doesNotThrowAnyException();
	List<AudienceSegment> segmentsReturned = snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken,
		this.adAccountId);
	assertThat(segmentsReturned).isNotNull();
	assertThat(segmentsReturned).isNotEmpty();
	assertThat(segmentsReturned).hasSize(2);
	for (int i = 0; i < segmentsReturned.size(); ++i) {
	    AudienceSegment s = segmentsReturned.get(i);
	    AudienceSegment sResult = this.segments.get(i);
	    assertThat(s.getId()).isEqualTo(sResult.getId());
	    assertThat(s.getName()).isEqualTo(sResult.getName());
	    assertThat(s.getStatus()).isEqualTo(sResult.getStatus());
	    assertThat(s.getDescription()).isEqualTo(sResult.getDescription());
	    assertThat(s.getSourceType()).isEqualTo(sResult.getSourceType());
	    assertThat(s.getRetentionInDays()).isEqualTo(sResult.getRetentionInDays());
	    assertThat(s.getAdAccountId()).isEqualTo(sResult.getAdAccountId());
	    assertThat(s.getApproximateNumberUsers()).isEqualTo(sResult.getApproximateNumberUsers());
	    assertThat(s.toString()).isNotEmpty();
	}
	assertThat(sdf.format(segmentsReturned.get(0).getCreatedAt())).isEqualTo("2016-08-12T21:11:01.196Z");
	assertThat(sdf.format(segmentsReturned.get(0).getUpdatedAt())).isEqualTo("2016-08-12T21:11:01.325Z");
	assertThat(sdf.format(segmentsReturned.get(1).getCreatedAt())).isEqualTo("2016-08-12T20:58:16.036Z");
	assertThat(sdf.format(segmentsReturned.get(1).getUpdatedAt())).isEqualTo("2016-08-12T20:58:16.098Z");

    }// test_get_all_audiences_segments_should_success()

    @Test
    public void test_get_all_audiences_segments_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(null, this.adAccountId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_all_audiences_segments_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_all_audiences_segments_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments("", this.adAccountId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_all_audiences_segments_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_all_audiences_segments_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId);
    }// test_get_all_audiences_segments_should_throw_IOException()

    @Test
    public void test_get_all_audiences_segments_should_throw_SnapArgumentException_when_adAccountId_is_null() {
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_get_all_audiences_segments_should_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_get_all_audiences_segments_should_throw_SnapArgumentException_when_adAccountId_is_empty() {
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_get_all_audiences_segments_should_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void should_throw_exception_400_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_get_all_audiences_segment()

    @Test
    public void should_throw_exception_401_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_get_all_audiences_segment()

    @Test
    public void should_throw_exception_403_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_get_all_audiences_segment()

    @Test
    public void should_throw_exception_404_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_get_all_audiences_segment()

    @Test
    public void should_throw_exception_405_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_get_all_audiences_segment()

    @Test
    public void should_throw_exception_406_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_get_all_audiences_segment()

    @Test
    public void should_throw_exception_410_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_get_all_audiences_segment()

    @Test
    public void should_throw_exception_418_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_get_all_audiences_segment()

    @Test
    public void should_throw_exception_429_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_get_all_audiences_segment()

    @Test
    public void should_throw_exception_500_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_get_all_audiences_segment()

    @Test
    public void should_throw_exception_503_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_get_all_audiences_segment()

    @Test
    public void should_throw_exception_1337_get_all_audiences_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_get_all_audiences_segment()

    @Test
    public void test_get_specific_audience_segment_should_success() throws IOException, InterruptedException,
	    SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapSpecificAudienceSegment());
	Assertions.assertThatCode(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.doesNotThrowAnyException();
	Optional<AudienceSegment> optSegment = snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken,
		specificId);
	optSegment.ifPresent(s -> {
	    assertThat(s.getId()).isEqualTo(this.specificId);
	    assertThat(s.getName()).isEqualTo("Lookalike");
	    assertThat(s.getStatus()).isEqualTo(StatusEnum.ACTIVE);
	    assertThat(s.getSourceType()).isEqualTo(SourceTypeEnum.LOOKALIKE);
	    assertThat(s.getTargetableStatus()).isEqualTo(TargetableStatusEnum.READY);
	    assertThat(s.getUploadStatus()).isEqualTo(UploadStatusEnum.COMPLETE);
	    assertThat(s.getRetentionInDays()).isEqualTo(180);
	    assertThat(s.getAdAccountId()).isEqualTo("3f539865-c001-4f5e-bd31-5ae129a4550a");
	    assertThat(s.getApproximateNumberUsers()).isEqualTo(11487000);
	    assertThat(s.toString()).isNotEmpty();
	    assertThat(s.getCreationSpec()).isNotNull();
	    assertThat(s.getCreationSpec().getCountry()).isEqualTo("US");
	    assertThat(s.getCreationSpec().getSeedSegmentId()).isEqualTo("5749764677173248");
	    assertThat(s.getCreationSpec().getType()).isEqualTo(TypeCreationSpecDetails.BALANCE);
	    assertThat(s.getCreationSpec().toString()).isNotEmpty();
	    assertThat(sdf.format(s.getCreatedAt())).isEqualTo("2018-03-09T00:57:57.462Z");
	    assertThat(sdf.format(s.getUpdatedAt())).isEqualTo("2018-06-25T02:13:52.956Z");
	});
    }// test_get_specific_audience_segment_should_success()

    @Test
    public void test_get_specific_audience_segment_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(null, specificId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_specific_audience_segment_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_specific_audience_segment_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment("", specificId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_specific_audience_segment_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_specific_audience_segment_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId);
    }// test_get_specific_audience_segment_should_throw_IOException()

    @Test
    public void test_get_specific_audience_segment_should_throw_SnapArgumentException_when_id_is_null() {
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("ID is required");
    } // test_get_specific_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_get_specific_audience_segment_should_throw_SnapArgumentException_when_id_is_empty() {
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("ID is required");
    } // test_get_specific_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void should_throw_exception_400_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_get_specific_audience_segment()

    @Test
    public void should_throw_exception_401_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_get_specific_audience_segment()

    @Test
    public void should_throw_exception_403_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_get_specific_audience_segment()

    @Test
    public void should_throw_exception_404_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_get_specific_audience_segment()

    @Test
    public void should_throw_exception_405_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_get_specific_audience_segment()

    @Test
    public void should_throw_exception_406_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_get_specific_audience_segment()

    @Test
    public void should_throw_exception_410_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_get_specific_audience_segment()

    @Test
    public void should_throw_exception_418_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_get_specific_audience_segment()

    @Test
    public void should_throw_exception_429_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_get_specific_audience_segment()

    @Test
    public void should_throw_exception_500_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_get_specific_audience_segment()

    @Test
    public void should_throw_exception_503_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_get_specific_audience_segment()

    @Test
    public void should_throw_exception_1337_get_specific_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_get_specific_audience_segment()
    
    @Test
    public void test_update_audience_segment_should_success() throws IOException, InterruptedException,
	    SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAudienceSegmentUpdated());
	Assertions.assertThatCode(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.doesNotThrowAnyException();
	Optional<AudienceSegment> optSegment = snapAudienceSegment.updateAudienceSegment(oAuthAccessToken,
		this.segment);
	optSegment.ifPresent(s -> {
	    assertThat(s.getId()).isEqualTo(segmentToUpdate.getId());
	    assertThat(s.getName()).isEqualTo(segmentToUpdate.getName());
	    assertThat(s.getStatus()).isEqualTo(segmentToUpdate.getStatus());
	    assertThat(s.getDescription()).isEqualTo(segmentToUpdate.getDescription());
	    assertThat(s.getSourceType()).isEqualTo(segmentToUpdate.getSourceType());
	    assertThat(s.getRetentionInDays()).isEqualTo(segmentToUpdate.getRetentionInDays());
	    assertThat(s.getAdAccountId()).isEqualTo(segmentToUpdate.getAdAccountId());
	    assertThat(s.getApproximateNumberUsers()).isEqualTo(segmentToUpdate.getApproximateNumberUsers());
	    assertThat(s.getTargetableStatus()).isEqualTo(segmentToUpdate.getTargetableStatus());
	    assertThat(s.getUploadStatus()).isEqualTo(segmentToUpdate.getUploadStatus());
	    assertThat(s.getOrganizationId()).isEqualTo(segmentToUpdate.getOrganizationId());
	    assertThat(s.getVisibleTo()).isEqualTo(segmentToUpdate.getVisibleTo());
	    assertThat(s.toString()).isNotEmpty();
	    assertThat(sdf.format(s.getCreatedAt())).isEqualTo("2019-03-28T14:47:17.956Z");
	    assertThat(sdf.format(s.getUpdatedAt())).isEqualTo("2019-11-06T17:56:57.053Z");
	});
    }// test_update_audience_segment_should_success()

    @Test
    public void test_update_audience_segment_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(null, this.segment))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_update_audience_segment_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_update_audience_segment_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment("", this.segment))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_update_audience_segment_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_update_audience_segment_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPut.class)))).thenThrow(IOException.class);
	snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment);
    }// test_update_audience_segment_should_throw_IOException()

    @Test
    public void test_update_audience_segment_should_throw_SnapArgumentException_when_segment_is_null() {
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Segment parameter is not given");
    } // test_update_audience_segment_should_throw_SnapArgumentException_when_segment_is_null()

    @Test
    public void test_update_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_null() {
	this.segment.setAdAccountId(null);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_update_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_update_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_empty() {
	this.segment.setAdAccountId("");
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_update_audience_segment_should_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void test_update_audience_segment_should_throw_SnapArgumentException_when_name_is_null() {
	this.segment.setName(null);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_update_audience_segment_should_throw_SnapArgumentException_when_name_is_null()

    @Test
    public void test_update_audience_segment_should_throw_SnapArgumentException_when_name_is_empty() {
	this.segment.setName("");
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_update_audience_segment_should_throw_SnapArgumentException_when_name_is_empty()

    @Test
    public void test_update_audience_segment_should_throw_SnapArgumentException_when_retention_days_is_lt_0() {
	this.segment.setRetentionInDays(-1);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("The retention must be equal or greater than zero");
    } // test_update_audience_segment_should_throw_SnapArgumentException_when_retention_days_is_lt_0()

    @Test
    public void should_throw_exception_400_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_update_audience_segment()

    @Test
    public void should_throw_exception_401_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_update_audience_segment()

    @Test
    public void should_throw_exception_403_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_update_audience_segment()

    @Test
    public void should_throw_exception_404_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_update_audience_segment()

    @Test
    public void should_throw_exception_405_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_update_audience_segment()

    @Test
    public void should_throw_exception_406_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_update_audience_segment()

    @Test
    public void should_throw_exception_410_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_update_audience_segment()

    @Test
    public void should_throw_exception_418_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_update_audience_segment()

    @Test
    public void should_throw_exception_429_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_update_audience_segment()

    @Test
    public void should_throw_exception_500_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_update_audience_segment()

    @Test
    public void should_throw_exception_503_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_update_audience_segment()

    @Test
    public void should_throw_exception_1337_update_audience_segment() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_update_audience_segment()

    private AudienceSegment initAudienceSegment() {
	AudienceSegment segment = new AudienceSegment();
	segment.setAdAccountId(this.adAccountId);
	segment.setDescription("all the sams in the world");
	segment.setName("all the sams in the world");
	segment.setRetentionInDays(180);
	segment.setSourceType(SourceTypeEnum.FIRST_PARTY);
	return segment;
    }// initAudienceSegment()
    
    private AudienceSegment initAudienceSegmentForUpdate() {
	AudienceSegment segment = new AudienceSegment();
	segment.setId("5603670370513719");
	segment.setAdAccountId(this.adAccountId);
	segment.setDescription("A list of Honey bear lovers across the globe");
	segment.setName("Honey Bear Segment 2019");
	segment.setAdAccountId("22225ba6-7559-4000-9663-bace8adff5f1");
	segment.setOrganizationId("1fdeefec-f502-4ca8-9a84-6411e0a51052");
	segment.setRetentionInDays(60);
	segment.setStatus(StatusEnum.PAUSED);
	segment.setTargetableStatus(TargetableStatusEnum.TOO_FEW_USERS);
	segment.setUploadStatus(UploadStatusEnum.COMPLETE);
	segment.setSourceType(SourceTypeEnum.FIRST_PARTY);
	segment.setApproximateNumberUsers(500000);
	List<String> visibleTo = new ArrayList<>();
	visibleTo.add("AdAccountEntity_22225ba6-7559-4000-9663-bace8adff5f1");
	segment.setVisibleTo(visibleTo);
	return segment;
    }// initAudienceSegmentForUpdate()

    public List<AudienceSegment> initAudienceSegments() {
	List<AudienceSegment> results = new ArrayList<>();
	AudienceSegment segment = new AudienceSegment();
	segment.setAdAccountId(this.adAccountId);
	segment.setDescription("all the sams in the world");
	segment.setName("super duper sam 2");
	segment.setRetentionInDays(180);
	segment.setSourceType(SourceTypeEnum.FIRST_PARTY);
	segment.setStatus(StatusEnum.PENDING);
	segment.setId("5689640350646272");
	AudienceSegment segment2 = new AudienceSegment();
	segment2.setAdAccountId(this.adAccountId);
	segment2.setDescription("all the sams in the world");
	segment2.setName("super duper sam");
	segment2.setRetentionInDays(180);
	segment2.setSourceType(SourceTypeEnum.FIRST_PARTY);
	segment2.setStatus(StatusEnum.PENDING);
	segment2.setId("5715031928864768");
	results.add(segment);
	results.add(segment2);
	return results;
    }// initAudienceSegments()
}// SnapAudienceSegmentTest
