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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExceptionsUtils;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.creatives.Creative;
import snapads4j.model.creatives.SnapHttpRequestCreative;
import snapads4j.model.creatives.SnapHttpResponseCreative;
import snapads4j.model.creatives.SnapHttpResponsePreviewCreative;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;

@Getter
@Setter
public class SnapCreative implements SnapCreativeInterface{
    
    private FileProperties fp;

    private String apiUrl;

    private String endpointCreateCreative;

    private String endpointUpdateCreative;

    private String endpointSpecificCreative;

    private String endpointAllCreatives;

    private String endpointPreviewCreative;

    private CloseableHttpClient httpClient;
    
    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapCreative.class);
    
    public SnapCreative() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointCreateCreative = this.apiUrl + (String) fp.getProperties().get("api.url.creative.create");
	this.endpointUpdateCreative = this.apiUrl + (String) fp.getProperties().get("api.url.creative.update");
	this.endpointSpecificCreative = this.apiUrl + (String) fp.getProperties().get("api.url.creative.one");
	this.endpointAllCreatives = this.apiUrl + (String) fp.getProperties().get("api.url.creative.all");
	this.endpointPreviewCreative = this.apiUrl + (String) fp.getProperties().get("api.url.creative.preview");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapCreative()

    @Override
    public Optional<Creative> createCreative(String oAuthAccessToken, Creative creative) throws SnapResponseErrorException, SnapOAuthAccessTokenException,
	    SnapArgumentException, JsonProcessingException, UnsupportedEncodingException {
	// TODO Auto-generated method stub
	return null;
    }// createCreative()


    @Override
    public Optional<Creative> updateCreative(String oAuthAccessToken, Creative creative)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	//checkCreative(creative, CheckAdEnum.UPDATE);
	Optional<Creative> result = Optional.empty();
	final String url = this.endpointUpdateCreative.replace("{id}", creative.getId());
	SnapHttpRequestCreative reqBody = new SnapHttpRequestCreative();
	reqBody.addCreative(creative);
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
		SnapHttpResponseCreative responseFromJson = mapper.readValue(body, SnapHttpResponseCreative.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificCreative();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to update creative, id = {}", creative.getId(), e);
	}
	return result;
    }// updateCreative()

    @Override
    public Optional<Creative> getSpecificCreative(String oAuthAccessToken, String id)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(id)) {
	    throw new SnapArgumentException("The Creative ID is mandatory");
	}
	Optional<Creative> result = Optional.empty();
	final String url = this.endpointSpecificCreative + id;
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
		SnapHttpResponseCreative responseFromJson = mapper.readValue(body, SnapHttpResponseCreative.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificCreative();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get specific Creative, id = {}", id, e);
	}
	return result;
    }// getSpecificCreative()

    @Override
    public List<Creative> getAllCreative(String oAuthAccessToken, String adAccountId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(adAccountId)) {
	    throw new SnapArgumentException("The AdAccount ID is mandatory");
	}
	List<Creative> results = new ArrayList<>();
	final String url = this.endpointAllCreatives.replace("{ad_account_id}", adAccountId);
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
		SnapHttpResponseCreative responseFromJson = mapper.readValue(body, SnapHttpResponseCreative.class);
		if (responseFromJson != null) {
		    results = responseFromJson.getAllCreatives();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get all creatives, adAccountId = {}", adAccountId, e);
	}
	return results;
    }// getAllCreative()

    @Override
    public Map<String, Object> getPreviewCreative(String oAuthAccessToken, String creativeID)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(creativeID)) {
	    throw new SnapArgumentException("The creative ID is missing");
	}
	Map<String, Object> result = new HashMap<>();
	final String url = this.endpointPreviewCreative.replace("{creative_id}", creativeID);
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
		SnapHttpResponsePreviewCreative responseFromJson = mapper.readValue(body, SnapHttpResponsePreviewCreative.class);
		if (responseFromJson != null) {
		    result.put("snapcodeLink", responseFromJson.getSnapCodeLink());
		    result.put("expiresAt", responseFromJson.getExpiresAt());
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get preview of creative, creativeID = {}", creativeID, e);
	}
	return result;
    }// getPreviewCreative()

}// SnapCreative
