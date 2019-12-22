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
package snapads4j.stats;

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
import snapads4j.enums.*;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.stats.Domain;
import snapads4j.model.stats.Stat;
import snapads4j.model.stats.TimeSerie;
import snapads4j.model.stats.TimeSerieStat;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.SnapResponseUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class SnapStatsTest {

    @Spy
    private SnapStats snapStats;

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

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    private final SimpleDateFormat xdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final String oAuthAccessToken = "meowmeowmeow";

    private final String campaignID = "69d120bd-b319-4201-9a2a-0e64b2ee5411";

    private final String adAccountID = "22335ba6-7558-4100-9663-baca7adff5f2";

    private final String adSquadID = "23995202-bfbc-45a0-9702-dd6841af52fe";

    private final String adID = "e8d6217f-32ab-400f-9e54-39a86a7963e4";

    private final String pixelID = "6c9d82ca-4a3a-4391-98ba-0317a8471296";

    private final String domainUrl = "abc.snapchat.com";

    private Date startTime;

    private Date endTime;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.snapStats.setHttpClient(httpClient);
        this.snapStats.setEntityUtilsWrapper(entityUtilsWrapper);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        startTime = cal.getTime();
        cal.add(Calendar.MONTH, +4);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        endTime = cal.getTime();
    }// setUp()

    @Test
    public void get_campaign_stats_should_success_total() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsCampaignTotal());
        Optional<TimeSerieStat> result = this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.TOTAL);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.CAMPAIGN);
            assertThat(stat.getGranularity()).isEqualTo(GranularityEnum.TOTAL);
            assertThat(stat.getId()).isEqualTo(campaignID);
            assertThat(stat.toString()).isNotEmpty();

            Stat stats = stat.getStats();
            assertThat(stats.toString()).isNotEmpty();
            assertThat(stats.getImpressions()).isEqualTo(0);
            assertThat(stats.getSwipes()).isEqualTo(0);
            assertThat(stats.getSpend()).isEqualTo(0);
            assertThat(stats.getQuartile1()).isEqualTo(0);
            assertThat(stats.getQuartile2()).isEqualTo(0);
            assertThat(stats.getQuartile3()).isEqualTo(0);
            assertThat(stats.getViewCompletion()).isEqualTo(0);
            assertThat(stats.getScreenTimeMillis()).isEqualTo(0);
        });
    }// get_campaign_stats_should_success_total()

    @Test
    public void get_campaign_stats_should_success_day() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsCampaignDay());
        List<String> fields = Stream.of(new String[]{"impressions", "swipes", "conversion_purchases", "conversion_save", "conversion_start_checkout", "conversion_add_cart", "conversion_view_content", "conversion_add_billing", "conversion_sign_ups", "conversion_searches", "conversion_level_completes", "conversion_app_opens", "conversion_page_views"}).collect(Collectors.toList());
        Optional<TimeSerieStat> result = this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY, fields, null, null, null, null, null, null, null, null, null);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.CAMPAIGN);
            assertThat(stat.getGranularity()).isEqualTo(GranularityEnum.DAY);
            assertThat(sdf.format(stat.getStartTime())).isEqualTo("2017-04-28T07:00:00.000Z");
            assertThat(sdf.format(stat.getEndTime())).isEqualTo("2017-04-30T07:00:00.000Z");
            assertThat(xdf.format(stat.getFinalizedDataEndTime())).isEqualTo("2017-05-01T00:00:00.000");

            List<TimeSerie> timeseries = stat.getTimeseries();
            assertThat(timeseries).isNotEmpty();
            assertThat(timeseries).hasSize(2);

            TimeSerie t1 = timeseries.get(0);
            Stat statsT1 = t1.getStats();
            assertThat(t1.toString()).isNotEmpty();
            assertThat(sdf.format(t1.getStartTime())).isEqualTo("2017-04-27T22:00:00.000Z");
            assertThat(sdf.format(t1.getEndTime())).isEqualTo("2017-04-28T22:00:00.000Z");
            assertThat(statsT1.getImpressions()).isEqualTo(7715);
            assertThat(statsT1.getSwipes()).isEqualTo(57);
            assertThat(statsT1.getConversionPurchases()).isEqualTo(200);
            assertThat(statsT1.getConversionSave()).isEqualTo(150);
            assertThat(statsT1.getConversionStartCheckout()).isEqualTo(300);
            assertThat(statsT1.getConversionAddCart()).isEqualTo(500);
            assertThat(statsT1.getConversionViewContent()).isEqualTo(785);
            assertThat(statsT1.getConversionAddBilling()).isEqualTo(666);
            assertThat(statsT1.getConversionSignUps()).isEqualTo(1000);
            assertThat(statsT1.getConversionSearches()).isEqualTo(1500);
            assertThat(statsT1.getConversionLevelCompletes()).isEqualTo(450);
            assertThat(statsT1.getConversionAppOpens()).isEqualTo(800);
            assertThat(statsT1.getConversionPageViews()).isEqualTo(1500);

            TimeSerie t2 = timeseries.get(1);
            Stat statsT2 = t2.getStats();
            assertThat(t2.toString()).isNotEmpty();
            assertThat(sdf.format(t2.getStartTime())).isEqualTo("2017-04-28T22:00:00.000Z");
            assertThat(sdf.format(t2.getEndTime())).isEqualTo("2017-04-29T22:00:00.000Z");
            assertThat(statsT2.getImpressions()).isEqualTo(7715);
            assertThat(statsT2.getSwipes()).isEqualTo(57);
            assertThat(statsT2.getConversionPurchases()).isEqualTo(200);
            assertThat(statsT2.getConversionSave()).isEqualTo(150);
            assertThat(statsT2.getConversionStartCheckout()).isEqualTo(300);
            assertThat(statsT2.getConversionAddCart()).isEqualTo(500);
            assertThat(statsT2.getConversionViewContent()).isEqualTo(785);
            assertThat(statsT2.getConversionAddBilling()).isEqualTo(666);
            assertThat(statsT2.getConversionSignUps()).isEqualTo(1000);
            assertThat(statsT2.getConversionSearches()).isEqualTo(1500);
            assertThat(statsT2.getConversionLevelCompletes()).isEqualTo(450);
            assertThat(statsT2.getConversionAppOpens()).isEqualTo(800);
            assertThat(statsT2.getConversionPageViews()).isEqualTo(1500);
        });
    }// get_campaign_stats_should_success_day()

    @Test
    public void get_campaign_stats_should_success_day_with_extra_params() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsCampaignDayOmittingRecords());
        List<String> fields = Stream.of(new String[]{"impressions", "swipes", "spend"}).collect(Collectors.toList());
        List<String> conversionsSourcesTypes = Stream.of(new String[]{"web","app","total"}).collect(Collectors.toList());
        Optional<TimeSerieStat> result = this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY, fields, BreakdownEnum.AD, false, DimensionEnum.DEMO, PivotEnum.GENDER, SwipeUpAttributionWindowEnum.TWENTY_EIGHT_DAY, ViewAttributionWindowEnum.SEVEN_DAY, true, true, conversionsSourcesTypes);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.CAMPAIGN);
            assertThat(stat.getGranularity()).isEqualTo(GranularityEnum.DAY);
            assertThat(stat.getSwipeUpAttributionWindow()).isEqualTo(SwipeUpAttributionWindowEnum.TWENTY_EIGHT_DAY);
            assertThat(stat.getViewAttributionWindow()).isEqualTo(ViewAttributionWindowEnum.SEVEN_DAY);
            assertThat(sdf.format(stat.getStartTime())).isEqualTo("2019-11-12T08:00:00.000Z");
            assertThat(sdf.format(stat.getEndTime())).isEqualTo("2019-11-16T08:00:00.000Z");
            assertThat(xdf.format(stat.getFinalizedDataEndTime())).contains("2019-11-28T");

            List<TimeSerie> timeseries = stat.getTimeseries();
            assertThat(timeseries).isNotEmpty();
            assertThat(timeseries).hasSize(4);

            TimeSerie t1 = timeseries.get(0);
            Stat statsT1 = t1.getStats();
            assertThat(t1.toString()).isNotEmpty();
            assertThat(sdf.format(t1.getStartTime())).isEqualTo("2019-11-12T08:00:00.000Z");
            assertThat(sdf.format(t1.getEndTime())).isEqualTo("2019-11-13T08:00:00.000Z");
            assertThat(statsT1.getImpressions()).isEqualTo(0);
            assertThat(statsT1.getSwipes()).isEqualTo(0);
            assertThat(statsT1.getSpend()).isEqualTo(0);
            assertThat(statsT1.getConversionPurchases()).isEqualTo(0);
            assertThat(statsT1.getConversionPurchasesApp()).isEqualTo(0);
            assertThat(statsT1.getConversionPurchasesWeb()).isEqualTo(0);

            TimeSerie t2 = timeseries.get(1);
            Stat statsT2 = t2.getStats();
            assertThat(t2.toString()).isNotEmpty();
            assertThat(sdf.format(t2.getStartTime())).isEqualTo("2019-11-13T08:00:00.000Z");
            assertThat(sdf.format(t2.getEndTime())).isEqualTo("2019-11-14T08:00:00.000Z");
            assertThat(statsT2.getImpressions()).isEqualTo(0);
            assertThat(statsT2.getSwipes()).isEqualTo(0);
            assertThat(statsT2.getSpend()).isEqualTo(0);
            assertThat(statsT2.getConversionPurchases()).isEqualTo(0);
            assertThat(statsT2.getConversionPurchasesApp()).isEqualTo(0);
            assertThat(statsT2.getConversionPurchasesWeb()).isEqualTo(0);

            TimeSerie t3 = timeseries.get(2);
            Stat statsT3 = t3.getStats();
            assertThat(t3.toString()).isNotEmpty();
            assertThat(sdf.format(t3.getStartTime())).isEqualTo("2019-11-14T08:00:00.000Z");
            assertThat(sdf.format(t3.getEndTime())).isEqualTo("2019-11-15T08:00:00.000Z");
            assertThat(statsT3.getImpressions()).isEqualTo(599725);
            assertThat(statsT3.getSwipes()).isEqualTo(3790);
            assertThat(statsT3.getSpend()).isEqualTo(594736665);
            assertThat(statsT3.getConversionPurchases()).isEqualTo(62);
            assertThat(statsT3.getConversionPurchasesApp()).isEqualTo(20);
            assertThat(statsT3.getConversionPurchasesWeb()).isEqualTo(42);

            TimeSerie t4 = timeseries.get(3);
            Stat statsT4 = t4.getStats();
            assertThat(t4.toString()).isNotEmpty();
            assertThat(sdf.format(t4.getStartTime())).isEqualTo("2019-11-15T08:00:00.000Z");
            assertThat(sdf.format(t4.getEndTime())).isEqualTo("2019-11-16T08:00:00.000Z");
            assertThat(statsT4.getImpressions()).isEqualTo(670692);
            assertThat(statsT4.getSwipes()).isEqualTo(4189);
            assertThat(statsT4.getSpend()).isEqualTo(716170632);
            assertThat(statsT4.getConversionPurchases()).isEqualTo(125);
            assertThat(statsT4.getConversionPurchasesApp()).isEqualTo(43);
            assertThat(statsT4.getConversionPurchasesWeb()).isEqualTo(82);
        });
    }// get_campaign_stats_should_success_day_with_extra_params()

    @Test
    public void get_campaign_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(null, campaignID, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_campaign_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void get_campaign_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats("", campaignID, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_campaign_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_campaign_id_is_null() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, null, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("Campaign ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_campaign_id_is_null()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_campaign_id_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, "", startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("Campaign ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_campaign_id_is_empty()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, null, endTime, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_null()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, null, endTime, GranularityEnum.DAY))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, null, endTime, GranularityEnum.HOUR))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, null, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_null()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, null, GranularityEnum.DAY))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, null, GranularityEnum.HOUR))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_campaign_stats_should_throw_SnapArgumentException_when_granularity_is_null() {
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, null))
                .hasMessage("Granularity is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_campaign_stats_should_throw_SnapArgumentException_when_granularity_is_null()

    @Test
    public void get_campaign_stats_should_throw_SnapExecutionException_when_IOException_is_occured() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapExecutionException.class);
    }// get_campaign_stats_should_throw_SnapExecutionException_when_IOException_is_occured()

    @Test
    public void should_throw_exception_400_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_campaigns_stats()

    @Test
    public void should_throw_exception_401_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_campaigns_stats()

    @Test
    public void should_throw_exception_403_get_campaigns_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_campaigns_stats()

    @Test
    public void should_throw_exception_404_get_campaigns_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_campaigns_stats()

    @Test
    public void should_throw_exception_405_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_campaigns_stats()

    @Test
    public void should_throw_exception_406_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_campaigns_stats()

    @Test
    public void should_throw_exception_410_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_campaigns_stats()

    @Test
    public void should_throw_exception_418_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_campaigns_stats()

    @Test
    public void should_throw_exception_429_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_campaigns_stats()

    @Test
    public void should_throw_exception_500_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_campaigns_stats()

    @Test
    public void should_throw_exception_503_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_campaigns_stats()

    @Test
    public void should_throw_exception_1337_get_campaigns_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_campaigns_stats()

    @Test
    public void get_ad_account_stats_should_success_day() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsAdAccountStats());
        Optional<TimeSerieStat> result = this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.TOTAL);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.AD_ACCOUNT);
            assertThat(stat.getGranularity()).isEqualTo(GranularityEnum.TOTAL);
            assertThat(stat.getStats()).isNotNull();
            assertThat(stat.getStats().getSpend()).isEqualTo(89196290);
            assertThat(sdf.format(stat.getFinalizedDataEndTime())).isEqualTo("2019-08-29T10:00:00.000Z");
        });
    }// get_ad_account_stats_should_success_day()

    @Test
    public void get_ad_account_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(null, adAccountID, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_ad_account_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void get_ad_account_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats("", adAccountID, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_ad_account_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_ad_account_id_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, null, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("AdAccount ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_ad_account_id_is_null()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_ad_account_id_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, "", startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("AdAccount ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_ad_account_id_is_empty()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, null, endTime, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_null()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, null, endTime, GranularityEnum.DAY))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, null, endTime, GranularityEnum.HOUR))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, null, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_null()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, null, GranularityEnum.DAY))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, null, GranularityEnum.HOUR))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_ad_account_stats_should_throw_SnapArgumentException_when_granularity_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, null))
                .hasMessage("Granularity is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_account_stats_should_throw_SnapArgumentException_when_granularity_is_null()

    @Test
    public void get_ad_account_stats_should_throw_SnapExecutionException_when_IOException_is_occured() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapExecutionException.class);
    }// get_ad_account_stats_should_throw_SnapExecutionException_when_IOException_is_occured()

    @Test
    public void should_throw_exception_400_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_ad_account_stats()

    @Test
    public void should_throw_exception_401_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_ad_account_stats()

    @Test
    public void should_throw_exception_403_get_ad_account_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_ad_account_stats()

    @Test
    public void should_throw_exception_404_get_ad_account_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_ad_account_stats()

    @Test
    public void should_throw_exception_405_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_ad_account_stats()

    @Test
    public void should_throw_exception_406_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_ad_account_stats()

    @Test
    public void should_throw_exception_410_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_ad_account_stats()

    @Test
    public void should_throw_exception_418_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_ad_account_stats()

    @Test
    public void should_throw_exception_429_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_ad_account_stats()

    @Test
    public void should_throw_exception_500_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_ad_account_stats()

    @Test
    public void should_throw_exception_503_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_ad_account_stats()

    @Test
    public void should_throw_exception_1337_get_ad_account_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_ad_account_stats()

    @Test
    public void get_ad_squad_stats_should_success_day() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsAdSquadStats());
        Optional<TimeSerieStat> result = this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.TOTAL);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.AD_SQUAD);
            assertThat(stat.getGranularity()).isEqualTo(GranularityEnum.TOTAL);
            assertThat(stat.getStats()).isNotNull();
            Stat stats = stat.getStats();
            assertThat(stats.getImpressions()).isEqualTo(0);
            assertThat(stats.getSwipes()).isEqualTo(0);
            assertThat(stats.getSpend()).isEqualTo(0);
            assertThat(stats.getQuartile1()).isEqualTo(0);
            assertThat(stats.getQuartile2()).isEqualTo(0);
            assertThat(stats.getQuartile3()).isEqualTo(0);
            assertThat(stats.getViewCompletion()).isEqualTo(0);
            assertThat(stats.getScreenTimeMillis()).isEqualTo(0);
        });
    }// get_ad_squad_stats_should_success_day()

    @Test
    public void get_ad_squad_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(null, adSquadID, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_ad_squad_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void get_ad_squad_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats("", adSquadID, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_ad_squad_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_ad_squad_id_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, null, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("AdSquad ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_ad_squad_id_is_null()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_ad_squad_id_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, "", startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("AdSquad ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_ad_squad_id_is_empty()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, null, endTime, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_null()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, null, endTime, GranularityEnum.DAY))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, null, endTime, GranularityEnum.HOUR))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, null, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_null()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, null, GranularityEnum.DAY))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, null, GranularityEnum.HOUR))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_ad_squad_stats_should_throw_SnapArgumentException_when_granularity_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, null))
                .hasMessage("Granularity is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_squad_stats_should_throw_SnapArgumentException_when_granularity_is_null()

    @Test
    public void get_ad_squad_stats_should_throw_SnapExecutionException_when_IOException_is_occured() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapExecutionException.class);
    }// get_ad_squad_stats_should_throw_SnapExecutionException_when_IOException_is_occured()

    @Test
    public void should_throw_exception_400_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_ad_squad_stats()

    @Test
    public void should_throw_exception_401_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_ad_squad_stats()

    @Test
    public void should_throw_exception_403_get_ad_squad_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_ad_squad_stats()

    @Test
    public void should_throw_exception_404_get_ad_squad_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_ad_squad_stats()

    @Test
    public void should_throw_exception_405_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_ad_squad_stats()

    @Test
    public void should_throw_exception_406_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_ad_squad_stats()

    @Test
    public void should_throw_exception_410_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_ad_squad_stats()

    @Test
    public void should_throw_exception_418_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_ad_squad_stats()

    @Test
    public void should_throw_exception_429_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_ad_squad_stats()

    @Test
    public void should_throw_exception_500_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_ad_squad_stats()

    @Test
    public void should_throw_exception_503_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_ad_squad_stats()

    @Test
    public void should_throw_exception_1337_get_ad_squad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_ad_squad_stats()

    @Test
    public void get_ad_stats_should_success_day() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsAdStats());
        Optional<TimeSerieStat> result = this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.TOTAL);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.AD);
            assertThat(stat.getGranularity()).isEqualTo(GranularityEnum.TOTAL);
            assertThat(stat.getStats()).isNotNull();
            Stat stats = stat.getStats();
            assertThat(stats.getImpressions()).isEqualTo(0);
            assertThat(stats.getSwipes()).isEqualTo(0);
            assertThat(stats.getSpend()).isEqualTo(0);
            assertThat(stats.getQuartile1()).isEqualTo(0);
            assertThat(stats.getQuartile2()).isEqualTo(0);
            assertThat(stats.getQuartile3()).isEqualTo(0);
            assertThat(stats.getViewCompletion()).isEqualTo(0);
            assertThat(stats.getScreenTimeMillis()).isEqualTo(0);
        });
    }// get_ad_stats_should_success_day()

    @Test
    public void get_ad_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(null, adID, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_ad_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void get_ad_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getAdStats("", adID, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_ad_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_ad_id_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, null, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("Ad ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_ad_id_is_null()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_ad_id_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, "", startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("Ad ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_ad_id_is_empty()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, null, endTime, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_null()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, null, endTime, GranularityEnum.DAY))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, null, endTime, GranularityEnum.HOUR))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, null, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_null()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, null, GranularityEnum.DAY))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, null, GranularityEnum.HOUR))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_ad_stats_should_throw_SnapArgumentException_when_granularity_is_null() {
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, null))
                .hasMessage("Granularity is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_ad_stats_should_throw_SnapArgumentException_when_granularity_is_null()

    @Test
    public void get_ad_stats_should_throw_SnapExecutionException_when_IOException_is_occured() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapExecutionException.class);
    }// get_ad_stats_should_throw_SnapExecutionException_when_IOException_is_occured()

    @Test
    public void should_throw_exception_400_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_ad_stats()

    @Test
    public void should_throw_exception_401_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_ad_stats()

    @Test
    public void should_throw_exception_403_get_ad_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_ad_stats()

    @Test
    public void should_throw_exception_404_get_ad_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_ad_stats()

    @Test
    public void should_throw_exception_405_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_ad_stats()

    @Test
    public void should_throw_exception_406_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_ad_stats()

    @Test
    public void should_throw_exception_410_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_ad_stats()

    @Test
    public void should_throw_exception_418_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_ad_stats()

    @Test
    public void should_throw_exception_429_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_ad_stats()

    @Test
    public void should_throw_exception_500_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.HOUR))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_ad_stats()

    @Test
    public void should_throw_exception_503_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_ad_stats()

    @Test
    public void should_throw_exception_1337_get_ad_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getAdStats(oAuthAccessToken, adID, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_ad_stats()

    @Test
    public void get_pixel_domains_stats_should_success_day() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsPixelDomains());
        Optional<TimeSerieStat> result = this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.PIXEL);
            assertThat(sdf.format(stat.getStartTime())).isEqualTo("2017-09-01T01:00:00.000Z");
            assertThat(sdf.format(stat.getEndTime())).isEqualTo("2017-09-08T01:00:00.000Z");
            assertThat(stat.getDomains()).isNotNull();
            List<Domain> domainStats = stat.getDomains();
            assertThat(domainStats).isNotEmpty();
            assertThat(domainStats).hasSize(4);
            assertThat(domainStats.get(0).getTotalEvents()).isEqualTo(30);
            assertThat(domainStats.get(0).getDomainName()).isEqualTo("abc.snapchat.com");
            assertThat(domainStats.get(1).getTotalEvents()).isEqualTo(8);
            assertThat(domainStats.get(1).getDomainName()).isEqualTo("xyz.snapchat.com");
            assertThat(domainStats.get(2).getTotalEvents()).isEqualTo(180886);
            assertThat(domainStats.get(2).getDomainName()).isEqualTo("snapchat.com");
            assertThat(domainStats.get(3).getTotalEvents()).isEqualTo(9682034);
            assertThat(domainStats.get(3).getDomainName()).isEqualTo("www.snapchat.com");
        });
    }// get_pixel_domains_stats_should_success_day()

    @Test
    public void get_pixel_domains_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(null, pixelID))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_pixel_domains_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void get_pixel_domains_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats("", pixelID))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// get_pixel_domains_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void get_pixel_domains_stats_should_throw_SnapArgumentException_when_pixel_id_is_null() {
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, null))
                .hasMessage("Pixel ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_pixel_domains_stats_should_throw_SnapArgumentException_when_pixel_id_is_null()

    @Test
    public void get_pixel_domains_stats_should_throw_SnapArgumentException_when_pixel_id_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, ""))
                .hasMessage("Pixel ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// get_pixel_domains_stats_should_throw_SnapArgumentException_when_pixel_id_is_empty()

    @Test
    public void get_pixel_domains_stats_should_throw_SnapExecutionException_when_IOException_is_occured() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapExecutionException.class);
    }// get_pixel_domains_stats_should_throw_SnapExecutionException_when_IOException_is_occured()

    @Test
    public void should_throw_exception_400_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_401_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_403_get_pixel_domains_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_404_get_pixel_domains_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_405_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_406_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_410_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_418_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_429_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_500_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_503_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_pixel_domains_stats()

    @Test
    public void should_throw_exception_1337_get_pixel_domains_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelDomainsStats(oAuthAccessToken, pixelID))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_pixel_domains_stats()

    @Test
    public void get_pixel_specific_domain_stats_should_success_day() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsSpecificPixelDomain());
        Optional<TimeSerieStat> result = this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getId()).isEqualTo(pixelID);
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.PIXEL);
            assertThat(stat.getGranularity()).isEqualTo(GranularityEnum.DAY);
            assertThat(stat.getDomain()).isEqualTo(domainUrl);
            assertThat(sdf.format(stat.getStartTime())).isEqualTo("2017-09-01T01:00:00.000Z");
            assertThat(sdf.format(stat.getEndTime())).isEqualTo("2017-09-08T01:00:00.000Z");

            List<TimeSerie> timeseries = stat.getTimeseries();
            assertThat(timeseries).isNotEmpty();
            assertThat(timeseries).hasSize(3);

            TimeSerie t1 = timeseries.get(0);
            assertThat(t1.toString()).isNotEmpty();
            assertThat(sdf.format(t1.getStartTime())).isEqualTo("2017-08-31T07:00:00.000Z");
            assertThat(sdf.format(t1.getEndTime())).isEqualTo("2017-09-01T07:00:00.000Z");
            assertThat(t1.getTotalEvents()).isEqualTo(253926);
            assertThat(t1.getEventTypeBreakdown()).isNotNull();
            assertThat(t1.getEventTypeBreakdown().getPageView()).isEqualTo(240366);
            assertThat(t1.getEventTypeBreakdown().getViewContent()).isEqualTo(13560);
            assertThat(t1.getOsTypeBreakdown()).isNotNull();
            assertThat(t1.getOsTypeBreakdown().getAndroid()).isEqualTo(36170);
            assertThat(t1.getOsTypeBreakdown().getLinux()).isEqualTo(2);
            assertThat(t1.getOsTypeBreakdown().getMacOsX()).isEqualTo(1);
            assertThat(t1.getOsTypeBreakdown().getIos()).isEqualTo(217753);
            assertThat(t1.getBrowserTypeBreakdown()).isNotNull();
            assertThat(t1.getBrowserTypeBreakdown().getFirefox()).isEqualTo(4);
            assertThat(t1.getBrowserTypeBreakdown().getBrowserTypeOther()).isEqualTo(154);
            assertThat(t1.getBrowserTypeBreakdown().getSafari()).isEqualTo(217747);
            assertThat(t1.getBrowserTypeBreakdown().getChrome()).isEqualTo(36021);

            TimeSerie t2 = timeseries.get(1);
            assertThat(sdf.format(t2.getStartTime())).isEqualTo("2017-09-01T07:00:00.000Z");
            assertThat(sdf.format(t2.getEndTime())).isEqualTo("2017-09-02T07:00:00.000Z");
            assertThat(t2.getTotalEvents()).isEqualTo(873039);
            assertThat(t2.getEventTypeBreakdown()).isNotNull();
            assertThat(t2.getEventTypeBreakdown().getPageView()).isEqualTo(836945);
            assertThat(t2.getEventTypeBreakdown().getViewContent()).isEqualTo(36094);
            assertThat(t2.getOsTypeBreakdown()).isNotNull();
            assertThat(t2.getOsTypeBreakdown().getAndroid()).isEqualTo(134867);
            assertThat(t2.getOsTypeBreakdown().getLinux()).isEqualTo(2);
            assertThat(t2.getOsTypeBreakdown().getMacOsX()).isEqualTo(17);
            assertThat(t2.getOsTypeBreakdown().getIos()).isEqualTo(738139);
            assertThat(t2.getOsTypeBreakdown().getWindows()).isEqualTo(14);
            assertThat(t2.getBrowserTypeBreakdown()).isNotNull();
            assertThat(t2.getBrowserTypeBreakdown().getFirefox()).isEqualTo(22);
            assertThat(t2.getBrowserTypeBreakdown().getBrowserTypeOther()).isEqualTo(709);
            assertThat(t2.getBrowserTypeBreakdown().getSafari()).isEqualTo(738135);
            assertThat(t2.getBrowserTypeBreakdown().getChrome()).isEqualTo(134163);
            assertThat(t2.getBrowserTypeBreakdown().getInternetExplorer()).isEqualTo(4);
            assertThat(t2.getBrowserTypeBreakdown().getOpera()).isEqualTo(5);
            assertThat(t2.getBrowserTypeBreakdown().getEdge()).isEqualTo(1);

            TimeSerie t3 = timeseries.get(2);
            assertThat(sdf.format(t3.getStartTime())).isEqualTo("2017-09-07T07:00:00.000Z");
            assertThat(sdf.format(t3.getEndTime())).isEqualTo("2017-09-08T07:00:00.000Z");
            assertThat(t3.getTotalEvents()).isEqualTo(1675610);
            assertThat(t3.getEventTypeBreakdown()).isNotNull();
            assertThat(t3.getEventTypeBreakdown().getPageView()).isEqualTo(1592779);
            assertThat(t3.getEventTypeBreakdown().getViewContent()).isEqualTo(82831);
            assertThat(t3.getOsTypeBreakdown()).isNotNull();
            assertThat(t3.getOsTypeBreakdown().getAndroid()).isEqualTo(311818);
            assertThat(t3.getOsTypeBreakdown().getLinux()).isEqualTo(1);
            assertThat(t3.getOsTypeBreakdown().getMacOsX()).isEqualTo(11);
            assertThat(t3.getOsTypeBreakdown().getIos()).isEqualTo(1363740);
            assertThat(t3.getOsTypeBreakdown().getWindows()).isEqualTo(40);
            assertThat(t3.getBrowserTypeBreakdown()).isNotNull();
            assertThat(t3.getBrowserTypeBreakdown().getFirefox()).isEqualTo(17);
            assertThat(t3.getBrowserTypeBreakdown().getBrowserTypeOther()).isEqualTo(570);
            assertThat(t3.getBrowserTypeBreakdown().getSafari()).isEqualTo(1363719);
            assertThat(t3.getBrowserTypeBreakdown().getChrome()).isEqualTo(311301);
            assertThat(t3.getBrowserTypeBreakdown().getOpera()).isEqualTo(3);
        });
    }// _get_pixel_specific_domain_stats_should_success_day()

    @Test
    public void get_pixel_specific_domain_stats_should_success_day_with_extra_params() throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, IOException, SnapResponseErrorException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(entityUtilsWrapper.toString(httpEntity))
                .thenReturn(SnapResponseUtils.getStatsSpecificPixelDomain());
        List<String> fields = Stream.of(new String[]{"event_type","os_type","browser_type"}).collect(Collectors.toList());
        Optional<TimeSerieStat> result = this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY, fields, BreakdownEnum.AD, false, DimensionEnum.DEMO, PivotEnum.GENDER, SwipeUpAttributionWindowEnum.TWENTY_EIGHT_DAY, ViewAttributionWindowEnum.SEVEN_DAY, true, true, null);
        assertThat(result).isPresent();
        result.ifPresent(stat -> {
            assertThat(stat.getId()).isEqualTo(pixelID);
            assertThat(stat.getType()).isEqualTo(TimeSerieTypeEnum.PIXEL);
            assertThat(stat.getGranularity()).isEqualTo(GranularityEnum.DAY);
            assertThat(stat.getDomain()).isEqualTo(domainUrl);
            assertThat(sdf.format(stat.getStartTime())).isEqualTo("2017-09-01T01:00:00.000Z");
            assertThat(sdf.format(stat.getEndTime())).isEqualTo("2017-09-08T01:00:00.000Z");

            List<TimeSerie> timeseries = stat.getTimeseries();
            assertThat(timeseries).isNotEmpty();
            assertThat(timeseries).hasSize(3);

            TimeSerie t1 = timeseries.get(0);
            assertThat(t1.toString()).isNotEmpty();
            assertThat(sdf.format(t1.getStartTime())).isEqualTo("2017-08-31T07:00:00.000Z");
            assertThat(sdf.format(t1.getEndTime())).isEqualTo("2017-09-01T07:00:00.000Z");
            assertThat(t1.getTotalEvents()).isEqualTo(253926);
            assertThat(t1.getEventTypeBreakdown()).isNotNull();
            assertThat(t1.getEventTypeBreakdown().getPageView()).isEqualTo(240366);
            assertThat(t1.getEventTypeBreakdown().getViewContent()).isEqualTo(13560);
            assertThat(t1.getOsTypeBreakdown()).isNotNull();
            assertThat(t1.getOsTypeBreakdown().getAndroid()).isEqualTo(36170);
            assertThat(t1.getOsTypeBreakdown().getLinux()).isEqualTo(2);
            assertThat(t1.getOsTypeBreakdown().getMacOsX()).isEqualTo(1);
            assertThat(t1.getOsTypeBreakdown().getIos()).isEqualTo(217753);
            assertThat(t1.getBrowserTypeBreakdown()).isNotNull();
            assertThat(t1.getBrowserTypeBreakdown().getFirefox()).isEqualTo(4);
            assertThat(t1.getBrowserTypeBreakdown().getBrowserTypeOther()).isEqualTo(154);
            assertThat(t1.getBrowserTypeBreakdown().getSafari()).isEqualTo(217747);
            assertThat(t1.getBrowserTypeBreakdown().getChrome()).isEqualTo(36021);

            TimeSerie t2 = timeseries.get(1);
            assertThat(sdf.format(t2.getStartTime())).isEqualTo("2017-09-01T07:00:00.000Z");
            assertThat(sdf.format(t2.getEndTime())).isEqualTo("2017-09-02T07:00:00.000Z");
            assertThat(t2.getTotalEvents()).isEqualTo(873039);
            assertThat(t2.getEventTypeBreakdown()).isNotNull();
            assertThat(t2.getEventTypeBreakdown().getPageView()).isEqualTo(836945);
            assertThat(t2.getEventTypeBreakdown().getViewContent()).isEqualTo(36094);
            assertThat(t2.getOsTypeBreakdown()).isNotNull();
            assertThat(t2.getOsTypeBreakdown().getAndroid()).isEqualTo(134867);
            assertThat(t2.getOsTypeBreakdown().getLinux()).isEqualTo(2);
            assertThat(t2.getOsTypeBreakdown().getMacOsX()).isEqualTo(17);
            assertThat(t2.getOsTypeBreakdown().getIos()).isEqualTo(738139);
            assertThat(t2.getOsTypeBreakdown().getWindows()).isEqualTo(14);
            assertThat(t2.getBrowserTypeBreakdown()).isNotNull();
            assertThat(t2.getBrowserTypeBreakdown().getFirefox()).isEqualTo(22);
            assertThat(t2.getBrowserTypeBreakdown().getBrowserTypeOther()).isEqualTo(709);
            assertThat(t2.getBrowserTypeBreakdown().getSafari()).isEqualTo(738135);
            assertThat(t2.getBrowserTypeBreakdown().getChrome()).isEqualTo(134163);
            assertThat(t2.getBrowserTypeBreakdown().getInternetExplorer()).isEqualTo(4);
            assertThat(t2.getBrowserTypeBreakdown().getOpera()).isEqualTo(5);
            assertThat(t2.getBrowserTypeBreakdown().getEdge()).isEqualTo(1);

            TimeSerie t3 = timeseries.get(2);
            assertThat(sdf.format(t3.getStartTime())).isEqualTo("2017-09-07T07:00:00.000Z");
            assertThat(sdf.format(t3.getEndTime())).isEqualTo("2017-09-08T07:00:00.000Z");
            assertThat(t3.getTotalEvents()).isEqualTo(1675610);
            assertThat(t3.getEventTypeBreakdown()).isNotNull();
            assertThat(t3.getEventTypeBreakdown().getPageView()).isEqualTo(1592779);
            assertThat(t3.getEventTypeBreakdown().getViewContent()).isEqualTo(82831);
            assertThat(t3.getOsTypeBreakdown()).isNotNull();
            assertThat(t3.getOsTypeBreakdown().getAndroid()).isEqualTo(311818);
            assertThat(t3.getOsTypeBreakdown().getLinux()).isEqualTo(1);
            assertThat(t3.getOsTypeBreakdown().getMacOsX()).isEqualTo(11);
            assertThat(t3.getOsTypeBreakdown().getIos()).isEqualTo(1363740);
            assertThat(t3.getOsTypeBreakdown().getWindows()).isEqualTo(40);
            assertThat(t3.getBrowserTypeBreakdown()).isNotNull();
            assertThat(t3.getBrowserTypeBreakdown().getFirefox()).isEqualTo(17);
            assertThat(t3.getBrowserTypeBreakdown().getBrowserTypeOther()).isEqualTo(570);
            assertThat(t3.getBrowserTypeBreakdown().getSafari()).isEqualTo(1363719);
            assertThat(t3.getBrowserTypeBreakdown().getChrome()).isEqualTo(311301);
            assertThat(t3.getBrowserTypeBreakdown().getOpera()).isEqualTo(3);
        });
    }// _get_pixel_specific_domain_stats_should_success_day_with_extra_params()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(null, pixelID, domainUrl,startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_null()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats("", pixelID, domainUrl, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("The OAuthAccessToken is required")
                .isInstanceOf(SnapOAuthAccessTokenException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapOAuthAccessTokenException_when_token_is_empty()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_campaign_id_is_null() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, null, domainUrl, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("Pixel ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_campaign_id_is_null()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_campaign_id_is_empty() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, "", domainUrl, startTime, endTime, GranularityEnum.TOTAL))
                .hasMessage("Pixel ID is required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_campaign_id_is_empty()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, null, endTime, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_null()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, null, endTime, GranularityEnum.DAY))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_day()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, null, endTime, GranularityEnum.HOUR))
                .hasMessage("StartTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_null_and_granularity_is_hour()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_null() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, null, GranularityEnum.TOTAL))
                .hasMessage("StartTime and EndTime are required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_null()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, null, GranularityEnum.DAY))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_day()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, null, GranularityEnum.HOUR))
                .hasMessage("EndTime is required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_null_and_granularity_is_hour()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        startTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_start_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_hour()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 15);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 10);
        endTime = cal.getTime();
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.HOUR))
                .hasMessage("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_end_time_is_not_top_hour_when_granularity_is_day()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_granularity_is_null() {
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, null))
                .hasMessage("Granularity is required")
                .isInstanceOf(SnapArgumentException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapArgumentException_when_granularity_is_null()

    @Test
    public void get_pixel_specific_domain_stats_should_throw_SnapExecutionException_when_IOException_is_occured() throws IOException {
        Mockito.when(httpClient.execute((Mockito.any(HttpGet.class)))).thenThrow(IOException.class);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapExecutionException.class);
    }// _get_pixel_specific_domain_stats_should_throw_SnapExecutionException_when_IOException_is_occured()

    @Test
    public void should_throw_exception_400_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Bad Request");
    }// should_throw_exception_400_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_401_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(401);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Unauthorized - Check your API key");
    }// should_throw_exception_401_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_403_get_pixel_specific_domain_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(403);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Access Forbidden");
    }// should_throw_exception_403_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_404_get_pixel_specific_domain_stats() throws IOException {

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Found");
    }// should_throw_exception_404_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_405_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(405);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Method Not Allowed");
    }// should_throw_exception_405_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_406_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(406);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Not Acceptable");
    }// should_throw_exception_406_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_410_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(410);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Gone");
    }// should_throw_exception_410_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_418_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(418);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("I'm a teapot");
    }// should_throw_exception_418_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_429_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(429);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Too Many Requests / Rate limit reached");
    }// should_throw_exception_429_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_500_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(500);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Internal Server Error");
    }// should_throw_exception_500_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_503_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(503);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Service Unavailable");
    }// should_throw_exception_503_get_pixel_specific_domain_stats()

    @Test
    public void should_throw_exception_1337_get_pixel_specific_domain_stats() throws IOException {
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(1337);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        assertThatThrownBy(() -> this.snapStats.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domainUrl, startTime, endTime, GranularityEnum.DAY))
                .isInstanceOf(SnapResponseErrorException.class).hasMessage("Error 1337");
    }// should_throw_exception_1337_get_pixel_specific_domain_stats()

}// SnapStatsTest
