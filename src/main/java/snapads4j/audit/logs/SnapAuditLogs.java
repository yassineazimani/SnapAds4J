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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.exceptions.*;
import snapads4j.model.audit.logs.AuditLog;
import snapads4j.model.audit.logs.SnapHttpResponseAuditLog;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;
import snapads4j.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
public class SnapAuditLogs implements SnapAuditLogsInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointFetchByCampaign;

    private String endpointFetchByAd;

    private String endpointFetchByAdSquad;

    private String endpointFetchByCreative;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapAuditLogs.class);

    public SnapAuditLogs() {
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointFetchByCampaign = this.apiUrl
                + fp.getProperties().get("api.url.audit.logs.by.campaign") + "?limit=50";
        this.endpointFetchByAd = this.apiUrl
                + fp.getProperties().get("api.url.audit.logs.by.ad") + "?limit=50";
        this.endpointFetchByAdSquad = this.apiUrl
                + fp.getProperties().get("api.url.audit.logs.by.adsquad") + "?limit=50";
        this.endpointFetchByCreative = this.apiUrl
                + fp.getProperties().get("api.url.audit.logs.by.creative") + "?limit=50";
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapAuditLogs()

    @Override
    public List<AuditLog> fetchChangeLogsForCampaign(String oAuthAccessToken, String campaignId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(campaignId)) {
            throw new SnapArgumentException("Campaign ID is required");
        }
        List<AuditLog> results = new ArrayList<>();
        final String url = this.endpointFetchByCampaign.replace("{campaign_id}", campaignId);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseAuditLog responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAuditLog.class);
                if (responseFromJson != null) {
                    results = responseFromJson.getAllAuditLogs();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get audit logs, campaign_id = {}", campaignId, e);
            throw new SnapExecutionException("Impossible to get audit logs", e);
        }
        return results;
    }// fetchChangeLogsForCampaign()

    @Override
    public List<AuditLog> fetchChangeLogsForAdSquad(String oAuthAccessToken, String adSquadId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(adSquadId)) {
            throw new SnapArgumentException("AdSquad ID is required");
        }
        List<AuditLog> results = new ArrayList<>();
        final String url = this.endpointFetchByAdSquad.replace("{adsquad_id}", adSquadId);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseAuditLog responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAuditLog.class);
                if (responseFromJson != null) {
                    results = responseFromJson.getAllAuditLogs();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get audit logs, ad_squad_id = {}", adSquadId, e);
            throw new SnapExecutionException("Impossible to get audit logs", e);
        }
        return results;
    }// fetchChangeLogsForAdSquad()

    @Override
    public List<AuditLog> fetchChangeLogsForAd(String oAuthAccessToken, String adId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(adId)) {
            throw new SnapArgumentException("Ad ID is required");
        }
        List<AuditLog> results = new ArrayList<>();
        final String url = this.endpointFetchByAd.replace("{ad_id}", adId);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseAuditLog responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAuditLog.class);
                if (responseFromJson != null) {
                    results = responseFromJson.getAllAuditLogs();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get audit logs, ad_id = {}", adId, e);
            throw new SnapExecutionException("Impossible to get audit logs", e);
        }
        return results;
    }// fetchChangeLogsForAd()

    @Override
    public List<AuditLog> fetchChangeLogsForCreative(String oAuthAccessToken, String creativeId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(creativeId)) {
            throw new SnapArgumentException("Creative ID is required");
        }
        List<AuditLog> results = new ArrayList<>();
        final String url = this.endpointFetchByCreative.replace("{creative_id}", creativeId);
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseAuditLog responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAuditLog.class);
                if (responseFromJson != null) {
                    results = responseFromJson.getAllAuditLogs();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get audit logs, creative_id = {}", creativeId, e);
            throw new SnapExecutionException("Impossible to get audit logs", e);
        }
        return results;
    }// fetchChangeLogsForCreative()

}// SnapAuditLogs
