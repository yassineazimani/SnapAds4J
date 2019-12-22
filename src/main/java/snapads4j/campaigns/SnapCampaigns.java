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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.enums.CheckCampaignEnum;
import snapads4j.exceptions.*;
import snapads4j.model.campaigns.Campaign;
import snapads4j.model.campaigns.SnapHttpRequestCampaign;
import snapads4j.model.campaigns.SnapHttpResponseCampaign;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;
import snapads4j.utils.JsonUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class SnapCampaigns implements SnapCampaignsInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointAllCampaigns;

    private String endpointSpecificCampaign;

    private String endpointCreationCampaign;

    private String endpointUpdateCampaign;

    private String endpointDeleteCampaign;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapCampaigns.class);

    /**
     * Constructor
     */
    public SnapCampaigns() {
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointAllCampaigns = this.apiUrl + fp.getProperties().get("api.url.campaigns.all");
        this.endpointSpecificCampaign = this.apiUrl + fp.getProperties().get("api.url.campaigns.one");
        this.endpointCreationCampaign = this.apiUrl + fp.getProperties().get("api.url.campaigns.create");
        this.endpointUpdateCampaign = this.apiUrl + fp.getProperties().get("api.url.campaigns.update");
        this.endpointDeleteCampaign = this.apiUrl + fp.getProperties().get("api.url.campaigns.delete");
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    } // SnapCampaigns()

    /**
     * Create a campaign.
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param campaign         Campaign to create {@link Campaign}
     * @return Campaign created
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException
     * @see <a href=
     * "https://developers.snapchat.com/api/docs/#create-a-campaign">Create
     * campaign</a>
     */
    @Override
    public Optional<Campaign> createCampaign(String oAuthAccessToken, Campaign campaign)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkCampaign(campaign, CheckCampaignEnum.CREATION);
        Optional<Campaign> result = Optional.empty();
        final String url = this.endpointCreationCampaign.replace("{ad_account_id}", campaign.getAdAccountId());
        SnapHttpRequestCampaign reqBody = new SnapHttpRequestCampaign();
        reqBody.addCampaign(campaign);
        HttpPost request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = JsonUtils.initMapper();
                String body = entityUtilsWrapper.toString(entity);
                SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificCampaign();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to create campaign, ad_account_id = {}", campaign.getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to create campaign", e);
        }
        return result;
    }

    @Override
    public Optional<Campaign> updateCampaign(String oAuthAccessToken, Campaign campaign)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkCampaign(campaign, CheckCampaignEnum.UPDATE);
        Optional<Campaign> result = Optional.empty();
        final String url = this.endpointUpdateCampaign.replace("{ad_account_id}", campaign.getAdAccountId());
        SnapHttpRequestCampaign reqBody = new SnapHttpRequestCampaign();
        reqBody.addCampaign(campaign);
        HttpPut request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = JsonUtils.initMapper();
                String body = entityUtilsWrapper.toString(entity);
                SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificCampaign();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to update campaign, id = {}", campaign.getId(), e);
            throw new SnapExecutionException("Impossible to update campaign", e);
        }
        return result;
    } // updateCampaign()

    @Override
    public List<Campaign> getAllCampaigns(String oAuthAccessToken, String adAccountId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(adAccountId)) {
            throw new SnapArgumentException("The Ad Account ID is required");
        }
        List<Campaign> campaigns = new ArrayList<>();
        final String url = this.endpointAllCampaigns.replace("{ad_account_id}", adAccountId);
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
                SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
                if (responseFromJson != null) {
                    campaigns = responseFromJson.getAllCampaigns();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get all campaigns, adAccountId = {}", adAccountId, e);
            throw new SnapExecutionException("Impossible to get all campaigns", e);
        }
        return campaigns;
    } // getAllCampaigns()

    @Override
    public Optional<Campaign> getSpecificCampaign(String oAuthAccessToken, String id)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(id)) {
            throw new SnapArgumentException("The campaign ID is required");
        }
        Optional<Campaign> result = Optional.empty();
        final String url = this.endpointSpecificCampaign + id;
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
                SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificCampaign();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get specific campaign, id = {}", id, e);
            throw new SnapExecutionException("Impossible to get specific campaign", e);
        }
        return result;
    } // getSpecificCampaign()

    @Override
    public boolean deleteCampaign(String oAuthAccessToken, String id)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(id)) {
            throw new SnapArgumentException("The campaign ID is required");
        }
        boolean result = false;
        final String url = this.endpointDeleteCampaign + id;
        HttpDelete request = HttpUtils.prepareDeleteRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
                if (responseFromJson != null && StringUtils.isNotEmpty(responseFromJson.getRequestStatus())) {
                    result = responseFromJson.getRequestStatus().equalsIgnoreCase("success");
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to delete specific campaign, id = {}", id, e);
            throw new SnapExecutionException("Impossible to get specific campaign", e);
        }
        return result;
    } // deleteCampaign()

    /**
     * Check the requirements of a campaign.
     *
     * @param campaign          campaign
     * @param typeCheckCampaign CREATION for a checking creation otherwise UPDATE
     * @throws SnapArgumentException
     * @see <a href=
     * "https://developers.snapchat.com/api/docs/#ad-accounts">Requirements</a>
     */
    private void checkCampaign(Campaign campaign, CheckCampaignEnum typeCheckCampaign) throws SnapArgumentException {
        if (typeCheckCampaign == null) {
            throw new SnapArgumentException("Please give type of checking campaign");
        }
        StringBuilder sb = new StringBuilder();
        if (campaign != null) {
            if (typeCheckCampaign == CheckCampaignEnum.UPDATE) {
                if (StringUtils.isEmpty(campaign.getId())) {
                    sb.append("The campaign ID is required,");
                }
            } else {
                if (campaign.getStartTime() == null) {
                    sb.append("The start time is required,");
                }
            }
            if (StringUtils.isEmpty(campaign.getName())) {
                sb.append("The campaign name is required,");
            }
            if (campaign.getStatus() == null) {
                sb.append("The campaign status is required,");
            }
            if (StringUtils.isEmpty(campaign.getAdAccountId())) {
                sb.append("The Ad Account ID is required,");
            }
        } else {
            sb.append("Campaign parameter is not given,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    } // checkCampaign()

} // SnapCampaigns
