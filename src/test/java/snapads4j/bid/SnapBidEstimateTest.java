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
package snapads4j.bid;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

import snapads4j.enums.OptimizationGoalEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.bid.BidEstimate;
import snapads4j.model.bid.TargetingSpecBidEstimate;
import snapads4j.model.demographics.Demographics;
import snapads4j.model.demographics.DemographicsBuilder;
import snapads4j.model.geolocation.GeoLocation;
import snapads4j.model.geolocation.GeolocationBuilder;
import snapads4j.model.targeting.TargetingBuilder;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

@RunWith(MockitoJUnitRunner.class)
public class SnapBidEstimateTest {

    @Spy
    private SnapBidEstimate snapBidEstimate;
    
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

    private final String adSquadId = "c5995202-bfbc-45a0-9702-dd6841af52fe";
    
    private TargetingSpecBidEstimate targetingSpecBidEstimate;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapBidEstimate.setHttpClient(httpClient);
	snapBidEstimate.setEntityUtilsWrapper(entityUtilsWrapper);
	this.targetingSpecBidEstimate = initTargetingSpec();
    } // setUp()
    
    @Test
    public void test_get_bid_estimate_by_squad_id_should_success() throws IOException, InterruptedException,
	    SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getSnapBidEstimateByAdSquadID());
	Assertions.assertThatCode(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, adSquadId))
		.doesNotThrowAnyException();
	Optional<BidEstimate> optSize = snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, adSquadId);
	Assertions.assertThat(optSize.isPresent()).isTrue();
	optSize.ifPresent(s -> {
	    Assertions.assertThat(s.getAdSquadId()).isNotEmpty();
	    Assertions.assertThat(s.getAdSquadId()).isEqualTo(this.adSquadId);
	    Assertions.assertThat(s.getOptimizationGoal()).isEqualTo(OptimizationGoalEnum.IMPRESSIONS);
	    Assertions.assertThat(s.getBidEstimateMinimum()).isEqualTo(8000000);
	    Assertions.assertThat(s.getBidEstimateMaximum()).isEqualTo(9000000);
	});
    }// test_get_bid_estimate_by_squad_id_should_success()

    @Test
    public void test_get_bid_estimate_by_squad_id_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(null, this.adSquadId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_bid_estimate_by_squad_id_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_bid_estimate_by_squad_id_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId("", this.adSquadId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_bid_estimate_by_squad_id_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_get_bid_estimate_by_squad_id_should_throw_SnapExecutionException() throws ClientProtocolException,
	    IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
	.isInstanceOf(SnapExecutionException.class);
    }// test_get_bid_estimate_by_squad_id_should_throw_SnapExecutionException()

    @Test
    public void test_get_bid_estimate_by_squad_id_should_throw_throw_SnapArgumentException_when_id_is_null() {
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("AdSquad ID is required");
    } // test_get_bid_estimate_by_squad_id_should_throw_throw_SnapArgumentException_when_adAccountId_is_null()

    @Test
    public void test_get_bid_estimate_by_squad_id_should_throw_throw_SnapArgumentException_when_id_is_empty() {
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("AdSquad ID is required");
    } // test_get_bid_estimate_by_squad_id_should_throw_throw_SnapArgumentException_when_adAccountId_is_empty()

    @Test
    public void should_throw_exception_400_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_401_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_403_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_404_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_405_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_406_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_410_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_418_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_429_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_500_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_503_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_get_bid_estimate_by_squad_id()

    @Test
    public void should_throw_exception_1337_get_bid_estimate_by_squad_id() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateByAdSquadId(oAuthAccessToken, this.adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_get_bid_estimate_by_squad_id()
    
    @Test
    public void test_get_bid_estimate_by_squad_spec_should_success() throws IOException, InterruptedException,
	    SnapOAuthAccessTokenException, SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getSnapBidEstimateBySquadSpec());
	Optional<BidEstimate> optSize = snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, adAccountId, targetingSpecBidEstimate);
	Assertions.assertThat(optSize.isPresent()).isTrue();
	optSize.ifPresent(s -> {
	    Assertions.assertThat(s.getOptimizationGoal()).isEqualTo(OptimizationGoalEnum.IMPRESSIONS);
	    Assertions.assertThat(s.getBidEstimateMinimum()).isEqualTo(8000000);
	    Assertions.assertThat(s.getBidEstimateMaximum()).isEqualTo(9000000);
	});
    }// test_get_bid_estimate_by_squad_spec_should_success()
    
    @Test
    public void test_get_bid_estimate_by_squad_spec_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(null, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_bid_estimate_by_squad_spec_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_get_bid_estimate_by_squad_spec_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec("", this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_get_bid_estimate_by_squad_spec_should_throw_SnapOAuthAccessTokenException_2()
    
    @Test
    public void test_get_bid_estimate_by_squad_spec_should_throw_SnapArgumentException_when_ad_account_id_is_null() {
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, null, targetingSpecBidEstimate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Ad Account ID is required");
    } // test_get_bid_estimate_by_squad_spec_should_throw_SnapArgumentException_when_ad_account_id_is_null()
    
    @Test
    public void test_get_bid_estimate_by_squad_spec_should_throw_SnapArgumentException_when_targetingSpecBidEstimate_is_null() {
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("TargetingSpecBidEstimate instance is required");
    } // test_get_bid_estimate_by_squad_spec_should_throw_SnapArgumentException_when_targetingSpecBidEstimate_is_null()
    
    @Test
    public void test_get_bid_estimate_by_squad_spec_should_throw_SnapArgumentException_when_optimization_goal_is_null() {
	targetingSpecBidEstimate.setOptimizationGoal(null);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Optimization goal is required");
    } // test_get_bid_estimate_by_squad_spec_should_throw_SnapArgumentException_when_targetingSpecBidEstimate_is_null()
    
    @Test
    public void test_get_bid_estimate_by_squad_spec_should_throw_SnapArgumentException_when_targeting_is_null() {
	targetingSpecBidEstimate.setTargeting(null);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Targeting is required");
    } // test_get_bid_estimate_by_squad_spec_should_throw_SnapArgumentException_when_targetingSpecBidEstimate_is_null()
    
    @Test
    public void test_get_bid_estimate_by_squad_spec_should_throw_SnapExecutionException() throws ClientProtocolException,
	    IOException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpPost.class)))).thenThrow(IOException.class);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(this.oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
	.isInstanceOf(SnapExecutionException.class);
    }// test_get_bid_estimate_by_squad_spec_should_throw_SnapExecutionException()
    
    @Test
    public void should_throw_exception_400_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    } // should_throw_exception_400_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_401_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_403_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_404_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_405_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_406_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_410_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_418_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_429_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_500_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_503_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_get_bid_estimate_by_squad_spec()

    @Test
    public void should_throw_exception_1337_get_bid_estimate_by_squad_spec() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> snapBidEstimate.getBidEstimateBySquadSpec(oAuthAccessToken, this.adAccountId, targetingSpecBidEstimate))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_get_bid_estimate_by_squad_spec()
    
    private TargetingSpecBidEstimate initTargetingSpec() {
	TargetingBuilder targetBuilder = new TargetingBuilder();
	List<GeoLocation> geos = new ArrayList<>();
	geos.add(new GeolocationBuilder().setCountryCode("us").build());
	targetBuilder.setGeolocation(geos);
	List<Demographics> demographics = new ArrayList<>();
	List<String> ageGroups = new ArrayList<>();
	ageGroups.add("13-17");
	ageGroups.add("18-20");
	ageGroups.add("21-24");
	demographics.add(new DemographicsBuilder().setAgeGroups(ageGroups).build());
	targetBuilder.setDemographics(demographics);
	TargetingSpecBidEstimate targetingSpecBidEstimate = new TargetingSpecBidEstimate();
	targetingSpecBidEstimate.setOptimizationGoal(OptimizationGoalEnum.APP_INSTALLS);
	targetingSpecBidEstimate.setTargeting(targetBuilder.build());
	return targetingSpecBidEstimate;
    }// initAdSquad()
}// SnapBidEstimateTest
