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
package snapads4j.organization;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
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
import snapads4j.model.organization.Organization;
import snapads4j.model.organization.OrganizationWithAdAccount;
import snapads4j.model.organization.SnapHttpResponseOrganization;
import snapads4j.model.organization.SnapHttpResponseOrganizationWithAdAccount;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;
import snapads4j.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SnapOrganization
 *
 * @see {https://developers.snapchat.com/api/docs/#organizations}
 */
@Getter
@Setter
public class SnapOrganization implements SnapOrganizationInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointAllOrganizations;

    private String endpointSpecificOrganization;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapOrganization.class);

    public SnapOrganization() {
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointAllOrganizations = (String) fp.getProperties().get("api.url.organizations.all");
        this.endpointSpecificOrganization = (String) fp.getProperties().get("api.url.organizations.one");
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    } // SnapOrganization()

    /**
     * Get all organizations
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @return All organizations
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapExecutionException
     * @see <a href="https://developers.snapchat.com/api/docs/#organizations">All
     * organizations</a>
     */
    @Override
    public List<Organization> getAllOrganizations(String oAuthAccessToken)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        List<Organization> organizations = new ArrayList<>();
        final String url = this.apiUrl + this.endpointAllOrganizations;
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
                SnapHttpResponseOrganization responseFromJson = mapper.readValue(body,
                        SnapHttpResponseOrganization.class);
                if (responseFromJson != null) {
                    organizations = responseFromJson.getAllOrganizations();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get all organizations", e);
            throw new SnapExecutionException("Impossible to get all organizations", e);
        }
        return organizations;
    } // getAllOrganizations()

    /**
     * Get all organizations with ad accounts
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @return All organizations
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapExecutionException
     * @see <a href="https://developers.snapchat.com/api/docs/#organizations">All
     * organizations with ad accounts</a>
     */
    @Override
    public List<OrganizationWithAdAccount> getAllOrganizationsWithAdAccounts(String oAuthAccessToken)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        List<OrganizationWithAdAccount> organizations = new ArrayList<>();
        final String url = this.apiUrl + this.endpointAllOrganizations + "?with_ad_accounts?true";
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
                SnapHttpResponseOrganizationWithAdAccount responseFromJson = mapper.readValue(body,
                        SnapHttpResponseOrganizationWithAdAccount.class);
                if (responseFromJson != null) {
                    organizations = responseFromJson.getAllOrganizations();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get all organizations with Ad-accounts", e);
            throw new SnapExecutionException("Impossible to get all organizations with Ad-accounts", e);
        }
        return organizations;
    } // getAllOrganizationsWithAdAccounts()

    /**
     * Get specific organization
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @param id               Organization ID
     * @return All organizations
     * @throws SnapResponseErrorException
     * @throws SnapOAuthAccessTokenException
     * @throws SnapExecutionException
     * @see <a href=
     * "https://developers.snapchat.com/api/docs/#organizations">Specific
     * organization</a>
     */
    @Override
    public Optional<Organization> getSpecificOrganization(String oAuthAccessToken, String id)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(id)) {
            throw new SnapArgumentException("The organization ID is required");
        }
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        Optional<Organization> result = Optional.empty();
        final String url = this.apiUrl + this.endpointSpecificOrganization + id;
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
                SnapHttpResponseOrganization responseFromJson = mapper.readValue(body,
                        SnapHttpResponseOrganization.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getOrganization();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get specific organization with id = {}", id, e);
            throw new SnapExecutionException("Impossible to get specific organization", e);
        }
        return result;
    } // getSpecificOrganization()
} // SnapOrganization
