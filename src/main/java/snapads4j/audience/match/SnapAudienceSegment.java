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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.enums.SchemaEnum;
import snapads4j.enums.SourceTypeEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.exceptions.*;
import snapads4j.model.Pagination;
import snapads4j.model.audience.match.*;
import snapads4j.model.config.HttpDeleteWithBody;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Setter
public class SnapAudienceSegment implements SnapAudienceSegmentInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointCreationAudienceSegment;

    private String endpointCreationSam;

    private String endpointUpdateAudienceSegment;

    private String endpointGetAllAudienceSegments;

    private String endpointGetSpecificAudienceSegment;

    private String endpointAddUserForAudienceSegment;

    private String endpointDeleteUserForAudienceSegment;

    private String endpointDeleteAllUsersForAudienceSegment;

    private String endpointDeleteAudienceSegment;

    private int minLimitPagination;

    private int maxLimitPagination;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapAudienceSegment.class);

    public SnapAudienceSegment() {
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointCreationAudienceSegment = this.apiUrl
                + fp.getProperties().get("api.url.audience.match.create");
        this.endpointCreationSam = this.apiUrl +
                fp.getProperties().get("api.url.audience.match.create.sam.lookalikes");
        this.endpointUpdateAudienceSegment = this.apiUrl
                + fp.getProperties().get("api.url.audience.match.update");
        this.endpointGetAllAudienceSegments = this.apiUrl
                + fp.getProperties().get("api.url.audience.match.all");
        this.endpointGetSpecificAudienceSegment = this.apiUrl
                + fp.getProperties().get("api.url.audience.match.one");
        this.endpointAddUserForAudienceSegment = this.apiUrl
                + fp.getProperties().get("api.url.audience.match.add.user");
        this.endpointDeleteUserForAudienceSegment = this.apiUrl
                + fp.getProperties().get("api.url.audience.match.delete.user");
        this.endpointDeleteAllUsersForAudienceSegment = this.apiUrl
                + fp.getProperties().get("api.url.audience.match.delete.all");
        this.endpointDeleteAudienceSegment = this.apiUrl
                + fp.getProperties().get("api.url.audience.match.delete");
        this.minLimitPagination = Integer.parseInt((String) fp.getProperties().get("api.url.pagination.limit.min"));
        this.maxLimitPagination = Integer.parseInt((String) fp.getProperties().get("api.url.pagination.limit.max"));
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapAudienceSegment()

    @Override
    public Optional<AudienceSegment> createAudienceSegment(String oAuthAccessToken, AudienceSegment segment)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkAudienceSegment(segment, true);
        Optional<AudienceSegment> result = Optional.empty();
        final String url = this.endpointCreationAudienceSegment.replace("{ad_account_id}", segment.getAdAccountId());
        SnapHttpRequestAudienceSegment reqBody = new SnapHttpRequestAudienceSegment();
        reqBody.addAudienceSegment(segment);
        HttpPost request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseAudienceSegment responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAudienceSegment.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificAudienceSegment();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to create audience segment, ad_account_id = {}", segment.getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to create audience segment", e);
        }
        return result;
    }// createAudienceSegment()

    @Override
    public Optional<AudienceSegment> createSamLookalikes(String oAuthAccessToken, SamLookalikes sam)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkSamLookalikes(sam);
        Optional<AudienceSegment> result = Optional.empty();
        final String url = this.endpointCreationSam.replace("{ad_account_id}", sam.getAdAccountId());
        SnapHttpRequestAudienceSegment reqBody = new SnapHttpRequestAudienceSegment();
        reqBody.addAudienceSegment(sam);
        HttpPost request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseAudienceSegment responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAudienceSegment.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificAudienceSegment();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to create sam lookalikes, ad_account_id = {}", sam.getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to create sam lookalikes", e);
        }
        return result;
    }// createSamLookalikes()

    @Override
    public Optional<AudienceSegment> updateAudienceSegment(String oAuthAccessToken, AudienceSegment segment)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkAudienceSegment(segment, false);
        Optional<AudienceSegment> result = Optional.empty();
        final String url = this.endpointUpdateAudienceSegment.replace("{ad_account_id}", segment.getAdAccountId());
        SnapHttpRequestAudienceSegment reqBody = new SnapHttpRequestAudienceSegment();
        reqBody.addAudienceSegment(segment);
        HttpPut request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseAudienceSegment responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAudienceSegment.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificAudienceSegment();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to update audience segment, ad_account_id = {}", segment.getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to update audience segment", e);
        }
        return result;
    }// updateAudienceSegment()

    @Override
    public List<Pagination<AudienceSegment>> getAllAudienceSegments(String oAuthAccessToken, String adAccountID, int limit)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(adAccountID)) {
            throw new SnapArgumentException("The Ad Account ID is required");
        }
        if (limit < minLimitPagination) {
            throw new SnapArgumentException("Minimum limit is " + minLimitPagination);
        }
        if (limit > maxLimitPagination) {
            throw new SnapArgumentException("Maximum limit is " + maxLimitPagination);
        }
        List<Pagination<AudienceSegment>> results = new ArrayList<>();
        String url = this.endpointGetAllAudienceSegments.replace("{ad_account_id}", adAccountID);
        url += "?limit=" + limit;
        boolean hasNextPage = true;
        int numberPage = 1;
        while (hasNextPage) {
            HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    ObjectMapper mapper = JsonUtils.initMapper();
                    String body = entityUtilsWrapper.toString(entity);
                    SnapHttpResponseAudienceSegment responseFromJson = mapper.readValue(body,
                            SnapHttpResponseAudienceSegment.class);
                    if (responseFromJson != null) {
                        results.add(new Pagination<>(numberPage++, responseFromJson.getAllAudienceSegment()));
                        hasNextPage = responseFromJson.hasPaging();
                        if (hasNextPage) {
                            url = responseFromJson.getPaging().getNextLink();
                            LOGGER.info("Next url page pagination is {}", url);
                        }
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Impossible to get all audience segments, ad_account_id = {}", adAccountID, e);
                throw new SnapExecutionException("Impossible to get all audience segments", e);
            }
        }
        return results;
    }// getAllAudienceSegments()

    @Override
    public Optional<AudienceSegment> getSpecificAudienceSegment(String oAuthAccessToken, String segmentID)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(segmentID)) {
            throw new SnapArgumentException("ID is required");
        }
        Optional<AudienceSegment> result = Optional.empty();
        final String url = this.endpointGetSpecificAudienceSegment + segmentID;
        HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = JsonUtils.initMapper();
                String body = entityUtilsWrapper.toString(entity);
                SnapHttpResponseAudienceSegment responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAudienceSegment.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificAudienceSegment();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get all audience segments, segmentID = {}", segmentID, e);
            throw new SnapExecutionException("Impossible to get all audience segments", e);
        }
        return result;
    }// getSpecificAudienceSegment()

    /**
     * Type schema mobile_ad_id regex isn't checked here unlike phone and email.
     *
     * @throws SnapExecutionException
     */
    @Override
    public int addUserToSegment(String oAuthAccessToken, FormUserForAudienceSegment formUserForAudienceSegment)
            throws SnapOAuthAccessTokenException, JsonProcessingException, UnsupportedEncodingException,
            SnapResponseErrorException, SnapArgumentException, SnapNormalizeArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkUserForAudienceSegment(formUserForAudienceSegment);
        int result = 0;
        if (CollectionUtils.isNotEmpty(formUserForAudienceSegment.getData())) {
            normalizeAndHashDataUserForAudienceSegment(formUserForAudienceSegment);
            final String url = this.endpointAddUserForAudienceSegment.replace("{segment_id}",
                    formUserForAudienceSegment.getId());
            SnapHttpRequestUserForAudienceSegment reqBody = new SnapHttpRequestUserForAudienceSegment();
            reqBody.addUserForAudienceSegment(formUserForAudienceSegment);
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
                    SnapHttpResponseUserForAudienceSegment responseFromJson = mapper.readValue(body,
                            SnapHttpResponseUserForAudienceSegment.class);
                    if (responseFromJson != null) {
                        Optional<UserForAudienceSegment> resp = responseFromJson.getSpecificUserForAudienceSegment();
                        if (resp.isPresent()) {
                            result = resp.get().getNumberUploadedUsers();
                        }
                    }
                }
            } catch (IOException ie) {
                LOGGER.error("Impossible to add user to an existant segment, segmentID = {}",
                        formUserForAudienceSegment.getId(), ie);
                throw new SnapExecutionException("Impossible to add user to an existant segment", ie);
            }
        }
        return result;
    }// addUserToSegment()

    /**
     * Type schema mobile_ad_id regex isn't checked here unlike phone and email.
     *
     * @throws SnapExecutionException
     */
    @Override
    public int deleteUserFromSegment(String oAuthAccessToken, FormUserForAudienceSegment formUserForAudienceSegment)
            throws SnapOAuthAccessTokenException, JsonProcessingException, UnsupportedEncodingException,
            SnapResponseErrorException, SnapArgumentException, SnapNormalizeArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkUserForAudienceSegment(formUserForAudienceSegment);
        int result = 0;
        if (CollectionUtils.isNotEmpty(formUserForAudienceSegment.getData())) {
            normalizeAndHashDataUserForAudienceSegment(formUserForAudienceSegment);
            final String url = this.endpointDeleteUserForAudienceSegment.replace("{segment_id}",
                    formUserForAudienceSegment.getId());
            SnapHttpRequestUserForAudienceSegment reqBody = new SnapHttpRequestUserForAudienceSegment();
            reqBody.addUserForAudienceSegment(formUserForAudienceSegment);
            HttpDeleteWithBody request = HttpUtils.prepareDeleteRequestObject(url, oAuthAccessToken, reqBody);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    ObjectMapper mapper = JsonUtils.initMapper();
                    String body = entityUtilsWrapper.toString(entity);
                    SnapHttpResponseUserForAudienceSegment responseFromJson = mapper.readValue(body,
                            SnapHttpResponseUserForAudienceSegment.class);
                    if (responseFromJson != null) {
                        Optional<UserForAudienceSegment> resp = responseFromJson.getSpecificUserForAudienceSegment();
                        if (resp.isPresent()) {
                            result = resp.get().getNumberUploadedUsers();
                        }
                    }
                }
            } catch (IOException ie) {
                LOGGER.error("Impossible to delete user to an existant segment, segmentID = {}",
                        formUserForAudienceSegment.getId(), ie);
                throw new SnapExecutionException("Impossible to delete user to an existant segment", ie);
            }
        }
        return result;
    }// deleteUserFromSegment()

    @Override
    public Optional<AudienceSegment> deleteAllUsersFromSegment(String oAuthAccessToken, String segmentID)
            throws SnapOAuthAccessTokenException,
            SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(segmentID)) {
            throw new SnapArgumentException("The segment ID is required");
        }
        Optional<AudienceSegment> result = Optional.empty();
        final String url = this.endpointDeleteAllUsersForAudienceSegment.replace("{segment_id}", segmentID);
        HttpDelete request = HttpUtils.prepareDeleteRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = JsonUtils.initMapper();
                String body = entityUtilsWrapper.toString(entity);
                SnapHttpResponseAudienceSegment responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAudienceSegment.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificAudienceSegment();
                }
            }
        } catch (IOException ie) {
            LOGGER.error("Impossible to delete all users from segment, segmentID = {}", segmentID, ie);
            throw new SnapExecutionException("Impossible to delete all users from segment", ie);
        }
        return result;
    }// deleteAllUsersFromSegment()

    public boolean deleteAudienceSegment(String oAuthAccessToken, String segmentID)
            throws SnapOAuthAccessTokenException,
            SnapResponseErrorException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(segmentID)) {
            throw new SnapArgumentException("The segment ID is required");
        }
        boolean result = false;
        final String url = this.endpointDeleteAudienceSegment + segmentID;

        HttpDelete request = HttpUtils.prepareDeleteRequest(url, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = JsonUtils.initMapper();
                String body = entityUtilsWrapper.toString(entity);
                SnapHttpResponseAudienceSegment responseFromJson = mapper.readValue(body,
                        SnapHttpResponseAudienceSegment.class);
                result = responseFromJson.getRequestStatus()
                        .equals(StatusEnum.SUCCESS.toString());
            }
        } catch (IOException ie) {
            LOGGER.error("Impossible to delete audience segment, segmentID = {}", segmentID, ie);
            throw new SnapExecutionException("Impossible to delete audience segment", ie);
        }
        return result;
    }// deleteAudienceSegment()

    private void checkUserForAudienceSegment(FormUserForAudienceSegment form) throws SnapArgumentException {
        StringBuilder sb = new StringBuilder();
        if (form != null) {
            if (form.getData() == null) {
                sb.append("List of hashed identifiers is required,");
            }
            if (CollectionUtils.isEmpty(form.getSchema())) {
                sb.append("Type schema is required,");
            }
            if (StringUtils.isEmpty(form.getId())) {
                sb.append("Segment ID is required,");
            }
        } else {
            sb.append("FormUserForAudienceSegment parameter is required,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkUserForAudienceSegment()

    /**
     * Mobile_ad_id isn't checked unlike email and phone
     *
     * @param form
     * @throws SnapArgumentException
     */
    private void normalizeAndHashDataUserForAudienceSegment(FormUserForAudienceSegment form)
            throws SnapNormalizeArgumentException {
        if (form == null || CollectionUtils.isEmpty(form.getSchema()) || CollectionUtils.isEmpty(form.getData())) {
            throw new SnapNormalizeArgumentException("Form must be normalized and hashed before send to Snap API");
        }
        SchemaEnum schema = form.getSchema().get(0);
        List<String> data = form.getData();
        data = data.stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
        if (schema == SchemaEnum.EMAIL_SHA256) {
            List<String> tmpEmails = data.stream().filter(Pattern.compile("^(.+)@(.+)$").asPredicate())
                    .collect(Collectors.toList());
            if (tmpEmails.size() != data.size()) {
                throw new SnapNormalizeArgumentException("Data must be have valid email(s)");
            }
        } else if (schema == SchemaEnum.PHONE_SHA256) {
            List<String> tmpPhones = data.stream()
                    .filter(Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}").asPredicate())
                    .collect(Collectors.toList());
            if (tmpPhones.size() != data.size()) {
                throw new SnapNormalizeArgumentException("Data must be have valid phone(s) number");
            }
        }
        data = data.stream().map(DigestUtils::sha256Hex).collect(Collectors.toList());
        form.setData(data);
    }// normalizeAndHashDataUserForAudienceSegment()

    private void checkAudienceSegment(AudienceSegment segment, boolean isForCreation) throws SnapArgumentException {
        StringBuilder sb = new StringBuilder();
        if (segment != null) {
            if (isForCreation) {
                if (segment.getSourceType() == null) {
                    sb.append("The source type is required,");
                }
            }
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<AudienceSegment>> violations = validator.validate(segment);
            for (ConstraintViolation<AudienceSegment> violation : violations) {
                sb.append(violation.getMessage()).append(",");
            }
        } else {
            sb.append("Segment parameter is required,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkAudienceSegment()

    private void checkSamLookalikes(SamLookalikes sam) throws SnapArgumentException {
        StringBuilder sb = new StringBuilder();
        if (sam != null) {
            if (sam.getRetentionInDays() > 180) {
                sb.append("The retention must be equal or less than 180 days,");
            }
            if (sam.getSourceType() == null) {
                sb.append("The source type is required,");
            }
            if (sam.getSourceType() != null && sam.getSourceType() != SourceTypeEnum.LOOKALIKE) {
                sb.append("The source type must be LOOKALIKE,");
            }
            if (sam.getCreationSpec() == null) {
                sb.append("Lookalike creation spec is required,");
            }
            if (sam.getCreationSpec() != null) {
                if (StringUtils.isEmpty(sam.getCreationSpec().getCountry())) {
                    sb.append("Lookalike creation spec country is required,");
                }
                if (StringUtils.isEmpty(sam.getCreationSpec().getSeedSegmentId())) {
                    sb.append("Lookalike creation spec seed segment ID is required,");
                }
                if (sam.getCreationSpec().getType() == null) {
                    sb.append("Lookalike creation spec type is required,");
                }
            }
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<SamLookalikes>> violations = validator.validate(sam);
            for (ConstraintViolation<SamLookalikes> violation : violations) {
                sb.append(violation.getMessage()).append(",");
            }
        } else {
            sb.append("Sam Lookalikes parameter is required,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkSamLookalikes()

}// SnapAudienceSegment
