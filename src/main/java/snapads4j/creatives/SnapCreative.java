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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.enums.CheckAdEnum;
import snapads4j.exceptions.*;
import snapads4j.model.creatives.*;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;
import snapads4j.utils.JsonUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Getter
@Setter
public class SnapCreative implements SnapCreativeInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointCreateCreative;

    private String endpointUpdateCreative;

    private String endpointSpecificCreative;

    private String endpointAllCreatives;

    private String endpointPreviewCreative;

    private int maxCharactersBrandname;

    private int maxCharactersHeadline;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapCreative.class);

    public SnapCreative() {
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointCreateCreative = this.apiUrl + fp.getProperties().get("api.url.creative.create");
        this.endpointUpdateCreative = this.apiUrl + fp.getProperties().get("api.url.creative.update");
        this.endpointSpecificCreative = this.apiUrl + fp.getProperties().get("api.url.creative.one");
        this.endpointAllCreatives = this.apiUrl + fp.getProperties().get("api.url.creative.all");
        this.endpointPreviewCreative = this.apiUrl + fp.getProperties().get("api.url.creative.preview");
        this.maxCharactersBrandname = Integer.parseInt((String) fp.getProperties().get("api.brandname.max.characters"));
        this.maxCharactersHeadline = Integer.parseInt((String) fp.getProperties().get("api.headline.max.characters"));
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapCreative()

    @Override
    public Optional<Creative> createCreative(String oAuthAccessToken, Creative creative)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkCreative(creative, CheckAdEnum.CREATION);
        Optional<Creative> result = Optional.empty();
        final String url = this.endpointCreateCreative.replace("{ad_account_id}", creative.getAdAccountId());
        SnapHttpRequestCreative reqBody = new SnapHttpRequestCreative();
        reqBody.addCreative(creative);
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
                SnapHttpResponseCreative responseFromJson = mapper.readValue(body, SnapHttpResponseCreative.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificCreative();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to create creative, ad_account_id = {}", creative.getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to create creative", e);
        }
        return result;
    }// createCreative()

    @Override
    public Optional<Creative> updateCreative(String oAuthAccessToken, Creative creative)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkCreative(creative, CheckAdEnum.UPDATE);
        Optional<Creative> result = Optional.empty();
        final String url = this.endpointUpdateCreative.replace("{ad_account_id}", creative.getAdAccountId());
        SnapHttpRequestCreative reqBody = new SnapHttpRequestCreative();
        reqBody.addCreative(creative);
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
                SnapHttpResponseCreative responseFromJson = mapper.readValue(body, SnapHttpResponseCreative.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificCreative();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to update creative, id = {}", creative.getId(), e);
            throw new SnapExecutionException("Impossible to update creative", e);
        }
        return result;
    }// updateCreative()

    @Override
    public Optional<Creative> getSpecificCreative(String oAuthAccessToken, String id)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(id)) {
            throw new SnapArgumentException("The Creative ID is required");
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
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseCreative responseFromJson = mapper.readValue(body, SnapHttpResponseCreative.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificCreative();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get specific Creative, id = {}", id, e);
            throw new SnapExecutionException("Impossible to get specific Creative", e);
        }
        return result;
    }// getSpecificCreative()

    @Override
    public List<Creative> getAllCreative(String oAuthAccessToken, String adAccountId)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        if (StringUtils.isEmpty(adAccountId)) {
            throw new SnapArgumentException("The AdAccount ID is required");
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
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponseCreative responseFromJson = mapper.readValue(body, SnapHttpResponseCreative.class);
                if (responseFromJson != null) {
                    results = responseFromJson.getAllCreatives();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get all creatives, adAccountId = {}", adAccountId, e);
            throw new SnapExecutionException("Impossible to get all creatives", e);
        }
        return results;
    }// getAllCreative()

    @Override
    public Map<String, Object> getPreviewCreative(String oAuthAccessToken, String creativeID)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
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
                    throw SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
                }
                ObjectMapper mapper = JsonUtils.initMapper();
                SnapHttpResponsePreviewCreative responseFromJson = mapper.readValue(body,
                        SnapHttpResponsePreviewCreative.class);
                if (responseFromJson != null) {
                    result.put("snapcodeLink", responseFromJson.getSnapCodeLink());
                    result.put("expiresAt", responseFromJson.getExpiresAt());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to get preview of creative, creativeID = {}", creativeID, e);
            throw new SnapExecutionException("Impossible to get preview of creative", e);
        }
        return result;
    }// getPreviewCreative()

    private void checkCreative(Creative creative, CheckAdEnum check) throws SnapArgumentException {
        if (check == null) {
            throw new SnapArgumentException("Please give type of checking Creative");
        }
        StringBuilder sb = new StringBuilder();
        if (creative != null) {
            AppInstallProperties appProperties = creative.getAppInstallProperties();
            WebViewProperties webViewProperties = creative.getWebViewProperties();
            DeepLinkProperties deepLinkProperties = creative.getDeepLinkProperties();
            PreviewProperties previewProperties = creative.getPreviewProperties();
            CollectionProperties collectionProperties = creative.getCollectionProperties();

            if (check == CheckAdEnum.UPDATE) {
                if (creative.getCallToAction() == null) {
                    sb.append("The call to action is required,");
                }
                if (deepLinkProperties != null) {
                    if (StringUtils.isEmpty(deepLinkProperties.getIosAppId())) {
                        sb.append("IOS App ID (Deep Link Properties) is required,");
                    }
                    if (StringUtils.isEmpty(deepLinkProperties.getAndroidAppUrl())) {
                        sb.append("Android App URL (Deep Link Properties) is required,");
                    }
                    if (StringUtils.isEmpty(deepLinkProperties.getFallbackType())) {
                        sb.append("Fallback Type (Deep Link Properties) is required,");
                    }
                    if (StringUtils.isEmpty(deepLinkProperties.getWebViewFallbackUrl())) {
                        sb.append("WebView Fallback Type URL (Deep Link Properties) is required,");
                    }
                }
            }
            if (StringUtils.isEmpty(creative.getAdAccountId())) {
                sb.append("The Ad Account ID is required,");
            }
            if (StringUtils.isEmpty(creative.getBrandName())) {
                sb.append("The brand name is required,");
            }
            if (StringUtils.isNotEmpty(creative.getBrandName()) && creative.getBrandName().length() > this.maxCharactersBrandname) {
                sb.append("The brand name max length is ").append(this.maxCharactersBrandname).append(" characters,");
            }
            if (StringUtils.isEmpty(creative.getHeadline())) {
                sb.append("The headline is required,");
            }
            if (StringUtils.isNotEmpty(creative.getHeadline()) && creative.getHeadline().length() > this.maxCharactersHeadline) {
                sb.append("The headline max length is ").append(this.maxCharactersHeadline).append(" characters,");
            }
            if (StringUtils.isEmpty(creative.getName())) {
                sb.append("The creative's name is required,");
            }
            if (StringUtils.isEmpty(creative.getTopSnapMediaId())) {
                sb.append("The top snap media ID is required,");
            }
            if (creative.getType() == null) {
                sb.append("The creative's type is required,");
            }
            if (creative.getLongformVideoProperties() != null
                    && StringUtils.isEmpty(creative.getLongformVideoProperties().getVideoMediaId())) {
                sb.append("Video Media ID (Long Form Video Properties) is required,");
            }

            if (webViewProperties != null && StringUtils.isEmpty(webViewProperties.getUrl())) {
                sb.append("URL (Web View Properties) is required,");
            }

            if (creative.getCompositeProperties() != null
                    && CollectionUtils.isEmpty(creative.getCompositeProperties().getCreativeIds())) {
                sb.append("Creative IDs (Composite Properties) is required,");
            }

            if (creative.getAdLensProperties() != null
                    && StringUtils.isEmpty(creative.getAdLensProperties().getLensMediaId())) {
                sb.append("Lens Media ID (Ad Lens Properties) is required,");
            }

            checkAppProperties(appProperties, sb);
            checkDeepLinkProperties(deepLinkProperties, sb);
            checkPreviewProperties(creative, previewProperties, sb);
            checkCollectionProperties(collectionProperties, sb);
        } else {
            sb.append("Creative parameter is not given,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkCreative()

    private void checkAppProperties(AppInstallProperties appProperties, StringBuilder sb) {
        if (appProperties != null) {
            if (StringUtils.isEmpty(appProperties.getAndroidAppUrl())) {
                sb.append("Android App URL (App Install Properties) is required,");
            }
            if (StringUtils.isEmpty(appProperties.getAppName())) {
                sb.append("App name (App Install Properties) is required,");
            }
            if (StringUtils.isEmpty(appProperties.getIconMediaId())) {
                sb.append("Icon Media ID (App Install Properties) is required,");
            }
            if (StringUtils.isEmpty(appProperties.getIosAppId())) {
                sb.append("IOS App ID (App Install Properties) is required,");
            }
        }
    }// checkAppProperties()

    private void checkDeepLinkProperties(DeepLinkProperties deepLinkProperties, StringBuilder sb) {
        if (deepLinkProperties != null) {
            if (StringUtils.isEmpty(deepLinkProperties.getDeepLinkUri())) {
                sb.append("Deep Link URI (Deep Link Properties) is required,");
            }
            if (StringUtils.isEmpty(deepLinkProperties.getAppName())) {
                sb.append("App name (Deep Link Properties) is required,");
            }
            if (StringUtils.isEmpty(deepLinkProperties.getIconMediaId())) {
                sb.append("Icon Media ID (Deep Link Properties) is required,");
            }
        }
    }// checkDeepLinkProperties()

    private void checkPreviewProperties(Creative creative, PreviewProperties previewProperties, StringBuilder sb) {
        if (previewProperties != null && creative != null) {
            if (StringUtils.isEmpty(creative.getPreviewCreativeId())) {
                sb.append("Preview Creative ID is required,");
            }
            if (StringUtils.isEmpty(previewProperties.getLogoMediaId())) {
                sb.append("Logo Media ID (Preview Properties) is required,");
            }
            if (StringUtils.isEmpty(previewProperties.getPreviewHeadline())) {
                sb.append("Preview Headline (Preview Properties) is required,");
            }
            if (StringUtils.isEmpty(previewProperties.getPreviewMediaId())) {
                sb.append("Preview Media ID (Preview Properties) is required,");
            }
        }
    }// checkDeepLinkProperties()

    private void checkCollectionProperties(CollectionProperties collectionProperties, StringBuilder sb) {
        if (collectionProperties != null) {
            if (StringUtils.isEmpty(collectionProperties.getInteractionZoneId())) {
                sb.append("Interaction Zone ID (Collection Properties) is required,");
            }
            if (StringUtils.isEmpty(collectionProperties.getDefaultFallbackInteractionType())) {
                sb.append("Default Fallback Interaction Type (Collection Properties) is required,");
            }
            if (StringUtils.isNotEmpty(collectionProperties.getDefaultFallbackInteractionType())
                    && collectionProperties.getWebViewProperties() == null
                    && collectionProperties.getDefaultFallbackInteractionType().equals("WEB_VIEW")) {
                sb.append("Web View Properties (Collection Properties) is required,");
            }
            if (StringUtils.isNotEmpty(collectionProperties.getDefaultFallbackInteractionType())
                    && collectionProperties.getDeepLinkProperties() == null
                    && collectionProperties.getDefaultFallbackInteractionType().equals("DEEP_LINK")) {
                sb.append("Deep Link Properties (Collection Properties) is required,");
            }
        }
    }// checkCollectionProperties()
}// SnapCreative
