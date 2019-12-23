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
package snapads4j.bid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.exceptions.*;
import snapads4j.model.bid.BidEstimate;
import snapads4j.model.bid.SnapHttpResponseBidEstimate;
import snapads4j.model.bid.TargetingSpecBidEstimate;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;
import snapads4j.utils.JsonUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Set;

/**
 * SnapBidEstimate
 *
 * @see {https://developers.snapchat.com/api/docs/#bid-estimate}
 */
@Setter
public class SnapBidEstimate implements SnapBidEstimateInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointSizeByAdAccount;

    private String endpointSizeByAdSquad;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapBidEstimate.class);

    public SnapBidEstimate() throws IOException{
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointSizeByAdAccount = this.apiUrl
                + fp.getProperties().get("api.url.bid.estimate.by.adaccount");
        this.endpointSizeByAdSquad = this.apiUrl +
                fp.getProperties().get("api.url.bid.estimate.by.adsquad");
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapBidEstimate()

    @Override
    public Optional<BidEstimate> getBidEstimateBySquadSpec(String oAuthAccessToken, String adAccountID, TargetingSpecBidEstimate targetingSpecBidEstimate)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(adAccountID)) {
            throw new SnapArgumentException("Ad Account ID is required");
        }
        this.checkTargetingSpecBidEstimate(targetingSpecBidEstimate);
        Optional<BidEstimate> result = Optional.empty();
        final String url = this.endpointSizeByAdAccount.replace("{ID}", adAccountID);
        HttpPost request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, targetingSpecBidEstimate);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                mapper.registerModule(new Jdk8Module());
                SnapHttpResponseBidEstimate responseFromJson = mapper.readValue(body,
                        SnapHttpResponseBidEstimate.class);
                if (responseFromJson != null) {
                    result = Optional.ofNullable(responseFromJson.getBidEstimate());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get bid estimate, ad_account_id = {}", adAccountID, e);
            throw new SnapExecutionException("Impossible to get bid estimate", e);
        }
        return result;
    }// getBidEstimateBySquadSpec()

    @Override
    public Optional<BidEstimate> getBidEstimateByAdSquadId(String oAuthAccessToken, String adSquadID)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(adSquadID)) {
            throw new SnapArgumentException("AdSquad ID is required");
        }
        Optional<BidEstimate> result = Optional.empty();
        final String url = this.endpointSizeByAdSquad.replace("{ID}", adSquadID);
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
                mapper.registerModule(new Jdk8Module());
                SnapHttpResponseBidEstimate responseFromJson = mapper.readValue(body,
                        SnapHttpResponseBidEstimate.class);
                if (responseFromJson != null) {
                    result = Optional.ofNullable(responseFromJson.getBidEstimate());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get bid estimate, ad_squad_id = {}", adSquadID, e);
            throw new SnapExecutionException("Impossible to get bid estimate", e);
        }
        return result;
    }// getBidEstimateByAdSquadId()

    private void checkTargetingSpecBidEstimate(TargetingSpecBidEstimate targetingSpecBidEstimate) throws SnapArgumentException {
        if (targetingSpecBidEstimate == null) {
            throw new SnapArgumentException("TargetingSpecBidEstimate instance is required");
        }
        StringBuilder sb = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TargetingSpecBidEstimate>> violations = validator.validate(targetingSpecBidEstimate);
        for (ConstraintViolation<TargetingSpecBidEstimate> violation : violations) {
            sb.append(violation.getMessage()).append(",");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkTargetingSpecBidEstimate()

}// SnapBidEstimate
