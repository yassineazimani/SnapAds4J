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
package snapads4j.audience.match;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Setter;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExceptionsUtils;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.audience.match.AudienceSegment;
import snapads4j.model.audience.match.SnapHttpRequestAudienceSegment;
import snapads4j.model.audience.match.SnapHttpResponseAudienceSegment;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;

@Setter
public class SnapAudienceSegment implements SnapAudienceSegmentInterface{
    
    private FileProperties fp;

    private String apiUrl;

    private String endpointCreationAudienceSegment;
    
    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapAudienceSegment.class);
    
    public SnapAudienceSegment() {
	this.fp = new FileProperties();
	this.apiUrl = (String) fp.getProperties().get("api.url");
	this.endpointCreationAudienceSegment = this.apiUrl + (String) fp.getProperties().get("api.url.audience.match.create");
	this.httpClient = HttpClients.createDefault();
	this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapAudienceSegment()

    @Override
    public Optional<AudienceSegment> createAudienceSegment(String oAuthAccessToken, AudienceSegment segment)
	    throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
	    JsonProcessingException, UnsupportedEncodingException {
	if (StringUtils.isEmpty(oAuthAccessToken)) {
	    throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
	}
	checkAudienceSegment(segment);
	Optional<AudienceSegment> result = Optional.empty();
	final String url = this.endpointCreationAudienceSegment.replace("{ad_account_id}", segment.getAdAccountId());
	SnapHttpRequestAudienceSegment reqBody = new SnapHttpRequestAudienceSegment();
	reqBody.addAudienceSegment(segment);
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
		SnapHttpResponseAudienceSegment responseFromJson = mapper.readValue(body, SnapHttpResponseAudienceSegment.class);
		if (responseFromJson != null) {
		    result = responseFromJson.getSpecificAudienceSegment();
		}
	    }
	} catch (IOException e) {
	    LOGGER.error("Impossible to create audience segment, ad_account_id = {}", segment.getAdAccountId(), e);
	}
	return result;
    }// createAudienceSegment()
    
    private void checkAudienceSegment(AudienceSegment segment) throws SnapArgumentException {
	StringBuilder sb = new StringBuilder();
	if (segment != null) {
	    if(StringUtils.isEmpty(segment.getAdAccountId())) {
		sb.append("The Ad Account ID is required,");
	    }
	    if(StringUtils.isEmpty(segment.getName())) {
		sb.append("The name is required,");
	    }
	    if(segment.getRetentionInDays() < 0) {
		sb.append("The retention must be equal or greater than zero,");
	    }
	    if(segment.getSourceType() == null) {
		sb.append("The source type is required,");
	    }
	} else {
	    sb.append("Segment parameter is not given,");
	}
	String finalErrors = sb.toString();
	if (!StringUtils.isEmpty(finalErrors)) {
	    finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
	    throw new SnapArgumentException(finalErrors);
	}
    }// checkAudienceSegment()
    
}// SnapAudienceSegment
