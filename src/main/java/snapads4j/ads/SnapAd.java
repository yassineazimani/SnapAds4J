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
package snapads4j.ads;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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
import snapads4j.adsquads.SnapAdSquads;
import snapads4j.enums.CheckAdEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExceptionsUtils;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.ads.Ad;
import snapads4j.model.ads.SnapHttpRequestAd;
import snapads4j.model.ads.SnapHttpResponseAd;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;

/**
 * 
 * @author yassine
 *
 */
@Getter
@Setter
public class SnapAd implements SnapAdInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointCreateAd;

    private String endpointUpdateAd;

    private String endpointDeleteAd;

    private String endpointSpecificAd;

    private String endpointAllAdsAdSquad;

    private String endpointAllAdsAdAccount;

    private CloseableHttpClient httpClient;
    
    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapAdSquads.class);

    public SnapAd() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointCreateAd = this.apiUrl + (String) fp.getProperties().get("api.url.ad.create");
	this.endpointUpdateAd = this.apiUrl + (String) fp.getProperties().get("api.url.ad.update");
	this.endpointDeleteAd = this.apiUrl + (String) fp.getProperties().get("api.url.ad.delete");
	this.endpointAllAdsAdSquad = this.apiUrl + (String) fp.getProperties().get("api.url.ad.all");
	this.endpointAllAdsAdAccount = this.apiUrl + (String) fp.getProperties().get("api.url.ad.all2");
	this.endpointSpecificAd = this.apiUrl + (String) fp.getProperties().get("api.url.ad.one");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapAd()

    @Override
    public Optional<Ad> createAd(String oAuthAccessToken, Ad ad) throws SnapOAuthAccessTokenException, JsonProcessingException,
	    SnapResponseErrorException, SnapArgumentException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	checkSnapAd(ad, CheckAdEnum.CREATION);
	Optional<Ad> result = Optional.empty();
	final String url = this.endpointCreateAd.replace("{ad_squad_id}", ad.getAdSquadId());
	SnapHttpRequestAd reqBody = new SnapHttpRequestAd();
	reqBody.addAd(ad);
	LOGGER.info("Body create ad => {}", reqBody);
	HttpPost request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	    HttpEntity entity = response.getEntity();
	    if(entity != null) {
		String body = entityUtilsWrapper.toString(entity);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SnapHttpResponseAd responseFromJson = mapper.readValue(body, SnapHttpResponseAd.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificAd();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to create ad, ad_squad_id = {}", ad.getAdSquadId(), e);
	}
	return result;
    }// createAd()

    @Override
    public Optional<Ad> updateAd(String oAuthAccessToken, Ad ad) throws SnapOAuthAccessTokenException, JsonProcessingException,
	    SnapResponseErrorException, SnapArgumentException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	checkSnapAd(ad, CheckAdEnum.UPDATE);
	Optional<Ad> result = Optional.empty();
	final String url = this.endpointUpdateAd.replace("{ad_squad_id}", ad.getAdSquadId());
	SnapHttpRequestAd reqBody = new SnapHttpRequestAd();
	reqBody.addAd(ad);
	LOGGER.info("Body update ad squad => {}", reqBody);
	HttpPut request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	    HttpEntity entity = response.getEntity();
	    if(entity != null) {
		String body = entityUtilsWrapper.toString(entity);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SnapHttpResponseAd responseFromJson = mapper.readValue(body, SnapHttpResponseAd.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificAd();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to update ad, id = {}", ad.getId(), e);
	}
	return result;
    }// updateAd()

    @Override
    public List<Ad> getAllAdsFromAdSquad(String oAuthAccessToken, String adSquadId)
	    throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(adSquadId)) {
	    throw new SnapArgumentException("The AdSquad ID is mandatory");
	}
	List<Ad> results = new ArrayList<>();
	final String url = this.endpointAllAdsAdSquad.replace("{ad_squad_id}", adSquadId);
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
		SnapHttpResponseAd responseFromJson = mapper.readValue(body, SnapHttpResponseAd.class);
		if (responseFromJson != null) {
		    results = responseFromJson.getAllAd();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get all ads, adSquadId = {}", adSquadId, e);
	}
	return results;
    }// getAllAdsFromAdSquad()

    @Override
    public List<Ad> getAllAdsFromAdAccount(String oAuthAccessToken, String adAccountId)
	    throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(adAccountId)) {
	    throw new SnapArgumentException("The AdAccount ID is mandatory");
	}
	List<Ad> results = new ArrayList<>();
	final String url = this.endpointAllAdsAdAccount.replace("{ad_account_id}", adAccountId);
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
		SnapHttpResponseAd responseFromJson = mapper.readValue(body, SnapHttpResponseAd.class);
		if (responseFromJson != null) {
		    results = responseFromJson.getAllAd();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get all ads, adAccountId = {}", adAccountId, e);
	}
	return results;
    }// getAllAdsFromAdAccount()

    @Override
    public Optional<Ad> getSpecificAd(String oAuthAccessToken, String id)
	    throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(id)) {
	    throw new SnapArgumentException("The AdSquad ID is mandatory");
	}
	Optional<Ad> result = Optional.empty();
	final String url = this.endpointSpecificAd + id;
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
		SnapHttpResponseAd responseFromJson = mapper.readValue(body, SnapHttpResponseAd.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificAd();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get specific Ad, id = {}", id, e);
	}
	return result;
    }// getSpecificAd()

    @Override
    public void deleteAd(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(id)) {
	    throw new SnapArgumentException("The Ad ID is mandatory");
	}
	final String url = this.endpointDeleteAd + id;
	HttpDelete request = HttpUtils.prepareDeleteRequest(url, oAuthAccessToken);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to delete specific ad, id = {}", id, e);
	}
    }// deleteAd()

    private void checkSnapAd(Ad ad, CheckAdEnum check) throws SnapArgumentException {
	if (check == null) {
	    throw new SnapArgumentException("Please give type of checking Ad");
	}
	StringBuilder sb = new StringBuilder();
	if (ad == null) {
	    sb.append("Ad parameter is not given,");
	} else {
	    if (check == CheckAdEnum.UPDATE) {
		if (StringUtils.isEmpty(ad.getId())) {
		    sb.append("The Ad ID is required,");
		}
	    }
	    if (StringUtils.isEmpty(ad.getAdSquadId())) {
		sb.append("Ad Squad ID parameter is not given,");
	    }
	    if (StringUtils.isEmpty(ad.getName())) {
		sb.append("Ad's name parameter is not given,");
	    }
	    if (ad.getStatus() == null) {
		sb.append("Ad's status parameter is not given,");
	    }
	}
	String finalErrors = sb.toString();
	if (!StringUtils.isEmpty(finalErrors)) {
	    finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
	    throw new SnapArgumentException(finalErrors);
	}
    }// checkSnapAd()
}// SnapAd
