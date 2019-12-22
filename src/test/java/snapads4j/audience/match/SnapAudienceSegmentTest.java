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

import com.fasterxml.jackson.core.JsonProcessingException;
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
import snapads4j.enums.*;
import snapads4j.exceptions.*;
import snapads4j.model.audience.match.AudienceSegment;
import snapads4j.model.audience.match.CreationSpec;
import snapads4j.model.audience.match.FormUserForAudienceSegment;
import snapads4j.model.audience.match.SamLookalikes;
import snapads4j.model.config.HttpDeleteWithBody;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    private AudienceSegment segment;

    private AudienceSegment segmentToUpdate;

    private SamLookalikes sam;

    private List<AudienceSegment> segments;

    private FormUserForAudienceSegment form;

    private List<String> data;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        snapAudienceSegment.setHttpClient(httpClient);
        snapAudienceSegment.setEntityUtilsWrapper(entityUtilsWrapper);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.segment = initAudienceSegment();
        this.segments = initAudienceSegments();
        this.segmentToUpdate = initAudienceSegmentForUpdate();
        data = new ArrayList<>();
        this.form = initFormUserForAudienceSegment(SchemaEnum.EMAIL_SHA256);
        this.sam = initSam();
    } // setUp()

    @Test
    public void test_create_audience_segment_should_success() throws IOException,
            SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapAudienceSegmentCreated());
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
    public void test_create_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(null, this.segment))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_create_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_create_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment("", this.segment))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_create_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_create_audience_segment_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapExecutionException.class);
    }// test_create_audience_segment_should_throw_SnapExecutionException()

    @Test
    public void test_create_audience_segment_should_throw_throw_SnapArgumentException_when_segment_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Segment parameter is required");
    } // test_create_audience_segment_should_throw_throw_SnapArgumentException_when_segment_is_null()

    @Test
    public void test_create_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_null() {
        this.segment.setAdAccountId(null);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_create_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_create_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty() {
        this.segment.setAdAccountId("");
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_create_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void test_create_audience_segment_should_throw_throw_SnapArgumentException_when_name_is_null() {
        this.segment.setName(null);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_create_audience_segment_should_throw_throw_SnapArgumentException_when_name_is_null()

    @Test
    public void test_create_audience_segment_should_throw_throw_SnapArgumentException_when_name_is_empty() {
        this.segment.setName("");
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_create_audience_segment_should_throw_throw_SnapArgumentException_when_name_is_empty()

    @Test
    public void test_create_audience_segment_should_throw_throw_SnapArgumentException_when_source_type_is_null() {
        this.segment.setSourceType(null);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The source type is required");
    } // test_create_audience_segment_should_throw_throw_SnapArgumentException_when_source_type_is_null()

    @Test
    public void test_create_audience_segment_should_throw_throw_SnapArgumentException_when_retention_days_is_lt_0() {
        this.segment.setRetentionInDays(-1);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The retention must be equal or greater than zero days");
    } // test_create_audience_segment_should_throw_throw_SnapArgumentException_when_retention_days_is_lt_0()

    @Test
    public void should_throw_exception_400_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_create_audience_segment()

    @Test
    public void should_throw_exception_401_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_audience_segment()

    @Test
    public void should_throw_exception_403_create_audience_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_audience_segment()

    @Test
    public void should_throw_exception_404_create_audience_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_audience_segment()

    @Test
    public void should_throw_exception_405_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_audience_segment()

    @Test
    public void should_throw_exception_406_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_audience_segment()

    @Test
    public void should_throw_exception_410_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_audience_segment()

    @Test
    public void should_throw_exception_418_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_audience_segment()

    @Test
    public void should_throw_exception_429_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_audience_segment()

    @Test
    public void should_throw_exception_500_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_audience_segment()

    @Test
    public void should_throw_exception_503_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_audience_segment()

    @Test
    public void should_throw_exception_1337_create_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_audience_segment()

    @Test
    public void test_get_all_audiences_segments_should_success() throws IOException,
            SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapAudienceSegmentsCreated());
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
    public void test_get_all_audiences_segments_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(null, this.adAccountId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_get_all_audiences_segments_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_get_all_audiences_segments_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments("", this.adAccountId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_get_all_audiences_segments_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_get_all_audiences_segments_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapExecutionException.class);
    }// test_get_all_audiences_segments_should_throw_SnapExecutionException()

    @Test
    public void test_get_all_audiences_segments_should_throw_throw_SnapArgumentException_when_adAccountId_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_get_all_audiences_segments_should_throw_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_get_all_audiences_segments_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, ""))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_get_all_audiences_segments_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void should_throw_exception_400_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_get_all_audiences_segment()

    @Test
    public void should_throw_exception_401_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_get_all_audiences_segment()

    @Test
    public void should_throw_exception_403_get_all_audiences_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_get_all_audiences_segment()

    @Test
    public void should_throw_exception_404_get_all_audiences_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_get_all_audiences_segment()

    @Test
    public void should_throw_exception_405_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_get_all_audiences_segment()

    @Test
    public void should_throw_exception_406_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_get_all_audiences_segment()

    @Test
    public void should_throw_exception_410_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_get_all_audiences_segment()

    @Test
    public void should_throw_exception_418_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_get_all_audiences_segment()

    @Test
    public void should_throw_exception_429_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_get_all_audiences_segment()

    @Test
    public void should_throw_exception_500_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_get_all_audiences_segment()

    @Test
    public void should_throw_exception_503_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_get_all_audiences_segment()

    @Test
    public void should_throw_exception_1337_get_all_audiences_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getAllAudienceSegments(oAuthAccessToken, this.adAccountId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_get_all_audiences_segment()

    @Test
    public void test_get_specific_audience_segment_should_success() throws IOException,
            SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapSpecificAudienceSegment());
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
    public void test_get_specific_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(null, specificId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_get_specific_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_get_specific_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment("", specificId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_get_specific_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_get_specific_audience_segment_should_throw_SnapExecutionException() throws
            IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapExecutionException.class);
    }// test_get_specific_audience_segment_should_throw_SnapExecutionException()

    @Test
    public void test_get_specific_audience_segment_should_throw_throw_SnapArgumentException_when_id_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("ID is required");
    } // test_get_specific_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_get_specific_audience_segment_should_throw_throw_SnapArgumentException_when_id_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, ""))
                .isInstanceOf(SnapArgumentException.class).hasMessage("ID is required");
    } // test_get_specific_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void should_throw_exception_400_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_get_specific_audience_segment()

    @Test
    public void should_throw_exception_401_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_get_specific_audience_segment()

    @Test
    public void should_throw_exception_403_get_specific_audience_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_get_specific_audience_segment()

    @Test
    public void should_throw_exception_404_get_specific_audience_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_get_specific_audience_segment()

    @Test
    public void should_throw_exception_405_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_get_specific_audience_segment()

    @Test
    public void should_throw_exception_406_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_get_specific_audience_segment()

    @Test
    public void should_throw_exception_410_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_get_specific_audience_segment()

    @Test
    public void should_throw_exception_418_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_get_specific_audience_segment()

    @Test
    public void should_throw_exception_429_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_get_specific_audience_segment()

    @Test
    public void should_throw_exception_500_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_get_specific_audience_segment()

    @Test
    public void should_throw_exception_503_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_get_specific_audience_segment()

    @Test
    public void should_throw_exception_1337_get_specific_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.getSpecificAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_get_specific_audience_segment()

    @Test
    public void test_update_audience_segment_should_success() throws IOException,
            SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapAudienceSegmentUpdated());
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
    public void test_update_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(null, this.segment))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_update_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_update_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment("", this.segment))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_update_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_update_audience_segment_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpPut.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapExecutionException.class);
    }// test_update_audience_segment_should_throw_SnapExecutionException()

    @Test
    public void test_update_audience_segment_should_throw_throw_SnapArgumentException_when_segment_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Segment parameter is required");
    } // test_update_audience_segment_should_throw_throw_SnapArgumentException_when_segment_is_null()

    @Test
    public void test_update_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_null() {
        this.segment.setAdAccountId(null);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_update_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_update_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty() {
        this.segment.setAdAccountId("");
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_update_audience_segment_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void test_update_audience_segment_should_throw_throw_SnapArgumentException_when_name_is_null() {
        this.segment.setName(null);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_update_audience_segment_should_throw_throw_SnapArgumentException_when_name_is_null()

    @Test
    public void test_update_audience_segment_should_throw_throw_SnapArgumentException_when_name_is_empty() {
        this.segment.setName("");
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_update_audience_segment_should_throw_throw_SnapArgumentException_when_name_is_empty()

    @Test
    public void test_update_audience_segment_should_throw_throw_SnapArgumentException_when_retention_days_is_lt_0() {
        this.segment.setRetentionInDays(-1);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The retention must be equal or greater than zero days");
    } // test_update_audience_segment_should_throw_throw_SnapArgumentException_when_retention_days_is_lt_0()

    @Test
    public void should_throw_exception_400_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_update_audience_segment()

    @Test
    public void should_throw_exception_401_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_update_audience_segment()

    @Test
    public void should_throw_exception_403_update_audience_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_update_audience_segment()

    @Test
    public void should_throw_exception_404_update_audience_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_update_audience_segment()

    @Test
    public void should_throw_exception_405_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_update_audience_segment()

    @Test
    public void should_throw_exception_406_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_update_audience_segment()

    @Test
    public void should_throw_exception_410_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_update_audience_segment()

    @Test
    public void should_throw_exception_418_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_update_audience_segment()

    @Test
    public void should_throw_exception_429_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_update_audience_segment()

    @Test
    public void should_throw_exception_500_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_update_audience_segment()

    @Test
    public void should_throw_exception_503_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_update_audience_segment()

    @Test
    public void should_throw_exception_1337_update_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.updateAudienceSegment(oAuthAccessToken, this.segment))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_update_audience_segment()

    @Test
    public void add_user_from_segment_should_success_when_data_added()
            throws SnapOAuthAccessTokenException, SnapResponseErrorException, IOException,
            SnapArgumentException, SnapNormalizeArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapAddUserForAudienceSegment());
        data.add("   yAssine.azimani@toto.com   ");
        data.add("john.jo@toto.com");
        assertThat(snapAudienceSegment.addUserToSegment(oAuthAccessToken, form)).isEqualTo(2);
    }// add_user_from_segment_should_success_when_data_added()

    @Test
    public void add_user_from_segment_should_success_when_zero_data_added()
            throws SnapOAuthAccessTokenException, JsonProcessingException, UnsupportedEncodingException,
            SnapResponseErrorException, SnapArgumentException, SnapNormalizeArgumentException, SnapExecutionException {
        assertThat(snapAudienceSegment.addUserToSegment(oAuthAccessToken, form)).isEqualTo(0);
    }// add_user_from_segment_should_success_when_zero_data_added()

    @Test
    public void add_user_from_segment_should_SnapOAuthAccessTokenException_when_oAuthAccessToken_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(null, form))
                .hasMessage("The OAuthAccessToken is required").isInstanceOf(SnapOAuthAccessTokenException.class);
    }// add_user_from_segment_should_SnapOAuthAccessTokenException_when_oAuthAccessToken_is_null()

    @Test
    public void add_user_from_segment_should_SnapOAuthAccessTokenException_when_oAuthAccessToken_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment("", form))
                .hasMessage("The OAuthAccessToken is required").isInstanceOf(SnapOAuthAccessTokenException.class);
    }// add_user_from_segment_should_SnapOAuthAccessTokenException_when_oAuthAccessToken_is_empty()

    @Test
    public void add_user_from_segment_should_throw_SnapArgumentException_when_data_is_null() {
        form.setData(null);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("List of hashed identifiers is required").isInstanceOf(SnapArgumentException.class);
    }// add_user_from_segment_should_throw_SnapArgumentException_when_data_is_null()

    @Test
    public void add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_null() {
        FormUserForAudienceSegment form = initFormUserForAudienceSegment(SchemaEnum.EMAIL_SHA256, false);
        form.setSchema(null);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Type schema is required").isInstanceOf(SnapArgumentException.class);
    }// add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_null()

    @Test
    public void add_user_from_segment_should_throw_SnapArgumentException_when_segment_id_is_null() {
        form.setId(null);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Segment ID is required").isInstanceOf(SnapArgumentException.class);
    }// add_user_from_segment_should_throw_SnapArgumentException_when_segment_id_is_null()

    @Test
    public void add_user_from_segment_should_throw_SnapArgumentException_when_segment_id_is_empty() {
        form.setId("");
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Segment ID is required").isInstanceOf(SnapArgumentException.class);
    }// add_user_from_segment_should_throw_SnapArgumentException_when_segment_id_is_empty()

    @Test
    public void add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_email_and_data_is_not_email() {
        form.setData(Stream.of("foo").collect(Collectors.toList()));
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid email(s)").isInstanceOf(SnapNormalizeArgumentException.class);
    }// add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_email_and_data_is_not_email()

    @Test
    public void add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_email_and_one_data_among_datas_not_email() {
        form.setData(Stream.of("foo", "bobo@test.com").collect(Collectors.toList()));
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid email(s)").isInstanceOf(SnapNormalizeArgumentException.class);
    }// add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_email_and_one_data_among_datas_not_email()

    @Test
    public void add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_phone_and_data_is_not_phone() {
        form.setData(Stream.of("102030405").collect(Collectors.toList()));
        form.setSchema(SchemaEnum.PHONE_SHA256);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid phone(s) number")
                .isInstanceOf(SnapNormalizeArgumentException.class);

        form.setData(Stream.of("A02#@!40B").collect(Collectors.toList()));
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid phone(s) number")
                .isInstanceOf(SnapNormalizeArgumentException.class);
    }// add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_phone_and_data_is_not_phone()

    @Test
    public void add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_phone_and_one_data_among_datas_not_phone() {
        form.setData(Stream.of("A02#@!40B", "0102030405", "123-456-7890", "(123)456-7890", "(123)4567890")
                .collect(Collectors.toList()));
        form.setSchema(SchemaEnum.PHONE_SHA256);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid phone(s) number")
                .isInstanceOf(SnapNormalizeArgumentException.class);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid phone(s) number")
                .isInstanceOf(SnapNormalizeArgumentException.class);
    }// add_user_from_segment_should_throw_SnapArgumentException_when_schema_is_phone_and_one_data_among_datas_not_phone()

    @Test
    public void should_throw_exception_400_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_add_user_from_segment()

    @Test
    public void should_throw_exception_401_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_add_user_from_segment()

    @Test
    public void should_throw_exception_403_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_add_user_from_segment()

    @Test
    public void should_throw_exception_404_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_add_user_from_segment()

    @Test
    public void should_throw_exception_405_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_add_user_from_segment()

    @Test
    public void should_throw_exception_406_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_add_user_from_segment()

    @Test
    public void should_throw_exception_410_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_add_user_from_segment()

    @Test
    public void should_throw_exception_418_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_add_user_from_segment()

    @Test
    public void should_throw_exception_429_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_add_user_from_segment()

    @Test
    public void should_throw_exception_500_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_add_user_from_segment()

    @Test
    public void should_throw_exception_503_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_add_user_from_segment()

    @Test
    public void should_throw_exception_1337_add_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_add_user_from_segment()

    @Test
    public void add_user_from_segment_should_throw_SnapExecutionException()
            throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.addUserToSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapExecutionException.class);
    }// add_user_from_segment_should_throw_SnapExecutionException()

    @Test
    public void delete_user_from_segment_should_success_when_data_add()
            throws SnapOAuthAccessTokenException, SnapResponseErrorException, IOException,
            SnapArgumentException, SnapNormalizeArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapDeleteUserForAudienceSegment());
        data.add("   yAssine.azimani@toto.com   ");
        data.add("john.jo@toto.com");
        assertThat(snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form)).isEqualTo(2);
    }// delete_user_from_segment_should_success_when_data_deleteed()

    @Test
    public void delete_user_from_segment_should_success_when_zero_data_add()
            throws SnapOAuthAccessTokenException, JsonProcessingException, UnsupportedEncodingException,
            SnapResponseErrorException, SnapArgumentException, SnapNormalizeArgumentException, SnapExecutionException {
        assertThat(snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form)).isEqualTo(0);
    }// delete_user_from_segment_should_success_when_zero_data_deleteed()

    @Test
    public void delete_user_from_segment_should_SnapOAuthAccessTokenException_when_oAuthAccessToken_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(null, form))
                .hasMessage("The OAuthAccessToken is required").isInstanceOf(SnapOAuthAccessTokenException.class);
    }// delete_user_from_segment_should_SnapOAuthAccessTokenException_when_oAuthAccessToken_is_null()

    @Test
    public void delete_user_from_segment_should_SnapOAuthAccessTokenException_when_oAuthAccessToken_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment("", form))
                .hasMessage("The OAuthAccessToken is required").isInstanceOf(SnapOAuthAccessTokenException.class);
    }// delete_user_from_segment_should_SnapOAuthAccessTokenException_when_oAuthAccessToken_is_empty()

    @Test
    public void delete_user_from_segment_should_throw_SnapArgumentException_when_data_is_null() {
        form.setData(null);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("List of hashed identifiers is required").isInstanceOf(SnapArgumentException.class);
    }// delete_user_from_segment_should_throw_SnapArgumentException_when_data_is_null()

    @Test
    public void delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_null() {
        FormUserForAudienceSegment form = initFormUserForAudienceSegment(SchemaEnum.EMAIL_SHA256, false);
        form.setSchema(null);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Type schema is required").isInstanceOf(SnapArgumentException.class);
    }// delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_null()

    @Test
    public void delete_user_from_segment_should_throw_SnapArgumentException_when_segment_id_is_null() {
        form.setId(null);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Segment ID is required").isInstanceOf(SnapArgumentException.class);
    }// delete_user_from_segment_should_throw_SnapArgumentException_when_segment_id_is_null()

    @Test
    public void delete_user_from_segment_should_throw_SnapArgumentException_when_segment_id_is_empty() {
        form.setId("");
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Segment ID is required").isInstanceOf(SnapArgumentException.class);
    }// delete_user_from_segment_should_throw_SnapArgumentException_when_segment_id_is_empty()

    @Test
    public void delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_email_and_data_is_not_email() {
        form.setData(Stream.of("foo").collect(Collectors.toList()));
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid email(s)").isInstanceOf(SnapNormalizeArgumentException.class);
    }// delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_email_and_data_is_not_email()

    @Test
    public void delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_email_and_one_data_among_datas_not_email() {
        form.setData(Stream.of("foo", "bobo@test.com").collect(Collectors.toList()));
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid email(s)").isInstanceOf(SnapNormalizeArgumentException.class);
    }// delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_email_and_one_data_among_datas_not_email()

    @Test
    public void delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_phone_and_data_is_not_phone() {
        form.setData(Stream.of("102030405").collect(Collectors.toList()));
        form.setSchema(SchemaEnum.PHONE_SHA256);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid phone(s) number")
                .isInstanceOf(SnapNormalizeArgumentException.class);

        form.setData(Stream.of("A02#@!40B").collect(Collectors.toList()));
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid phone(s) number")
                .isInstanceOf(SnapNormalizeArgumentException.class);
    }// delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_phone_and_data_is_not_phone()

    @Test
    public void delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_phone_and_one_data_among_datas_not_phone() {
        form.setData(Stream.of("A02#@!40B", "0102030405", "123-456-7890", "(123)456-7890", "(123)4567890")
                .collect(Collectors.toList()));
        form.setSchema(SchemaEnum.PHONE_SHA256);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid phone(s) number")
                .isInstanceOf(SnapNormalizeArgumentException.class);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .hasMessage("Data must be have valid phone(s) number")
                .isInstanceOf(SnapNormalizeArgumentException.class);
    }// delete_user_from_segment_should_throw_SnapArgumentException_when_schema_is_phone_and_one_data_among_datas_not_phone()

    @Test
    public void should_throw_exception_400_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_delete_user_from_segment()

    @Test
    public void should_throw_exception_401_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_delete_user_from_segment()

    @Test
    public void should_throw_exception_403_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_delete_user_from_segment()

    @Test
    public void should_throw_exception_404_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_delete_user_from_segment()

    @Test
    public void should_throw_exception_405_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_delete_user_from_segment()

    @Test
    public void should_throw_exception_406_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_delete_user_from_segment()

    @Test
    public void should_throw_exception_410_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_delete_user_from_segment()

    @Test
    public void should_throw_exception_418_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_delete_user_from_segment()

    @Test
    public void should_throw_exception_429_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_delete_user_from_segment()

    @Test
    public void should_throw_exception_500_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_delete_user_from_segment()

    @Test
    public void should_throw_exception_503_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_delete_user_from_segment()

    @Test
    public void should_throw_exception_1337_delete_user_from_segment() throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpDeleteWithBody.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_delete_user_from_segment()

    @Test
    public void delete_user_from_segment_should_throw_SnapExecutionException()
            throws IOException {
        form.setData(Stream.of("toto@toto.com").collect(Collectors.toList()));
        Mockito.when(httpClient.execute((Mockito.any(HttpDeleteWithBody.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.deleteUserFromSegment(oAuthAccessToken, form))
                .isInstanceOf(SnapExecutionException.class);
    }// delete_user_from_segment_should_throw_SnapExecutionException()

    @Test
    public void test_delete_all_users_from_segment_should_success() throws SnapResponseErrorException,
            SnapOAuthAccessTokenException, SnapArgumentException, IOException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapDeleteAllUsersFromAudienceSegment());
        Assertions.assertThatCode(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .doesNotThrowAnyException();
        Optional<AudienceSegment> opt = snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId);
        Assertions.assertThat(opt.isPresent()).isTrue();
        opt.ifPresent(a -> {
            assertThat(a.getId()).isEqualTo("5769345128988888");
            assertThat(a.getName()).isEqualTo("super duper sam");
            assertThat(a.getStatus()).isEqualTo(StatusEnum.ACTIVE);
            assertThat(a.getDescription()).isEqualTo("all the sams in the world");
            assertThat(a.getSourceType()).isEqualTo(SourceTypeEnum.FIRST_PARTY);
            assertThat(a.getRetentionInDays()).isEqualTo(180);
            assertThat(a.getAdAccountId()).isEqualTo("8adc3db7-8148-4fbf-999c-8d1111111d11");
            assertThat(a.getApproximateNumberUsers()).isEqualTo(0);
            assertThat(a.toString()).isNotEmpty();
            assertThat(sdf.format(a.getCreatedAt())).isEqualTo("2017-02-23T18:34:48.900Z");
            assertThat(sdf.format(a.getUpdatedAt())).isEqualTo("2017-02-23T19:01:30.080Z");
        });
    } // test_delete_all_users_from_segment_should_success()

    @Test
    public void test_delete_all_users_from_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(null, specificId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_delete_all_users_from_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_delete_all_users_from_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment("", specificId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_delete_all_users_from_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_delete_all_users_from_segment_should_throw_SnapExecutionException() throws
            IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpDelete.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapExecutionException.class);
    }// test_delete_all_users_from_segment_should_throw_SnapExecutionException()

    @Test
    public void test_delete_all_users_from_segment_should_throw_SnapArgumentException_when_segment_id_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The segment ID is required");
    } // test_delete_all_users_from_segment_should_throw_SnapArgumentException_when_segment_id_is_null()

    @Test
    public void test_delete_all_users_from_segment_should_throw_SnapArgumentException_when_segment_id_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, ""))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The segment ID is required");
    } // test_delete_all_users_from_segment_should_throw_SnapArgumentException_when_segment_id_is_empty()

    @Test
    public void should_throw_exception_401_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_403_delete_all_users_from_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_404_delete_all_users_from_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_405_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_406_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_410_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_418_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_429_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_500_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_503_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_delete_all_users_from_segment()

    @Test
    public void should_throw_exception_1337_delete_all_users_from_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAllUsersFromSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_delete_all_users_from_segment()

    @Test
    public void test_delete_audience_segment_should_success() throws
            IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapDeleteAudienceSegment());
        Assertions.assertThatCode(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .doesNotThrowAnyException();
    } // test_delete_audience_segment_should_success()

    @Test
    public void test_delete_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(null, specificId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_delete_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_delete_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment("", specificId))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_delete_audience_segment_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_delete_audience_segment_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpDelete.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapExecutionException.class);
    }// test_delete_audience_segment_should_throw_SnapExecutionException()

    @Test
    public void test_delete_audience_segment_should_throw_SnapArgumentException_when_segment_id_is_required() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The segment ID is required");
    } // test_delete_audience_segment_should_throw_SnapArgumentException_when_segment_id_is_required()

    @Test
    public void test_delete_audience_segment_should_throw_SnapArgumentException_when_segument_id_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, ""))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The segment ID is required");
    } // test_delete_audience_segment_should_throw_SnapArgumentException_when_segument_id_is_empty()

    @Test
    public void should_throw_exception_401_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_delete_audience_segment()

    @Test
    public void should_throw_exception_403_delete_audience_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_delete_audience_segment()

    @Test
    public void should_throw_exception_404_delete_audience_segment() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_delete_audience_segment()

    @Test
    public void should_throw_exception_405_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_delete_audience_segment()

    @Test
    public void should_throw_exception_406_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_delete_audience_segment()

    @Test
    public void should_throw_exception_410_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_delete_audience_segment()

    @Test
    public void should_throw_exception_418_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_delete_audience_segment()

    @Test
    public void should_throw_exception_429_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_delete_audience_segment()

    @Test
    public void should_throw_exception_500_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_delete_audience_segment()

    @Test
    public void should_throw_exception_503_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_delete_audience_segment()

    @Test
    public void should_throw_exception_1337_delete_audience_segment() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.deleteAudienceSegment(oAuthAccessToken, specificId))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_delete_audience_segment()

    @Test
    public void test_create_sam_look_a_likes_should_success() throws IOException,
            SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getSnapSamLookalikesCreated());
        Assertions.assertThatCode(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .doesNotThrowAnyException();
        Optional<AudienceSegment> optSegment = snapAudienceSegment.createSamLookalikes(oAuthAccessToken,
                this.sam);
        assertThat(optSegment.isPresent()).isTrue();
        optSegment.ifPresent(s -> {
            assertThat(s.getId()).isEqualTo("5652536396611584");
            assertThat(s.getName()).isEqualTo(this.sam.getName());
            assertThat(s.getStatus()).isEqualTo(StatusEnum.ACTIVE);
            assertThat(s.getDescription()).isEqualTo(this.sam.getDescription());
            assertThat(s.getSourceType()).isEqualTo(this.sam.getSourceType());
            assertThat(s.getRetentionInDays()).isEqualTo(this.sam.getRetentionInDays());
            assertThat(s.getAdAccountId()).isEqualTo(this.sam.getAdAccountId());
            assertThat(s.getApproximateNumberUsers()).isEqualTo(0);
            assertThat(s.getCreationSpec()).isNotNull();
            assertThat(s.getCreationSpec().getCountry()).isEqualTo(this.sam.getCreationSpec().getCountry());
            assertThat(s.getCreationSpec().getSeedSegmentId()).isEqualTo(this.sam.getCreationSpec().getSeedSegmentId());
            assertThat(s.getCreationSpec().getType()).isEqualTo(this.sam.getCreationSpec().getType());
            assertThat(s.toString()).isNotEmpty();
            assertThat(s.getCreationSpec().toString()).isNotEmpty();
            assertThat(sdf.format(s.getCreatedAt())).isEqualTo("2016-08-12T22:59:42.452Z");
            assertThat(sdf.format(s.getUpdatedAt())).isEqualTo("2016-08-12T22:59:42.452Z");
        });
    }// test_create_sam_look_a_likes_should_success()

    @Test
    public void test_create_sam_look_a_likes_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(null, this.sam))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_create_sam_look_a_likes_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void test_create_sam_look_a_likes_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes("", this.sam))
                .isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken is required");
    } // test_create_sam_look_a_likes_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void test_create_sam_look_a_likes_should_throw_SnapExecutionException() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapExecutionException.class);
    }// test_create_sam_look_a_likes_should_throw_SnapExecutionException()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_sam_is_null() {
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, null))
                .isInstanceOf(SnapArgumentException.class).hasMessage("Sam Lookalikes parameter is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_sam_is_null()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_adAccountId_is_null() {
        this.sam.setAdAccountId(null);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty() {
        this.sam.setAdAccountId("");
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_name_is_null() {
        this.sam.setName(null);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_name_is_null()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_name_is_empty() {
        this.sam.setName("");
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class).hasMessage("The name is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_name_is_empty()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_source_type_is_null() {
        this.sam.setSourceType(null);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The source type is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_source_type_is_null()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_source_type_is_not_lookalike() {
        this.sam.setSourceType(SourceTypeEnum.ENGAGEMENT);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The source type must be LOOKALIKE");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_source_type_is_not_lookalike()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_retention_days_is_lt_0() {
        this.sam.setRetentionInDays(-1);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The retention must be equal or greater than zero days");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_retention_days_is_lt_0()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_retention_days_is_gt_180() {
        this.sam.setRetentionInDays(181);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("The retention must be equal or less than 180 days");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_retention_days_is_gt_180()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_creation_spec_is_null() {
        this.sam.setCreationSpec(null);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessage("Lookalike creation spec is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_creation_spec_is_null()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_creation_spec_country_is_null() {
        this.sam.setCreationSpec(new CreationSpec());
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessageContaining("Lookalike creation spec country is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_creation_spec_country_is_null()

    @Test
    public void test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_creation_spec_seed_segment_id_is_null() {
        this.sam.setCreationSpec(new CreationSpec());
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessageContaining("Lookalike creation spec seed segment ID is required");
    } // test_create_sam_look_a_likes_should_throw_throw_SnapArgumentException_when_creation_spec_seed_segment_id_is_null()

    @Test
    public void test_create_sam_look_a_likes_should_not_throw_throw_SnapArgumentException_when_creation_spec_type_is_not_given() {
        this.sam.setCreationSpec(new CreationSpec());
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapArgumentException.class)
                .hasMessageContaining("Lookalike creation spec country is required")
                .hasMessageContaining("Lookalike creation spec seed segment ID is required");
        assertThat(new CreationSpec().getType()).isEqualTo(TypeCreationSpecDetails.BALANCE);
    } // test_create_sam_look_a_likes_should_not_throw_throw_SnapArgumentException_when_creation_spec_type_is_not_given()

    @Test
    public void should_throw_exception_400_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_401_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_403_create_sam_look_a_likes() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_404_create_sam_look_a_likes() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_405_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_406_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_410_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_418_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_429_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_500_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_503_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_sam_look_a_likes()

    @Test
    public void should_throw_exception_1337_create_sam_look_a_likes() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
        assertThatThrownBy(() -> snapAudienceSegment.createSamLookalikes(oAuthAccessToken, this.sam))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_sam_look_a_likes()

    private FormUserForAudienceSegment initFormUserForAudienceSegment(SchemaEnum schema) {
        return initFormUserForAudienceSegment(schema, true);
    }// initFormUserForAudienceSegment()

    private FormUserForAudienceSegment initFormUserForAudienceSegment(SchemaEnum schema, boolean withSchema) {
        FormUserForAudienceSegment form = new FormUserForAudienceSegment();
        form.setId(specificId);
        form.setSchema(withSchema ? schema : null);
        form.setData(data);
        return form;
    }// initFormUserForAudienceSegment()

    private AudienceSegment initAudienceSegment() {
        AudienceSegment segment = new AudienceSegment();
        segment.setAdAccountId(this.adAccountId);
        segment.setDescription("all the sams in the world");
        segment.setName("all the sams in the world");
        segment.setRetentionInDays(180);
        segment.setSourceType(SourceTypeEnum.FIRST_PARTY);
        return segment;
    }// initAudienceSegment()

    private SamLookalikes initSam() {
        SamLookalikes sam = new SamLookalikes();
        sam.setAdAccountId(this.adAccountId);
        sam.setDescription("similar to all the sams in the world");
        sam.setName("lookalikes of all the sams in the world");
        sam.setRetentionInDays(180);
        sam.setSourceType(SourceTypeEnum.LOOKALIKE);
        sam.setAdAccountId("d47d2516-4f1f-46f0-a63c-31a46804c3aa");
        CreationSpec cs = new CreationSpec();
        cs.setCountry("US");
        cs.setType(TypeCreationSpecDetails.REACH);
        cs.setSeedSegmentId("5677923948298240");
        sam.setCreationSpec(cs);
        return sam;
    }// initSam()

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
