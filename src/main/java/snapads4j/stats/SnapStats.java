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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.enums.*;
import snapads4j.exceptions.*;
import snapads4j.model.stats.SnapHttpResponseTimeseriesStat;
import snapads4j.model.stats.SnapHttpResponseTotalStat;
import snapads4j.model.stats.TimeSerieStat;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;
import snapads4j.utils.JsonUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * SnapStats
 *
 * @see {https://developers.snapchat.com/api/docs/#measurement}
 */
@Getter
@Setter
public class SnapStats implements SnapStatsInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointCampaignStats;

    private String endpointAdAccountStats;

    private String endpointAdSquadStats;

    private String endpointAdStats;

    private String endpointPixelDomains;

    private String endpointPixelSpecificDomain;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    private static final Logger LOGGER = LogManager.getLogger(SnapStats.class);

    public SnapStats() throws IOException{
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointCampaignStats = this.apiUrl + fp.getProperties().get("api.url.stats.campaign");
        this.endpointAdAccountStats = this.apiUrl + fp.getProperties().get("api.url.stats.adaccounts");
        this.endpointAdSquadStats = this.apiUrl + fp.getProperties().get("api.url.stats.adsquad");
        this.endpointAdStats = this.apiUrl + fp.getProperties().get("api.url.stats.ad");
        this.endpointPixelDomains = this.apiUrl + fp.getProperties().get("api.url.stats.pixel.domains");
        this.endpointPixelSpecificDomain = this.apiUrl + fp.getProperties().get("api.url.stats.pixel.specific.domain");
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapStats()

    @Override
    public Optional<TimeSerieStat> getCampaignStats(String oAuthAccessToken, String campaignID, Date startTime, Date endTime, GranularityEnum granularity) throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, SnapResponseErrorException {
        return getCampaignStats(oAuthAccessToken, campaignID, startTime, endTime, granularity, null, null, null, null, null, null, null, null, null, null);
    }// getCampaignStats()

    @Override
    public Optional<TimeSerieStat> getCampaignStats(String oAuthAccessToken, String campaignID, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, BreakdownEnum breakdown, Boolean test, DimensionEnum dimension,
                                                    PivotEnum pivot, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                                    ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                                    Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException, SnapResponseErrorException {
        checkParams(oAuthAccessToken, startTime, endTime, granularity);
        if (StringUtils.isEmpty(campaignID)) {
            throw new SnapArgumentException("Campaign ID is required");
        }
        Optional<TimeSerieStat> result = Optional.empty();
        String url = this.endpointCampaignStats.replace("{campaign_id}", campaignID);
        url = prepareFinalUrl(url, startTime, endTime, granularity, fields, breakdown, test, dimension, pivot, swipeUpAttributionWindow, viewAttributionWindow, positionStats, omitEmpty, conversionSourceTypes, null);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                if (granularity == GranularityEnum.TOTAL) {
                    SnapHttpResponseTotalStat responseFromJson = mapper.readValue(body, SnapHttpResponseTotalStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                } else {
                    SnapHttpResponseTimeseriesStat responseFromJson = mapper.readValue(body, SnapHttpResponseTimeseriesStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get campaign stats, campaignId = {}", campaignID, e);
            throw new SnapExecutionException("Impossible to get campaign stats", e);
        }
        return result;
    }// getCampaignStats()

    @Override
    public Optional<TimeSerieStat> getAdAccountStats(String oAuthAccessToken, String adAccountID, Date startTime, Date endTime, GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
        return this.getAdAccountStats(oAuthAccessToken, adAccountID, startTime, endTime, granularity,null, null, null, null, null, null, null, null, null);
    }// getAdAccountStats()

    @Override
    public Optional<TimeSerieStat> getAdAccountStats(String oAuthAccessToken, String adAccountID, Date startTime, Date endTime, GranularityEnum granularity, BreakdownEnum breakdown, Boolean test, DimensionEnum dimension,
                                                     PivotEnum pivot, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                                     ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                                     Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapExecutionException, SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException {
        checkParams(oAuthAccessToken, startTime, endTime, granularity);
        if (StringUtils.isEmpty(adAccountID)) {
            throw new SnapArgumentException("AdAccount ID is required");
        }
        Optional<TimeSerieStat> result = Optional.empty();
        String url = this.endpointAdAccountStats.replace("{ad_account_id}", adAccountID);
        url = prepareFinalUrl(url, startTime, endTime, granularity, null, breakdown, test, dimension, pivot, swipeUpAttributionWindow, viewAttributionWindow, positionStats, omitEmpty, conversionSourceTypes, null);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                if (granularity == GranularityEnum.TOTAL) {
                    SnapHttpResponseTotalStat responseFromJson = mapper.readValue(body, SnapHttpResponseTotalStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                } else {
                    SnapHttpResponseTimeseriesStat responseFromJson = mapper.readValue(body, SnapHttpResponseTimeseriesStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get ad account stats, adAccountID = {}", adAccountID, e);
            throw new SnapExecutionException("Impossible to get ad account stats", e);
        }
        return result;
    }// getAdAccountStats()

    @Override
    public Optional<TimeSerieStat> getAdSquadStats(String oAuthAccessToken, String adSquadID, Date startTime, Date endTime, GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
        return this.getAdSquadStats(oAuthAccessToken, adSquadID, startTime, endTime, granularity, null, null, null, null, null, null, null, null, null, null);
    }// getAdSquadStats()

    @Override
    public Optional<TimeSerieStat> getAdSquadStats(String oAuthAccessToken, String adSquadID, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, BreakdownEnum breakdown, Boolean test, DimensionEnum dimension, PivotEnum pivot, SwipeUpAttributionWindowEnum swipeUpAttributionWindow, ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats, Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapExecutionException, SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException {
        checkParams(oAuthAccessToken, startTime, endTime, granularity);
        if (StringUtils.isEmpty(adSquadID)) {
            throw new SnapArgumentException("AdSquad ID is required");
        }
        Optional<TimeSerieStat> result = Optional.empty();
        String url = this.endpointAdSquadStats.replace("{adsquad_id}", adSquadID);
        url = prepareFinalUrl(url, startTime, endTime, granularity, fields, breakdown, test, dimension, pivot, swipeUpAttributionWindow, viewAttributionWindow, positionStats, omitEmpty, conversionSourceTypes, null);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                if (granularity == GranularityEnum.TOTAL) {
                    SnapHttpResponseTotalStat responseFromJson = mapper.readValue(body, SnapHttpResponseTotalStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                } else {
                    SnapHttpResponseTimeseriesStat responseFromJson = mapper.readValue(body, SnapHttpResponseTimeseriesStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get ad squad stats, adSquadID = {}", adSquadID, e);
            throw new SnapExecutionException("Impossible to get ad squad stats", e);
        }
        return result;
    }// getAdSquadStats()

    @Override
    public Optional<TimeSerieStat> getAdStats(String oAuthAccessToken, String adID, Date startTime, Date endTime, GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
        return this.getAdStats(oAuthAccessToken, adID, startTime, endTime, granularity, null, null, null, null, null, null, null, null, null, null);
    }// getAdStats()

    @Override
    public Optional<TimeSerieStat> getAdStats(String oAuthAccessToken, String adID, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, BreakdownEnum breakdown, Boolean test, DimensionEnum dimension,
                                              PivotEnum pivot, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                              ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                              Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException {
        checkParams(oAuthAccessToken, startTime, endTime, granularity);
        if (StringUtils.isEmpty(adID)) {
            throw new SnapArgumentException("Ad ID is required");
        }
        Optional<TimeSerieStat> result = Optional.empty();
        String url = this.endpointAdStats.replace("{ad_id}", adID);
        url = prepareFinalUrl(url, startTime, endTime, granularity, fields, breakdown, test, dimension, pivot, swipeUpAttributionWindow, viewAttributionWindow, positionStats, omitEmpty, conversionSourceTypes, null);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                if (granularity == GranularityEnum.TOTAL) {
                    SnapHttpResponseTotalStat responseFromJson = mapper.readValue(body, SnapHttpResponseTotalStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                } else {
                    SnapHttpResponseTimeseriesStat responseFromJson = mapper.readValue(body, SnapHttpResponseTimeseriesStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get ad stats, adID = {}", adID, e);
            throw new SnapExecutionException("Impossible to get ad stats", e);
        }
        return result;
    }// getAdStats()

    @Override
    public Optional<TimeSerieStat> getPixelDomainsStats(String oAuthAccessToken, String pixelID) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(pixelID)) {
            throw new SnapArgumentException("Pixel ID is required");
        }
        Optional<TimeSerieStat> result = Optional.empty();
        String url = this.endpointPixelDomains.replace("{pixel_id}", pixelID);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseTimeseriesStat responseFromJson = mapper.readValue(body, SnapHttpResponseTimeseriesStat.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getStats();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get pixel domains stats stats, pixelID = {}", pixelID, e);
            throw new SnapExecutionException("Impossible to get pixel domains stats", e);
        }
        return result;
    }// getPixelDomainsStats()

    @Override
    public Optional<TimeSerieStat> getPixelSpecificDomainStats(String oAuthAccessToken, String pixelID, String domain, Date startTime, Date endTime, GranularityEnum granularity) throws SnapExecutionException, SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
        return this.getPixelSpecificDomainStats(oAuthAccessToken, pixelID, domain, startTime, endTime, granularity, null, null, null, null, null, null, null, null, null, null);
    }// getPixelSpecificDomainStats()

    @Override
    public Optional<TimeSerieStat> getPixelSpecificDomainStats(String oAuthAccessToken, String pixelID, String domain, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, BreakdownEnum breakdown, Boolean test, DimensionEnum dimension,
                                                               PivotEnum pivot, SwipeUpAttributionWindowEnum swipeUpAttributionWindow,
                                                               ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats,
                                                               Boolean omitEmpty, List<String> conversionSourceTypes) throws SnapArgumentException, SnapOAuthAccessTokenException, SnapExecutionException, SnapResponseErrorException {
        checkParams(oAuthAccessToken, startTime, endTime, granularity, domain);
        if (StringUtils.isEmpty(pixelID)) {
            throw new SnapArgumentException("Pixel ID is required");
        }
        Optional<TimeSerieStat> result = Optional.empty();
        String url = this.endpointPixelSpecificDomain.replace("{pixel_id}", pixelID);
        url = prepareFinalUrl(url, startTime, endTime, granularity, fields, breakdown, test, dimension, pivot, swipeUpAttributionWindow, viewAttributionWindow, positionStats, omitEmpty, conversionSourceTypes, domain);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                if (granularity == GranularityEnum.TOTAL) {
                    SnapHttpResponseTotalStat responseFromJson = mapper.readValue(body, SnapHttpResponseTotalStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                } else {
                    SnapHttpResponseTimeseriesStat responseFromJson = mapper.readValue(body, SnapHttpResponseTimeseriesStat.class);
                    if (responseFromJson != null) {
                        result = responseFromJson.getStats();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get pixel specific domain stats, pixelID = {}", pixelID, e);
            throw new SnapExecutionException("Impossible to get pixel specific domain stats", e);
        }
        return result;
    }// getPixelSpecificDomainStats()

    private void checkParams(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity) throws SnapOAuthAccessTokenException, SnapArgumentException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (granularity == null) {
            throw new SnapArgumentException("Granularity is required");
        }
        if (startTime == null && (granularity == GranularityEnum.DAY || granularity == GranularityEnum.HOUR)) {
            throw new SnapArgumentException("StartTime is required");
        }
        if (endTime == null && (granularity == GranularityEnum.DAY || granularity == GranularityEnum.HOUR)) {
            throw new SnapArgumentException("EndTime is required");
        }
        if(startTime == null && endTime != null || startTime != null && endTime == null){
            throw new SnapArgumentException("StartTime and EndTime are required");
        }
        Calendar cal = Calendar.getInstance();
        if(startTime != null) {
            cal.setTime(startTime);
        }
        if(cal.get(Calendar.MINUTE) != 0){
            throw new SnapArgumentException("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)");
        }
        if(cal.get(Calendar.SECOND) != 0){
            throw new SnapArgumentException("StarTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)");
        }
        if(endTime != null) {
            cal.setTime(endTime);
        }
        if(cal.get(Calendar.MINUTE) != 0){
            throw new SnapArgumentException("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)");
        }
        if(cal.get(Calendar.SECOND) != 0){
            throw new SnapArgumentException("EndTime must be set to the top of the hour (Example : 22:00:00 not 22:45:11)");
        }
    }// checkParams()

    private void checkParams(String oAuthAccessToken, Date startTime, Date endTime, GranularityEnum granularity, String domain) throws SnapOAuthAccessTokenException, SnapArgumentException {
        checkParams(oAuthAccessToken, startTime, endTime, granularity);
        if (StringUtils.isEmpty(domain)) {
            throw new SnapOAuthAccessTokenException("Domain is required");
        }
    }// checkParams()

    /**
     * Convert String Date to Date by follow ISO 8601 pattern
     *
     * @param date Date
     * @return String Date
     */
    private String convertStringDateToISO8601(Date date) {
        if (date == null) {
            return "";
        }
        return sdf.format(date);
    }// convertStringDateToISO8601()

    /**
     * Prepare final URL
     *
     * @param url
     * @param startTime
     * @param endTime
     * @param granularity
     * @param fields
     * @param breakdown
     * @param test
     * @param dimension
     * @param pivot
     * @param swipeUpAttributionWindow
     * @param viewAttributionWindow
     * @param positionStats
     * @param omitEmpty
     * @param conversionSourceTypes
     * @return
     */
    private String prepareFinalUrl(String url, Date startTime, Date endTime, GranularityEnum granularity, List<String> fields, BreakdownEnum breakdown, Boolean test, DimensionEnum dimension, PivotEnum pivot, SwipeUpAttributionWindowEnum swipeUpAttributionWindow, ViewAttributionWindowEnum viewAttributionWindow, Boolean positionStats, Boolean omitEmpty, List<String> conversionSourceTypes, String domain) {
        StringBuilder sbUrl = new StringBuilder(url);
        sbUrl.append("?granularity=").append(granularity);
        if(startTime != null && endTime != null) {
            sbUrl.append("&start_time=").append(convertStringDateToISO8601(startTime))
                    .append("&end_time=").append(convertStringDateToISO8601(endTime));
        }
        if (CollectionUtils.isNotEmpty(fields)) {
            sbUrl.append("&fields=").append(String.join(",", fields));
        }
        if (CollectionUtils.isNotEmpty(conversionSourceTypes)) {
            sbUrl.append("&conversion_source_types=").append(String.join(",", conversionSourceTypes));
        }
        if (breakdown != null) {
            sbUrl.append("&breakdown=").append(breakdown);
        }
        if (test != null) {
            sbUrl.append("&test=").append(test);
        }
        if (dimension != null) {
            sbUrl.append("&dimension=").append(dimension);
        }
        if (pivot != null) {
            sbUrl.append("&pivot=").append(pivot);
        }
        if (swipeUpAttributionWindow != null) {
            sbUrl.append("&swipe_up_attribution_window=").append(swipeUpAttributionWindow);
        }
        if (viewAttributionWindow != null) {
            sbUrl.append("&view_attribution_window=").append(viewAttributionWindow);
        }
        if (positionStats != null) {
            sbUrl.append("&positionStats=").append(positionStats);
        }
        if (omitEmpty != null) {
            sbUrl.append("&omitEmpty=").append(omitEmpty);
        }
        if(StringUtils.isNotEmpty(domain)){
            sbUrl.append("&domain=").append(domain);
        }
        String result = sbUrl.toString();
        LOGGER.info("Url prepared for stats : {}", result);
        return result;
    }// prepareFinalUrl()

}// SnapStats
