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
package snapads4j.audit.logs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import snapads4j.enums.TypeAuditLogEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.audit.logs.AuditLog;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

public class SnapAuditLogsTest {
    
    @Spy
    private SnapAuditLogs snapAuditLogs;

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
    
    private final String campaignId = "6cf25572-048b-4447-95d1-eb48231751be";
    
    private final String adSquadId = "1cf25572-048b-4447-95d1-eb48231751be";
    
    private final String adId = "2cf25572-048b-4447-95d1-eb48231751be";
    
    private final String creativeId = "3cf25572-048b-4447-95d1-eb48231751be";
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    
    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	snapAuditLogs.setHttpClient(httpClient);
	snapAuditLogs.setEntityUtilsWrapper(entityUtilsWrapper);
	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    } // setUp()

    @Test
    public void test_fetch_change_logs_for_campaign_should_success() throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException, SnapExecutionException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getChangeLogsForCampaign());
	List<AuditLog> logs = snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId);
	assertThat(logs).isNotNull();
	assertThat(logs).isNotEmpty();
	assertThat(logs).hasSize(2);
	
	assertThat(logs.get(0).getId()).isEqualTo("f9fd14cf-1bb0-4592-beeb-9d7e358e746b");
	assertThat(logs.get(0).getAction()).isEqualTo("UPDATED");
	assertThat(logs.get(0).getAppId()).isEqualTo("87947032-5fbd-46a7-ba60-073ca8efefbb");
	assertThat(logs.get(0).getAppName()).isEqualTo("Honey badger App");
	assertThat(logs.get(0).getEmail()).isEqualTo("honey.badger@hooli.com");
	assertThat(logs.get(0).getEntityId()).isEqualTo("dcc3f407-7049-47aa-8300-6cce946ed04e");
	assertThat(logs.get(0).getEntityType()).isEqualTo(TypeAuditLogEnum.CAMPAIGN);
	assertThat(logs.get(0).getName()).isEqualTo("Badger Campaign - July 2019");
	assertThat(logs.get(0).getUserId()).isEqualTo("a71cfcae-895d-4314-9460-e2ffd2515dd0");
	assertThat(sdf.format(logs.get(0).getCreatedAt())).isEqualTo("2019-07-17T16:17:30.194Z");
	assertThat(sdf.format(logs.get(0).getUpdatedAt())).isEqualTo("2019-07-17T16:17:30.194Z");
	assertThat(sdf.format(logs.get(0).getEventAt())).isEqualTo("2019-07-17T16:17:29.991Z");
	assertThat(logs.get(0).getUpdateValueRecords()).hasSize(1);
	assertThat(logs.get(0).getUpdateValueRecords().containsKey("status")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("status").containsKey("before_value")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("status").containsKey("after_value")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("status").get("before_value")).isEqualTo("\"ACTIVE\"");
	assertThat(logs.get(0).getUpdateValueRecords().get("status").get("after_value")).isEqualTo("\"PAUSED\"");
    
	assertThat(logs.get(1).getId()).isEqualTo("3cd457c6-077d-4d4b-9647-73a97045e709");
	assertThat(logs.get(1).getAction()).isEqualTo("UPDATED");
	assertThat(logs.get(1).getAppId()).isEqualTo("87947032-5fbd-46a7-ba60-073ca8efefbb");
	assertThat(logs.get(1).getAppName()).isEqualTo("Honey badger App");
	assertThat(logs.get(1).getEmail()).isEqualTo("honey.badger@hooli.com");
	assertThat(logs.get(1).getEntityId()).isEqualTo("dcc3f407-7049-47aa-8300-6cce946ed04e");
	assertThat(logs.get(1).getEntityType()).isEqualTo(TypeAuditLogEnum.CAMPAIGN);
	assertThat(logs.get(1).getName()).isEqualTo("Badger Campaign - July 2019");
	assertThat(logs.get(1).getUserId()).isEqualTo("a71cfcae-895d-4314-9460-e2ffd2515dd0");
	assertThat(sdf.format(logs.get(1).getCreatedAt())).isEqualTo("2019-07-17T15:54:59.955Z");
	assertThat(sdf.format(logs.get(1).getUpdatedAt())).isEqualTo("2019-07-17T15:54:59.955Z");
	assertThat(sdf.format(logs.get(1).getEventAt())).isEqualTo("2019-07-17T15:54:59.755Z");
	assertThat(logs.get(1).getUpdateValueRecords()).hasSize(1);
	assertThat(logs.get(1).getUpdateValueRecords().containsKey("end_time")).isTrue();
	assertThat(logs.get(1).getUpdateValueRecords().get("end_time").containsKey("after_value")).isTrue();
	assertThat(logs.get(1).getUpdateValueRecords().get("end_time").get("after_value")).isEqualTo("1563580491000");
    }// test_fetch_change_logs_for_campaign_should_success()
    
    @Test
    public void test_fetch_change_logs_for_campaign_should_throw_SnapOAuthAccessTokenException_when_token_is_null () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(null, campaignId))
		.hasMessage("The OAuthAccessToken must to be given")
		.isInstanceOf(SnapOAuthAccessTokenException.class);
    }// test_fetch_change_logs_for_campaign_should_throw_SnapOAuthAccessTokenException_when_token_is_null()
    
    @Test
    public void test_fetch_change_logs_for_campaign_should_throw_SnapOAuthAccessTokenException_when_token_is_empty () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign("", campaignId))
		.hasMessage("The OAuthAccessToken must to be given")
		.isInstanceOf(SnapOAuthAccessTokenException.class);
    }// test_fetch_change_logs_for_campaign_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()
    
    @Test
    public void test_fetch_change_logs_for_campaign_should_throw_SnapArgumentException_when_campaign_id_is_null () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, null))
	.hasMessage("Campaign ID is required")
	.isInstanceOf(SnapArgumentException.class);
    }// test_fetch_change_logs_for_campaign_should_throw_SnapArgumentException_when_campaign_id_is_null()
    
    @Test
    public void test_fetch_change_logs_for_campaign_should_throw_SnapArgumentException_when_campaign_id_is_empty () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, ""))
	.hasMessage("Campaign ID is required")
	.isInstanceOf(SnapArgumentException.class);
    }// test_fetch_change_logs_for_campaign_should_throw_SnapArgumentException_when_campaign_id_is_empty()
    
    @Test
    public void test_fetch_change_logs_for_campaign_should_throw_SnapExecutionException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
	.isInstanceOf(SnapExecutionException.class);
    }// test_fetch_change_logs_for_campaign_should_throw_SnapExecutionException()
    
    @Test
public void should_throw_exception_400_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
} // should_throw_exception_401_fetch_change_logs_for_campaign()
	
	@Test
public void should_throw_exception_401_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
} // should_throw_exception_401_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_403_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
} // should_throw_exception_403_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_404_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
} // should_throw_exception_404_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_405_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
} // should_throw_exception_405_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_406_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
} // should_throw_exception_406_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_410_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
} // should_throw_exception_410_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_418_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
} // should_throw_exception_418_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_429_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
} // should_throw_exception_429_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_500_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
} // should_throw_exception_500_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_503_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
} // should_throw_exception_503_fetch_change_logs_for_campaign()

@Test
public void should_throw_exception_1337_fetch_change_logs_for_campaign() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCampaign(oAuthAccessToken, campaignId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
} // should_throw_exception_1337_fetch_change_logs_for_campaign()
    
    @Test
    public void test_fetch_change_logs_for_ad_squad_should_success() throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException, SnapExecutionException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getChangeLogsForAdSquad());
	List<AuditLog> logs = snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId);
	assertThat(logs).isNotNull();
	assertThat(logs).isNotEmpty();
	assertThat(logs).hasSize(1);
	
	assertThat(logs.get(0).getId()).isEqualTo("ae2161fb-737a-4f3c-9a3d-e6c83d9c9e30");
	assertThat(logs.get(0).getAction()).isEqualTo("UPDATED");
	assertThat(logs.get(0).getAppId()).isEqualTo("e9bdc78d-81fa-4470-8f6b-2a3d6f0487b3");
	assertThat(logs.get(0).getAppName()).isEqualTo("Honey badger App");
	assertThat(logs.get(0).getEmail()).isEqualTo("honey.badger@hooli.com");
	assertThat(logs.get(0).getEntityId()).isEqualTo("c478150d-b177-4ecb-938d-f5157375f937");
	assertThat(logs.get(0).getEntityType()).isEqualTo(TypeAuditLogEnum.ADSQUAD);
	assertThat(logs.get(0).getName()).isEqualTo("Badger Ad Squad - July 2019");
	assertThat(logs.get(0).getUserId()).isEqualTo("a71cfcae-895d-4314-9460-e2ffd2515dd0");
	assertThat(sdf.format(logs.get(0).getCreatedAt())).isEqualTo("2019-07-17T15:58:40.150Z");
	assertThat(sdf.format(logs.get(0).getUpdatedAt())).isEqualTo("2019-07-17T15:58:40.150Z");
	assertThat(sdf.format(logs.get(0).getEventAt())).isEqualTo("2019-07-17T15:58:39.872Z");
	assertThat(logs.get(0).getUpdateValueRecords()).hasSize(1);
	assertThat(logs.get(0).getUpdateValueRecords().containsKey("bid_micro")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("bid_micro").containsKey("before_value")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("bid_micro").containsKey("after_value")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("bid_micro").get("before_value")).isEqualTo("868402");
	assertThat(logs.get(0).getUpdateValueRecords().get("bid_micro").get("after_value")).isEqualTo("876174");
    }// test_fetch_change_logs_for_ad_squad_should_success()
    
    @Test
    public void test_fetch_change_logs_for_ad_squad_should_throw_SnapOAuthAccessTokenException_when_token_is_null () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(null, adSquadId))
		.hasMessage("The OAuthAccessToken must to be given")
		.isInstanceOf(SnapOAuthAccessTokenException.class);
    }// test_fetch_change_logs_for_ad_squad_should_throw_SnapOAuthAccessTokenException_when_token_is_null()
    
    @Test
    public void test_fetch_change_logs_for_ad_squad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad("", adSquadId))
		.hasMessage("The OAuthAccessToken must to be given")
		.isInstanceOf(SnapOAuthAccessTokenException.class);
    }// test_fetch_change_logs_for_ad_squad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()
    
    @Test
    public void test_fetch_change_logs_for_ad_squad_should_throw_SnapArgumentException_when_ad_squad_id_is_null () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, null))
	.hasMessage("AdSquad ID is required")
	.isInstanceOf(SnapArgumentException.class);
    }// test_fetch_change_logs_for_ad_squad_should_throw_SnapArgumentException_when_ad_squad_id_is_null()
    
    @Test
    public void test_fetch_change_logs_for_ad_squad_should_throw_SnapArgumentException_when_ad_squad_id_is_empty () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, ""))
	.hasMessage("AdSquad ID is required")
	.isInstanceOf(SnapArgumentException.class);
    }// test_fetch_change_logs_for_ad_squad_should_throw_SnapArgumentException_when_ad_squad_id_is_empty()
    
    @Test
    public void test_fetch_change_logs_for_ad_squad_should_throw_SnapExecutionException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
	.isInstanceOf(SnapExecutionException.class);
    }// test_fetch_change_logs_for_ad_squad_should_throw_SnapExecutionException()
    
    @Test
public void should_throw_exception_400_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
} // should_throw_exception_401_fetch_change_logs_for_ad_squad()
	
	@Test
public void should_throw_exception_401_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
} // should_throw_exception_401_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_403_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
} // should_throw_exception_403_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_404_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
} // should_throw_exception_404_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_405_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
} // should_throw_exception_405_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_406_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
} // should_throw_exception_406_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_410_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
} // should_throw_exception_410_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_418_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
} // should_throw_exception_418_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_429_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
} // should_throw_exception_429_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_500_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
} // should_throw_exception_500_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_503_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
} // should_throw_exception_503_fetch_change_logs_for_ad_squad()

@Test
public void should_throw_exception_1337_fetch_change_logs_for_ad_squad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAdSquad(oAuthAccessToken, adSquadId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
} // should_throw_exception_1337_fetch_change_logs_for_ad_squad()
    
    @Test
    public void test_fetch_change_logs_for_ad_should_success() throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException, SnapExecutionException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getChangeLogsForAd());
	List<AuditLog> logs = snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId);
	assertThat(logs).isNotNull();
	assertThat(logs).isNotEmpty();
	assertThat(logs).hasSize(1);
	
	assertThat(logs.get(0).getId()).isEqualTo("36f4b88b-1c24-41f5-af86-24983795edd7");
	assertThat(logs.get(0).getAction()).isEqualTo("UPDATED");
	assertThat(logs.get(0).getAppId()).isEqualTo("87947032-5fbd-46a7-ba60-073ca8efefbb");
	assertThat(logs.get(0).getAppName()).isEqualTo("Honey badger App");
	assertThat(logs.get(0).getEmail()).isEqualTo("honey.badger@hooli.com");
	assertThat(logs.get(0).getEntityId()).isEqualTo("cd88b368-35a3-46f4-b37f-2f1a72db2692");
	assertThat(logs.get(0).getEntityType()).isEqualTo(TypeAuditLogEnum.AD);
	assertThat(logs.get(0).getName()).isEqualTo("Badger Holiday Ad 2019");
	assertThat(logs.get(0).getUserId()).isEqualTo("a71cfcae-895d-4314-9460-e2ffd2515dd0");
	assertThat(sdf.format(logs.get(0).getCreatedAt())).isEqualTo("2019-07-25T17:08:51.279Z");
	assertThat(sdf.format(logs.get(0).getUpdatedAt())).isEqualTo("2019-07-25T17:08:51.279Z");
	assertThat(sdf.format(logs.get(0).getEventAt())).isEqualTo("2019-07-25T17:08:51.030Z");
	assertThat(logs.get(0).getUpdateValueRecords()).hasSize(1);
	assertThat(logs.get(0).getUpdateValueRecords().containsKey("status")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("status").containsKey("before_value")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("status").containsKey("after_value")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("status").get("before_value")).isEqualTo("\"ACTIVE\"");
	assertThat(logs.get(0).getUpdateValueRecords().get("status").get("after_value")).isEqualTo("\"PAUSED\"");
    }// test_fetch_change_logs_for_ad_should_success()
    
    @Test
    public void test_fetch_change_logs_for_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_null () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(null, adId))
		.hasMessage("The OAuthAccessToken must to be given")
		.isInstanceOf(SnapOAuthAccessTokenException.class);
    }// test_fetch_change_logs_for_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_null()
    
    @Test
    public void test_fetch_change_logs_for_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd("", adId))
		.hasMessage("The OAuthAccessToken must to be given")
		.isInstanceOf(SnapOAuthAccessTokenException.class);
    }// test_fetch_change_logs_for_ad_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()
    
    @Test
    public void test_fetch_change_logs_for_ad_should_throw_SnapArgumentException_when_ad_id_is_null () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, null))
	.hasMessage("Ad ID is required")
	.isInstanceOf(SnapArgumentException.class);
    }// test_fetch_change_logs_for_ad_should_throw_SnapArgumentException_when_ad_id_is_null()
    
    @Test
    public void test_fetch_change_logs_for_ad_should_throw_SnapArgumentException_when_ad_id_is_empty () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, ""))
	.hasMessage("Ad ID is required")
	.isInstanceOf(SnapArgumentException.class);
    }// test_fetch_change_logs_for_ad_should_throw_SnapArgumentException_when_ad_id_is_empty()
    
    @Test
    public void test_fetch_change_logs_for_ad_should_throw_SnapExecutionException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
	.isInstanceOf(SnapExecutionException.class);
    }// test_fetch_change_logs_for_ad_should_throw_SnapExecutionException()
    
    @Test
public void should_throw_exception_400_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
} // should_throw_exception_401_fetch_change_logs_for_ad()
	
	@Test
public void should_throw_exception_401_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
} // should_throw_exception_401_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_403_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
} // should_throw_exception_403_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_404_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
} // should_throw_exception_404_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_405_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
} // should_throw_exception_405_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_406_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
} // should_throw_exception_406_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_410_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
} // should_throw_exception_410_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_418_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
} // should_throw_exception_418_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_429_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
} // should_throw_exception_429_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_500_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
} // should_throw_exception_500_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_503_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
} // should_throw_exception_503_fetch_change_logs_for_ad()

@Test
public void should_throw_exception_1337_fetch_change_logs_for_ad() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForAd(oAuthAccessToken, adId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
} // should_throw_exception_1337_fetch_change_logs_for_ad()
    
    @Test
    public void test_fetch_change_logs_for_creative_should_success() throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, IOException, InterruptedException, SnapExecutionException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(200);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	Mockito.when(entityUtilsWrapper.toString(httpEntity))
		.thenReturn(SnapResponseUtils.getChangeLogsForCreative());
	List<AuditLog> logs = snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId);
	assertThat(logs).isNotNull();
	assertThat(logs).isNotEmpty();
	assertThat(logs).hasSize(2);
	
	assertThat(logs.get(0).getId()).isEqualTo("a94cb9ed-0171-402a-aee7-ffecae0ce7bc");
	assertThat(logs.get(0).getAction()).isEqualTo("UPDATED");
	assertThat(logs.get(0).getAppId()).isEqualTo("e9bdc78d-81fa-4470-8f6b-2a3d6f0487b3");
	assertThat(logs.get(0).getAppName()).isEqualTo("Honey Badger App");
	assertThat(logs.get(0).getEmail()).isEqualTo("honey.badger@hooli.com");
	assertThat(logs.get(0).getEntityId()).isEqualTo("6475383b-cb70-4353-93b2-b227054169ae");
	assertThat(logs.get(0).getEntityType()).isEqualTo(TypeAuditLogEnum.CREATIVE);
	assertThat(logs.get(0).getName()).isEqualTo("Badger Rush");
	assertThat(logs.get(0).getUserId()).isEqualTo("a71cfcae-895d-4314-9460-e2ffd2515dd0");
	assertThat(sdf.format(logs.get(0).getCreatedAt())).isEqualTo("2019-07-17T16:27:00.717Z");
	assertThat(sdf.format(logs.get(0).getUpdatedAt())).isEqualTo("2019-07-17T16:27:00.717Z");
	assertThat(sdf.format(logs.get(0).getEventAt())).isEqualTo("2019-07-17T16:27:00.566Z");
	assertThat(logs.get(0).getUpdateValueRecords()).hasSize(1);
	assertThat(logs.get(0).getUpdateValueRecords().containsKey("call_to_action")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("call_to_action").containsKey("before_value")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("call_to_action").containsKey("after_value")).isTrue();
	assertThat(logs.get(0).getUpdateValueRecords().get("call_to_action").get("before_value")).isEqualTo("\"MORE\"");
	assertThat(logs.get(0).getUpdateValueRecords().get("call_to_action").get("after_value")).isEqualTo("\"SIGN UP\"");
    
	assertThat(logs.get(1).getId()).isEqualTo("72b18e3a-598a-4e29-a288-980613de3714");
	assertThat(logs.get(1).getAction()).isEqualTo("CREATED");
	assertThat(logs.get(1).getAppId()).isEqualTo("e9bdc78d-81fa-4470-8f6b-2a3d6f0487b3");
	assertThat(logs.get(1).getAppName()).isEqualTo("Honey Badger App");
	assertThat(logs.get(1).getEmail()).isEqualTo("honey.badger@hooli.com");
	assertThat(logs.get(1).getEntityId()).isEqualTo("6475383b-cb70-4353-93b2-b227054169ae");
	assertThat(logs.get(1).getEntityType()).isEqualTo(TypeAuditLogEnum.CREATIVE);
	assertThat(logs.get(1).getName()).isEqualTo("Badger Rush");
	assertThat(logs.get(1).getUserId()).isEqualTo("a71cfcae-895d-4314-9460-e2ffd2515dd0");
	assertThat(sdf.format(logs.get(1).getCreatedAt())).isEqualTo("2019-07-17T16:07:00.985Z");
	assertThat(sdf.format(logs.get(1).getUpdatedAt())).isEqualTo("2019-07-17T16:07:00.985Z");
	assertThat(sdf.format(logs.get(1).getEventAt())).isEqualTo("2019-07-17T16:07:00.629Z");
	assertThat(logs.get(1).getUpdateValueRecords()).isNull();
    }// test_fetch_change_logs_for_creative_should_success()
    
    @Test
    public void test_fetch_change_logs_for_creative_should_throw_SnapOAuthAccessTokenException_when_token_is_null () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(null, creativeId))
		.hasMessage("The OAuthAccessToken must to be given")
		.isInstanceOf(SnapOAuthAccessTokenException.class);
    }// test_fetch_change_logs_for_creative_should_throw_SnapOAuthAccessTokenException_when_token_is_null()
    
    @Test
    public void test_fetch_change_logs_for_creative_should_throw_SnapOAuthAccessTokenException_when_token_is_empty () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative("", creativeId))
		.hasMessage("The OAuthAccessToken must to be given")
		.isInstanceOf(SnapOAuthAccessTokenException.class);
    }// test_fetch_change_logs_for_creative_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()
    
    @Test
    public void test_fetch_change_logs_for_creative_should_throw_SnapArgumentException_when_creative_id_is_null () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, null))
	.hasMessage("Creative ID is required")
	.isInstanceOf(SnapArgumentException.class);
    }// test_fetch_change_logs_for_creative_should_throw_SnapArgumentException_when_creative_id_is_null()
    
    @Test
    public void test_fetch_change_logs_for_creative_should_throw_SnapArgumentException_when_creative_id_is_empty () {
	Assertions.assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, ""))
	.hasMessage("Creative ID is required")
	.isInstanceOf(SnapArgumentException.class);
    }// test_fetch_change_logs_for_creative_should_throw_SnapArgumentException_when_creative_id_is_empty()
    
    @Test
    public void test_fetch_change_logs_for_creative_should_throw_SnapExecutionException() throws ClientProtocolException, IOException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
	.isInstanceOf(SnapExecutionException.class);
    }// test_fetch_change_logs_for_creative_should_throw_SnapExecutionException()
    
    @Test
public void should_throw_exception_400_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(400);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
} // should_throw_exception_401_fetch_change_logs_for_creative()
	
	@Test
public void should_throw_exception_401_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(401);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
} // should_throw_exception_401_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_403_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(403);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
} // should_throw_exception_403_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_404_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {

	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(404);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
} // should_throw_exception_404_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_405_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(405);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
} // should_throw_exception_405_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_406_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(406);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
} // should_throw_exception_406_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_410_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(410);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
} // should_throw_exception_410_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_418_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(418);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
} // should_throw_exception_418_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_429_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(429);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
} // should_throw_exception_429_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_500_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(500);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
} // should_throw_exception_500_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_503_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(503);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
} // should_throw_exception_503_fetch_change_logs_for_creative()

@Test
public void should_throw_exception_1337_fetch_change_logs_for_creative() throws IOException, InterruptedException,
	    SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
	Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
	Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
	Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
	assertThatThrownBy(() -> snapAuditLogs.fetchChangeLogsForCreative(oAuthAccessToken, creativeId))
		.isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
} // should_throw_exception_1337_fetch_change_logs_for_creative()
    
}// SnapAuditLogsTest
