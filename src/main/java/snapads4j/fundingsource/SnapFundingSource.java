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
package snapads4j.fundingsource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExceptionsUtils;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.fundingsource.FundingSource;
import snapads4j.model.fundingsource.SnapHttpResponseFundingSource;
import snapads4j.utils.EntityUtilsWrapper;
import snapads4j.utils.FileProperties;
import snapads4j.utils.HttpUtils;

/**
 * SnapFundingSource
 *
 * @author Yassine
 */
@Getter
@Setter
public class SnapFundingSource implements SnapFundingSourceInterface {

  private FileProperties fp;

  private String apiUrl;

  private String endpointAllFundingSource;

  private String endpointSpecificFundingSource;

  private CloseableHttpClient httpClient;
  
  private EntityUtilsWrapper entityUtilsWrapper;

  private static final Logger LOGGER = LogManager.getLogger(SnapFundingSource.class);

  /** Constructor */
  public SnapFundingSource() {
    this.fp = new FileProperties();
    this.apiUrl = (String) fp.getProperties().get("api.url");
    this.endpointAllFundingSource =
        this.apiUrl + (String) fp.getProperties().get("api.url.funding.source.all");
    this.endpointSpecificFundingSource =
        this.apiUrl + (String) fp.getProperties().get("api.url.funding.source.one");
    this.httpClient = HttpClients.createDefault();
    this.entityUtilsWrapper = new EntityUtilsWrapper();
  } // SnapFundingSource()

  /**
   * Get all funding sources for the specified Organization.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-all-funding-sources">All funding
   *     source</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param organizationID Organization ID
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @return funding sources
   */
  @Override
  public List<FundingSource> getAllFundingSource(String oAuthAccessToken, String organizationID)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    if (StringUtils.isEmpty(organizationID)) {
      throw new SnapArgumentException("The organization ID is mandatory");
    }
    List<FundingSource> fundingSources = new ArrayList<>();
    final String url = this.endpointAllFundingSource.replace("{organization-id}", organizationID);
    HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try(CloseableHttpResponse response = httpClient.execute(request)) {
      int statusCode = response.getStatusLine().getStatusCode();
      HttpEntity entity = response.getEntity();
      if(entity != null) {
          String body = entityUtilsWrapper.toString(entity);
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      SnapHttpResponseFundingSource responseFromJson =
          mapper.readValue(body, SnapHttpResponseFundingSource.class);
      if (responseFromJson != null) {
        fundingSources = responseFromJson.getAllFundingSource();
      }}
    } catch (IOException e) {
      LOGGER.error("Impossible to get all funding source, organizationID = {}", organizationID, e);
    }
    return fundingSources;
  } // getAllFundingSource()

  /**
   * Get a specific funding source.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-a-specific-funding-source">Specific
   *     funding source</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id FundingSource ID
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @return funding source
   */
  @Override
  public Optional<FundingSource> getSpecificFundingSource(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
    if (StringUtils.isEmpty(id)) {
      throw new SnapArgumentException("The Funding source ID is mandatory");
    }
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    Optional<FundingSource> result = Optional.empty();
    final String url = this.endpointSpecificFundingSource + id;
    HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try(CloseableHttpResponse response = httpClient.execute(request)) {
      int statusCode = response.getStatusLine().getStatusCode();
      HttpEntity entity = response.getEntity();
      if(entity != null) {
          String body = entityUtilsWrapper.toString(entity);
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      SnapHttpResponseFundingSource responseFromJson =
          mapper.readValue(body, SnapHttpResponseFundingSource.class);
      if (responseFromJson != null) {
        result = responseFromJson.getSpecificFundingSource();
      }}
    } catch (IOException e) {
      LOGGER.error("Impossible to get specific funding source, id = {}", id, e);
    }
    return result;
  } // getSpecificFundingSource()
} // SnapFundingSource
