package snap.api.adsquads;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Getter;
import lombok.Setter;
import snap.api.enums.CheckAdSquadEnum;
import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapExceptionsUtils;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.adsquads.AdSquad;
import snap.api.model.adsquads.SnapHttpRequestAdSquad;
import snap.api.utils.FileProperties;
import snap.api.utils.HttpUtils;

/**
 * SnapAdSquads
 *
 * @author Yassine
 */
@Getter
@Setter
public class SnapAdSquads implements SnapAdSquadsInterface {

  private FileProperties fp;

  private String apiUrl;

  private String endpointAllAdSquadsCampaign;

  private String endpointAllAdSquadsAdAccount;

  private String endpointSpecificAdSquad;

  private String endpointCreationAdSquad;

  private String endpointUpdateAdSquad;

  private String endpointDeleteAdSquad;

  private HttpClient httpClient;

  private static final Logger LOGGER = LogManager.getLogger(SnapAdSquads.class);

  /** Constructor */
  public SnapAdSquads() {
    this.fp = new FileProperties();
    this.apiUrl = (String) fp.getProperties().get("api.url");
    this.endpointAllAdSquadsCampaign =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.all");
    this.endpointAllAdSquadsAdAccount =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.all2");
    this.endpointSpecificAdSquad =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.one");
    this.endpointCreationAdSquad =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.create");
    this.endpointUpdateAdSquad =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.update");
    this.endpointDeleteAdSquad =
        this.apiUrl + (String) fp.getProperties().get("api.url.adsquads.delete");
    this.httpClient = HttpClient.newHttpClient();
  } // SnapAdSquads()

  @Override
  public void createAdSquad(String oAuthAccessToken, AdSquad adSquad)
      throws JsonProcessingException, SnapOAuthAccessTokenException, SnapResponseErrorException,
          SnapArgumentException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    checkAdSquad(adSquad, CheckAdSquadEnum.CREATION);
    final String url =
        this.endpointCreationAdSquad.replace("{campaign_id}", adSquad.getCampaignId());
    SnapHttpRequestAdSquad reqBody = new SnapHttpRequestAdSquad();
    reqBody.addAdSquad(adSquad);
    LOGGER.info("Body create ad squad => {}", reqBody);
    HttpRequest request = HttpUtils.preparePostRequestObject(url, oAuthAccessToken, reqBody);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
    } catch (IOException | InterruptedException e) {
      LOGGER.error("Impossible to create ad squad, campaign_id = {}", adSquad.getCampaignId(), e);
    }
  } // createAdSquad()

  @Override
  public void updateAdSquad(String oAuthAccessToken, AdSquad adSquad)
      throws SnapOAuthAccessTokenException, JsonProcessingException, SnapResponseErrorException,
          SnapArgumentException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    checkAdSquad(adSquad, CheckAdSquadEnum.UPDATE);
    final String url = this.endpointUpdateAdSquad.replace("{campaign_id}", adSquad.getCampaignId());
    SnapHttpRequestAdSquad reqBody = new SnapHttpRequestAdSquad();
    reqBody.addAdSquad(adSquad);
    LOGGER.info("Body update ad squad => {}", reqBody);
    HttpRequest request = HttpUtils.preparePutRequestObject(url, oAuthAccessToken, reqBody);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
    } catch (IOException | InterruptedException e) {
      LOGGER.error("Impossible to update ad squad, id = {}", adSquad.getId(), e);
    }
  } // updateAdSquad()

  @Override
  public List<AdSquad> getAllAdSquadsFromCampaign(String oAuthAccessToken, String campaignId) {
    // TODO Auto-generated method stub
    return null;
  } // getAllAdSquadsFromCampaign()

  @Override
  public List<AdSquad> getAllAdSquadsFromAdAccount(String oAuthAccessToken, String adAccountId) {
    // TODO Auto-generated method stub
    return null;
  } // getAllAdSquadsFromAdAccount()

  @Override
  public Optional<AdSquad> getSpecificAdSquad(String oAuthAccessToken, String id) {
    // TODO Auto-generated method stub
    return null;
  } // getSpecificAdSquad()

  @Override
  public void deleteAdSquad(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException {
    if (StringUtils.isEmpty(oAuthAccessToken)) {
      throw new SnapOAuthAccessTokenException("The OAuthAccessToken must to be given");
    }
    if (StringUtils.isEmpty(id)) {
      throw new SnapArgumentException("The Ad Squad ID is mandatory");
    }
    final String url = this.endpointDeleteAdSquad + id;
    HttpRequest request = HttpUtils.prepareDeleteRequest(url, oAuthAccessToken);
    try {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      if (statusCode >= 300) {
        SnapResponseErrorException ex =
            SnapExceptionsUtils.getResponseExceptionByStatusCode(statusCode);
        throw ex;
      }
    } catch (IOException | InterruptedException e) {
      LOGGER.error("Impossible to delete specific ad squad, id = {}", id, e);
    }
  } // deleteAdSquad()

  private void checkAdSquad(AdSquad adSquad, CheckAdSquadEnum check) throws SnapArgumentException {
    if (check == null) {
      throw new SnapArgumentException("Please give type of checking Ad Squad");
    }
    StringBuilder sb = new StringBuilder();
    if (adSquad != null) {
      if (check == CheckAdSquadEnum.UPDATE) {
        if (StringUtils.isEmpty(adSquad.getId())) {
          sb.append("The Ad Squad ID is required,");
        }
        if (adSquad.getBillingEvent() == null) {
          sb.append("The Billing event is required,");
        }
      } else {
        if (adSquad.getOptimizationGoal() == null) {
          sb.append("The optimization goal is required,");
        }
        if (adSquad.getPlacement() == null) {
          sb.append("The placement is required,");
        }
        if (adSquad.getType() == null) {
          sb.append("The type is required,");
        }
      }
      if (StringUtils.isEmpty(adSquad.getCampaignId())) {
        sb.append("The Campaign ID is required,");
      }
      if (adSquad.getBidMicro() == null) {
        sb.append("The bid micro is required,");
      }
      if (adSquad.getDailyBudgetMicro() == null) {
        sb.append("The daily budget micro is required,");
      }
      if (adSquad.getDailyBudgetMicro() != null && adSquad.getDailyBudgetMicro() < 20000000) {
        sb.append("The daily budget micro minimum value is 20000000,");
      }
      if (adSquad.getLifetimeBudgetMicro() == null) {
        sb.append("The lifetime budget micro is required,");
      }
      if (StringUtils.isEmpty(adSquad.getName())) {
        sb.append("The Ad Squad name is required,");
      }
      if (adSquad.getStatus() == null) {
        sb.append("The status is required,");
      }
      if (adSquad.getTargeting() == null) {
        sb.append("The targeting is required,");
      }
    } else {
      sb.append("Ad squad parameter is not given,");
    }
    String finalErrors = sb.toString();
    if (!StringUtils.isEmpty(finalErrors)) {
      finalErrors = finalErrors.substring(0, finalErrors.length() - 1);
      throw new SnapArgumentException(finalErrors);
    }
  } // checkAdSquad()
} // SnapAdSquads
