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
package snapads4j.creatives.elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snapads4j.enums.CreativeTypeEnum;
import snapads4j.enums.InteractionTypeEnum;
import snapads4j.exceptions.*;
import snapads4j.model.creatives.elements.*;
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
 * SnapCreativeElement
 *
 * @see {https://developers.snapchat.com/api/docs/#creative-elements}
 */
@Getter
@Setter
public class SnapCreativeElement implements SnapCreativeElementInterface {

    private FileProperties fp;

    private String apiUrl;

    private String endpointCreate;

    private String endpointCreateMultiple;

    private String endpointCreateInteractionZone;

    private CloseableHttpClient httpClient;

    private EntityUtilsWrapper entityUtilsWrapper;

    private static final Logger LOGGER = LogManager.getLogger(SnapCreativeElement.class);

    public SnapCreativeElement() throws IOException{
        this.fp = new FileProperties();
        this.apiUrl = (String) fp.getProperties().get("api.url");
        this.endpointCreate = this.apiUrl + fp.getProperties().get("api.url.creative.element.create");
        this.endpointCreateMultiple = this.apiUrl + fp.getProperties().get("api.url.creative.element.create.multiple");
        this.endpointCreateInteractionZone = this.apiUrl + fp.getProperties().get("api.url.interaction.zone.create");
        this.httpClient = HttpClients.createDefault();
        this.entityUtilsWrapper = new EntityUtilsWrapper();
    }// SnapCreativeElement()

    @Override
    public Optional<CreativeElement> createCreativeElement(String oAuthAccessToken, CreativeElement creative)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkCreativeElement(creative);
        Optional<CreativeElement> result = Optional.empty();
        final String url = this.endpointCreate.replace("{ad_account_id}", creative.getAdAccountId());
        SnapHttpRequestCreativeElement reqBody = new SnapHttpRequestCreativeElement();
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
                SnapHttpResponseCreativeElement responseFromJson = mapper.readValue(body, SnapHttpResponseCreativeElement.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificCreative();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to create creative element, ad_account_id = {}", creative.getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to create creative element", e);
        }
        return result;
    }// createCreativeElement()

    @Override
    public List<CreativeElement> createCreativeElements(String oAuthAccessToken, List<CreativeElement> creatives)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkCreativeElements(creatives);
        List<CreativeElement> results = new ArrayList<>();
        final String url = this.endpointCreate.replace("{ad_account_id}", creatives.get(0).getAdAccountId());
        SnapHttpRequestCreativeElement reqBody = new SnapHttpRequestCreativeElement();
        creatives.forEach(reqBody::addCreative);
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
                SnapHttpResponseCreativeElement responseFromJson = mapper.readValue(body, SnapHttpResponseCreativeElement.class);
                if (responseFromJson != null) {
                    results = responseFromJson.getAllCreatives();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to create creative elements, ad_account_id = {}", creatives.get(0).getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to create creative elements", e);
        }
        return results;
    }// createCreativeElements()

    @Override
    public Optional<InteractionZone> createInteractionZone(String oAuthAccessToken, InteractionZone interactionZone)
            throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
            JsonProcessingException, UnsupportedEncodingException, SnapExecutionException {
        if (StringUtils.isEmpty(oAuthAccessToken)) {
            throw new SnapOAuthAccessTokenException("The OAuthAccessToken is required");
        }
        checkInteractionZone(interactionZone);
        Optional<InteractionZone> result = Optional.empty();
        final String url = this.endpointCreateInteractionZone.replace("{ad_account_id}", interactionZone.getAdAccountId());
        SnapHttpRequestInteractionZone reqBody = new SnapHttpRequestInteractionZone();
        reqBody.addInteractionZone(interactionZone);
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
                SnapHttpResponseInteractionZone responseFromJson = mapper.readValue(body, SnapHttpResponseInteractionZone.class);
                if (responseFromJson != null) {
                    result = responseFromJson.getSpecificInteractionZone();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Impossible to create interaction zone, ad_account_id = {}", interactionZone.getAdAccountId(), e);
            throw new SnapExecutionException("Impossible to create interaction zone", e);
        }
        return result;
    }// createInteractionZone()

    private void checkCommonCreativeElement(CreativeElement creative, StringBuilder sb, Integer idx) {
        if (StringUtils.isEmpty(creative.getAdAccountId())) {
            if (idx != null) {
                sb.append("CreativeElement index n°").append(idx).append(" : ");
            }
            sb.append("The Ad Account ID is required,");
        }
        if (StringUtils.isEmpty(creative.getName())) {
            if (idx != null) {
                sb.append("CreativeElement index n°").append(idx).append(" : ");
            }
            sb.append("The name is required,");
        }
        if (creative.getType() == null) {
            if (idx != null) {
                sb.append("CreativeElement index n°").append(idx).append(" : ");
            }
            sb.append("The creative type is required,");
        }
        if (creative.getInteractionType() == null) {
            if (idx != null) {
                sb.append("CreativeElement index n°").append(idx).append(" : ");
            }
            sb.append("The interaction type is required,");
        }
        if (creative.getType() != null && creative.getType() == CreativeTypeEnum.BUTTON
                && creative.getButtonProperties() == null) {
            if (idx != null) {
                sb.append("CreativeElement index n°").append(idx).append(" : ");
            }
            sb.append("Button Properties is required,");
        }
        if (creative.getInteractionType() != null && creative.getInteractionType() == InteractionTypeEnum.WEB_VIEW
                && creative.getWebViewProperties() == null) {
            if (idx != null) {
                sb.append("CreativeElement index n°").append(idx).append(" : ");
            }
            sb.append("Web View Properties is required,");
        }
        if (creative.getInteractionType() != null && creative.getInteractionType() == InteractionTypeEnum.DEEP_LINK
                && creative.getDeepLinkProperties() == null) {
            if (idx != null) {
                sb.append("CreativeElement index n°").append(idx).append(" : ");
            }
            sb.append("Deep Link Properties is required,");
        }
    }// checkCommonCreativeElement()

    private void checkCreativeElement(CreativeElement creative) throws SnapArgumentException {
        StringBuilder sb = new StringBuilder();
        if (creative != null) {
            checkCommonCreativeElement(creative, sb, null);
        } else {
            sb.append("Creative parameter is required,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkCreativeElement()

    private void checkCreativeElements(List<CreativeElement> creativeElements) throws SnapArgumentException {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(creativeElements)) {
            for (int i = 0; i < creativeElements.size(); ++i) {
                CreativeElement c = creativeElements.get(i);
                checkCommonCreativeElement(c, sb, i);
            }
        } else {
            sb.append("Creative elements parameter is required,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkCreativeElements()

    private void checkInteractionZone(InteractionZone interactionZone) throws SnapArgumentException {
        StringBuilder sb = new StringBuilder();
        if (interactionZone != null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<InteractionZone>> violations = validator.validate(interactionZone);
            for (ConstraintViolation<InteractionZone> violation : violations) {
                sb.append(violation.getMessage()).append(",");
            }
        } else {
            sb.append("Interaction Zone parameter is required,");
        }
        String finalErrors = sb.toString();
        if (!StringUtils.isEmpty(finalErrors)) {
            finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
            throw new SnapArgumentException(finalErrors);
        }
    }// checkInteractionZone()

}// SnapCreativeElement
