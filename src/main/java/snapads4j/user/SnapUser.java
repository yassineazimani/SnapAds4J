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
package snapads4j.user;

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
import snapads4j.exceptions.SnapExceptionsUtils;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.user.AuthenticatedUser;
import snapads4j.model.user.SnapHttpResponseUser;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;

import java.io.IOException;
import java.util.Optional;

@Getter
@Setter
public class SnapUser implements SnapUserInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointMe;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapUser.class);

    /**
     * Constructor
     */
    public SnapUser() {
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointMe = this.apiUrl + fp.getProperties().get("api.url.user.me");
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    } // SnapUser()

    /**
     * Get informations about the authenticated user.
     *
     * @param oAuthAccessToken oAuthAccessToken
     * @return AuthenticatedUser {@link AuthenticatedUser}
     * @throws SnapOAuthAccessTokenException
     * @throws SnapResponseErrorException
     * @throws SnapExecutionException
     * @see <a href="https://developers.snapchat.com/api/docs/#user">User</a>
     */
    @Override
    public Optional<AuthenticatedUser> aboutMe(String oAuthAccessToken)
            throws SnapOAuthAccessTokenException, SnapResponseErrorException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        Optional<AuthenticatedUser> result = Optional.empty();
        HttpGet request = HttpUtils.prepareGetRequest(this.endpointMe, oAuthAccessToken);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String body = entityUtilsWrapper.toString(entity);
                if (statusCode >= 300) {
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = new ObjectMapper();
                SnapHttpResponseUser responseFromJson = mapper.readValue(body, SnapHttpResponseUser.class);
                if (responseFromJson != null) {
                    result = Optional.ofNullable(responseFromJson.getMe());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get informations about me", e);
            throw new SnapExecutionException("Impossible to get informations about me", e);
        }
        return result;
    } // aboutMe()
} // SnapUser
