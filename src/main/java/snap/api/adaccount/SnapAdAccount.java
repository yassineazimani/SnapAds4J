package snap.api.adaccount;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.adaccount.AdAccount;
import snap.api.model.adaccount.SnapHttpRequestAdAccount;
import snap.api.model.adaccount.SnapHttpResponseAdAccount;
import snap.api.utils.EntityUtilsWrapper;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

/**
 * SnapAdAccount
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

  private CloseableHttpClient httpClient;
  
  private EntityUtilsWrapper entityUtilsWrapper;

  private static final Logger LOGGER = LogManager.getLogger(SnapAdAccount.class);

  /** Constructor */
  public SnapAdAccount() {
    this.fp = new FileProperties();
    this.apiUrl = (String) fp.getProperties().get("api.url");
    this.endpointAllAdAccounts =
        this.apiUrl + (String) fp.getProperties().get("api.url.adaccount.all");
    this.endpointSpecificAdAccount =
        this.apiUrl + (String) fp.getProperties().get("api.url.adaccount.one");
    this.endpointUpdateAdAccount =
        this.apiUrl + (String) fp.getProperties().get("api.url.adaccount.update");
    this.httpClient = HttpClients.createDefault();
    this.entityUtilsWrapper = new EntityUtilsWrapper();
  } // SnapAdAccount()

  /**
   * Get all ad accounts
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-all-ad-accounts">All Ad
   *     Accounts</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param organizationID Organization ID
   * @return All ad accounts
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   */
  @Override
  public List<AdAccount> getAllAdAccounts(String oAuthAccessToken, String organizationID)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    if (StringUtils.isEmpty(organizationID)) {
      throw new SnapArgumentException("The organization ID is mandatory");
    }
    List<AdAccount> adAccounts = new ArrayList<>();
    final String url = this.endpointAllAdAccounts.replace("{organization-id}", organizationID);
    HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try (CloseableHttpResponse response = httpClient.execute(request)) {
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
          SnapHttpResponseAdAccount responseFromJson =
              mapper.readValue(body, SnapHttpResponseAdAccount.class);
          if (responseFromJson != null) {
            adAccounts = responseFromJson.getAllAdAccounts();
          }
      }
    } catch (IOException e) {
      LOGGER.error("Impossible to get all ad accounts, organizationID = {}", organizationID, e);
    }
    return adAccounts;
  } // getAllAdAccounts()

  /**
   * Get specific ad account
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-a-specific-ad-account">Specific Ad
   *     Account</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id Organization ID
   * @return specific ad account
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   */
  @Override
  public Optional<AdAccount> getSpecificAdAccount(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
    if (StringUtils.isEmpty(id)) {
      throw new SnapArgumentException("The Ad Account ID is mandatory");
    }
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    Optional<AdAccount> result = Optional.empty();
    final String url = this.endpointSpecificAdAccount + id;
    HttpGet request = HttpUtils.prepareGetRequest(url, oAuthAccessToken);
    try (CloseableHttpResponse response = httpClient.execute(request)) {
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
	    SnapHttpResponseAdAccount responseFromJson =
		    mapper.readValue(body, SnapHttpResponseAdAccount.class);
	    if (responseFromJson != null) {
		result = responseFromJson.getSpecificAdAccount();
	    }
      }
    } catch (IOException e) {
      LOGGER.error("Impossible to get specific ad account, id = {}", id, e);
    }
    return result;
  } // getSpecificAdAccount()

  /**
   * Update a specific ad account
   *
   * @see <a
   *     href="https://developers.snapchat.com/api/docs/#update-an-ad-accounts-lifetime-spend-cap">Update
   *     Ad Account</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param adAccount ad account to update
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @throws JsonProcessingException
   * @throws UnsupportedEncodingException 
   */
  @Override
  public Optional<AdAccount> updateAdAccount(String oAuthAccessToken, AdAccount adAccount)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
          JsonProcessingException, UnsupportedEncodingException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    this.checkAdAccount(adAccount);
    Optional<AdAccount> result = Optional.empty();
    final String url =
        this.endpointUpdateAdAccount.replace("{organization-id}", adAccount.getOrganizationId());
    SnapHttpRequestAdAccount reqBody = new SnapHttpRequestAdAccount();
    reqBody.addAdAccount(this.convertAdAccountToMap(adAccount));
    HttpPut request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
    try (CloseableHttpResponse response = httpClient.execute(request)){
	int statusCode = response.getStatusLine().getStatusCode();
	HttpEntity entity = response.getEntity();
	if(entity != null) {
          if (statusCode >= 300) {
            SnapResponseErrorException ex =
                SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
            throw ex;
          }
          ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    String body = entityUtilsWrapper.toString(entity);
	    SnapHttpResponseAdAccount responseFromJson =
		    mapper.readValue(body, SnapHttpResponseAdAccount.class);
	    if (responseFromJson != null) {
		result = responseFromJson.getSpecificAdAccount();
	    }
      }
    } catch (IOException e) {
      LOGGER.error("Impossible to update ad account, id = {}", adAccount.getId(), e);
    }
    return result;
  } // updateAdAccount()

  /**
   * Check the requirements of an ad account.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#ad-accounts">Requirements</a>
   * @param adAccount adAccount
   * @throws SnapArgumentException
   */
  private void checkAdAccount(AdAccount adAccount) throws SnapArgumentException {
    StringBuilder sb = new StringBuilder();
    if (adAccount != null) {
      if (StringUtils.isEmpty(adAccount.getAdvertiser())) {
        sb.append("The name of advertiser is required,");
      }
      if (adAccount.getCurrency() == null) {
        sb.append("The currency is required,");
      }
      if (CollectionUtils.isEmpty(adAccount.getFundingSourceIds())) {
        sb.append("The funding source ids are required,");
      }
      if (StringUtils.isEmpty(adAccount.getId())) {
        sb.append("The ad account ID is required,");
      }
      if (StringUtils.isEmpty(adAccount.getName())) {
        sb.append("The name is required,");
      }
      if (StringUtils.isEmpty(adAccount.getOrganizationId())) {
        sb.append("The organization ID is required,");
      }
      if (StringUtils.isEmpty(adAccount.getTimezone())) {
        sb.append("The time zone is required,");
      }
      if (adAccount.getType() == null) {
        sb.append("The ad account type is required,");
      }
    } else {
      sb.append("Ad account parameter is not given,");
    }
    String finalErrors = sb.toString();
    if (!StringUtils.isEmpty(finalErrors)) {
      finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
      throw new SnapArgumentException(finalErrors);
    }
  } // checkAdAccount()

  /**
   * Convert an ad account instance to a map
   *
   * @param adAccount
   * @return map
   */
  private Map<String, String> convertAdAccountToMap(AdAccount adAccount) {
    Map<String, String> result = new HashMap<>();
    if (adAccount != null) {
      if (StringUtils.isNotEmpty(adAccount.getAdvertiser())) {
        result.put("advertiser", adAccount.getAdvertiser());
      }
      if (StringUtils.isNotEmpty(adAccount.getAdvertiserOrganizationId())) {
        result.put("advertiser_organization_id", adAccount.getAdvertiserOrganizationId());
      }
      if (StringUtils.isNotEmpty(adAccount.getBrandName())) {
        result.put("brand_name", adAccount.getBrandName());
      }
      if (StringUtils.isNotEmpty(adAccount.getId())) {
        result.put("id", adAccount.getId());
      }
      if (StringUtils.isNotEmpty(adAccount.getName())) {
        result.put("name", adAccount.getName());
      }
      if (StringUtils.isNotEmpty(adAccount.getOrganizationId())) {
        result.put("organization_id", adAccount.getOrganizationId());
      }
      if (StringUtils.isNotEmpty(adAccount.getTimezone())) {
        result.put("timezone", adAccount.getTimezone());
      }
      if (adAccount.getCurrency() != null) {
        result.put("currency", adAccount.getCurrency().toString());
      }
      if (CollectionUtils.isNotEmpty(adAccount.getFundingSourceIds())) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < adAccount.getFundingSourceIds().size(); ++i) {
          sb.append(adAccount.getFundingSourceIds().get(i));
          if (i < adAccount.getFundingSourceIds().size() - 1) {
            sb.append(",");
          }
        }
        sb.append("]");
        result.put("funding_source_ids", sb.toString());
      }
      if (adAccount.getLifetimeSpendCapMicro() != null) {
        result.put("lifetime_spend_cap_micro", adAccount.getLifetimeSpendCapMicro().toString());
      }
      if (adAccount.getStatus() != null) {
        result.put("status", adAccount.getStatus().toString());
      }
      if (adAccount.getType() != null) {
        result.put("type", adAccount.getType().toString());
      }
      LOGGER.debug("convertAdAccountToMap {}", result);
    }
    return result;
  } // convertAdAccountToMap()
} // SnapAdAccount
