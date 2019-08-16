package snap.api.fundingsource;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.fundingsource.FundingSource;
import snap.api.model.fundingsource.SnapHttpResponseFundingSource;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

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

  private HttpClient httpClient;

  private static final Logger LOGGER = LogManager.getLogger(SnapFundingSource.class);

  /** Constructor */
  public SnapFundingSource() {
    this.fp = new FileProperties();
    this.apiUrl = (String) fp.getProperties().get("api.url");
    this.endpointAllFundingSource =
        this.apiUrl + (String) fp.getProperties().get("api.url.funding.source.all");
    this.endpointSpecificFundingSource =
        this.apiUrl + (String) fp.getProperties().get("api.url.funding.source.one");
    this.httpClient = HttpClient.newHttpClient();
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
    HttpRequest request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      String body = response.body();
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
      }
    } catch (IOException | InterruptedException e) {
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
    HttpRequest request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      String body = response.body();
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
      }
    } catch (IOException | InterruptedException e) {
      LOGGER.error("Impossible to get specific funding source, id = {}", id, e);
    }
    return result;
  } // getSpecificFundingSource()
} // SnapFundingSource
