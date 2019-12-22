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

package snapads4j.adaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.exceptions.*;
import snapads4j.model.Pagination;
import snapads4j.model.adaccount.AdAccount;
import snapads4j.model.adaccount.SnapHttpRequestAdAccount;
import snapads4j.model.adaccount.SnapHttpResponseAdAccount;
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

/**
 * SnapAdAccount
 *
 * @see {https://developers.snapchat.com/api/docs/#ad-accounts}
 *
 * @author Yassine
 */
@Getter
@Setter
public class SnapAdAccount implements SnapAdAccountInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointAllAdAccounts;

    private String endpointSpecificAdAccount;

    private String endpointUpdateAdAccount;

    private int minLimitPagination;

    private int maxLimitPagination;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapAdAccount.class);

    /**
     * Constructor
     */
    public SnapAdAccount() {
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointAllAdAccounts = this.apiUrl + fp.getProperties().get("api.url.adaccount.all");
        this.endpointSpecificAdAccount = this.apiUrl + fp.getProperties().get("api.url.adaccount.one");
        this.endpointUpdateAdAccount = this.apiUrl + fp.getProperties().get("api.url.adaccount.update");
        this.minLimitPagination = Integer.parseInt((String) fp.getProperties().get("api.url.pagination.limit.min"));
        this.maxLimitPagination = Integer.parseInt((String) fp.getProperties().get("api.url.pagination.limit.max"));
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    } // SnapAdAccount()

    /**
     * Get all ad accounts
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param organizationID   Organization ID
     * @return All ad accounts
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws SnapExecutionException
     * @see <a href=
     * "https://developers.snapchat.com/api/docs/#get-all-ad-accounts">All Ad
     * Accounts</a>
     */
    @Override
    public List<Pagination<AdAccount>> getAllAdAccounts(String oAuthAccessToken, String organizationID, int limit)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(organizationID)) {
            throw new SnapArgumentException("The organization ID is required");
        }
        if(limit < minLimitPagination){
            throw new SnapArgumentException("Minimum limit is " + minLimitPagination);
        }
        if(limit > maxLimitPagination){
            throw new SnapArgumentException("Maximum limit is " + maxLimitPagination);
        }
        List<Pagination<AdAccount>> adAccounts = new ArrayList<>();
        String url = this.endpointAllAdAccounts.replace("{organization-id}", organizationID);
        url += "?limit=" + limit;
        boolean hasNextPage = true;
        int numberPage = 1;
        while(hasNextPage) {
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
                    SnapHttpResponseAdAccount responseFromJson = mapper.readValue(body, SnapHttpResponseAdAccount.class);
                    if (responseFromJson != null) {
                        adAccounts.add(new Pagination<>(numberPage++, responseFromJson.getAllAdAccounts()));
                        hasNextPage = responseFromJson.hasPaging();
                        if(hasNextPage){
                            url = responseFromJson.getPaging().getNextLink();
                            LOGGER.info("Next url page pagination is {}", url);
                        }
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Impossible to get all ad accounts, organizationID = {}", organizationID, e);
                throw new SnapExecutionException("Impossible to get all ad accounts", e);
            }
        }
        return adAccounts;
    } // getAllAdAccounts()

    /**
     * Get specific ad account
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               Organization ID
     * @return specific ad account
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapExecutionException
     * @see <a href=
     * "https://developers.snapchat.com/api/docs/#get-a-specific-ad-account">Specific
     * Ad Account</a>
     */
    @Override
    public Optional<AdAccount> getSpecificAdAccount(String oAuthAccessToken, String id)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(id)) {
            throw new SnapArgumentException("The Ad Account ID is required");
        }
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        Optional<AdAccount> result = Optional.empty();
        final String url = this.endpointSpecificAdAccount + id;
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
                SnapHttpResponseAdAccount responseFromJson = mapper.readValue(body, SnapHttpResponseAdAccount.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificAdAccount();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get specific ad account, id = {}", id, e);
            throw new SnapExecutionException("Impossible to get specific ad account", e);
        }
        return result;
    } // getSpecificAdAccount()

    /**
     * Update a specific ad account
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param adAccount        ad account to update
     * @return AdAccount updated
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapArgumentException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     * @throws SnapExecutionException
     * @see <a href=
     * "https://developers.snapchat.com/api/docs/#update-an-ad-accounts-lifetime-spend-cap">Update
     * Ad Account</a>
     */
    @Override
    public Optional<AdAccount> updateAdAccount(String oAuthAccessToken, AdAccount adAccount)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        this.checkAdAccount(adAccount);
        Optional<AdAccount> result = Optional.empty();
        final String url = this.endpointUpdateAdAccount.replace("{organization-id}", adAccount.getOrganizationId());
        SnapHttpRequestAdAccount reqBody = new SnapHttpRequestAdAccount();
        reqBody.addAdAccount(adAccount);
        HttpPut request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                String body = entityUtilsWrapper.toString(entity);
                SnapHttpResponseAdAccount responseFromJson = mapper.readValue(body, SnapHttpResponseAdAccount.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificAdAccount();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to update ad account, id = {}", adAccount.getId(), e);
            throw new SnapExecutionException("Impossible to update ad account", e);
        }
        return result;
    } // updateAdAccount()

    /**
     * Check the requirements of an ad account.
     *
     * @param adAccount adAccount
     * @throws SnapArgumentException
     * @see <a href=
     * "https://developers.snapchat.com/api/docs/#ad-accounts">Requirements</a>
     */
    private void checkAdAccount(AdAccount adAccount) throws SnapArgumentException {
        StringBuilder sb = new StringBuilder();
        if (adAccount != null) {
            if (StringUtils.isEmpty(adAccount.getId())) {
                sb.append("The ad account ID is required,");
            }
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<AdAccount>> violations = validator.validate(adAccount);
            for (ConstraintViolation<AdAccount> violation : violations) {
                sb.append(violation.getMessage()).append(",");
            }
        } else {
            sb.append("Ad account parameter is required,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    } // checkAdAccount()

} // SnapAdAccount
