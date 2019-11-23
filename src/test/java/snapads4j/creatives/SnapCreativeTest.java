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
package snapads4j.creatives;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.List;

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

import snapads4j.enums.CallToActionEnum;
import snapads4j.enums.CreativeTypeEnum;
import snapads4j.enums.PackagingStatusEnum;
import snapads4j.enums.ReviewStatusEnum;
import snapads4j.enums.TopSnapCropPositionEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.creatives.Creative;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

@RunWith(MockitoJUnitRunner.class)
public class SnapCreativeTest {

    @Spy
    private SnapCreative snapCreative;

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

    private final String oAuthAccessToken = "meowmeowmeow";
    
    private final String creativeID = "c1e6e929-acec-466f-b023-852b8cacc54f";
    
    private final String adAccountID = "8adc3db7-8148-4fbf-999c-8d2266369d74";
    
    private final String topSnapMediaId = "a7bee653-1865-41cf-8cee-8ab85a205837";

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapCreative.setHttpClient(httpClient);
	snapCreative.setEntityUtilsWrapper(entityUtilsWrapper);
    }// setUp()
    
    @Test
    public void test_get_all_creatives_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, ClientProtocolException, IOException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAllCreatives());
	Assertions.assertThatCode(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID)).doesNotThrowAnyException();
	List<Creative> creatives = snapCreative.getAllCreative(oAuthAccessToken, adAccountID);
	Assertions.assertThat(creatives).isNotNull();
	Assertions.assertThat(creatives).isNotEmpty();
	Assertions.assertThat(creatives).hasSize(5);
	Assertions.assertThat(creatives.get(0).getId()).isEqualTo("184fe3d0-ff80-4388-8d5f-05c340eff231");
	Assertions.assertThat(creatives.get(0).getName()).isEqualTo("Creative LFV");
	Assertions.assertThat(creatives.get(0).getAdAccountId()).isEqualTo(adAccountID);
	Assertions.assertThat(creatives.get(0).getType()).isEqualTo(CreativeTypeEnum.LONGFORM_VIDEO);
	Assertions.assertThat(creatives.get(0).getPackagingStatus()).isEqualTo(PackagingStatusEnum.PENDING);
	Assertions.assertThat(creatives.get(0).getReviewStatus()).isEqualTo(ReviewStatusEnum.PENDING_REVIEW);
	Assertions.assertThat(creatives.get(0).isShareable()).isEqualTo(true);
	Assertions.assertThat(creatives.get(0).getCallToAction()).isEqualTo(CallToActionEnum.WATCH);
	Assertions.assertThat(creatives.get(0).getTopSnapMediaId()).isEqualTo(topSnapMediaId);
	Assertions.assertThat(creatives.get(0).getTopSnapCropPosition()).isEqualTo(TopSnapCropPositionEnum.MIDDLE);
	Assertions.assertThat(creatives.get(0).getLongformVideoProperties()).isNotNull();
	Assertions.assertThat(creatives.get(0).getLongformVideoProperties().toString()).isNotEmpty();
	Assertions.assertThat(creatives.get(0).getLongformVideoProperties().getVideoMediaId()).isEqualTo("a7bee653-1865-41cf-8cee-8ab85a205837");
	Assertions.assertThat(creatives.get(1).getId()).isEqualTo("1c7065c2-ad9f-41cc-b2c5-d48d9810439b");
	Assertions.assertThat(creatives.get(1).getName()).isEqualTo("Creative App Install");
	Assertions.assertThat(creatives.get(1).getAdAccountId()).isEqualTo(adAccountID);
	Assertions.assertThat(creatives.get(1).getType()).isEqualTo(CreativeTypeEnum.APP_INSTALL);
	Assertions.assertThat(creatives.get(1).getPackagingStatus()).isEqualTo(PackagingStatusEnum.PENDING);
	Assertions.assertThat(creatives.get(1).getReviewStatus()).isEqualTo(ReviewStatusEnum.PENDING_REVIEW);
	Assertions.assertThat(creatives.get(1).isShareable()).isEqualTo(true);
	Assertions.assertThat(creatives.get(1).getCallToAction()).isEqualTo(CallToActionEnum.INSTALL_NOW);
	Assertions.assertThat(creatives.get(1).getTopSnapMediaId()).isEqualTo(topSnapMediaId);
	Assertions.assertThat(creatives.get(1).getTopSnapCropPosition()).isEqualTo(TopSnapCropPositionEnum.MIDDLE);
	Assertions.assertThat(creatives.get(1).getAppInstallProperties()).isNotNull();
	Assertions.assertThat(creatives.get(1).getAppInstallProperties().toString()).isNotEmpty();
	Assertions.assertThat(creatives.get(1).getAppInstallProperties().getAndroidAppUrl()).isEqualTo("com.snapchat.android");
	Assertions.assertThat(creatives.get(1).getAppInstallProperties().getAppName()).isEqualTo("Cool App Yo");
	Assertions.assertThat(creatives.get(1).getAppInstallProperties().getIconMediaId()).isEqualTo("ab32d7e5-1f80-4e1a-a76b-3c543d2b28e4");
	Assertions.assertThat(creatives.get(1).getAppInstallProperties().getIosAppId()).isEqualTo("447188370");
	
	Assertions.assertThat(creatives.get(2).getId()).isEqualTo("313e8415-6294-47d6-b064-5a0d9f21d224");
	Assertions.assertThat(creatives.get(2).getName()).isEqualTo("Creative LFV 2");
	Assertions.assertThat(creatives.get(2).getAdAccountId()).isEqualTo(adAccountID);
	Assertions.assertThat(creatives.get(2).getType()).isEqualTo(CreativeTypeEnum.LONGFORM_VIDEO);
	Assertions.assertThat(creatives.get(2).getPackagingStatus()).isEqualTo(PackagingStatusEnum.PENDING);
	Assertions.assertThat(creatives.get(2).getReviewStatus()).isEqualTo(ReviewStatusEnum.PENDING_REVIEW);
	Assertions.assertThat(creatives.get(2).isShareable()).isEqualTo(true);
	Assertions.assertThat(creatives.get(2).getCallToAction()).isEqualTo(CallToActionEnum.WATCH);
	Assertions.assertThat(creatives.get(2).getTopSnapMediaId()).isEqualTo(topSnapMediaId);
	Assertions.assertThat(creatives.get(2).getTopSnapCropPosition()).isEqualTo(TopSnapCropPositionEnum.MIDDLE);
	Assertions.assertThat(creatives.get(2).getLongformVideoProperties()).isNotNull();
	Assertions.assertThat(creatives.get(2).getLongformVideoProperties().toString()).isNotEmpty();
	Assertions.assertThat(creatives.get(2).getLongformVideoProperties().getVideoMediaId()).isEqualTo("a7bee653-1865-41cf-8cee-8ab85a205837");
	Assertions.assertThat(creatives.get(3).getId()).isEqualTo("67e4296c-486b-4bf3-877b-f34e8eeb173c");
	Assertions.assertThat(creatives.get(3).getName()).isEqualTo("Creative WV");
	Assertions.assertThat(creatives.get(3).getAdAccountId()).isEqualTo(adAccountID);
	Assertions.assertThat(creatives.get(3).getType()).isEqualTo(CreativeTypeEnum.WEB_VIEW);
	Assertions.assertThat(creatives.get(3).getPackagingStatus()).isEqualTo(PackagingStatusEnum.PENDING);
	Assertions.assertThat(creatives.get(3).getReviewStatus()).isEqualTo(ReviewStatusEnum.PENDING_REVIEW);
	Assertions.assertThat(creatives.get(3).isShareable()).isEqualTo(true);
	Assertions.assertThat(creatives.get(3).getCallToAction()).isEqualTo(CallToActionEnum.VIEW_MORE);
	Assertions.assertThat(creatives.get(3).getTopSnapMediaId()).isEqualTo(topSnapMediaId);
	Assertions.assertThat(creatives.get(3).getTopSnapCropPosition()).isEqualTo(TopSnapCropPositionEnum.MIDDLE);
	Assertions.assertThat(creatives.get(3).getWebViewProperties()).isNotNull();
	Assertions.assertThat(creatives.get(3).getWebViewProperties().toString()).isNotEmpty();
	Assertions.assertThat(creatives.get(3).getWebViewProperties().getUrl()).isEqualTo("http://snapchat.com/ads");
	Assertions.assertThat(creatives.get(4).getId()).isEqualTo("c1e6e929-acec-466f-b023-852b8cacc18f");
	Assertions.assertThat(creatives.get(4).getName()).isEqualTo("Creative Creative");
	Assertions.assertThat(creatives.get(4).getAdAccountId()).isEqualTo(adAccountID);
	Assertions.assertThat(creatives.get(4).getType()).isEqualTo(CreativeTypeEnum.SNAP_AD);
	Assertions.assertThat(creatives.get(4).getPackagingStatus()).isEqualTo(PackagingStatusEnum.PENDING);
	Assertions.assertThat(creatives.get(4).getReviewStatus()).isEqualTo(ReviewStatusEnum.PENDING_REVIEW);
	Assertions.assertThat(creatives.get(4).isShareable()).isEqualTo(true);
	Assertions.assertThat(creatives.get(4).getTopSnapMediaId()).isEqualTo(topSnapMediaId);
	Assertions.assertThat(creatives.get(4).getTopSnapCropPosition()).isEqualTo(TopSnapCropPositionEnum.MIDDLE);
    }// test_get_all_creatives_should_success()
    
    @Test
    public void test_get_all_creatives_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapCreative.getAllCreative("", adAccountID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_all_creatives_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_all_creatives_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapCreative.getAllCreative(null, adAccountID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_all_creatives_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_all_creatives_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The AdAccount ID is mandatory");
    }// test_get_all_creatives_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_all_creatives_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The AdAccount ID is mandatory");
    }// test_get_all_creatives_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_get_all_creatives_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapCreative.getAllCreative(oAuthAccessToken, adAccountID);
    }// test_get_all_creatives_should_throw_IOException()
    
    @Test
    public void should_throw_exception_400_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_all_creatives()

    @Test
    public void should_throw_exception_401_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_all_creatives()

    @Test
    public void should_throw_exception_403_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_all_creatives()

    @Test
    public void should_throw_exception_404_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_all_creatives()

    @Test
    public void should_throw_exception_405_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_all_creatives()

    @Test
    public void should_throw_exception_406_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_all_creatives()

    @Test
    public void should_throw_exception_410_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_all_creatives()

    @Test
    public void should_throw_exception_418_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_all_creatives()

    @Test
    public void should_throw_exception_429_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_all_creatives()

    @Test
    public void should_throw_exception_500_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_all_creatives()

    @Test
    public void should_throw_exception_503_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_all_creatives()

    @Test
    public void should_throw_exception_1337_get_all_creatives() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getAllCreative(oAuthAccessToken, adAccountID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_all_creatives()
    
    @Test
    public void test_get_specific_creative_should_success() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
	.thenReturn(SnapResponseUtils.getSnapSpecificCreative());
	Assertions.assertThatCode(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID)).doesNotThrowAnyException();
	Assertions.assertThat(snapCreative.getSpecificCreative(oAuthAccessToken, creativeID)).isNotNull();
	snapCreative.getSpecificCreative(oAuthAccessToken, creativeID)
		.ifPresent(creative -> {
		    Assertions.assertThat(creative.toString()).isNotNull();
		    Assertions.assertThat(creative.toString()).isNotEmpty();
		    Assertions.assertThat(creative.getId()).isEqualTo(creativeID);
		    Assertions.assertThat(creative.getName()).isEqualTo("Creative Creative");
		    Assertions.assertThat(creative.isShareable()).isEqualTo(true);
		    Assertions.assertThat(creative.getAdAccountId()).isEqualTo(adAccountID);
		    Assertions.assertThat(creative.getTopSnapMediaId()).isEqualTo(topSnapMediaId);
		    Assertions.assertThat(creative.getPackagingStatus()).isEqualTo(PackagingStatusEnum.PENDING);
		    Assertions.assertThat(creative.getReviewStatus()).isEqualTo(ReviewStatusEnum.PENDING_REVIEW);
		    Assertions.assertThat(creative.getType()).isEqualTo(CreativeTypeEnum.SNAP_AD);
		    Assertions.assertThat(creative.getTopSnapCropPosition()).isEqualTo(TopSnapCropPositionEnum.MIDDLE);
		});
    }// test_get_specific_creative_should_success()

    @Test
    public void test_get_specific_creative_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapCreative.getSpecificCreative("", creativeID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_specific_creative_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_specific_creative_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(null, creativeID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_specific_creative_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_specific_creative_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Creative ID is mandatory");
    }// test_get_specific_creative_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_specific_creative_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Creative ID is mandatory");
    }// test_get_specific_creative_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_specific_creative_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapCreative.getSpecificCreative(oAuthAccessToken, creativeID);
    }// test_specific_creative_should_throw_IOException()
    
    @Test
    public void should_throw_exception_400_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_specific_creative()

    @Test
    public void should_throw_exception_401_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_specific_creative()

    @Test
    public void should_throw_exception_403_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_specific_creative()

    @Test
    public void should_throw_exception_404_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_specific_creative()

    @Test
    public void should_throw_exception_405_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_specific_creative()

    @Test
    public void should_throw_exception_406_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_specific_creative()

    @Test
    public void should_throw_exception_410_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_specific_creative()

    @Test
    public void should_throw_exception_418_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_specific_creative()

    @Test
    public void should_throw_exception_429_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_specific_creative()

    @Test
    public void should_throw_exception_500_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_specific_creative()

    @Test
    public void should_throw_exception_503_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_specific_creative()

    @Test
    public void should_throw_exception_1337_specific_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getSpecificCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_specific_creative()
    
    @Test
    public void test_get_preview_creative_should_success() throws ClientProtocolException, IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
	.thenReturn(SnapResponseUtils.getSnapPreviewCreative());
	Assertions.assertThatCode(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID)).doesNotThrowAnyException();
	Assertions.assertThat(snapCreative.getPreviewCreative(oAuthAccessToken, creativeID)).isNotNull();
	Assertions.assertThat(snapCreative.getPreviewCreative(oAuthAccessToken, creativeID)).isNotEmpty();
	Assertions.assertThat(snapCreative.getPreviewCreative(oAuthAccessToken, creativeID).containsKey("snapcodeLink")).isTrue();
	Assertions.assertThat(snapCreative.getPreviewCreative(oAuthAccessToken, creativeID).containsKey("expiresAt")).isTrue();
	Assertions.assertThat(snapCreative.getPreviewCreative(oAuthAccessToken, creativeID).get("snapcodeLink")).isEqualTo("https://adsapisc.appspot.com/snapcodeimage/c1e6e929-acec-466f-b023-852b8cacc54f/352a899e-4a9d-3fdf-8efe-9bac9b8a0a21");
    }// test_get_preview_creative_should_success()

    @Test
    public void test_get_preview_creative_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapCreative.getPreviewCreative("", creativeID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_preview_creative_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_preview_creative_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(null, creativeID))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    }// test_get_preview_creative_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_preview_creative_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The creative ID is missing");
    }// test_get_preview_creative_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_preview_creative_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The creative ID is missing");
    }// test_get_preview_creative_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_preview_creative_should_throw_IOException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	snapCreative.getPreviewCreative(oAuthAccessToken, creativeID);
    }// test_preview_creative_should_throw_IOException()
    
    @Test
    public void should_throw_exception_400_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_preview_creative()

    @Test
    public void should_throw_exception_401_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_preview_creative()

    @Test
    public void should_throw_exception_403_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_preview_creative()

    @Test
    public void should_throw_exception_404_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_preview_creative()

    @Test
    public void should_throw_exception_405_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_preview_creative()

    @Test
    public void should_throw_exception_406_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_preview_creative()

    @Test
    public void should_throw_exception_410_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_preview_creative()

    @Test
    public void should_throw_exception_418_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_preview_creative()

    @Test
    public void should_throw_exception_429_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_preview_creative()

    @Test
    public void should_throw_exception_500_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_preview_creative()

    @Test
    public void should_throw_exception_503_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_preview_creative()

    @Test
    public void should_throw_exception_1337_preview_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapCreative.getPreviewCreative(oAuthAccessToken, creativeID))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_preview_creative()
    
}// SnapCreativeTest
