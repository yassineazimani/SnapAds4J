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
package snapads4j.campaigns;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
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

import snapads4j.enums.StatusEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.campaigns.Campaign;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

/** Unit tests mocked for SnapCampaigns. */
@RunWith(MockitoJUnitRunner.class)
public class SnapCampaignsTest {

    @Spy
    private SnapCampaigns sCampaigns;

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

    private final String accountId = "8adc3db7-8148-4fbf-999c-8d2266369d74";

    private final String id = "92e1c28a-a331-45b4-8c26-fd3e0eea8c39";

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	sCampaigns.setHttpClient(httpClient);
	sCampaigns.setEntityUtilsWrapper(entityUtilsWrapper);
    } // setUp()

    @Test
    public void test_create_campaign_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
	    SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapCampaignCreated());
	Assertions.assertThatCode(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.doesNotThrowAnyException();
	Optional<Campaign> optCampaign = sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation());
	optCampaign.ifPresent(campaign -> {
	    assertThat(campaign.getId()).isEqualTo(id);
	    assertThat(campaign.getName()).isEqualTo("Cool Campaign");
	    assertThat(campaign.getStatus()).isEqualTo(StatusEnum.PAUSED);
	    assertThat(campaign.getName()).isEqualTo("Cool Campaign");
	    assertThat(campaign.getAdAccountId()).isEqualTo(accountId);
	});
    } // test_create_campaign_should_success()

    @Test
    public void test_create_campaign_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sCampaigns.createCampaign(null, this.initCampaignForCreation()))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_create_campaign_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_create_campaign_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sCampaigns.createCampaign("", this.initCampaignForCreation()))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_create_campaign_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_create_campaign_should_throw_SnapArgumentException_0() {
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Campaign parameter is not given");
    } // test_create_campaign_should_throw_SnapArgumentException_0()

    @Test
    public void test_create_campaign_should_throw_SnapArgumentException_1() {
	Campaign c = new Campaign();
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, c))
		.isInstanceOf(SnapArgumentException.class).hasMessage(
			"The start time is required,The campaign name is required,The campaign status is required,The Ad Account ID is required");
    } // test_create_campaign_should_throw_SnapArgumentException_1()

    @Test
    public void test_create_campaign_should_throw_SnapArgumentException_2() {
	Campaign c = new Campaign();
	c.setName("Cool Campaign");
	c.setAdAccountId(null);
	c.setStatus(null);
	c.setStartTime(null);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, c))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("The start time is required,The campaign status is required,The Ad Account ID is required");
    } // test_create_campaign_should_throw_SnapArgumentException_2()

    @Test
    public void test_create_campaign_should_throw_SnapArgumentException_3() {
	Campaign c = new Campaign();
	c.setName("Cool Campaign");
	c.setAdAccountId(null);
	c.setStatus(StatusEnum.PAUSED);
	c.setStartTime(null);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, c))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("The start time is required,The Ad Account ID is required");
    } // test_create_campaign_should_throw_SnapArgumentException_3()

    @Test
    public void test_create_campaign_should_throw_SnapArgumentException_4() {
	Campaign c = new Campaign();
	c.setName("Cool Campaign");
	c.setAdAccountId(null);
	c.setStatus(StatusEnum.PAUSED);
	c.setStartTime(new Date());
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, c))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is required");
    } // test_create_campaign_should_throw_SnapArgumentException_4()

    @Test
    public void should_throw_exception_401_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_create_campaign()

    @Test
    public void should_throw_exception_403_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_create_campaign()

    @Test
    public void should_throw_exception_404_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_create_campaign()

    @Test
    public void should_throw_exception_405_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_create_campaign()

    @Test
    public void should_throw_exception_406_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_create_campaign()

    @Test
    public void should_throw_exception_410_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_create_campaign()

    @Test
    public void should_throw_exception_418_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_create_campaign()

    @Test
    public void should_throw_exception_429_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_create_campaign()

    @Test
    public void should_throw_exception_500_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_create_campaign()

    @Test
    public void should_throw_exception_503_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_create_campaign()

    @Test
    public void should_throw_exception_1337_create_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.createCampaign(oAuthAccessToken, this.initCampaignForCreation()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_create_campaign()

    @Test
    public void test_update_campaign_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
	    SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapCampaignUpdated());
	Assertions.assertThatCode(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.doesNotThrowAnyException();
	Optional<Campaign> optCampaign = sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate());
	optCampaign.ifPresent(campaign -> {
	    assertThat(campaign.getId()).isEqualTo(id);
	    assertThat(campaign.getName()).isEqualTo("Cool Campaign");
	    assertThat(campaign.getStatus()).isEqualTo(StatusEnum.PAUSED);
	    assertThat(campaign.getName()).isEqualTo("Cool Campaign");
	    assertThat(campaign.getAdAccountId()).isEqualTo(accountId);
	});
    } // test_update_campaign_should_success()

    @Test
    public void test_update_campaign_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sCampaigns.updateCampaign(null, this.initCampaignForCreation()))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_update_campaign_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_update_campaign_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sCampaigns.updateCampaign("", this.initCampaignForCreation()))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_update_campaign_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_update_campaign_should_throw_SnapArgumentException_0() {
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("Campaign parameter is not given");
    } // test_update_campaign_should_throw_SnapArgumentException_0()

    @Test
    public void test_update_campaign_should_throw_SnapArgumentException_1() {
	Campaign c = new Campaign();
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, c))
		.isInstanceOf(SnapArgumentException.class).hasMessage(
			"The campaign ID is required,The campaign name is required,The campaign status is required,The Ad Account ID is required");
    } // test_update_campaign_should_throw_SnapArgumentException_1()

    @Test
    public void test_update_campaign_should_throw_SnapArgumentException_2() {
	Campaign c = new Campaign();
	c.setName("Cool Campaign");
	c.setAdAccountId(null);
	c.setStatus(null);
	c.setStartTime(null);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, c))
		.isInstanceOf(SnapArgumentException.class).hasMessage(
			"The campaign ID is required,The campaign status is required,The Ad Account ID is required");
    } // test_update_campaign_should_throw_SnapArgumentException_2()

    @Test
    public void test_update_campaign_should_throw_SnapArgumentException_3() {
	Campaign c = new Campaign();
	c.setName("Cool Campaign");
	c.setAdAccountId(null);
	c.setStatus(StatusEnum.PAUSED);
	c.setStartTime(null);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, c))
		.isInstanceOf(SnapArgumentException.class)
		.hasMessage("The campaign ID is required,The Ad Account ID is required");
    } // test_update_campaign_should_throw_SnapArgumentException_3()

    @Test
    public void test_update_campaign_should_throw_SnapArgumentException_4() {
	Campaign c = new Campaign();
	c.setName("Cool Campaign");
	c.setAdAccountId("a");
	c.setStatus(StatusEnum.PAUSED);
	c.setStartTime(new Date());
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, c))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The campaign ID is required");
    } // test_update_campaign_should_throw_SnapArgumentException_4()

    @Test
    public void should_throw_exception_401_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_update_campaign()

    @Test
    public void should_throw_exception_403_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_update_campaign()

    @Test
    public void should_throw_exception_404_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_update_campaign()

    @Test
    public void should_throw_exception_405_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_update_campaign()

    @Test
    public void should_throw_exception_406_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_update_campaign()

    @Test
    public void should_throw_exception_410_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_update_campaign()

    @Test
    public void should_throw_exception_418_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_update_campaign()

    @Test
    public void should_throw_exception_429_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_update_campaign()

    @Test
    public void should_throw_exception_500_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_update_campaign()

    @Test
    public void should_throw_exception_503_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_update_campaign()

    @Test
    public void should_throw_exception_1337_update_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpPut.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.updateCampaign(oAuthAccessToken, this.initCampaignForUpdate()))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_update_campaign()

    @Test
    public void test_getAllCampaigns_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
	    SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapAllCampaigns());
	List<Campaign> campaigns = sCampaigns.getAllCampaigns(oAuthAccessToken, accountId);
	assertThat(campaigns).isNotNull();
	assertThat(campaigns).isNotEmpty();
	assertThat(campaigns).hasSize(4);

	assertThat(campaigns.get(0).getId()).isEqualTo("06302efa-4c0f-4e36-b880-a395a36cef64");
	assertThat(campaigns.get(0).getStatus()).isEqualTo(StatusEnum.ACTIVE);
	assertThat(campaigns.get(0).getName()).isEqualTo("Campaign One");
	assertThat(campaigns.get(0).getAdAccountId()).isEqualTo(accountId);
	assertThat(campaigns.get(0).getDailyBudgetMicro()).isEqualTo(200000000.);

	assertThat(campaigns.get(1).getId()).isEqualTo("0fc8e179-6f3b-46e7-be8e-ca53fd404ece");
	assertThat(campaigns.get(1).getStatus()).isEqualTo(StatusEnum.ACTIVE);
	assertThat(campaigns.get(1).getName()).isEqualTo("Campaign Deux");
	assertThat(campaigns.get(1).getAdAccountId()).isEqualTo(accountId);
	assertThat(campaigns.get(1).getDailyBudgetMicro()).isEqualTo(500000000.);

	assertThat(campaigns.get(2).getId()).isEqualTo("92e1c28a-a331-45b4-8c26-fd3e0eea8c39");
	assertThat(campaigns.get(2).getStatus()).isEqualTo(StatusEnum.PAUSED);
	assertThat(campaigns.get(2).getName()).isEqualTo("Cool Campaign");
	assertThat(campaigns.get(2).getAdAccountId()).isEqualTo(accountId);

	assertThat(campaigns.get(3).getId()).isEqualTo("fedf8e04-0176-4ce3-a1ca-148204aee62c");
	assertThat(campaigns.get(3).getStatus()).isEqualTo(StatusEnum.PAUSED);
	assertThat(campaigns.get(3).getName()).isEqualTo("Crazy Campaign");
	assertThat(campaigns.get(3).getAdAccountId()).isEqualTo(accountId);
    } // test_getAllCampaigns_should_success()

    @Test
    public void test_getAllCampaigns_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(null, accountId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getAllCampaigns_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_getAllCampaigns_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns("", accountId))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getAllCampaigns_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_getAllCampaigns_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is mandatory");
    } // test_getAllCampaigns_should_throw_SnapArgumentException_1()

    @Test
    public void test_getAllCampaigns_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The Ad Account ID is mandatory");
    } // test_getAllCampaigns_should_throw_SnapArgumentException_2()

    @Test
    public void should_throw_exception_401_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getAllCampaigns()

    @Test
    public void should_throw_exception_403_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_getAllCampaigns()

    @Test
    public void should_throw_exception_404_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_getAllCampaigns()

    @Test
    public void should_throw_exception_405_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getAllCampaigns()

    @Test
    public void should_throw_exception_406_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_getAllCampaigns()

    @Test
    public void should_throw_exception_410_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_getAllCampaigns()

    @Test
    public void should_throw_exception_418_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_getAllCampaigns()

    @Test
    public void should_throw_exception_429_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getAllCampaigns()

    @Test
    public void should_throw_exception_500_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_getAllCampaigns()

    @Test
    public void should_throw_exception_503_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_getAllCampaigns()

    @Test
    public void should_throw_exception_1337_getAllCampaigns() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getAllCampaigns(oAuthAccessToken, accountId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_getAllCampaigns()

    @Test
    public void test_getSpecificCampaign_should_success() throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity)).thenReturn(SnapResponseUtils.getSnapSpecificCampaign());
	final String id = "92e1c28a-a331-45b4-8c26-fd3e0eea8c39";
	Optional<Campaign> optCampaign = sCampaigns.getSpecificCampaign(oAuthAccessToken, id);
	assertThat(optCampaign.isPresent()).isTrue();
	optCampaign.ifPresent(f -> {
	    assertThat(f.getId()).isEqualTo(id);
	    assertThat(f.getName()).isEqualTo("Cool Campaign");
	    assertThat(f.getAdAccountId()).isEqualTo(accountId);
	    assertThat(f.getStatus()).isEqualTo(StatusEnum.PAUSED);
	});
    } // test_getSpecificCampaign_should_success()

    @Test
    public void test_getSpecificCampaign_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(null, id))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getSpecificCampaign_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_getSpecificCampaign_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign("", id))
		.isInstanceOf(SnapOAuthAccessTokenException.class).hasMessage("The OAuthAccessToken must to be given");
    } // test_getSpecificCampaign_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_getSpecificCampaign_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The campaign ID is mandatory");
    } // test_getSpecificCampaign_should_throw_SnapArgumentException_1()

    @Test
    public void test_getSpecificAdAccount_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The campaign ID is mandatory");
    } // test_getSpecificAdAccount_should_throw_SnapArgumentException_2()

    @Test
    public void should_throw_exception_401_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_getSpecificCampaign()

    @Test
    public void should_throw_exception_403_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_getSpecificCampaign()

    @Test
    public void should_throw_exception_404_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_getSpecificCampaign()

    @Test
    public void should_throw_exception_405_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_getSpecificCampaign()

    @Test
    public void should_throw_exception_406_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_getSpecificCampaign()

    @Test
    public void should_throw_exception_410_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_getSpecificCampaign()

    @Test
    public void should_throw_exception_418_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_getSpecificCampaign()

    @Test
    public void should_throw_exception_429_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_getSpecificCampaign()

    @Test
    public void should_throw_exception_500_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_getSpecificCampaign()

    @Test
    public void should_throw_exception_503_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_getSpecificCampaign()

    @Test
    public void should_throw_exception_1337_getSpecificCampaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> sCampaigns.getSpecificCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_getSpecificCampaign()

    @Test
    public void test_delete_campaign_should_success() throws SnapResponseErrorException, SnapOAuthAccessTokenException,
	    SnapArgumentException, IOException, InterruptedException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	Assertions.assertThatCode(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id)).doesNotThrowAnyException();
    } // test_delete_campaign_should_success()

    @Test
    public void test_delete_campaign_should_throw_SnapOAuthAccessTokenException_1() {
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(null, id)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_delete_campaign_should_throw_SnapOAuthAccessTokenException_1()

    @Test
    public void test_delete_campaign_should_throw_SnapOAuthAccessTokenException_2() {
	assertThatThrownBy(() -> sCampaigns.deleteCampaign("", id)).isInstanceOf(SnapOAuthAccessTokenException.class)
		.hasMessage("The OAuthAccessToken must to be given");
    } // test_delete_campaign_should_throw_SnapOAuthAccessTokenException_2()

    @Test
    public void test_delete_campaign_should_throw_SnapArgumentException_1() {
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, null))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The campaign ID is mandatory");
    } // test_delete_campaign_should_throw_SnapArgumentException_1()

    @Test
    public void test_delete_campaign_should_throw_SnapArgumentException_2() {
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, ""))
		.isInstanceOf(SnapArgumentException.class).hasMessage("The campaign ID is mandatory");
    } // test_delete_campaign_should_throw_SnapArgumentException_2()

    @Test
    public void should_throw_exception_401_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    } // should_throw_exception_401_delete_campaign()

    @Test
    public void should_throw_exception_403_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    } // should_throw_exception_403_delete_campaign()

    @Test
    public void should_throw_exception_404_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    } // should_throw_exception_404_delete_campaign()

    @Test
    public void should_throw_exception_405_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    } // should_throw_exception_405_delete_campaign()

    @Test
    public void should_throw_exception_406_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    } // should_throw_exception_406_delete_campaign()

    @Test
    public void should_throw_exception_410_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    } // should_throw_exception_410_delete_campaign()

    @Test
    public void should_throw_exception_418_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    } // should_throw_exception_418_delete_campaign()

    @Test
    public void should_throw_exception_429_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    } // should_throw_exception_429_delete_campaign()

    @Test
    public void should_throw_exception_500_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    } // should_throw_exception_500_delete_campaign()

    @Test
    public void should_throw_exception_503_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    } // should_throw_exception_503_delete_campaign()

    @Test
    public void should_throw_exception_1337_delete_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpDelete.class))).thenReturn(httpResponse);
	assertThatThrownBy(() -> sCampaigns.deleteCampaign(oAuthAccessToken, id))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    } // should_throw_exception_1337_delete_campaign()

    private Campaign initCampaignForCreation() {
	Campaign c = new Campaign();
	c.setName("Cool Campaign");
	c.setAdAccountId("3b0fbace-04b4-4f04-a425-33b5e0af1d0d");
	c.setStatus(StatusEnum.PAUSED);
	c.setStartTime(new Date());
	return c;
    } // initCampaignForCreation()

    private Campaign initCampaignForUpdate() {
	Campaign c = new Campaign();
	c.setId("92e1c28a-a331-45b4-8c26-fd3e0eea8c39");
	c.setName("Cool Campaign");
	c.setAdAccountId("8adc3db7-8148-4fbf-999c-8d2266369d74");
	c.setStatus(StatusEnum.PAUSED);
	c.setStartTime(new Date());
	c.setEndTime(new Date());
	return c;
    } // initCampaignForUpdate()
} // SnapCampaignsTest
