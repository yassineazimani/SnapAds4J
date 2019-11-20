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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import snapads4j.enums.CheckCampaignEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExceptionsUtils;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.campaigns.Campaign;
import snapads4j.model.campaigns.SnapHttpRequestCampaign;
import snapads4j.model.campaigns.SnapHttpResponseCampaign;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;

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

    /** Constructor */
    public SnapCampaigns() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointAllCampaigns = this.apiUrl + (String) fp.getProperties().get("api.url.campaigns.all");
	this.endpointSpecificCampaign = this.apiUrl + (String) fp.getProperties().get("api.url.campaigns.one");
	this.endpointCreationCampaign = this.apiUrl + (String) fp.getProperties().get("api.url.campaigns.create");
	this.endpointUpdateCampaign = this.apiUrl + (String) fp.getProperties().get("api.url.campaigns.update");
	this.endpointDeleteCampaign = this.apiUrl + (String) fp.getProperties().get("api.url.campaigns.delete");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
    } // SnapCampaigns()

    /**
     * Create a campaign.
     *
     * @see <a href=
     *      "https://developers.snapchat.com/api/docs/#create-a-campaign">Create
     *      campaign</a>
     * @param oAuthAccessToken oAuthAccessToken
     * @param campaign         Campaign to create {@link #Campaign}
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * 
     * @return Campaign created
     */
    @Override
    public Optional<Campaign> createCampaign(String oAuthAccessToken, Campaign campaign)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	checkCampaign(campaign, CheckCampaignEnum.CREATION);
	Optional<Campaign> result = Optional.empty();
	final String url = this.endpointCreationCampaign.replace("{ad_account_id}", campaign.getAdAccountId());
	SnapHttpRequestCampaign reqBody = new SnapHttpRequestCampaign();
	reqBody.addCampaign(this.convertCampaignToMap(campaign));
	LOGGER.info("Body create campaign => {}", reqBody);
	HttpPost request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String body = entityUtilsWrapper.toString(entity);
		SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificCampaign();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to create campaign, ad_account_id = {}", campaign.getAdAccountId(), e);
	}
	return result;
    }

    @Override
    public Optional<Campaign> updateCampaign(String oAuthAccessToken, Campaign campaign)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	checkCampaign(campaign, CheckCampaignEnum.UPDATE);
	Optional<Campaign> result = Optional.empty();
	final String url = this.endpointUpdateCampaign.replace("{ad_account_id}", campaign.getAdAccountId());
	SnapHttpRequestCampaign reqBody = new SnapHttpRequestCampaign();
	reqBody.addCampaign(this.convertCampaignToMap(campaign));
	LOGGER.info("Body update campaign => {}", reqBody);
	HttpPut request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String body = entityUtilsWrapper.toString(entity);
		SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificCampaign();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to update campaign, id = {}", campaign.getId(), e);
	}
	return result;
    } // updateCampaign()

    @Override
    public List<Campaign> getAllCampaigns(String oAuthAccessToken, String adAccountId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(adAccountId)) {
	    throw new SnapArgumentException("The Ad Account ID is mandatory");
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
		    SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		    throw ex;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
		if (responseFromJson != null) {
		    campaigns = responseFromJson.getAllCampaigns();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get all campaigns, adAccountId = {}", adAccountId, e);
	}
	return campaigns;
    } // getAllCampaigns()

    @Override
    public Optional<Campaign> getSpecificCampaign(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(id)) {
	    throw new SnapArgumentException("The campaign ID is mandatory");
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
		    SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		    throw ex;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SnapHttpResponseCampaign responseFromJson = mapper.readValue(body, SnapHttpResponseCampaign.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificCampaign();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get specific campaign, id = {}", id, e);
	}
	return result;
    } // getSpecificCampaign()

    @Override
    public void deleteCampaign(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(id)) {
	    throw new SnapArgumentException("The campaign ID is mandatory");
	}
	final String url = this.endpointDeleteCampaign + id;
	HttpDelete request = HttpUtils.prepareDeleteRequest(url, oAuthAccessToken);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to delete specific campaign, id = {}", id, e);
	}
    } // deleteCampaign()

    /**
     * Check the requirements of a campaign.
     *
     * @see <a href=
     *      "https://developers.snapchat.com/api/docs/#ad-accounts">Requirements</a>
     * @param campaign          campaign
     * @param typeCheckCampaign CREATION for a checking creation otherwise UPDATE
     * @throws SnapArgumentException
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

    /**
     * Convert an campaign instance to a map
     *
     * @param campaign
     * @return map
     */
    private Map<String, String> convertCampaignToMap(Campaign campaign) {
	Map<String, String> result = new HashMap<>();
	if (campaign != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    if (StringUtils.isNotEmpty(campaign.getId())) {
		result.put("id", campaign.getId());
	    }
	    if (StringUtils.isNotEmpty(campaign.getAdAccountId())) {
		result.put("ad_account_id", campaign.getAdAccountId());
	    }
	    if (campaign.getDailyBudgetMicro() != null) {
		result.put("daily_budget_micro", campaign.getDailyBudgetMicro().toString());
	    }
	    if (campaign.getEndTime() != null) {
		result.put("end_time", sdf.format(campaign.getEndTime()));
	    }
	    if (StringUtils.isNotEmpty(campaign.getName())) {
		result.put("name", campaign.getName());
	    }
	    if (campaign.getStartTime() != null) {
		result.put("start_time", sdf.format(campaign.getStartTime()));
	    }
	    if (campaign.getStatus() != null) {
		result.put("status", campaign.getStatus().toString());
	    }
	    if (campaign.getLifetimeSpendCapMicro() != null) {
		result.put("lifetime_spend_cap_micro", campaign.getLifetimeSpendCapMicro().toString());
	    }
	    if (campaign.getObjective() != null) {
		result.put("objective", campaign.getObjective().toString());
	    }
	    if (campaign.getBuyModel() != null) {
		result.put("buy_model", campaign.getBuyModel().toString());
	    }
	    if (campaign.getMeasurementSpec() != null) {
		Map<String, String> measurementSpec = new HashMap<>();
		measurementSpec.put("ios_app_id", campaign.getMeasurementSpec().getIosAppId());
		measurementSpec.put("android_app_url", campaign.getMeasurementSpec().getAndroidAppUrl());
		ObjectMapper mapper = new ObjectMapper();
		try {
		    result.put("measurement_spec", mapper.writeValueAsString(measurementSpec));
		} catch (JsonProcessingException e) {
		    LOGGER.error("Impossible to write measurement_spec {}", measurementSpec);
		}
	    }
	}
	return result;
    } // convertCampaignToMap()
} // SnapCampaigns
