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
package snapads4j.pixel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.pixel.Pixel;
import snapads4j.model.pixel.SnapHttpRequestPixel;
import snapads4j.model.pixel.SnapHttpResponsePixel;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;

@Getter
@Setter
public class SnapPixel implements SnapPixelInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointPixelByAdAccount;

    private String endpointSpecificPixel;

    private String endpointUpdatePixel;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapPixel.class);

    public SnapPixel() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointPixelByAdAccount = this.apiUrl + (String) fp.getProperties().get("api.url.pixel.one.by.adaccount");
	this.endpointSpecificPixel = this.apiUrl + (String) fp.getProperties().get("api.url.pixel.one");
	this.endpointUpdatePixel = this.apiUrl + (String) fp.getProperties().get("api.url.pixel.update");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapPixel()

    @Override
    public Optional<Pixel> getSpecificPixelAssociatedByAdAccount(String oAuthAccessToken, String adAccountId)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    SnapExecutionException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(adAccountId)) {
	    throw new SnapArgumentException("The Ad Account ID is required");
	}
	Optional<Pixel> result = Optional.empty();
	final String url = this.endpointPixelByAdAccount.replace("{ad_account_id}", adAccountId);
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
		SnapHttpResponsePixel responseFromJson = mapper.readValue(body, SnapHttpResponsePixel.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getPixel();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get pixel, adAccountId = {}", adAccountId, e);
	    throw new SnapExecutionException("Impossible to get pixel", e);
	}
	return result;
    }// getSpecificPixelAssociatedByAdAccount()

    @Override
    public Optional<Pixel> getSpecificPixel(String oAuthAccessToken, String pixelId) throws SnapResponseErrorException,
	    SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	if (StringUtils.isEmpty(pixelId)) {
	    throw new SnapArgumentException("The Pixel ID is required");
	}
	Optional<Pixel> result = Optional.empty();
	final String url = this.endpointSpecificPixel + pixelId;
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
		SnapHttpResponsePixel responseFromJson = mapper.readValue(body, SnapHttpResponsePixel.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getPixel();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to get specific pixel, id = {}", pixelId, e);
	    throw new SnapExecutionException("Impossible to get specific pixel", e);
	}
	return result;
    }// getSpecificPixel()

    @Override
    public Optional<Pixel> updatePixel(String oAuthAccessToken, Pixel pixel)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	checkPixel(pixel);
	Optional<Pixel> result = Optional.empty();
	final String url = this.endpointUpdatePixel.replace("{ad_account_id}", pixel.getAdAccountId());
	SnapHttpRequestPixel reqBody = new SnapHttpRequestPixel();
	reqBody.addPixel(pixel);
	HttpPut request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
	try (CloseableHttpResponse response = httpClient.execute(request)) {
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode >= 300) {
		SnapResponseErrorException ex = SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
		throw ex;
	    }
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		String body = entityUtilsWrapper.toString(entity);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SnapHttpResponsePixel responseFromJson = mapper.readValue(body, SnapHttpResponsePixel.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getPixel();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to update pixel, adAccountId = {}", pixel.getAdAccountId(), e);
	    throw new SnapExecutionException("Impossible to update pixel", e);
	}
	return result;
    }// updatePixel()

    private void checkPixel(Pixel pixel) throws SnapArgumentException {
	StringBuilder sb = new StringBuilder();
	if (pixel != null) {
	    if (StringUtils.isEmpty(pixel.getId())) {
		sb.append("Pixel ID parameter is required,");
	    }
	    if (StringUtils.isEmpty(pixel.getName())) {
		sb.append("Pixel name parameter is required,");
	    }
	    if (StringUtils.isEmpty(pixel.getAdAccountId())) {
		sb.append("Ad Account ID parameter is required,");
	    }
	} else {
	    sb.append("Pixel parameter is not given,");
	}
	String finalErrors = sb.toString();
	if (!StringUtils.isEmpty(finalErrors)) {
	    finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
	    throw new SnapArgumentException(finalErrors);
	}
    }// checkPixel()

}// SnapPixel
